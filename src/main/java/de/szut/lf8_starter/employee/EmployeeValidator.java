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

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "https://employee.szut.dev/employees";
//    @RequestHeader("Authorization") String authorizationHeader

    public static boolean doesEmployeeExist(long id) {
//        String token = authorizationHeader.replace("Bearer ", "");
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3Mjk3ODQ3NTIsImlhdCI6MTcyOTc4MTE1MiwianRpIjoiMDNmZGY2YzktNjk2OS00Zjc1LTg2NDMtMTQ0ZTEwNTI3NDUzIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI2NDIxNTU4Mi1hNmJhLTQ4ZTQtODg1NC04ZDJlMGQ5MTc3MzQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.Pun0oL79zEcPVZLKT0TTUd5Y7sA-O8NJCZILlJO8PqTOPq0-KHBIJrl8NSOe2VGMIPy6LNkWZdCSkQMP_yFlNOKqEJo7WC6uzfruNmLnFy8gdrJ88fyzwdZu0JVUDeN_YVyXzTBbdGA1RQnrFPBSd9hNQyjvKZdPySuRVyLg8VVbnUu1_NhWJMS1Xr1vvAPLdmbsucbSw8cOT_ypwXCeEybCCN1noHJZf9paX1Q_ri_BJnIVJ16fF-sN6fkn6ADDtrtFu6hflIsxgXNb9c8pKMcvh1e2boHplIeTIinpc0GKueeuDCw_lcyM9o2qB--G1a5m7ZbAp2fyYwb49rUtiw";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(url +"/{id}", HttpMethod.GET, entity, String.class, id);
        return result.getBody() != null;


//        HttpURLConnection connection = null;
//        String bearerToken = null;
//        try {
//            bearerToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3Mjk3ODQ3NTIsImlhdCI6MTcyOTc4MTE1MiwianRpIjoiMDNmZGY2YzktNjk2OS00Zjc1LTg2NDMtMTQ0ZTEwNTI3NDUzIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI2NDIxNTU4Mi1hNmJhLTQ4ZTQtODg1NC04ZDJlMGQ5MTc3MzQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.Pun0oL79zEcPVZLKT0TTUd5Y7sA-O8NJCZILlJO8PqTOPq0-KHBIJrl8NSOe2VGMIPy6LNkWZdCSkQMP_yFlNOKqEJo7WC6uzfruNmLnFy8gdrJ88fyzwdZu0JVUDeN_YVyXzTBbdGA1RQnrFPBSd9hNQyjvKZdPySuRVyLg8VVbnUu1_NhWJMS1Xr1vvAPLdmbsucbSw8cOT_ypwXCeEybCCN1noHJZf9paX1Q_ri_BJnIVJ16fF-sN6fkn6ADDtrtFu6hflIsxgXNb9c8pKMcvh1e2boHplIeTIinpc0GKueeuDCw_lcyM9o2qB--G1a5m7ZbAp2fyYwb49rUtiw";
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            URL url = new URL("https://employee.szut.dev/employees/" + id);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
//            connection.setRequestProperty("Content-Type", "application/json");
//
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                return true;
//            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
//                return false;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//
//        return false;
//    }

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + authorizationHeader);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        try {
//            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//            if (result.getStatusCode() == HttpStatus.OK) {
//                return result.getBody() != null;
//            }
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//                System.err.println("Unauthorized: " + e.getMessage());
//            }
//        } catch (Exception e) {
//            System.err.println("Error occurred: " + e.getMessage());
//        }
//
//        return false;
    }

    public static boolean doesEmployeeExists(long id) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3Mjk3ODQ3NTIsImlhdCI6MTcyOTc4MTE1MiwianRpIjoiMDNmZGY2YzktNjk2OS00Zjc1LTg2NDMtMTQ0ZTEwNTI3NDUzIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI2NDIxNTU4Mi1hNmJhLTQ4ZTQtODg1NC04ZDJlMGQ5MTc3MzQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.Pun0oL79zEcPVZLKT0TTUd5Y7sA-O8NJCZILlJO8PqTOPq0-KHBIJrl8NSOe2VGMIPy6LNkWZdCSkQMP_yFlNOKqEJo7WC6uzfruNmLnFy8gdrJ88fyzwdZu0JVUDeN_YVyXzTBbdGA1RQnrFPBSd9hNQyjvKZdPySuRVyLg8VVbnUu1_NhWJMS1Xr1vvAPLdmbsucbSw8cOT_ypwXCeEybCCN1noHJZf9paX1Q_ri_BJnIVJ16fF-sN6fkn6ADDtrtFu6hflIsxgXNb9c8pKMcvh1e2boHplIeTIinpc0GKueeuDCw_lcyM9o2qB--G1a5m7ZbAp2fyYwb49rUtiw";
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "https://employee.szut.dev/employees/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return result.getStatusCode() == HttpStatus.OK && result.getBody() != null;
        } catch (HttpClientErrorException e) {
            // Handle client error exceptions (e.g., 404 Not Found)
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false; // Employee does not exist
            }
            // Log or handle other client error responses if necessary
            } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error occurred: " + e.getMessage());
        }        return false; // Default return false for other error cases
    }

    public static void main(String[] args) {
        System.out.println(doesEmployeeExists(298));
    }
}


//        return restTemplate.getForObject(url + "{id}", HttpStatusCode.class, id);
//        if (result.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
//            return false;
//        }
