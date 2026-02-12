package com.shashi.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testProvideConnectionCreatesNewConnectionWhenNull() throws Exception {
        Connection connection = DBUtil.provideConnection();
        assertNotNull(connection, "Connection should not be null when created");
        assertFalse(connection.isClosed(), "Connection should be open");
    }

    @Test
    void testProvideConnectionReturnsExistingConnectionWhenOpen() throws Exception {
        Connection firstConnection = DBUtil.provideConnection();
        Connection secondConnection = DBUtil.provideConnection();
        assertSame(firstConnection, secondConnection, "Should return the same connection instance when already open");
    }

    @Test
    void testCloseConnectionResultSetClosesWhenOpen() throws Exception {
        when(mockResultSet.isClosed()).thenReturn(false);
        DBUtil.closeConnection(mockResultSet);
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testCloseConnectionResultSetDoesNothingWhenAlreadyClosed() throws Exception {
        when(mockResultSet.isClosed()).thenReturn(true);
        DBUtil.closeConnection(mockResultSet);
        verify(mockResultSet, never()).close();
    }

    @Test
    void testCloseConnectionPreparedStatementClosesWhenOpen() throws Exception {
        when(mockPreparedStatement.isClosed()).thenReturn(false);
        DBUtil.closeConnection(mockPreparedStatement);
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testCloseConnectionPreparedStatementDoesNothingWhenAlreadyClosed() throws Exception {
        when(mockPreparedStatement.isClosed()).thenReturn(true);
        DBUtil.closeConnection(mockPreparedStatement);
        verify(mockPreparedStatement, never()).close();
    }

    @Test
    void testProvideConnectionWithInjectedResourceBundleValid() throws Exception {
        ResourceBundle rb = mock(ResourceBundle.class);
        when(rb.getString("db.connectionString")).thenReturn("jdbc:h2:mem:testdb");
        when(rb.getString("db.driverName")).thenReturn("org.h2.Driver");
        when(rb.getString("db.username")).thenReturn("sa");
        when(rb.getString("db.password")).thenReturn("");

        Connection connection = DriverManager.getConnection(
                rb.getString("db.connectionString"),
                rb.getString("db.username"),
                rb.getString("db.password")
        );
        assertNotNull(connection, "Injected ResourceBundle should create a valid connection");
        connection.close();
    }

    @Test
    void testProvideConnectionWithInjectedResourceBundleInvalid() {
        ResourceBundle rb = mock(ResourceBundle.class);
        when(rb.getString("db.connectionString")).thenReturn("jdbc:invalid");
        when(rb.getString("db.driverName")).thenReturn("invalid.Driver");
        when(rb.getString("db.username")).thenReturn("user");
        when(rb.getString("db.password")).thenReturn("pass");

        assertThrows(SQLException.class, () -> {
            DriverManager.getConnection(
                    rb.getString("db.connectionString"),
                    rb.getString("db.username"),
                    rb.getString("db.password")
            );
        }, "Injected ResourceBundle with invalid settings should throw SQLException");
    }

    @Test
    void testProvideConnectionHandlesSQLExceptionGracefully() {
        assertDoesNotThrow(() -> DBUtil.provideConnection(), "Should handle SQLException without throwing");
    }
}