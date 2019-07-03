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

import com.github.electrostar.picolib.exception.ConfigurationException;
import com.github.electrostar.picolib.exception.NotSupportedException;
import com.github.electrostar.picolib.exception.PicoException;
import com.github.electrostar.picolib.exception.UnitNotFoundException;
import com.github.electrostar.picolib.unit.PicoUnit;
import com.github.electrostar.picolib.unit.UnitFactory;

/**
 * The <code>PicoScope</code> Class represents an interface to a physical Pico Technology PicoScope.
 *
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class PicoScope implements AutoCloseable {

  private PicoUnit unit;
  private UnitFactory unitFactory;

  /**
   * Creates an instance to interacte with a PicoScope.
   */
  public PicoScope() {
    unitFactory = new UnitFactory();
  }

  /**
   * Creates an instance and tries to connect directly to a PicoScope whose type has been specified.
   * If no connection can be established, this constructor throws an exception.
   *
   * @param unitSeries {@link UnitSeries} of device you want to use.
   * @throws PicoException if no connection to the device can be established.
   * @throws NotSupportedException if the given {@link UnitSeries} is not supported.
   * @throws UnitNotFoundException if no device of the specifying {@link UnitSeries} is connected to
   *                               the computer.
   * @throws IllegalStateException if the current instance holds already a connection to an opened
   *                               PicoScope device or no {@link UnitFactory} is set.
   */
  public PicoScope(UnitSeries unitSeries) throws PicoException {
    this();
    
    openUnit(unitSeries); 
  }

  /**
   * Internal Constructor to pass a mocked UnitFactory.
   * 
   * @param unitFactory the {@link UnitFactory}
   */
  PicoScope(UnitFactory unitFactory) {
    this.unitFactory = unitFactory;
  }
  
  /**
   * Open a connection to a Pico Technology PicoScope.
   *
   * @param unitSeries {@link UnitSeries} of device you want to use.
   * @throws IllegalStateException if the current instance holds already a connection to an opened
   *                               PicoScope device or no {@link UnitFactory} is set.
   * @throws PicoException if no connection to the device can be established.
   * @throws NotSupportedException if the given {@link UnitSeries} is not supported.
   * @throws UnitNotFoundException if no device of the specifying {@link UnitSeries} is connected to
   *                               the computer.
   */
  public void open(UnitSeries unitSeries) throws PicoException {
    openUnit(unitSeries);
  }

  private void openUnit(UnitSeries unitSeries) throws PicoException {
    // Check for already open
    if (null != unit && unit.isOpen()) {
      throw new IllegalStateException("A Unit is already open for this instance.");
    }
    
    if (null == unitFactory) {
      throw new IllegalStateException("No Unit Factory set.");
    }

    // Try to generate a Handler for this unit Series
    this.unit = unitFactory.getUnit(unitSeries);

    // Open the Unit over the handler
    this.unit.open();
  }

  /**
   * Returns the {@link UnitSeries} of the connected device.
   *
   * @return the unit series of the device.
   * @throws IllegalStateException if instance is not opened.
   */
  public UnitSeries getUnitSeries() {
    checkUnit();

    return unit.getUnitSeries();
  }

  private void checkUnit() {
    if (null == unit) {
      throw new IllegalStateException("No Unit is given. Open a Unit Device first.");
    }
  }

  /**
   * Returns the {@link UnitInfo} of the connected device.
   *
   * @return the unit information of the device.
   * @throws IllegalStateException if instance is not opened.
   */
  public UnitInfo getInfo() {
    checkUnit();

    return unit.getInfo();
  }

  /**
   * Disable Channel.
   *
   * @param channel is a pysical Channel on the device.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if channel is not supported opened device.
   * @throws ConfigurationException if channel configuration was aborted.
   */
  public void disableChannel(Channel channel) throws NotSupportedException, ConfigurationException {

    checkUnit();

    unit.setChannel(channel, null);
  }

  /**
   * Configurate Channel.
   *
   * @param channel  is a pysical Channel on the device.
   * @param settings is a set of options which should be applied on the channel.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   * @throws IllegalArgumentException if a parameter is out of range.
   */
  public void setChannel(Channel channel, ChannelSettings settings) throws NotSupportedException, 
          ConfigurationException {

    checkUnit();

    unit.setChannel(channel, settings);
  }

  /**
   * Configurate Channel.
   *
   * @param channel  is a pysical Channel on the device.
   * @param coupling is the voltage coupling of the channel.
   * @param range    is the measuring voltage range of the channel.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   * @throws IllegalArgumentException if a parameter is out of range.
   */
  public void setChannel(Channel channel, Coupling coupling, Range range) 
          throws NotSupportedException, ConfigurationException {

    ChannelSettings settings = new ChannelSettings(range, coupling, true);

    setChannel(channel, settings);
  }

  /**
   * Configurate Channel.
   *
   * @param channel      is a pysical Channel on the device.
   * @param coupling     is the voltage coupling of the channel.
   * @param range        is the measuring voltage range of the channel.
   * @param analogOffset is a voltage to add to the input channel before digitization. In Volt.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   * @throws IllegalArgumentException if a parameter is out of range.
   */
  public void setChannel(Channel channel, Coupling coupling, Range range, float analogOffset)
          throws NotSupportedException, ConfigurationException {

    ChannelSettings settings = new ChannelSettings(range, coupling, true, analogOffset);

    setChannel(channel, settings);
  }

  /**
   * Configurate Channel.
   *
   * @param settings the settings for the trigger.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   * @throws IllegalArgumentException if a parameter is out of range.
   */
  public void setTrigger(TriggerSettings settings) throws NotSupportedException, 
          ConfigurationException {

    checkUnit();

    unit.setTrigger(settings);
  }

  /**
   * Set a Trigger.
   *
   * @param channel   the pysical channel on the device which should be triggered.
   * @param direction the signal direction on which the channel should be triggered.
   * @param threshold of the trigger in millivolt.
   * @throws IllegalStateException if instance is not opened.
   * @throws IllegalArgumentException if a parameter is out of range.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   */
  public void setTrigger(Channel channel, TriggerDirection direction, float threshold)
          throws NotSupportedException, ConfigurationException {

    TriggerSettings settings = new TriggerSettings(channel, direction, threshold);

    setTrigger(settings);
  }

  /**
   * Set a Trigger.
   *
   * @param channel   the pysical channel on the device which should be triggered.
   * @param direction the signal direction on which the channel should be triggered.
   * @param threshold the threshold of the trigger in millivolt.
   * @param delay     of sampling process. Values from 0 to 100 are allowed. 0 means first sample is
   *                  trigger sample and 50 means tigger is in the middle of the block.
   * @throws IllegalStateException if instance is not opened.
   * @throws IllegalArgumentException if a parameter is out of range.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   */
  public void setTrigger(Channel channel, TriggerDirection direction, float threshold, float delay)
          throws NotSupportedException, ConfigurationException {

    TriggerSettings settings = new TriggerSettings(channel, direction, threshold, delay);

    setTrigger(settings);
  }

  /**
   * Set a Trigger.
   *
   * @param channel       the pysical channel on the device which should be triggered.
   * @param direction     the signal direction on which the channel should be triggered.
   * @param threshold     of the trigger in millivolt.
   * @param delay         of sampling process. Values from 0 to 100 are allowed. 0 means first
   *                      sample is trigger sample and 50 means tigger is in the middle of the
   *                      block.
   * @param autoTriggerMs is the value in milliseconds after that the device will start sampling if
   *                      no trigger occurs. Zero means oscilloscope will wait indefinite for the
   *                      trigger.
   * @throws IllegalStateException if instance is not opened.
   * @throws IllegalArgumentException if a parameter is out of range.
   * @throws NotSupportedException if a feature is not supported by the device.
   * @throws ConfigurationException if a setting is out of range.
   */
  public void setTrigger(Channel channel, TriggerDirection direction, float threshold,
          float delay, short autoTriggerMs)
          throws NotSupportedException, ConfigurationException {

    setTrigger(new TriggerSettings(channel, direction, threshold, delay, autoTriggerMs));
  }

  /**
   * Stops the current block or streaming execution.
   *
   * @throws IllegalStateException if instance is not opened.
   */
  public void stop() throws IllegalStateException {
    checkUnit();

    unit.stop();
  }

  /**
   * Returns the current state of the block execution.
   *
   * @return <code>true</code> if the device has executed the block, otherwise <code>false</code>.
   * @throws IllegalStateException if instance is not opened.
   * @throws PicoException if no communication between the library and the device is possible.
   */
  public boolean ready() throws PicoException {
    checkUnit();

    return unit.ready();
  }

  /**
   * Sets the {@link CollectionTime} for the device.
   *
   * @param collectionTime the size of a time block for which there are a total of 10 blocks.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if no configuration could be find for this device for the
   *                                configurated channels and the desired {@link CollectionTime}
   */
  public void setTimebase(CollectionTime collectionTime) throws ConfigurationException {
    setTimebase(new Timebase(collectionTime));
  }

  /**
   * Sets the {@link CollectionTime} for the device.
   *
   * @param collectionTime the size of one time block.
   * @param divisions      the number of blocks. Must be a between 1 and 10.
   * @throws IllegalArgumentException if the division is out of range.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if no configuration could be find for this device for the
   *                                configurated channels and the desired {@link CollectionTime}
   */
  public void setTimebase(CollectionTime collectionTime, int divisions)
          throws ConfigurationException {
    setTimebase(new Timebase(collectionTime, divisions));
  }

  /**
   * Sets the {@link CollectionTime} for the device.
   *
   * @param collectionTime the size of one time block.
   * @param divisions      the number of blocks. Must be between 1 and 10.
   * @param oversample     the number of oversamples. Must be between 1 and 255.
   * @throws IllegalArgumentException if the division or oversample is out of range.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if no configuration could be find for this device for the
   *                                configurated channels and the desired {@link CollectionTime}
   */
  public void setTimebase(CollectionTime collectionTime, int divisions, short oversample)
          throws ConfigurationException {
    setTimebase(new Timebase(collectionTime, divisions, oversample));
  }

  /**
   * Sets the {@link CollectionTime} for the device.
   *
   * @param collectionTime the size of one time block.
   * @param divisions      the number of blocks. Must be between 1 and 10.
   * @param oversample     the number of oversamples. Must be between 1 and 255.
   * @param minSamples     the number of minium of data samples.
   * @throws IllegalArgumentException if the division, oversample or minimum of Samples is out of
   *                                  range.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if no configuration could be find for this device for the
   *                                configurated channels and the desired {@link CollectionTime}
   */
  public void setTimebase(CollectionTime collectionTime, int divisions, short oversample,
          int minSamples) throws ConfigurationException {
    setTimebase(new Timebase(collectionTime, divisions, oversample, minSamples));
  }

  /**
   * Sets the {@link CollectionTime} for the device.
   *
   * @param collectionTime the size of one time block.
   * @param divisions      the number of blocks. Must be between 1 and 10.
   * @param minSamples     the number of minium of data samples.
   * @throws IllegalArgumentException if the division or minimum of Samples is out of range.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if no configuration could be find for this device for the
   *                                configurated channels and the desired {@link CollectionTime}
   */
  public void setTimebase(CollectionTime collectionTime, int divisions, int minSamples)
          throws ConfigurationException {
    setTimebase(new Timebase(collectionTime, divisions, minSamples));
  }

  /**
   * Sets the {@link CollectionTime} for the device.
   *
   * @param timebase the timebase settings for the device.
   * @throws IllegalArgumentException if the division, oversample or minimum of Samples is out of
   *                                  range.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if no configuration could be find for this device for the
   *                                configurated channels and the desired {@link CollectionTime}
   */
  public void setTimebase(Timebase timebase) throws ConfigurationException {
    checkUnit();

    Timebase t = unit.setTimebase(timebase);

    if (null == t) {
      throw new ConfigurationException("PS0021: Selected Collection Time and Divisions combination"
              + " is not supported by this device.");
    }
  }

  /**
   * Returns the {@link Timebase} which were selected on the device.
   *
   * @return Informationen about the Timebase.
   * @throws IllegalStateException if instance is not opened.
   */
  public Timebase getTimebase() {
    checkUnit();
    
    return unit.getTimebase();
  }

  /**
   * Set the Signal for the Signal Generator.
   *
   * @param waveType  the type of wave of the outcomming signal.
   * @param frequency the frequency of the wave in Hertz.
   * @param amplitude the amplitude of the signal in volt.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported.
   * @throws ConfigurationException if a parameter is out of range.
   */
  public void setSignalGenerator(WaveType waveType, double frequency, double amplitude)
          throws NotSupportedException, ConfigurationException {
    setSignalGenerator(new GeneratorSettings(waveType, frequency, amplitude));
  }

  /**
   * Set the Signal for the Signal Generator.
   *
   * @param waveType  the type of wave of the outcomming signal.
   * @param frequency the frequency of the wave in Hertz.
   * @param amplitude the amplitude of the signal in volt.
   * @param offset    the offset of the wave signal.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported.
   * @throws ConfigurationException if a parameter is out of range.
   */
  public void setSignalGenerator(WaveType waveType, double frequency, double amplitude,
          double offset)
          throws NotSupportedException, ConfigurationException {
    setSignalGenerator(new GeneratorSettings(waveType, frequency, amplitude, offset));
  }

  /**
   * Set the Signal for the Signal Generator.
   *
   * @param settings the settings for the signal generator.
   * @throws IllegalStateException if instance is not opened.
   * @throws NotSupportedException if a feature is not supported.
   * @throws ConfigurationException if a parameter is out of range.
   */
  public void setSignalGenerator(GeneratorSettings settings)
          throws NotSupportedException, ConfigurationException {
    checkUnit();

    unit.setGenerator(settings);
  }

  /**
   * Starts running a Block. To detect if the block is finished check the {@link #ready()} method.
   * To get a {@link ResultSet} of the block check the {@link #getTimesAndValues()} method.
   *
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if the block could not be run because of wrong configuration.
   */
  public void runBlock() throws ConfigurationException {
    checkUnit();

    unit.runBlock();
  }

  /**
   * Returns the {@link ResultSet} of a executed block.
   *
   * @return the result set.
   * @throws IllegalStateException if instance is not opened.
   */
  public ResultSet getTimesAndValues() {
    checkUnit();

    return unit.getTimesAndValues();
  }

  /**
   * Set the ETS Mode.
   *
   * @param mode          the ets mode.
   * @param etsCyles      the number of ets cylces.
   * @param etsInterleave the number of interleaves for the ets mode.
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if one parameter is out of range.
   * @throws NotSupportedException if the ETS Mode is not supported by the device.
   */
  public void setModeEts(EtsMode mode, int etsCyles, int etsInterleave)
          throws ConfigurationException, NotSupportedException {
    setModeEts(new EtsSettings(mode, etsCyles, etsInterleave));
  }

  /**
   * Set the ETS Mode.
   *
   * @param settings the settings for the ETS mode
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if one parameter is out of range.
   * @throws NotSupportedException if the ETS Mode is not supported by the device.
   */
  public void setModeEts(EtsSettings settings) 
          throws ConfigurationException, NotSupportedException {
    checkUnit();

    unit.setEts(settings);
  }

  /**
   * Deactivate the ETS Mode.
   *
   * @throws IllegalStateException if instance is not opened.
   * @throws ConfigurationException if one parameter is out of range.
   * @throws NotSupportedException if the ETS Mode is not supported by the device.
   */
  public void deactivateModeEts() throws ConfigurationException, NotSupportedException {
    checkUnit();

    EtsSettings settings;
    if (null != unit.getEtsSettings()) {
      settings = new EtsSettings(unit.getEtsSettings());
      settings.setMode(EtsMode.OFF);
    } else {
      settings = new EtsSettings(EtsMode.OFF, 0, 0);
    }

    setModeEts(settings);
  }

  /**
   * Start streaming sample data from the device to the computer. To receive the sample data see
   * {@link #registerStreamingCallback(com.github.electrostar.picolib.OnDataCallback)} method. Must 
   * be called after starting the streaming mode. To stop the streaming data see #{@link #stop()}
   * method.
   *
   * @throws IllegalStateException if instance is not opened, no timebase is set or instance is
   *                               already in streaming mode.
   * @throws NotSupportedException if the device does not support streaming mode.
   * @throws ConfigurationException if no streaming mode could start because of configuration error.
   */
  public void runStreaming() throws NotSupportedException, ConfigurationException {
    checkUnit();

    unit.runStreaming();
  }

  /**
   * Register a Callback for {@link ResultSet} while in streaming mode.
   *
   * @param callback the callback function.
   * @throws IllegalStateException if instance is not opened, a callback already registered or not
   *                               in streaming mode.
   * @throws ConfigurationException if a register callback could not be registered on the driver.
   */
  public void registerStreamingCallback(OnDataCallback callback) throws ConfigurationException {
    checkUnit();

    unit.registerCallback(callback);
  }

  @Override
  public String toString() {
    if (null != unit && unit.isOpen()) {
      UnitInfo info = unit.getInfo();
      if (null != info) {
        return "PicoScope " + info.getVariantInfo();
      }
    }
    return "Unknown unconnected PicoScope";
  }

  @Override
  public void close() {
    if (null != unit) {
      if (unit.isOpen()) {
        unit.close();
      }
      unit = null;
    }
  }
}
