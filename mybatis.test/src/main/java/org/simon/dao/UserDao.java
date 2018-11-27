package org.simon.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.simon.po.UserBean;

/**
 * @author 36410
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-10-25 12:45 Description:TODO
 */
@Mapper
public interface UserDao {
  List<UserBean> queryall();

  @Select("select * from user where id = #{id} and username = #{username}")
  UserBean queryById(@Param("id") Long id, @Param("username") String username);

  UserBean select(UserBean userBean);

  UserBean select2(@Param("id") Long id, @Param("username") String username);

  Integer insert(UserBean userBean);
}
