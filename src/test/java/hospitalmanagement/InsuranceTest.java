package hospitalmanagement;


import hospitalManagement.entity.Appointment;
import hospitalManagement.entity.Insurance;
import hospitalManagement.entity.Patient;
import hospitalManagement.service.AppointmentService;
import hospitalManagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testInsurance(){

        Insurance insurance = Insurance.builder()
                .policyNumber("POL123456")
                .provider("POL")
                .validUntil(LocalDate.of(2030,12,31))
                .build();

        Patient patient = insuranceService.assignInsuranceToPatient(insurance,1L);
        System.out.println(patient);

        var newPatient = insuranceService.disassociateInsuranceFromPatient(patient.getId());
        System.out.println(newPatient);
    }

    @Test
    public void testCreateAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,11,20,10,30))
                .reason("Regular Checkup")
                .build();
        Appointment savedAppointment = appointmentService.createNewAppointment(appointment,1L,2L);
        System.out.println(savedAppointment);

        var updatedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(savedAppointment.getId(), 3L);
        System.out.println(updatedAppointment);

    }
}
