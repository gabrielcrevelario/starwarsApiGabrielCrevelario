package com.planet.api.planet.services;
import com.planet.api.planet.model.Planet;
import com.planet.api.planet.repository.PlanetRepository;
import com.planet.api.planet.services.exception.NonExistPlanetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

@org.springframework.stereotype.Service
public class PlanetService implements Service<Planet> {

    private Logger log = LoggerFactory.getLogger(PlanetService.class);

    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private SwapService swapService;

    @Override
    public Planet findById(String id) {
        log.info("Accessing the database repository to get the planet by id: "+ id);
        Optional<Planet> planet = planetRepository.findById(id);
        if (planet.isPresent()) {
            swapService.listFimlsWithPlanets(planet.get());
            return planet.get();
        } else {
            log.error("Returning exception with status 404 the planet is not found by id :" + id);
            throw new NonExistPlanetException();
        }
    }

    @Override
    public Page<Planet> findAll(Pageable pageable) {
        log.info("accessing the database to obtain all the planets paginated with their films");
        return formatedPlanet(planetRepository.findAll(pageable));
    }
    @Override
    public Page<Planet> findByName(Pageable pageable, String name) {
        log.info("Find planet by Name");
        return formatedPlanet(planetRepository.findByName(name, pageable));
    }

    @Override
    public void delete(String id) {
        log.info("delete object planet by Id");
        Planet pln = findById(id);
        planetRepository.delete(pln);
    }

    @Override
    public Planet create(Planet planet) {
        log.info("Created object planet after valid properties");
        Planet plnCreated = planetRepository.save(planet);
        swapService.listFimlsWithPlanets(plnCreated);
        return plnCreated;
    }

    public Planet update(String id, Planet planet) {
        log.info("Update object planet and copying properties that have not changed");
        Planet pln = findById(id);
        BeanUtils.copyProperties(planet, pln, "id");
        Planet plnUpdated = planetRepository.save(planet);
        swapService.listFimlsWithPlanets(plnUpdated);
        return plnUpdated;
    }

    private Page<Planet> formatedPlanet(Page<Planet> pagePlanetsAll) {
        return pagePlanetsAll.map(planet -> {
            swapService.listFimlsWithPlanets(planet);
            return planet;
        });
    }



}
