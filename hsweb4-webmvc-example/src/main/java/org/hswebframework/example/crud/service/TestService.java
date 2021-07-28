package org.hswebframework.example.crud.service;

import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.web.crud.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class TestService extends GenericCrudService<TestEntity,String> {
}
