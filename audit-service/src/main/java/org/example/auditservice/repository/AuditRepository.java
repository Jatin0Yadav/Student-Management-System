package org.example.auditservice.repository;

import org.example.auditservice.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {
}