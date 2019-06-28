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

import com.github.electrostar.picolib.PicoInfo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link PicoInfo} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class PicoInfoTest {

  public PicoInfoTest() {
  }

  /**
   * Test of values method, of class PicoInfo.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    Set<Integer> actual = new HashSet<>();
    for (PicoInfo e : PicoInfo.values()) {
      actual.add(e.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class PicoInfo.
   */
  @Test
  public void testValueOf() {
    assertEquals(PicoInfo.BATCH_AND_SERIAL, PicoInfo.valueOf("BATCH_AND_SERIAL"));
    assertEquals(PicoInfo.CALIBRATION_DATE, PicoInfo.valueOf("CALIBRATION_DATE"));
    assertEquals(PicoInfo.DRIVER_PATH, PicoInfo.valueOf("DRIVER_PATH"));
    assertEquals(PicoInfo.DRIVER_VERSION, PicoInfo.valueOf("DRIVER_VERSION"));
    assertEquals(PicoInfo.ERROR_CODE, PicoInfo.valueOf("ERROR_CODE"));
    assertEquals(PicoInfo.HARDWARE_VERSION, PicoInfo.valueOf("HARDWARE_VERSION"));
    assertEquals(PicoInfo.KERNEL_VERSION, PicoInfo.valueOf("KERNEL_VERSION"));
    assertEquals(PicoInfo.USB_VERSION, PicoInfo.valueOf("USB_VERSION"));
    assertEquals(PicoInfo.VARIANT_INFO, PicoInfo.valueOf("VARIANT_INFO"));
    assertThrows(IllegalArgumentException.class, () -> {
      PicoInfo.valueOf("NotFound");
    });
  }

  /**
   * Test of getId method, of class PicoInfo.
   */
  @Test
  public void testGetId() {
    assertEquals(0, PicoInfo.DRIVER_VERSION.getId());
    assertEquals(1, PicoInfo.USB_VERSION.getId());
    assertEquals(2, PicoInfo.HARDWARE_VERSION.getId());
    assertEquals(3, PicoInfo.VARIANT_INFO.getId());
    assertEquals(4, PicoInfo.BATCH_AND_SERIAL.getId());
    assertEquals(5, PicoInfo.CALIBRATION_DATE.getId());
    assertEquals(6, PicoInfo.ERROR_CODE.getId());
    assertEquals(7, PicoInfo.KERNEL_VERSION.getId());
    assertEquals(8, PicoInfo.DRIVER_PATH.getId());
  }
}
