package com.example.shared.authentication;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class SharedAuthenticationCondition implements Condition {

  private final String propertyName;

  public SharedAuthenticationCondition() {
    this.propertyName = "authentication.shared.issuer-uri";
  }

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Environment environment = context.getEnvironment();

    String uri = environment.getProperty(propertyName + "[0]", String.class);

    return StringUtils.hasText(uri);
  }

}
