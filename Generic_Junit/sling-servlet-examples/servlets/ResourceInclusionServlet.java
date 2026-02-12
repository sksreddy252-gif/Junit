package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.request.RequestDispatcherOptions;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.paths=/bin/include",
        "sling.servlet.methods=GET"
    }
)
public class ResourceInclusionServlet extends SlingSafeMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        
        ResourceResolver resolver = request.getResourceResolver();
        Resource targetResource = resolver.getResource("/content/mypage");
        
        if (targetResource != null) {
            RequestDispatcherOptions options = new RequestDispatcherOptions();
            options.setForceResourceType("myapp/components/page");
            
            // Include resource rendering
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                targetResource, options);
            
            // Note: This is simplified - actual implementation 
            // needs custom response wrapper
            dispatcher.include(request, response);
            
            response.setContentType("text/html");
        } else {
            response.sendError(404, "Resource not found");
        }
    }
}
