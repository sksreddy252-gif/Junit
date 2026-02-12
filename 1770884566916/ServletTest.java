// Existing imports and package remain unchanged

public class ServletTest {

    // ... all your existing test methods remain here unchanged

    @Test
    void testModified_doPost() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Servlet servlet = new Servlet();

        // TODO: Configure request mock for modified logic
        when(request.getParameter("paramName")).thenReturn("value");

        // Act
        servlet.doPost(request, response);

        // Assert
        // TODO: Add assertions based on modified logic in doPost
        verify(response, atLeastOnce()).setContentType(anyString());
    }

    @Test
    void testNew_handleCustomAction() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Servlet servlet = new Servlet();

        // TODO: Configure request mock for new method
        when(request.getParameter("action")).thenReturn("custom");

        // Act
        servlet.handleCustomAction(request, response);

        // Assert
        // TODO: Add assertions for expected behavior of handleCustomAction
        verify(response, atLeastOnce()).getWriter();
    }
}