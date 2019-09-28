package org.focus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessInstanceDto {
    private String instanceId;
    private String definitionId;
}
