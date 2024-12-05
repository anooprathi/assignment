package com.anoop.assignment.repository;

import com.anoop.assignment.model.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByEmailAddress(@NotBlank(message = "Email address is required") @Email(message = "Invalid email format") String emailAddress);
}

