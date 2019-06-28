/* 
 * picolib, open source library to work with PicoScopes.
 * Copyright (C) 2018-2019 ElectroStar <startrooper@startrooper.org>
 *
 * This file is part of picolib.
 *
 * picolib is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 *  version 3 of the License, or (at your option) any later version.
 *
 * picolib is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with picolib. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.electrostar.picolib;

import com.github.electrostar.picolib.WaveType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link WaveType} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class WaveTypeTest {

  public WaveTypeTest() {
  }

  /**
   * Test of values method, of class WaveType.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    Set<Integer> actual = new HashSet<>();
    for (WaveType e : WaveType.values()) {
      actual.add(e.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class WaveType.
   */
  @Test
  public void testValueOf() {
    assertEquals(WaveType.DC_VOLTAGE, WaveType.valueOf("DC_VOLTAGE"));
    assertEquals(WaveType.GAUSSIAN, WaveType.valueOf("GAUSSIAN"));
    assertEquals(WaveType.HALF_SINE, WaveType.valueOf("HALF_SINE"));
    assertEquals(WaveType.RAMPDOWN, WaveType.valueOf("RAMPDOWN"));
    assertEquals(WaveType.RAMPUP, WaveType.valueOf("RAMPUP"));
    assertEquals(WaveType.SINC, WaveType.valueOf("SINC"));
    assertEquals(WaveType.SINE, WaveType.valueOf("SINE"));
    assertEquals(WaveType.SQUARE, WaveType.valueOf("SQUARE"));
    assertEquals(WaveType.TRIANGLE, WaveType.valueOf("TRIANGLE"));
    assertThrows(IllegalArgumentException.class, () -> {
      WaveType.valueOf("NotFound");
    });
  }

  /**
   * Test of getId method, of class WaveType.
   */
  @Test
  public void testGetId() {
    assertEquals(0, WaveType.SINE.getId());
    assertEquals(1, WaveType.SQUARE.getId());
    assertEquals(2, WaveType.TRIANGLE.getId());
    assertEquals(3, WaveType.RAMPUP.getId());
    assertEquals(4, WaveType.RAMPDOWN.getId());
    assertEquals(5, WaveType.DC_VOLTAGE.getId());
    assertEquals(6, WaveType.GAUSSIAN.getId());
    assertEquals(7, WaveType.SINC.getId());
    assertEquals(8, WaveType.HALF_SINE.getId());
  }
  
  /**
   * Test of findByLabel method, of class WaveType.
   */
  @Test
  public void testFindByLabel() {
    for (WaveType wt : WaveType.values()) {
      assertEquals(wt, WaveType.findByLabel(wt.getLabel()));
    }
    assertNull(WaveType.findByLabel("Dummy"));
    assertNull(WaveType.findByLabel(null));
    assertNull(WaveType.findByLabel(""));
  }
  
  /**
   * Test of toString and getLabel method, of class WaveType.
   */
  @Test
  public void testToStringAndGetLabel() {
    assertEquals(WaveType.DC_VOLTAGE.toString(), "DCVoltage");
    assertEquals(WaveType.DC_VOLTAGE.getLabel(), "DCVoltage");
    assertNotEquals(WaveType.DC_VOLTAGE.getLabel(), "Gaussian");
    assertNotEquals(WaveType.DC_VOLTAGE.getLabel(), "RampDown");
  }
}
