package com.application.dao.v1.demo;

import com.application.exception.v1.dao.MongoException;
import com.application.models.v1.Demo;
import reactor.core.publisher.Mono;

public interface IDemoDao {
    Mono<Demo> persist(Demo demo, Class<Demo> clazz);

    Mono<Demo> findById(String id, Class<Demo> clazz);
}
