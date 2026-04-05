package com.example.core.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for {@link MyGetServlet}.
 * This is categorized as a generic Java servlet file (non-Sling Model).
 */
public class MyGetServletTest {

    private MyGetServlet servlet;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        servlet = new MyGetServlet();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    /**
     * Test happy path for doGet method.
     * Verifies that response content type is set and output contains expected class name.
     */
    @Test
    void testDoGetHappyPath() throws ServletException, IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        verify(response).setContentType("text/plain");
        pw.flush();
        String output = sw.toString();

        assertTrue(output.contains("Response from " + MyGetServlet.class.getName()),
                "Output should contain servlet class name");
        assertTrue(output.contains(" at "), "Output should contain timestamp separator");
    }

    /**
     * Test doGet when response.getWriter throws IOException.
     * Verifies that IOException is propagated.
     */
    @Test
    void testDoGetWriterThrowsIOException() throws ServletException, IOException {
        when(response.getWriter()).thenThrow(new IOException("Writer error"));

        IOException thrown = org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
            servlet.doGet(request, response);
        });

        assertTrue(thrown.getMessage().contains("Writer error"), "Exception message should match");
    }

    /**
     * Test doGet when response.setContentType throws RuntimeException.
     * Verifies that RuntimeException is propagated.
     */
    @Test
    void testDoGetSetContentTypeThrowsRuntimeException() throws ServletException, IOException {
        doThrow(new RuntimeException("Content type error")).when(response).setContentType(anyString());

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            servlet.doGet(request, response);
        });

        assertTrue(thrown.getMessage().contains("Content type error"), "Exception message should match");
    }
}