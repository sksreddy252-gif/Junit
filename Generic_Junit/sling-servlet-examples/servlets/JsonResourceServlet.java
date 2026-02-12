package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.resourceTypes=myapp/components/page",
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=json"
    }
)
public class JsonResourceServlet extends SlingSafeMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        
        Resource resource = request.getResource();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("path", resource.getPath());
        jsonBuilder.add("type", resource.getResourceType());
        
        // Add resource properties
        resource.getValueMap().forEach((key, value) -> {
            if (value != null) {
                jsonBuilder.add(key, value.toString());
            }
        });
        
        response.getWriter().write(jsonBuilder.build().toString());
    }
}
