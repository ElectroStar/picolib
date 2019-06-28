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

import com.github.electrostar.picolib.SweepType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link SweepType} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class SweepTypeTest {

  public SweepTypeTest() {
  }

  /**
   * Test of values method, of class SweepType.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3));
    Set<Integer> actual = new HashSet<>();
    for (SweepType e : SweepType.values()) {
      actual.add(e.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class SweepType.
   */
  @Test
  public void testValueOf() {
    assertEquals(SweepType.DOWN, SweepType.valueOf("DOWN"));
    assertEquals(SweepType.DOWNUP, SweepType.valueOf("DOWNUP"));
    assertEquals(SweepType.UP, SweepType.valueOf("UP"));
    assertEquals(SweepType.UPDOWN, SweepType.valueOf("UPDOWN"));
    assertThrows(IllegalArgumentException.class, () -> {
      SweepType.valueOf("NotFound");
    });
  }

  /**
   * Test of getId method, of class SweepType.
   */
  @Test
  public void testGetId() {
    assertEquals(0, SweepType.UP.getId());
    assertEquals(1, SweepType.DOWN.getId());
    assertEquals(2, SweepType.UPDOWN.getId());
    assertEquals(3, SweepType.DOWNUP.getId());
  }

}
