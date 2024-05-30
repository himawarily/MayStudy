package com.cy.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest
class StoreApplicationTests {

    @Autowired //加载数据源信息 自动装配
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

//    HikariProxyConnection@1085016662 wrapping com.mysql.cj.jdbc.ConnectionImpl@492c9892
//    ⬆表示数据库连接池
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
