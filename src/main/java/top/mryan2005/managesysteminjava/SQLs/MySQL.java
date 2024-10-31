package top.mryan2005.managesysteminjava.SQLs;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class MySQL {
    private String ip, username, password, port, databaseName;
    private Connection con;
    public MySQL(String ip, String port, String username, String password, String databaseName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.ip = ip;
        this.port = port;
        String connectionUrl = "jdbc:mysql://"+ip+":"+port+"/"+databaseName+"?user="+username+"&password="+password+"&useUnicode=true&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&useSSL=false";
        con = getConnection(connectionUrl);
    }

    public MySQL() {
    }

    public MySQL(String ip, String port, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.username = username;
        this.password = password;
        this.databaseName = null;
        this.ip = ip;
        this.port = port;
        String connectionUrl = "jdbc:mysql://"+ip+":"+port+"?user="+username+"&password="+password+"&useUnicode=true&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&useSSL=false";
        con = getConnection(connectionUrl);
    }

    public Connection getSQLer() {
        return con;
    }

    public boolean testConnection(String ip, String port, String username, String password, String databaseName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://"+ip+":"+port+"?user="+username+"&password="+password+"&useUnicode=true&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&useSSL=false";
            getConnection(connectionUrl);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        try {
            MySQL sql = new MySQL("100.67.158.142", "336", "root", "123456");
        } catch (SQLException e) {
            System.out.println("连接数据库时发生错误！");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(String tableName, String[] columns, String[] values) {
        return false;
    }

    public void FindSomeThings(String query) throws SQLException {
    }
}