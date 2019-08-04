package com.application.controller.v1.demo;

import com.application.controller.v1.routes.ApiRoutes;
import com.application.encryptor.v1.PBKDF2Encoder;
import com.application.exception.v1.dao.MongoException;
import com.application.jwt.v1.enums.Role;
import com.application.jwt.v1.mappers.request.AuthRequest;
import com.application.jwt.v1.mappers.request.User;
import com.application.jwt.v1.mappers.response.AuthResponse;
import com.application.jwt.v1.util.JWTUtil;
import com.application.mappers.v1.DemoResponseMapper;
import com.application.models.v1.Demo;
import com.application.services.v1.demo.IDemoService;
import com.application.utils.v1.Demo.DemoResponseMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RestController
public class DemoController {

    private final Logger DEMO_LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDemoService demoService;

    @PreAuthorize("hasRole('ROLE_USER')")
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

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PBKDF2Encoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
//        return userRepository.findByUsername(ar.getUsername()).map((userDetails) -> {
//            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
//                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        User user = new User(ar.getUsername(),ar.getPassword(), true, Arrays.asList(Role.valueOf(ar.getRole())));
         return Mono.just(ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user))));

    }

    @RequestMapping(value = "/resource/user1", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<ResponseEntity<?>> user() {
        return Mono.just(ResponseEntity.ok("Content for admin"));
    }

    @RequestMapping(value = "/resource/user2", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER') or  hasRole('ROLE_ADMIN')")
    public Mono<ResponseEntity<?>> user2() {
        return Mono.just(ResponseEntity.ok("Content for user both"));
    }

    @RequestMapping(value = "/resource/user3", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> user1() {
        return Mono.just(ResponseEntity.ok("Content for no"));
    }

    @RequestMapping(value = "/resource/user4", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_BISHAL')")
    public Mono<ResponseEntity<?>> user4() {
        return Mono.just(ResponseEntity.ok("Content for bishal"));
    }
}
