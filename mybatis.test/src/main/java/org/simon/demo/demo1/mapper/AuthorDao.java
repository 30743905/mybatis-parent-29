package org.simon.demo.demo1.mapper;

import org.apache.ibatis.annotations.Param;
import org.simon.demo.demo1.entity.Author;

/**
 * AuthorDao
 *
 * @author Tian ZhongBo
 * @date 2018-07-14 21:53:53
 */
public interface AuthorDao {

    Author findOne(@Param("id") int id);
}
