package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.request.RequestParameter;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.paths=/bin/formhandler",
        "sling.servlet.methods=POST"
    }
)
public class FormHandlerServlet extends SlingAllMethodsServlet {
    
    @Override
    protected void doPost(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
                         throws ServletException, IOException {
        
        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        
        // Handle file upload
        RequestParameter fileParam = request.getRequestParameter("file");
        if (fileParam != null) {
            InputStream stream = fileParam.getInputStream();
            String fileName = fileParam.getFileName();
            String contentType = fileParam.getContentType();
            
            // Process file...
            byte[] fileContent = IOUtils.toByteArray(stream);
            stream.close();
        }
        
        response.setContentType("application/json");
        response.getWriter().write(String.format(
            "{" + "\"status\": \"success\", \"name\": \"%s\", \"email\": \"%s\"" + "}",
            name, email));
    }
}
