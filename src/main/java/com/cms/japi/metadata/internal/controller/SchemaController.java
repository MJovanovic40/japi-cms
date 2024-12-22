package com.cms.japi.metadata.internal.controller;

import com.cms.japi.metadata.internal.entity.Schema;
import com.cms.japi.metadata.internal.requests.CreateSchemaRequest;
import com.cms.japi.metadata.internal.service.SchemaServiceImpl;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/schema")
public class SchemaController {
    private final SchemaServiceImpl schemaService;

    public SchemaController(SchemaServiceImpl schemaService) {
        this.schemaService = schemaService;
    }

    @PostMapping(path = "/")
    public Schema createSchema(@RequestBody @Valid CreateSchemaRequest body) {

        return schemaService.createSchema(body.getName());
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<Schema>> getAllSchemas() {
        return ResponseEntity.ok(schemaService.getAll());
    }

    @GetMapping(path = "/{schemaId}")
    public Schema getSchemaData(@PathVariable Integer schemaId) {
        return schemaService.getSchema(schemaId);
    }

    @PutMapping(path = "/{schemaId}")
    public String updateSchemaData(@PathVariable Integer schemaId, @RequestBody String schemaJsonString) {
        return schemaService.updateSchemaData(schemaId, schemaJsonString);
    }

    @DeleteMapping(path = "/{schemaId}")
    public String deleteSchema(@PathVariable Integer schemaId) {
        return schemaService.deleteSchema(schemaId);
    }
}
