package com.anoop.assignment.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String emailAddress;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9. ()-]{7,20}$",
            message = "Invalid phone number format"
    )
    private String phoneNumber;



}

