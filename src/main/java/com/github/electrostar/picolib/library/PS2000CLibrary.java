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

package com.github.electrostar.picolib.library;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;

/**
 * Interface for the PS2000 Library.
 * The C Definitions are in the {@code ps2000.h} file.
 */
public interface PS2000CLibrary extends Library, PicoLibrary {

  /**
   * Instance of the PS2000 C Library.
   * The instance will be automatically loaded with jna on init.
   */
  PS2000CLibrary INSTANCE = (PS2000CLibrary) Native.loadLibrary(
          "ps2000", PS2000CLibrary.class
  );

  /**
   * Prototype Method to open the device unit.
   * This function opens a PicoScope 2000 Series oscilloscope. The driver can support up to 64 
   * oscilloscopes.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_open_unit
   * (
   *    void
   * )
   * </code>
   * </pre>
   * @return {@code -1} if the oscilloscope fails to open.<br>
   *         {@code 0} if no oscilloscope is found.<br>
   *         {@code >0} f the oscilloscope opened. Use this as thehandle argument for 
   *         all subsequent API calls for this oscilloscope.
   */
  short ps2000_open_unit();

  /**
   * Prototype Method to close the device unit.
   * Shuts down a PicoScope 2000 Series oscilloscope.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_close_unit
   * (
   *    int16_t handle
   * )
   * </code>
   * </pre>
   * @param handle the handle, returned by {@link #ps2000_open_unit()}, 
   *               of the oscilloscope being closed
   * @return <code>non-zero</code> if handle is valid, <code>0</code> otherwise.
   */
  short ps2000_close_unit(short handle);

  /**
   * Prototype Method to receive device unit informations.
   * This function writes oscilloscope information to a character string. If the oscilloscopefailed 
   * to open, only <code>line</code> types 0 and 6 are available to explain why the last open 
   * unitcall failed. 
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * uint32_t ps2000_get_unit_info 
   * (
   *    int16_t handle, 
   *    int8_t *string, 
   *    int16_t stringLength, 
   *    int16_t *requiredSize, 
   *    PICO_INFO info
   * )
   * </code>
   * </pre>
   * @param handle  the handle of the oscilloscope from which information isrequired. If an invalid 
   *                handle is passed, the error code from the last oscilloscope that failed to open 
   *                is returned.
   * @param string  a pointer to the character string buffer in the callingfunction where the 
   *                function will write the oscilloscope informationstring selected with <code>line
   *                </code>. If string is <code>NULL</code>, no information will bewritten.
   * @param stringLength  the length of the character string buffer. If thestring is not long enough
   *                      to accept all of the information, only thefirst <code>string_length</code>
   *                      characters are returned.
   * @param info    a value selected from enumerated type 
   *                {@link com.github.electrostar.picolib.PicoInfo} specifying what information is 
   *                required from the driver.
   * @return        The length of the string written to the <code>string</code> buffer.<br>
   *                <code>0</code> if one of the parameters is out of range or 
   *                string is <code>NULL</code>
   */
  short ps2000_get_unit_info(short handle, 
          byte[] string, 
          short stringLength, 
          short info);

  /**
   * Prototype Method to open the device unit asynchronously.
   * This function opens a PicoScope 2000 Series oscilloscope without waiting for theoperation to 
   * finish. You can find out when it has finished by periodically calling 
   * {@link #ps2000_open_unit_progress(com.sun.jna.ptr.ShortByReference, 
   * com.sun.jna.ptr.ShortByReference) ps2000_open_unit_progress} until that function returns a 
   * non-zero value and avalid oscilloscope handle.
   * The driver can support up to 64 oscilloscopes.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_open_unit_async
   * (
   *    void
   * )
   * </code>
   * </pre>
   * @return {@code 0} if there is a previous open operation in progress.<br>
   *         {@code non-zero} if the call has successfully initiated an open operation.
   */
  short ps2000_open_unit_async();

  /**
   * Prototype Method to get the handle from an asynchronous open operation.
   * This function checks on the progress of {@link #ps2000_open_unit_async() 
   * ps2000_open_unit_async}.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_open_unit_progress
   * (
   *    int16_t *handle,
   *    int16_t *progress_percent
   * )
   * </code>
   * </pre>
   * @param handle a pointer to where the function should store the handle ofthe opened oscilloscope
   *               {@code 0} if no oscilloscope is found or the oscilloscope fails to open,handle 
   *               of oscilloscope (valid only if function returns {@code 1})
   * @param progressPercent a pointer to an estimate of the progresstowards opening the 
   *                        oscilloscope. The function will write a valuefrom 0 to 100, where 100 
   *                        implies that the operation is complete.
   * @return {@code >0} if the driver successfully opens the oscilloscope.<br>
   *         {@code 0} if opening still in progress.<br>
   *         {@code -1} if the oscilloscope failed to open or was not found.
   */
  short ps2000_open_unit_progress(ShortByReference handle, 
          ShortByReference progressPercent);

