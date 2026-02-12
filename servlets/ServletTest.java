import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class ServletTest {

    // Existing tests remain unchanged...

    @Test
    public void testDoGet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);

        Servlet servlet = new Servlet();
        servlet.doGet(request, response);

        verify(response).getWriter();
        verify(writer).println("Hello World");
    }

    // Newly added tests for delta changes

    @Test
    public void testModified_doPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("name")).thenReturn("TestUser");

        Servlet servlet = new Servlet();
        servlet.doPost(request, response);

        verify(response).getWriter();
        verify(writer).println("Hello, TestUser");
    }

    @Test
    public void testNew_handleCustomLogic() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);

        Servlet servlet = new Servlet();
        servlet.handleCustomLogic(request, response);

        verify(response).getWriter();
        verify(writer).println("Custom Logic Executed");
    }
}