package top.mryan2005.managesysteminjava.getData;

import top.mryan2005.managesysteminjava.SQLs.SQLLinker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class getDataFromDatabase {
    public ResultSet getAllData() throws SQLException {
        SQLLinker sqlLinker = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "wuzhouDict");
        return sqlLinker.runSQL("SELECT * FROM entry.viewAll");
    }

    public ResultSet getApart() throws SQLException {
        SQLLinker sqlLinker = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "wuzhouDict");
        return sqlLinker.runSQL("SELECT * FROM entry.aPart");
    }

    public static void main(String[] args) {
        try {
            getDataFromDatabase getDataFromDatabase = new getDataFromDatabase();
            ResultSet res = getDataFromDatabase.getAllData();
            while (res.next()) {
                System.out.println(res.getString("simplified_Chinese_character"));
            }
            res = getDataFromDatabase.getApart();
            while (res.next()) {
                System.out.println(res.getString("simplified_Chinese_character"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