  /**
   * Prototype Method to set channel options.
   * Specifies if a channel is to be enabled, the AC/DC coupling mode and the input range.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_set_channel
   * (
   *    int16_t handle,
   *    int16_t channel,
   *    int16_t enabled,
   *    int16_t dc,
   *    int16_t range
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param channel an enumerated type specifying the channel. Use 
   *                {@link com.github.electrostar.picolib.Channel#CHANNEL_A CHANNEL_A (0)} or 
   *                {@link com.github.electrostar.picolib.Channel#CHANNEL_B CHANNEL_B (1)}.
   * @param enabled specifies if the channel is active:<ul><li>{@code TRUE}: active</li><li>
   *                {@code FALSE}: inactive</li></ul>
   * @param dc specifies the AC/DC coupling mode:<ul><li>{@code TRUE}: DC coupling</li><li>
   *           {@code FALSE}: AC coupling</li></ul>
   * @param range a code between 1 and 10. See the {@link com.github.electrostar.picolib.Range 
   *              Range}, but note that each oscilloscope variant supports only a subset of these 
   *              ranges.
   * @return {@code 0} if unsuccessful, or if one or more of the arguments are out of range.<br>
   *         {@code non-zero} if successful.
   */
  short ps2000_set_channel(short handle, 
          short channel, 
          short enabled, 
          short dc,
          short range);

  /**
   * Prototype Method to set ETS mode and parameters.
   * This function is used to enable or disable ETS mode and to set the ETS parameters.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int32_t ps2000_set_ets
   * (
   *    int16_t handle,
   *    int16_t mode,
   *    int16_t ets_cycles,
   *    int16_t ets_interleave
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param mode <ul>
   *             <li>
   *              {@link com.github.electrostar.picolib.EtsMode#OFF OFF (0)}: diables ETS
   *             </li>
   *             <li>
   *              {@link com.github.electrostar.picolib.EtsMode#FAST FAST (1)}: enables ETS and 
   *              provides {@code ets_cycles} cycles of data, which may contain data from previously
   *              returned cycles
   *             </li>
   *             <li>
   *              {@link com.github.electrostar.picolib.EtsMode#SLOW SLOW (2)}: enables ETS and 
   *              provides fresh data every {@code ets_cycles} cycles. 
   *              {@link com.github.electrostar.picolib.EtsMode#SLOW} takes longer to provide each 
   *              data set, but the data sets are more stable and unique.
   *             </li>
   *             </ul>
   * @param ets_cycles the number of cycles to store. The computer can then select 
   *                   {@code ets_interleave} cycles to give the most uniform spread of samples.
   *                   {@code ets_cycles} should be between two and five times the value of 
   *                   {@code ets_interleave}. 
   * @param ets_interleave the number of ETS interleaves to use. If thesample time is 20 ns and the 
   *                       interleave 10, the approximate timeper sample will be 2 ns.
   * @return The effective sample time in picoseconds, if ETS is enabled.<br>
   *         {@code 0} if ETS is disabled or one of the parameters is out of range
   */
  int ps2000_set_ets(short handle, 
          short mode, 
          short ets_cycles, 
          short ets_interleave);

