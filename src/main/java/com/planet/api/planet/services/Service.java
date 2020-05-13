package com.planet.api.planet.services;
import com.planet.api.planet.model.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface Service<T> {
    T findById(String id);
    Page<T> findAll(Pageable pageable);
    Page<T> findByName(Pageable pageable, String name);
    void delete(String id);
    T create(T t);
    T update(String id, T t);
}
