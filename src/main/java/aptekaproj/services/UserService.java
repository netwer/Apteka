package aptekaproj.services;

import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.controllers.repository.IDiagnosesRepository;
import aptekaproj.helpers.Hash;
import aptekaproj.controllers.repository.IUsersRepository;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 */
@Service
public class UserService {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IDiagnosesRepository diagnosesRepository;

    public Users userInDb(String login, String password){
        List<Users> usersList = (List<Users>) usersRepository.findAll();
        for(Users user : usersList){
            if(user.getLogin().equals(login) && user.getHash().equals(Hash.getHash(password + user.getSalt()))){
                return user;
            }
        }
        return null;
    }

    public String getUserNameById(int id){
        return usersRepository.findOne(id).getName();
    }

    public Users getUserById(int id){
        return usersRepository.findOne(id);
    }

    public List<UsersDoctorViewModel> getPatients(int userId){
        List<Diagnoses> diagnoseses = (List<Diagnoses>) diagnosesRepository.findAll();
        List<UsersDoctorViewModel> usersDoctorViewModels = new ArrayList<>();

        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctor_user_id() == userId){
                Users patient = getUserById(diagnoses.getPatient_user_id());
                UsersDoctorViewModel usersDoctorViewModel = new UsersDoctorViewModel();
                usersDoctorViewModel.DoctorId = userId;
                usersDoctorViewModel.PatientId = patient.getId();
                usersDoctorViewModel.PatientFullName = patient.getFullName();
                usersDoctorViewModel.LastVisitDate = new SimpleDateFormat("MM/dd/yyyy").format(diagnoses.getCreated_at()).toString();
                usersDoctorViewModels.add(usersDoctorViewModel);
            }
        }
        return usersDoctorViewModels;
    }
}
