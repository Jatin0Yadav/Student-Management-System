package org.example.departmentservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.departmentservice.dto.DepartmentRequest;
import org.example.departmentservice.dto.DepartmentResponse;
import org.example.departmentservice.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/department/insert")
    public ResponseEntity<DepartmentResponse> insertDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.insertDepartment(departmentRequest));
    }

    @GetMapping("/department/show/{id}")
    public ResponseEntity<DepartmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @GetMapping("/department/showAll")
    public ResponseEntity<List<DepartmentResponse>> showAllDepartments() {
        return ResponseEntity.ok().body(departmentService.showAllDepartments());
    }

    @DeleteMapping("/department/delete/{id}")
    public ResponseEntity<DepartmentResponse> deleteById(@PathVariable Long id) {
        DepartmentResponse departmentResponse = departmentService.deleteById(id);

        if(departmentResponse == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(departmentResponse);
    }

    @PutMapping("/department/update/{id}")
    public ResponseEntity<DepartmentResponse> updateById(@PathVariable Long id,
                                                         @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok()
                .body(departmentService.updateById(id, departmentRequest));

    }

}
