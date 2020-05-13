package com.planet.api.planet.services;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.planet.api.planet.model.Planet;
import com.planet.api.planet.model.response.ResponseRest.ResponseRestPlanet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SwapService {

    @Value("${url.planet}")
    private String urlPlanet;

    public Planet findPlanetWithMovies(Planet planet) throws UnirestException {
        Optional<ResponseRestPlanet> listPln = Optional.ofNullable(Unirest.get(urlPlanet)
                .queryString("search", planet.getName()).asObject(ResponseRestPlanet.class).getBody());
        List<String> listFilms = new ArrayList<>();
        if(listPln.isPresent())
            listFilms = listPln.get().getResults()[0].getFilms();
        if(listFilms.size() > 0)
            planet.setFilms(listFilms);
        else
            planet.setFilms(new ArrayList<>());
        return planet;
    }

}
