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

import java.util.Arrays;
import java.util.Objects;

/**
 * The {@code ResultSet} class contains all sample data for a result.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class ResultSet {

  private TimeUnit timeUnit;
  private int divisions;
  private CollectionTime collectionTime;
  private int numberOfSamples;
  private int[] times;
  private float[] channelA;
  private float[] channelB;
  private float[] channelC;
  private float[] channelD;

  /**
   * Create empty ResultSet.
   */
  public ResultSet() {
  }

  /**
   * Create ResultSet with base information.
   * 
   * @param timeUnit the {@link TimeUnit} in which the times are stored.
   * @param divisions the number of divisions.
   * @param collectionTime the {@link CollectionTime} for one division in which the sample data 
   *                       where collected.
   */
  public ResultSet(TimeUnit timeUnit, int divisions, CollectionTime collectionTime) {
    this.timeUnit = timeUnit;
    this.divisions = divisions;
    this.collectionTime = collectionTime;
  }

  /**
   * Gets the {@link TimeUnit} of the sampling process.
   * 
   * @return the {@link TimeUnit}
   */
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  /**
   * Sets the {@link TimeUnit} of the sampling process.
   * 
   * @param timeUnit the new {@link TimeUnit}.
   */
  public void setTimeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  /**
   * Gets the number of divisions of the sampling process.
   * 
   * @return number of divisions.
   */
  public int getDivisions() {
    return divisions;
  }

  /**
   * Sets the number of divisions of the sampling process.
   * 
   * @param divisions the new number of divisions.
   */
  public void setDivisions(int divisions) {
    this.divisions = divisions;
  }

  /**
   * Gets the {@link CollectionTime} of the sampling process.
   * 
   * @return {@link CollectionTime} of sampling process.
   */
  public CollectionTime getCollectionTime() {
    return collectionTime;
  }

  /**
   * Sets the {@link CollectionTime} of the sampling process.
   * 
   * @param collectionTime the new {@link CollectionTime}.
   */
  public void setCollectionTime(CollectionTime collectionTime) {
    this.collectionTime = collectionTime;
  }

  /**
   * Gets the time values for the channels of the sampling process.
   * 
   * @return time values.
   */
  public int[] getTimes() {
    return times;
  }

  /**
   * Sets the time values for the channels of the sampling process.
   * 
   * @param times the new time values.
   */
  public void setTimes(int[] times) {
    this.times = times;
  }

  /**
   * Gets the samples for Channel A of the sampling process.
   * 
   * @return channel A samples.
   */
  public float[] getChannelA() {
    return channelA;
  }

  /**
   * Sets the samples for Channel A of the sampling process.
   * 
   * @param channelA the new samples.
   */
  public void setChannelA(float[] channelA) {
    this.channelA = channelA;
  }

  /**
   * Gets the samples for Channel B of the sampling process.
   * 
   * @return channel B samples.
   */
  public float[] getChannelB() {
    return channelB;
  }

  /**
   * Sets the samples for Channel B of the sampling process.
   * 
   * @param channelB the new samples.
   */
  public void setChannelB(float[] channelB) {
    this.channelB = channelB;
  }

  /**
   * Gets the samples for Channel C of the sampling process.
   * 
   * @return channel C samples.
   */
  public float[] getChannelC() {
    return channelC;
  }

  /**
   * Sets the samples for Channel C of the sampling process.
   * 
   * @param channelC the new samples.
   */
  public void setChannelC(float[] channelC) {
    this.channelC = channelC;
  }

  /**
   * Gets the samples for Channel D of the sampling process.
   * 
   * @return channel D samples.
   */
  public float[] getChannelD() {
    return channelD;
  }

  /**
   * Sets the samples for Channel D of the sampling process.
   * 
   * @param channelD the new samples.
   */
  public void setChannelD(float[] channelD) {
    this.channelD = channelD;
  }

  /**
   * Gets the number of samples of the sampling process.
   * 
   * @return the number of samples.
   */
  public int getNumberOfSamples() {
    return numberOfSamples;
  }

  /**
   * Sets the number of samples of the sampling process.
   * 
   * @param numberOfSamples the new number of samples.
   */
  public void setNumberOfSamples(int numberOfSamples) {
    this.numberOfSamples = numberOfSamples;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.timeUnit);
    hash = 23 * hash + this.divisions;
    hash = 23 * hash + Objects.hashCode(this.collectionTime);
    hash = 23 * hash + this.numberOfSamples;
    hash = 23 * hash + Arrays.hashCode(this.times);
    hash = 23 * hash + Arrays.hashCode(this.channelA);
    hash = 23 * hash + Arrays.hashCode(this.channelB);
    hash = 23 * hash + Arrays.hashCode(this.channelC);
    hash = 23 * hash + Arrays.hashCode(this.channelD);
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
    final ResultSet other = (ResultSet) obj;
    if (this.divisions != other.divisions) {
      return false;
    }
    if (this.numberOfSamples != other.numberOfSamples) {
      return false;
    }
    if (this.timeUnit != other.timeUnit) {
      return false;
    }
    if (this.collectionTime != other.collectionTime) {
      return false;
    }
    if (!Arrays.equals(this.times, other.times)) {
      return false;
    }
    if (!Arrays.equals(this.channelA, other.channelA)) {
      return false;
    }
    if (!Arrays.equals(this.channelB, other.channelB)) {
      return false;
    }
    if (!Arrays.equals(this.channelC, other.channelC)) {
      return false;
    }
    return Arrays.equals(this.channelD, other.channelD);
  }
}
