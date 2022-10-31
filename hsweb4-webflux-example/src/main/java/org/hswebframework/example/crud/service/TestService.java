package org.hswebframework.example.crud.service;

import lombok.AllArgsConstructor;
import org.hswebframework.example.crud.entity.JoinTable;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.crud.events.EntityPrepareCreateEvent;
import org.hswebframework.web.crud.service.GenericCrudService;
import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TestService extends GenericReactiveCrudService<TestEntity, String> {

    private final ReactiveRepository<JoinTable, String> joinRepository;


    @EventListener
    public void handleSavedEvent(EntityPrepareCreateEvent<TestEntity> event) {

        event.async(
                Flux.fromIterable(event.getEntity())
                    .flatMap(e -> {
                        if (e.getJoinTable() != null) {
                            return joinRepository
                                    .save(e.getJoinTable())
                                    .then(Mono.fromRunnable(() -> e.setJoinId(e.getJoinTable().getId())));
                        }
                        return Mono.empty();
                    })
        );
    }


}
