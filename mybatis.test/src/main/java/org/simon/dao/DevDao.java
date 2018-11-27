package org.simon.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.simon.po.DevBean;

/**
 * @author 36410
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-10-25 13:05 Description:TODO
 */
//@Mapper
public interface DevDao {

  @Select("select id, device_code , device_name from vsl_dev")
  List<DevBean> queryAll();
}
