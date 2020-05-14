package com.planet.api.planet.services;

import com.planet.api.planet.model.Planet;
import com.planet.api.planet.repository.PlanetRepository;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.CoreMatchers.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlanetServiceTest {
    @InjectMocks
    private PlanetService planetService;
    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private SwapService swapService;
    @Before
    public void setup() {
    MockitoAnnotations.initMocks(this);
    }
    @Test
    public void findById() throws NullPointerException {

    }

    @Test
    public void createTest() {
        Planet planet = generateMock();
        when(planetRepository.save(planet)).thenReturn(planet);
        Planet planetCompare = planetService.create(planet);
        Assert.assertEquals(planet, planetCompare);
    }
    @Test
    public void updateTest() {
        Optional<Planet> plnMock = Optional.of(new Planet());
        String id = "1234";
        Planet planet = generateMock();
        when(planetRepository.save(planet)).thenReturn(planet);
        when(planetRepository.findById(id)).thenReturn(plnMock);
        Planet planetCompare = planetService.update(id, planet);
        Assert.assertEquals(plnMock.get(), planetCompare);
    }


    private Planet generateMock() {
        return Planet.builder().id("saadsfa").name("name").climate("climate1")
                .terrain("terrain").films(new ArrayList<String>(Collections.singleton("filmsTest")))
                .diameter("").gravity("").population("").numberOfFilmAppearances(0)
                .build();
    }

}
