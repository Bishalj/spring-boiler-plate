package com.application.services.v1.demo.impl;

import com.application.dao.v1.demo.IDemoDao;
import com.application.exception.v1.dao.MongoException;
import com.application.models.v1.Demo;
import com.application.services.v1.demo.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DemoServiceImpl  implements IDemoService {

    @Autowired
    IDemoDao demoDao;
    @Override
    public Mono<Demo> saveDemo(Demo demo){
        return demoDao.persist(demo, Demo.class);
    }
}
