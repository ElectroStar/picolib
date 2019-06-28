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

import com.github.electrostar.picolib.unit.ResultSetConverter;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.electrostar.picolib.ChannelSettings;
import com.github.electrostar.picolib.Coupling;
import com.github.electrostar.picolib.Range;
import com.github.electrostar.picolib.ResultSet;

/**
 * Tests for the {@link ResultSetConverter} class.
 *
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class ResultSetConverterTest {
  
  private static final int SAMPLES = 4;
  private static final int MAX_VALUE = 32767;
  private static final int MIN_VALUE = -32767;
  private static final int LOST_VALUE = -32768;
  
  private ResultSetConverter rsc;
  private List<Pointer> channelDatas;
  private List<ChannelSettings> channelSettings;
  private Pointer times;
  
  @BeforeEach
  public void setUp() {
    // Create Dummy Data for 4 Channels
    channelDatas = new ArrayList<>();
    channelSettings = new ArrayList<>();
    for(int i = 0; i < 5; i++) {
      Memory data = new Memory(Native.getNativeSize(Short.TYPE) * SAMPLES);
      data.setShort(0 * Native.getNativeSize(Short.TYPE), (short)MIN_VALUE);
      data.setShort(1 * Native.getNativeSize(Short.TYPE), (short)MAX_VALUE);
      data.setShort(2 * Native.getNativeSize(Short.TYPE), (short)LOST_VALUE);
      data.setShort(3 * Native.getNativeSize(Short.TYPE), (short)0);
      channelSettings.add(new ChannelSettings(Range.RANGE_5V, Coupling.DC, true));
      channelDatas.add(data);
    }
    
    times = new Memory(SAMPLES * Native.getNativeSize(Integer.TYPE));
    for(int i = 0; i < SAMPLES; i++) {
      times.setInt(i * Native.getNativeSize(Integer.TYPE), i);
    }
    
    rsc = new ResultSetConverter(SAMPLES, MAX_VALUE, LOST_VALUE, 
            times, channelDatas, channelSettings);
  }
  
  /**
   * Test of convert method, of class ResultSetConverter.
   */
  @Test
  public void testConvert() {
    // Test no Sample Data available
    ResultSet rs = new ResultSet();
    rs.setNumberOfSamples(0);
    
    ResultSetConverter noSamples = new ResultSetConverter(0, MAX_VALUE, LOST_VALUE, times, 
            channelDatas, channelSettings);
    
    assertEquals(rs, noSamples.convert());
    
    // Do that again with zero Thread
    noSamples = new ResultSetConverter(0, MAX_VALUE, LOST_VALUE, times, 
            channelDatas, channelSettings, 0);
    
    assertEquals(rs, noSamples.convert());
    
    // Do it the right way
    rs = new ResultSet();
    rs.setNumberOfSamples(SAMPLES);
    float[] data = new float[SAMPLES];
    data[0] = -5f;
    data[1] = 5f;
    data[2] = Float.MAX_VALUE;
    data[3] = 0f;
    rs.setChannelA(data);
    rs.setChannelB(data);
    rs.setChannelC(data);
    rs.setChannelD(data);
    
    ResultSetConverter noTimes = new ResultSetConverter(SAMPLES, MAX_VALUE, LOST_VALUE, null, 
            channelDatas, channelSettings);
    
    // Test with no times
    assertEquals(rs, noTimes.convert());
    
    int[] timeData = new int[SAMPLES];
    for(int i = 0; i < SAMPLES; i++) {
      timeData[i] = i;
    }
    rs.setTimes(timeData);
    
    // Test full with Times
    assertEquals(rs, rsc.convert());
  }
}
