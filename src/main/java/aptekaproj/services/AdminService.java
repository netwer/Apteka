package aptekaproj.services;

import aptekaproj.viewModels.PostViewModel;
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
public class AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private DiagnosesService diagnosesService;

    public List<UserDoctorViewModel> getAppointments(){
        List<User> doctors = userService.getDoctors();
        List<UserDoctorViewModel> userDoctorViewModels = new ArrayList<>();
        for (User doctor:doctors){
            userDoctorViewModels.addAll(userService.getPatients(doctor.getId()));
        }

        return userDoctorViewModels;
    }

    public PostViewModel saveAppointment(UserDoctorViewModel userDoctorViewModel) {
        Diagnoses diagnoses = new Diagnoses();

        diagnoses.setPatientUserId(userDoctorViewModel.patientId);
        diagnoses.setDoctorUserId(userDoctorViewModel.doctorId);
        diagnoses.setCreatedAt(userDoctorViewModel.lastVisitDate);
        diagnoses.setDiagnosis("");
        diagnoses.setComplaints("");
        diagnoses.setRecipeId(null);

        return diagnosesService.saveDiagnosis(diagnoses);
    }

    public void deleteAppointment(int id) {
        diagnosesService.deleteDiagnosis(id);
    }
}
