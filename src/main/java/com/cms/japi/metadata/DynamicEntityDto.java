package com.cms.japi.metadata;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.cms.japi.metadata.internal.entities.DynamicEntity}
 */
@Data
@NoArgsConstructor
public class DynamicEntityDto implements Serializable {
    Integer id;
    String name;
    String data;
}