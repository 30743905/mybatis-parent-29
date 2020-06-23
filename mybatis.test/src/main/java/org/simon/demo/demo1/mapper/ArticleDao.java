package org.simon.demo.demo1.mapper;

import org.apache.ibatis.annotations.Param;
import org.simon.demo.demo1.entity.Article;


/**
 * ArticleDao
 *
 * @author Tian ZhongBo
 * @date 2018-07-01 13:20:51
 */
public interface ArticleDao {

    Article findOne(@Param("id") int id);
    void insert(Article article);
}
