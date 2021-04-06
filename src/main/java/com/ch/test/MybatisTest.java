package com.ch.test;

import com.ch.dao.IUserDao;
import com.ch.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    @Test
    public void testFindAll() throws IOException {
        //Resource:工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //解析配置文件，创建了sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //生产了sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();//默认开启了一个事务，但是不会自动提交
                                                               //在增删改时要手动提交事务,传入参数为true的时候会自动提交事务
        //4.sqlSession调用方法：查询所有调用selectList，查询单个调用selectOne，添加：insert 修改调用update，删除调用delete
        List<User> userList = sqlSession.selectList("com.ch.dao.IUserDao.findAll");
        for (User user:userList) {

            System.out.println("用户数据"+user);
        }

        sqlSession.close();
    }

    @Test
    public void testInsert() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        user.setUsername("test");
         sqlSession.insert("user.saveUser",user);
         //增删改的时候必须提交事务
         sqlSession.commit();

         sqlSession.close();
    }

    @Test
    public void testUpdate() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(2);
        user.setUsername("修改");
        sqlSession.update("user.updateUser",user);
        //增删改的时候必须提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void testDelete() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

       sqlSession.delete("com.ch.dao.IUserDao.deleteUser",3);
        //增删改的时候必须提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void test5() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao=sqlSession.getMapper(IUserDao.class);
        List<User> all = userDao.findAll();
        System.out.println("用户信息"+all);
        sqlSession.close();
    }

    @Test
    public void test6() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
     //   user.setId(3);
        user.setUsername("test");
        IUserDao userDao=sqlSession.getMapper(IUserDao.class);
        List<User> all = userDao.findByCondition(user);
        System.out.println("用户信息"+all);
        sqlSession.close();
    }
    @Test
    public void test7() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int[] ids={2,3,4};
        IUserDao userDao=sqlSession.getMapper(IUserDao.class);
        List<User> all = userDao.findByIds(ids);
        System.out.println("用户信息"+all);
        sqlSession.close();
    }
}
