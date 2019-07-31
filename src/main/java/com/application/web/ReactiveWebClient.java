package com.application.web;

import com.application.exception.v1.webclient.ReactiveWebClientException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static com.application.constants.v1.ReactiveWebClientConstant.ResponseMessage.*;
import static com.application.constants.v1.ReactiveWebClientConstant.LoggingMessage.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
public class ReactiveWebClient<T> {

    private final Logger Reactive_LOGGER = LoggerFactory.getLogger(this.getClass());


    WebClient webClient = WebClient.create();

    public Mono<JsonNode> getMonoClient(@NotNull final String url){
        return Mono.subscriberContext().flatMap( context ->{
            return webClient.get().uri(url)
                    .exchange()
                    .flatMap( clientResponse -> clientResponse.bodyToMono(JsonNode.class))
                    .doOnNext( data -> Reactive_LOGGER.info(INFO_MESSAGE, data))
                    .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE+ err.getMessage()))
                    .log(GET_MESSAGE);
        });
    }

    public Flux<JsonNode> getFluxClient(@NotNull final String url){
        return webClient.get().uri(url)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(JsonNode.class))
                .doOnNext( data -> Reactive_LOGGER.info(INFO_MESSAGE, data))
                .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE+ err.getMessage()))
                .log(GET_MESSAGE);
    }

    public Flux<JsonNode> putFluxClient(@NotNull final String url, @NotNull final Mono <T> t,  @NotNull final Class<T> clazz, final Map<String, String> headersMap){
        return webClient.put().uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers( header -> addHeaders(header, headersMap))
                .body(t, clazz)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(JsonNode.class))
                .doOnNext( data ->  Reactive_LOGGER.info(INFO_MESSAGE, data))
                .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE + err.getMessage()))
                .log(PUT_MESSAGE);
    }

    public Flux<JsonNode> postFluxClient(@NotNull final String url, @NotNull final Mono<T> t,@ NotNull final Class<T> clazz, final Map<String, String> headersMap){
        return webClient.post().uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers( headers -> addHeaders(headers, headersMap))
                .body(t, clazz)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(JsonNode.class))
                .doOnNext( data ->  Reactive_LOGGER.info(INFO_MESSAGE, data))
                .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE + err.getMessage()))
                .log(POST_MESSAGE);
    }

    private HttpHeaders addHeaders(final HttpHeaders headers, final Map<String, String> headersMap) {
        if(headersMap == null || headersMap.isEmpty()) {
            return null;
        }
        headersMap.entrySet().parallelStream().forEach(h  -> {
            headers.add(h.getKey(), h.getValue());
        });
//        headersMap.forEach(headers::set);
        return headers;
    }

    public Mono<JsonNode> putMonoClient(@NotNull final String url,@NotNull final Mono<T> t,@NotNull final Class<T> clazz, final Map<String, String> headersMap){
        return webClient.put().uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header -> addHeaders(header, headersMap))
                .body(t, clazz)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(JsonNode.class))
                .doOnNext( data ->  Reactive_LOGGER.info(INFO_MESSAGE, data))
                .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE + err.getMessage()))
                .log(PUT_MESSAGE);
    }

    public Mono<JsonNode> postMonoClient(@NotNull final String url, @NotNull final Mono<T> t,@NotNull Class<T> clazz, Map<String, String> headersMap){
        return webClient.post().uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header -> addHeaders(header, headersMap))
                .body(t, clazz)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(JsonNode.class))
                .doOnNext( data ->  Reactive_LOGGER.info(INFO_MESSAGE, data))
                .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE + err.getMessage()))
                .log(PUT_MESSAGE);
    }

    public Mono<JsonNode> deleteMonoClient(@NotNull final String url){
        return Mono.subscriberContext().flatMap( context ->{
            return webClient.delete().uri(url)
                    .exchange()
                    .flatMap( clientResponse -> clientResponse.bodyToMono(JsonNode.class))
                    .doOnNext( data -> Reactive_LOGGER.info(INFO_MESSAGE, data))
                    .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE+ err.getMessage()))
                    .log(DELETE_MESSAGE);
        });
    }

    public Flux<JsonNode> deleteFluxClient(@NotNull final String url){
        return webClient.delete().uri(url)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(JsonNode.class))
                .doOnNext( data -> Reactive_LOGGER.info(INFO_MESSAGE, data))
                .onErrorMap(err -> new ReactiveWebClientException(ERROR_MESSAGE+ err.getMessage()))
                .log(DELETE_MESSAGE);
    }
}
