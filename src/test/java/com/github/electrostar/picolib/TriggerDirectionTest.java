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

import com.github.electrostar.picolib.TriggerDirection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TriggerDirection} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class TriggerDirectionTest {

  public TriggerDirectionTest() {
  }

  /**
   * Test of values method, of class TriggerDirection.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1));
    Set<Integer> actual = new HashSet<>();
    for (TriggerDirection e : TriggerDirection.values()) {
      actual.add(e.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class TriggerDirection.
   */
  @Test
  public void testValueOf() {
    assertEquals(TriggerDirection.FALLING, TriggerDirection.valueOf("FALLING"));
    assertEquals(TriggerDirection.RISING, TriggerDirection.valueOf("RISING"));
    assertThrows(IllegalArgumentException.class, () -> {
      TriggerDirection.valueOf("NotFound");
    });
  }

  /**
   * Test of getId method, of class TriggerDirection.
   */
  @Test
  public void testGetId() {
    assertEquals(0, TriggerDirection.RISING.getId());
    assertEquals(1, TriggerDirection.FALLING.getId());
  }

  /**
   * Test of findByChar method, of class TriggerDirection.
   */
  @Test
  public void testFindByChar() {
    assertEquals(TriggerDirection.RISING, TriggerDirection.findByChar('r'));
    assertEquals(TriggerDirection.RISING, TriggerDirection.findByChar('R'));
    assertEquals(TriggerDirection.FALLING, TriggerDirection.findByChar('f'));
    assertEquals(TriggerDirection.FALLING, TriggerDirection.findByChar('F'));
    
    for(int idx = 0; idx < 256; idx++) {
      char c = (char)idx;
      if(c != 'r' && c!= 'R' && c != 'f' && c != 'F') {
        assertNull(TriggerDirection.findByChar(c));
      } else {
        assertNotNull(TriggerDirection.findByChar(c));
      }
    }
  }
}
