package org.simon.test;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.simon.Log;
import org.simon.dao.DevDao;
import org.simon.dao.UserDao;
import org.simon.po.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 36410
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-10-25 10:59 Description:TODO
 */
public class TestMain {

  SqlSessionFactory factory;
  @Before
  public void before() throws IOException {
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    factory = new SqlSessionFactoryBuilder()
        .build(reader);
  }

  @Test
  public void fun1() throws IOException {
    SqlSession session = factory.openSession();
    List<Object> ret = session.selectList("org.simon.dao.UserDao.queryall");
    System.out.println(ret);

    UserDao userDao = session.getMapper(UserDao.class);
    System.out.println(userDao.getClass().getName());
    System.out.println(userDao instanceof MapperProxyFactory);
    //System.out.println(JSON.toJSONString(userDao.queryall(), true));
    System.out.println("------------");
    System.out.println(userDao.queryById(25L, "陈小明"));
    session.close();
  }

  @Test
  public void fun2() throws IOException {
    SqlSession session = factory.openSession();

    DevDao devDao = session.getMapper(DevDao.class);
    System.out.println(devDao.getClass());
    System.out.println(JSON.toJSONString(devDao.queryAll(), true));
    session.close();
  }

  @Test
  public void fun3(){
    SqlSession session = factory.openSession(true);
    UserDao userDao = session.getMapper(UserDao.class);

    /*UserBean bean = new UserBean();
    bean.setAddress("email55");
    bean.setUsername("name:55");
    bean.setSex(0);
    Integer id = userDao.insert(bean);
    System.out.println("id:"+id);
    //session.commit();
*/

    UserBean bean2 = new UserBean();
    //bean2.setId(3L);
    //bean2.setUsername("test3");
    userDao.select(bean2);

    userDao.queryById(4L, "test4");
  }

  @Test
  public void fun4(){
    SqlSession session = factory.openSession(true);
    UserDao userDao = session.getMapper(UserDao.class);

    userDao.select2(10L, "张三");


    UserBean bean = new UserBean();
    //bean.setId(10L);
    //bean.setUsername("张三");
    userDao.select(bean);
  }


  @Test
  public void fun5(){
      System.out.println("-----------✨ JPA Criteria ✨------------");
  }

}
