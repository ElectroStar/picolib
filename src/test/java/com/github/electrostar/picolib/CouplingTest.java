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

import com.github.electrostar.picolib.Coupling;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Coupling} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class CouplingTest {

  public CouplingTest() {
  }

  /**
   * Test of values method, of class Coupling.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1));
    Set<Integer> actual = new HashSet<>();
    for (Coupling c : Coupling.values()) {
      actual.add(c.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class Coupling.
   */
  @Test
  public void testValueOf() {
    assertEquals(Coupling.AC, Coupling.valueOf("AC"));
    assertEquals(Coupling.DC, Coupling.valueOf("DC"));
    assertThrows(IllegalArgumentException.class, () -> {
      Coupling.valueOf("NotFound");
    });
  }

  /**
   * Test of getId method, of class Coupling.
   */
  @Test
  public void testGetId() {
    assertEquals(0, Coupling.AC.getId());
    assertEquals(1, Coupling.DC.getId());
  }
}
