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
import com.github.electrostar.picolib.EtsMode;
import com.github.electrostar.picolib.EtsSettings;
import com.github.electrostar.picolib.GeneratorSettings;
import com.github.electrostar.picolib.OnDataCallback;
import com.github.electrostar.picolib.PicoInfo;
import com.github.electrostar.picolib.Range;
import com.github.electrostar.picolib.ResultSet;
import com.github.electrostar.picolib.TimeUnit;
import com.github.electrostar.picolib.Timebase;
import com.github.electrostar.picolib.TriggerSettings;
import com.github.electrostar.picolib.UnitInfo;
import com.github.electrostar.picolib.UnitSeries;
import com.github.electrostar.picolib.exception.ConfigurationException;
import com.github.electrostar.picolib.exception.NotSupportedException;
import com.github.electrostar.picolib.exception.PicoException;
import com.github.electrostar.picolib.exception.UnitNotFoundException;
import com.github.electrostar.picolib.library.PS2000CLibrary;
import com.sun.jna.Memory; // NOSONAR
import com.sun.jna.Native; // NOSONAR
import com.sun.jna.Pointer; // NOSONAR
import com.sun.jna.ptr.IntByReference; // NOSONAR
import com.sun.jna.ptr.ShortByReference; // NOSONAR
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@code PicoScope2000} is the implementation of {@link PicoUnit} for a PicoScope of the 
 * 2000 series. 
 * <p>
 * It interact with a 
 * Pico Technology PicoScope 2000 Series Device. 
 * Supported PicoScope variants are the following:
 * </p>
 * <ul>
 * <li>2104</li>
 * <li>2105</li>
 * <li>2202</li>
 * <li>2203</li>
 * <li>2204</li>
 * <li>2204A</li>
 * <li>2205</li>
 * <li>2205A</li>
 * </ul>
 * For more information about the methods see {@link PicoUnit}.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 * @since 0.0.1
 */
final class PicoScope2000 implements PicoUnit {

  private static final int MAX_CHANNELS = 2;
  private static final UnitSeries UNIT_SERIES = UnitSeries.PICOSCOPE2000;
  
  private final List<ChannelSettings> channels = new ArrayList<>(MAX_CHANNELS);
  private final PS2000CLibrary library;
  
  private short handle;
  private UnitInfo unitInfo;
  private PS2000CLibrary.GetOverviewBuffersMaxMin deviceCallback;
  private boolean streaming;
  private boolean streamingStarted;
  private Timebase timebase;
  private TriggerSettings trigger;
  private GeneratorSettings generator;
  private EtsSettings ets;
  
  private Thread callbackThread;

  /**
   * Creates an instance of PicoUnit to handle one physical Pico Technology PicoScope of
   * the 2000er Series.
   */
  public PicoScope2000(PS2000CLibrary library) {
    this.library = library;
    
    init();
  }

  private void init() {
    handle = 0;
    unitInfo = null;
    deviceCallback = null;
    streaming = false;
    streamingStarted = false;
    timebase = null;
    trigger = null;
    generator = null;
    ets = null;
    callbackThread = null;
    
    for (int i = 0; i < MAX_CHANNELS; i++) {
      channels.add(new ChannelSettings());
    }
  }
  
  @Override
  public void open() throws PicoException {
    checkNotOpen();
    handle = library.ps2000_open_unit();
    if (handle == 0) {
      throw new UnitNotFoundException("No PicoScope of 2000er Series found.");
    } else if (handle < 0) {
      throw new PicoException("Fails to open a PicoScope of 2000er Series.");
    }
  }

  private void checkNotOpen() {
    if (handle > 0) {
      throw new IllegalStateException("Unit already open.");
    }
  }

  private void checkOpen() {
    if (handle <= 0) {
      throw new IllegalStateException("Unit is not open.");
    }
  }