  /**
   * Prototype Method to set the Timebase.
   * This function discovers which timebases are available on the oscilloscope. You should set up 
   * the channels using {@link #ps2000_set_channel(short, short, short, short, short) 
   * ps2000_set_channel} and, if required, ETS mode using 
   * {@link #ps2000_set_ets(short, short, short, short) ps2000_set_ets} first. 
   * Then call this function with increasing values of timebase, starting from 0, until you find a 
   * timebase with a sampling interval and sample count close enough to your requirements.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_get_timebase
   * (
   *    int16_t handle,
   *    int32_t timebase,
   *    int32_t no_of_samples,
   *    int32_t *time_interval,
   *    int16_t *time_units,
   *    int16_t oversample,
   *    int32_t *max_samples
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope
   * @param timebase a code between 0 and the maximum timebase (depending on the oscilloscope). 
   *                 Timebase 0 is the fastest timebase. Each successive timebase has twice the 
   *                 sampling interval of the previous one.
   * @param no_of_samples the number of samples that you require. The function uses this value to 
   *                      calculate the most suitable time unit to use.
   * @param time_interval on exit, this location will contain the time interval, in nanoseconds, 
   *                      between readings at the selected timebase. If {@code time_interval} 
   *                      is {@code NULL}, the function will write nothing.
   * @param time_units on exit, this location will contain an enumerated type indicating the most 
   *                   suitable unit for expressing sample times. You should pass this value to 
   *                   {@link #ps2000_get_times_and_values(short, com.sun.jna.Memory, 
   *                   com.sun.jna.Memory, com.sun.jna.Memory, com.sun.jna.Memory, 
   *                   com.sun.jna.Memory, com.sun.jna.ptr.ShortByReference, short, int) 
   *                   ps2000_get_times_and_values}. If {@code time_units} is null, the function 
   *                   will write nothing.
   * @param oversample the amount of oversample required. For example, an oversample of 4 results 
   *                   in a {@code time_interval} 4 times larger and a {@code max_samples} 4 times 
   *                   smaller. At the same time it increases the effective resolution by one bit. 
   *                   See <a href="https://www.picotech.com/download/manuals/picoscope-2000-series-programmers-guide.pdf#%5B%7B%22num%22%3A573%2C%22gen%22%3A0%7D%2C%7B%22name%22%3A%22XYZ%22%7D%2C57%2C770%2C0%5D">Oversampling</a> 
   *                   for more details.
   * @param max_samples on exit, the maximum number of samples available.The scope allocates a 
   *                    certain amount of memory for internal overheads and this may vary depending 
   *                    on the number of channels enabled, the timebase chosen and the oversample 
   *                    multiplier selected. If {@code max_samples} is {@code NULL}, the function 
   *                    will write nothing.
   * @return {@code non-zero} if all parameters are in range, {@code 0} on error.
   */
  short ps2000_get_timebase(short handle, 
          short timebase, 
          int no_of_samples, 
          IntByReference time_interval, 
          ShortByReference time_units, 
          short oversample, 
          IntByReference max_samples);

  /**
   * Prototype Method to set the trigger.
   * This function is used to enable or disable basic triggering and set its parameters. 
   * Triggering is available in {@link #ps2000_run_block(short, int, short, 
   * short, com.sun.jna.ptr.IntByReference) block mode} and {@link #ps2000_run_streaming_ns(short, 
   * int, short, int, short, int, int) fast streaming mode} only.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_set_trigger
   * (
   *    int16_t handle,
   *    int16_t source,
   *    int16_t threshold,
   *    int16_t direction,
   *    int16_t delay,
   *    int16_t auto_trigger_ms
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param source where to look for a trigger. Use 
   *               {@link com.github.electrostar.picolib.Channel#CHANNEL_A CHANNEL_A (0)}, 
   *               {@link com.github.electrostar.picolib.Channel#CHANNEL_B CHANNEL_B (1)} 
   *               or {@link com.github.electrostar.picolib.Channel#NONE NONE(5)}. The number of 
   *               channels available depends on the oscilloscope.
   * @param threshold the threshold for the trigger event. This is scaled in 16-bit ADC counts at 
   *                  the currently selected range.
   * @param direction use {@link com.github.electrostar.picolib.TriggerDirection#RISING RISING (0)} 
   *                 or {@link com.github.electrostar.picolib.TriggerDirection#FALLING FALLING (1)}.
   * @param delay the delay, as a percentage of the requested number of data points, between the 
   *              trigger event and the start of the block. It should be in the range -100% to 
   *              +100%. Thus, 0% means that the trigger event is at the first data value in the 
   *              block, and -50% means that it is in the middle of the block. If you wish to 
   *              specify the delay as a floating-point value, use 
   *              {@link #ps2000_set_trigger2(short, short, short, short, float, short) 
   *              ps2000_set_trigger2} instead. Note that if delay = 0 and you call 
   *              {@link #ps2000_stop(short) ps2000_stop} before a trigger event occurs, the device 
   *              will return no data.
   * @param auto_trigger_ms the delay in milliseconds after which the oscilloscope will collect 
   *                        samples if no trigger event occurs. If this is set to zero the 
   *                        oscilloscope will wait for a trigger indefinitely.
   * @return {@code non-zero} if successfull, {@code 0} if one of the parameters is out of range.
   */
  short ps2000_set_trigger(short handle, 
          short source, 
          short threshold, 
          short direction, 
          short delay, 
          short auto_trigger_ms);

