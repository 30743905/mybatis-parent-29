package org.simon.demo.demo1.entity;

import org.simon.demo.demo1.constant.SexEnum;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Author
 *
 * @author Tian ZhongBo
 * @date 2018-07-14 21:54:45
 */
@Data
public class Author implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private SexEnum sex;
    private String email;
    private List<Article> articles;
}
