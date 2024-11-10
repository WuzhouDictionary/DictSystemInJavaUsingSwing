package top.mryan2005.managesysteminjava.BasicClass;

import top.mryan2005.managesysteminjava.SQLs.SQLLinker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public String username;    // 用户名
    public String password;    // 密码
    public int level;   // 等级
    public String role;    // 角色
    public String UName;    // 昵称
    public String avatar;  // 头像
    public String sex;

    public User(String username, String password, int level, String role, String UName, String avatar) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.role = role;
        this.UName = UName;
        this.avatar = avatar;
        this.sex = "Unknown";
    }

    public User() {
    }

    public void loadUser(String username, SQLLinker sql) {
        if (username == null) {
            return;
        }
        try {
            ResultSet res = sql.runSQL("SELECT * FROM Users.[user] WHERE username = '" + username + "'");
            if (res == null) {
                return;
            }
            while (res.next()) {
                this.username = res.getString("username");
                this.password = res.getString("password");
                this.level = res.getInt("level");
                this.role = res.getString("role");
                this.UName = res.getString("name");
                this.avatar = res.getString("avator");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
