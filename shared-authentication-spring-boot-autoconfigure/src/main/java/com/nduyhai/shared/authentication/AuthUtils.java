package com.nduyhai.shared.authentication;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class AuthUtils {

  private AuthUtils() {
  }

  public static Optional<String> getClaims(String name, Authentication authentication) {
    if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
      Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();
      return Optional.ofNullable(jwt.getClaim(name));
    }
    return Optional.empty();
  }

  public static Optional<String> getClaims(String name) {
    return getClaims(name, SecurityContextHolder.getContext().getAuthentication());
  }


  public static Optional<String> getIssuer() {
    return getClaims(IdTokenClaimNames.ISS);
  }

  public static Optional<String> getSub() {
    return getClaims(IdTokenClaimNames.SUB);
  }

}
