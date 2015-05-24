package aptekaproj.services;

import aptekaproj.ViewModels.PatientCardViewModel;
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

    //todo add check is recipe_id is null ?!
    //todo add check is Complaints is null ?!
    //todo add check is Diagnosis is null?!
    public List<UsersDoctorViewModel> getPatients(int userId){
        List<Diagnoses> diagnoseses = (List<Diagnoses>) diagnosesRepository.findAll();
        List<UsersDoctorViewModel> usersDoctorViewModels = new ArrayList<>();

        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctor_user_id()  == userId &&
               diagnoses.getRecipe_id()       == null   &&
               diagnoses.getComplaints()      == null   &&
               diagnoses.getDiagnosis()       == null){

                Users patient = getUserById(diagnoses.getPatient_user_id());
                UsersDoctorViewModel usersDoctorViewModel = new UsersDoctorViewModel();
                usersDoctorViewModel.DoctorId = userId;
                usersDoctorViewModel.PatientId = patient.getId();
                usersDoctorViewModel.PatientFullName = patient.getFullName();
                //todo right date?
                usersDoctorViewModel.LastVisitDate = new SimpleDateFormat("MM/dd/yyyy").format(diagnoses.getCreated_at()).toString();
                usersDoctorViewModels.add(usersDoctorViewModel);
            }
        }
        return usersDoctorViewModels;
    }

    //todo add check is recipe_id is null ?!
    //todo add check is Complaints is null ?!
    //todo add check is Diagnosis is null?!
    public PatientCardViewModel getPatientCard(int patientId,int doctorId){
        PatientCardViewModel patientCardViewModel = new PatientCardViewModel();
        Users patient = new Users();
        List<Diagnoses> diagnoseses = (List<Diagnoses>) diagnosesRepository.findAll();
        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctor_user_id()  == doctorId  &&
               diagnoses.getPatient_user_id() == patientId &&
               diagnoses.getRecipe_id()       == null      &&
               diagnoses.getComplaints()      == null      &&
               diagnoses.getDiagnosis()       == null){

                patient = getUserById(patientId);
                patientCardViewModel.Complaints = diagnoses.getComplaints();
                patientCardViewModel.Diagnosis = diagnoses.getDiagnosis();
                patientCardViewModel.DoctorId = doctorId;
                patientCardViewModel.PatientId = patientId;
                patientCardViewModel.PatientAddress = patient.getAddress();
                patientCardViewModel.PatientFullName = patient.getFullName();
                patientCardViewModel.PatientPoliceNumber = patient.getMedicalPolicyNumber();

                //todo may be error
                break;
            }
        }
        return patientCardViewModel;
    }
}
