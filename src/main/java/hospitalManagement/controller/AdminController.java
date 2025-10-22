package hospitalManagement.controller;


import hospitalManagement.dto.DoctorResponseDto;
import hospitalManagement.dto.OnboardDoctorRequestDto;
import hospitalManagement.dto.PatientResponseDto;
import hospitalManagement.service.DoctorService;
import hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {

        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }


    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto) throws IllegalAccessException {

        // Implementation for onboarding a new doctor
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }
}
