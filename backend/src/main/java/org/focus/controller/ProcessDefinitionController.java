package org.focus.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.focus.dto.ProcessDefinitionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProcessDefinitionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionController.class);

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

    @GetMapping(value = "/api/process/definition/{id}", produces = {MediaType.APPLICATION_XML_VALUE})
    public String getProcessDefinition(@PathVariable String id) {
        try {
            final ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("zags_process")
                    .latestVersion()
                    .singleResult();

            final List<String> deploymentResources = repositoryService.getDeploymentResourceNames(processDefinition.getDeploymentId());
            final Optional<String> diagram = deploymentResources.stream().filter(r -> r.endsWith(".bpmn")).findFirst();
            if (diagram.isPresent()) {
                final String resourceName = diagram.get();
                final InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
                return IOUtils.toString(resourceAsStream, Charset.defaultCharset());
            }
        } catch (Exception e) {
            LOGGER.error("could not find process definition", e);
        }

        return null;

    }

    @PostMapping(value = "/api/process/definition",
            consumes = {MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String, String> addProcess(@RequestBody String xml) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("zags_process.bpmn"))) {
            writer.append(xml);
        } catch (IOException e) {
            LOGGER.info("could not save process");
        }

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
