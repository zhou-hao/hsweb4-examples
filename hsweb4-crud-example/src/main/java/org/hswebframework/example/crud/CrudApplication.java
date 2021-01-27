package org.hswebframework.example.crud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.hswebframework.web.crud.annotation.EnableEasyormRepository;
import org.springdoc.webflux.core.SpringDocWebFluxConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEasyormRepository("org.hswebframework.example.**.entity")
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

    @Configuration(proxyBeanMethods = false)
    @OpenAPIDefinition(
            info = @Info(
                    title = "hsweb4 curd example",
                    description = "hsweb4 增删改查例子",
                    contact = @Contact(name = "admin"),
                    version = "1.0.0-SNAPSHOT"
            )
    )
    @AutoConfigureBefore(SpringDocWebFluxConfiguration.class)
    static class SwaggerConfiguration {

    }
}