  /**
   * Prototype Method to set the trigger.
   * This function is used to enable or disable triggering and set its parameters. It has the same 
   * behavior as {@link #ps2000_set_trigger(short, short, short, short, short, short) 
   * ps2000_set_trigger}, except that the {@code delay} parameter is a floating-point value. 
   * Triggering is available in {@link #ps2000_run_block(short, int, short, 
   * short, com.sun.jna.ptr.IntByReference) block mode} and {@link #ps2000_run_streaming_ns(short, 
   * int, short, int, short, int, int) fast streaming mode} only.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_set_trigger
   * (
   *    int16_t handle,
   *    int16_t source,
   *    int16_t threshold,
   *    int16_t direction,
   *    float delay,
   *    int16_t auto_trigger_ms
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param source where to look for a trigger. Use 
   *               {@link com.github.electrostar.picolib.Channel#CHANNEL_A CHANNEL_A (0)}, 
   *               {@link com.github.electrostar.picolib.Channel#CHANNEL_B CHANNEL_B (1)} 
   *               or {@link com.github.electrostar.picolib.Channel#NONE NONE(5)}. The number of 
   *              channels available depends on the oscilloscope.
   * @param threshold the threshold for the trigger event. This is scaled in 16-bit ADC counts at 
   *                  the currently selected range.
   * @param direction use {@link com.github.electrostar.picolib.TriggerDirection#RISING RISING (0)} 
   *                 or {@link com.github.electrostar.picolib.TriggerDirection#FALLING FALLING (1)}.
   * @param delay the delay, as a percentage of the requested number of data points, between the 
   *              trigger event and the start of the block. It should be in the range -100% to 
   *              +100%. Thus, 0% means that the trigger event is at the first data value in the 
   *              block, and -50% means that it is in the middle of the block. If you wish to 
   *              specify the delay as an integer, use 
   *              {@link #ps2000_set_trigger(short, short, short, short, short, short)  
   *              ps2000_set_trigger} instead. Note that if delay = 0 and you call 
   *              {@link #ps2000_stop(short) ps2000_stop} before a trigger event occurs, the device 
   *              will return no data.
   * @param auto_trigger_ms the delay in milliseconds after which the oscilloscope will collect 
   *                        samples if no trigger event occurs. If this is set to zero the 
   *                        oscilloscope will wait for a trigger indefinitely.
   * @return {@code non-zero} if successfull, {@code 0} if one of the parameters is out of range.
   */
  short ps2000_set_trigger2(short handle, 
          short source, 
          short threshold, 
          short direction, 
          float delay, 
          short auto_trigger_ms);

  /**
   * Prototype Method to run the oscilloscope in block mode.
   * This function tells the oscilloscope to start collecting data in block mode.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_run_block 
   * (
   *    int16_t handle,
   *    int32_t no_of_values,
   *    int16_t timebase,
   *    int16_t oversample,
   *    int32_t *time_indisposed_ms
   * )
   * </code>
   * </pre>
   * @param handle the oscilloscope of the required oscilloscope.
   * @param no_of_values the number of samples to return.
   * @param timebase a code between 0 and the maximum timebase available (consult the driver header 
   *                 file). Timebase 0 gives the maximum sample rate available, timebase 1 selects 
   *                 a sample rate half as fast, timebase 2 is half as fast again and so on. For 
   *                 the maximum sample rate, see the specifications for your oscilloscope. The 
   *                 number of channels enabled may affect the availability of the fastest 
   *                 timebases.
   * @param oversample the oversampling factor, a number between 1 and 256.
   *                   See <a href="https://www.picotech.com/download/manuals/picoscope-2000-series-programmers-guide.pdf#%5B%7B%22num%22%3A573%2C%22gen%22%3A0%7D%2C%7B%22name%22%3A%22XYZ%22%7D%2C57%2C770%2C0%5D">Oversampling</a> 
   *                   for details.
   * @param time_indisposed_ms a pointer to the approximate time, in milliseconds, that the ADC 
   *                           will take to collect data. If a trigger is set, it is the amount of 
   *                           time the ADC takes to collect a block of data after a trigger event, 
   *                           calculated as (sample interval) x (number ofpoints required). The 
   *                           actual time may differ from computer to computer, depending on how 
   *                           quickly the computer can respond to I/O requests.
   * @return {@code non-zero} if successfull, {@code 0} if one of the parameters is out of range.
   */
  short ps2000_run_block(short handle, 
          int no_of_values, 
          short timebase, 
          short oversample, 
          IntByReference time_indisposed_ms);

