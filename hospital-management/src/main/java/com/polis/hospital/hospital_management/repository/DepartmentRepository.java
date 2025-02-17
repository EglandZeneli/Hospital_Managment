package com.polis.hospital.hospital_management.repository;

import com.polis.hospital.hospital_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name); // Ensure no duplicate department names
}
