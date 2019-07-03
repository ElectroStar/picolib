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
import java.util.List;

/**
 * The Unit Series of the Pico Technology PicoScopes.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum UnitSeries {

  /**
   * PicoScope 2000 series PC Oscilloscope.
   */
  PICOSCOPE2000("PicoScope 2000 series PC Oscilloscope", "2000", 
          new int[]{0x1007, 0x1200}),

  /**
   * PicoScope 2000A series PC Oscilloscope.
   */
  PICOSCOPE2000A("PicoScope 2000A series PC Oscilloscope", "2000A", 
          new int[]{0x1016}),

  /**
   * PicoScope 3000 series PC Oscilloscope.
   */
  PICOSCOPE3000("PicoScope 3000 series PC Oscilloscope", "3000", 
          new int[]{0x1001, 0x1201, 0x1211}),

  /**
   * PicoScope 3000A series PC Oscilloscope.
   */
  PICOSCOPE3000A("PicoScope 3000A series PC Oscilloscope", "3000A", 
          new int[]{0x1012}),

  /**
   * PicoScope 4000er series PC Oscilloscope.
   */
  PICOSCOPE4000("PicoScope 4000 series PC Oscilloscope", "4000", 
          new int[]{0x1009, 0x1202, 0x1212}),

  /**
   * PicoScope 4000A series PC Oscilloscope.
   */
  PICOSCOPE4000A("PicoScope 4000A series PC Oscilloscope", "4000A", 
          new int[]{0x1018}),

  /**
   * PicoScope 5000 series PC Oscilloscope.
   */
  PICOSCOPE5000("PicoScope 5000 series PC Oscilloscope", "5000", 
          new int[]{0x1008, 0x1203}),

  /**
   * PicoScope 6000 series PC Oscilloscope.
   */
  PICOSCOPE6000("PicoScope 6000 series PC Oscilloscope", "6000", 
          new int[]{0x100e, 0x1204});

  private final String description;
  private final String label;
  private final List<Short> productIds;

  UnitSeries(String description, String label, int[] productIds) {
    this.description = description;
    this.label = label;
    this.productIds = new ArrayList<>();
    for (int val : productIds) {
      this.productIds.add((short) val);
    }
  }

  /**
   * Finds a {@link UnitSeries} by Label.
   * 
   * @param label the label to search for.
   * @return {@link UnitSeries} if one where found, otherwise {@code null}.
   */
  public static UnitSeries findByLabel(String label) {
    if (null == label || label.isEmpty()) {
      return null;
    }

    for (UnitSeries us : values()) {
      if (label.equals(us.getLabel())) {
        return us;
      }
    }

    return null;
  }

  /**
   * Gets the label.
   * 
   * @return the label.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Gets the description.
   * 
   * @return the description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the USB Product Ids.
   * 
   * @return product ids.
   */
  public List<Short> getProductIds() {
    return productIds;
  }

  @Override
  public String toString() {
    return description;
  }
}
