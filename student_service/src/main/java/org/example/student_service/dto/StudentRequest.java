package org.example.student_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message="Name can't be empty")
    private String name;

    @Min(value=10, message="Age must not be below 12")
    @Max(value=50, message="Age must not be above 26")
    private int age;

    @Email(message="Email should be valid")
    @NotBlank(message="Email can't be blank")
    private String email;

    @NotBlank(message="Password can't be blank")
    @Size(min=2, max=20, message="Password must be between 2 and 10 characters.")
    private String password;

    private Long departmentId;

}


/*
Validation for Password:
 @NotBlank(message = "Password cannot be blank")
@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
@Pattern(
    regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
    message = "Password must contain uppercase, lowercase, digit and special character"
)
private String password;
*/
