package de.szut.lf8_starter.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

//@Service
//public class KeyCloakTokenService {
//
//    public HttpEntity<Void> getAuthorizationHeaderWithBearerToken(@RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//         return new HttpEntity<>(headers);
//    }
//}
