package io.graxon.projects.system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/projects")
public class DebugController {


    private static final Logger log = LoggerFactory.getLogger(DebugController.class);

    /**
     *
     */
    @GetMapping("/debug")
    public String debug() {
        log.info("debugging...");
        return "projects-" + UUID.randomUUID().toString();
    }
}
