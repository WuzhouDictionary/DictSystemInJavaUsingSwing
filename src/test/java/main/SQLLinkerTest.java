package main;

import org.junit.Test;
import top.mryan2005.managesysteminjava.SQLs.*;

import java.sql.Array;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        SQLLinker sqlite = new SQLLinker("test");
        assertTrue(sqlite.testConnection());
    }

    @Test
    public void TestCloseSQLite() throws SQLException, ClassNotFoundException {
        SQLLinker sqlite = new SQLLinker("test");
        assertTrue(sqlite.closeConnection());
    }

    @Test
    public void TestReadSQLite() throws SQLException, ClassNotFoundException {
        SQLLinker sqlite = new SQLLinker("test");
        assertArrayEquals(new String[]{"mryan2005"}, new Array[]{sqlite.runSQL("SELECT * FROM test").getArray(1)});
    }
}
