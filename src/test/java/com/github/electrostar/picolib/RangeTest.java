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

import com.github.electrostar.picolib.Range;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Range} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class RangeTest {

  public RangeTest() {
  }

  /**
   * Test of values method, of class Range.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
    Set<Integer> actual = new HashSet<>();
    for (Range e : Range.values()) {
      actual.add(e.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class Range.
   */
  @Test
  public void testValueOf() {
    assertEquals(Range.RANGE_10MV, Range.valueOf("RANGE_10MV"));
    assertEquals(Range.RANGE_20MV, Range.valueOf("RANGE_20MV"));
    assertEquals(Range.RANGE_50MV, Range.valueOf("RANGE_50MV"));
    assertEquals(Range.RANGE_100MV, Range.valueOf("RANGE_100MV"));
    assertEquals(Range.RANGE_200MV, Range.valueOf("RANGE_200MV"));
    assertEquals(Range.RANGE_500MV, Range.valueOf("RANGE_500MV"));
    assertEquals(Range.RANGE_1V, Range.valueOf("RANGE_1V"));
    assertEquals(Range.RANGE_2V, Range.valueOf("RANGE_2V"));
    assertEquals(Range.RANGE_5V, Range.valueOf("RANGE_5V"));
    assertEquals(Range.RANGE_10V, Range.valueOf("RANGE_10V"));
    assertEquals(Range.RANGE_20V, Range.valueOf("RANGE_20V"));
    assertEquals(Range.RANGE_50V, Range.valueOf("RANGE_50V"));
    assertThrows(IllegalArgumentException.class, () -> {
      Range.valueOf("NotFound");
    });
  }

  /**
   * Test of getValue method, of class Range.
   */
  @Test
  public void testGetValue() {
    int value = 0;
    int multiplier = 1;
    Range[] r = Range.values();
    for (int i = 0; i < Range.values().length; i++) {
      switch (i % 3) {
        case 0:
          value = 10 * multiplier;
          multiplier *= 10;
          break;
        case 1:
          value = 2 * multiplier;
          break;
        case 2:
          value = 5 * multiplier;
          break;
      }
      assertEquals(value, r[i].getValue());
    }
  }

  /**
   * Test of toString method, of class Range.
   */
  @Test
  public void testToString() {
    int value = 0;
    int multiplier = 1;
    Range[] r = Range.values();
    for (int i = 0; i < Range.values().length; i++) {
      switch (i % 3) {
        case 0:
          value = 10 * multiplier;
          multiplier *= 10;
          break;
        case 1:
          value = 2 * multiplier;
          break;
        case 2:
          value = 5 * multiplier;
          break;
      }
      if (value >= 1000) {
        assertEquals((value / 1000) + " V", r[i].toString());
      } else {
        assertEquals(value + " mV", r[i].toString());
      }
    }
  }

  /**
   * Test of getId method, of class Range.
   */
  @Test
  public void testGetId() {
    assertEquals(0, Range.RANGE_10MV.getId());
    assertEquals(1, Range.RANGE_20MV.getId());
    assertEquals(2, Range.RANGE_50MV.getId());
    assertEquals(3, Range.RANGE_100MV.getId());
    assertEquals(4, Range.RANGE_200MV.getId());
    assertEquals(5, Range.RANGE_500MV.getId());
    assertEquals(6, Range.RANGE_1V.getId());
    assertEquals(7, Range.RANGE_2V.getId());
    assertEquals(8, Range.RANGE_5V.getId());
    assertEquals(9, Range.RANGE_10V.getId());
    assertEquals(10, Range.RANGE_20V.getId());
    assertEquals(11, Range.RANGE_50V.getId());
  }
  
  /**
   * Test of findByVoltage method, of class Range.
   */
  @Test
  public void testFindByVoltage() {
    Range[] range = Range.values();
    for (int i = 0; i < Range.values().length; i++) {
      assertEquals(range[i], Range.findByVoltage(range[i].getValue()));
    }
    assertNull(Range.findByVoltage(1));
  }
}
