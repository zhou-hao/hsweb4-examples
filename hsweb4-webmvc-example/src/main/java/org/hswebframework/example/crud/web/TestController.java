package org.hswebframework.example.crud.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.example.crud.service.TestService;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.ezorm.rdb.mapping.SyncRepository;
import org.hswebframework.web.crud.web.CrudController;
import org.hswebframework.web.crud.web.ServiceCrudController;
import org.hswebframework.web.crud.web.reactive.ReactiveCrudController;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
@Tag(name = "简单测试")
public class TestController implements ServiceCrudController<TestEntity,String> {

   private final TestService service;

    @Override
    public TestService getService() {
        return service;
    }

    //mvc中也可以直接使用reactor进行异步处理
    @GetMapping(value = "/async")
    public Mono<Boolean> testAsync(){

        return Mono
                .just(true)
                .delayElement(Duration.ofSeconds(1));
    }

}
