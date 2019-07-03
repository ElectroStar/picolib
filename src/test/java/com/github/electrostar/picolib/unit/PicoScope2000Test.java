/*
 * picolib, open source library to work with PicoScopes.
 * Copyright (C) 2018-2019 ElectroStar <startrooper@startrooper.org>
 *
 * This file is part of picolib.
 *
 * picolib is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
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
import com.github.electrostar.picolib.CollectionTime;
import com.github.electrostar.picolib.Coupling;
import com.github.electrostar.picolib.EtsMode;
import com.github.electrostar.picolib.EtsSettings;
import com.github.electrostar.picolib.GeneratorSettings;
import com.github.electrostar.picolib.OnDataCallback;
import com.github.electrostar.picolib.PicoInfo;
import com.github.electrostar.picolib.Range;
import com.github.electrostar.picolib.ResultSet;
import com.github.electrostar.picolib.TimeUnit;
import com.github.electrostar.picolib.Timebase;
import com.github.electrostar.picolib.TriggerDirection;
import com.github.electrostar.picolib.TriggerSettings;
import com.github.electrostar.picolib.UnitInfo;
import com.github.electrostar.picolib.UnitSeries;
import com.github.electrostar.picolib.exception.ConfigurationException;
import com.github.electrostar.picolib.exception.NotSupportedException;
import com.github.electrostar.picolib.exception.PicoException;
import com.github.electrostar.picolib.exception.UnitNotFoundException;
import com.github.electrostar.picolib.library.PS2000CLibrary;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.AdditionalMatchers.or;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * Tests for the {@link PicoScope2000} class.
 *
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PicoScope2000Test {
  
  private static final String DRIVER_VERSION          = "1, 0, 0, 2";
  private static final String USB_VERSION             = "2.0";
  private static final String HARDWARE_VERSION        = "1";
  private static final String VARIANT_INFO            = "2203";
  private static final String BATCH_AND_SERIAL        = "CMY66/052";
  private static final String CAL_DATE                = "14Jan08";
  private static final String KERNAL_DRIVER_VERSION   = "1,1,2,4";
  private static final String DRIVER_PATH             = "DummyPath";
  
  private final UnitInfo ui = new UnitInfo( DRIVER_VERSION, 
                                            USB_VERSION, 
                                            HARDWARE_VERSION, 
                                            VARIANT_INFO, 
                                            BATCH_AND_SERIAL, 
                                            CAL_DATE, 
                                            KERNAL_DRIVER_VERSION);
  
  private final OnDataCallback callback = (ResultSet rs) -> {
  };
  
  @Mock
  private PS2000CLibrary mockLib;
  
  private PicoScope2000 ps;
  
  public PicoScope2000Test() {
    ui.setDriverPath(DRIVER_PATH);
  }
  
  @BeforeAll
  public static void setUpClass() {
  }
  
  @AfterAll
  public static void tearDownClass() {
  }
  
  @BeforeEach
  public void setUp() throws PicoException {
    ps = new PicoScope2000(mockLib);
    
    when(mockLib.ps2000_open_unit()).thenReturn((short)1);
    
    ps.open();
    
    // Answers for the normal UnitInfo
    // Driver Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.DRIVER_VERSION.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, DRIVER_VERSION);
              return (short)DRIVER_VERSION.length();
            });
    // USB Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.USB_VERSION.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, USB_VERSION);
              return (short)USB_VERSION.length();
            });
    // Hardware Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.HARDWARE_VERSION.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, HARDWARE_VERSION);
              return (short)HARDWARE_VERSION.length();
            });
    // Variant Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.VARIANT_INFO.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, VARIANT_INFO);
              return (short)VARIANT_INFO.length();
            });
    // Batch and Serial Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.BATCH_AND_SERIAL.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, BATCH_AND_SERIAL);
              return (short)BATCH_AND_SERIAL.length();
            });
    // Calibration Datel Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.CALIBRATION_DATE.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, CAL_DATE);
              return (short)CAL_DATE.length();
            });
    // Kernal Version
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.KERNEL_VERSION.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, KERNAL_DRIVER_VERSION);
              return (short)KERNAL_DRIVER_VERSION.length();
            });
    // Driver Path
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.DRIVER_PATH.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, DRIVER_PATH);
              return (short)DRIVER_PATH.length();
            });
    // Min, Max and Lost Value
    when(mockLib.getLostValue()).thenReturn(-32768);
    when(mockLib.getMaxValue()).thenReturn(32767);
    when(mockLib.getMinValue()).thenReturn(-32767);
  }
  
  private void fillArray(byte[] target, String value) {
    byte[] vals = value.getBytes();
    for(int i = 0; i < vals.length; i++) {
      target[i] = vals[i];
    }
    target[vals.length] = 0;
  }
  
  @AfterEach
  public void tearDown() {
  }

  /**
   * Test of open method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testOpen() throws Exception {
    // Check already Open
    assertThrows(IllegalStateException.class, () -> {
      ps.open();
    });
    
    // Check not found
    when(mockLib.ps2000_open_unit()).thenReturn((short)0);
    assertThrows(UnitNotFoundException.class, () -> {
      new PicoScope2000(mockLib).open();
    });
    
    // Check fails to open
    when(mockLib.ps2000_open_unit()).thenReturn((short)-1);
    assertThrows(PicoException.class, () -> {
      new PicoScope2000(mockLib).open();
    });
    
    // Check not open
    assertThrows(IllegalStateException.class, () -> {
      new PicoScope2000(mockLib).getInfo();
    });
  }

  /**
   * Test of getInfo method, of class PicoScope2000.
   */
  @Test
  public void testGetInfo() {
    // First get UnitInfo
    assertEquals(ui, ps.getInfo());
    
    // Second get UnitInfo (from Storage)
    assertEquals(ui, ps.getInfo());
  }

  /**
   * Test of close method, of class PicoScope2000.
   */
  @Test
  public void testClose() {
    ps.close();
    
    assertDoesNotThrow(() -> {
      new PicoScope2000(mockLib).close();
    });
  }

  /**
   * Test of isOpen method, of class PicoScope2000.
   */
  @Test
  public void testIsOpen() {
    assertTrue(ps.isOpen());
    
    assertFalse(new PicoScope2000(mockLib).isOpen());
  }

  /**
   * Test of stop method, of class PicoScope2000.
   */
  @Test
  public void testStop() {
    assertDoesNotThrow(() -> {
      ps.stop();
    });
  }

  /**
   * Test of ready method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testReady() throws Exception {
    // Test Error
    when(mockLib.ps2000_ready(any(short.class))).thenReturn((short)-1);
    assertThrows(PicoException.class, () -> {
      ps.ready();
    });
    
    // Test Ready
    when(mockLib.ps2000_ready(any(short.class))).thenReturn((short)1);
    assertTrue(ps.ready());
    
    // Test not Ready
    when(mockLib.ps2000_ready(any(short.class))).thenReturn((short)0);
    assertFalse(ps.ready());
  }

  /**
   * Test of getTimebase method, of class PicoScope2000.
   */
  @Test
  public void testGetTimebase() {
    assertNull(ps.getTimebase());
  }

  /**
   * Test of setTimebase method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTimebase() throws Exception {
    // Wrong number of Divisions
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTimebase(new Timebase(CollectionTime.DIV1MS, 0));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTimebase(new Timebase(CollectionTime.DIV1MS, 11));
    });
    
    // Wrong oversample number
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTimebase(new Timebase(CollectionTime.DIV1MS, 10, (short)0));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTimebase(new Timebase(CollectionTime.DIV1MS, 10, (short)256));
    });
    
    // Wrong minimal samples number
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTimebase(new Timebase(CollectionTime.DIV1MS, 10, 0));
    });
    
    // Test until internal Timebase Id is 255 or greater
    assertNull(ps.setTimebase(new Timebase()));
    
    // Test working example
    mockTimebase();
    
    Timebase result = new Timebase(CollectionTime.DIV1MS, 10, (short)1, 1);
    result.setSamples(15625);
    result.setTimeInterval(640);
    result.setTimeUnit(TimeUnit.NANOSECOND);
    result.setMaxSamples(16256);
    result.setInternalTimebaseId((short)7);
    
    assertEquals(ps.setTimebase(new Timebase()), result);
    
    assertEquals(ps.getTimebase(), result);
    
    // Should Fail -> second tryTimebase returns null
    when(mockLib.ps2000_get_timebase( anyShort(), 
                                      anyShort(), 
                                      anyInt(), 
                                      any(IntByReference.class), 
                                      any(ShortByReference.class), 
                                      anyShort(), 
                                      any(IntByReference.class)))
            .thenAnswer((iom) -> {
              short timebase = iom.getArgument(1);
              int samples = iom.getArgument(2);
              IntByReference timeInterval = iom.getArgument(3);
              ShortByReference timeUnits = iom.getArgument(4);
              IntByReference maxSamples = iom.getArgument(6);
              maxSamples.setValue(16256);
              
              timeInterval.setValue(5 * ((int)Math.pow(2, timebase)));
              if (samples > 1) {
                timeUnits.setValue((short)2);
                return (short)0;
              } else {
                timeUnits.setValue((short)0);
                return (short)1;
              }
            });
    assertThrows(ConfigurationException.class, () -> {
      ps.setTimebase(new Timebase());
    });
  }

  /**
   * Test of setEts method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetEts() throws Exception {
    // Test ConfigurationException
    assertThrows(ConfigurationException.class, () -> {
      ps.setEts(new EtsSettings(EtsMode.FAST, 10, 2));
    });
    // Fail but command was set off
    ps.setEts(new EtsSettings());
    // Successfull
    when(mockLib.ps2000_set_ets(anyShort(), anyShort(), anyShort(), anyShort()))
            .thenReturn(10);
    ps.setEts(new EtsSettings());
    
    assertEquals(new EtsSettings(), ps.getEtsSettings());
  }
  
  @Test
  /**
   * Test of NotSupported for setEts method, of class PicoScope2000.
   */
  public void testNotSupportedSetEts() {
    // Test Not Supported
    when(mockLib.ps2000_get_unit_info(
            any(short.class), 
            any(byte[].class), 
            any(short.class), 
            eq((short) PicoInfo.VARIANT_INFO.getId())))
            .thenAnswer((iom) -> {
              byte[] array = iom.getArgument(1);
              fillArray(array, "2202");
              return (short)"2202".length();
            });
    
    assertThrows(NotSupportedException.class, () -> {
      ps.setEts(new EtsSettings());
    });
  }

  /**
   * Test of registerCallback method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testRegisterCallback() throws Exception {
    // Fail because not in streaming mode
    assertThrows(IllegalStateException.class, () -> {
      ps.registerCallback(callback);
    });
    
    // Start Streaming mode
    setupStreaming();
    
    ps.registerCallback(callback);
    
    Awaitility.await().pollDelay(10, java.util.concurrent.TimeUnit.MILLISECONDS).until(() -> true);
    
    when(mockLib.ps2000_get_streaming_last_values(anyShort(), 
            any(PS2000CLibrary.GetOverviewBuffersMaxMin.class)))
            .thenReturn((short)1);
    
    // Test already registered
    assertThrows(IllegalStateException.class, () -> {
      ps.registerCallback(callback);
    });
    
    // Stop Streaming
    ps.stop();
    
    Awaitility.await().pollDelay(Duration.ONE_SECOND).until(() -> true);
    
    // Start again
    ps.runStreaming();
    ps.registerCallback(callback);
    ps.stop();
  }

  private void mockActivateStreaming() {
    when(mockLib.ps2000_run_streaming_ns(anyShort(), anyInt(), anyShort(), anyInt(), 
            anyShort(), anyInt(), anyInt()))
            .thenReturn((short)1);
  }
  
  /**
   * Test of isStreaming method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testIsStreaming() throws Exception {
    assertFalse(ps.isStreaming());
    
    setupStreaming();
    ps.registerCallback(callback);
    
    assertTrue(ps.isStreaming());
    
    ps.stop();
  }

  /**
   * Test of getUnitSeries method, of class PicoScope2000.
   */
  @Test
  public void testGetUnitSeries() {
    assertEquals(UnitSeries.PICOSCOPE2000, ps.getUnitSeries());
  }

  /**
   * Test of getChannelSettings method, of class PicoScope2000.
   */
  @Test
  public void testGetChannelSettings() {
    List<ChannelSettings> channels = new ArrayList<>();
    channels.add(new ChannelSettings());
    channels.add(new ChannelSettings());
    assertEquals(channels, ps.getChannelSettings());
  }

  /**
   * Test of getTriggerSettings method, of class PicoScope2000.
   */
  @Test
  public void testGetTriggerSettings() {
    assertNull(ps.getTriggerSettings());
  }

  /**
   * Test of getGeneratorSettings method, of class PicoScope2000.
   */
  @Test
  public void testGetGeneratorSettings() {
    assertNull(ps.getGeneratorSettings());
  }

  /**
   * Test of setChannel method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetChannel() throws Exception {
    // Not Supported Analog Offset
    assertThrows(NotSupportedException.class, () -> {
      ps.setChannel(Channel.CHANNEL_A, new ChannelSettings(Range.RANGE_1V, Coupling.AC, true, 2f));
    });
    
    // Channel C & D not supported
    assertThrows(NotSupportedException.class, () -> {
      ps.setChannel(Channel.CHANNEL_C, new ChannelSettings());
    });
    
    // Configuration Exception
    assertThrows(ConfigurationException.class, () -> {
      ps.setChannel(Channel.CHANNEL_A, new ChannelSettings());
    });
    
    mockChannel();
    
    // Null Settings
    ps.setChannel(Channel.CHANNEL_A, null);
    
    // Check for Equal Settings
    ChannelSettings cs = new ChannelSettings(Range.RANGE_5V, Coupling.DC, true);
    ps.setChannel(Channel.CHANNEL_A, cs);
    assertEquals(ps.getChannelSettings().get(0), cs);
  }

  /**
   * Test of setTrigger method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTrigger() throws Exception {
    // Channel C & D not supported
    assertThrows(NotSupportedException.class, () -> {
      ps.setTrigger(new TriggerSettings(Channel.CHANNEL_C, TriggerDirection.RISING, 0));
    });
    
    // Configuration Exception
    assertThrows(ConfigurationException.class, () -> {
      ps.setTrigger(new TriggerSettings(Channel.CHANNEL_A, TriggerDirection.RISING, 0));
    });
    
    when(mockLib.ps2000_set_trigger2(anyShort(), anyShort(), anyShort(), 
            anyShort(), anyFloat(), anyShort()))
            .thenReturn((short)1);
    
    // Test Channel None (Deactivating)
    TriggerSettings ts = new TriggerSettings(Channel.NONE, TriggerDirection.FALLING, 2f);
    ps.setTrigger(ts);
    assertEquals(ts, ps.getTriggerSettings());
    
    // Test Normal Channel
    ts.setChannel(Channel.CHANNEL_A);
    ps.setTrigger(ts);
    assertEquals(ts, ps.getTriggerSettings());
    
    // Test over Limit Threadhold
    ts.setThreshold(2000f);
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTrigger(ts);
    });
    
    // Test under Limit Threadhold
    ts.setThreshold(-2000f);
    assertThrows(IllegalArgumentException.class, () -> {
      ps.setTrigger(ts);
    });
  }

  /**
   * Test of setGenerator method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetGenerator() throws Exception {
    // Configuration Exception
    assertThrows(ConfigurationException.class, () -> {
      ps.setGenerator(new GeneratorSettings());
    });
    
    when(mockLib.ps2000_set_sig_gen_built_in(anyShort(), anyInt(), anyInt(), anyInt(), anyFloat(), 
            anyFloat(), anyFloat(), anyFloat(), anyInt(), anyInt()))
            .thenReturn((short)1);
    
    GeneratorSettings gs = new GeneratorSettings();
    ps.setGenerator(gs);
    assertEquals(gs, ps.getGeneratorSettings());
  }

  /**
   * Test of runBlock method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testRunBlock() throws Exception {
    setupTimebase();
    
    // Test Configuration Error
     assertThrows(ConfigurationException.class, () -> {
      ps.runBlock();
    });
     
    when(mockLib.ps2000_run_block(anyShort(), anyInt(), anyShort(), 
             anyShort(), any(IntByReference.class)))
             .thenReturn((short)1);
    
    ps.runBlock();
  }

  /**
   * Test of getTimesAndValues method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testGetTimesAndValues() throws Exception {
    setupTimebase();
    
    // No Read and No Channel
    assertNull(ps.getTimesAndValues());
    
    mockTimesAndValues();
    
    // Activate Channel A and B
    mockChannel();
    ps.setChannel(Channel.CHANNEL_A, new ChannelSettings(Range.RANGE_2V, Coupling.DC, true));
    ps.setChannel(Channel.CHANNEL_B, new ChannelSettings(Range.RANGE_10V, Coupling.DC, true));
    
    // Some Data Received
    assertNotNull(ps.getTimesAndValues());
    
    // Deactive Channel A and do it again
    ps.setChannel(Channel.CHANNEL_A, new ChannelSettings());
    assertNotNull(ps.getTimesAndValues());
    
    // Activate Channel A and Deactivate Channel B
    ps.setChannel(Channel.CHANNEL_A, new ChannelSettings(Range.RANGE_2V, Coupling.DC, true));
    ps.setChannel(Channel.CHANNEL_B, new ChannelSettings());
    assertNotNull(ps.getTimesAndValues());
  }

  /**
   * Test of runStreaming method, of class PicoScope2000.
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testRunStreaming() throws Exception {
    // Test Timebase
    assertThrows(IllegalStateException.class, () -> {
      ps.runStreaming();
    });
    
    // Setup Timebase
    setupTimebase();
    
    // Test for ConfigurationException
    assertThrows(ConfigurationException.class, () -> {
      ps.runStreaming();
    });
    
    // Mock and Start Streaming
    setupStreaming();
    
    // Try to start streaming again
    assertThrows(IllegalStateException.class, () -> {
      ps.runStreaming();
    });
  }

  private void mockStreaming() {
    when(mockLib.ps2000_run_streaming_ns(anyShort(), anyInt(), anyShort(), anyInt(), 
            anyShort(), anyInt(), anyInt()))
            .thenReturn((short)1);
  }
  
  private void setupStreaming() throws Exception {
    setupTimebase();
    
    mockStreaming();
    
    ps.runStreaming();
  }
  
  private void mockChannel() {
    when(mockLib.ps2000_set_channel(anyShort(), anyShort(), anyShort(), anyShort(), anyShort()))
            .thenReturn((short)1);
  }
  
  private void mockTimesAndValues() {
    when(mockLib.ps2000_get_times_and_values( anyShort(), 
                                              any(Memory.class), 
                                              or(any(Memory.class), eq(null)),
                                              or(any(Memory.class), eq(null)),
                                              eq(null), 
                                              eq(null), 
                                              any(ShortByReference.class), 
                                              anyShort(), 
                                              anyInt()))
            .thenAnswer((iom) -> {
              Memory times = iom.getArgument(1);
              Memory chA = iom.getArgument(2);
              Memory chB = iom.getArgument(3);
              int samples = iom.getArgument(8);
              int max = 32767;
              int min = -32767;
              int lost = -32768;
              
              Random rnd = new Random();
              
              for(int i = 0; i < samples; i++) {
                times.setInt(i*Native.getNativeSize(Integer.TYPE), i);
                if (null != chA) {
                  chA.setShort(i*Native.getNativeSize(Short.TYPE), 
                          (short) (rnd.nextInt((max - min) + 1) + min));
                }
                if (null != chB) {
                  chB.setShort(i*Native.getNativeSize(Short.TYPE), 
                          (short) (rnd.nextInt((max - min) + 1) + min));
                }
              }
              // Set the First 3 With Min, Max and Lost Value
              if (null != chA) {
                chA.setShort(0*Native.getNativeSize(Short.TYPE), (short)min);
                chA.setShort(1*Native.getNativeSize(Short.TYPE), (short)max);
                chA.setShort(2*Native.getNativeSize(Short.TYPE), (short)lost);
              }
              if (null != chB) {
                chB.setShort(0*Native.getNativeSize(Short.TYPE), (short)min);
                chB.setShort(1*Native.getNativeSize(Short.TYPE), (short)max);
                chB.setShort(2*Native.getNativeSize(Short.TYPE), (short)lost);
              }
              
              return samples;
            });
  }
  
  private void mockTimebase() {
    when(mockLib.ps2000_get_timebase( anyShort(), 
                                      anyShort(), 
                                      anyInt(), 
                                      any(IntByReference.class), 
                                      any(ShortByReference.class), 
                                      anyShort(), 
                                      any(IntByReference.class)))
            .thenAnswer((iom) -> {
              short timebase = iom.getArgument(1);
              int samples = iom.getArgument(2);
              IntByReference timeInterval = iom.getArgument(3);
              ShortByReference timeUnits = iom.getArgument(4);
              IntByReference maxSamples = iom.getArgument(6);
              maxSamples.setValue(16256);
              
              timeInterval.setValue(5 * ((int)Math.pow(2, timebase)));
              if (samples > 1) {
                timeUnits.setValue((short)2);
              } else {
                timeUnits.setValue((short)0);
              }
              return (short)1;
            });
  }
  
  private void setupTimebase() throws Exception {
    mockTimebase();
    
    ps.setTimebase(new Timebase());
  }
}
