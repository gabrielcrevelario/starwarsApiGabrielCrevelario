package com.planet.api.planet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.print.Pageable;

public interface Controller<T> {
    ResponseEntity<T> findById(@PathVariable String id);
    ResponseEntity finAll(@RequestParam(value = "offset",  required = false) int offset, @RequestParam(value = "limit",  required = false) int limit);
    ResponseEntity findByName(@PathVariable String name, @RequestParam(value = "offset",  required = false) int offset, @RequestParam(value = "limit",
            required = false) int limit);
    ResponseEntity create(@RequestBody @Valid T t, HttpServletResponse response);
    ResponseEntity<T> update(@PathVariable String id,HttpServletResponse response, @RequestBody @Valid T t);
    ResponseEntity delete(@PathVariable String id);
}
