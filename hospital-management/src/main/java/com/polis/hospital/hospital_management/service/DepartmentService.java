package com.polis.hospital.hospital_management.service;

import com.polis.hospital.hospital_management.entity.Department;
import com.polis.hospital.hospital_management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Create a new department
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Department already exists!");
        }
        return departmentRepository.save(department);
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get department by ID
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    // Update department
    public Department updateDepartment(Long id, Department departmentDetails) {
        // Find the existing department or throw an error if not found
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        // Update department details
        department.setName(departmentDetails.getName());
        department.setLocation(departmentDetails.getLocation());
        // Save and return the updated department
        return departmentRepository.save(department);
    }

    // Delete department (only if it has no patients)
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
// Prevent deletion if the department has assigned patients
        if (!department.getPatients().isEmpty()) {
            throw new RuntimeException("Cannot delete department with assigned patients");
        }
        // Proceed with deletion
        departmentRepository.delete(department);
    }
}
