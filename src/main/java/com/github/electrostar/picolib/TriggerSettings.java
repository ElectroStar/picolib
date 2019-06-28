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
 * The {@code TriggerSettings} class contains all parameter for the Trigger.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class TriggerSettings {

  private Channel channel;
  private TriggerDirection direction;
  private float threshold;
  private float delay;
  private short autoTigger;

  /**
   * Create Trigger Settings.
   * 
   * @param channel the {@link Channel} on which the trigger should work. Use {@link Channel#NONE} 
   *                to deactivate the trigger.
   * @param direction the {@link TriggerDirection triggering direction} of the signal.
   * @param threshold the threshold of the trigger in millivolt.
   * @param delay of sampling process. Values from 0 to 100 are allowed. 0 means first sample is
   *              trigger sample and 50 means tigger is in the middle of the block.
   * @param autoTigger is the value in milliseconds after that the device will start sampling if
   *                   no trigger occurs. Zero means oscilloscope will wait indefinite for the
   *                   trigger.
   */
  public TriggerSettings(Channel channel, 
          TriggerDirection direction, 
          float threshold, 
          float delay, 
          short autoTigger) {
    this.channel = channel;
    this.direction = direction;
    this.threshold = threshold;
    this.delay = delay;
    this.autoTigger = autoTigger;
  }

  /**
   * Create Trigger Settings.
   *
   * @param channel the {@link Channel} on which the trigger should work. Use {@link Channel#NONE} 
   *                to deactivate the trigger.
   * @param direction the {@link TriggerDirection triggering direction} of the signal.
   * @param threshold the threshold of the trigger in millivolt.
   * @param delay of sampling process. Values from 0 to 100 are allowed. 0 means first sample is
   *              trigger sample and 50 means tigger is in the middle of the block.
   */
  public TriggerSettings(Channel channel, 
          TriggerDirection direction, 
          float threshold, 
          float delay) {
    this(channel, direction, threshold, delay, (short) 0);
  }

  /**
   * Create Trigger Settings.
   * 
   * @param channel the {@link Channel} on which the trigger should work. Use {@link Channel#NONE} 
   *                to deactivate the trigger.
   * @param direction the {@link TriggerDirection triggering direction} of the signal.
   * @param threshold the threshold of the trigger in millivolt.
   */
  public TriggerSettings(Channel channel, 
          TriggerDirection direction, 
          float threshold) {
    this(channel, direction, threshold, 0f, (short) 0);
  }

  /**
   * Create Trigger Settings.
   * <p>
   * Default is deactivated.
   * </p>
   */
  public TriggerSettings() {
    this(Channel.NONE, TriggerDirection.RISING, 0f, 0f, (short) 0);
  }

  /**
   * Copy Constructor.
   * 
   * @param s the settings to copy.
   */
  public TriggerSettings(TriggerSettings s) {
    this(s.channel, s.direction, s.threshold, s.delay, s.autoTigger);
  }

  /**
   * Gets the {@link Channel}.
   * 
   * @return the {@link Channel}.
   */
  public Channel getChannel() {
    return channel;
  }

  /**
   * Sets the {@link Channel} of the Trigger.
   * 
   * @param channel the new {@link Channel}.
   */
  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  /**
   * Gets the {@link TriggerDirection triggering direction} of the signal.
   * 
   * @return the {@link TriggerDirection triggering direction}.
   */
  public TriggerDirection getDirection() {
    return direction;
  }

  /**
   * Sets the {@link TriggerDirection triggering direction} of the signal.
   * @param direction the new {@link TriggerDirection triggering direction}.
   */
  public void setDirection(TriggerDirection direction) {
    this.direction = direction;
  }

  /**
   * Gets the trigger threshold.
   * 
   * @return the threshold in millivolt.
   */
  public float getThreshold() {
    return threshold;
  }

  /**
   * Sets the trigger threshold.
   * 
   * @param threshold the new threshold in millivolt.
   */
  public void setThreshold(float threshold) {
    this.threshold = threshold;
  }

  /**
   * Gets the Trigger Delay.
   * 
   * @return trigger delay in percentage.
   */
  public float getDelay() {
    return delay;
  }

  /**
   * Sets the Trigger Delay.
   * 
   * @param delay the new trigger delay in percentrage. Values from 0 to 100 are allowed. 0 means 
   *              first sample is trigger sample and 50 means tigger is in the middle of the block.
   */
  public void setDelay(float delay) {
    this.delay = delay;
  }

  /**
   * Gets the auto trigger time.
   * 
   * @return auto trigger time in milliseconds.
   */
  public short getAutoTigger() {
    return autoTigger;
  }

  /**
   * Sets the auto trigger time.
   * 
   * @param autoTigger is the value in milliseconds after that the device will start sampling if
   *                   no trigger occurs. Zero means oscilloscope will wait indefinite for the
   *                   trigger.
   */
  public void setAutoTigger(short autoTigger) {
    this.autoTigger = autoTigger;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 59 * hash + Objects.hashCode(this.channel);
    hash = 59 * hash + Objects.hashCode(this.direction);
    hash = 59 * hash + Float.floatToIntBits(this.threshold);
    hash = 59 * hash + Float.floatToIntBits(this.delay);
    hash = 59 * hash + this.autoTigger;
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
    final TriggerSettings other = (TriggerSettings) obj;
    if (Float.floatToIntBits(this.threshold) != Float.floatToIntBits(other.threshold)) {
      return false;
    }
    if (Float.floatToIntBits(this.delay) != Float.floatToIntBits(other.delay)) {
      return false;
    }
    if (this.autoTigger != other.autoTigger) {
      return false;
    }
    if (this.channel != other.channel) {
      return false;
    }
    return this.direction == other.direction;
  }

  @Override
  public String toString() {
    return "TriggerSettings{" + "channel=" + channel + ", direction=" + direction 
            + ", threshold=" + threshold + ", delay=" + delay + ", autoTigger=" + autoTigger + '}';
  }
}
