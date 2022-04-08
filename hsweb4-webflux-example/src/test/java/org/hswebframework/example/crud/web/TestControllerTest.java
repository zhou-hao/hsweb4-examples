package org.hswebframework.example.crud.web;

import org.hswebframework.example.crud.CrudApplication;
import org.hswebframework.example.crud.entity.JoinTable;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.example.crud.enums.TestEnum;
import org.hswebframework.ezorm.core.GlobalConfig;
import org.hswebframework.ezorm.core.ObjectPropertyOperator;
import org.hswebframework.ezorm.rdb.operator.DatabaseOperator;
import org.hswebframework.web.bean.FastBeanCopier;
import org.hswebframework.web.crud.configuration.EasyormConfiguration;
import org.hswebframework.web.crud.configuration.R2dbcSqlExecutorConfiguration;
import org.hswebframework.web.starter.jackson.CustomCodecsAutoConfiguration;
import org.hswebframework.web.starter.jackson.CustomMappingJackson2HttpMessageConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(TestController.class)
@ImportAutoConfiguration({
        EasyormConfiguration.class,
        CustomCodecsAutoConfiguration.class,
        R2dbcSqlExecutorConfiguration.class, R2dbcAutoConfiguration.class,
        R2dbcTransactionManagerAutoConfiguration.class, TransactionAutoConfiguration.class,
        WebClientAutoConfiguration.class
})
@ComponentScan("org.hswebframework.example.crud")
class TestControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    void test() {

        TestEntity entity = new TestEntity();
        entity.setId("Test");
        entity.setName("Test");
        entity.setEnumTest(TestEnum.state1);
        entity.setJoinTable(JoinTable.of("Test", "Code"));
        testClient
                .post()
                .uri("/api/test")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(entity)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.joinId")
                .isNotEmpty();


        testClient
                .get()
                .uri("/api/test/{id}", entity.getId())
                .exchange()
                .expectBody()
                .jsonPath("$.joinId").isNotEmpty()
                .jsonPath("$.joinTable.name").isEqualTo("Test")
                .jsonPath("$.joinTable.code").isEqualTo("Code");


    }

}