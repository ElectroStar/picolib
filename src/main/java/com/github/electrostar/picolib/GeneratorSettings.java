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
 * Generator Settings.
 * Contains all parameters for the build in Signal Generator.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class GeneratorSettings {

  private WaveType waveType;
  private double startFrequency;
  private double amplitude;
  private double offset;
  private SweepType sweepType;
  private int sweeps;
  private double stopFrequency;
  private double increment;
  private double dwellTime;

  /**
   * Create Default Generator Settings.
   * <p>
   * This means the Generator is deactivated.
   * </p>
   */
  public GeneratorSettings() {
    this(WaveType.SINE, 0, 0, 0, SweepType.UP, 0, 0, 0, 0);
  }

  /**
   * Copy Consturctor.
   * 
   * @param s the Settings to copy.
   */
  public GeneratorSettings(GeneratorSettings s) {
    this(s.waveType, 
            s.startFrequency, 
            s.amplitude, 
            s.offset, 
            s.sweepType, 
            s.sweeps, 
            s.stopFrequency, 
            s.increment, 
            s.dwellTime);
  }

  /**
   * Create Generator Settings.
   * 
   * @param waveType  the type of wave of the outcomming signal.
   * @param startFrequency the frequency of the wave in Hertz.
   * @param amplitude the amplitude of the signal in volt.
   */
  public GeneratorSettings(WaveType waveType, double startFrequency, double amplitude) {
    this(waveType, startFrequency, amplitude, 0, SweepType.UP, 0, startFrequency, 0, 0);
  }

  /**
   * Create Generator Settings.
   * 
   * @param waveType  the type of wave of the outcomming signal.
   * @param startFrequency the frequency of the wave in Hertz.
   * @param amplitude the amplitude of the signal in volt.
   * @param offset    the offset of the wave signal.
   */
  public GeneratorSettings(WaveType waveType, 
          double startFrequency, 
          double amplitude, 
          double offset) {
    this(waveType, startFrequency, amplitude, offset, SweepType.UP, 0, startFrequency, 0, 0);
  }

  /**
   * Create Generator Settings.
   * 
   * @param waveType       the type of wave of the outcomming signal.
   * @param startFrequency the starting frequency of the wave in Hertz.
   * @param amplitude      the amplitude of the signal in volt.
   * @param offset         the offset of the wave signal.
   * @param sweepType      the type of the sweep of the outcoming signal.
   * @param sweeps         the number of sweeps in Hertz.
   * @param stopFrequency  the stopping frequency of the wave in Hertz.
   * @param increment      the amount by which the frequency rises or falls every {@code dwellTime}
   *                       seconds in sweep mode
   * @param dwellTime      the dwell time of an increment step in Seconds.
   */
  public GeneratorSettings(WaveType waveType, 
          double startFrequency, 
          double amplitude, 
          double offset, 
          SweepType sweepType, 
          int sweeps, 
          double stopFrequency, 
          double increment, 
          double dwellTime) {
    this.waveType = waveType;
    this.startFrequency = startFrequency;
    this.amplitude = amplitude;
    this.offset = offset;
    this.sweepType = sweepType;
    this.sweeps = sweeps;
    this.stopFrequency = stopFrequency;
    this.increment = increment;
    this.dwellTime = dwellTime;
  }

  /**
   * Gets the {@link WaveType} of the Generator Signal.
   * 
   * @return the type of the wave.
   */
  public WaveType getWaveType() {
    return waveType;
  }

  /**
   * Sets the new {@link WaveType} of the Generator Signal.
   * 
   * @param waveType the new type of wave.
   */
  public void setWaveType(WaveType waveType) {
    this.waveType = waveType;
  }

  /**
   * Gets the starting frequency of the Generator Signal.
   * 
   * @return the starting frquency in Hertz.
   */
  public double getStartFrequency() {
    return startFrequency;
  }

  /**
   * Sets the new starting frquency of the Generator Signal.
   * 
   * @param startFrequency the new starting frequency in Hertz.
   */
  public void setStartFrequency(double startFrequency) {
    this.startFrequency = startFrequency;
  }

  /**
   * Gets the amplitude of the Generator Signal.
   * 
   * @return the signal amplitude in Volt.
   */
  public double getAmplitude() {
    return amplitude;
  }

  /**
   * Sets the signal amplitude of the Generator Signal.
   * 
   * @param amplitude the new amplitude in Volt.
   */
  public void setAmplitude(double amplitude) {
    this.amplitude = amplitude;
  }

  /**
   * Gets the offset of the Generator Signal.
   * 
   * @return the offset of the signal.
   */
  public double getOffset() {
    return offset;
  }

  /**
   * Sets the signal amplitude of the Generator Signal.
   * 
   * @param offset the new signal offset.
   */
  public void setOffset(double offset) {
    this.offset = offset;
  }

  /**
   * Gets the {@link SweepType} of the Generator Signal.
   * 
   * @return the {@link SweepType}.
   */
  public SweepType getSweepType() {
    return sweepType;
  }

  /**
   * Sets the {@link SweepType} of the Generator Signal.
   * 
   * @param sweepType the new {@link SweepType}.
   */
  public void setSweepType(SweepType sweepType) {
    this.sweepType = sweepType;
  }

  /**
   * Gets the number of sweeps of the Generator Signal.
   * 
   * @return the number of sweeps.
   */
  public int getSweeps() {
    return sweeps;
  }

  /**
   * Sets the number of sweeps of the Generator Signal.
   * 
   * @param sweeps the new number of sweeps.
   */
  public void setSweeps(int sweeps) {
    this.sweeps = sweeps;
  }

  /**
   * Gets the stopping frequency of the Generator Signal.
   * 
   * @return the stopping frequency in Hertz.
   */
  public double getStopFrequency() {
    return stopFrequency;
  }

  /**
   * Sets the stopping frequency of the Generator Signal.
   * 
   * @param stopFrequency the new stopping frequency in Hertz.
   */
  public void setStopFrequency(double stopFrequency) {
    this.stopFrequency = stopFrequency;
  }

  /**
   * Gets the number of increments of the Generator Signal.
   * 
   * @return the number of increments.
   */
  public double getIncrement() {
    return increment;
  }

  /**
   * Sets the number of increments of the Generator Signal.
   * 
   * @param increment the new number of increments.
   */
  public void setIncrement(double increment) {
    this.increment = increment;
  }

  /**
   * Gets the time between frequency changes in sweep mode.
   * 
   * @return the changing time in seconds.
   */
  public double getDwellTime() {
    return dwellTime;
  }

  /**
   * Sets the time between frequency changes in sweep mode.
   * 
   * @param dwellTime the new chaning time in seconds.
   */
  public void setDwellTime(double dwellTime) {
    this.dwellTime = dwellTime;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 73 * hash + Objects.hashCode(this.waveType);
    hash = 73 * hash + (int) (Double.doubleToLongBits(this.startFrequency) 
            ^ (Double.doubleToLongBits(this.startFrequency) >>> 32));
    hash = 73 * hash + (int) (Double.doubleToLongBits(this.amplitude) 
            ^ (Double.doubleToLongBits(this.amplitude) >>> 32));
    hash = 73 * hash + (int) (Double.doubleToLongBits(this.offset) 
            ^ (Double.doubleToLongBits(this.offset) >>> 32));
    hash = 73 * hash + Objects.hashCode(this.sweepType);
    hash = 73 * hash + this.sweeps;
    hash = 73 * hash + (int) (Double.doubleToLongBits(this.stopFrequency) 
            ^ (Double.doubleToLongBits(this.stopFrequency) >>> 32));
    hash = 73 * hash + (int) (Double.doubleToLongBits(this.increment) 
            ^ (Double.doubleToLongBits(this.increment) >>> 32));
    hash = 73 * hash + (int) (Double.doubleToLongBits(this.dwellTime) 
            ^ (Double.doubleToLongBits(this.dwellTime) >>> 32));
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
    final GeneratorSettings other = (GeneratorSettings) obj;
    if (Double.doubleToLongBits(this.startFrequency) 
            != Double.doubleToLongBits(other.startFrequency)) {
      return false;
    }
    if (Double.doubleToLongBits(this.amplitude) != Double.doubleToLongBits(other.amplitude)) {
      return false;
    }
    if (Double.doubleToLongBits(this.offset) != Double.doubleToLongBits(other.offset)) {
      return false;
    }
    if (this.sweeps != other.sweeps) {
      return false;
    }
    if (Double.doubleToLongBits(this.stopFrequency) 
            != Double.doubleToLongBits(other.stopFrequency)) {
      return false;
    }
    if (Double.doubleToLongBits(this.increment) != Double.doubleToLongBits(other.increment)) {
      return false;
    }
    if (Double.doubleToLongBits(this.dwellTime) != Double.doubleToLongBits(other.dwellTime)) {
      return false;
    }
    if (this.waveType != other.waveType) {
      return false;
    }
    return this.sweepType == other.sweepType;
  }

  @Override
  public String toString() {
    return "GeneratorSettings{" + "waveType=" + waveType + ", startFrequency=" + startFrequency 
            + ", amplitude=" + amplitude + ", offset=" + offset + ", sweepType=" + sweepType 
            + ", sweeps=" + sweeps + ", stopFrequency=" + stopFrequency + ", increment=" 
            + increment + ", dwellTime=" + dwellTime + '}';
  }
}
