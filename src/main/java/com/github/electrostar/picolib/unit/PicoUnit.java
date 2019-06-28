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

package com.github.electrostar.picolib.unit;

import com.github.electrostar.picolib.Channel;
import com.github.electrostar.picolib.ChannelSettings;
import com.github.electrostar.picolib.EtsSettings;
import com.github.electrostar.picolib.GeneratorSettings;
import com.github.electrostar.picolib.OnDataCallback;
import com.github.electrostar.picolib.ResultSet;
import com.github.electrostar.picolib.Timebase;
import com.github.electrostar.picolib.TriggerSettings;
import com.github.electrostar.picolib.UnitInfo;
import com.github.electrostar.picolib.UnitSeries;
import com.github.electrostar.picolib.exception.ConfigurationException;
import com.github.electrostar.picolib.exception.NotSupportedException;
import com.github.electrostar.picolib.exception.PicoException;
import com.github.electrostar.picolib.exception.UnitNotFoundException;
import java.util.List;

/**
 * Interface of a PicoUnit.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public interface PicoUnit {

  /**
   * Gets the status if the unit is in streaming mode.
   * 
   * @return <code>true</code> if in streaming mode, <code>false</code> otherwise.
   */
  boolean isStreaming();

  /**
   * Gets the current {@link Timebase}.
   * @return the {@link Timebase}.
   */
  Timebase getTimebase();

  /**
   * Gets the {@link UnitSeries} of the current unit.
   * 
   * @return the {@link UnitSeries}.
   */
  UnitSeries getUnitSeries();

  /**
   * Gets the {@link List} of all current {@link ChannelSettings}.
   * 
   * @return the {@link List} of {@link ChannelSettings}.
   */
  List<ChannelSettings> getChannelSettings();

  /**
   * Gets the current {@link TriggerSettings}.
   * 
   * @return the {@link TriggerSettings}.
   */
  TriggerSettings getTriggerSettings();

  /**
   * Gets the current {@link GeneratorSettings}.
   * @return the {@link GeneratorSettings}.
   */
  GeneratorSettings getGeneratorSettings();

  /**
   * Setup the Channel.
   * 
   * @param channel the {@link Channel} to setup.
   * @param settings the {@link ChannelSettings} to configure.
   * @throws ConfigurationException if the configuration is not valid.
   * @throws NotSupportedException if this function is not supported by the unit.
   */
  void setChannel(Channel channel, ChannelSettings settings) throws ConfigurationException, 
          NotSupportedException;

  /**
   * Setup the Trigger.
   * 
   * @param settings the {@link TriggerSettings} to setup.
   * @throws ConfigurationException if the configuration is not valid.
   * @throws NotSupportedException if this function is not supported by the unit.
   */
  void setTrigger(TriggerSettings settings) throws ConfigurationException, NotSupportedException;

  /**
   * Setup the Signal Generator.
   * 
   * @param settings the {@link GeneratorSettings} to setup.
   * @throws ConfigurationException if the configuration is not valid.
   * @throws NotSupportedException if this function is not supported by the unit.
   */
  void setGenerator(GeneratorSettings settings) throws ConfigurationException, 
          NotSupportedException;

  /**
   * Open a unit.
   * 
   * @throws UnitNotFoundException if no unit was found.
   * @throws PicoException if function failed.
   */
  void open() throws UnitNotFoundException, PicoException;

  /**
   * Gets the open unit status.
   * 
   * @return {@code true} if the unit is open, otherwise {@code false}.
   */
  boolean isOpen();

  /**
   * Gets the {@link UnitInfo} of the current unit.
   * 
   * @return the {@link UnitInfo}.
   */
  UnitInfo getInfo();

  /**
   * Closes the current unit.
   */
  void close();

  /**
   * Stops the collection of sample data on the current unit.
   */
  void stop();

  /**
   * Gets the status if new sample data are ready to receive.
   * 
   * @return {@code true} if new sample data are ready to receive, otherwise {@code false}.
   * @throws PicoException if function failed.
   */
  boolean ready() throws PicoException;

  /**
   * Start block mode execution on the oscilloscope.
   * 
   * @throws ConfigurationException if the previous configuration is not valid.
   */
  void runBlock() throws ConfigurationException;

  /**
   * Gets the sample data and time values of last block mode execution.
   * 
   * @return {@code ResultSet} if sample data could received and converted, otherwise {@code null}.
   */
  ResultSet getTimesAndValues();

  /**
   * Start the streaming mode execution on the oscilloscope.
   * 
   * @throws ConfigurationException if the previous configuration is not valid.
   * @throws NotSupportedException if this function is not supported by the unit.
   */
  void runStreaming() throws ConfigurationException, NotSupportedException;

  /**
   * Register an application callback for new sample data arrived.
   * 
   * @param callback the application callback method.
   * @throws ConfigurationException if the previous configuration is not valid.
   */
  void registerCallback(OnDataCallback callback) throws ConfigurationException;

  /**
   * Setup the {@link Timebase}.
   * 
   * @param searchBase the {@link Timebase} informations to search a suitable Timebase.
   * @return {@code Timebase} if a suitable timebase was found, otherwise {@code null}.
   * @throws ConfigurationException if configuration failed due to PicoScope driver.
   */
  Timebase setTimebase(Timebase searchBase) throws ConfigurationException;

  /**
   * Setup the Equivalent Time Sampling (ETS).
   * 
   * @param settings the {@link EtsSettings} to setup.
   * @throws ConfigurationException if configuration failed due to PicoScope driver.
   * @throws NotSupportedException if this function is not supported by the unit.
   */
  void setEts(EtsSettings settings) throws ConfigurationException, NotSupportedException;

  /**
   * Gets the current {@link EtsSettings} of the unit.
   * @return the {@link EtsSettings}
   */
  EtsSettings getEtsSettings();
}
