package com.application.services.v1.demo;

import com.application.exception.v1.dao.MongoException;
import com.application.models.v1.Demo;
import reactor.core.publisher.Mono;

public interface IDemoService {

    Mono<Demo> saveDemo(Demo demo);
}
