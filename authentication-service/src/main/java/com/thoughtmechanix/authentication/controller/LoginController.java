package com.thoughtmechanix.authentication.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
  private final AuthenticationManager authenticationManager;

  public LoginController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/login")
  public String login() {
    return "login"; // Return a view name for the login page
  }

//  @PostMapping("/login")
//  public String processLogin(@RequestParam String username, @RequestParam String password) {
//    Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
//    authenticationManager.authenticate(auth); // Handle authentication
//    return "redirect:/"; // Redirect after successful login
//  }
}
