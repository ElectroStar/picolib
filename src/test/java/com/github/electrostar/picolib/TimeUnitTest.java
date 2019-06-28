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

import com.github.electrostar.picolib.TimeUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TimeUnit} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class TimeUnitTest {

  public TimeUnitTest() {
  }

  /**
   * Test of values method, of class TimeUnit.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5));
    Set<Integer> actual = new HashSet<>();
    for (TimeUnit e : TimeUnit.values()) {
      actual.add(e.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class TimeUnit.
   */
  @Test
  public void testValueOf() {
    assertEquals(TimeUnit.FEMTOSECOND, TimeUnit.valueOf("FEMTOSECOND"));
    assertEquals(TimeUnit.MICROSECOND, TimeUnit.valueOf("MICROSECOND"));
    assertEquals(TimeUnit.MILLISECOND, TimeUnit.valueOf("MILLISECOND"));
    assertEquals(TimeUnit.NANOSECOND, TimeUnit.valueOf("NANOSECOND"));
    assertEquals(TimeUnit.PICOSECOND, TimeUnit.valueOf("PICOSECOND"));
    assertEquals(TimeUnit.SECOND, TimeUnit.valueOf("SECOND"));
    assertThrows(IllegalArgumentException.class, () -> {
      TimeUnit.valueOf("NotFound");
    });
  }

  /**
   * Test of getId method, of class TimeUnit.
   */
  @Test
  public void testGetId() {
    assertEquals(0, TimeUnit.FEMTOSECOND.getId());
    assertEquals(1, TimeUnit.PICOSECOND.getId());
    assertEquals(2, TimeUnit.NANOSECOND.getId());
    assertEquals(3, TimeUnit.MICROSECOND.getId());
    assertEquals(4, TimeUnit.MILLISECOND.getId());
    assertEquals(5, TimeUnit.SECOND.getId());
  }

  /**
   * Test of getSymbol method, of class TimeUnit.
   */
  @Test
  public void testGetSymbol() {
    assertEquals("fs", TimeUnit.FEMTOSECOND.getSymbol());
    assertEquals("ps", TimeUnit.PICOSECOND.getSymbol());
    assertEquals("ns", TimeUnit.NANOSECOND.getSymbol());
    assertEquals("µs", TimeUnit.MICROSECOND.getSymbol());
    assertEquals("ms", TimeUnit.MILLISECOND.getSymbol());
    assertEquals("s", TimeUnit.SECOND.getSymbol());
  }

  /**
   * Test of findById method, of class TimeUnit.
   */
  @Test
  public void testFindById() {
    assertEquals(TimeUnit.FEMTOSECOND, TimeUnit.findById(0));
    assertEquals(TimeUnit.PICOSECOND, TimeUnit.findById(1));
    assertEquals(TimeUnit.NANOSECOND, TimeUnit.findById(2));
    assertEquals(TimeUnit.MICROSECOND, TimeUnit.findById(3));
    assertEquals(TimeUnit.MILLISECOND, TimeUnit.findById(4));
    assertEquals(TimeUnit.SECOND, TimeUnit.findById(5));
    assertEquals(null, TimeUnit.findById(6));
    assertEquals(null, TimeUnit.findById(-1));
  }

}
