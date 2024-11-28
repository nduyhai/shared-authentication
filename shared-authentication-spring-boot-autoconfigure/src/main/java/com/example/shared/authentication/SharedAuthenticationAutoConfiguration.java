package com.example.shared.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.CollectionUtils;

@AutoConfiguration(before = SecurityAutoConfiguration.class)
@Conditional(SharedAuthenticationCondition.class)
@EnableConfigurationProperties(SharedAuthenticationProperties.class)
public class SharedAuthenticationAutoConfiguration {


  @Bean
  public SecurityFilterChain jwtFilterChain(HttpSecurity http,
      JwtIssuerAuthenticationManagerResolver jwtIssuerAuthenticationManagerResolver,
      AuthenticationEntryPoint authenticationEntryPoint,
      AccessDeniedHandler accessDeniedHandler,
      List<UrlSecurityRule> urlSecurityRules) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(customizeAuthorization(urlSecurityRules))
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.authenticationManagerResolver(jwtIssuerAuthenticationManagerResolver)
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler))
    ;

    return http.build();
  }

  private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> customizeAuthorization(
      List<UrlSecurityRule> rules) {
    return registry -> {

      if (rules.isEmpty()) {
        registry.anyRequest().permitAll();
        return;
      }
      for (UrlSecurityRule rule : rules) {
        switch (rule.getAccessType()) {
          case PERMIT:
            registry.requestMatchers(rule.getUrlPattern()).permitAll();
            break;
          case DENY:
            registry.requestMatchers(rule.getUrlPattern()).denyAll();
            break;
          case AUTHENTICATED:
            registry.requestMatchers(rule.getUrlPattern()).authenticated();
            break;
          case ROLE_BASED:
            if (!CollectionUtils.isEmpty(rule.getRoles())) {
              registry.requestMatchers(rule.getUrlPattern())
                  .hasAnyRole(rule.getRoles().toArray(new String[0]));
            }
            break;
          default:
            throw new IllegalArgumentException("Unknown access type: " + rule.getAccessType());
        }
      }
      registry.anyRequest().authenticated();

    };
  }

  @Bean
  @ConditionalOnMissingBean(JwtIssuerAuthenticationManagerResolver.class)
  public JwtIssuerAuthenticationManagerResolver jwtIssuerAuthenticationManagerResolver(
      SharedAuthenticationProperties properties) {
    return JwtIssuerAuthenticationManagerResolver.fromTrustedIssuers(properties.getIssuerUri());
  }


  @Bean
  @ConditionalOnMissingBean(AccessDeniedHandler.class)
  public AccessDeniedHandler accessDeniedHandler() {
    return new OauthAccessDeniedHandler(new ObjectMapper());
  }


  @Bean
  @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new OauthEntryPoint(new ObjectMapper());
  }
}
