package main;

import org.junit.Test;
import top.mryan2005.managesysteminjava.SQLs.*;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SQLLinkerTest {
    @Test
    public void TestConnectSQL() throws SQLException, ClassNotFoundException {
        SQLLinker mysql = new SQLLinker("MySQL", "127.0.0.1", "3800", "root", "123456", "testDatabase");
        assertTrue(mysql.testConnection());
    }

    @Test
    public void TestCloseConnect() throws SQLException, ClassNotFoundException {
        SQLLinker mysql = new SQLLinker("MySQL", "127.0.0.1", "3800", "root", "123456", "testDatabase");
        assertTrue(mysql.closeConnection());
    }

    @Test
    public void TestSQLite() throws SQLException, ClassNotFoundException {
        SQLLinker sqlite = new SQLLinker("SQLite", "test");
        assertTrue(sqlite.testConnection());
    }

    @Test
    public void TestCloseSQLite() throws SQLException, ClassNotFoundException {
        SQLLinker sqlite = new SQLLinker("SQLite", "test");
        assertTrue(sqlite.closeConnection());
    }

    @Test
    public void TestReadSQLite() throws SQLException, ClassNotFoundException {
        SQLLinker sqlite = new SQLLinker("SQLite", "test.db");
        ResultSet res = sqlite.runSQL("SELECT * FROM test1");
        String[] result = new String[2];
        while (res.next()) {
            result[0] = res.getString("name");
        }
        assertSame("Mryan2005", result[0]);
    }
}
