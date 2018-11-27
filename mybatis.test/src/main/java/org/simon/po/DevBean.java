package org.simon.po;

/**
 * @author 36410
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-10-25 13:04 Description:TODO
 */
public class DevBean {
  private Long id;
  private String deviceCode;
  private String deviceName;
  private String deviceIp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDeviceCode() {
    return deviceCode;
  }

  public void setDeviceCode(String deviceCode) {
    this.deviceCode = deviceCode;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDeviceIp() {
    return deviceIp;
  }

  public void setDeviceIp(String deviceIp) {
    this.deviceIp = deviceIp;
  }
}
