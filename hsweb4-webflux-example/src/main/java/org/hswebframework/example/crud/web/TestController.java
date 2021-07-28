package org.hswebframework.example.crud.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.crud.web.reactive.ReactiveCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
@Tag(name = "简单测试")
public class TestController implements ReactiveCrudController<TestEntity,String> {

   private final ReactiveRepository<TestEntity, String> repository;

    @Override
    public ReactiveRepository<TestEntity, String> getRepository() {
        return repository;
    }


}
