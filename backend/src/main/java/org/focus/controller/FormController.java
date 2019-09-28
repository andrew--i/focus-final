package org.focus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class FormController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormController.class);

    private String getFormFileName(String form) {
        return form + "_form.json";
    }

    @PostMapping(value = "/api/form/{name}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void saveForm(@RequestBody String formJson, @PathVariable String name) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFormFileName(name)))) {
            writer.append(formJson);
        }
    }

    @GetMapping(value = "/api/form/{name}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
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
