package de.szut.lf8_starter.qualification;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

@Service
public class QualificationService {
    private String qualificationUrl = "https://employee.szut.dev/qualifications";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3Mjk2NzgwNDgsImlhdCI6MTcyOTY3NDQ0OCwianRpIjoiMWRmMzU4NWEtYWRkYS00ZGYzLTg3ZTAtOTU1MTYwMDBkMjMxIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI1NmYxNzFmMi0wODQ4LTQzYzAtYjg1Ni02NTNiMzgwN2FjNGIiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.T261bdzLBpjoJ5cJNr-byflvw1z749eZTmSePaOmskyCgFUWp_2NUP5NM7TexQ5cWEi0eqRkbmV88EUVl2gsRo2ejxeP2S6gQWYMXP46hqmhd8zKsX2RTNA_yF_8YR7zqDe7XCLTVu_ZnQiJaC2Bo110VKTqB8rGelzixxTbAW4GGVLeiR4TPzbQHYCpbmt6rKF3olxA4pXrJP1aUTlsA9-s2sTj-HeBLeg-SOa7i4pykP-G62yiPSmrE76YGZA_KlSQbwFZ03c3FQJVr7DR7-k6VjCjRYRwmTz-ZpP2xQew_4NFSZL3KOWlsjdhleE2b1ik2lAjd72NfABh6x3M2w";

    public ResponseEntity<QualificationDto> getQualificationById(long qid, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return this.restTemplate.exchange(qualificationUrl + "/{id}"+ "/employees", HttpMethod.GET, entity, QualificationDto.class, qid);
    }

    //BearerToken vom eignem Service abfangen
    // Keycloak
//    mit Resttemplate im header authorization bearer token mit geben

}
