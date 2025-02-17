package com.polis.hospital.hospital_management.controller;

import com.polis.hospital.hospital_management.entity.Admission;
import com.polis.hospital.hospital_management.service.AdmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admissions")
@CrossOrigin(origins = "*")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }
    // Endpoint to admit a patient
    @PostMapping("/admit")
    public ResponseEntity<Admission> admitPatient(@RequestParam Long patientId, @RequestParam Long departmentId) {
        Admission admission = admissionService.admitPatient(patientId, departmentId);
        return ResponseEntity.ok(admission);
    }
    // Endpoint to discharge a patient
    @PostMapping("/discharge")
    public ResponseEntity<Admission> dischargePatient(@RequestParam Long patientId, @RequestParam String reason) {
        Admission admission = admissionService.dischargePatient(patientId, reason);
        return ResponseEntity.ok(admission);
    }
    // Endpoint to transfer a patient to another department
    @PostMapping("/transfer")
    public ResponseEntity<Admission> transferPatient(@RequestParam Long patientId, @RequestParam Long newDepartmentId) {
        Admission admission = admissionService.transferPatient(patientId, newDepartmentId);
        return ResponseEntity.ok(admission);
    }
}
