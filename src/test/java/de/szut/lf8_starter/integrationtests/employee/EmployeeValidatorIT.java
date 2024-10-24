//package de.szut.lf8_starter.integrationtests.employee;
//
//import de.szut.lf8_starter.employee.EmployeeValidator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.web.client.RestTemplate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//
//
//public class EmployeeValidatorIT {
//    @Autowired
//    private EmployeeValidator employeeValidator;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private MockRestServiceServer mockServer;
//
//    private final String baseUrl = "https://employee.szut.dev/employees";
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize the mock server with the RestTemplate
//        mockServer = MockRestServiceServer.createServer(restTemplate);
//    }
//
//    @Test
//    public void testDoesEmployeeExist_ShouldReturnTrue_WhenEmployeeExists() {
//        long employeeId = 123;
//        String authorizationHeader = "Bearer valid_token";
//
//        // Mock the REST call
//        mockServer.expect(requestTo(baseUrl + "/" + employeeId))
//                .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer valid_token"))
//                .andRespond(withStatus(HttpStatus.OK).body("Employee exists"));
//
//        // Call the method
//        boolean employeeExists = employeeValidator.doesEmployeeExist(employeeId, authorizationHeader);
//
//        // Assert the result
//        assertThat(employeeExists).isTrue();
//
//        // Verify all expectations were met
//        mockServer.verify();
//    }
//
//}
