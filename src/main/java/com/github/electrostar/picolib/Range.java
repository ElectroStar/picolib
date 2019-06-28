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
 * Voltage Range for a Channel.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum Range {

  /**
   * Voltage Range from +10 mV to -10 mV.
   */
  RANGE_10MV(0, 10),

  /**
   * Voltage Range from +20 mV to -20 mV.
   */
  RANGE_20MV(1, 20),

  /**
   * Voltage Range from +50 mV to -50 mV.
   */
  RANGE_50MV(2, 50),

  /**
   * Voltage Range from +100 mV to -100 mV.
   */
  RANGE_100MV(3, 100),

  /**
   * Voltage Range from +200 mV to -200 mV.
   */
  RANGE_200MV(4, 200),

  /**
   * Voltage Range from +500 mV to -500 mV.
   */
  RANGE_500MV(5, 500),

  /**
   * Voltage Range from +1 V to -1 V.
   */
  RANGE_1V(6, 1000),

  /**
   * Voltage Range from +2 V to -2 V.
   */
  RANGE_2V(7, 2000),

  /**
   * Voltage Range from +5 V to -5 V.
   */
  RANGE_5V(8, 5000),

  /**
   * Voltage Range from +10 V to -10 V.
   */
  RANGE_10V(9, 10000),

  /**
   * Voltage Range from +20 V to -20 V.
   */
  RANGE_20V(10, 20000),

  /**
   * Voltage Range from +50 V to -50 V.
   */
  RANGE_50V(11, 50000);

  private final int id;
  private final int value;

  Range(int id, int value) {
    this.id = id;
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value >= 1000 ? (value / 1000) + " V" : value + " mV";
  }

  public int getId() {
    return id;
  }

  /**
   * Finds a {@link Range} by Voltage.
   * 
   * @param voltage the voltage in millivolt.
   * @return {@link Range} if one where found, otherwise {@code null}.
   */
  public static Range findByVoltage(int voltage) {
    for (Range r : values()) {
      if (r.getValue() == voltage) {
        return r;
      }
    }
    return null;
  }
}
