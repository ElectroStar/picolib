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

import com.github.electrostar.picolib.UnitSeries;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link UnitSeries} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class UnitSeriesTest {

  public UnitSeriesTest() {
  }

  /**
   * Test of values method, of class UnitSeries.
   */
  @Test
  public void testValues() {
    Set<String> expected = new HashSet<>(Arrays.asList("2000", "2000A", "3000", "3000A", "4000", "4000A", "5000", "6000"));
    Set<String> actual = new HashSet<>();
    for (UnitSeries e : UnitSeries.values()) {
      actual.add(e.getLabel());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class UnitSeries.
   */
  @Test
  public void testValueOf() {
    assertEquals(UnitSeries.PicoScope2000er, UnitSeries.valueOf("PicoScope2000er"));
    assertEquals(UnitSeries.PicoScope2000Aer, UnitSeries.valueOf("PicoScope2000Aer"));
    assertEquals(UnitSeries.PicoScope3000er, UnitSeries.valueOf("PicoScope3000er"));
    assertEquals(UnitSeries.PicoScope3000Aer, UnitSeries.valueOf("PicoScope3000Aer"));
    assertEquals(UnitSeries.PicoScope4000er, UnitSeries.valueOf("PicoScope4000er"));
    assertEquals(UnitSeries.PicoScope4000Aer, UnitSeries.valueOf("PicoScope4000Aer"));
    assertEquals(UnitSeries.PicoScope5000er, UnitSeries.valueOf("PicoScope5000er"));
    assertEquals(UnitSeries.PicoScope6000er, UnitSeries.valueOf("PicoScope6000er"));
    assertThrows(IllegalArgumentException.class, () -> {
      UnitSeries.valueOf("NotFound");
    });
  }

  /**
   * Test of findByLabel method, of class UnitSeries.
   */
  @Test
  public void testFindByLabel() {
    assertEquals(UnitSeries.PicoScope2000er, UnitSeries.findByLabel("2000"));
    assertEquals(UnitSeries.PicoScope2000Aer, UnitSeries.findByLabel("2000A"));
    assertEquals(UnitSeries.PicoScope3000er, UnitSeries.findByLabel("3000"));
    assertEquals(UnitSeries.PicoScope3000Aer, UnitSeries.findByLabel("3000A"));
    assertEquals(UnitSeries.PicoScope4000er, UnitSeries.findByLabel("4000"));
    assertEquals(UnitSeries.PicoScope4000Aer, UnitSeries.findByLabel("4000A"));
    assertEquals(UnitSeries.PicoScope5000er, UnitSeries.findByLabel("5000"));
    assertEquals(UnitSeries.PicoScope6000er, UnitSeries.findByLabel("6000"));
    assertEquals(null, UnitSeries.findByLabel(""));
    assertEquals(null, UnitSeries.findByLabel(null));
    assertEquals(null, UnitSeries.findByLabel("NotFound"));
  }
  
  /**
   * Test of getLabel method, of class UnitSeries.
   */
  @Test
  public void testGetLabel() {
    assertEquals(UnitSeries.PicoScope2000er.getLabel(), "2000");
    assertEquals(UnitSeries.PicoScope2000Aer.getLabel(), "2000A");
    assertEquals(UnitSeries.PicoScope3000er.getLabel(), "3000");
    assertEquals(UnitSeries.PicoScope3000Aer.getLabel(), "3000A");
    assertEquals(UnitSeries.PicoScope4000er.getLabel(), "4000");
    assertEquals(UnitSeries.PicoScope4000Aer.getLabel(), "4000A");
    assertEquals(UnitSeries.PicoScope5000er.getLabel(), "5000");
    assertEquals(UnitSeries.PicoScope6000er.getLabel(), "6000");
    assertNotEquals(UnitSeries.PicoScope2000er.getLabel(), "XXXX");
  }
  
  /**
   * Test of getDescription method, of class UnitSeries.
   */
  @Test
  public void testGetDescription() {
    assertEquals(UnitSeries.PicoScope2000er.getDescription(), 
            "PicoScope 2000er series PC Oscilloscope");
    assertNotEquals(UnitSeries.PicoScope2000Aer.getDescription(), 
            "PicoScope 2000er series PC Oscilloscope");
  }
  
  /**
   * Test of getProductIds method, of class UnitSeries.
   */
  @Test
  public void testGetProductIds() {
    List<Short> variants = new ArrayList<>();
    variants.add((short)0x1007);
    variants.add((short)0x1200);
    
    
    assertEquals(UnitSeries.PicoScope2000er.getProductIds(), 
            variants);
    assertNotEquals(UnitSeries.PicoScope2000Aer.getProductIds(), 
            variants);
  }
  
  /**
   * Test of toString method, of class UnitSeries.
   */
  @Test
  public void testToString() {
    assertEquals(UnitSeries.PicoScope2000er.toString(), 
            "PicoScope 2000er series PC Oscilloscope");
    assertNotEquals(UnitSeries.PicoScope2000Aer.toString(), 
            "PicoScope 2000er series PC Oscilloscope");
  }
}
