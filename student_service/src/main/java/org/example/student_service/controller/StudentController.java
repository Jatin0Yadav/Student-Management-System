package org.example.student_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.student_service.dto.StudentRequest;
import org.example.student_service.dto.StudentResponse;
import org.example.student_service.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService firstService;

    // Insert Data:
    // for this Jackson needs NoArgsConstructor in Entity for building a student object.
    // here RequestBody will create a new student obj from the provided raw data,
    // so u don't have to create a new student obj using builder.
    @PostMapping("/student/insert")
    public ResponseEntity<StudentResponse> insert(@Valid @RequestBody StudentRequest st) {
        return ResponseEntity.ok(firstService.insert(st));
    }

    // Get Data:
    @GetMapping("/student/showAll")
    public ResponseEntity<List<StudentResponse>> showAllStudents() {
        return ResponseEntity.ok().body(firstService.showAllStudents());
    }


    @GetMapping("/student/show/{id}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok().body(firstService.findById(id));

    }


    // Delete Data:
    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<StudentResponse> deleteById(@PathVariable long id) {

        StudentResponse student = firstService.deleteById(id);

        if (student == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(student);
    }

    @PutMapping("student/update/{id}")
    public ResponseEntity<StudentResponse> updateById(@PathVariable long id,
                                              @RequestBody StudentRequest studentRequest)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(firstService.updateById(id, studentRequest));
    }
}