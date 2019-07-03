/*
 * picolib, open source library to work with PicoScopes.
 * Copyright (C) 2018-2019 ElectroStar <startrooper@startrooper.org>
 *
 * This file is part of picolib.
 *
 * picolib is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * picolib is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with picolib. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.electrostar.picolib.unit;

import com.github.electrostar.picolib.UnitInfo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class UnitHelperTest {
    
  /**
   * Tests for the {@link UnitHelper} class.
   * 
   * Test of isSupported method, of class UnitHelper.
   */
  @Test
  public void testIsSupported() {
    String[] variants1 = new String[] {"3", "2", "1"};
    String[] variants2 = new String[] {"1"};
    
    UnitInfo ui = new UnitInfo();
    ui.setVariantInfo("1");
    
    assertFalse(UnitHelper.isSupported(null, variants1));
    assertFalse(UnitHelper.isSupported(ui, null));
    assertFalse(UnitHelper.isSupported(ui, new String[]{}));
    assertTrue(UnitHelper.isSupported(ui, variants2));
    assertTrue(UnitHelper.isSupported(ui, variants1));
  }
}
