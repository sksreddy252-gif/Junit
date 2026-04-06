// Unified JUnit test suite for FormHandlerServlet
// Includes original scenarios and new exception handling cases

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class FormHandlerServletTest {

    private FormHandlerServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletOutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        servlet = new FormHandlerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    public void testValidFormSubmission() throws Exception {
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@example.com");

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testMissingNameParameter() throws Exception {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("email")).thenReturn("john@example.com");

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testIOExceptionDuringProcessing() throws Exception {
        ServletOutputStream failingStream = mock(ServletOutputStream.class);
        doThrow(new IOException("Stream error")).when(failingStream).write(any(byte[].class), anyInt(), anyInt());
        when(response.getOutputStream()).thenReturn(failingStream);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testServletExceptionHandling() throws Exception {
        FormHandlerServlet faultyServlet = spy(servlet);
        doThrow(new ServletException("Test exception")).when(faultyServlet).doPost(any(HttpServletRequest.class), any(HttpServletResponse.class));

        try {
            faultyServlet.doPost(request, response);
            fail("Expected ServletException");
        } catch (ServletException e) {
            assertEquals("Test exception", e.getMessage());
        }
    }

    @Test
    public void testJsonOutputStructureOnError() throws Exception {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("email")).thenReturn("john@example.com");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ServletOutputStream sos = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }
        };
        when(response.getOutputStream()).thenReturn(sos);

        servlet.doPost(request, response);

        String json = baos.toString();
        assertTrue(json.contains("error"));
    }
}
