package org.simon.demo.demo1.entity;

import org.simon.demo.demo1.constant.ArticleTypeEnum;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Article
 *
 * @author Tian ZhongBo
 * @date 2018-06-25 14:11:14
 */
@Data
public class Article implements Serializable {
    private Integer id;
    private String title;
    private Author author;
    private String content;
    private ArticleTypeEnum type;
    private Date createTime;
}
