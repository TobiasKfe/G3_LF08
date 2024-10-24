package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.security.KeyCloakTokenService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class EmployeeValidator {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://employee.szut.dev/employees";


    public boolean doesEmployeeExist(long id) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + KeyCloakTokenService.getKeycloakToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> result = restTemplate.exchange(url + "/" + id, HttpMethod.GET, entity, String.class);
            return result.getStatusCode() == HttpStatus.OK && result.getBody() != null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
        return false;
    }

    public boolean isSkillsetValid(long skillId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + KeyCloakTokenService.getKeycloakToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String urlQ = "https://employee.szut.dev/qualifications";

        try {
            ResponseEntity<String> response = restTemplate.exchange(urlQ, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JSONArray qualificationsArray = new JSONArray(response.getBody());

                for (int i = 0; i < qualificationsArray.length(); i++) {
                    JSONObject qualification = qualificationsArray.getJSONObject(i);
                    if (qualification.getLong("id") == skillId) {
                        return true; // Skill ID exists
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        return false;
    }
}



