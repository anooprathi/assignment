package com.anoop.assignment.web;

import com.anoop.assignment.model.Customer;
import com.anoop.assignment.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
//The endpoint could be secured using spring security and JWT token.
//Swagger could be used for API documentation. This will give a testing place as well as clients could check this for most up-to-date API information.
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerServiceImpl;


    //This API returns the Customer by using customerId
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable UUID id) {
        logger.info("Received request to get customer with ID: {}", id);
        Customer customer = customerServiceImpl.getCustomerById(id);
        logger.info("Retrieved customer: {}", customer);
        return ResponseEntity.ok(customer);

    }

    //This API created and returns the created Customer.
    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
        logger.info("Received request to create customer with emailAddress : {}", customer.getEmailAddress());
        Customer createdCustomer = customerServiceImpl.createCustomer(customer);
        logger.info("Customer created successfully with ID: {}", createdCustomer.getId());
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    //This API updates and returns the updated Customer.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @Valid @RequestBody Customer updatedCustomer) {
        logger.info("Received request to update customer with ID: {}", id);
        Customer customer = customerServiceImpl.updateCustomer(id, updatedCustomer);
        logger.info("Customer updated successfully with ID: {}", id);

        return ResponseEntity.ok(customer);
    }

    //This API deleted the customer records from the system.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        logger.info("Received request to delete customer with ID: {}", id);
        boolean deleted = customerServiceImpl.deleteCustomerById(id);
        if(deleted){
            logger.info("Customer deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        }else {
            logger.warn("Attempt to delete non-existing customer with ID: {}", id);
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }


}