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
 * Trigger Direction for a Channel.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public enum TriggerDirection {

  /**
   * Trigger on Rising Edge.
   */
  RISING(0),

  /**
   * Trigger on Falling Edge.
   */
  FALLING(1);

  private final int id;

  TriggerDirection(int id) {
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
   * Finds a {@link TriggerDirection} by Character.
   * 
   * @param direction the direction idicator as char. {@code r} or {@code f} are suitable options.
   *                  the character is case insensitiv.
   * @return {@link TriggerDirection} if one where found, otherwise {@code null}.
   */
  public static TriggerDirection findByChar(char direction) {
    switch (Character.toLowerCase(direction)) {
      case 'r':
        return RISING;
      case 'f':
        return FALLING;
      default:
        return null;
    }
  }
}
