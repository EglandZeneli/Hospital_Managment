package com.polis.hospital.hospital_management.repository;
import org.springframework.data.jpa.repository.Query;
import com.polis.hospital.hospital_management.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Optional<Admission> findByPatientIdAndStatus(Long patientId, String status);
    List<Admission> findByPatientId(Long patientId);

    @Query("SELECT a FROM Admission a WHERE a.patient.id = :patientId AND a.dischargeDate IS NULL")
    Optional<Admission> findActiveAdmission(@Param("patientId") Long patientId);

}

