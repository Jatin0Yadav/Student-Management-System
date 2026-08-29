package org.example.auditservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.auditservice.dto.StudentCreatedEvent;
import org.example.auditservice.entity.AuditLog;
import org.example.auditservice.repository.AuditRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentKafkaConsumer {

    private final AuditRepository auditLogRepository;

    @KafkaListener(
            topics = "student-created",
            groupId = "audit-service"
    )
    public void consumeStudentCreatedEvent(StudentCreatedEvent event) {

        AuditLog auditLog = AuditLog.builder()
                .eventType("STUDENT_CREATED")
                .serviceName("student-service")
                .message(
                        "New student created - Name: "
                                + event.getName()
                                + ", Email: "
                                + event.getEmail()
                )
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);

        log.info(
                "Audit log saved for student: {}",
                event.getName()
        );
    }
}