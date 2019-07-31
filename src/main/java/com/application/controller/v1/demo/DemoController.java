package com.application.controller.v1.demo;

import com.application.controller.v1.routes.ApiRoutes;
import com.application.exception.v1.dao.MongoException;
import com.application.mappers.v1.DemoResponseMapper;
import com.application.models.v1.Demo;
import com.application.services.v1.demo.IDemoService;
import com.application.utils.v1.Demo.DemoResponseMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    private final Logger DEMO_LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDemoService demoService;

    @GetMapping("/demo/")
    public Mono<String> get(){
        DEMO_LOGGER.info("info");
        DEMO_LOGGER.error("error");
        DEMO_LOGGER.warn("warn");
        DEMO_LOGGER.trace("trace");
        DEMO_LOGGER.debug("debug");
        return Mono.just("qwert");
    }

    @PostMapping(ApiRoutes.DemoControllerRoutes.GET_DEMO)
    public Mono<DemoResponseMapper> get(@RequestBody Demo demo){
            return demoService.saveDemo(demo)
                    .map(demoResult -> DemoResponseMapperUtils.createSuccessResponse(demoResult))
                    .onErrorMap(ex -> {
                        if(ex instanceof MongoException) {
                            System.out.println("22222222222222222222222");
                            return ex;
                        }
                        else
                            return ex;
                    });
    }
}
