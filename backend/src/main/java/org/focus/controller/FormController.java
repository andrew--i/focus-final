package org.focus.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class FormController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormController.class);

    private String getFormFileName(String form) {
        return form + "_form.json";
    }

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping(value = "/api/form")
    public List<String> getForms() throws IOException {
        final List<Deployment> deployments = repositoryService.createDeploymentQuery()
                .list();

        List<String> forms = new ArrayList<>();
        for (Deployment deployment : deployments) {
            final List<String> deploymentResources = repositoryService.getDeploymentResourceNames(deployment.getId());
            final Optional<String> diagram = deploymentResources.stream().filter(r -> r.endsWith(".bpmn")).findFirst();
            if (diagram.isPresent()) {
                final String resourceName = diagram.get();
                final InputStream resourceAsStream = repositoryService.getResourceAsStream(deployment.getId(), resourceName);
                final String bpmn = IOUtils.toString(resourceAsStream, Charset.defaultCharset());


                final String regex = "userTask .*(name)=\"(.*)\"";
                final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(bpmn);

                while (matcher.find()) {
                    if (matcher.groupCount() > 1) {
                        forms.add(matcher.group(2));
                    }
                }

            }
        }
        return forms;
    }

    @PostMapping(value = "/api/form/{name}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void saveForm(@RequestBody String formJson, @PathVariable String name) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFormFileName(name)))) {
            writer.append(formJson);
        }
    }

    @GetMapping(value = "/api/form/{name}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void getForm(@PathVariable String name, HttpServletResponse response) {

        final String fileName = getFormFileName(name);
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(fileName));
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            LOGGER.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
