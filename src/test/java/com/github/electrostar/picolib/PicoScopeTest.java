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

package com.github.electrostar.picolib;

import com.github.electrostar.picolib.Range;
import com.github.electrostar.picolib.OnDataCallback;
import com.github.electrostar.picolib.Timebase;
import com.github.electrostar.picolib.Coupling;
import com.github.electrostar.picolib.UnitInfo;
import com.github.electrostar.picolib.PicoScope;
import com.github.electrostar.picolib.EtsSettings;
import com.github.electrostar.picolib.SweepType;
import com.github.electrostar.picolib.WaveType;
import com.github.electrostar.picolib.ChannelSettings;
import com.github.electrostar.picolib.CollectionTime;
import com.github.electrostar.picolib.ResultSet;
import com.github.electrostar.picolib.UnitSeries;
import com.github.electrostar.picolib.EtsMode;
import com.github.electrostar.picolib.GeneratorSettings;
import com.github.electrostar.picolib.Channel;
import com.github.electrostar.picolib.TriggerDirection;
import com.github.electrostar.picolib.TriggerSettings;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.github.electrostar.picolib.exception.ConfigurationException;
import com.github.electrostar.picolib.exception.PicoException;
import com.github.electrostar.picolib.unit.PicoUnit;
import com.github.electrostar.picolib.unit.UnitFactory;

