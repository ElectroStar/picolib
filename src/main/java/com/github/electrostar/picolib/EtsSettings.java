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
 * Equivalent Time Sampling (ETS) Settings.
 * Contains all parameters for ETS.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class EtsSettings {
  
  private EtsMode mode;
  private int cycles;
  private int interleaves;

  /**
   * Constructs a Equivalent Time Sampling (ETS) Settings instance with default values.
   * <p>
   * This means the ETS Mode is deactivated. 10 Cycles and 2 interleaves.
   * </p>
   */
  public EtsSettings() {
    this.mode = EtsMode.OFF;
    this.cycles = 10;
    this.interleaves = 2;
  }
  
  /**
   * Constructs a Equivalent Time Sampling (ETS) Settings instance.
   * 
   * @param mode the {@link EtsMode} for the Equivalent Time Sampling.
   * @param cycles the number of cycles to store.
   * @param interleaves the number of interleaves.
   */
  public EtsSettings(final EtsMode mode, final int cycles, final int interleaves) {
    this.mode = mode;
    this.cycles = cycles;
    this.interleaves = interleaves;
  }

  /**
   * Copy Constructor.
   * 
   * @param s the settings to copy.
   */
  public EtsSettings(EtsSettings s) {
    this.mode = s.mode;
    this.cycles = s.cycles;
    this.interleaves = s.interleaves;
  }
  
  /**
   * Equivalent Time Sampling Mode.
   *
   * @return the current {@link EtsMode}.
   */
  public EtsMode getMode() {
    return this.mode;
  }

  /**
   * Equivalent Time Sampling cyles.
   * The number of cycles to store. The computer can then select {@code interleave} cycles to give 
   * the most uniform spread of samples. {@code cycles} should be between two and five times the
   * value of {@code interleave}. 
   *
   * @return the current number of cycles.
   */
  public int getCycles() {
    return this.cycles;
  }

  /**
   * Equivalent Time Sampling interleaves.
   * the number of ETS interleaves to use. If the sample time is 20 ns and the interleave 10, 
   * the approximate time per sample will be 2 ns.
   *
   * @return the current number of interleaves.
   */
  public int getInterleaves() {
    return this.interleaves;
  }

  /**
   * Equivalent Time Sampling Mode.
   * 
   * @param mode the new {@link EtsMode} for the Equivalent Time Sampling.
   */
  public void setMode(final EtsMode mode) {
    this.mode = mode;
  }

  /**
   * Equivalent Time Sampling cyles.
   * The number of cycles to store. The computer can then select {@code interleave} cycles to give 
   * the most uniform spread of samples. {@code cycles} should be between two and five times the
   * value of {@code interleave}. 
   * 
   * @param cycles the new number of cycles to store.
   */
  public void setCycles(final int cycles) {
    this.cycles = cycles;
  }

  /**
   * Equivalent Time Sampling interleaves.
   * the number of ETS interleaves to use. If the sample time is 20 ns and the interleave 10, 
   * the approximate time per sample will be 2 ns.
   * 
   * @param interleaves the new number of interleaves.
   */
  public void setInterleaves(final int interleaves) {
    this.interleaves = interleaves;
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
    final EtsSettings other = (EtsSettings) obj;
    if (this.cycles != other.cycles) {
      return false;
    }
    if (this.interleaves != other.interleaves) {
      return false;
    }
    return this.mode == other.mode;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 13 * hash + Objects.hashCode(this.mode);
    hash = 13 * hash + this.cycles;
    hash = 13 * hash + this.interleaves;
    return hash;
  }
  
  @Override
  public String toString() {
    return "EtsSettings(mode=" + this.getMode() + ", cycles=" + this.getCycles() 
            + ", interleaves=" + this.getInterleaves() + ")";
  }
}