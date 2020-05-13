package com.planet.api.planet.services;

import com.planet.api.planet.model.Planet;
import com.planet.api.planet.model.response.ResponseRest.ResponseRestPlanet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SwapService {
    private Logger log = LoggerFactory.getLogger(SwapService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.planet}")
    private String urlPlanet;
    private List<String> findPlanetWithMovies(Planet planet) {
        String uriPlanets = UriComponentsBuilder.fromHttpUrl(urlPlanet).queryParam("search", planet.getName()).toUriString();
        Optional<ResponseRestPlanet> listPln = Optional.ofNullable(restTemplate.getForObject(uriPlanets, ResponseRestPlanet.class));
        if (listPln.isPresent() && listPln.get().getResults().length > 0)
           return listPln.get().getResults()[0].getFilms();
        else
            return new ArrayList<String>();
    }
    public void listFimlsWithPlanets(Planet planet) {
        planet.setFilms(findPlanetWithMovies(planet));
        if(planet.getFilms().size() > 0) {
            planet.setNumberOfFilmAppearances(planet.getFilms().size());
            log.info("returning planet with their respective films and the number of films");
        } else {
            log.info("There are no films for this planet");
            planet.getFilms().add("There are no films for this planet");
        }
    }

}
