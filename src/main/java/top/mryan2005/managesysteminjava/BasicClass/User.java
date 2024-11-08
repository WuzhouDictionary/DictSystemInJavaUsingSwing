package top.mryan2005.managesysteminjava.BasicClass;

public class User {
    public String username;    // 用户名
    public String password;    // 密码
    public String level;   // 等级
    public String role;    // 角色
    public String UName;    // 昵称
    public String avatar;  // 头像

    public User(String username, String password, String level, String role, String UName, String avatar) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.role = role;
        this.UName = UName;
        this.avatar = avatar;
    }
}
