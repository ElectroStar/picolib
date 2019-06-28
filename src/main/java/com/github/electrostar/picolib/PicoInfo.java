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
 * Types of Informationen which a PicoScope can provide.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum PicoInfo {

  /**
   * The version number of the DLL used by the oscilloscope driver.
   * <p>
   * Example:
   * </p>
   * {@code "1, 0, 0, 2"}
   */
  DRIVER_VERSION(0),

  /**
   * The type of USB connection that is being used to connect the oscilloscope to the computer.
   * <p>
   * Example:
   * </p>
   * {@code "1.1"} or {@code "2.0"}
   */
  USB_VERSION(1),

  /**
   * The hardware version of the attached oscilloscope.
   * <p>
   * Example:
   * </p>
   * {@code "1"}
   */
  HARDWARE_VERSION(2),

  /**
   * The variant of PicoScope 2000 PC Oscilloscope that is attached to the computer.
   * <p>
   * Example:
   * </p>
   * {@code "2203"}
   */
  VARIANT_INFO(3),

  /**
   * The batch and serial number of the oscilloscope.
   * <p>
   * Example:
   * </p>
   * {@code "CMY66/052"}
   */
  BATCH_AND_SERIAL(4),

  /**
   * The calibration date of the oscilloscope.
   * <p>
   * Example:
   * </p>
   * {@code "14Jan08"}
   */
  CALIBRATION_DATE(5),

  /**
   * One of the Error Codes.
   * <p>
   * See {@link ErrorCode} to see all available Error Codes.
   * </p>
   * <p>
   * Example:
   * </p>
   * {@code "4"}
   */
  ERROR_CODE(6),

  /**
   * The version number of the kernel driver.
   * <p>
   * Example:
   * </p>
   * {@code "1,1,2,4"}
   */
  KERNEL_VERSION(7),

  /**
   * The path to the driver.
   */
  DRIVER_PATH(8);

  private final int id;

  PicoInfo(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
