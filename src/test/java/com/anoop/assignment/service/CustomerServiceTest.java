package com.anoop.assignment.service;

import com.anoop.assignment.model.Customer;
import com.anoop.assignment.repository.CustomerRepository;
import com.anoop.assignment.web.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Customer mockCustomer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = UUID.randomUUID();
        mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setFirstName("John");
        mockCustomer.setMiddleName("M");
        mockCustomer.setLastName("Doe");
        mockCustomer.setEmailAddress("john.doe@example.com");
        mockCustomer.setPhoneNumber("+1234567890");
    }

    @Test
    void testGetCustomerById_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        Customer customer = customerServiceImpl.getCustomerById(customerId);

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.getCustomerById(customerId));
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testCreateCustomer_NewCustomer() {
        when(customerRepository.findByEmailAddress(mockCustomer.getEmailAddress())).thenReturn(Optional.empty());
        when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);

        Customer createdCustomer = customerServiceImpl.createCustomer(mockCustomer);

        assertNotNull(createdCustomer);
        assertEquals("John", createdCustomer.getFirstName());
        verify(customerRepository, times(1)).findByEmailAddress(mockCustomer.getEmailAddress());
        verify(customerRepository, times(1)).save(mockCustomer);
    }

    @Test
    void testCreateCustomer_ExistingCustomer() {
        when(customerRepository.findByEmailAddress(mockCustomer.getEmailAddress())).thenReturn(Optional.of(mockCustomer));

        Customer existingCustomer = customerServiceImpl.createCustomer(mockCustomer);

        assertNotNull(existingCustomer);
        assertEquals("John", existingCustomer.getFirstName());
        verify(customerRepository, times(1)).findByEmailAddress(mockCustomer.getEmailAddress());
        verify(customerRepository, never()).save(mockCustomer);
    }

    @Test
    void testUpdateCustomer_Success() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setMiddleName("M");
        updatedCustomer.setLastName("Smith");
        updatedCustomer.setEmailAddress("jane.smith@example.com");
        updatedCustomer.setPhoneNumber("+9876543210");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerServiceImpl.updateCustomer(customerId, updatedCustomer);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(mockCustomer);
    }

    @Test
    void testUpdateCustomer_NotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Customer updatedCustomer = new Customer();
        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.updateCustomer(customerId, updatedCustomer));

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomerById_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        doNothing().when(customerRepository).deleteById(customerId);

        boolean result = customerServiceImpl.deleteCustomerById(customerId);

        assertTrue(result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomerById_NotFound() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        boolean result = customerServiceImpl.deleteCustomerById(customerId);

        assertFalse(result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).deleteById(customerId);
    }
}
