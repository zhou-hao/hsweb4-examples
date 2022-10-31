package org.hswebframework.example.crud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.hswebframework.web.crud.annotation.EnableEasyormRepository;
import org.hswebframework.web.crud.web.ResponseMessage;
import org.reactivestreams.Publisher;
import org.springdoc.core.ReturnTypeParser;
import org.springdoc.webflux.core.SpringDocWebFluxConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@SpringBootApplication(scanBasePackages = "org.hswebframework.example.crud")
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
                    contact = @Contact(name = "admin",email = "admin@hsweb.me"),
                    version = "1.0.0-SNAPSHOT"
            )
    )
    @AutoConfigureBefore(SpringDocWebFluxConfiguration.class)
    static class SwaggerConfiguration {
        @Bean
        public ReturnTypeParser operationCustomizer() {
            //自定义文档的返回类型
            return new ReturnTypeParser() {
                @Override
                public Type getReturnType(MethodParameter methodParameter) {
                    Type type = ReturnTypeParser.super.getReturnType(methodParameter);

                    if (type instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = ((ParameterizedType) type);
                        Type rawType = parameterizedType.getRawType();
                        if (rawType instanceof Class && Publisher.class.isAssignableFrom(((Class<?>) rawType))) {
                            Type actualType = parameterizedType.getActualTypeArguments()[0];

                            if (actualType instanceof ParameterizedType) {
                                actualType = ((ParameterizedType) actualType).getRawType();
                            }
                            if (actualType == ResponseEntity.class || actualType == ResponseMessage.class) {
                                return type;
                            }
                            boolean returnList = Flux.class.isAssignableFrom(((Class<?>) rawType));

                            //统一返回ResponseMessage
                            return ResolvableType
                                    .forClassWithGenerics(
                                            Mono.class,
                                            ResolvableType.forClassWithGenerics(
                                                    ResponseMessage.class,
                                                    returnList ?
                                                            ResolvableType.forClassWithGenerics(
                                                                    List.class,
                                                                    ResolvableType.forType(parameterizedType.getActualTypeArguments()[0])
                                                            ) :
                                                            ResolvableType.forType(parameterizedType.getActualTypeArguments()[0])
                                            ))
                                    .getType();

                        }
                    }

                    return type;
                }
            };
        }

    }
}
