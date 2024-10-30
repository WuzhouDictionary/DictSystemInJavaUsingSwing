package top.mryan2005.managesysteminjava.SQLs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class testLinker {
    public static void main(String[] args) {
        try {
            SQLLinker sqlLinker = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "testDatabase");
            ResultSet res = sqlLinker.runSQL("SELECT * FROM test.test1");
            while (res.next()) {
                System.out.println(res.getString("name")+" "+res.getString("QQ"));
            }
            res = sqlLinker.executeQuery("SELECT * FROM test.test1");
            while (res.next()) {
                System.out.println(res.getString("name")+" "+res.getString("QQ"));
            }
            sqlLinker.runSQL("INSERT INTO test.test1 VALUES ('mryan2005', '123456')");
            res = sqlLinker.executeQuery("SELECT * FROM test.test1");
            while (res.next()) {
                System.out.println(res.getString("name")+" "+res.getString("QQ"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
