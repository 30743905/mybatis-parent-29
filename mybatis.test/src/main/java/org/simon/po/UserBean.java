package org.simon.po;

import java.util.Date;

/**
 * @author 36410
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-10-25 12:35 Description:TODO
 */
public class UserBean {

  private Long id;
  private String username;
  private Date birthday;
  private Integer sex;
  private String address;



  @Override
  public String toString() {
    return "UserBean{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", birthday=" + birthday +
            ", sex=" + sex +
            ", address='" + address + '\'' +
            '}';
  }
}
