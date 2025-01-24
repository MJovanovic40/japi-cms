package com.cms.japi.classgeneration.internal.generation.controller;

import com.cms.japi.classgeneration.internal.generation.dto.GeneratedDto;
import com.cms.japi.classgeneration.internal.generation.service.GeneratedService;
import com.cms.japi.classgeneration.internal.utils.ClassGenerationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
public class DefaultControllerProvider {

    private final GeneratedService generatedService;

    public ResponseEntity<GeneratedDto> create(@RequestBody @Valid GeneratedDto body) {
        return ResponseEntity.ok(
                generatedService.create(
                        ClassGenerationUtils.convertToVarArgs(
                                body,
                                List.of("id")
                        )
                )
        );
    }

    public ResponseEntity<List<GeneratedDto>> getAll() {
        return ResponseEntity.ok(generatedService.getAll());
    }

    public ResponseEntity<GeneratedDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(generatedService.get(id));
    }

    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody @Valid GeneratedDto body) {
        generatedService.update(id, ClassGenerationUtils.convertToVarArgs(body, List.of("id")));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        generatedService.delete(id);
        return ResponseEntity.ok().build();
    }
}
