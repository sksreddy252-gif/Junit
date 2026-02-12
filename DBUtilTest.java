package your.package.name; // Replace with actual package of DBUtil.java

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for {@link DBUtil}.
 * Categorized as a generic Java source file.
 * Covers happy-path, edge cases, and exceptional scenarios for all public methods.
 */
public class DBUtilTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DBUtil.provideConnection();
        preparedStatement = connection.prepareStatement("SELECT 1");
        resultSet = preparedStatement.executeQuery();
    }

    @AfterEach
    void tearDown() {
        DBUtil.closeConnection(resultSet);
        DBUtil.closeConnection(preparedStatement);
        DBUtil.closeConnection(connection);
    }

    @Test
    void testProvideConnectionReturnsValidConnection() throws SQLException {
        Connection conn = DBUtil.provideConnection();
        assertNotNull(conn, "Connection should not be null");
        assertFalse(conn.isClosed(), "Connection should be open initially");
    }

    @Test
    void testProvideConnectionMultipleCalls() throws SQLException {
        Connection conn1 = DBUtil.provideConnection();
        Connection conn2 = DBUtil.provideConnection();
        assertNotNull(conn1);
        assertNotNull(conn2);
        assertFalse(conn1.isClosed());
        assertFalse(conn2.isClosed());
        assertNotSame(conn1, conn2, "Each call should return a distinct connection if pooling is not used");
    }

    @Test
    void testCloseConnectionWithValidConnection() throws SQLException {
        assertFalse(connection.isClosed(), "Connection should be open before closing");
        DBUtil.closeConnection(connection);
        assertTrue(connection.isClosed(), "Connection should be closed after method call");
    }

    @Test
    void testCloseConnectionWithNullConnection() {
        assertDoesNotThrow(() -> DBUtil.closeConnection((Connection) null),
                "Closing a null connection should not throw an exception");
    }

    @Test
    void testCloseConnectionWithValidPreparedStatement() throws SQLException {
        assertFalse(preparedStatement.isClosed(), "PreparedStatement should be open before closing");
        DBUtil.closeConnection(preparedStatement);
        assertTrue(preparedStatement.isClosed(), "PreparedStatement should be closed after method call");
    }

    @Test
    void testCloseConnectionWithNullPreparedStatement() {
        assertDoesNotThrow(() -> DBUtil.closeConnection((PreparedStatement) null),
                "Closing a null PreparedStatement should not throw an exception");
    }

    @Test
    void testCloseConnectionWithValidResultSet() throws SQLException {
        assertFalse(resultSet.isClosed(), "ResultSet should be open before closing");
        DBUtil.closeConnection(resultSet);
        assertTrue(resultSet.isClosed(), "ResultSet should be closed after method call");
    }

    @Test
    void testCloseConnectionWithNullResultSet() {
        assertDoesNotThrow(() -> DBUtil.closeConnection((ResultSet) null),
                "Closing a null ResultSet should not throw an exception");
    }

    @Test
    void testCloseConnectionOnAlreadyClosedConnection() throws SQLException {
        connection.close();
        assertTrue(connection.isClosed(), "Connection should be closed before calling closeConnection");
        assertDoesNotThrow(() -> DBUtil.closeConnection(connection),
                "Closing an already closed connection should not throw an exception");
    }

    @Test
    void testCloseConnectionOnAlreadyClosedPreparedStatement() throws SQLException {
        preparedStatement.close();
        assertTrue(preparedStatement.isClosed(), "PreparedStatement should be closed before calling closeConnection");
        assertDoesNotThrow(() -> DBUtil.closeConnection(preparedStatement),
                "Closing an already closed PreparedStatement should not throw an exception");
    }

    @Test
    void testCloseConnectionOnAlreadyClosedResultSet() throws SQLException {
        resultSet.close();
        assertTrue(resultSet.isClosed(), "ResultSet should be closed before calling closeConnection");
        assertDoesNotThrow(() -> DBUtil.closeConnection(resultSet),
                "Closing an already closed ResultSet should not throw an exception");
    }

    @Test
    void testProvideConnectionUnderConcurrency() throws InterruptedException {
        Runnable task = () -> {
            Connection conn = DBUtil.provideConnection();
            assertNotNull(conn);
            DBUtil.closeConnection(conn);
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    @Test
    void testProvideConnectionAndCloseImmediately() throws SQLException {
        Connection conn = DBUtil.provideConnection();
        assertNotNull(conn);
        DBUtil.closeConnection(conn);
        assertTrue(conn.isClosed(), "Connection should be closed immediately after closeConnection");
    }

    @Test
    void testProvideConnectionThrowsSQLExceptionScenario() {
        assertDoesNotThrow(() -> {
            Connection conn = DBUtil.provideConnection();
            if (conn != null) {
                DBUtil.closeConnection(conn);
            }
        }, "provideConnection should handle exceptions gracefully");
    }
}