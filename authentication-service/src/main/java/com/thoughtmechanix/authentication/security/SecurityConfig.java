package com.thoughtmechanix.authentication.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
            authorizeRequest -> authorizeRequest.requestMatchers("/user").authenticated()
                .anyRequest().permitAll())
        .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
            jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(
                jwtAuthenticationConverter())))
        .exceptionHandling(exception -> {
          exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        })
        .formLogin(login -> {
          login.loginPage("/login").failureHandler((request, response, exception) -> {
            System.out.println("Authentication failed: " + exception.getMessage());
            request.getSession()
                .setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception.getMessage());
            response.sendRedirect("/login?error=true");
          });
        })
    ;
    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
      List<String> authorities = jwt.getClaimAsStringList("authorities");
      System.out.println("Authorities from JWT: " + authorities);
      if (authorities == null) {
        return Collections.emptyList();
      }
      return AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));
    });
    return converter;
  }
}
