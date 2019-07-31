package com.application.models.v1;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Demo {
    @Id
    private String id;
    private String name;
}
