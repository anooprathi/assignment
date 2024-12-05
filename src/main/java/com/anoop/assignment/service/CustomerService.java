package com.anoop.assignment.service;

import com.anoop.assignment.model.Customer;
import java.util.UUID;

public interface CustomerService {


    public Customer getCustomerById(UUID id) ;

    public Customer createCustomer(Customer customer);

    public Customer updateCustomer(UUID id, Customer updatedCustomer);

    public boolean deleteCustomerById(UUID id);



}
