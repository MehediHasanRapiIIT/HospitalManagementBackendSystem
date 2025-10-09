package com.learning.hospitalmanagement.repository;

import com.learning.hospitalmanagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}