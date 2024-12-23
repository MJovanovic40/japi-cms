package com.cms.japi.metadata.internal.controllers;

import com.cms.japi.metadata.DynamicEntityDto;
import com.cms.japi.metadata.internal.requests.CreateDynamicEntityRequest;
import com.cms.japi.metadata.internal.requests.UpdateDynamicEntityRequest;
import com.cms.japi.metadata.internal.services.DynamicEntityServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/dynamic-entity")
public class DynamicEntityController {

    private final DynamicEntityServiceImpl dynamicEntityService;

    @PostMapping
    public ResponseEntity<DynamicEntityDto> createDynamicEntity(@RequestBody @Valid CreateDynamicEntityRequest body) {
        return ResponseEntity.ok(dynamicEntityService.createDynamicEntity(body.getName()));
    }

    @GetMapping
    public ResponseEntity<List<DynamicEntityDto>> getAllDynamicEntities() {
        return ResponseEntity.ok(dynamicEntityService.getAll());
    }

    @GetMapping(path = "/{dynamicEntityId}")
    public ResponseEntity<DynamicEntityDto> getDynamicEntityData(@PathVariable Integer dynamicEntityId) {
        return ResponseEntity.ok(dynamicEntityService.getDynamicEntity(dynamicEntityId));
    }

    @PutMapping(path = "/{dynamicEntityId}")
    public ResponseEntity<Object> updateDynamicEntityData(@PathVariable Integer dynamicEntityId, @RequestBody @Valid UpdateDynamicEntityRequest request) {
        dynamicEntityService.updateDynamicEntityData(dynamicEntityId, request.getName(), request.getData());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{dynamicEntityId}")
    public ResponseEntity<Object> deleteDynamicEntity(@PathVariable Integer dynamicEntityId) {
        dynamicEntityService.deleteDynamicEntity(dynamicEntityId);
        return ResponseEntity.ok().build();
    }
}
