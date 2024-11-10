package top.mryan2005.managesysteminjava.BasicClass;

import top.mryan2005.managesysteminjava.SQLs.SQLLinker;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class LoginPart {

    private SQLLinker sql;

    private HashMap<String, User> users = new HashMap<>();

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
                String role = switch (res.getInt("role")) {
                    case 0 -> "forbidden";
                    case 1 -> "admin";
                    case 2 -> "public";
                    case 3 -> "Dictionary Admin";
                    case 4 -> "professor";
                    case 5 -> "Community Admin";
                    case 6 -> "Owner";
                    default -> "";
                };
                if(role.matches("")) {
                    continue;
                }
                User user = new User(res.getString("username"), res.getString("password"), res.getInt("level"), role, res.getString("name"), res.getString("avator"));
                users.put(res.getString("username"), user);
            }
        } catch (SQLException e) {
            return;
        }
    }

    public LoginPart(SQLLinker sql1) {
        sql = sql1;
    }

    public void setSQL(SQLLinker sql1) {
        sql = sql1;
    }

    public LoginPart() {}

    public void register(String username, String password, String Sex, String UName) throws UnsupportedEncodingException {
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

    public boolean login(String username, String password) throws UnsupportedEncodingException {
        readUsersTable();
        password = runMd5(password);
        if (users.containsKey(username)) {
            return users.get(username).password.matches(password);
        }
        return false;
    }

    private String runMd5(String password) throws UnsupportedEncodingException {
        for (int i = 0; i < 5; i++) {
            password = DigestUtils.md5Hex(password.getBytes("utf-8"));
        }
        return password;
    }

    public void setAvatar(String username, String avator) {
        sql.runSQL("UPDATE Users.[user] SET avator = '" + avator + "' WHERE username = '" + username + "'");
        users.get(username).avatar = avator;
    }

    public void setUName(String username, String UName) {
        sql.runSQL("UPDATE Users.[user] SET name = '" + UName + "' WHERE username = '" + username + "'");
        users.get(username).UName = UName;
    }

    public void setPassword(String username, String password) throws UnsupportedEncodingException {
        password = runMd5(password);
        sql.runSQL("UPDATE Users.[user] SET password = '" + password + "' WHERE username = '" + username + "'");
        users.get(username).password = password;
    }

    public void setSex(String username, String sex) {
        sql.runSQL("UPDATE Users.[user] SET sex = '" + sex + "' WHERE username = '" + username + "'");
        users.get(username).sex = sex;
    }

    public void setLevel(String username, int level) {
        sql.runSQL("UPDATE Users.[user] SET level = '" + level + "' WHERE username = '" + username + "'");
        users.get(username).level = level;
    }

    public String LevelUp(String username, String Operator) {
        if(users.get(Operator).role.matches("Owner") || users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Community Admin")) {
            if(users.get(username) == null) {
                return "用户不存在！";
            }
            int res = users.get(username).level + 1;
            sql.runSQL("UPDATE Users.[user] SET level = " + res + " WHERE username = '" + username + "'");
            users.get(username).level = res;
            return "";
        } else {
            return "权限不足！";
        }
    }

    public String LevelUp(String username, String Operator, int dis) {
        if(users.get(Operator).role.matches("Owner") || users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Community Admin")) {
            if(users.get(username) == null) {
                return "用户不存在！";
            }
            int res = users.get(username).level + dis;
            sql.runSQL("UPDATE Users.[user] SET level = " + res + " WHERE username = '" + username + "'");
            users.get(username).level = res;
            return "";
        } else {
            return "权限不足！";
        }
    }

    public void levelDown(String username, String Operator) {
        if(users.get(Operator).role.matches("Owner") || users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Community Admin")) {
            if(users.get(username) == null) {
                return;
            }
            int res = users.get(username).level - 1;
            sql.runSQL("UPDATE Users.[user] SET level = " + res + " WHERE username = '" + username + "'");
            users.get(username).level = res;
        }
    }

    public void levelDown(String username, String Operator, int dis) {
        if(users.get(Operator).role.matches("Owner") || users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Community Admin")) {
            if(users.get(username) == null) {
                return;
            }
            int res = users.get(username).level - dis;
            sql.runSQL("UPDATE Users.[user] SET level = " + res + " WHERE username = '" + username + "'");
            users.get(username).level = res;
        }
    }

    public void removeUser(String username, String Operator) {
        if(users.get(Operator).role.matches("Owner")) {
            return;
        } else if((users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Owner")) && !users.get(username).role.matches("Owner")) {
            sql.runSQL("DELETE FROM Users.[user] WHERE username = '" + username + "'");
            users.remove(username);
        }
    }

    public void forbidAnUser(String username, String Operator) {
        if(users.get(Operator).role.matches("Owner")) {
            return;
        } else if((users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Owner") || users.get(Operator).role.matches("Community Admin")) && (!users.get(username).role.matches("Owner") || !users.get(username).role.matches("admin") || !users.get(username).role.matches("Community Admin"))) {
            sql.runSQL("UPDATE Users.[user] SET role = '0' WHERE username = '" + username + "'");
            users.get(username).role = "forbidden";
        }
    }

    public void allowAnUser(String username, String Operator) {
        if(!users.get(Operator).role.matches("forbidden")) {
            return;
        } else if(users.get(Operator).role.matches("admin") || users.get(Operator).role.matches("Owner") || users.get(Operator).role.matches("Community Admin")) {
            sql.runSQL("UPDATE Users.[user] SET role = '2' WHERE username = '" + username + "'");
            users.get(username).role = "public";
        }
    }

    public void ChangeUserRole(String username, String Operator, String role) {
        if(users.get(Operator).role.matches("Owner")) {
            return;
        } else if(users.get(username) == null) {
            return;
        } else if(users.get(Operator).role.matches("Owner")) {
            int res = switch (role) {
                case "admin" -> 1;
                case "Dictionary Admin" -> 3;
                case "professor" -> 4;
                case "Community Admin" -> 5;
                case "public" -> 2;
                default -> 0;
            };
            if(users.get(username).role.matches(role)) {
                return;
            }
            sql.runSQL("UPDATE Users.[user] SET role = "+ res +" WHERE username = '" + username + "'");
            users.get(username).role = role;
        } else if(users.get(Operator).role.matches("admin")) {
            int res = switch (role) {
                case "Dictionary Admin" -> 3;
                case "professor" -> 4;
                case "Community Admin" -> 5;
                case "public" -> 2;
                default -> 0;
            };
            if(users.get(username).role.matches(role)) {
                return;
            }
            sql.runSQL("UPDATE Users.[user] SET role = "+ res +" WHERE username = '" + username + "'");
            users.get(username).role = role;
        }
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
            login.setAvatar("test", "test");
            login.setUName("test", "test");
            login.setPassword("test", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
