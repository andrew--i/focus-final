package org.focus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessDefinitionDto {
    private String id;
    private String description;
    private String formKey;
    private String key;
    private String name;
    private int version;

}
