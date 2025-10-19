package hospitalmanagement;


import hospitalManagement.entity.Patient;
import hospitalManagement.repository.PatientRepository;
import hospitalManagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientTest {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void findAllPatients() {
       List<Patient> patientList = patientRepository.findAllPatientWithAppointment();
        System.out.println(patientList);
    }
    @Test
    public void testTransactionMethods(){
     //   List<Patient> patientList = patientRepository.findBloodGroup(BloodGroupType.A_POSITIVE);
     //   List<Patient> patientList = patientRepository.findByBornAfterDate(LocalDate.of(1990,1,1));
        Page<Patient> patientList = patientRepository.findAllPatients(PageRequest.of(0, 2));
        for(Patient patient: patientList){
            System.out.println(patient);
        }

//        List<Object[]> bloodGroupCount = patientRepository.countBloodGroup();
//        for(Object[] count : bloodGroupCount){
//            System.out.println(count[0] + " : " + count[1]);
//        }
    }
}
