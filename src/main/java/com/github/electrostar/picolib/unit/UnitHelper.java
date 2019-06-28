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

import com.github.electrostar.picolib.UnitInfo;

/**
 * {@code UnitHelper} is a class with some helper methods for a unit.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
class UnitHelper {

  /**
   * Checks if one variant is supported by one unit in form of its {@link UnitInfo}.
   * 
   * @param ui {@link UnitInfo} to check.
   * @param variants array of variant strings.
   * @return {@code true} if the {@code ui} supports one of the {@code variants}, 
   *         otherwise {@code false}.
   */
  public static boolean isSupported(UnitInfo ui, String[] variants) {
    if (null == ui || null == variants) {
      return false;
    }

    boolean found = false;
    for (String v : variants) {
      if (v.equals(ui.getVariantInfo())) {
        found = true;
        break;
      }
    }

    return found;
  }
}
