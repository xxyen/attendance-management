package edu.duke.ece651.shared.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * The GlobalSettingDAO class provides data access operations specific to global settings.
 * It extends the BasicDAO class and inherits basic data access methods.
 */
public class GlobalSettingDAO extends BasicDAO<Object> {

  /**
     * Updates the display name permission setting and increments the modification count.
     *
     * @param permission the new value of the display name permission setting
     * @return the number of rows affected by the update
     */
  public int updateDisplayNamePermission(boolean permission) {
    return updateDisplayNamePermissionAll(permission, queryModificationCount("allow_modify_display_name") + 1);
  }

  /**
     * Updates the display name permission setting and modification count.
     *
     * @param permission the new value of the display name permission setting
     * @param cnt        the new value of the modification count
     * @return the number of rows affected by the update
     */
  public int updateDisplayNamePermissionAll(boolean permission, int cnt) {
    String sql = "UPDATE global_settings SET setting_value = ?, modify_count = ? WHERE setting_name = 'allow_modify_display_name'";
    String permissionString = permission ? "true" : "false";
    return update(sql, permissionString, cnt);
  }

  /**
     * Updates the display name permission setting and modification count.
     *
     * @param permission the new value of the display name permission setting
     * @param cnt        the new value of the modification count
     * @return the number of rows affected by the update
     */
  public int updateDisplayNamePermissionAll(String permission, int cnt) {
    String sql = "UPDATE global_settings SET setting_value = ?, modify_count = ? WHERE setting_name = 'allow_modify_display_name'";
    return update(sql, permission, cnt);
  }

  /**
     * Queries the modification count for the specified setting name.
     *
     * @param settingName the name of the setting to query
     * @return the modification count for the specified setting
     */
  public int queryModificationCount(String settingName) {
    String sql = " SELECT modify_count FROM global_settings WHERE setting_name = ?";
    Map<String, Object> res = querySingleMapped(sql, settingName);
    // if (res == null) {
    //   throw new NullPointerException("Failed to query display name permission!");
    // }
    return (int) res.get("modify_count");
  }

  /**
     * Queries the display name permission setting.
     *
     * @return the value of the display name permission setting
     * @throws IllegalStateException if the setting value is neither "true" nor "false"
     */
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
