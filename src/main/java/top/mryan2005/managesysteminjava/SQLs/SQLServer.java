package top.mryan2005.managesysteminjava.SQLs;

import java.sql.*;

public class SQLServer {
    private String username, password, databaseName, ip, port, connectionUrl;
    private Connection con;

    public SQLServer(String ip, String port, String username, String password, String databaseName) throws SQLException, ClassNotFoundException {
        Class.forName( "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.ip = ip;
        this.port = port;
        connectionUrl = "jdbc:sqlserver://"+ip+":"+port+";databaseName="+databaseName+";user="+username+";password="+password+";encrypt=false;";
        con = DriverManager.getConnection(connectionUrl);
    }

    public Connection getSQLer() {
        return con;
    }

    public void close() throws SQLException {
        con.close();
    }

    public static void main(String args[]) throws SQLException {
        try {
            SQLServer sql = new SQLServer("127.0.0.1", "1433", "sa", "123456", "abc");
            System.out.println("连接成功！");
        } catch (SQLException e) {
            System.out.println("连接数据库时发生错误！");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
