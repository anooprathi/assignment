package com.anoop.assignment.web;

import com.anoop.assignment.service.CustomerServiceImpl;
import com.anoop.assignment.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;  // To perform HTTP requests and assertions

    @Mock
    private CustomerServiceImpl customerServiceImpl;  // Mock the CustomerService

    @InjectMocks
    private CustomerController customerController;

    private Customer mockCustomer;


    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();  // Set up MockMvc

        mockCustomer = Customer.builder()
                .firstName("Alpha")
                .lastName("Beta")
                .emailAddress("alpha.beta@example.com")
                .phoneNumber("+1234567890")
                .build();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        String jsonPayload = "{\"firstName\": \"Alpha\", \"lastName\": \"Beta\", \"emailAddress\": \"alpha.Beta@example.com\", \"phoneNumber\": \"+1234567890\"}";
        when(customerServiceImpl.createCustomer(any()))
                .thenReturn(mockCustomer);
        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Alpha"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Beta"));
    }

    @Test
    public void testCreateCustomer_Exception() throws Exception {
        String jsonPayload = "{\"firstName\": \"Alpha\", \"lastName\": \"Beta\", \"emailAddress\": \"alpha@Beta@example.com\", \"phoneNumber\": \"+1234567890\"}";

        mockMvc.perform(post("/api/customers")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    public void testGetCustomerById() throws Exception {
        when(customerServiceImpl.getCustomerById(any(UUID.class))).thenReturn(mockCustomer);
        mockMvc.perform(get("/api/customers/{id}", "d98229b2-bd25-48d4-829b-3d9ed0fcf26c"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Alpha"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Beta"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // Mock behavior for delete operation
        when(customerServiceImpl.deleteCustomerById(any())).thenReturn(true);

        mockMvc.perform(delete("/api/customers/{id}", "d98229b2-bd25-48d4-829b-3d9ed0fcf26c"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());  // Expecting 204 No Content
    }

    @Test
    public void testDeleteCustomer_Exception() throws Exception {
        // Mock behavior for delete operation
        when(customerServiceImpl.deleteCustomerById(any())).thenReturn(false);

        mockMvc.perform(delete("/api/customers/{id}", "d98229b2-bd25-48d4-829b-3d9ed0fcf26c"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());  // Expecting 204 No Content
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        mockCustomer.setFirstName("Delta");
        when(customerServiceImpl.updateCustomer(any(UUID.class), any(Customer.class)))
                .thenReturn(mockCustomer);

        String jsonPayload = "{\"firstName\": \"Delta\", \"lastName\": \"Beta\", \"emailAddress\": \"delta.beta@example.com\", \"phoneNumber\": \"+1234567890\"}";
        mockMvc.perform(put("/api/customers/{id}", "d98229b2-bd25-48d4-829b-3d9ed0fcf26c")
                        .contentType("application/json")
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Delta"));
    }
}
