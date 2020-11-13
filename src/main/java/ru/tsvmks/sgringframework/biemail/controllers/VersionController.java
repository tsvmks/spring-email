package ru.tsvmks.sgringframework.biemail.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsvmks.sgringframework.biemail.model.Version;

@RestController
public class VersionController {
    @Autowired
    private org.springframework.context.ApplicationContext applicationContext;

    @GetMapping("/version")
    String version() {
        Version version = (Version) applicationContext.getBean("versionSingleton");

        return version.getVersion();
    }
}
