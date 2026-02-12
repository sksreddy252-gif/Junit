package com.example.core.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.resourceTypes=my/resource/type",
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=html",
        "sling.servlet.selectors=info"
    }
)
public class MyGetServlet extends SlingSafeMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request, 
                        SlingHttpServletResponse response) 
                        throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Response from " + 
            getClass().getName() + " at " + 
            new java.util.Date());
    }
}
