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

import com.github.electrostar.picolib.ChannelSettings;
import com.github.electrostar.picolib.OnDataCallback;
import com.github.electrostar.picolib.ResultSet;
import com.github.electrostar.picolib.Timebase;
import com.github.electrostar.picolib.library.PS2000CLibrary;
import com.sun.jna.Pointer; // NOSONAR
import com.sun.jna.ptr.PointerByReference; // NOSONAR
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Callback Function for the PicoScope of the 2000 series.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
class PS2000Callback implements PS2000CLibrary.GetOverviewBuffersMaxMin {

  private final OnDataCallback apiCallback;
  private final List<ChannelSettings> channels;
  private final int lostValue;
  private final int maxValue;
  private final Timebase timebase;

  /**
   * Constructs a {@code PS2000Callback} with an application callback and channel settings.
   * @param callback to the application.
   * @param channelSettings the settings of the channels.
   * @param lostValue the value on which sample data are mean to be lost.
   * @param maxValue the maximum value of the voltage range mapped as digital int value.
   * @param timebase the {@link com.github.electrostar.picolib.Timebase} to set the additional 
   *                 informations like {@link com.github.electrostar.picolib.TimeUnit} in the
   *                 {@link com.github.electrostar.picolib.ResultSet}.
   */
  public PS2000Callback(OnDataCallback callback, 
          List<ChannelSettings> channelSettings,
          int lostValue,
          int maxValue,
          Timebase timebase) {
    this.apiCallback = callback;
    this.channels = channelSettings;
    this.lostValue = lostValue;
    this.maxValue = maxValue;
    this.timebase = timebase;
  }

  @Override
  public void invoke(PointerByReference overviewBuffers, short overflow, int triggeredAt, 
          short triggered, short autoStop, int values) {
    if (null != apiCallback 
            && null != overviewBuffers 
            && values > 0 
            && null != channels
            && !channels.isEmpty()) {
      // Try to Process the Channel Informations
      // Get the Address (Pointer) of the First Element of the Array of Pointers

      // Get the Pointer which points to the Array of Pointers
      Pointer ptrArray = overviewBuffers.getPointer();
      // Convert to an Array
      Pointer[] pointers = ptrArray.getPointerArray(0, 2 * 4);

      List<Pointer> channelDatas = new ArrayList<>();
      
      for (int i = 0; i < channels.size(); i++) {
        channelDatas.add(pointers[i * 2]);
      }
      
      ResultSetConverter rsc = new ResultSetConverter(
              values, 
              maxValue,
              lostValue,
              null, 
              channelDatas, 
              channels);
      
      ResultSet rs = rsc.convert();
      rs.setCollectionTime(timebase.getCollectionTime());
      rs.setDivisions(timebase.getDivisions());
      rs.setTimeUnit(timebase.getTimeUnit());

      apiCallback.onDataEvent(rs);
    }
  }
}
