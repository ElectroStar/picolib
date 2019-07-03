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
import com.github.electrostar.picolib.ResultSet;
import com.sun.jna.Pointer; // NOSONAR
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts the integer based values to float based voltages.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
class ResultSetConverter {

  private final int threadsPerCalc;
  private final int lostDataValue;
  private final int maxDataValue;
  private final int numberOfSamples;
  private final Pointer times;
  private final List<Pointer> channelDatas;
  private final List<ChannelSettings> channelSettings;

  ResultSetConverter(int numberOfSamples, 
          int maxDataValue,
          int lostDataValue,
          Pointer times, 
          List<Pointer> channelDatas,
          List<ChannelSettings> channelSettings) {
    this(numberOfSamples, maxDataValue, lostDataValue, times, channelDatas, channelSettings, 4);
  }

  ResultSetConverter(int numberOfSamples, 
          int maxDataValue,
          int lostDataValue,
          Pointer times, 
          List<Pointer> channelDatas, 
          List<ChannelSettings> channelSettings, 
          int threadsPerCalc) {
    this.numberOfSamples = numberOfSamples;
    this.maxDataValue = maxDataValue;
    this.lostDataValue = lostDataValue;
    this.times = times;
    this.channelDatas = channelDatas;
    this.channelSettings = channelSettings;
    
    // If wrong ThreadsPerCalc Value set it automatic to 1
    if (threadsPerCalc <= 0) {
      this.threadsPerCalc = 1;
    } else {
      this.threadsPerCalc = threadsPerCalc;
    }
      
  }

  public ResultSet convert() {
    ResultSet rs = new ResultSet();
    rs.setNumberOfSamples(numberOfSamples);
    if (numberOfSamples > 0) {
      if (null != times) {
        rs.setTimes(times.getIntArray(0, numberOfSamples));
      }

      List<Thread> threads = new ArrayList<>();

      int steps = numberOfSamples / threadsPerCalc;
      for (int i = 0; i < channelDatas.size(); i++) {
        convertChannelData(i, rs, steps, threads);
      }
      
      // Wait for all Threads to be finished
      threads.forEach(t -> {
        try {
          t.join();
        } catch (InterruptedException ex) {
          Logger.getLogger(PicoScope2000.class.getName()).log(Level.SEVERE, null, ex);
          Thread.currentThread().interrupt();
        }
      });
    }

    return rs;
  }

  private void convertChannelData(int i, ResultSet rs, int steps, List<Thread> threads) {
    Pointer channel = channelDatas.get(i);
    // Only Process when there is data
    if (null != channel) {
      // Calculate the multiplier for this channel
      ChannelSettings cs = channelSettings.get(i);
      float multiplier = ((float) cs.getRange().getValue() / 1000) / maxDataValue;

      // Create Float Array
      float[] convertedData = new float[numberOfSamples];

      switch (i) {
        case 0:
          rs.setChannelA(convertedData);
          break;
        case 1:
          rs.setChannelB(convertedData);
          break;
        case 2:
          rs.setChannelC(convertedData);
          break;
        case 3:
          rs.setChannelD(convertedData);
          break;
        default:
          break;
      }

      createThreads(steps, threads, channel, convertedData, multiplier);
    }
  }

  private void createThreads(int steps, 
          List<Thread> threads, 
          Pointer channel, 
          float[] convertedData, 
          float multiplier) {
    // Create Threads and start them
    for (int threadCnt = 0; threadCnt < threadsPerCalc; threadCnt++) {
      int start = threadCnt * steps;
      int end = (threadCnt + 1) * steps;
      if (threadCnt + 1 == threadsPerCalc) {
        end = numberOfSamples;
      }

      threads.add(calculateValues(start,
              end,
              channel.getShortArray(0, numberOfSamples),
              convertedData,
              multiplier));
    }
  }

  private Thread calculateValues(int start, 
          int end, 
          short[] values, 
          float[] target, 
          float multiplier) {
    Thread t = new Thread(new CalculateValuesTask(start,
                                                  end, 
                                                  lostDataValue, 
                                                  values, 
                                                  target, 
                                                  multiplier));
    t.start();
    return t;
  }
  
  private static class CalculateValuesTask implements Runnable {
    int start;
    int end;
    int lostDataValue;
    short[] values;
    float[] target;
    float multiplier;

    CalculateValuesTask(int start, 
                        int end, 
                        int lostDataValue, 
                        short[] values, 
                        float[] target, 
                        float multiplier) {
      this.start = start;
      this.end = end;
      this.lostDataValue = lostDataValue;
      this.values = values;
      this.target = target;
      this.multiplier = multiplier;
    }

    @Override
    public void run() {
      for (int i = start; i < end; i++) {
        if (values[i] == lostDataValue) {
          target[i] = Float.MAX_VALUE;
        } else {
          target[i] = multiplier * values[i];
        }
      }
    }
  }
}
