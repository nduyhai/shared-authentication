package com.nduyhai.rsexample;

import com.nduyhai.shared.authentication.UrlSecurityRule;
import com.nduyhai.shared.authentication.UrlSecurityRule.AccessType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

  @Bean
  public UrlSecurityRule publicRule() {
    return new UrlSecurityRule("/actuator/**", AccessType.PERMIT);
  }

  @Bean
  public UrlSecurityRule greetingRule() {
    return new UrlSecurityRule("/greeting/**", AccessType.AUTHENTICATED);
  }
}
