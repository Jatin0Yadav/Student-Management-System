package org.example.notificationservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.dto.StudentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentKafkaConsumer {

    @KafkaListener(
            topics = "student-created",
            groupId = "notification-service"
    )
    public void consume(StudentCreatedEvent event) {

        log.info(
                "Notification: New student created - Name: {}, Email: {}",
                event.getName(),
                event.getEmail()
        );
    }
}