package com.learning.hospitalmanagement;

import com.learning.hospitalmanagement.entity.Patient;
import com.learning.hospitalmanagement.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTest {
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void findAllPatients() {
       List<Patient> patientList = patientRepository.findAll();
        System.out.println(patientList);
    }
}
