package com.planet.api.planet.model.response.ResponseRest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planet.api.planet.model.Planet;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRestPlanet {
    private SwapPlanet[] results;
}
