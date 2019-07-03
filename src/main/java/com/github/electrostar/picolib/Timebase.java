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
 * The {@code Timebase} class contains all information about the time settings.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class Timebase {

  private CollectionTime collectionTime;
  private int divisions;
  private int samples;
  private int timeInterval;
  private TimeUnit timeUnit;
  private int maxSamples;
  private short internalTimebaseId;
  private short oversample;
  private int minSamples;

  /**
   * Create Timebase Setting.
   * <p>
   * Default 1 ms/Div, 10 Divisions, no oversample (1).
   * </p>
   */
  public Timebase() {
    this(CollectionTime.DIV1MS, 
            10, 
            (short) 1, 
            1);
  }

  /**
   * Create Timebase Setting.
   *
   * @param collectionTime the {@link CollectionTime} for one division.
   */
  public Timebase(CollectionTime collectionTime) {
    this(collectionTime, 
            10, 
            (short) 1, 
            1);
  }

  /**
   * Create Timebase Setting.
   *
   * @param collectionTime the {@link CollectionTime} for one division.
   * @param divisions the number of divisions.
   */
  public Timebase(CollectionTime collectionTime, int divisions) {
    this(collectionTime, 
            divisions, 
            (short) 1, 
            1);
  }

  /**
   * Create Timebase Setting.
   *
   * @param collectionTime the {@link CollectionTime} for one division.
   * @param divisions the number of divisions.
   * @param minSamples the number of the minimum needed samples per recorded {@link ResultSet}.
   */
  public Timebase(CollectionTime collectionTime, int divisions, int minSamples) {
    this(collectionTime,
            divisions, 
            (short) 1, 
            minSamples);
  }

  /**
   * Create Timebase Setting.
   *
   * @param collectionTime the {@link CollectionTime} for one division.
   * @param divisions the number of divisions.
   * @param oversample the number of oversamples.
   * @param minSamples the number of the minimum needed samples per recorded {@link ResultSet}.
   */
  public Timebase(CollectionTime collectionTime, int divisions, short oversample, int minSamples) {
    this.collectionTime = collectionTime;
    this.divisions = divisions;
    this.oversample = oversample;
    this.minSamples = minSamples;
    
    this.samples = 0;
    this.timeInterval = 0;
    this.timeUnit = TimeUnit.MICROSECOND;
    this.maxSamples = 0;
    this.internalTimebaseId = (short)0;
  }

  /**
   * Create Timebase Setting.
   *
   * @param collectionTime the {@link CollectionTime} for one division.
   * @param divisions the number of divisions.
   * @param oversample the number of oversamples.
   */
  public Timebase(CollectionTime collectionTime, int divisions, short oversample) {
    this(collectionTime, 
            divisions, 
            oversample, 
            1);
  }

  /**
   * Gets the {@link CollectionTime}.
   * 
   * @return the {@link CollectionTime}.
   */
  public CollectionTime getCollectionTime() {
    return collectionTime;
  }

  /**
   * Set the {@link CollectionTime}.
   * 
   * @param collectionTime the new {@link CollectionTime}.
   */
  public void setCollectionTime(CollectionTime collectionTime) {
    this.collectionTime = collectionTime;
  }

  /**
   * Gets the number of divisions.
   * 
   * @return the number of divisions.
   */
  public int getDivisions() {
    return divisions;
  }

  /**
   * Sets the number of divisions.
   * 
   * @param divisions the new number of divisions.
   */
  public void setDivisions(int divisions) {
    this.divisions = divisions;
  }

  /**
   * Gets the number of samples for one {@link ResultSet}.
   * When using {@link PicoScope#runStreaming()} the number may differ from the {@link ResultSet}.
   * 
   * @return the number of samples.
   */
  public int getSamples() {
    return samples;
  }

  /**
   * Sets the number of samples.
   * 
   * @param samples the new number of samples.
   */
  public void setSamples(int samples) {
    this.samples = samples;
  }

  /**
   * Gets the time interval between two samples.
   * 
   * @return the time interval between two samples.
   */
  public int getTimeInterval() {
    return timeInterval;
  }

  /**
   * Sets the time interval between two samples.
   * 
   * @param timeInterval the new time interval.
   */
  public void setTimeInterval(int timeInterval) {
    this.timeInterval = timeInterval;
  }

  /**
   * Gets the {@link TimeUnit}.
   * 
   * @return the {@link TimeUnit}.
   */
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  /**
   * Sets the {@link TimeUnit}.
   * 
   * @param timeUnit the new {@link TimeUnit}
   */
  public void setTimeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  /**
   * Gets the number of maximum samples.
   * 
   * @return maximum samples.
   */
  public int getMaxSamples() {
    return maxSamples;
  }

  /**
   * Sets the number of maximum samples.
   * 
   * @param maxSamples the new number of maximum samples.
   */
  public void setMaxSamples(int maxSamples) {
    this.maxSamples = maxSamples;
  }

  /**
   * Gets the internal Timebase number of the driver.
   * 
   * @return internal Timebase number.
   */
  public short getInternalTimebaseId() {
    return internalTimebaseId;
  }

  /**
   * Sets the internal Timebase number of the driver.
   * 
   * @param internalTimebaseId new timebase.
   */
  public void setInternalTimebaseId(short internalTimebaseId) {
    this.internalTimebaseId = internalTimebaseId;
  }

  /**
   * Gets the number of oversamples.
   * 
   * @return number of oversamples.
   */
  public short getOversample() {
    return oversample;
  }

  /**
   * Sets the number of oversamples.
   * 
   * @param oversample the new number of oversamples.
   */
  public void setOversample(short oversample) {
    this.oversample = oversample;
  }

  /**
   * Gets the number of the minimum samples that should be recored.
   * 
   * @return the minimum of samples.
   */
  public int getMinSamples() {
    return minSamples;
  }

  /**
   * Sets the number of the minium samples that should be recored.
   * 
   * @param minSamples the new minimum of samples.
   */
  public void setMinSamples(int minSamples) {
    this.minSamples = minSamples;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + Objects.hashCode(this.collectionTime);
    hash = 79 * hash + this.divisions;
    hash = 79 * hash + this.samples;
    hash = 79 * hash + this.timeInterval;
    hash = 79 * hash + Objects.hashCode(this.timeUnit);
    hash = 79 * hash + this.maxSamples;
    hash = 79 * hash + this.internalTimebaseId;
    hash = 79 * hash + this.oversample;
    hash = 79 * hash + this.minSamples;
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
    final Timebase other = (Timebase) obj;
    if (this.divisions != other.divisions) {
      return false;
    }
    if (this.samples != other.samples) {
      return false;
    }
    if (this.timeInterval != other.timeInterval) {
      return false;
    }
    if (this.maxSamples != other.maxSamples) {
      return false;
    }
    if (this.internalTimebaseId != other.internalTimebaseId) {
      return false;
    }
    if (this.oversample != other.oversample) {
      return false;
    }
    if (this.minSamples != other.minSamples) {
      return false;
    }
    if (this.collectionTime != other.collectionTime) {
      return false;
    }
    return this.timeUnit == other.timeUnit;
  }
  
  @Override
  public String toString() {
    return "Timebase{" + "collectionTime=" + collectionTime + ", divisions=" + divisions 
            + ", samples=" + samples + ", timeInterval=" + timeInterval + ", timeUnit=" + timeUnit 
            + ", maxSamples=" + maxSamples + ", internalTimebaseId=" + internalTimebaseId 
            + ", oversample=" + oversample + ", minSamples=" + minSamples + '}';
  }
}