  /**
   * Prototype Method to check if the oscilloscope has new data ready.
   * This function polls the driver to see if the oscilloscope has finished the last 
   * datacollection operation.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_ready
   * (
   *    int16_t handle
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @return <ul>
   *         <li>{@code >0}: if ready. The oscilloscope has collected a complete block of data or 
   *         the auto trigger timeout has been reached.</li>
   *         <li>{@code 0}: if not ready. An invalid handle was passed, or the oscilloscope is in 
   *         streaming mode, or the oscilloscope is still collecting data in block mode.</li>
   *         <li>{@code -1}: f the oscilloscope is not attached. The USB transfer failed, 
   *         indicating that the oscilloscope may have been unplugged.</li>
   *         </ul>
   */
  short ps2000_ready(short handle);

  /**
   * Prototype Method to stop the oscilloscope of sampling data.
   * <p>
   * Call this function to stop the oscilloscope sampling data. When running the device in 
   * {@link #ps2000_run_streaming_ns(short, int, short, int, short, int, int) streaming mode}, you 
   * should always call this function afterthe end of a capture to ensure that the scope is ready 
   * for the next capture.
   * </p>
   * <p>
   * When running the device in {@link #ps2000_run_block(short, int, short, short, 
   * com.sun.jna.ptr.IntByReference) block mode} or ETS mode, you can call this function to 
   * interrupt data capture.
   * </p>
   * <p>
   * Note that if you are using block mode or ETS mode and call this function before the 
   * oscilloscope is ready, no capture will be available and the driver will not return any samples.
   * </p>
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_stop
   * (
   *    int16_t handle
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @return {@code non-zero} if successfull, {@code 0} if one of the parameters is out of range.
   */
  short ps2000_stop(short handle);

  /**
   * Prototype Method to get the collected data from oscilloscope.
   * This function is used to get values in block mode after calling 
   * {@link #ps2000_run_block(short, int, short, short, com.sun.jna.ptr.IntByReference) 
   * ps2000_run_block}. 
   * <p>
   * Note that if you are using block mode or ETS mode and call this function before the 
   * oscilloscope is ready, no capture will be available and the driver will not return any samples.
   * </p>
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int32_t ps2000_get_values
   * (
   *    int16_t handle,
   *    int16_t *buffer_a,
   *    int16_t *buffer_b,
   *    int16_t *buffer_c,
   *    int16_t *buffer_d,
   *    int16_t *overflow,
   *    int32_t no_of_values
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param buffer_a pointer to the buffer that receive datafrom the specified channel A. A pointer 
   *                 is not used if the oscilloscope is not collecting data from that channel. If 
   *                 a pointer is {@code NULL}, nothing will be written to it.
   * @param buffer_b pointer to the buffer that receive datafrom the specified channel B. A pointer 
   *                 is not used if the oscilloscope is not collecting data from that channel. If 
   *                 a pointer is {@code NULL}, nothing will be written to it.
   * @param buffer_c not used.
   * @param buffer_d not used.
   * @param overflow on exit, contains a bit pattern indicating whether an overflow has occurred 
   *                 and, if so, on which channel. Bit 0 is the least significant bit. The bit 
   *                 assignments are as follows: 
   *                 <ul>
   *                 <li>Bit 0: Channel A overflow</li>
   *                 <li>Bit 1: Channel B overflow</li>
   *                 </ul>
   * @param no_of_values the number of data points to return. In streamingmode, this is the 
   *                     maximum number of values to return.
   * @return The actual number of data values per channel returned, which maybe less than
   *         {@code no_of_values} if streaming.<br>
   *         {@code 0}: if one of the parameters is out of range or the oscilloscope is not in a 
   *         suitable mode.
   */
  int ps2000_get_values(short handle, 
          Memory buffer_a, 
          Memory buffer_b, 
          Memory buffer_c, 
          Memory buffer_d, 
          ShortByReference overflow, 
          int no_of_values);

