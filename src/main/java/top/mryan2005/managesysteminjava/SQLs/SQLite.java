package top.mryan2005.managesysteminjava.SQLs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLite {
    public Connection con;

    public String getPath() {
        String path = String.valueOf(this.getClass().getClassLoader().getResource(""));
        if(path.matches("file:(.*)/classes/")) {
            path = path.substring(6, path.length() - 15) + "src/main/resources/top/mryan2005/managesysteminjava/";
        } else if(path.matches("file:(.*)/target/(.*)")) {
            path =  "/home/runner/work/ManageSystemInJava/ManageSystemInJava/src/test/resources/top/mryan2005/managesysteminjava/";
        } else if(path == "null") {
            path = "";
        }
        return path;
    }

    public SQLite(String databaseFileName) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        System.out.println(getPath()+databaseFileName);
        con = java.sql.DriverManager.getConnection("jdbc:sqlite:"+getPath()+databaseFileName);
    }

    public Connection getSQLer() {
        return con;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("关闭数据库时发生错误！");
            System.out.println(e);
        }
    }

    public ResultSet runSQL(String sql) {
        try {
            java.sql.Statement stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("执行SQL语句时发生错误！");
            System.out.println(e);
        }
        return null;
    }

    public static void main(String args[]) {
        try {
            SQLite sql = new SQLite("test.db");
            System.out.println("连接成功！");
            ResultSet res = sql.runSQL("SELECT * FROM test");
            while (res.next()) {
                System.out.println(res.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("连接数据库时发生错误！");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("声明类时发生错误！");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
