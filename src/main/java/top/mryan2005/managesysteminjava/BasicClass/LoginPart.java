package top.mryan2005.managesysteminjava.BasicClass;

import org.apache.commons.codec.digest.DigestUtils;
import top.mryan2005.managesysteminjava.SQLs.SQLLinker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class LoginPart {

    private SQLLinker sql;

    private HashMap<String, User> users = new HashMap<String, User>();

    public String getMaxId() {
        ResultSet res = sql.runSQL("SELECT MAX(id) 'maxid' FROM Users.[user]");
        if (res == null) {
            return "-1";
        }
        try {
            res.next();
            return valueOf(res.getInt("maxid") + 1);
        } catch (SQLException e) {
            return "-1";
        }
    }

    public void readUsersTable() {
        users.clear();
        ResultSet res = sql.runSQL("SELECT * FROM Users.[user]");
        if (res == null) {
            return;
        }
        try {
            while (res.next()) {
                User user = new User(res.getString("username"), res.getString("password"), res.getString("level"), res.getString("role"), res.getString("name"), res.getString("avator"));
                users.put(res.getString("username"), user);
            }
        } catch (SQLException e) {
            return;
        }
    }

    public LoginPart(SQLLinker sql1) {
        sql = sql1;
    }

    public void register(String username, String password, String Sex, String UName) {
        readUsersTable();
        if (username.matches("") || password.matches("") || UName.matches("")) {
            return;
        }
        if(users.containsKey(username)) {
            return;
        }
        password = runMd5(password);
        sql.runSQL("INSERT INTO Users.[user] (id, username, password, Sex, name) VALUES ('" + getMaxId() +"', '" + username + "', '" + password + "', '" + Sex + "', '" + UName + "')");
    }

    public boolean login(String username, String password) {
        readUsersTable();
        password = runMd5(password);
        if (users.containsKey(username)) {
            return users.get(username).password.matches(password);
        }
        return false;
    }

    private String runMd5(String password) {
        for (int i = 0; i < 5; i++) {
            password = DigestUtils.md5Hex(password);
        }
        return password;
    }

    public void setAvatar(String username, String avatar) {
        sql.runSQL("UPDATE Users.[user] SET avatar = '" + avatar + "' WHERE username = '" + username + "'");
    }

    public void setUName(String username, String UName) {
        sql.runSQL("UPDATE Users.[user] SET name = '" + UName + "' WHERE username = '" + username + "'");
    }

    public void setPassword(String username, String password) {
        password = runMd5(password);
        sql.runSQL("UPDATE Users.[user] SET password = '" + password + "' WHERE username = '" + username + "'");
    }

    public void setSex(String username, String sex) {
        sql.runSQL("UPDATE Users.[user] SET sex = '" + sex + "' WHERE username = '" + username + "'");
    }

    public void setLevel(String username, int level) {
        sql.runSQL("UPDATE Users.[user] SET level = '" + valueOf(level) + "' WHERE username = '" + username + "'");
    }

    public void setRole(String username, int role) {
        sql.runSQL("UPDATE Users.[user] SET role = '" + valueOf(role) + "' WHERE username = '" + username + "'");
    }

    public void removeUser(String username) {
        sql.runSQL("DELETE FROM Users.[user] WHERE username = '" + username + "'");
        readUsersTable();
    }

    public static void main(String[] args) {
        try {
            SQLLinker sql = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "wuzhouDict");
            System.out.println("连接成功！");
            LoginPart login = new LoginPart(sql);
            login.register("test", "123456", "M", "test");
            if(login.login("test", "123456")) {
                System.out.println("登录成功！");
            } else {
                System.out.println("登录失败！");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
