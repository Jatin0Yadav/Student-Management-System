package org.example.auditservice.service;

import lombok.RequiredArgsConstructor;
import org.example.auditservice.entity.AuditLog;
import org.example.auditservice.repository.AuditRepository;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    public List<AuditLog> findAllLogs() {
        return auditRepository.findAll();
    }
}
