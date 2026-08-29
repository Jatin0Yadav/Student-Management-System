package org.example.departmentservice.repository;

import org.example.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

// for db containing departments' names mapped with IDs.
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
