package de.szut.lf8_starter.employee;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class EmployeeValidator {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://employee.szut.dev/employees";


    public boolean doesEmployeeExist(long id, @RequestHeader("Authorization") String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
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


//    public static void main(String[] args) {
//        System.out.println(doesEmployeeExist(1));
//    }
}



