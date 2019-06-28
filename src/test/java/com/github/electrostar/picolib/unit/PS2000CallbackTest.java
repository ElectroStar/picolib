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

import com.github.electrostar.picolib.unit.PS2000Callback;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.electrostar.picolib.ChannelSettings;
import com.github.electrostar.picolib.CollectionTime;
import com.github.electrostar.picolib.Coupling;
import com.github.electrostar.picolib.OnDataCallback;
import com.github.electrostar.picolib.Range;
import com.github.electrostar.picolib.ResultSet;
import com.github.electrostar.picolib.TimeUnit;
import com.github.electrostar.picolib.Timebase;

/**
 * Tests for the {@link ResultSetConverter} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class PS2000CallbackTest {

  private static final int CHANNELS = 4;
  private static final int MAX_VALUE = 32767;
  private static final int LOST_VALUE = -32768;
  
  private PointerByReference buffers;
  private Timebase timebase;
  private List<ChannelSettings> channelSettings;
  private boolean indicator;
  
  private final OnDataCallback apiCallback = (ResultSet rs) -> {
    indicator = true;
    ResultSet result = new ResultSet();
    result.setNumberOfSamples(1);
    result.setCollectionTime(timebase.getCollectionTime());
    result.setDivisions(timebase.getDivisions());
    result.setTimeUnit(timebase.getTimeUnit());
    assertEquals(rs, result);
  };
  
  @BeforeEach
  public void setUp() {
    long size = Pointer.SIZE * 2 * CHANNELS;
    Memory pointers = new Memory(size);
    pointers.clear();
    
    buffers = new PointerByReference();
    buffers.setPointer(pointers);
    timebase = new Timebase(CollectionTime.DIV1MS);
    timebase.setTimeUnit(TimeUnit.NANOSECOND);
    indicator = false;
    
    channelSettings = new ArrayList<>();
    for(int i = 0; i < CHANNELS; i++) {
      channelSettings.add(new ChannelSettings(Range.RANGE_5V, Coupling.DC, true));
    }
  }
  
  @AfterEach
  public void tearDown() {
  }

  /**
   * Test of no api callback for invoke method of class PS2000Callback.
   */
  @Test
  public void testNoApiCallbackInvoke() {
    PS2000Callback callback = new PS2000Callback(null, channelSettings, 
                                                 LOST_VALUE, MAX_VALUE, timebase);
    callback.invoke(buffers, (short)0, 0, (short)0, (short)0, 5);
    
    assertFalse(indicator);
  }
  
  /**
   * Test of no channels for invoke method of class PS2000Callback.
   */
  @Test
  public void testNoChannelsInvoke() {
    PS2000Callback callback = new PS2000Callback(apiCallback, null, 
                                                 LOST_VALUE, MAX_VALUE, timebase);
    callback.invoke(buffers, (short)0, 0, (short)0, (short)0, 5);
    
    assertFalse(indicator);
  }
  
  /**
   * Test of empty channels for invoke method of class PS2000Callback.
   */
  @Test
  public void testEmptyChannelsInvoke() {
    channelSettings.clear();
    PS2000Callback callback = new PS2000Callback(apiCallback, channelSettings, 
                                                 LOST_VALUE, MAX_VALUE, timebase);
    callback.invoke(buffers, (short)0, 0, (short)0, (short)0, 5);
    
    assertFalse(indicator);
  }
  
  /**
   * Test of no values for invoke method of class PS2000Callback.
   */
  @Test
  public void testNoValuesInvoke() {
    PS2000Callback callback = new PS2000Callback(apiCallback, channelSettings, 
                                                 LOST_VALUE, MAX_VALUE, timebase);
    callback.invoke(buffers, (short)0, 0, (short)0, (short)0, 0);
    
    assertFalse(indicator);
  }
  
  /**
   * Test of no values for invoke method of class PS2000Callback.
   */
  @Test
  public void testNullDataInvoke() {
    PS2000Callback callback = new PS2000Callback(apiCallback, channelSettings, 
                                                 LOST_VALUE, MAX_VALUE, timebase);
    callback.invoke(null, (short)0, 0, (short)0, (short)0, 5);
    
    assertFalse(indicator);
  }
  
  /**
   * Test of working invoke method of class PS2000Callback.
   */
  @Test
  public void testInvoke() {
    PS2000Callback callback = new PS2000Callback(apiCallback, channelSettings, 
                                                 LOST_VALUE, MAX_VALUE, timebase);
    callback.invoke(buffers, (short)0, 0, (short)0, (short)0, 1);
    
    assertTrue(indicator);
  }
}