  /**
   * Prototype Method to get the collected data and times from oscilloscope.
   * This function is used to get values and times in block mode after calling 
   * {@link #ps2000_run_block(short, int, short, short, com.sun.jna.ptr.IntByReference) 
   * ps2000_run_block}. 
   * <p>
   * Note that if you are using block mode or ETS mode and call this function before the 
   * oscilloscope is ready, no capture will be available and the driver will not return any samples.
   * </p>
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int32_t ps2000_get_times_and_values
   * (
   *    int16_t handle,
   *    int32_t *times,
   *    int16_t *buffer_a,
   *    int16_t *buffer_b,
   *    int16_t *buffer_c,
   *    int16_t *buffer_d,
   *    int16_t *overflow,
   *    int16_t time_units,
   *    int32_t no_of_values
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param times a pointer to a buffer for the sample times in {@code time_units}. Each time is 
   *              the interval between the trigger event and the corresponding sample. Times before 
   *              the trigger event are negative, and times after the trigger event are positive.
   * @param buffer_a pointer to the buffer that receive datafrom the specified channel A. A pointer 
   *                 is not used if the oscilloscope is not collecting data from that channel. If 
   *                 a pointer is {@code NULL}, nothing will be written to it.
   * @param buffer_b pointer to the buffer that receive datafrom the specified channel B. A pointer 
   *                 is not used if the oscilloscope is not collecting data from that channel. If 
   *                 a pointer is {@code NULL}, nothing will be written to it.
   * @param buffer_c not used.
   * @param buffer_d not used.
   * @param overflow on exit, contains a bit pattern indicating whether an overflow has occurred 
   *                 and, if so, on which channel. Bit 0 is the least significant bit. The bit 
   *                 assignments are as follows: 
   *                 <ul>
   *                 <li>Bit 0: Channel A overflow</li>
   *                 <li>Bit 1: Channel B overflow</li>
   *                 </ul>
   * @param time_units can be one of the following:
   *                   <ul>
   *                   <li>{@link com.github.electrostar.picolib.TimeUnit#FEMTOSECOND 
   *                        FEMTOSECOND (0)}</li>
   *                   <li>{@link com.github.electrostar.picolib.TimeUnit#PICOSECOND 
   *                        PICOSECOND (1)}</li>
   *                   <li>{@link com.github.electrostar.picolib.TimeUnit#NANOSECOND 
   *                        NANOSECOND (2)}</li>
   *                   <li>{@link com.github.electrostar.picolib.TimeUnit#MICROSECOND 
   *                        MICROSECOND (3)}</li>
   *                   <li>{@link com.github.electrostar.picolib.TimeUnit#MILLISECOND 
   *                        MILLISECOND (4)}</li>
   *                   <li>{@link com.github.electrostar.picolib.TimeUnit#SECOND SECOND (5)}</li>
   *                   </ul>
   * @param no_of_values the number of data points to return. In streaming mode, this is the 
   *                     maximum number of values to return.
   * @return The actual number of data values per channel returned, which maybe less than
   *         {@code no_of_values} if streaming.<br>
   *         {@code 0}: f one or more of the parameters are out of range, if the times will 
   *         overflow with the {@code time_units} requested (use 
   *         {@link #ps2000_get_timebase(short, short, int, com.sun.jna.ptr.IntByReference, 
   *         com.sun.jna.ptr.ShortByReference, short, com.sun.jna.ptr.IntByReference) 
   *         ps2000_get_timebase} to acquire the most suitable {@code time_units}) or if the 
   *         oscilloscope is in streaming mode
   */
  int ps2000_get_times_and_values(short handle, 
          Memory times, 
          Memory buffer_a, 
          Memory buffer_b, 
          Memory buffer_c, 
          Memory buffer_d, 
          ShortByReference overflow, 
          short time_units, 
          int no_of_values);

  /**
   * Prototype Method to set the build in signal generator of the oscilloscope.
   * This function sets up the signal generator to produce a signal from a list of built-in 
   * waveforms. If different start and stop frequencies are specified, the oscilloscope will sweep 
   * either up, down or up and down.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_set_sig_gen_built_in
   * (
   *    int16_t handle,
   *    int32_t offsetVoltage,
   *    uint32_t pkToPk,
   *    PS2000_WAVE_TYPE waveType,
   *    float startFrequency,
   *    float increment,
   *    float dwellTime,
   *    PS2000_SWEEP_TYPE sweepType,
   *    uint32_t sweeps
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param offsetVoltage the voltage offset, in microvolts, to be applied to the waveform.
   * @param pkToPk the peak-to-peak voltage, in microvolts, of the waveform signal.
   * @param waveType the type of waveform to be generated by the oscilloscope. 
   *                 See {@link com.github.electrostar.picolib.WaveType Wave Types}.
   * @param startFrequency the frequency at which the signal generator should begin. For allowable 
   *                       values see {@code ps2000.h}.
   * @param stopFrequency the frequency at which the sweep should reverse direction or return to 
   *                      the start frequency.
   * @param increment the amount by which the frequency rises or falls every {@code dwellTime} 
   *                  seconds in sweep mode.
   * @param dwellTime the time in seconds between frequency changes in sweep mode.
   * @param sweepType specifies whether the frequency should sweep from {@code startFrequency} to 
   *                  {@code stopFrequency}, or in the opposite direction, or repeatedly reverse 
   *                  direction. Use one of these values of the enumerated type 
   *                  {@link com.github.electrostar.picolib.SweepType SweepType}:
   *                  <ul>
   *                  <li>{@link com.github.electrostar.picolib.SweepType#UP UP (0)}</li>
   *                  <li>{@link com.github.electrostar.picolib.SweepType#DOWN DOWN (1)}</li>
   *                  <li>{@link com.github.electrostar.picolib.SweepType#UPDOWN UPDOWN (2)}</li>
   *                  <li>{@link com.github.electrostar.picolib.SweepType#DOWNUP DOWNUP (3)}</li>
   *                  </ul>
   * @param sweeps the number of times to sweep the frequency.
   * @return {@code non-zero} if successfull, {@code 0} if one of the parameters is out of range.
   */
  short ps2000_set_sig_gen_built_in(short handle, 
          int offsetVoltage, 
          int pkToPk, 
          int waveType, 
          float startFrequency, 
          float stopFrequency, 
          float increment, 
          float dwellTime, 
          int sweepType, 
          int sweeps);

