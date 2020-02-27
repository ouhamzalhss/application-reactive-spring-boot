package com.ouhamza.webflux.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * @author Lhouceine OUHAMZA
 */
@Document
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Matches {
    @Id
    private String id;
    private Instant instant;
    private Double points;
    @DBRef
    private Teams teams;

}
