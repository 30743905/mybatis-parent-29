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
import java.util.Arrays;
import java.util.List;

/**
 * 你搞忘写注释了
 *
 * @author Admin
 * @Copyright © 2019 tiger Inc. All rights reserved.
 * @create 2019-09-29 23:51
 */
public class CacheTest {

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
            //同一个SqlSession中两次调用，这时第一次调用结果还没有刷新到一级缓存中，所以，这时走的是二级缓存
            Article article2 = articleDao.findOne(1);
            Author author = article.getAuthor();
            article.setAuthor(null);
            System.out.println("\nauthor info:");
            System.out.println(author);
            System.out.println("\narticles info:");
            System.out.println(article);
        } finally {
            session.close();//关闭时才会将缓存刷新到二级缓存中
        }


        session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            Article article = articleDao.findOne(1);
            Article article2 = articleDao.findOne(1);
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
    public void testCache() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            //从数据库查询，然后放入到一级缓存localCache中
            Article article = articleDao.findOne(1);
            System.out.println("1>>>"+System.identityHashCode(article));
            articleDao.insert(new Article().setTitle("test++11").setAuthor(new Author().setId(2)));
            /**
             *同一个SqlSession中第二次执行，这时是从一级缓存中获取
             * 注意：这时二级缓存中是还不存在的，第一次查询的结果放入到一级缓存和TransactionalCache.entriesToAddOnCommit中，
             * 此时并没有真正刷入到二级缓存PerpetualCache中，所以这时从二级缓存中根本获取不到
             */
            article = articleDao.findOne(1);
            System.out.println("2>>>"+System.identityHashCode(article));
        } finally {
            /**
             * SqlSession.close()或SqlSession.commit()会将TransactionalCache.entriesToAddOnCommit中数据刷入到二级缓存PerpetualCache中
             */
            session.close();
        }

        session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            //因为上面已经将结果刷入到二级缓存中，所以，这里会从二级缓存中获取到结果
            Article article = articleDao.findOne(1);
            System.out.println("2>>>"+System.identityHashCode(article));
            /**
             * 1、执行INSERT、UPDATE、DELETE操作时，CachingExecutor#update()方法会首先触发flushCacheIfRequired(ms)方法调用
             * 2、flushCacheIfRequired()方法中会采用TransactionalCacheManager.clear()进行清理缓存，最终会查找到对应的TransactionalCache，并调用其clear()
             * 3、TransactionalCache#clear()方法：1：clearOnCommit=true；2：entriesToAddOnCommit.clear()，所以，它只会清理TransactionalCache中存储的缓存，并没有清理掉二级缓存PerpetualCache中数据
             */
            articleDao.insert(new Article().setTitle("test++11").setAuthor(new Author().setId(2)));
            /**
             * 1、这时候查询，因为没有真正从二级缓存中清理掉，所以这里依然可以从二级缓存中查找到数据，
             * 但是在返回结果前会判断clearOnCommit=true时，注解返回null，而不用二级缓存数据，具体见：TransactionalCache.getObject；
             * 2、二级缓存返回null，继续从一级缓存获取也失败，这时候就从数据库查询；
             * 3、然后将数据库查询结果放入到一级缓存和TransactionalCache.entriesToAddOnCommit中
             */
            article = articleDao.findOne(1);
            System.out.println("3>>>"+System.identityHashCode(article));
        } finally {
            /**
             * 1、如果这时候commit，会将TransactionalCache.entriesToAddOnCommit中数据刷入到二级缓存PerpetualCache中进行覆盖；
             * 2、如果这时候session.rollback()或session.close()(close方法内部也是触发rollback方法)，则会将TransactionalCache.entriesToAddOnCommit清空，然后将clearOnCommit=false
             * 所以，当rollback时，二级缓存是不会覆盖的
             */
            session.commit();
            session.close();
        }

        session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            //如果上面是commit操作，则这里从二级缓存会获取到insert之后最新数据，如果上面是rollback，则二级缓存还是之前旧的，因为INSERT操作被回滚了，二级缓存没有发生修改
            Article article = articleDao.findOne(1);
            System.out.println("4>>>"+System.identityHashCode(article));
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
