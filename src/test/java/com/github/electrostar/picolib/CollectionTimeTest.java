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

import com.github.electrostar.picolib.CollectionTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link CollectionTime} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class CollectionTimeTest {

  public CollectionTimeTest() {
  }

  /**
   * Test of values method, of class CollectionTime.
   */
  @Test
  public void testValues() {
    Set<Long> expected = new HashSet<>(Arrays.asList(
            50L, 100L, 200L, 500L, 1000L, 2000L, 5000L, 10000L, 20000L, 50000L,
            100000L, 200000L, 500000L, 1000000L, 2000000L, 5000000L, 10000000L,
            20000000L, 50000000L, 100000000L, 200000000L, 500000000L, 1000000000L,
            2000000000L, 5000000000L, 10000000000L, 20000000000L, 50000000000L,
            100000000000L, 200000000000L, 500000000000L, 1000000000000L, 2000000000000L,
            5000000000000L));
    Set<Long> actual = new HashSet<>();
    for (CollectionTime c : CollectionTime.values()) {
      actual.add(c.getDivisionTime());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class CollectionTime.
   */
  @Test
  public void testValueOf() {
    assertEquals(CollectionTime.DIV50NS, CollectionTime.valueOf("DIV50NS"));
    assertEquals(CollectionTime.DIV100NS, CollectionTime.valueOf("DIV100NS"));
    assertEquals(CollectionTime.DIV200NS, CollectionTime.valueOf("DIV200NS"));
    assertEquals(CollectionTime.DIV500NS, CollectionTime.valueOf("DIV500NS"));
    assertEquals(CollectionTime.DIV1US, CollectionTime.valueOf("DIV1US"));
    assertEquals(CollectionTime.DIV2US, CollectionTime.valueOf("DIV2US"));
    assertEquals(CollectionTime.DIV5US, CollectionTime.valueOf("DIV5US"));
    assertEquals(CollectionTime.DIV10US, CollectionTime.valueOf("DIV10US"));
    assertEquals(CollectionTime.DIV20US, CollectionTime.valueOf("DIV20US"));
    assertEquals(CollectionTime.DIV50US, CollectionTime.valueOf("DIV50US"));
    assertEquals(CollectionTime.DIV100US, CollectionTime.valueOf("DIV100US"));
    assertEquals(CollectionTime.DIV200US, CollectionTime.valueOf("DIV200US"));
    assertEquals(CollectionTime.DIV500US, CollectionTime.valueOf("DIV500US"));
    assertEquals(CollectionTime.DIV1MS, CollectionTime.valueOf("DIV1MS"));
    assertEquals(CollectionTime.DIV2MS, CollectionTime.valueOf("DIV2MS"));
    assertEquals(CollectionTime.DIV5MS, CollectionTime.valueOf("DIV5MS"));
    assertEquals(CollectionTime.DIV10MS, CollectionTime.valueOf("DIV10MS"));
    assertEquals(CollectionTime.DIV20MS, CollectionTime.valueOf("DIV20MS"));
    assertEquals(CollectionTime.DIV50MS, CollectionTime.valueOf("DIV50MS"));
    assertEquals(CollectionTime.DIV100MS, CollectionTime.valueOf("DIV100MS"));
    assertEquals(CollectionTime.DIV200MS, CollectionTime.valueOf("DIV200MS"));
    assertEquals(CollectionTime.DIV500MS, CollectionTime.valueOf("DIV500MS"));
    assertEquals(CollectionTime.DIV1S, CollectionTime.valueOf("DIV1S"));
    assertEquals(CollectionTime.DIV2S, CollectionTime.valueOf("DIV2S"));
    assertEquals(CollectionTime.DIV5S, CollectionTime.valueOf("DIV5S"));
    assertEquals(CollectionTime.DIV10S, CollectionTime.valueOf("DIV10S"));
    assertEquals(CollectionTime.DIV20S, CollectionTime.valueOf("DIV20S"));
    assertEquals(CollectionTime.DIV50S, CollectionTime.valueOf("DIV50S"));
    assertEquals(CollectionTime.DIV100S, CollectionTime.valueOf("DIV100S"));
    assertEquals(CollectionTime.DIV200S, CollectionTime.valueOf("DIV200S"));
    assertEquals(CollectionTime.DIV500S, CollectionTime.valueOf("DIV500S"));
    assertEquals(CollectionTime.DIV1000S, CollectionTime.valueOf("DIV1000S"));
    assertEquals(CollectionTime.DIV2000S, CollectionTime.valueOf("DIV2000S"));
    assertEquals(CollectionTime.DIV5000S, CollectionTime.valueOf("DIV5000S"));
    assertThrows(IllegalArgumentException.class, () -> {
      CollectionTime.valueOf("NotFound");
    });
  }

  /**
   * Test of getDivisionTime method, of class CollectionTime.
   */
  @Test
  public void testGetDivisionTime() {
    long value = 0;
    long multiplier = 1;
    CollectionTime[] ct = CollectionTime.values();
    for (int i = 0; i < CollectionTime.values().length; i++) {
      switch (i % 3) {
        case 0:
          value = 50L * multiplier;
          multiplier *= 10;
          break;
        case 1:
          value = 10L * multiplier;
          break;
        case 2:
          value = 20L * multiplier;
          break;
      }
      assertEquals(value, ct[i].getDivisionTime());
    }
  }

  /**
   * Test of toString and getLabel method, of class CollectionTime.
   */
  @Test
  public void testToStringAndGetLabel() {
    String unit;
    long showValue;
    CollectionTime[] ct = CollectionTime.values();
    for (int i = 0; i < CollectionTime.values().length; i++) {
      long value = ct[i].getDivisionTime();
      if (value <= 999) {
        unit = "ns";
        showValue = value;
      } else if(value <= 999999) {
        unit = "us";
        showValue = value / 1000;
      } else if(value <= 999999999) {
        unit = "ms";
        showValue = value / 1000000;
      } else {
        unit = "s";
        showValue = value / 1000000000;
      }
      
      assertEquals(String.valueOf(showValue) + " " + unit + "/Div", ct[i].toString());
      assertEquals(String.valueOf(showValue) + " " + unit + "/Div", ct[i].getLabel());
    }
    
    assertNotEquals(CollectionTime.DIV100MS.toString(), "100 us/Div");
    assertNotEquals(CollectionTime.DIV100MS.getLabel(), "100 us/Div");
  }
  
  /**
   * Test of findByTime method, of class CollectionTime.
   */
  @Test
  public void testFindByTime() {
    CollectionTime[] ct = CollectionTime.values();
    for (int i = 0; i < CollectionTime.values().length; i++) {
      assertEquals(ct[i], CollectionTime.findByTime(ct[i].getDivisionTime()));
    }
    assertNull(CollectionTime.findByTime(1));
  }
}
