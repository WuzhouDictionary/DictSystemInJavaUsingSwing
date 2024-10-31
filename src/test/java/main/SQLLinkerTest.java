package main;

import org.junit.Test;
import top.mryan2005.managesysteminjava.SQLs.*;

import java.sql.SQLException;

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
}
