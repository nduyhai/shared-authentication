package com.nduyhai.shared.authentication;


import java.util.List;


public class UrlSecurityRule {

  private String urlPattern;
  private AccessType accessType;
  private List<String> roles;

  public UrlSecurityRule(String urlPattern, AccessType accessType) {
    this.urlPattern = urlPattern;
    this.accessType = accessType;
  }

  public UrlSecurityRule(String urlPattern, AccessType accessType, List<String> roles) {
    this.urlPattern = urlPattern;
    this.accessType = accessType;
    this.roles = roles;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(String urlPattern) {
    this.urlPattern = urlPattern;
  }

  public AccessType getAccessType() {
    return accessType;
  }

  public void setAccessType(AccessType accessType) {
    this.accessType = accessType;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public enum AccessType {
    PERMIT, AUTHENTICATED, DENY, ROLE_BASED
  }
}
