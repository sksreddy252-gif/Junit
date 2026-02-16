package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FormHandlerServletTest {

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    @Mock
    private RequestParameter fileParam;

    private FormHandlerServlet servlet;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
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

        verify(response).setContentType("application/json");
        String expectedJson = "{\"status\": \"success\", \"name\": \"John Doe\", \"email\": \"john@example.com\"}";
        assertEquals(expectedJson, responseWriter.toString());
    }

    @Test
    void testDoPostWithFile() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Jane Doe");
        when(request.getParameter("email")).thenReturn("jane@example.com");

        byte[] fileData = "Test File Content".getBytes();
        when(fileParam.getInputStream()).thenReturn(new ByteArrayInputStream(fileData));
        when(fileParam.getFileName()).thenReturn("test.txt");
        when(fileParam.getContentType()).thenReturn("text/plain");
        when(request.getRequestParameter("file")).thenReturn(fileParam);

        servlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(fileParam).getInputStream();
        verify(fileParam).getFileName();
        verify(fileParam).getContentType();
        String expectedJson = "{\"status\": \"success\", \"name\": \"Jane Doe\", \"email\": \"jane@example.com\"}";
        assertEquals(expectedJson, responseWriter.toString());
    }

    @Test
    void testDoPostFileIOException() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Error User");
        when(request.getParameter("email")).thenReturn("error@example.com");

        when(fileParam.getInputStream()).thenThrow(new IOException("Stream error"));
        when(request.getRequestParameter("file")).thenReturn(fileParam);

        assertThrows(IOException.class, () -> servlet.doPost(request, response));
    }
}
