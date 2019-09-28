package org.focus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TaskDto {
    private final String id;
    private final String name;
    private final String assignee;
    private final String createTime;
    private final Map context;

}
