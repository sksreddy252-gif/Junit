package com.example.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PathBasedServlet}.
 */
public class PathBasedServletTest {

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    private PathBasedServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new PathBasedServlet();
    }

    /**
     * Test that doGet correctly sets the content type and writes the expected JSON payload.
     */
    @Test
    void testDoGetWritesExpectedJson() throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);

        verify(response).setContentType("application/json");

        printWriter.flush();

        assertEquals(EXPECTED_JSON_PAYLOAD, stringWriter.toString());
    }

    /**
     * Test that IOException thrown from getWriter is propagated.
     */
    @Test
    void testDoGetThrowsIOException() throws IOException {
        when(response.getWriter()).thenThrow(new IOException("Writer failure"));

        IOException thrown = org.junit.jupiter.api.Assertions.assertThrows(
            IOException.class,
            () -> servlet.doGet(request, response)
        );

        assertEquals("Writer failure", thrown.getMessage());
    }

    private static final String EXPECTED_JSON_PAYLOAD =
            "{"message": \"Hello from path-based servlet\"}";
}
