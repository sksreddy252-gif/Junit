package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.osgi.service.component.annotations.ServiceScope.PROTOTYPE;

@Component(
    service = Servlet.class,
    scope = PROTOTYPE,
    property = {
        "sling.servlet.paths=/bin/myservlet",
        "sling.servlet.methods=GET"
    }
)
public class PathBasedServlet extends SlingSafeMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        response.setContentType("application/json");
        response.getWriter().write("{" + "\"message\": \"Hello from path-based servlet\"" + "}");
    }
}
