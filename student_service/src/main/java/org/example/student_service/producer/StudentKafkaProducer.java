package org.example.student_service.producer;

import lombok.RequiredArgsConstructor;
import org.example.student_service.dto.StudentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentKafkaProducer {

    private final KafkaTemplate<String, StudentCreatedEvent> kafkaTemplate;

    public void sendStudentCreatedEvent(StudentCreatedEvent event) {

        kafkaTemplate
                .send(
                        "student-created",
                        event.getId().toString(),
                        event
                )
                .whenComplete((result, ex) -> {

                    if (ex == null) {
                        System.out.println(
                                "Student event sent successfully: " + event
                        );
                    } else {
                        System.err.println(
                                "Kafka error while sending student event: "
                                        + ex.getMessage()
                        );

                        ex.printStackTrace();
                    }
                });
    }
}
