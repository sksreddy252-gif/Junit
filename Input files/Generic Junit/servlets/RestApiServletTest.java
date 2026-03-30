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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestApiServletTest {

    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private SlingHttpServletResponse response;
    @Mock
    private PrintWriter printWriter;

    private RestApiServlet servlet;

    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new RestApiServlet();
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void testDoGetSetsStatusAndWritesJson() throws ServletException, IOException {
        servlet.doGet(request, response);
        printWriter.flush();
        verify(response).setContentType("application/json");
        verify(response).setStatus(SlingHttpServletResponse.SC_OK);
        assertEquals(EXPECTED_JSON_GET, stringWriter.toString());
    }

    @Test
    void testDoPostSetsStatusAndWritesJson() throws ServletException, IOException {
        servlet.doPost(request, response);
        printWriter.flush();
        verify(response).setContentType("application/json");
        verify(response).setStatus(SlingHttpServletResponse.SC_CREATED);
        assertEquals(EXPECTED_JSON_POST, stringWriter.toString());
    }

    @Test
    void testDoPutSetsStatusAndWritesJson() throws ServletException, IOException {
        servlet.doPut(request, response);
        printWriter.flush();
        verify(response).setContentType("application/json");
        verify(response).setStatus(SlingHttpServletResponse.SC_OK);
        assertEquals(EXPECTED_JSON_PUT, stringWriter.toString());
    }

    @Test
    void testDoDeleteSetsStatusAndNoBody() throws ServletException, IOException {
        servlet.doDelete(request, response);
        verify(response).setContentType("application/json");
        verify(response).setStatus(SlingHttpServletResponse.SC_NO_CONTENT);
        assertEquals("", stringWriter.toString());
    }

    @Test
    void testDoGetIOExceptionThrown() throws IOException {
        when(response.getWriter()).thenThrow(new IOException("Test IO Exception"));
        assertThrows(IOException.class, () -> servlet.doGet(request, response));
    }

    @Test
    void testDoPostIOExceptionThrown() throws IOException {
        when(response.getWriter()).thenThrow(new IOException("Test IO Exception"));
        assertThrows(IOException.class, () -> servlet.doPost(request, response));
    }

    @Test
    void testDoPutIOExceptionThrown() throws IOException {
        when(response.getWriter()).thenThrow(new IOException("Test IO Exception"));
        assertThrows(IOException.class, () -> servlet.doPut(request, response));
    }

    @Test
    void testDoDeleteIOExceptionThrown() throws IOException {
        when(response.getWriter()).thenThrow(new IOException("Test IO Exception"));
        assertThrows(IOException.class, () -> servlet.doDelete(request, response));
    }

    private static final String EXPECTED_JSON_GET = "{\"method\": \"GET\"}";
    private static final String EXPECTED_JSON_POST = "{\"method\": \"POST\", \"status\": \"created\"}";
    private static final String EXPECTED_JSON_PUT = "{\"method\": \"PUT\", \"status\": \"updated\"}";
}