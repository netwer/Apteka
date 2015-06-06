package aptekaproj.services;

import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Users;
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

    public List<UsersDoctorViewModel> getAppointments(){
        List<Users> doctors = userService.getDoctors();
        List<UsersDoctorViewModel> usersDoctorViewModels = new ArrayList<>();
        for (Users doctor:doctors){
            usersDoctorViewModels.addAll(userService.getPatients(doctor.getId()));
        }

        return usersDoctorViewModels;
    }

    public void saveAppointment(UsersDoctorViewModel usersDoctorViewModel) {
        Diagnoses diagnoses = new Diagnoses();

        diagnoses.setPatient_user_id(usersDoctorViewModel.PatientId);
        diagnoses.setDoctor_user_id(usersDoctorViewModel.DoctorId);
        diagnoses.setCreated_at(usersDoctorViewModel.LastVisitDate);
        diagnoses.setDiagnosis("");
        diagnoses.setComplaints("");
        diagnoses.setRecipe_id(null);

        diagnosesService.SaveDiagnoses(diagnoses);
    }

    public void deleteAppointment(int id) {
        diagnosesService.delete(id);
    }
}
