package com.learning.hospitalmanagement.repository;

import com.learning.hospitalmanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}