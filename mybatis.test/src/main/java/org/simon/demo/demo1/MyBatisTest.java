package org.simon.demo.demo1;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.simon.demo.demo1.entity.Article;
import org.simon.demo.demo1.entity.Author;
import org.simon.demo.demo1.mapper.ArticleDao;
import org.simon.demo.demo1.mapper.AuthorDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 你搞忘写注释了
 *
 * @author Admin
 * @Copyright © 2019 tiger Inc. All rights reserved.
 * @create 2019-09-29 23:51
 */
public class MyBatisTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        String resource = "mapper/demo.demo1/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    @Test
    public void testOne2One() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            Article article = articleDao.findOne(1);
            Author author = article.getAuthor();
            article.setAuthor(null);
            System.out.println("\nauthor info:");
            System.out.println(author);
            System.out.println("\narticles info:");
            System.out.println(article);
        } finally {
            session.close();
        }
    }

    @Test
    public void testOne2Many() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            AuthorDao authorDao = session.getMapper(AuthorDao.class);
            Author author = authorDao.findOne(1);
            List<Article> arts = author.getArticles();
            List<Article> articles =
                    Arrays.asList(arts.toArray(new Article[arts.size()]));
            arts.clear();
            System.out.println("\nauthor info:");
            System.out.println(author);
            System.out.println("\narticles info:");
            articles.forEach(System.out::println);
        } finally {
            session.close();
        }
    }




    @Test
    public void t1() throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource("org/simon/demo/demo1/entity");
        InputStream is = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        for (String line; (line = reader.readLine()) != null;) {
            System.out.println(line);
        }
    }

}
