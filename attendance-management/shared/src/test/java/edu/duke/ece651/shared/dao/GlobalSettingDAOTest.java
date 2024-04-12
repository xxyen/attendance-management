package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.dao.GlobalSettingDAO;

public class GlobalSettingDAOTest {
  @Test
  public void test_displayNamePermission() {
    GlobalSettingDAO gsDAO = new GlobalSettingDAO();
    int cnt =  gsDAO.queryModificationCount("allow_modify_display_name");
    boolean permission = gsDAO.queryDisplayNamePermission();

    gsDAO.updateDisplayNamePermissionAll(false, 5);
    assertFalse(gsDAO.queryDisplayNamePermission());
    assertEquals(5, gsDAO.queryModificationCount("allow_modify_display_name"));
    gsDAO.updateDisplayNamePermission(true);
    assertTrue(gsDAO.queryDisplayNamePermission());
    assertEquals(6, gsDAO.queryModificationCount("allow_modify_display_name"));
    gsDAO.updateDisplayNamePermissionAll("ss", 1);
    assertThrows(IllegalStateException.class, () -> gsDAO.queryDisplayNamePermission());
    gsDAO.updateDisplayNamePermissionAll(permission, cnt);
    
  }

}
