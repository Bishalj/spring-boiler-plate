package com.application.mappers.v1;

import com.application.models.v1.Demo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class DemoResponseMapper {
    private String message;
    private HttpStatus status;
    private Demo demo;
}
