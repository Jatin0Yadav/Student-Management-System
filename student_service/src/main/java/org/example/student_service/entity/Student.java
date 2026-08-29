package org.example.student_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String email;

    private String password;

    // Only store the ID of the department.
    // Department belongs to department-service.
    private Long departmentId;
}



// @Data generates:
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@RequiredArgsConstructor

// Jackson needs at least Getter to read values of the obj and return it as JSON, in case of Get Data operation.
// Serialization = converting an object into a format that can be sent/stored.