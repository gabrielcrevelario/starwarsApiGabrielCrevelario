package com.planet.api.planet.model.response.ResponseRest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapPlanet {
    private String name;
    private String terrain;

    private String diameter;

    private String gravity;

    private String population;
    private List<String> films;

}
