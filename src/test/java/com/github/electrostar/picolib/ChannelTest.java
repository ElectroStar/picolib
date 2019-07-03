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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Channel} class.
 * 
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class ChannelTest {

  public ChannelTest() {
  }

  /**
   * Test of values method, of class Channel.
   */
  @Test
  public void testValues() {
    Set<Integer> expected = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5));
    Set<Integer> actual = new HashSet<>();
    for (Channel c : Channel.values()) {
      actual.add(c.getId());
    }
    assertEquals(expected, actual);
  }

  /**
   * Test of valueOf method, of class Channel.
   */
  @Test
  public void testValueOf() {
    assertEquals(Channel.CHANNEL_A, Channel.valueOf("CHANNEL_A"));
    assertEquals(Channel.CHANNEL_B, Channel.valueOf("CHANNEL_B"));
    assertEquals(Channel.CHANNEL_C, Channel.valueOf("CHANNEL_C"));
    assertEquals(Channel.CHANNEL_D, Channel.valueOf("CHANNEL_D"));
    assertEquals(Channel.EXTERNAL, Channel.valueOf("EXTERNAL"));
    assertEquals(Channel.NONE, Channel.valueOf("NONE"));
    assertThrows(IllegalArgumentException.class, () -> {
      Channel.valueOf("NotFound");
    });

  }

  /**
   * Test of getId method, of class Channel.
   */
  @Test
  public void testGetId() {
    assertEquals(0, Channel.CHANNEL_A.getId());
    assertEquals(1, Channel.CHANNEL_B.getId());
    assertEquals(2, Channel.CHANNEL_C.getId());
    assertEquals(3, Channel.CHANNEL_D.getId());
    assertEquals(4, Channel.EXTERNAL.getId());
    assertEquals(5, Channel.NONE.getId());
  }

  /**
   * Test of findById method, of class Channel.
   */
  @Test
  public void testFindById() {
    assertEquals(Channel.CHANNEL_A, Channel.findById(0));
    assertEquals(Channel.CHANNEL_B, Channel.findById(1));
    assertEquals(Channel.CHANNEL_C, Channel.findById(2));
    assertEquals(Channel.CHANNEL_D, Channel.findById(3));
    assertEquals(Channel.EXTERNAL, Channel.findById(4));
    assertEquals(Channel.NONE, Channel.findById(5));
    assertEquals(null, Channel.findById(6));
    assertEquals(null, Channel.findById(-1));
  }

  /**
   * Test of toString and getLabel method, of class Channel.
   */
  @Test
  public void testToStringAndGetLabel() {
    assertEquals("Channel A", Channel.CHANNEL_A.toString());
    assertEquals("Channel A", Channel.CHANNEL_A.getLabel());
    assertEquals("Channel B", Channel.CHANNEL_B.toString());
    assertEquals("Channel B", Channel.CHANNEL_B.getLabel());
    assertEquals("Channel C", Channel.CHANNEL_C.toString());
    assertEquals("Channel C", Channel.CHANNEL_C.getLabel());
    assertEquals("Channel D", Channel.CHANNEL_D.toString());
    assertEquals("Channel D", Channel.CHANNEL_D.getLabel());
    assertEquals("External", Channel.EXTERNAL.toString());
    assertEquals("External", Channel.EXTERNAL.getLabel());
    assertEquals("None", Channel.NONE.toString());
    assertEquals("None", Channel.NONE.getLabel());
  }
  
  /**
   * Test of findByChar method, of class Channel.
   */
  @Test
  public void testFindByChar() {
    assertEquals(Channel.CHANNEL_A, Channel.findByChar('A'));
    assertEquals(Channel.CHANNEL_A, Channel.findByChar('a'));
    assertEquals(Channel.CHANNEL_B, Channel.findByChar('B'));
    assertEquals(Channel.CHANNEL_B, Channel.findByChar('b'));
    assertEquals(Channel.CHANNEL_C, Channel.findByChar('C'));
    assertEquals(Channel.CHANNEL_C, Channel.findByChar('c'));
    assertEquals(Channel.CHANNEL_D, Channel.findByChar('D'));
    assertEquals(Channel.CHANNEL_D, Channel.findByChar('d'));
    for(int idx = 0; idx < 256; idx++) {
      char c = (char)idx;
      if(c != 'a' && c!= 'A' && c != 'b' && c != 'B' 
              && c != 'c' && c != 'C' && c != 'd' && c != 'D') {
        assertNull(Channel.findByChar(c));
      } else {
        assertNotNull(Channel.findByChar(c));
      }
    }
  }
}
