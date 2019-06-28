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

import com.github.electrostar.picolib.ErrorCode;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link ErrorCode} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class ErrorCodeTest {

  public ErrorCodeTest() {
  }

  /**
   * Test of valueOf method, of class ErrorCode.
   */
  @Test
  public void testValueOf() {
    assertEquals(ErrorCode.OK, ErrorCode.valueOf("OK"));
    assertEquals(ErrorCode.MAX_UNITS_OPENED, ErrorCode.valueOf("MAX_UNITS_OPENED"));
    assertEquals(ErrorCode.MEM_FAIL, ErrorCode.valueOf("MEM_FAIL"));
    assertEquals(ErrorCode.NOT_FOUND, ErrorCode.valueOf("NOT_FOUND"));
    assertEquals(ErrorCode.FW_FAIL, ErrorCode.valueOf("FW_FAIL"));
    assertEquals(ErrorCode.NOT_RESPONDING, ErrorCode.valueOf("NOT_RESPONDING"));
    assertEquals(ErrorCode.CONFIG_FAIL, ErrorCode.valueOf("CONFIG_FAIL"));
    assertEquals(ErrorCode.OS_NOT_SUPPORTED, ErrorCode.valueOf("OS_NOT_SUPPORTED"));
    assertThrows(IllegalArgumentException.class, () -> {
      ErrorCode.valueOf("NotFound");
    });
  }

  /**
   * Test of fromShort method, of class ErrorCode.
   */
  @Test
  public void testfromShort() {
    ErrorCode[] codes = ErrorCode.values();
    
    for(int i = 0; i < codes.length; i++) {
      assertEquals(codes[i], ErrorCode.fromShort((short) i));
    }
    
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      ErrorCode.fromShort((short)codes.length);
    });
    
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      ErrorCode.fromShort((short)-1);
    });
  }

}
