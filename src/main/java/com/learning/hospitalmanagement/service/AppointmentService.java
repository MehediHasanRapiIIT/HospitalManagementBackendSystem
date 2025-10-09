package com.learning.hospitalmanagement.service;

import com.learning.hospitalmanagement.entity.Appointment;
import com.learning.hospitalmanagement.entity.Doctor;
import com.learning.hospitalmanagement.entity.Patient;
import com.learning.hospitalmanagement.repository.AppointmentRepository;
import com.learning.hospitalmanagement.repository.DoctorRepository;
import com.learning.hospitalmanagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long patientId) {
        // Implementation for creating an appointment
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));

        if(appointment.getId() != null) throw new IllegalArgumentException("Appointment already exists");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        return appointmentRepository.save(appointment);


    }
    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long newDoctorId) {
        // Implementation for re-assigning an appointment to another doctor
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        Doctor newDoctor = doctorRepository.findById(newDoctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + newDoctorId));
        appointment.setDoctor(newDoctor);
        newDoctor.getAppointments().add(appointment);

        return appointment;
    }
}
