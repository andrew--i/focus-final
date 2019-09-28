package org.focus.controller;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.focus.dto.ProcessDefinitionDto;
import org.focus.dto.ProcessInstanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProcessInstanceController {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "/api/process/instance", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<ProcessDefinitionDto> listProcessDefinitions() {
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        return processDefinitionPage.getContent().stream()
                .map(pd ->
                        new ProcessDefinitionDto(pd.getId(), pd.getDescription(), pd.getKey(), pd.getName(), pd.getVersion()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/api/process/instance/{id}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ProcessInstanceDto startProcess(@PathVariable("id") String processDefinitionKey) {
        final org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();

        final ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        return new ProcessInstanceDto(processInstance.getProcessInstanceId(), processInstance.getProcessDefinitionId());
    }

    @GetMapping(value = "/api/process/instance/{id}")
    public boolean hasRunningProcess(@PathVariable String id) {

        final long runningProcesses = runtimeService.createExecutionQuery()
                .processDefinitionKey(id)
                .count();

        return runningProcesses != 0;
    }
}
