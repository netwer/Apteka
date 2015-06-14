package aptekaproj.services;

import aptekaproj.viewModels.UserDoctorViewModel;
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

    public List<UserDoctorViewModel> GetAppointments(){
        List<User> doctors = userService.getDoctors();
        List<UserDoctorViewModel> userDoctorViewModels = new ArrayList<>();
        for (User doctor:doctors){
            userDoctorViewModels.addAll(userService.getPatients(doctor.getId()));
        }

        return userDoctorViewModels;
    }

    public void SaveAppointment(UserDoctorViewModel userDoctorViewModel) {
        Diagnoses diagnoses = new Diagnoses();

        diagnoses.setPatientUserId(userDoctorViewModel.PatientId);
        diagnoses.setDoctorUserId(userDoctorViewModel.DoctorId);
        diagnoses.setCreatedAt(userDoctorViewModel.LastVisitDate);
        diagnoses.setDiagnosis("");
        diagnoses.setComplaints("");
        diagnoses.setRecipeId(null);

        diagnosesService.SaveDiagnosis(diagnoses);
    }

    public void DeleteAppointment(int id) {
        diagnosesService.DeleteDiagnosis(id);
    }
}
