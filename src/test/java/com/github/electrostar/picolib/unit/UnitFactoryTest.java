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

import com.github.electrostar.picolib.UnitSeries;
import com.github.electrostar.picolib.exception.NotSupportedException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * Tests for the {@link UnitFactory} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UnitFactoryTest {
  
  @Mock
  private UnitFactory.FactoryHelper mockFactoryHelper;
  
  @Mock
  private PicoUnit mockUnitPS2000;
  
  private UnitFactory factory;
  
  @BeforeEach
  public void setUp() {
    factory = new UnitFactory(mockFactoryHelper);
    when(mockFactoryHelper.makePS2000()).thenReturn(mockUnitPS2000);
    when(mockUnitPS2000.getUnitSeries()).thenReturn(UnitSeries.PICOSCOPE2000);
  }
  
  /**
   * Test of getUnit method, of class UnitFactory.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testGetUnit() throws Exception {
    for(UnitSeries us : UnitSeries.values()) {
      if(us == UnitSeries.PICOSCOPE2000) {
        PicoUnit unit = factory.getUnit(us);
        assertEquals(UnitSeries.PICOSCOPE2000, unit.getUnitSeries());
      } else {
        assertThrows(NotSupportedException.class, () -> {
          factory.getUnit(us);
        });
      }
    }
    
    assertThrows(NotSupportedException.class, () -> {
      factory.getUnit(null);
    });
  }
  
  /**
   * Test the Constructor, of class UnitFactory.
   */
  @Test
  public void testConstructor() {
    assertDoesNotThrow(() -> {
      UnitFactory uf = new UnitFactory();
    });
  }
}
