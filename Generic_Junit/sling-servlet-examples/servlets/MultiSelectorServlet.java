package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.request.RequestPathInfo;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.resourceTypes=myapp/components/article",
        "sling.servlet.methods=GET",
        "sling.servlet.selectors={print,mobile,tablet}",
        "sling.servlet.extensions={html,xml,json}"
    }
)
public class MultiSelectorServlet extends SlingSafeMethodsServlet {
    
    @Override
    protected void doGet(SlingHttpServletRequest request,
                        SlingHttpServletResponse response)
                        throws ServletException, IOException {
        
        RequestPathInfo pathInfo = request.getRequestPathInfo();
        String[] selectors = pathInfo.getSelectors();
        String extension = pathInfo.getExtension();
        
        // Determine response based on selectors
        boolean isPrint = containsSelector(selectors, "print");
        boolean isMobile = containsSelector(selectors, "mobile");
        
        // Set appropriate content type
        if ("json".equals(extension)) {
            response.setContentType("application/json");
            response.getWriter().write(
                String.format("{\"format\": \"%s\", \"mobile\": %b}",
                    extension, isMobile));
        } else if ("xml".equals(extension)) {
            response.setContentType("application/xml");
            response.getWriter().write(
                String.format("<response><format>%s</format></response>", 
                    extension));
        } else {
            response.setContentType("text/html");
            response.getWriter().write(
                String.format("<html><body>Format: %s, Print: %b</body></html>",
                    extension, isPrint));
        }
    }
    
    private boolean containsSelector(String[] selectors, String target) {
        if (selectors != null) {
            for (String selector : selectors) {
                if (target.equals(selector)) {
                    return true;
                }
            }
        }
        return false;
    }
}
