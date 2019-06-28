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
 * Collection Time for one Division.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum CollectionTime {

  /**
   * 50 Nanoseconds per Division.
   */
  DIV50NS(50L, "50 ns/Div"),

  /**
   * 100 Nanoseconds per Division.
   */
  DIV100NS(100L, "100 ns/Div"),

  /**
   * 200 Nanoseconds per Division.
   */
  DIV200NS(200L, "200 ns/Div"),

  /**
   * 500 Nanoseconds per Division.
   */
  DIV500NS(500L, "500 ns/Div"),

  /**
   * 1 Microsecond per Division.
   */
  DIV1US(1000L, "1 us/Div"),

  /**
   * 2 Microseconds per Division.
   */
  DIV2US(2000L, "2 us/Div"),

  /**
   * 5 Microseconds per Division.
   */
  DIV5US(5000L, "5 us/Div"),

  /**
   * 10 Microseconds per Division.
   */
  DIV10US(10000L, "10 us/Div"),

  /**
   * 20 Microseconds per Division.
   */
  DIV20US(20000L, "20 us/Div"),

  /**
   * 50 Microseconds per Division.
   */
  DIV50US(50000L, "50 us/Div"),

  /**
   * 100 Microseconds per Division.
   */
  DIV100US(100000L, "100 us/Div"),

  /**
   * 200 Microseconds per Division.
   */
  DIV200US(200000L, "200 us/Div"),

  /**
   * 500 Microseconds per Division.
   */
  DIV500US(500000L, "500 us/Div"),

  /**
   * 1 Millisecond per Division.
   */
  DIV1MS(1000000L, "1 ms/Div"),

  /**
   * 2 Milliseconds per Division.
   */
  DIV2MS(2000000L, "2 ms/Div"),

  /**
   * 5 Milliseconds per Division.
   */
  DIV5MS(5000000L, "5 ms/Div"),

  /**
   * 10 Milliseconds per Division.
   */
  DIV10MS(10000000L, "10 ms/Div"),

  /**
   * 20 Milliseconds per Division.
   */
  DIV20MS(20000000L, "20 ms/Div"),

  /**
   * 50 Milliseconds per Division.
   */
  DIV50MS(50000000L, "50 ms/Div"),

  /**
   * 100 Milliseconds per Division.
   */
  DIV100MS(100000000L, "100 ms/Div"),

  /**
   * 200 Milliseconds per Division.
   */
  DIV200MS(200000000L, "200 ms/Div"),

  /**
   * 500 Milliseconds per Division.
   */
  DIV500MS(500000000L, "500 ms/Div"),

  /**
   * 1 Second per Division.
   */
  DIV1S(1000000000L, "1 s/Div"),

  /**
   * 2 Seconds per Division.
   */
  DIV2S(2000000000L, "2 s/Div"),

  /**
   * 5 Seconds per Division.
   */
  DIV5S(5000000000L, "5 s/Div"),

  /**
   * 10 Seconds per Division.
   */
  DIV10S(10000000000L, "10 s/Div"),

  /**
   * 20 Seconds per Division.
   */
  DIV20S(20000000000L, "20 s/Div"),

  /**
   * 50 Seconds per Division.
   */
  DIV50S(50000000000L, "50 s/Div"),

  /**
   * 100 Seconds per Division.
   */
  DIV100S(100000000000L, "100 s/Div"),

  /**
   * 200 Seconds per Division.
   */
  DIV200S(200000000000L, "200 s/Div"),

  /**
   * 500 Seconds per Division.
   */
  DIV500S(500000000000L, "500 s/Div"),

  /**
   * 1000 Seconds per Division.
   */
  DIV1000S(1000000000000L, "1000 s/Div"),

  /**
   * 2000 Seconds per Division.
   */
  DIV2000S(2000000000000L, "2000 s/Div"),

  /**
   * 5000 Seconds per Division.
   */
  DIV5000S(5000000000000L, "5000 s/Div");

  private final long divTime;
  private final String label;

  CollectionTime(long divTime, String label) {
    this.divTime = divTime;
    this.label = label;
  }

  /**
   * Gets the Time for one Division in nanoseconds.
   * 
   * @return the division time.
   */
  public long getDivisionTime() {
    return divTime;
  }

  /**
   * Finds a {@link CollectionTime} by Time.
   * 
   * @param time in nanoseconds.
   * @return {@link CollectionTime} if one where found, otherwise {@code null}.
   */
  public static CollectionTime findByTime(long time) {
    for (CollectionTime ct : values()) {
      if (ct.getDivisionTime() == time) {
        return ct;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.label;
  }

  /**
   * Gets the Label.
   * A label like {@code 50 ms/Div}
   * @return the label.
   */
  public String getLabel() {
    return label;
  }
}
