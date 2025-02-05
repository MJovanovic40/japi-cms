package com.cms.japi.metadata.internal.entities;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DynamicEntity implements Serializable {
    private Integer id;

    @EqualsExclude
    private String name;

    @EqualsExclude
    private String data;
}
