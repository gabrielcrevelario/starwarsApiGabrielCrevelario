package com.planet.api.planet.repository;

import com.planet.api.planet.model.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public  interface PlanetRepository extends MongoRepository<Planet, String> {
    @Query("{'name': {$regex: ?0 }})")
    Page<Planet> findByName(String name, Pageable pageable);
}
