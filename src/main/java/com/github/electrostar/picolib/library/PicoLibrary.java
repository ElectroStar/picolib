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

package com.github.electrostar.picolib.library;

/**
 * Basic Library Interface for a Pico Technology PicoScope C-Library.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public interface PicoLibrary {

  /**
   * Maximum Value for the Voltage Range representated by the data type short.
   * 
   * @return the maximum value.
   */
  default int getMaxValue() {
    return 32767;
  }
  
  /**
   * Minimum Value for the Voltage Range representated by the data type short.
   * 
   * @return the minimum value.
   */
  default int getMinValue() {
    return -32767;
  }
  
  /**
   * The Value which indicated that the sample data is a lost data.
   * 
   * @return the lost data value.
   */
  default int getLostValue() {
    return -32768;
  }
}