  @Override
  public UnitInfo getInfo() {
    if (null == unitInfo) {
      checkOpen();

      UnitInfo ui = new UnitInfo();
      byte[] infoBytes = new byte[80];

      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.DRIVER_VERSION.getId());
      ui.setDriverVersion(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.USB_VERSION.getId());
      ui.setUsbVersion(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.HARDWARE_VERSION.getId());
      ui.setHardwareVersion(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.VARIANT_INFO.getId());
      ui.setVariantInfo(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.BATCH_AND_SERIAL.getId());
      ui.setBatchAndSerial(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.CALIBRATION_DATE.getId());
      ui.setCalibrationDate(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.KERNEL_VERSION.getId());
      ui.setKernelVersion(Native.toString(infoBytes));
      library.ps2000_get_unit_info(handle, infoBytes, (short) infoBytes.length, 
              (short) PicoInfo.DRIVER_PATH.getId());
      ui.setDriverPath(Native.toString(infoBytes));

      unitInfo = ui;
    }
    return unitInfo;
  }

  @Override
  public void close() {
    if (handle > 0) {
      library.ps2000_close_unit(handle);
      // Reset all Values
      init();
    }
  }

  @Override
  public boolean isOpen() {
    return handle > 0;
  }

  @Override
  public void stop() {
    checkOpen();
    library.ps2000_stop(handle);
    streaming = false;
    streamingStarted = false;
  }

  @Override
  public boolean ready() throws PicoException {
    checkOpen();
    short r = library.ps2000_ready(handle);
    if (r == -1) {
      throw new PicoException("The USB transfer failed, indicating that the oscilloscope may "
              + "have been unplugged.");
    }

    return r > 0;
  }

  @Override
  public Timebase getTimebase() {
    return timebase;
  }

  @Override
  public Timebase setTimebase(Timebase sb) throws ConfigurationException {
    checkTimebaseOptions(sb);

    short timebaseId = 0;
    boolean found = false;
    Timebase t = null;

    do {
      t = tryTimebase(timebaseId, sb.getMinSamples(), sb.getOversample());
      // t == null -> next Timebase
      // ((CollectionTime * Divisions) / timeInterval) > maxSamples -> next Timebase
      if (null != t) {
        long neededSamples = (sb.getCollectionTime().getDivisionTime() * sb.getDivisions()) 
                             / t.getTimeInterval();
        if (neededSamples <= t.getMaxSamples()) {
          // Finally set the Timebase on the Device with the current samples number
          t = tryTimebase(timebaseId, (int) neededSamples, sb.getOversample());
          if (null == t) {
            throw new ConfigurationException("PS0022: Fatalerror while setting up the timebase.");
          }

          found = true;
          t.setCollectionTime(sb.getCollectionTime());
          t.setDivisions(sb.getDivisions());
          t.setSamples((int) neededSamples);
          t.setInternalTimebaseId(timebaseId);
          t.setMinSamples(sb.getMinSamples());
        }
      }
      
      if (timebaseId >= 255) {
        break;
      }
      
      timebaseId++;
    } while (!found);

    if (found) {
      this.timebase = t;
      return t;
    } else {
      return null;
    }
  }

  private void checkTimebaseOptions(Timebase sb) {
    if (sb.getDivisions() <= 0 || sb.getDivisions() > 10) {
      throw new IllegalArgumentException("Divisions are out of range.");
    }

    if (sb.getOversample() < 1 || sb.getOversample() > 255) {
      throw new IllegalArgumentException("Oversampling value must be beetween 1 and 255.");
    }

    if (sb.getMinSamples() < 1) {
      throw new IllegalArgumentException("Min Samples must be 1 or greater.");
    }
  }

  private Timebase tryTimebase(short timebase, int numberOfSamples, short oversampling) {
    checkOpen();

    IntByReference timeInterval = new IntByReference(0);
    ShortByReference timeUnits = new ShortByReference((short) 0);
    IntByReference maxSamples = new IntByReference(0);

    short r = library.ps2000_get_timebase(
            handle, 
            timebase, 
            numberOfSamples, 
            timeInterval, 
            timeUnits, 
            oversampling, 
            maxSamples);
    
    if (r == 0) {
      return null;
    }

    Timebase t = new Timebase();
    t.setMaxSamples(maxSamples.getValue());
    t.setTimeInterval(timeInterval.getValue());
    t.setTimeUnit(TimeUnit.findById(timeUnits.getValue()));
    t.setOversample(oversampling);
    return t;
  }

  private void checkSupported(String[] variants) throws NotSupportedException {
    // Get Unit Information
    if (null == unitInfo) {
      this.getInfo();
    }

    // Check if this device version is in range
    boolean found = UnitHelper.isSupported(unitInfo, variants);

    if (!found) {
      throw new NotSupportedException("Function is not supported by this variant!");
    }
  }

  private void checkNotSupported(String[] variants) throws NotSupportedException {
    boolean supported = true;
    try {
      checkSupported(variants);
    } catch (NotSupportedException ex) {
      supported = false;
    }
    if (supported) {
      throw new NotSupportedException("Function is not supported by this variant!");
    }
  }

  @Override
  public void setEts(EtsSettings settings) throws ConfigurationException, NotSupportedException {
    checkOpen();
    checkNotSupported(new String[]{"2202"});

    int r = library.ps2000_set_ets(
            handle,
            (short) settings.getMode().getId(),
            (short) settings.getCycles(),
            (short) settings.getInterleaves());

    if (r == 0 && settings.getMode() != EtsMode.OFF) {
      throw new ConfigurationException("One of the parameters is out of range.");
    }

    ets = settings;
  }

  @Override
  public void registerCallback(OnDataCallback callback) throws ConfigurationException {
    checkOpen();
    checkTimebase();

    if (!streamingStarted) {
      throw new IllegalStateException("Only allowed in Streaming mode.");
    }

    if (null != callbackThread && callbackThread.isAlive()) {
      throw new IllegalStateException("Could not register more than one callback.");
    }

    deviceCallback = new PS2000Callback(callback, channels, 
            library.getLostValue(), library.getMaxValue(), timebase);

    streaming = true;
    callbackThread = new Thread(() -> {
      while (streaming) {
        try {
          short r = library.ps2000_get_streaming_last_values(
                  handle, 
                  deviceCallback);
          
          if (r == 1) {
            Thread.sleep(5);
          }
        } catch (InterruptedException ex) {
          Logger.getLogger(PicoScope2000.class.getName()).log(Level.SEVERE, null, ex);
          Thread.currentThread().interrupt();
        }
      }
      deviceCallback = null;
    });
    callbackThread.start();
  }

  @Override
  public boolean isStreaming() {
    return streaming;
  }

  @Override
  public UnitSeries getUnitSeries() {
    return UNIT_SERIES;
  }

  @Override
  public List<ChannelSettings> getChannelSettings() {
    return channels;
  }

  @Override
  public TriggerSettings getTriggerSettings() {
    return trigger;
  }

  @Override
  public GeneratorSettings getGeneratorSettings() {
    return generator;
  }

  @Override
  public void setChannel(Channel channel, ChannelSettings settings) 
          throws ConfigurationException, NotSupportedException {
    checkOpen();

    if (null != settings && settings.getAnalogOffset() != 0f) {
      throw new NotSupportedException("Analog Offset is not supported by " + UNIT_SERIES);
    }

    if (channel.getId() >= Channel.CHANNEL_C.getId()) {
      throw new NotSupportedException("Only Channel A & B are supported by the 2000er series.");
    }

    if (null == settings) {
      settings = new ChannelSettings(channels.get(channel.getId()));
      settings.setEnabled(false);
    }

    short status = library.ps2000_set_channel(
            handle,
            (short) channel.getId(),
            settings.isEnabled() ? (short) 1 : (short) 0,
            (short) settings.getCoupling().getId(),
            (short) settings.getRange().getId());

    if (0 == status) {
      throw new ConfigurationException("Could not configure channel properly.");
    }

    channels.set(channel.getId(), settings);
  }

  @Override
  public void setTrigger(TriggerSettings settings) 
          throws NotSupportedException, ConfigurationException {
    checkOpen();

    if (settings.getChannel().getId() >= Channel.CHANNEL_C.getId() 
            && settings.getChannel() != Channel.NONE) {
      throw new NotSupportedException("Only Channel A & B are supported by the 2000er series.");
    }

    short r;
    if (settings.getChannel() == Channel.NONE) {
      r = library.ps2000_set_trigger2(
              handle, 
              (short) settings.getChannel().getId(),
              (short) 0, 
              (short) 0, 
              0f, 
              (short) 0);
    } else {
      short threshold = calculateThreshold(
              channels.get(settings.getChannel().getId()).getRange(), 
              settings.getThreshold());
      
      r = library.ps2000_set_trigger2(
              handle,
              (short) settings.getChannel().getId(),
              threshold,
              (short) settings.getDirection().getId(),
              settings.getDelay(),
              settings.getAutoTigger());
    }

    if (0 == r) {
      throw new ConfigurationException("Could not configure trigger properly.");
    }

    trigger = settings;
  }

  private short calculateThreshold(Range range, double value) {
    double mvRange = range.getValue();

    if (value > mvRange || value < (mvRange * -1)) {
      throw new IllegalArgumentException("Threshold Value is out of Range.");
    }

    double mvPerStep = mvRange / 65534;
    Double erg = mvPerStep * value;
    return erg.shortValue();
  }

  @Override
  public void setGenerator(GeneratorSettings settings) 
          throws ConfigurationException, NotSupportedException {
    checkOpen();
    checkSupported(new String[]{"2203", "2204", "2204A", "2205", "2205A"});

    short r = library.ps2000_set_sig_gen_built_in(
            handle,
            ((Double) (settings.getOffset() * 1000000)).intValue(),
            ((Double) (settings.getAmplitude() * 2000000)).intValue(),
            settings.getWaveType().getId(),
            (float) settings.getStartFrequency(),
            (float) settings.getStopFrequency(),
            (float) settings.getIncrement(),
            (float) settings.getDwellTime(),
            settings.getSweepType().getId(),
            settings.getSweeps());
    if (r == 0) {
      throw new ConfigurationException("Could not configure built in signal generator properly.");
    }

    generator = settings;
  }

  private void checkTimebase() {
    if (null == timebase) {
      throw new IllegalStateException("Timebase was not set.");
    }
  }

  @Override
  public void runBlock() throws ConfigurationException {
    checkOpen();
    checkTimebase();
    IntByReference timeIndisposedMs = new IntByReference(0);
    short r = library.ps2000_run_block(handle,
            timebase.getSamples(),
            timebase.getInternalTimebaseId(),
            timebase.getOversample(),
            timeIndisposedMs);
    if (r == 0) {
      throw new ConfigurationException("Could not run block. Check timebase configuration.");
    }
  }

  @Override
  public ResultSet getTimesAndValues() {
    checkOpen();
    checkTimebase();

    Memory timesPointer;
    Memory chABufferPointer;
    Memory chBBufferPointer;
    
    timesPointer = new Memory((long)timebase.getSamples() * Native.getNativeSize(Integer.TYPE));
    chABufferPointer = channels.get(0).isEnabled() 
            ? new Memory((long)timebase.getSamples() * Native.getNativeSize(Short.TYPE)) : null;
    chBBufferPointer = channels.get(1).isEnabled() 
            ? new Memory((long)timebase.getSamples() * Native.getNativeSize(Short.TYPE)) : null;

    ShortByReference overflow = new ShortByReference((short) 0);

    List<Pointer> channelDatas = new ArrayList<>();
    channelDatas.add(chABufferPointer);
    channelDatas.add(chBBufferPointer);
    
    int read = library.ps2000_get_times_and_values(
            handle,
            timesPointer,
            chABufferPointer,
            chBBufferPointer,
            null,
            null,
            overflow,
            (short) timebase.getTimeUnit().getId(),
            timebase.getSamples());

    if (read > 0) {
      ResultSetConverter rsc = new ResultSetConverter(
              read, 
              library.getMaxValue(),
              library.getLostValue(),
              timesPointer, 
              channelDatas, 
              channels);
      
      ResultSet rs = rsc.convert();
      rs.setCollectionTime(timebase.getCollectionTime());
      rs.setDivisions(timebase.getDivisions());
      rs.setTimeUnit(timebase.getTimeUnit());
      return rs;
    }
    return null;
  }

  @Override
  public void runStreaming() throws ConfigurationException, NotSupportedException {
    checkOpen();
    checkTimebase();
    checkSupported(new String[]{"2202", "2203", "2204", "2204A", "2205", "2205A"});

    if (streamingStarted) {
      throw new IllegalStateException("Already in streaming mode.");
    }

    short r = library.ps2000_run_streaming_ns(
            handle,
            timebase.getTimeInterval(),
            (short) timebase.getTimeUnit().getId(), // Should be Nanoseconds
            timebase.getSamples(),
            (short) 0,
            1,
            30000);

    if (r == 0) {
      throw new ConfigurationException("One of the parameters is out of range.");
    }

    streamingStarted = true;
  }

  @Override
  public EtsSettings getEtsSettings() {
    return ets;
  }
}
