package com.learning.hospitalmanagement.repository;

import com.learning.hospitalmanagement.entity.Patient;
import com.learning.hospitalmanagement.entity.type.BloodGroupType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.bloodGroup = ?1")
    List<Patient> findBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    @Query("SELECT p FROM Patient p WHERE p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("SELECT p.bloodGroup, COUNT(p) FROM Patient p GROUP BY p.bloodGroup")
    List<Object[]> countBloodGroup();

    @Query(value = "SELECT * FROM patient", nativeQuery = true)
    //List<Patient> findAllPatients();
    Page<Patient> findAllPatients(Pageable pageable);
}
