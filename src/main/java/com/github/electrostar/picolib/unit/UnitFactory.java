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

package com.github.electrostar.picolib.unit;

import com.github.electrostar.picolib.UnitSeries;
import com.github.electrostar.picolib.exception.NotSupportedException;
import com.github.electrostar.picolib.library.PS2000CLibrary;

/**
 * The {@code UnitFactory} create PicoScope Driver Instrance based on the {@link UnitSeries}.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class UnitFactory {
  
  private final FactoryHelper helper;
  
  /**
   * Constructs a UnitFactory.
   * Used to create instances of Pico Units.
   */
  public UnitFactory() {
    this(new FactoryHelper());
  }

  /**
   * Internal Constructor for Testing.
   * 
   * @param helper internal Factory Helper to create Instances.
   */
  UnitFactory(FactoryHelper helper) {
    this.helper = helper;
  }
  
  /**
   * Creates a PicoScope instance for the given {@link UnitSeries}.
   * 
   * @param series the {@link UnitSeries} to create an instance of.
   * @return an instance of the {@link PicoUnit} like {@link PicoScope2000}.
   * @throws NotSupportedException if the {@link UnitSeries} is not supported.
   */
  public PicoUnit getUnit(UnitSeries series) throws NotSupportedException {
    PicoUnit unit = null;
    if (UnitSeries.PICOSCOPE2000 == series) {
      unit = helper.makePS2000();
    } else {
      throw new NotSupportedException("Unit Series is not supported by this SDK");
    }

    return unit;
  }
  
  static class FactoryHelper {
    PicoUnit makePS2000() {
      return new PicoScope2000(PS2000CLibrary.INSTANCE);
    }
  }
}
