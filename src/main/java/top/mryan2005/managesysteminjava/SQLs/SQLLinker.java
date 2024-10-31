package top.mryan2005.managesysteminjava.SQLs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class SQLLinker {
    private Connection con;
    private String type;
    public SQLLinker(String type, String ip, String port, String username, String password, String databaseName) throws SQLException {
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
            try {
                MySQL sql = new MySQL(ip, port, username, password, databaseName);
                System.out.println("连接成功！");
                con = sql.getSQLer();
            } catch (SQLException e) {
                System.out.println("连接数据库时发生错误！");
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
        Statement stmt = con.createStatement();
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("执行SQL语句时发生错误！");
            System.out.println(e);
        }
        return null;
    }

    public ResultSet executeQuery(String WhatYouNeed, String ViewOrTable) throws SQLException, ClassNotFoundException {
        Statement stmt = con.createStatement();
        try {
            if(type.matches("SQL Server")) {
                return stmt.executeQuery("SELECT "+WhatYouNeed+" FROM "+ViewOrTable);
            } else if(type.matches("MySQL")) {
                return stmt.executeQuery("SELECT "+WhatYouNeed+" FROM "+ViewOrTable);
            }
        } catch (SQLException e) {
            System.out.println("执行SQL语句时发生错误！");
            System.out.println(e);
        }
        return null;
    }

    public ResultSet runSQL(String sql) {
        try {
            Statement stmt = con.createStatement();
            if (stmt.execute(sql)) {
                return stmt.getResultSet();
            }
        } catch (SQLException e) {
            System.out.println("执行SQL语句时发生错误！");
            System.out.println(e);
        }
        return null;
    }

    public void addEntry(String sql) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("执行SQL语句时发生错误！");
            System.out.println(e);
        }
    }

    public void addEntry(String Table, Map items) {
        if(type.matches("SQL Server")) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO "+Table+" VALUES ()");
            } catch (SQLException e) {
                System.out.println("执行SQL语句时发生错误！");
                System.out.println(e);
            }
        } else if(type.matches("MySQL")) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO "+Table+" VALUES ()");
            } catch (SQLException e) {
                System.out.println("执行SQL语句时发生错误！");
                System.out.println(e);
            }
        }
    }

    public void close() throws SQLException {
        con.close();
    }

    public static void main(String args[]) throws SQLException {
        try {
            SQLLinker sql = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "wuzhouDict");
            System.out.println("连接成功！");
            ResultSet res = sql.executeQuery("SELECT * FROM entry.aPart");
            while(res.next()) {
                System.out.println(res.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("连接数据库或者操作数据库时发生错误！");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean testConnection() {
        if(con == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean closeConnection() {
        try {
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
