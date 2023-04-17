package org.hswebframework.example.crud.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.example.crud.entity.JoinTable;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.example.crud.enums.TestEnum;
import org.hswebframework.example.crud.service.TestService;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveCrudController;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
@Tag(name = "简单测试")
public class TestController implements ReactiveServiceCrudController<TestEntity, String> {

    @Getter
    private final TestService service;

    private final QueryHelper queryHelper;

    // http://localhost:8080/api/test/join/_query?where=joinTable.name%20is%20123&pageSize=10
    @GetMapping("/join/_query")
    public Mono<PagerResult<TestInfo>> testJoin(QueryParamEntity query) {

        return queryHelper
                .select(TestInfo.class)
                .all(JoinTable.class, TestInfo::setJoinTable)
                .all(TestEntity.class)
                .from(TestEntity.class)
                .leftJoin(JoinTable.class, join -> join.is(JoinTable::getId, TestEntity::getJoinId))
                .where(query)
                .fetchPaged();
    }


    @Getter
    @Setter
    public static class TestInfo {
        private String id;
        private String name;
        private TestEnum enumTest;

        private JoinTable joinTable;
    }

}
