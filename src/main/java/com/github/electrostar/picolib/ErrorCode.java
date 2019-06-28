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
 * PicoScope Error Codes.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum ErrorCode {

  /**
   * The oscilloscope is functioning correctly.
   */
  OK,

  /**
   * Attempts have been made to open more than the maximum number of allowed oscilloscopes.
   */
  MAX_UNITS_OPENED,

  /**
   * Not enough memory could be allocated onthe host machine.
   */
  MEM_FAIL,

  /**
   * An oscilloscope could not be found.
   */
  NOT_FOUND,

  /**
   * Unable to download firmware.
   */
  FW_FAIL,

  /**
   * The oscilloscope is not responding to commands from the PC.
   */
  NOT_RESPONDING,

  /**
   * The configuration information in the oscilloscope has become corrupt or is missing.
   */
  CONFIG_FAIL,

  /**
   * The operating system is not supported bythis driver.
   */
  OS_NOT_SUPPORTED;
  
  /**
   * Gets {@link ErrorCode} from Identifier Number.
   * 
   * @param x the idenifier.
   * @return {@link ErrorCode} if one where found.
   */
  public static ErrorCode fromShort(short x) {
    return ErrorCode.values()[x];
  }
}
