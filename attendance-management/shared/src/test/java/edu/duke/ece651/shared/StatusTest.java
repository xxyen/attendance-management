package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

import org.junit.jupiter.api.Test;

public class StatusTest {
  @Test
  public void test_valid_status() {
    Status status = new Status('p');
    assertEquals('p', status.getStatus());

    status.setStatus('a');
    assertEquals('a', status.getStatus());

    status.setStatus('l');
    assertEquals('l', status.getStatus());

    status.setStatus('n');
    assertEquals('n', status.getStatus());
  }

  @Test
  public void test_invalid_status() {
    assertThrows(IllegalArgumentException.class, () -> new Status('z'));
  }
}
