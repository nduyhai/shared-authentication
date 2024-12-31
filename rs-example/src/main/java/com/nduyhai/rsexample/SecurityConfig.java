package com.nduyhai.rsexample;

import com.nduyhai.shared.authentication.UrlSecurityRule;
import com.nduyhai.shared.authentication.UrlSecurityRule.AccessType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);



  @Bean
  public UrlSecurityRule userApiRule2() {
    return new UrlSecurityRule("/**", AccessType.PERMIT);
  }

  @EventListener
  public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
    logger.error("Authentication failed for user: {}", 
        event.getAuthentication().getName(), 
        event.getException());
  }

}
