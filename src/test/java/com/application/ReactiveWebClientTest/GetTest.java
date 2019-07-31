package com.application.ReactiveWebClientTest;

import com.application.web.ReactiveWebClient;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@DirtiesContext
public class GetTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReactiveWebClient reactiveWebClient;

    @Test
    public void webClientGetTest(){
//        webTestClient.get().uri("http://localhost:8083/demo/")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .isEqualTo("qwert");
        Mono<JsonNode>  mono = reactiveWebClient.getMonoClient("http://localhost:8083/demo/");

        StepVerifier.create(mono)
                .expectSubscription()
                .expectNextMatches( j -> j.get("name").equals("qwert"))
                .verifyComplete();
    }
}
