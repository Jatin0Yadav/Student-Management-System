package org.example.auditservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreatedEvent {

    private Long id;
    private String name;
    private String email;
    private Long departmentId;
}