package de.szut.lf8_starter.qualification;

import org.springframework.web.client.RestTemplate;

public class QualificationValidator {
    private String url = "https://employee.szut.dev/qualifications";
    private final RestTemplate restTemplate = new RestTemplate();


}
