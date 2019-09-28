package org.focus.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.focus.dto.ProcessDefinitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProcessDefinitionController {


    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "/api/process/definition", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<ProcessDefinitionDto> listProcessDefinitions() {
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .list();

        return processDefinitions.stream().map(pd ->
                new ProcessDefinitionDto(pd.getId(), pd.getDescription(), pd.getKey(), pd.getName(), pd.getVersion())).collect(Collectors.toList());
    }

    @PostMapping(value = "/api/process/definition",
            consumes = {MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String, String> addProcess(@RequestBody String xml) {


        final Deployment deployment = repositoryService.createDeployment()
                .addInputStream(
                        "zags.bpmn",
                        IOUtils.toInputStream(xml, Charset.defaultCharset()))
                .key("zags")
                .name("zags")
                .category("zags")
                .deploy();

        return new HashMap<String, String>() {{
            put("deploymentId", deployment.getId());
        }};
    }
}
