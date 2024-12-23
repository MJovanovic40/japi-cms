package com.cms.japi.metadata.internal.controller;

import com.cms.japi.metadata.internal.entity.DynamicEntity;
import com.cms.japi.metadata.internal.requests.CreateDynamicEntityRequest;
import com.cms.japi.metadata.internal.requests.UpdateDynamicEntityRequest;
import com.cms.japi.metadata.internal.service.DynamicEntityServiceImpl;
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
    public ResponseEntity<DynamicEntity> createDynamicEntity(@RequestBody @Valid CreateDynamicEntityRequest body) {
        return ResponseEntity.ok(dynamicEntityService.createDynamicEntity(body.getName()));
    }

    @GetMapping
    public ResponseEntity<List<DynamicEntity>> getAllDynamicEntities() {
        return ResponseEntity.ok(dynamicEntityService.getAll());
    }

    @GetMapping(path = "/{dynamicEntityId}")
    public ResponseEntity<DynamicEntity> getDynamicEntityData(@PathVariable Integer dynamicEntityId) {
        DynamicEntity dynamicEntity = dynamicEntityService.getDynamicEntity(dynamicEntityId);
        return ResponseEntity.ok(dynamicEntity);
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
