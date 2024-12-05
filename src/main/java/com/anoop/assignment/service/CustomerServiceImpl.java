package com.anoop.assignment.service;

import com.anoop.assignment.model.Customer;
import com.anoop.assignment.repository.CustomerRepository;
import com.anoop.assignment.web.CustomerController;
import com.anoop.assignment.web.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    //This service returns the customer using UUID
    public Customer getCustomerById(UUID id) {
        logger.debug("Fetching customer with ID: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Customer with ID: {} not found!", id);
                    return new ResourceNotFoundException("Customer not found!");
                });
    }

    //This method takes the input customer object and create a new customer in the database.
    public Customer createCustomer(Customer customer) {
        logger.info("Creating customer with email: {}", customer.getEmailAddress());
        return customerRepository.findByEmailAddress(customer.getEmailAddress())
                .orElseGet(() -> {
                    return customerRepository.save(customer);});
    }

    //this method fetches the customer using input customer and updates with new values.
    public Customer updateCustomer(UUID id, Customer updatedCustomer) {
        logger.info("Updating customer with ID: {}", id);

        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setFirstName(updatedCustomer.getFirstName());
        existingCustomer.setMiddleName(updatedCustomer.getMiddleName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setEmailAddress(updatedCustomer.getEmailAddress());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(existingCustomer);
        logger.debug("Updated customer: {}", savedCustomer);

        return savedCustomer;

    }

    public boolean deleteCustomerById(UUID id) {
        logger.info("Deleting customer with ID: {}", id);

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            logger.debug("Customer with ID: {} deleted successfully.", id);
            customerRepository.deleteById(id);
            return true;
        }
        logger.warn("Attempt to delete non-existent customer with ID: {}", id);
        return false;
    }

}

