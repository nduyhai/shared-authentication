package com.nduyhai.rsexample;

import com.nduyhai.shared.authentication.AuthUtils;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/greeting")
public class GreetingController {

  @Observed
  @GetMapping
  public String greeting(Authentication authentication) {
    log.info("Found login user {}", authentication);
    log.info("Found login user from iss {}, sub {}", AuthUtils.getIssuer().orElse(""),
        AuthUtils.getSub().orElse(""));
    return "Greetings from Spring Boot!";
  }
}
