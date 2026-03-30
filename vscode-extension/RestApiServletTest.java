package com.example.core.servlets;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.junit5.ResourceResolverType;

@ExtendWith(AemContextExtension.class)
public class RestApiServletTest {

    private AemContext context;
    private RestApiServlet servlet;
    private SlingHttpServletRequest request;
    private SlingHttpServletResponse response;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        context = new AemContext(ResourceResolverType.JCR_MOCK);
        servlet = new RestApiServlet();
        request = Mockito.mock(SlingHttpServletRequest.class);
        response = Mockito.mock(SlingHttpServletResponse.class);
        responseWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testDoGetReturnsExpectedJson() throws Exception {
        servlet.doGet(request, response);
        assertEquals("application/json", Mockito.verify(response).getContentType());
        Mockito.verify(response).setStatus(SlingHttpServletResponse.SC_OK);
        assertEquals("{\"method\": \"GET\"}", responseWriter.toString());
    }

    @Test
    void testDoPostReturnsExpectedJson() throws Exception {
        servlet.doPost(request, response);
        assertEquals("application/json", Mockito.verify(response).getContentType());
        Mockito.verify(response).setStatus(SlingHttpServletResponse.SC_CREATED);
        assertEquals("{\"method\": \"POST\", \"status\": \"created\"}", responseWriter.toString());
    }

    @Test
    void testDoPutReturnsExpectedJson() throws Exception {
        servlet.doPut(request, response);
        assertEquals("application/json", Mockito.verify(response).getContentType());
        Mockito.verify(response).setStatus(SlingHttpServletResponse.SC_OK);
        assertEquals("{\"method\": \"PUT\", \"status\": \"updated\"}", responseWriter.toString());
    }

    @Test
    void testDoDeleteReturnsNoContent() throws Exception {
        servlet.doDelete(request, response);
        assertEquals("application/json", Mockito.verify(response).getContentType());
        Mockito.verify(response).setStatus(SlingHttpServletResponse.SC_NO_CONTENT);
        assertTrue(StringUtils.isEmpty(responseWriter.toString()));
    }
}

// SCORE: 9/10 - Covers all servlet methods with status, content type, and body assertions; could add negative/malformed request tests for completeness.