/**
 * Tests for the {@link PicoScope} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PicoScopeTest {

  private PicoScope unopendPS;
  private PicoScope ps;

  @Mock
  private UnitFactory mockFactory;

  @Mock
  private PicoUnit mockUnit;

  private final OnDataCallback callback = (ResultSet rs) -> {
  };

  public PicoScopeTest() {
  }

  @BeforeEach
  public void setUp() throws PicoException, Exception {
    unopendPS = new PicoScope(mockFactory);

    ps = new PicoScope(mockFactory);

    when(mockFactory.getUnit(any(UnitSeries.class))).thenReturn(mockUnit);

    ps.open(UnitSeries.PicoScope2000er);

    when(mockUnit.isOpen()).thenReturn(true);
  }

  @AfterEach
  public void tearDown() {
    unopendPS.close();

    ps.close();
  }

  /**
   * Test of open constructor for class PicoScope.
   */
  @Test
  public void testConstructor() {

    // Test null constructor
    assertThrows(NullPointerException.class, () -> {
      new PicoScope((UnitSeries) null);
    });
  }

  /**
   * Test of open method, of with no Factory class PicoScope.
   */
  @Test
  public void testNoFactory() {
    // Test no Factory
    PicoScope noFactory = new PicoScope((UnitFactory) null);

    assertThrows(IllegalStateException.class, () -> {
      noFactory.open(UnitSeries.PicoScope2000er);
    });
  }

  /**
   * Test of open method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testOpen() throws Exception {
    // Test already open
    assertThrows(IllegalStateException.class, () -> {
      ps.open(UnitSeries.PicoScope2000er);
    });

    // Test was open
    when(mockUnit.isOpen()).thenReturn(false);
    ps.open(UnitSeries.PicoScope2000er);
  }

  /**
   * Test of getUnitSeries method, of class PicoScope.
   */
  @Test
  public void testGetUnitSeries() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.getUnitSeries();
    });

    when(mockUnit.getUnitSeries()).thenReturn(UnitSeries.PicoScope2000er);

    assertEquals(UnitSeries.PicoScope2000er, ps.getUnitSeries());
  }

  /**
   * Test of getInfo method, of class PicoScope.
   */
  @Test
  public void testGetInfo() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.getInfo();
    });

    UnitInfo ui = new UnitInfo("1", "2", "3", "4", "5", "6", "7", "8");

    when(mockUnit.getInfo()).thenReturn(ui);

    assertEquals(ui, ps.getInfo());
  }

  /**
   * Test of disableChannel method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testDisableChannel() throws Exception {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.disableChannel(Channel.CHANNEL_A);
    });

    ps.disableChannel(Channel.CHANNEL_A);
  }

  /**
   * Test of setChannel method, of class PicoScope.
   */
  @Test
  public void testSetChannel_Channel_ChannelSettings() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.setChannel(Channel.CHANNEL_A, new ChannelSettings());
    });
  }

  /**
   * Test of setChannel method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetChannel_3args() throws Exception {
    ps.setChannel(Channel.CHANNEL_A, Coupling.AC, Range.RANGE_1V);
  }

  /**
   * Test of setChannel method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetChannel_4args() throws Exception {
    ps.setChannel(Channel.CHANNEL_A, Coupling.AC, Range.RANGE_1V, 0);
  }

  /**
   * Test of setTrigger method, of class PicoScope.
   */
  @Test
  public void testSetTrigger_TriggerSettings() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.setTrigger(new TriggerSettings());
    });
  }

  /**
   * Test of setTrigger method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTrigger_3args() throws Exception {
    ps.setTrigger(Channel.CHANNEL_A, TriggerDirection.RISING, 0);
  }

  /**
   * Test of setTrigger method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTrigger_4args() throws Exception {
    ps.setTrigger(Channel.CHANNEL_A, TriggerDirection.RISING, 0, 0);
  }

  /**
   * Test of setTrigger method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTrigger_5args() throws Exception {
    ps.setTrigger(Channel.CHANNEL_A, TriggerDirection.RISING, 0, 0, (short) 0);
  }

  /**
   * Test of stop method, of class PicoScope.
   */
  @Test
  public void testStop() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.stop();
    });

    ps.stop();
  }

  /**
   * Test of ready method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testReady() throws Exception {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.ready();
    });

    when(mockUnit.ready()).thenReturn(true);

    assertTrue(ps.ready());
  }

  /**
   * Test of setTimebase method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTimebase_CollectionTime() throws Exception {
    when(mockUnit.setTimebase(any(Timebase.class))).then(returnsFirstArg());

    ps.setTimebase(CollectionTime.DIV1MS);
  }

  /**
   * Test of setTimebase method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTimebase_CollectionTime_int() throws Exception {
    when(mockUnit.setTimebase(any(Timebase.class))).then(returnsFirstArg());

    ps.setTimebase(CollectionTime.DIV1MS, 10);
  }

  /**
   * Test of setTimebase method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTimebase_3args_1() throws Exception {
    when(mockUnit.setTimebase(any(Timebase.class))).then(returnsFirstArg());

    ps.setTimebase(CollectionTime.DIV1MS, 10, 5000);
  }

  /**
   * Test of setTimebase method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTimebase_4args() throws Exception {
    when(mockUnit.setTimebase(any(Timebase.class))).then(returnsFirstArg());

    ps.setTimebase(CollectionTime.DIV1MS, 10, (short) 1, 5000);
  }

  /**
   * Test of setTimebase method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetTimebase_3args_2() throws Exception {
    when(mockUnit.setTimebase(any(Timebase.class))).then(returnsFirstArg());

    ps.setTimebase(CollectionTime.DIV1MS, 10, (short) 1);
  }

  /**
   * Test of setTimebase method, of class PicoScope.
   */
  @Test
  public void testSetTimebase_Timebase() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.setTimebase(new Timebase());
    });

    assertThrows(ConfigurationException.class, () -> {
      ps.setTimebase(new Timebase());
    });
  }

  /**
   * Test of getTimebase method, of class PicoScope.
   */
  @Test
  public void testGetTimebase() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.getTimebase();
    });

    Timebase tb = new Timebase();

    when(mockUnit.getTimebase()).thenReturn(tb);

    assertEquals(tb, ps.getTimebase());
  }

  /**
   * Test of setSignalGenerator method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetSignalGenerator_3args() throws Exception {
    ps.setSignalGenerator(WaveType.RAMPUP, 1, 1);
  }

  /**
   * Test of setSignalGenerator method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetSignalGenerator_4args() throws Exception {
    ps.setSignalGenerator(WaveType.RAMPUP, 0, 0, 0);
  }

  /**
   * Test of setSignalGenerator method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetSignalGenerator_9args() throws Exception {
    ps.setSignalGenerator(WaveType.RAMPUP, 0, 0, 0, SweepType.UP, 0, 0, 0, 0);
  }

  /**
   * Test of setSignalGenerator method, of class PicoScope.
   */
  @Test
  public void testSetSignalGenerator_GeneratorSettings() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.setSignalGenerator(new GeneratorSettings());
    });
  }

  /**
   * Test of runBlock method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testRunBlock() throws Exception {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.runBlock();
    });

    ps.runBlock();
  }

  /**
   * Test of getTimesAndValues method, of class PicoScope.
   */
  @Test
  public void testGetTimesAndValues() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.getTimesAndValues();
    });

    ResultSet dummy = new ResultSet();

    when(mockUnit.getTimesAndValues()).thenReturn(dummy);

    assertEquals(dummy, ps.getTimesAndValues());
  }

  /**
   * Test of setModeEts method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testSetModeEts_3args() throws Exception {
    ps.setModeEts(EtsMode.OFF, 10, 2);
  }

  /**
   * Test of setModeEts method, of class PicoScope.
   */
  @Test
  public void testSetModeEts_EtsSettings() {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.setModeEts(new EtsSettings());
    });
  }

  /**
   * Test of deactivateModeEts method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testDeactivateModeEts() throws Exception {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.deactivateModeEts();
    });

    ps.deactivateModeEts();

    when(mockUnit.getEtsSettings()).thenReturn(new EtsSettings());

    ps.deactivateModeEts();
  }

  /**
   * Test of runStreaming method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testRunStreaming() throws Exception {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.runStreaming();
    });

    ps.runStreaming();
  }

  /**
   * Test of registerStreamingCallback method, of class PicoScope.
   *
   * @throws java.lang.Exception if any error occur.
   */
  @Test
  public void testRegisterStreamingCallback() throws Exception {
    assertThrows(IllegalStateException.class, () -> {
      unopendPS.registerStreamingCallback(callback);
    });

    ps.registerStreamingCallback(callback);
  }

  /**
   * Test of toString method, of class PicoScope.
   */
  @Test
  public void testToString() {
    assertEquals("Unknown unconnected PicoScope", unopendPS.toString());
    assertEquals("Unknown unconnected PicoScope", ps.toString());

    UnitInfo ui = new UnitInfo();
    ui.setVariantInfo("1234");

    when(mockUnit.getInfo()).thenReturn(ui);

    assertEquals("PicoScope " + ui.getVariantInfo(), ps.toString());

    when(mockUnit.isOpen()).thenReturn(false);

    assertEquals("Unknown unconnected PicoScope", ps.toString());
  }
}
