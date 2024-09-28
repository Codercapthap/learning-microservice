package com.thoughtmechanix.authentication.security;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class JwtConfig {
//  @Bean
//  public KeyPair rsaKeyPair() throws Exception {
//    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//    keyPairGenerator.initialize(2048);
//    return keyPairGenerator.generateKeyPair();
//  }
//
//  @Bean
//  public JwtEncoder jwtEncoder(KeyPair keyPair) {
//    RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate()).build();
//    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
//    return new NimbusJwtEncoder(jwkSource);
//  }
//

  // Base64-encoded signing key
  private static final String SIGNING_KEY = "345345fsdgsf5345";
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
      UserDetailsService userDetailsService) {
    return context -> {
      if (context.getPrincipal() != null) {
        Authentication authentication = (Authentication) context.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> authorityList = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        context.getClaims().claim("authorities", authorityList);
      }
    };
  }
  @Bean
  public TokenSettings tokenSettings() {
    return TokenSettings.builder()
        .accessTokenTimeToLive(Duration.ofMinutes(30)) // Access token validity
        .refreshTokenTimeToLive(Duration.ofDays(30))   // Refresh token validity
        .build();
  }

//  @Bean
//  public JwtDecoder jwtDecoder() {
//    String secret = "345345fsdgsf5345";
//    SecretKey key = new SecretKeySpec(secret.getBytes(), "HmacSHA256"); // Adjust algorithm as needed
//    return NimbusJwtDecoder.withSecretKey(key).build();
//  }
//  @Bean
//  public JwtDecoder jwtDecoder() {
//    // Configure the decoder to validate JWTs from the authentication server
//    return JwtDecoders.fromIssuerLocation("http://localhost:8901"); // Authentication server's issuer URL
//  }
}
