package org.example.auditservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.auditservice.entity.AuditLog;
import org.example.auditservice.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/audit/showAll")
    public ResponseEntity<List<AuditLog>> findAllLogs() {
        List<AuditLog> l = auditService.findAllLogs();

        return ResponseEntity.ok()
                .body(l);
    }

    

}
