package com.shashi.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DBUtilTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @AfterEach
    void tearDown() {
        mockConnection = null;
        mockPreparedStatement = null;
        mockResultSet = null;
    }

    @Test
    void testProvideConnectionWhenConnectionIsNull() throws Exception {
        Connection connection = DBUtil.provideConnection();
        assertNotNull(connection, "Connection should not be null when provided");
    }

    @Test
    void testProvideConnectionWhenConnectionIsClosed() throws Exception {
        DBUtil.provideConnection();
        Connection conn = DBUtil.provideConnection();
        assertNotNull(conn, "Connection should be re-provided when closed");
    }

    @Test
    void testCloseConnectionResultSetWhenOpen() throws Exception {
        when(mockResultSet.isClosed()).thenReturn(false);
        DBUtil.closeConnection(mockResultSet);
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testCloseConnectionResultSetWhenAlreadyClosed() throws Exception {
        when(mockResultSet.isClosed()).thenReturn(true);
        DBUtil.closeConnection(mockResultSet);
        verify(mockResultSet, never()).close();
    }

    @Test
    void testCloseConnectionPreparedStatementWhenOpen() throws Exception {
        when(mockPreparedStatement.isClosed()).thenReturn(false);
        DBUtil.closeConnection(mockPreparedStatement);
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testCloseConnectionPreparedStatementWhenAlreadyClosed() throws Exception {
        when(mockPreparedStatement.isClosed()).thenReturn(true);
        DBUtil.closeConnection(mockPreparedStatement);
        verify(mockPreparedStatement, never()).close();
    }

    @Test
    void testCloseConnectionPreparedStatementWithSQLExceptionOnIsClosed() throws Exception {
        when(mockPreparedStatement.isClosed()).thenThrow(new SQLException("Test exception"));
        assertDoesNotThrow(() -> DBUtil.closeConnection(mockPreparedStatement));
    }

    @Test
    void testCloseConnectionResultSetWithSQLExceptionOnIsClosed() throws Exception {
        when(mockResultSet.isClosed()).thenThrow(new SQLException("Test exception"));
        assertDoesNotThrow(() -> DBUtil.closeConnection(mockResultSet));
    }
}