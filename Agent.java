import os, base64, mimetypes, requests
from io import BytesIO

DMS_BASE = os.environ.get("DMS_BASE", "https://v2-beta.hclaiforce.com")

def file_dl(**kwargs):
    # --- 1) Normalize payload regardless of wrapper ---
    if "file_dl" in kwargs and isinstance(kwargs["file_dl"], dict):
        payload = kwargs["file_dl"]
    elif "generate_download_link" in kwargs and isinstance(kwargs["generate_download_link"], dict):
        payload = kwargs["generate_download_link"]
    else:
        payload = kwargs  # assume flat

    # --- 2) Extract fields with defaults ---
    filename        = payload.get("filename")
    collection_id   = payload.get("collection_id")
    data_source     = payload.get("data_source", "AgentOutput")
    content_text    = payload.get("content_text")
    content_base64  = payload.get("content_base64")
    existing_file_id= payload.get("existing_file_id")
    token_override  = payload.get("token_override")

    if not filename:
        return {"error": "Missing 'filename'."}

    # --- 3) If just linking an existing file, no token needed ---
    if existing_file_id is not None:
        return {
            "url": f"/dms/file_download?file_id={existing_file_id}",
            "filename": filename,
            "file_id": existing_file_id
        }

    # --- 4) Otherwise we’re uploading -> need token ---
    token = token_override or os.getenv("DMS_TOKEN")
    if not token:
        return {"error": "Missing Bearer token. Set DMS_TOKEN env/secret or pass token_override."}

    # Build bytes
    if content_text is not None:
        data = content_text.encode("utf-8")
    elif content_base64 is not None:
        data = base64.b64decode(content_base64)
    else:
        return {"error": "Provide content_text or content_base64, or existing_file_id."}

    if not collection_id:
        return {"error": "Missing 'collection_id' for upload path."}

    # Upload to DMS
    headers = {"Authorization": f"Bearer {token}"}
    url = f"{DMS_BASE}/dms/collection/{collection_id}/add_files"
    mime = (mimetypes.guess_type(filename)[0] or "application/octet-stream")
    files = [("files", (filename, BytesIO(data), mime))]
    params = {"data_source": data_source}

    r = requests.post(url, headers=headers, params=params, files=files, timeout=120)
    if r.status_code == 404:
        return {"error": "Collection not found", "status": 404}
    if r.status_code >= 400:
        return {"error": f"Upload failed: {r.status_code}", "details": r.text}

    # Resolve file_id
    resp = r.json()
    file_id = None
    for f in resp.get("data", {}).get("files", []):
        if f.get("file_name") == filename:
            file_id = f.get("id"); break

    if file_id is None:
        # fallback: search by filename in the collection
        list_url = f"{DMS_BASE}/dms/collection/{collection_id}"
        q = {"search_query": filename, "page_number": 1, "page_size": 10}
        lr = requests.get(list_url, headers=headers, params=q, timeout=60)
        if lr.ok:
            for item in lr.json().get("data", {}).get("items", []):
                if item.get("file_name") == filename:
                    file_id = item.get("id"); break

    if file_id is None:
        return {"error": "Uploaded but could not resolve file_id"}

    return {"url": f"/dms/file_download?file_id={file_id}", "filename": filename, "file_id": file_id}