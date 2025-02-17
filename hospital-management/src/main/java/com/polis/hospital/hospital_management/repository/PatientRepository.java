package com.polis.hospital.hospital_management.repository;

import com.polis.hospital.hospital_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Custom query methods (if needed) can be added here
}
