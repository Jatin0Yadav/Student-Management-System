package org.example.student_service.dto;


import lombok.Builder;
import lombok.Data;
import org.example.student_service.exception.DepartmentNotFoundException;

@Data
@Builder
public class StudentResponse {

    private Long id;

    private String name;

    private int age;

    private String email;

    private Long departmentId;

    private DepartmentResponse department;
}
