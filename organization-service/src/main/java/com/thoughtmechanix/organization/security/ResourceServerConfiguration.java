package com.thoughtmechanix.organization.security;


import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.DELETE, "/v1/organizations/**").hasRole("ADMIN")
//            .anyRequest().authenticated()  // Protect all endpoints
//        )
//        .oauth2ResourceServer(oauth2 -> oauth2
//            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//        );
    http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection if you don't need it
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll() // Allow all requests without authentication
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );
    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    // Configure the decoder to validate JWTs from the authentication server
    return JwtDecoders.fromIssuerLocation(
        "http://localhost:8901"); // Authentication server's issuer URL
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
      List<String> authorities = jwt.getClaimAsStringList("authorities");
      if (authorities == null) {
        return Collections.emptyList();
      }
      return AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));
    });
    return converter;
  }
}
