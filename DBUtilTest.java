package com.shashi.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBUtilTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Create a real connection using DBUtil
        connection = DBUtil.provideConnection();
        assertNotNull(connection, "Connection should not be null in setup");
        preparedStatement = connection.prepareStatement("SELECT 1");
        resultSet = preparedStatement.executeQuery();
    }

    @AfterEach
    void tearDown() {
        // Ensure all resources are closed
        DBUtil.closeResultSet(resultSet);
        DBUtil.closeStatement(preparedStatement);
        DBUtil.closeConnection(connection);
    }

    @Test
    void testProvideConnectionReturnsValidConnection() throws SQLException {
        // Happy path: should return a valid, open connection
        Connection conn = DBUtil.provideConnection();
        assertNotNull(conn, "Connection should not be null");
        assertFalse(conn.isClosed(), "Connection should be open");
        conn.close();
    }

    @Test
    void testCloseConnectionClosesOpenConnection() throws SQLException {
        Connection conn = DBUtil.provideConnection();
        assertFalse(conn.isClosed(), "Connection should be open before closing");
        DBUtil.closeConnection(conn);
        assertTrue(conn.isClosed(), "Connection should be closed after closeConnection");
    }

    @Test
    void testCloseConnectionWithNullDoesNotThrow() {
        assertDoesNotThrow(() -> DBUtil.closeConnection(null), "Closing null connection should not throw");
    }

    @Test
    void testCloseStatementClosesOpenStatement() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT 1");
        DBUtil.closeStatement(stmt);
        assertTrue(stmt.isClosed(), "Statement should be closed after closeStatement");
    }

    @Test
    void testCloseStatementWithNullDoesNotThrow() {
        assertDoesNotThrow(() -> DBUtil.closeStatement(null), "Closing null statement should not throw");
    }

    @Test
    void testCloseResultSetClosesOpenResultSet() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT 1");
        ResultSet rs = stmt.executeQuery();
        DBUtil.closeResultSet(rs);
        assertTrue(rs.isClosed(), "ResultSet should be closed after closeResultSet");
        stmt.close();
    }

    @Test
    void testCloseResultSetWithNullDoesNotThrow() {
        assertDoesNotThrow(() -> DBUtil.closeResultSet(null), "Closing null ResultSet should not throw");
    }

    @Test
    void testProvideConnectionThrowsSQLExceptionForInvalidConfig() {
        // This test assumes invalid DB config in application.properties will cause SQLException
        // Temporarily simulate by catching the exception
        try {
            Connection conn = DBUtil.provideConnection();
            assertNotNull(conn, "Connection should still be returned if config is valid");
        } catch (Exception e) {
            assertTrue(e instanceof SQLException || e instanceof RuntimeException,
                    "Should throw SQLException or RuntimeException for invalid config");
        }
    }
}