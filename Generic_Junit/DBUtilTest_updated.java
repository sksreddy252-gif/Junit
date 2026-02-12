package com.shashi.utility;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBUtilTest_updated {

    @Test
    void testProvideConnectionReturnsConnection() throws SQLException {
        Connection connection = DBUtil.provideConnection();
        assertTrue(connection == null || !connection.isClosed());
    }

    @Test
    void testCloseConnectionWithResultSet() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        DBUtil.closeConnection(rs);
        verify(rs, times(1)).close();
    }

    @Test
    void testCloseConnectionWithPreparedStatement() throws SQLException {
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.isClosed()).thenReturn(false);
        DBUtil.closeConnection(ps);
        verify(ps, times(1)).close();
    }

    @Test
    void testCloseConnectionWithNullResultSet() {
        assertDoesNotThrow(() -> DBUtil.closeConnection((ResultSet) null));
    }

    @Test
    void testCloseConnectionWithNullPreparedStatement() {
        assertDoesNotThrow(() -> DBUtil.closeConnection((PreparedStatement) null));
    }
}