package com.application.dao.v1.common;

import com.application.exception.v1.dao.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractBaseDaoImpl<T> {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<T> persist(T t, Class<T> clazz) {
        return reactiveMongoTemplate
                .save(t, clazz.getSimpleName())
                .onErrorMap(ex -> new MongoException(ex.getMessage()))
                .log();
    }

    public Mono<T> findById(String id, Class<T> clazz){
        return reactiveMongoTemplate.findById(id, clazz)
                .onErrorMap(ex -> new MongoException(ex.getMessage()))
                .log();
    }

    public Flux<T> find(Query query, Class<T> clazz){
        return reactiveMongoTemplate.find(query, clazz)
                .onErrorMap(ex -> new MongoException(ex.getMessage()))
                .log();
    }

    public Mono<T> findOne(Query query, Class<T> clazz){
        return reactiveMongoTemplate.findOne(query, clazz)
                .onErrorMap(ex -> new MongoException(ex.getMessage()))
                .log();
    }
}
