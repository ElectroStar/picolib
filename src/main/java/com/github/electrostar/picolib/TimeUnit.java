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
 * The Time Units used by the PicoScope.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum TimeUnit {

  /**
   * Femtosecond (fs).
   */
  FEMTOSECOND(0, "fs"),

  /**
   * Picosecond (ps).
   */
  PICOSECOND(1, "ps"),

  /**
   * Nanosecond (ns).
   */
  NANOSECOND(2, "ns"),

  /**
   * Microsecond (µs).
   */
  MICROSECOND(3, "µs"),

  /**
   * Millisecond (ms).
   */
  MILLISECOND(4, "ms"),

  /**
   * Second (s).
   */
  SECOND(5, "s");

  private final int id;
  private final String symbol;

  TimeUnit(int id, String symbol) {
    this.id = id;
    this.symbol = symbol;
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
   * Gets the Symbol.
   * <p>
   * Example:
   * </p>
   * {@code ms} or {@code µs}
   * @return the symbol.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Finds a {@link TimeUnit} by Identifier.
   * 
   * @param id the identifier.
   * @return {@link TimeUnit} if one where found, otherwise {@code null}.
   */
  public static TimeUnit findById(int id) {
    if (id < 0) {
      return null;
    }

    for (TimeUnit u : values()) {
      if (u.getId() == id) {
        return u;
      }
    }
    return null;
  }
}
