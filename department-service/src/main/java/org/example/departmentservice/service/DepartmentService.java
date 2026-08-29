package org.example.departmentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.departmentservice.entity.Department;
import org.example.departmentservice.dto.DepartmentRequest;
import org.example.departmentservice.dto.DepartmentResponse;
import org.example.departmentservice.exception.DepartmentNotFoundException;
import org.example.departmentservice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse insertDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .name(departmentRequest.getName())
                .build();

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentResponse.builder()
                .id(savedDepartment.getId())
                .name(savedDepartment.getName())
                .build();
    }

    public DepartmentResponse findById(Long id) {
        return departmentRepository.findById(id)
                .map(department -> DepartmentResponse.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .build())
                .orElseThrow(() -> new DepartmentNotFoundException("Department Not Found"));
    }

    public List<DepartmentResponse> showAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(i -> DepartmentResponse.builder()
                        .id(i.getId())
                        .name(i.getName())
                        .build())
                .toList();
    }

    public DepartmentResponse updateById(Long id, DepartmentRequest departmentRequest) {
        Department savedDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department Not Found"));

        savedDepartment.setName(departmentRequest.getName());

        departmentRepository.save(savedDepartment);

        return DepartmentResponse.builder()
                .id(savedDepartment.getId())
                .name(savedDepartment.getName())
                .build();

    }

    public DepartmentResponse deleteById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department Not Found"));

        departmentRepository.deleteById(id);

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
