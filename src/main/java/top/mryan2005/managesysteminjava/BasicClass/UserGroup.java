package top.mryan2005.managesysteminjava.BasicClass;

import top.mryan2005.managesysteminjava.SQLs.SQLLinker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserGroup {
    public final HashMap<String, Integer> userGroup = new HashMap<>();

    public final void getCurrentGroup(SQLLinker sql) {
        try {
            ResultSet res = sql.runSQL("SELECT * FROM Users.[permission]");
            if (res != null) {
                while (res.next()) {
                    userGroup.put(res.getString("permissionName"), res.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public final int getGroupID(String groupName) {
        return userGroup.get(groupName);
    }

    public final String getGroupName(int groupID) {
        for (String key : userGroup.keySet()) {
            if (userGroup.get(key) == groupID) {
                return key;
            }
        }
        return null;
    }

    public final String addGroup(String groupName, int groupID, SQLLinker sql) {
        if(userGroup.containsKey(groupName)) {
            return "Group name already exists!";
        }
        userGroup.put(groupName, groupID);
        sql.runSQL("INSERT INTO Users.[permission] (permissionName, id) VALUES ('" + groupName + "', " + groupID + ")");
        return "Group added successfully!";
    }

    public final String removeGroup(String groupName, SQLLinker sql) {
        if(!userGroup.containsKey(groupName)) {
            return "Group name does not exist!";
        }
        userGroup.remove(groupName);
        sql.runSQL("DELETE FROM Users.[permission] WHERE permissionName = '" + groupName + "'");
        return "Group removed successfully!";
    }
}
