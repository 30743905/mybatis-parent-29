package org.simon.domain;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

import lombok.Data;

/**
 * @author simon
 * @Copyright © 2018 tiger Inc. All rights reserved.
 * @create 2018-11-27 21:46
 * @Description:TODO
 */
@Data
public class Article {
    private Long id;
    private String author;
    private Date createTime;
    private String title;
    private String content;

    public Article(@Param("id") Long id, @Param("title") String title, @Param("content") String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}



