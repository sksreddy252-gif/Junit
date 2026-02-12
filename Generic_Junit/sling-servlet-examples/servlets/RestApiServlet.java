package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.resourceTypes=myapp/api/resource",
        "sling.servlet.methods={GET,POST,PUT,DELETE}",
        "sling.servlet.extensions=json"
    }
)
public class RestApiServlet extends SlingAllMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(SlingHttpServletResponse.SC_OK);
        response.getWriter().write("{" + "\"method\": \"GET\"" + "}");
    }
    
    @Override
    protected void doPost(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
                         throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(SlingHttpServletResponse.SC_CREATED);
        response.getWriter().write("{" + "\"method\": \"POST\", \"status\": \"created\"" + "}");
    }
    
    @Override
    protected void doPut(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(SlingHttpServletResponse.SC_OK);
        response.getWriter().write("{" + "\"method\": \"PUT\", \"status\": \"updated\"" + "}");
    }
    
    @Override
    protected void doDelete(SlingHttpServletRequest request,
                           SlingHttpServletResponse response)
                           throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(SlingHttpServletResponse.SC_NO_CONTENT);
    }
}
