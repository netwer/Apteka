package aptekaproj.services;

import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 06.06.2015.
 */
@Service
public class ApiService {

    @Autowired
    private UserService userService;

    @Autowired
    private DiagnosesService diagnosesService;

    public List<UsersDoctorViewModel> GetAppointments(){
        List<User> doctors = userService.getDoctors();
        List<UsersDoctorViewModel> usersDoctorViewModels = new ArrayList<>();
        for (User doctor:doctors){
            usersDoctorViewModels.addAll(userService.getPatients(doctor.getId()));
        }

        return usersDoctorViewModels;
    }

    public void SaveAppointment(UsersDoctorViewModel usersDoctorViewModel) {
        Diagnoses diagnoses = new Diagnoses();

        diagnoses.setPatientUserId(usersDoctorViewModel.PatientId);
        diagnoses.setDoctorUserId(usersDoctorViewModel.DoctorId);
        diagnoses.setCreatedAt(usersDoctorViewModel.LastVisitDate);
        diagnoses.setDiagnosis("");
        diagnoses.setComplaints("");
        diagnoses.setRecipeId(null);

        diagnosesService.SaveDiagnosis(diagnoses);
    }

    public void DeleteAppointment(int id) {
        diagnosesService.DeleteDiagnosis(id);
    }
}
