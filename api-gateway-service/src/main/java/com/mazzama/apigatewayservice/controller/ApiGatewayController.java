package com.mazzama.apigatewayservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by azzam on 28/03/19.
 */
public class ApiGatewayController {

  private static Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

  public String testEndpoint() {
    logger.info("Berada di api gateway");
    return "Kamu berada di api gateway";
  }
}
