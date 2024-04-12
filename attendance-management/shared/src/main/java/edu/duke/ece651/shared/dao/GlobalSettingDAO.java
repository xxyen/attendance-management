package edu.duke.ece651.shared.dao;

import java.util.HashMap;
import java.util.Map;

public class GlobalSettingDAO extends BasicDAO<Object> {

  public int updateDisplayNamePermission(boolean permission) {
    return updateDisplayNamePermissionAll(permission, queryModificationCount("allow_modify_display_name") + 1);
  }

  public int updateDisplayNamePermissionAll(boolean permission, int cnt) {
    String sql = "UPDATE global_settings SET setting_value = ?, modify_count = ? WHERE setting_name = 'allow_modify_display_name'";
    String permissionString = permission ? "true" : "false";
    return update(sql, permissionString, cnt);
  }

  public int updateDisplayNamePermissionAll(String permission, int cnt) {
    String sql = "UPDATE global_settings SET setting_value = ?, modify_count = ? WHERE setting_name = 'allow_modify_display_name'";
    return update(sql, permission, cnt);
  }

  public int queryModificationCount(String settingName) {
    String sql = " SELECT modify_count FROM global_settings WHERE setting_name = ?";
    Map<String, Object> res = querySingleMapped(sql, settingName);
    // if (res == null) {
    //   throw new NullPointerException("Failed to query display name permission!");
    // }
    return (int) res.get("modify_count");
  }

  public boolean queryDisplayNamePermission() {
    String sql = " SELECT setting_value FROM global_settings WHERE setting_name = 'allow_modify_display_name'";
    Map<String, Object> res = querySingleMapped(sql);
    // if (res == null) {
    //   throw new NullPointerException("Failed to query display name permission!");
    // }
    String ans = (String) res.get("setting_value");
    if (ans.equals("true")) {
      return true;
    } else if (ans.equals("false")) {
      return false;
    }
    throw new IllegalStateException("Failed to query display name permission!");
  }
}
