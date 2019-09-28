package org.focus.controller;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.focus.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;


    final static java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm");

    @GetMapping(value = "/api/task/{role}")
    public TaskDto getRoleTask(@PathVariable String role) {
        final List<Task> tasks = taskService.createTaskQuery()
                .taskNameLike(role + "%")
                .list();

        if (tasks.isEmpty())
            return null;

        final Task task = tasks.iterator().next();


        return new TaskDto(task.getId(), task.getName(), task.getAssignee(),
                format.format(task.getCreateTime()),
                taskService.getVariables(task.getId()));
    }

    @GetMapping(value = "/api/task", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Set<TaskDto> getAll() {
        return taskService.createTaskQuery()
                .list()
                .stream()
                .map(task -> {
                    return new TaskDto(
                            task.getId(),
                            task.getName(),
                            task.getAssignee(),
                            format.format(task.getCreateTime()),
                            taskService.getVariables(task.getId()));
                })
                .collect(Collectors.toSet());
    }

    @PostMapping(value = "/api/task/{id}")
    public void completeTask(@PathVariable String id, @RequestBody Map<String, Object> taskData) {
        taskService.complete(id, taskData);
    }
}
