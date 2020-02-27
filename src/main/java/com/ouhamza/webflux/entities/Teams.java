package com.ouhamza.webflux.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Lhouceine OUHAMZA
 */

@Document
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Teams {
    @Id
    private String id;
    private String name;
    private int points;



}
