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
 * Channels that can be used.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 * @since 0.0.1
 */
public enum Channel {

  /**
   * Channel A or Channel 1.
   */
  CHANNEL_A(0, "Channel A"),

  /**
   * Channel B or Channel 2.
   */
  CHANNEL_B(1, "Channel B"),

  /**
   * Channel C or Channel 3.
   */
  CHANNEL_C(2, "Channel C"),

  /**
   * Channel D or Channel 4.
   */
  CHANNEL_D(3, "Channel D"),

  /**
   * External Channel.
   */
  EXTERNAL(4, "External"),

  /**
   * No Channel.
   */
  NONE(5, "None");

  private final int id;
  private final String label;

  Channel(int id, String label) {
    this.id = id;
    this.label = label;
  }

  /**
   * Returns the identification number.
   * 
   * @return identification number.
   */
  public int getId() {
    return id;
  }

  /**
   * Finds and Return the Channel by its character identifier.
   * Possible Values are:
   * <ul>
   * <li>A</li>
   * <li>B</li>
   * <li>C</li>
   * <li>D</li>
   * </ul>
   * The character is case insensitive so you can use a, b, c or d as well.
   * 
   * @param channel the channel identifier by a character.
   * @return {@link Channel} if the channel could be found, <code>null</code> otherwise.
   */
  public static Channel findByChar(char channel) {
    switch (Character.toLowerCase(channel)) {
      case 'a':
        return CHANNEL_A;
      case 'b':
        return CHANNEL_B;
      case 'c':
        return CHANNEL_C;
      case 'd':
        return CHANNEL_D;
      default:
        return null;
    }
  }

  /**
   * Finds and Return the Channel by its identifier number.
   * 
   * @param id the identification number.
   * @return {@link Channel} if the channel could be found, <code>null</code> otherwise.
   */
  public static Channel findById(int id) {
    for (Channel c : values()) {
      if (c.getId() == id) {
        return c;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.label;
  }

  /**
   * Returns the Channel Label.
   * 
   * @return the label.
   */
  public String getLabel() {
    return label;
  }
}
