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
 * Wave Types for the Signal Generator.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum WaveType {

  /**
   * Sine.
   */
  SINE(0, "Sine"),

  /**
   * Square.
   */
  SQUARE(1, "Square"),

  /**
   * Triangle.
   */
  TRIANGLE(2, "Triangle"),

  /**
   * Ramp up.
   */
  RAMPUP(3, "RampUp"),

  /**
   * Ramp down.
   */
  RAMPDOWN(4, "RampDown"),

  /**
   * DC Voltage.
   */
  DC_VOLTAGE(5, "DCVoltage"),

  /**
   * Gaussian.
   */
  GAUSSIAN(6, "Gaussian"),

  /**
   * Cardinal Sine.
   */
  SINC(7, "Sinc"),

  /**
   * Half Sine.
   */
  HALF_SINE(8, "HalfSine");

  private final int id;
  private final String label;

  WaveType(int id, String label) {
    this.label = label;
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
   * Gets the Label.
   * <p>
   * Example:
   * </p>
   * {@code RampUp} or {@code DCVoltage}
   * 
   * @return the label.
   */
  public String getLabel() {
    return label;
  }

  @Override
  public String toString() {
    return label;
  }

  /**
   * Finds a {@link WaveType} by Label.
   * 
   * @param label the label to search with. {@code label} is case insensitive.
   * @return {@link WaveType} if one where found, otherwise {@code null}.
   */
  public static WaveType findByLabel(String label) {
    if (null == label || label.isEmpty()) {
      return null;
    }

    for (WaveType wt : values()) {
      if (label.equalsIgnoreCase(wt.getLabel())) {
        return wt;
      }
    }
    return null;
  }
}
