package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author DeepakKumar
 */

@RestController()
@Api(tags = {"HealthCheckAPI"})
@RequestMapping("/springboot/demo/v1/health")
public class PublicHealthController {
    private static final Logger logger = LoggerFactory.getLogger(PublicHealthController.class);

    private final HealthEndpoint healthEndpoint;


    @Autowired
    public PublicHealthController(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @GetMapping
    @ApiOperation(value = "Health Check API", response = String.class)
    public ResponseEntity<String> publicHealthIndicator() {
        Health health = healthEndpoint.health();
        if(! health.getStatus().equals(Status.DOWN)) {
            return new ResponseEntity<>("UP", HttpStatus.OK);
        }
        logger.warn("Health warning :{} " , health.toString() );
        return new ResponseEntity<>(health.getStatus().getCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
