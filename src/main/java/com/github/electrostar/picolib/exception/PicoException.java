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

package com.github.electrostar.picolib.exception;

import java.io.IOException;

/**
 * Throws when a PicoScope got an error.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class PicoException extends IOException {

  /**
   * Constructs a {@code PicoException} with no detail message.
   */
  public PicoException() {
    super();
  }

  /**
   * Constructs a {@code PicoException} with the specified detail message.
   * @param message the detail message.
   */
  public PicoException(String message) {
    super(message);
  }

  /**
   * Constructs a {@code PicoException} with the specified detail message and cause.
   * <p>
   * Note that the detail message associated with cause is not automatically incorporated in this 
   * pico exception's detail message.
   * </p>
   * @param message the detail message.
   * @param cause the cause.
   */
  public PicoException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a {@code PicoException} with the specified cause.
   * @param cause the cause.
   */
  public PicoException(Throwable cause) {
    super(cause);
  }
}
