package de.szut.lf8_starter.qualification;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

public class QualificationValidator {
    private String url = "https://employee.szut.dev/qualifications";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean doesQualificationExist(long id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return Boolean.TRUE.equals(restTemplate.getForObject(url, Boolean.class));
    }

}
