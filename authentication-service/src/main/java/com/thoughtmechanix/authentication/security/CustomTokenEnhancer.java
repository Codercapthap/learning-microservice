//package com.thoughtmechanix.authentication.security;
//
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//
//public class CustomTokenEnhancer extends TokenEnhancer {
//    @Override
//    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//      Map<String, Object> additionalInfo = new HashMap<>();
//      // Add custom fields
//      additionalInfo.put("customField1", "value1");
//      additionalInfo.put("customField2", "value2");
//
//      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
//      return accessToken;
//    }
//}
