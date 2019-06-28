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

/**
 * Equivalent Time Sampling (ETS) Mode for PicoScope.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum EtsMode {

  /**
   * Disables ETS.
   */
  OFF(0),

  /**
   * Enables ETS in Fast Mode.
   */
  FAST(1),

  /**
   * Enables ETS in Slow Mode.
   * Takes longer to provide each data set, but the data sets are more stable and unique than in
   * {@link #FAST} Mode.
   */
  SLOW(2);

  private final int id;

  EtsMode(int id) {
    this.id = id;
  }

  /**
   * Gets the Internal Id.
   * Gets the internal id for the PicoScope Driver.
   * 
   * @return the Identifier.
   */
  public int getId() {
    return id;
  }

  /**
   * Finds a EtsMode by Identifier.
   * 
   * @param id the idenifier.
   * @return {@link EtsMode} if one where found, otherwise {@code null}.
   */
  public static EtsMode findById(int id) {
    for (EtsMode c : values()) {
      if (c.getId() == id) {
        return c;
      }
    }
    return null;
  }
}
