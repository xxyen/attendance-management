package edu.duke.ece651.shared.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class BasicDAOTest {
  BasicDAO basicDAO = new BasicDAO();
  
  @Test
  public void test_errorSql() {
    assertThrows(RuntimeException.class, () -> basicDAO.update("nothing"));
    assertThrows(RuntimeException.class, () -> basicDAO.queryMulti("nothing", int.class));
    assertThrows(RuntimeException.class, () -> basicDAO.querySingle("nothing", int.class));
    assertThrows(RuntimeException.class, () -> basicDAO.querySingleMapped("nothing"));
    assertThrows(RuntimeException.class, () -> basicDAO.queryMultiMapped("nothing"));
    //    assertThrows(RuntimeException.class, () -> basicDAO.queryScalar("nothing"));
    assertThrows(RuntimeException.class, () -> basicDAO.insertAndGetGeneratedKey("nothing"));
  }

}
