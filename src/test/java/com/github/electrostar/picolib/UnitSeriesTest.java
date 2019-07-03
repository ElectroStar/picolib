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
    assertEquals(UnitSeries.PICOSCOPE2000, UnitSeries.valueOf("PICOSCOPE2000"));
    assertEquals(UnitSeries.PICOSCOPE2000A, UnitSeries.valueOf("PICOSCOPE2000A"));
    assertEquals(UnitSeries.PICOSCOPE3000, UnitSeries.valueOf("PICOSCOPE3000"));
    assertEquals(UnitSeries.PICOSCOPE3000A, UnitSeries.valueOf("PICOSCOPE3000A"));
    assertEquals(UnitSeries.PICOSCOPE4000, UnitSeries.valueOf("PICOSCOPE4000"));
    assertEquals(UnitSeries.PICOSCOPE4000A, UnitSeries.valueOf("PICOSCOPE4000A"));
    assertEquals(UnitSeries.PICOSCOPE5000, UnitSeries.valueOf("PICOSCOPE5000"));
    assertEquals(UnitSeries.PICOSCOPE6000, UnitSeries.valueOf("PICOSCOPE6000"));
    assertThrows(IllegalArgumentException.class, () -> {
      UnitSeries.valueOf("NotFound");
    });
  }

  /**
   * Test of findByLabel method, of class UnitSeries.
   */
  @Test
  public void testFindByLabel() {
    assertEquals(UnitSeries.PICOSCOPE2000, UnitSeries.findByLabel("2000"));
    assertEquals(UnitSeries.PICOSCOPE2000A, UnitSeries.findByLabel("2000A"));
    assertEquals(UnitSeries.PICOSCOPE3000, UnitSeries.findByLabel("3000"));
    assertEquals(UnitSeries.PICOSCOPE3000A, UnitSeries.findByLabel("3000A"));
    assertEquals(UnitSeries.PICOSCOPE4000, UnitSeries.findByLabel("4000"));
    assertEquals(UnitSeries.PICOSCOPE4000A, UnitSeries.findByLabel("4000A"));
    assertEquals(UnitSeries.PICOSCOPE5000, UnitSeries.findByLabel("5000"));
    assertEquals(UnitSeries.PICOSCOPE6000, UnitSeries.findByLabel("6000"));
    assertEquals(null, UnitSeries.findByLabel(""));
    assertEquals(null, UnitSeries.findByLabel(null));
    assertEquals(null, UnitSeries.findByLabel("NotFound"));
  }
  
  /**
   * Test of getLabel method, of class UnitSeries.
   */
  @Test
  public void testGetLabel() {
    assertEquals("2000", UnitSeries.PICOSCOPE2000.getLabel());
    assertEquals("2000A", UnitSeries.PICOSCOPE2000A.getLabel());
    assertEquals("3000", UnitSeries.PICOSCOPE3000.getLabel());
    assertEquals("3000A", UnitSeries.PICOSCOPE3000A.getLabel());
    assertEquals("4000", UnitSeries.PICOSCOPE4000.getLabel());
    assertEquals("4000A", UnitSeries.PICOSCOPE4000A.getLabel());
    assertEquals("5000", UnitSeries.PICOSCOPE5000.getLabel());
    assertEquals("6000", UnitSeries.PICOSCOPE6000.getLabel());
    assertNotEquals("XXXX", UnitSeries.PICOSCOPE2000.getLabel());
  }
  
  /**
   * Test of getDescription method, of class UnitSeries.
   */
  @Test
  public void testGetDescription() {
    assertEquals("PicoScope 2000 series PC Oscilloscope",
            UnitSeries.PICOSCOPE2000.getDescription());
    assertNotEquals("PicoScope 2000 series PC Oscilloscope",
            UnitSeries.PICOSCOPE2000A.getDescription());
  }
  
  /**
   * Test of getProductIds method, of class UnitSeries.
   */
  @Test
  public void testGetProductIds() {
    List<Short> variants = new ArrayList<>();
    variants.add((short)0x1007);
    variants.add((short)0x1200);
    
    
    assertEquals(UnitSeries.PICOSCOPE2000.getProductIds(), 
            variants);
    assertNotEquals(UnitSeries.PICOSCOPE2000A.getProductIds(), 
            variants);
  }
  
  /**
   * Test of toString method, of class UnitSeries.
   */
  @Test
  public void testToString() {
    assertEquals("PicoScope 2000 series PC Oscilloscope", 
            UnitSeries.PICOSCOPE2000.toString());
    assertNotEquals("PicoScope 2000 series PC Oscilloscope",
            UnitSeries.PICOSCOPE2000A.toString());
  }
}