  /**
   * Prototype Method to start the streaming mode of the oscilloscope.
   * This function tells the oscilloscope to start collecting data in fast streaming mode. It 
   * returns immediately without waiting for data to be captured. After calling it, you should 
   * next call {@link #ps2000_get_streaming_last_values(short, 
   * com.github.electrostar.picolib.library.PS2000CLibrary.GetOverviewBuffersMaxMin) 
   * ps2000_get_streaming_last_values} to copy the data to your application's buffer.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_run_streaming_ns
   * (
   *    int16_t handle,
   *    uint32_t smaple_interval,
   *    in16_t time_units,
   *    uint32_t max_samples,
   *    int16_t auto_stop,
   *    uint32_t noOfSamplesPeAggregate,
   *    uint32_t overview_buffer_size
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param sample_interval the time interval, in {@code time_units}, between data points.
   * @param time_units the units in which {@code sample_interval} is measured.
   * @param max_samples the maximum number of samples that the driver should store from each 
   *                    channel. Your computer must have enough physical memory for this many 
   *                    samples, multiplied by the number of channels in use, multiplied by the 
   *                    number of bytes per sample.
   * @param auto_stop a Boolean to indicate whether streaming should stop automatically when 
   *                  {@code max_samples} is reached. Set to any non-zero value for {@code TRUE}.
   * @param noOfSamplesPerAggregate the number of incoming samples that the driver will merge 
   *                                together to create each value pair passed to the application. 
   *                                The value mustbe between 1 and {@code max_samples}.
   * @param overview_buffer_size the size of the overview buffers, temporary buffers used by the 
   *                             driver to store data before passing it to your application. You 
   *                             can check for overview buffer overruns using the 
   *                             {@link #ps2000_overview_buffer_status(short, 
   *                             com.sun.jna.ptr.ShortByReference) ps2000_overview_buffer_status} 
   *                             function and adjust the overview buffer size if necessary. The 
   *                             maximum allowable value is 1,000,000. We recommend using an 
   *                             initial value of 15,000 samples.
   * @return {@code non-zero} if streaming has been enabled correctly, {@code 0}: if a problem 
   *         occurred or a value was out of range.
   */
  short ps2000_run_streaming_ns(short handle, 
          int sample_interval, 
          short time_units, 
          int max_samples, 
          short auto_stop, 
          int noOfSamplesPerAggregate, 
          int overview_buffer_size);

  /**
   * Prototype Method to register a callback function to receive data from streaming mode.
   * This function is used to collect the next block of values while fast streaming is running. 
   * You must call {@link #ps2000_run_streaming_ns(short, int, short, int, short, int, int) 
   * ps2000_run_streaming_ns} beforehand to set up fast streaming.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_get_streaming_last_values )
   * (
   *    int16_t handle,
   *    GetOverviewBuffersMaxMin lpGetOverviewBuffersMaxMin
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param func a pointer to the {@link GetOverviewBuffersMaxMin} callback function in your 
   *             applicationthat receives data from the streaming driver.
   * @return {@code 1} if the callback will be called, {@code 0} f the callback will not be called,
   *         either because one of the inputs is out of range or because there are no samples 
   *         available.
   */
  short ps2000_get_streaming_last_values(short handle, 
          GetOverviewBuffersMaxMin func);

  // C prototype definition:
  // 

