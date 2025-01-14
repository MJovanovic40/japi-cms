package com.cms.japi.classgeneration.internal.generation.controller;

import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

public class GeneratedController {

    public ResponseEntity<GeneratedDto> create(@RequestBody @Valid GeneratedDto body) {
        return null;
    }

    public List<ResponseEntity<GeneratedDto>> getAll() {
        return Collections.emptyList();
    }

    public ResponseEntity<GeneratedDto> get(@PathVariable("id") Long id) {
        return null;
    }

    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody @Valid GeneratedDto body) {
        return null;
    }

    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return null;
    }
}
