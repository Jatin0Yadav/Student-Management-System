package org.example.departmentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank(message = "Department name can't be empty")
    @Size(min = 2, max = 50, message = "Department length should be between 2 and 50 characters")
    private String name;

}
