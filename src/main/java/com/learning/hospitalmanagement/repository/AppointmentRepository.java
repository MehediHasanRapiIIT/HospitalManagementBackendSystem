package com.learning.hospitalmanagement.repository;

import com.learning.hospitalmanagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}