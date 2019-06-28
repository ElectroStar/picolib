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

import java.util.Objects;

/**
 * Channel Settings Object.
 * Contains all parameters for one channel.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class ChannelSettings {

  private Range range;
  private Coupling coupling;
  private boolean enabled;
  private float analogOffset;

  /**
   * Create Channel Settings.
   * @param range the {@link Range} for the Channel.
   * @param coupling of the Channel. See {@link Coupling}.
   * @param enabled {@code true} to active the channel and {@code false} to deactivate.
   * @param analogOffset the value of the analog offset.
   */
  public ChannelSettings(Range range, Coupling coupling, boolean enabled, float analogOffset) {
    this.range = range;
    this.coupling = coupling;
    this.enabled = enabled;
    this.analogOffset = analogOffset;
  }

  /**
   * Create Channel Settings.
   * @param range the {@link Range} for the Channel.
   * @param coupling of the Channel. See {@link Coupling}.
   * @param enabled {@code true} to active the channel and {@code false} to deactivate.
   */
  public ChannelSettings(Range range, Coupling coupling, boolean enabled) {
    this(range, coupling, enabled, 0f);
  }

  /**
   * Create Default Channel Settings.
   * With a Range of +- 1V, DC coupling, disabled and zero offset.
   */
  public ChannelSettings() {
    this(Range.RANGE_1V, Coupling.DC, false, 0f);
  }

  /**
   * Copy Constructor.
   * @param s ChannelSettings to Copy
   */
  public ChannelSettings(ChannelSettings s) {
    this(s.range, s.coupling, s.enabled, s.analogOffset);
  }

  /**
   * Get the current {@link Range} for the Channel. 
   * @return the {@link Range}
   */
  public Range getRange() {
    return range;
  }

  /**
   * Set a new {@link Range} for the Channel.
   * @param range the new {@link Range}.
   */
  public void setRange(Range range) {
    this.range = range;
  }

  /**
   * Get the current {@link Coupling} for the Channel. 
   * @return the {@link Coupling}
   */
  public Coupling getCoupling() {
    return coupling;
  }

  /**
   * Set a new {@link Coupling} mode for the Channel.
   * @param coupling mode for the channel.
   */
  public void setCoupling(Coupling coupling) {
    this.coupling = coupling;
  }

  /**
   * Get the current Channel Status.
   * @return {@code true} if the channel is active, otherwise {@code false}.
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Set the new Status of the Channel.
   * @param enabled {@code true} to activate the channel, {@code false} to deactivate.
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Get the current analog offset of the Channel.
   * @return analog offset.
   */
  public float getAnalogOffset() {
    return analogOffset;
  }

  /**
   * Set the new analog offset of the Channel.
   * @param analogOffset of the channel.
   */
  public void setAnalogOffset(float analogOffset) {
    this.analogOffset = analogOffset;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + Objects.hashCode(this.range);
    hash = 79 * hash + Objects.hashCode(this.coupling);
    hash = 79 * hash + (this.enabled ? 1 : 0);
    hash = 79 * hash + Float.floatToIntBits(this.analogOffset);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ChannelSettings other = (ChannelSettings) obj;
    if (this.enabled != other.enabled) {
      return false;
    }
    if (Float.floatToIntBits(this.analogOffset) != Float.floatToIntBits(other.analogOffset)) {
      return false;
    }
    if (this.range != other.range) {
      return false;
    }
    return this.coupling == other.coupling;
  }

  @Override
  public String toString() {
    return "ChannelSettings{" + "range=" + range + ", coupling=" + coupling + ", enabled=" 
            + enabled + ", analogOffset=" + analogOffset + '}';
  }
}
