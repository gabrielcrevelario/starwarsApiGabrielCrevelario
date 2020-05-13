package com.planet.api.planet.controller;

import com.planet.api.planet.event.ResourceCreateEvent;
import com.planet.api.planet.model.Planet;
import com.planet.api.planet.services.PlanetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Api(value="API REST Planets")
@CrossOrigin(origins = "*")
public class PlanetController implements Controller<Planet> {

    private Logger log =LoggerFactory.getLogger(PlanetController.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PlanetService planetService;

    @ApiOperation(value="Find Planets by Id")
    @GetMapping("/planets/id/{id}")
    @Override
    public ResponseEntity<Planet> findById(@PathVariable String id) {
        log.info("Search planet by Id");
        Planet planet = planetService.findById(id);
       return planet != null ? ResponseEntity.ok(planet) :  ResponseEntity.notFound().build();
    }
    @ApiOperation(value="Find all Planets")
    @GetMapping("/planets")
    @Override
    public ResponseEntity findAll(@RequestParam(value = "offset",
            required = false, defaultValue = "0") int offset, @RequestParam(value = "limit",  required = false,defaultValue = "10") int limit) {
        log.info("Search all planets");
       Pageable pageable = PageRequest.of(offset,limit);
        var planetPage = planetService.findAll(pageable);

        if(planetPage.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(new PageImpl<Planet>(planetPage.getContent(), pageable, planetPage.getSize()), HttpStatus.OK);
    }

    @ApiOperation(value="Find Planets by name")
    @GetMapping("/planets/name/{name}")
    @Override
    public ResponseEntity findByName( @PathVariable String name,
                                        @RequestParam(value = "offset",  required = false, defaultValue = "0") int offset,
                                     @RequestParam(value = "limit",  required = false,defaultValue = "10") int limit ) {
        Pageable pageable = PageRequest.of(offset, limit);
        log.info("Search planets by name");
        var planetPage = planetService.findByName(pageable, name);

        if (planetPage.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(new PageImpl<Planet>(planetPage.getContent(), pageable, planetPage.getSize()));
    }
    @ApiOperation(value="Create Planets")
    @PostMapping("/planets")
    @Override
    public ResponseEntity create(@RequestBody @Valid Planet planet, HttpServletResponse response) {
        log.info("Create planet");
        Planet plnCreated = planetService.create(planet);
        publisher.publishEvent(new ResourceCreateEvent(this, response, plnCreated.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(plnCreated);
    }

    @ApiOperation(value="Update Planets")
    @PutMapping("/planets")
    @Override
    public ResponseEntity<Planet> update(@RequestParam(defaultValue = "id") String id,
                                         HttpServletResponse response, @RequestBody @Valid Planet planet) {
        Planet plnUpdate = planetService.update(id, planet);
        log.info("Update planet");
        publisher.publishEvent(new ResourceCreateEvent(this, response, plnUpdate.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(plnUpdate);
    }
    @ApiOperation(value="Delete Planets")
    @DeleteMapping("/planets")
    @Override
    public ResponseEntity delete(@RequestParam(defaultValue = "id") String id) {
        log.info("Delete planet by id");
        planetService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Planet Removed");
    }
}
