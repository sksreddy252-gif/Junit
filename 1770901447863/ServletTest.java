package com.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServletTest {

    // Existing tests remain unchanged above...

    // --- Delta-based additions start here ---

    @Test
    void testModified_doGet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);

        Servlet servlet = new Servlet();
        servlet.doGet(request, response);

        verify(response, times(1)).getWriter();
        verify(writer, times(1)).write(anyString());
    }

    @Test
    void testNew_handleCustomLogic() {
        Servlet servlet = new Servlet();
        String expectedValue = "CustomLogicExecuted";
        String actualValue = servlet.handleCustomLogic();
        assertEquals(expectedValue, actualValue);
    }
}