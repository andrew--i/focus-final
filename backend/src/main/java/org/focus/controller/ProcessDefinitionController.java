package org.focus.controller;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.focus.dto.ProcessDefinitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProcessDefinitionController {

    @Autowired
    private ProcessRuntime processRuntime;

    @GetMapping(value = "/process", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<ProcessDefinitionDto> listProcessDefinitions() {
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        return processDefinitionPage.getContent().stream().map(pd -> new ProcessDefinitionDto(pd.getId(), pd.getDescription(), pd.getFormKey(), pd.getKey(), pd.getName(), pd.getVersion())).collect(Collectors.toList());
    }
}
