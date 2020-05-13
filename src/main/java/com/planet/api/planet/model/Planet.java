package com.planet.api.planet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import java.util.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private String climate;

    @NotBlank
    private String terrain;

    private String diameter;

    private String gravity;

    private String population;

    private List<String> films;

    private int numberOfFilmAppearances;

}
