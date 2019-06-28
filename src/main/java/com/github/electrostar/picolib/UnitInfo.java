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

import java.util.Objects;

/**
 * The {@code UnitInfo} class contains all information about the PicoScope.
 * 
 * @since 0.0.1
 * @author <a href="mailto:startrooper@startrooper.org">ElectroStar</a>
 */
public class UnitInfo {

  private String driverVersion;
  private String usbVersion;
  private String hardwareVersion;
  private String variantInfo;
  private String batchAndSerial;
  private String calibrationDate;
  private String kernelVersion;
  private String driverPath;

  /**
   * Create Unit Info.
   * 
   * @param driverVersion the version number of the DLL used by the oscilloscope driver.
   * @param usbVersion the type of USB connection thatis being used to connect the oscilloscope 
   *                   to the computer.
   * @param hardwareVersion the hardware version of the attached oscilloscope.
   * @param variantInfo the variant of PicoScope PC Oscilloscope that is attached to the computer.
   * @param batchAndSerial the batch and serial number of the oscilloscope.
   * @param calibrationDate the calibration date of the oscilloscope.
   * @param kernelVersion the versionnumber of the kernel driver.
   * @param driverPath the path of the driver.
   */
  public UnitInfo(String driverVersion, 
          String usbVersion, 
          String hardwareVersion, 
          String variantInfo, 
          String batchAndSerial, 
          String calibrationDate, 
          String kernelVersion, 
          String driverPath) {
    this.driverVersion = driverVersion;
    this.usbVersion = usbVersion;
    this.hardwareVersion = hardwareVersion;
    this.variantInfo = variantInfo;
    this.batchAndSerial = batchAndSerial;
    this.calibrationDate = calibrationDate;
    this.kernelVersion = kernelVersion;
    this.driverPath = driverPath;
  }

  /**
   * Copy Consturctor.
   * 
   * @param u {@link UnitInfo} to copy.
   */
  public UnitInfo(UnitInfo u) {
    this(u.driverVersion, 
            u.usbVersion, 
            u.hardwareVersion, 
            u.variantInfo, 
            u.batchAndSerial, 
            u.calibrationDate, 
            u.kernelVersion, 
            u.driverPath);
  }

  /**
   * Create Unit Info.
   */
  public UnitInfo() {
  }

  /**
   * Gets the version number of the DLL used by the oscilloscope driver.
   * 
   * @return driver version.
   */
  public String getDriverVersion() {
    return driverVersion;
  }

  /**
   * Sets the version number of the DLL used by the oscilloscope driver.
   * 
   * @param driverVersion the new driver version.
   */
  public void setDriverVersion(String driverVersion) {
    this.driverVersion = driverVersion;
  }

  /**
   * Gets the type of USB connection thatis being used to connect the oscilloscope to the computer.
   * 
   * @return usb version.
   */
  public String getUsbVersion() {
    return usbVersion;
  }

  /**
   * Sets the type of USB connection thatis being used to connect the oscilloscope to the computer.
   * 
   * @param usbVersion the new usb version.
   */
  public void setUsbVersion(String usbVersion) {
    this.usbVersion = usbVersion;
  }

  /**
   * Gets the hardware version of the attached oscilloscope.
   * 
   * @return hardware version.
   */
  public String getHardwareVersion() {
    return hardwareVersion;
  }

  /**
   * Sets the hardware version of the attached oscilloscope.
   * 
   * @param hardwareVersion the new hardware version.
   */
  public void setHardwareVersion(String hardwareVersion) {
    this.hardwareVersion = hardwareVersion;
  }

  /**
   * Gets the variant of PicoScope PC Oscilloscope that is attached to the computer.
   * 
   * @return variant info.
   */
  public String getVariantInfo() {
    return variantInfo;
  }

  /**
   * Sets the variant of PicoScope PC Oscilloscope that is attached to the computer.
   * 
   * @param variantInfo the new variant info.
   */
  public void setVariantInfo(String variantInfo) {
    this.variantInfo = variantInfo;
  }

  /**
   * Gets the batch and serial number of the oscilloscope.
   * 
   * @return batch and serial number.
   */
  public String getBatchAndSerial() {
    return batchAndSerial;
  }

  /**
   * Sets the batch and serial number of the oscilloscope.
   * @param batchAndSerial the new batch and serial number.
   */
  public void setBatchAndSerial(String batchAndSerial) {
    this.batchAndSerial = batchAndSerial;
  }

  /**
   * Gets the calibration date of the oscilloscope.
   * 
   * @return calibration date.
   */
  public String getCalibrationDate() {
    return calibrationDate;
  }

  /**
   * Sets the calibration date of the oscilloscope.
   * 
   * @param calibrationDate the new calibration date.
   */
  public void setCalibrationDate(String calibrationDate) {
    this.calibrationDate = calibrationDate;
  }

  /**
   * Gets the versionnumber of the kernel driver.
   * 
   * @return kernel version.
   */
  public String getKernelVersion() {
    return kernelVersion;
  }

  /**
   * Sets the versionnumber of the kernel driver.
   * 
   * @param kernelVersion the new kernel version.
   */
  public void setKernelVersion(String kernelVersion) {
    this.kernelVersion = kernelVersion;
  }

  /**
   * Gets the path of the driver.
   * 
   * @return driver path.
   */
  public String getDriverPath() {
    return driverPath;
  }

  /**
   * Sets the path of the driver.
   * 
   * @param driverPath the new driver path.
   */
  public void setDriverPath(String driverPath) {
    this.driverPath = driverPath;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final UnitInfo other = (UnitInfo) obj;
    if (!Objects.equals(this.driverVersion, other.driverVersion)) {
      return false;
    }
    if (!Objects.equals(this.usbVersion, other.usbVersion)) {
      return false;
    }
    if (!Objects.equals(this.hardwareVersion, other.hardwareVersion)) {
      return false;
    }
    if (!Objects.equals(this.variantInfo, other.variantInfo)) {
      return false;
    }
    if (!Objects.equals(this.batchAndSerial, other.batchAndSerial)) {
      return false;
    }
    if (!Objects.equals(this.calibrationDate, other.calibrationDate)) {
      return false;
    }
    if (!Objects.equals(this.kernelVersion, other.kernelVersion)) {
      return false;
    }
    return Objects.equals(this.driverPath, other.driverPath);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.driverVersion);
    hash = 37 * hash + Objects.hashCode(this.usbVersion);
    hash = 37 * hash + Objects.hashCode(this.hardwareVersion);
    hash = 37 * hash + Objects.hashCode(this.variantInfo);
    hash = 37 * hash + Objects.hashCode(this.batchAndSerial);
    hash = 37 * hash + Objects.hashCode(this.calibrationDate);
    hash = 37 * hash + Objects.hashCode(this.kernelVersion);
    hash = 37 * hash + Objects.hashCode(this.driverPath);
    return hash;
  }

  @Override
  public String toString() {
    return "UnitInfo{" + "driverVersion=" + driverVersion + ", usbVersion=" + usbVersion 
            + ", hardwareVersion=" + hardwareVersion + ", variantInfo=" + variantInfo 
            + ", batchAndSerial=" + batchAndSerial + ", calibrationDate=" + calibrationDate 
            + ", kernelVersion=" + kernelVersion + ", driverPath=" + driverPath + '}';
  }
}
