package com.cms.japi.metadata.internal.service;

import com.cms.japi.metadata.internal.SchemaService;
import com.cms.japi.metadata.internal.entity.Schema;
import com.cms.japi.metadata.internal.repository.SchemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaServiceImpl implements SchemaService {
    private final SchemaRepository schemaRepository;

    public SchemaServiceImpl(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    public Schema createSchema(String schemaName) {
        Schema newSchema = new Schema();
        newSchema.setName(schemaName);

        return schemaRepository.save(newSchema);
    }

    public List<Schema> getAll() {
        return schemaRepository.findAll();
    }

    public Schema getSchema(Integer schemaId) {
        return schemaRepository.findById(schemaId)
                .orElseThrow(() -> new RuntimeException("Schema not found"));
    }

    public String updateSchemaData(Integer schemaId, String schemaJsonString) {
        return schemaRepository.findById(schemaId)
                .map(schema -> {
                    schema.setData(schemaJsonString);
                    schemaRepository.save(schema);
                    return "Data saved";
                })
                .orElseGet(() -> "Schema not found");
    }

    public String deleteSchema(Integer schemaId) {
        schemaRepository.deleteById(schemaId);
        return "Schema deleted";
    }

}
