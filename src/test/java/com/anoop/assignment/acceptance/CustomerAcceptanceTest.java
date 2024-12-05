package com.anoop.assignment.acceptance;

import com.anoop.assignment.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.yaml")
@ActiveProfiles("test") // Explicitly load test configuration
public class CustomerAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // Test the POST /api/customers (Create Customer)
    @Test
    public void testCreateCustomer() {
        Customer customer = Customer.builder()
                .firstName("Create")
                .lastName("Test")
                .emailAddress("create.testa@example.com")
                .phoneNumber("+1234567890")
                .build();
        ResponseEntity<Customer> response = restTemplate.postForEntity("/api/customers", customer, Customer.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmailAddress()).isEqualTo("create.testa@example.com");
    }

    // Test the GET /api/customers/{id} (Get Customer by ID)
    @Test
    public void testGetCustomerById() {
        Customer customer = createSampleCustomer("getcustomer@example.com");
        ResponseEntity<Customer> response = restTemplate.getForEntity("/api/customers/{id}", Customer.class, customer.getId());

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmailAddress()).isEqualTo("getcustomer@example.com");
    }

    // Test the PUT /api/customers/{id} (Update Customer)
    @Test
    public void testUpdateCustomer() {
        Customer customer = createSampleCustomer("updatecustomer@example.com");
        customer.setFirstName("UpdatedName");

        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer);
        ResponseEntity<Customer> response = restTemplate.exchange("/api/customers/{id}", HttpMethod.PUT, requestEntity, Customer.class, customer.getId());

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("UpdatedName");
    }

    // Test the DELETE /api/customers/{id} (Delete Customer)
    @Test
    public void testDeleteCustomerById() {
        Customer customer = createSampleCustomer("deletecustomer@example.com");

        ResponseEntity<Void> response = restTemplate.exchange("/api/customers/{id}", HttpMethod.DELETE, null, Void.class, customer.getId());

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);

        // Verify the customer is deleted
        ResponseEntity<Customer> getResponse = restTemplate.getForEntity("/api/customers/{id}", Customer.class, customer.getId());
        assertThat(getResponse.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    // Utility method to create a sample customer for testing
    private Customer createSampleCustomer(String email) {
        Customer customer = Customer.builder()
                .firstName("Alpha")
                .lastName("Beta")
                .emailAddress(email)
                .phoneNumber("+1234567890")
                .build();
        ResponseEntity<Customer> response = restTemplate.postForEntity("/api/customers", customer, Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        return response.getBody();
    }
}
