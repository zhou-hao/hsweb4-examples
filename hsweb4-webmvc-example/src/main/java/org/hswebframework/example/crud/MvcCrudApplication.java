package org.hswebframework.example.crud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.hswebframework.web.crud.annotation.EnableEasyormRepository;
import org.hswebframework.web.crud.web.ResponseMessage;
import org.springdoc.core.ReturnTypeParser;
import org.springdoc.core.SpringDocConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootApplication
@EnableEasyormRepository(value = "org.hswebframework.example.**.entity",reactive = false, nonReactive = true)
@EnableAsync
public class MvcCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcCrudApplication.class, args);
    }

    @Configuration(proxyBeanMethods = false)
    @OpenAPIDefinition(
            info = @Info(
                    title = "hsweb4 curd example",
                    description = "hsweb4 增删改查例子",
                    contact = @Contact(name = "admin", email = "admin@hsweb.me"),
                    version = "1.0.0-SNAPSHOT"
            )
    )
    @AutoConfigureBefore(SpringDocConfiguration.class)
    static class SwaggerConfiguration {
        @Bean
        public ReturnTypeParser operationCustomizer() {
            //自定义文档的返回类型
            return new ReturnTypeParser() {
                @Override
                public Type getReturnType(MethodParameter methodParameter) {
                    Type type = ReturnTypeParser.super.getReturnType(methodParameter);

                    if (type instanceof Class) {
                        if (type == ResponseEntity.class || type == ResponseMessage.class) {
                            return type;
                        }
                        ResolvableType resolvableType = ResolvableType.forType(type);

                        boolean returnList = List.class.isAssignableFrom(((Class<?>) type));

                        //统一返回ResponseMessage
                        return ResolvableType
                                .forClassWithGenerics(
                                        ResponseMessage.class,
                                        returnList ?
                                                ResolvableType.forClassWithGenerics(
                                                        List.class,
                                                        resolvableType.getGeneric(0)
                                                ) :
                                                resolvableType
                                )
                                .getType();

                    }


                    return type;
                }
            };
        }

    }
}
