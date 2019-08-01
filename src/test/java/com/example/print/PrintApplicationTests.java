package com.example.print;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrintApplicationTests {

    @Autowired
    private SqlSessionFactory factory;

    @Autowired
    private DataSource dataSource;

    @Test
    public void contextLoads() {

    }

    @Test
    public void test() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(baos, "gbk");
        writer.write(0x1b);
        writer.flush();
        baos.write("(k".getBytes());
        writer.write("(k");
        writer.flush();

        for (byte b : baos.toByteArray()) {
            System.out.print(b);
            System.out.print(" ");
        }
    }


    @Test
    public void name2() throws Exception {

        SqlSession sqlSession = factory.openSession(true);
        int update = sqlSession.update("updateTemplate");
        System.out.println("*********************" + update);
    }
}
