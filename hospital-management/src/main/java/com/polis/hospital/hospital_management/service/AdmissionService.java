package com.polis.hospital.hospital_management.service;

import com.polis.hospital.hospital_management.entity.Admission;
import com.polis.hospital.hospital_management.entity.Department;
import com.polis.hospital.hospital_management.entity.Patient;
import com.polis.hospital.hospital_management.repository.AdmissionRepository;
import com.polis.hospital.hospital_management.repository.DepartmentRepository;
import com.polis.hospital.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public AdmissionService(AdmissionRepository admissionRepository, PatientRepository patientRepository, DepartmentRepository departmentRepository) {
        this.admissionRepository = admissionRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Admit a patient to a department.
     * patientId ID of the patient to be admitted.
     * departmentId ID of the department where the patient will be admitted.
     * @return The newly created admission record.
     */
    public Admission admitPatient(Long patientId, Long departmentId) {
        // Find the patient by ID or throw an exception if not found
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        // Find the department by ID or throw an exception if not found
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));

        // Create a new admission record
        Admission admission = new Admission();
        admission.setPatient(patient);
        admission.setDepartment(department);
        admission.setAdmissionDate(LocalDateTime.now());
        admission.setStatus("Admitted");

        // Save and return the admission record
        return admissionRepository.save(admission);
    }

    /**
     * Discharge a patient with a given reason.
     * @param patientId ID of the patient to be discharged.
     * @param reason The reason for discharge.
     * @return Updated admission record with discharge details.
     */
    public Admission dischargePatient(Long patientId, String reason) {
        // Find the active admission for the patient or throw an exception if not found
        Admission admission = admissionRepository.findByPatientIdAndStatus(patientId, "Admitted")
                .orElseThrow(() -> new RuntimeException("No active admission found for patient ID: " + patientId));

        // Update admission status and discharge details
        admission.setStatus("Discharged");
        admission.setDischargeReason(reason);
        admission.setDischargeDate(LocalDateTime.now());

        // Save and return the updated admission record
        return admissionRepository.save(admission);
    }

    /**
     * Transfer a patient to a new department.
     * @param patientId ID of the patient to be transferred.
     * @param newDepartmentId ID of the new department.
     * @return Updated admission record with new department details.
     */
    public Admission transferPatient(Long patientId, Long newDepartmentId) {
        // Find the active admission for the patient or throw an exception if not found
        Admission admission = admissionRepository.findActiveAdmission(patientId)
                .orElseThrow(() -> new RuntimeException("No active admission found for patient ID: " + patientId));

        // Find the new department by ID or throw an exception if not found
        Department newDepartment = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + newDepartmentId));

        // Update admission record with new department
        admission.setDepartment(newDepartment);

        // Save and return the updated admission record
        return admissionRepository.save(admission);
    }
}