  /**
   * Prototype Method to get the status of the buffers.
   * This function indicates whether or not the overview buffers used by 
   * {@link #ps2000_run_streaming_ns(short, int, short, int, short, int, int) 
   * ps2000_run_streaming_ns} have overrun. If an overrun occurs, you can choose to increase the 
   * {@code overview_buffer_size} argument that you pass in the next call to 
   * {@link #ps2000_run_streaming_ns(short, int, short, int, short, int, int) 
   * ps2000_run_streaming_ns}.
   * <p>
   * C definition:
   * </p>
   * <pre>
   * <code>
   * int16_t ps2000_overview_buffer_status
   * (
   *    int16_t handle,
   *    int16_t *previous_buffer_overrun
   * )
   * </code>
   * </pre>
   * @param handle the handle of the required oscilloscope.
   * @param previous_buffer_overrun a pointer to a Boolean indicating whether the overview buffers 
   *                                have overrun. The function will write a non-zero value to 
   *                                indicate a buffer overrun.
   * @return {@code 0} if the function was successful, {@code 1} if the function failed due to an
   *         invalid handle.
   */
  short ps2000_overview_buffer_status(short handle, 
          ShortByReference previous_buffer_overrun);

  // End of Method definitions

  /**
   * Interface for a prototype Callback to receive data from the driver.
   */
  interface GetOverviewBuffersMaxMin extends Callback {

    /**
     * Prototype Callback Method to receive data from the driver.
     * This is the callback function in your application that receives data from the driver in fast 
     * streaming mode. You pass a pointer to this function to 
     * {@link #ps2000_get_streaming_last_values(short, 
     * com.github.electrostar.picolib.library.PS2000CLibrary.GetOverviewBuffersMaxMin) 
     * ps2000_get_streaming_last_values}, which then calls it back when the data is ready. Your 
     * callback function should do nothing more than copy the data to another buffer within your 
     * application. To maintain the best application performance, the function should return as 
     * quickly as possible without attempting to process or display the data.
     * <p>
     * C definition:
     * </p>
     * <pre>
     * <code>
     * void *GetOverviewBuffersMaxMin
     * (
     *    int16_t **overviewBuffers,
     *    int16_t overflow,
     *    uint32_t triggeredAt,
     *    int16_t triggered,
     *    int16_t auto_stop,
     *    uint32_t nValues
     * )
     * </code>
     * </pre>
     * @param overviewBuffers a pointer to a location where 
     *                     {@link #ps2000_get_streaming_last_values(short, 
     *               com.github.electrostar.picolib.library.PS2000CLibrary.GetOverviewBuffersMaxMin)
     *                      ps2000_get_streaming_last_values} will store a pointer to its 
     *                      {@code overview buffers} that contain the sampled data. The driver 
     *                      createsthe overview buffers when you call 
     *                      {@link #ps2000_run_streaming_ns(short, int, short, int, short, int, int)
     *                      ps2000_run_streaming_ns} to start fast streaming. 
     *                      {@code overviewBuffers} is a two dimensional array containing an array 
     *                      of length nValues for each channel 
     *                      ({@code overviewBuffers[4][nValues]}). Disabled channels return a null 
     *                      pointer resulting in four overview pointers whether all channels are 
     *                      enabled or not.
     *                      <ul>
     *                      <li>{@code overviewBuffer[0]} ch_a_max</li>
     *                      <li>{@code overviewBuffer[1]} ch_a_min</li>
     *                      <li>{@code overviewBuffer[2]} ch_b_max</li>
     *                      <li>{@code overviewBuffer[3]} ch_b_min</li>
     *                      </ul>
     * @param overflow a bit field that indicates whether there has been a voltage overflow and, 
     *                 if so, on which channel. The bit assignments are as follows:
     *                 <ul>
     *                 <li>{@code Bit 0}: Channel A overflow</li>
     *                 <li>{@code Bit 1}: Channel B overflow</li>
     *                 </ul>
     * @param triggeredAt an index into the overview buffers, indicating the sample at the trigger 
     *                    event. Valid only when triggered is {@code TRUE}.
     * @param triggered a Boolean indicating whether a trigger event has occurred and 
     *                  {@code triggeredAt} is valid. Any non-zero value signifies {@code TRUE}.
     * @param auto_stop a Boolean indicating whether streaming data capture has automatically 
     *                  stopped. Any non-zero value signifies {@code TRUE}.
     * @param nValues the number of values in each overview buffer.
     */
    void invoke(PointerByReference overviewBuffers,
            short overflow,
            int triggeredAt,
            short triggered,
            short auto_stop,
            int nValues);
  }
}
