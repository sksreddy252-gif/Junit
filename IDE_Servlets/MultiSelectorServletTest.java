package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MultiSelectorServletTest {

    private MultiSelectorServlet servlet;
    private SlingHttpServletRequest request;
    private SlingHttpServletResponse response;
    private RequestPathInfo pathInfo;
    private StringWriter responseWriter;

    @BeforeEach
    public void setUp() throws Exception {
        servlet = new MultiSelectorServlet();
        request = mock(SlingHttpServletRequest.class);
        response = mock(SlingHttpServletResponse.class);
        pathInfo = mock(RequestPathInfo.class);
        responseWriter = new StringWriter();

        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    public void testJsonExtensionWithMobileSelector() throws Exception {
        when(pathInfo.getSelectors()).thenReturn(new String[]{"mobile"});
        when(pathInfo.getExtension()).thenReturn("json");

        servlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals("{\"format\": \"json\", \"mobile\": true}", responseWriter.toString());
    }

    @Test
    public void testXmlExtensionWithoutSelectors() throws Exception {
        when(pathInfo.getSelectors()).thenReturn(new String[]{});
        when(pathInfo.getExtension()).thenReturn("xml");

        servlet.doGet(request, response);

        verify(response).setContentType("application/xml");
        assertEquals("<response><format>xml</format></response>", responseWriter.toString());
    }

    @Test
    public void testHtmlExtensionWithPrintSelector() throws Exception {
        when(pathInfo.getSelectors()).thenReturn(new String[]{"print"});
        when(pathInfo.getExtension()).thenReturn("html");

        servlet.doGet(request, response);

        verify(response).setContentType("text/html");
        assertEquals("<html><body>Format: html, Print: true</body></html>", responseWriter.toString());
    }

    @Test
    public void testHtmlExtensionWithoutPrintSelector() throws Exception {
        when(pathInfo.getSelectors()).thenReturn(new String[]{"tablet"});
        when(pathInfo.getExtension()).thenReturn("html");

        servlet.doGet(request, response);

        verify(response).setContentType("text/html");
        assertEquals("<html><body>Format: html, Print: false</body></html>", responseWriter.toString());
    }
}