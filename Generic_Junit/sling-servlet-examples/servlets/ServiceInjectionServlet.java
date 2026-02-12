package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import com.example.core.services.DataService;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.resourceTypes=myapp/components/data",
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=json"
    }
)
public class ServiceInjectionServlet extends SlingSafeMethodsServlet {
    
    @Reference
    private DataService dataService;
    
    @Override
    protected void doGet(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        
        try {
            String data = dataService.fetchData(request.getResource());
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(data);
            
        } catch (Exception e) {
            response.sendError(
                SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error fetching data: " + e.getMessage()
            );
        }
    }
}
