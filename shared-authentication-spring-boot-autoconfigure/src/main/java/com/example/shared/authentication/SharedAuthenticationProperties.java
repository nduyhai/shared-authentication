package com.example.shared.authentication;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "authentication.shared")
public class SharedAuthenticationProperties {

  private List<String> issuerUri;

  public List<String> getIssuerUri() {
    return issuerUri;
  }

  public void setIssuerUri(List<String> issuerUri) {
    this.issuerUri = issuerUri;
  }
}
