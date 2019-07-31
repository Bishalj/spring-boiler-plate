package com.application.mongo.demo;

import com.application.dao.v1.demo.IDemoDao;
import com.application.exception.v1.dao.MongoException;
import com.application.models.v1.Demo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureDataMongo
public class DemoDaoImplTest {

    @Autowired
    IDemoDao demoDaoImpl;

    Demo demo;

    @Before
    public void initializeData(){
        this.demo = new Demo();
        this.demo.setName("Bishal");
    }

    @Test
    public void saveDemoTest(){
        Mono<Demo> demoMono= demoDaoImpl.persist(this.demo, Demo.class)
                .onErrorMap( e ->new MongoException(e.getMessage()))
                .log();

        StepVerifier.create(demoMono)
                .expectNextMatches(item -> StringUtils.isNotEmpty(item.getId()))
//                .expectNextCount(1)
                .verifyComplete();
    }
}
