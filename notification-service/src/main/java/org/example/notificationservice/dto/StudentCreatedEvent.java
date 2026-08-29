package org.example.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreatedEvent {
    private Long id;
    private String name;
    private String email;
    private Long departmentId;
}
