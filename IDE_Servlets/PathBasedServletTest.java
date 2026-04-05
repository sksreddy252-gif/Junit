package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PathBasedServletTest {

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    private PathBasedServlet servlet;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new PathBasedServlet();
        responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    /**
     * Test happy path for doGet method ensuring correct content type and payload.
     */
    @Test
    void testDoGetHappyPath() throws Exception {
        servlet.doGet(request, response);

        // Verify content type set correctly
        verify(response).setContentType("application/json");

        // Verify output matches expected JSON payload
        assertEquals(EXPECTED_JSON_PAYLOAD, responseWriter.toString());
    }

    /**
     * Test doGet when response.getWriter() throws IOException.
     * Should propagate IOException as per method signature.
     */
    @Test
    void testDoGetWriterThrowsIOException() throws Exception {
        when(response.getWriter()).thenThrow(new IOException("Writer error"));

        IOException thrown = assertThrows(IOException.class, () -> servlet.doGet(request, response));
        assertEquals("Writer error", thrown.getMessage());
    }

    /**
     * Test doGet when response.setContentType throws RuntimeException.
     * Should propagate RuntimeException.
     */
    @Test
    void testDoGetContentTypeThrowsRuntimeException() throws Exception {
        doThrow(new RuntimeException("Content type error")).when(response).setContentType(anyString());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> servlet.doGet(request, response));
        assertEquals("Content type error", thrown.getMessage());
    }

    private static final String EXPECTED_JSON_PAYLOAD =
            "{\"message\": \"Hello from path-based servlet\"}";
}