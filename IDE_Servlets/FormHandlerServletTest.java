package com.example.core.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FormHandlerServletTest {

    private FormHandlerServlet servlet;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    @Mock
    private RequestParameter fileParam;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new FormHandlerServlet();
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testDoPostWithoutFile() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("John Doe");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getRequestParameter("file")).thenReturn(null);

        servlet.doPost(request, response);

        String output = responseWriter.toString();
        assertTrue(output.contains("\"status\": \"success\""));
        assertTrue(output.contains("John Doe"));
        assertTrue(output.contains("john@example.com"));
    }

    @Test
    void testDoPostWithFile() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Jane Doe");
        when(request.getParameter("email")).thenReturn("jane@example.com");
        when(fileParam.getInputStream()).thenReturn(new ByteArrayInputStream("filecontent".getBytes()));
        when(fileParam.getFileName()).thenReturn("test.txt");
        when(fileParam.getContentType()).thenReturn("text/plain");
        when(request.getRequestParameter("file")).thenReturn(fileParam);

        servlet.doPost(request, response);

        String output = responseWriter.toString();
        assertTrue(output.contains("\"status\": \"success\""));
        assertTrue(output.contains("Jane Doe"));
        assertTrue(output.contains("jane@example.com"));
    }
}