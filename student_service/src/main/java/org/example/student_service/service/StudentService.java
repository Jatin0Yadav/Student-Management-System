package org.example.student_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.example.student_service.dto.DepartmentResponse;
import org.example.student_service.dto.StudentCreatedEvent;
import org.example.student_service.dto.StudentRequest;
import org.example.student_service.dto.StudentResponse;
import org.example.student_service.entity.Student;
import org.example.student_service.exception.StudentNotFoundException;
import org.example.student_service.producer.StudentKafkaProducer;
import org.example.student_service.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository userRepository;
    private final WebClient.Builder webClientBuilder;
    private final StudentKafkaProducer studentKafkaProducer;


    // ================= INSERT STUDENT =================

    @RateLimiter(
            name = "department-service",
            fallbackMethod = "insertFallback"
    )
//    @Retry(
//            name = "department-service",
//            fallbackMethod = "insertFallback"
//    )
    @CircuitBreaker(
            name = "department-service",
            fallbackMethod = "insertFallback"
    )
    public StudentResponse insert(StudentRequest request) {

        DepartmentResponse department = getDepartment(
                request.getDepartmentId()
        );

        Student student = Student.builder()
                .name(request.getName())
                .age(request.getAge())
                .email(request.getEmail())
                .password(request.getPassword())
                .departmentId(request.getDepartmentId())
                .build();

        Student savedStudent = userRepository.save(student);

        StudentCreatedEvent event = StudentCreatedEvent.builder()
                .id(savedStudent.getId())
                .name(savedStudent.getName())
                .email(savedStudent.getEmail())
                .departmentId(savedStudent.getDepartmentId())
                .build();

        studentKafkaProducer.sendStudentCreatedEvent(event);


        return StudentResponse.builder()
                .id(savedStudent.getId())
                .name(savedStudent.getName())
                .age(savedStudent.getAge())
                .email(savedStudent.getEmail())
                .departmentId(savedStudent.getDepartmentId())
                .department(department)
                .build();
    }


    // ================= GET STUDENT BY ID =================

    public StudentResponse findById(Long id) {

        Student student = userRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student Not Found"));

        DepartmentResponse department = getDepartment(
                student.getDepartmentId()
        );

        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .email(student.getEmail())
                .departmentId(student.getDepartmentId())
                .department(department)
                .build();
    }


    // ================= GET ALL STUDENTS =================

    public List<StudentResponse> showAllStudents() {

        return userRepository.findAll()
                .stream()
                .map(student -> {

                    DepartmentResponse department =
                            getDepartment(student.getDepartmentId());

                    return StudentResponse.builder()
                            .id(student.getId())
                            .name(student.getName())
                            .age(student.getAge())
                            .email(student.getEmail())
                            .departmentId(student.getDepartmentId())
                            .department(department)
                            .build();
                })
                .toList();
    }


    // ================= DELETE STUDENT =================

    public StudentResponse deleteById(long id) {

        Student student = userRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student Not Found"));

        DepartmentResponse department = getDepartment(
                student.getDepartmentId()
        );

        userRepository.delete(student);

        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .email(student.getEmail())
                .departmentId(student.getDepartmentId())
                .department(department)
                .build();
    }


    // ================= UPDATE STUDENT =================

    public StudentResponse updateById(
            Long id,
            StudentRequest studentRequest) {

        Student student = userRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student Not Found"));

        // Validate the department
        DepartmentResponse department = getDepartment(
                studentRequest.getDepartmentId()
        );

        student.setName(studentRequest.getName());
        student.setAge(studentRequest.getAge());
        student.setEmail(studentRequest.getEmail());
        student.setDepartmentId(studentRequest.getDepartmentId());

        Student updatedStudent = userRepository.save(student);

        return StudentResponse.builder()
                .id(updatedStudent.getId())
                .name(updatedStudent.getName())
                .age(updatedStudent.getAge())
                .email(updatedStudent.getEmail())
                .departmentId(updatedStudent.getDepartmentId())
                .department(department)
                .build();
    }


    // ================= DEPARTMENT SERVICE CALL =================

    private DepartmentResponse getDepartment(Long departmentId) {

        return webClientBuilder.build()
                .get()
                .uri(
                        "http://department-service/department/show/{id}",
                        departmentId
                )
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();
    }


    // ================= FALLBACK =================

    public StudentResponse insertFallback(
            StudentRequest request,
            Throwable throwable) {

        System.out.println("ACTUAL ERROR: " + throwable.getMessage());
        throwable.printStackTrace();

        throw new RuntimeException(
                "Student insertion failed",
                throwable
        );
    }
}