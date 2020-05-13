package com.planet.api.planet.services;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.planet.api.planet.model.Planet;
import com.planet.api.planet.model.response.ResponseRest.ResponseRestPlanet;
import com.planet.api.planet.model.response.ResponseRest.SwapPlanet;
import com.planet.api.planet.repository.PlanetRepository;
import com.planet.api.planet.services.exception.NonExistPlanetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.BeanUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PlanetService implements Service<Planet> {

    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private SwapService swapService;

    @Override
    public Planet findById(String id) {
        Optional<Planet> planet = planetRepository.findById(id);
        if(planet.isEmpty())
            throw new NonExistPlanetException();
        return planet.get();
    }

    @Override
    public Page<Planet> findAll(Pageable pageable) {
         return formatedPlanet(planetRepository.findAll(pageable));
    }
    @Override
    public Page<Planet> findByName(Pageable pageable, String name) {
        return formatedPlanet(planetRepository.findByName(name, pageable));
    }

    @Override
    public void delete(String id) {
        Planet pln = findById(id);
        planetRepository.delete(pln);
    }

    @Override
    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    public Planet update(String id, Planet planet) {
        Planet pln = findById(id);
        BeanUtils.copyProperties(planet, pln, "id");
        return planetRepository.save(planet);
    }

    private Page<Planet> formatedPlanet(Page<Planet> pagePlanetsAll) {
        return pagePlanetsAll.map(planet -> {
            List<String> listTitleMovies = new ArrayList<String>();
            Optional<Planet> planetFind = null;
            try {
                planetFind = Optional.of(swapService.findPlanetWithMovies(planet));
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            if(planetFind.get().getFilms().size() > 0) {
                planet.setFilms(planetFind.get().getFilms());
                planet.setNumberOfFilmAppearances(planet.getFilms().size());
                return planet;
            } else {
                listTitleMovies.add("There are no films for this planet");
                planet.setFilms(listTitleMovies);
                return planet;
            }
        });
    }



}
