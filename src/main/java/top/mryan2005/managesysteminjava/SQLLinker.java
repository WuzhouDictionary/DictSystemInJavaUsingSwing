package top.mryan2005.managesysteminjava;

import top.mryan2005.managesysteminjava.SQLs.MySQL;
import top.mryan2005.managesysteminjava.SQLs.SQLServer;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLLinker {
    private Connection con;
    private String type;
    public SQLLinker(String type, String ip, String port, String username, String password, String databaseName) {
        this.type = type;
        if("SQL Server".matches(type)) {
            try {
                SQLServer sql = new SQLServer(ip, port, username, password, databaseName);
                System.out.println("连接成功！");
                con = sql.getSQLer();
            } catch (SQLException e) {
                System.out.println("连接数据库时发生错误！");
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if("MySQL".matches(type)) {
            MySQL sql = new MySQL(ip, port, username, password, databaseName);
        }
    }

    public void addEntry() {
        if(this.type == "SQL Server") {
            try {
                ;
            } finally {

            }
        }
    }
}
