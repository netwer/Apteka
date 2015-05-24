package aptekaproj.services;

import aptekaproj.ViewModels.PatientCardViewModel;
import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.controllers.repository.IDiagnosesRepository;
import aptekaproj.controllers.repository.IRolesRepository;
import aptekaproj.helpers.Hash;
import aptekaproj.controllers.repository.IUsersRepository;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Roles;
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

    @Autowired
    private RoleService roleService;

    public UserViewModel getUser(String login, String password){
        Users user = userInDb(login,password);
        UserViewModel responseViewModel = new UserViewModel();
        if(user != null ){
            responseViewModel.Url = "/" + roleService.getRoleName(user.getRoleId()) + "/";
            responseViewModel.UserFullName = user.getFullName();
            responseViewModel.UserId = user.getId();
            responseViewModel.UserLogin = user.getLogin();
            responseViewModel.UserRole = roleService.getRoleName(user.getRoleId());
            responseViewModel.UserRoleId = user.getRoleId();
            responseViewModel.ErrorMessage = "";
            //return"redirect:/" + roleService.getRoleName(user.getRoleId()) + "/Success?message=Welcome," + userName;
            return responseViewModel;
        }
        responseViewModel.ErrorMessage = "login or password incorrect";
        responseViewModel.Url = "/Login/sigin";
        return responseViewModel;
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
                    (diagnoses.getComplaints() == null || diagnoses.getComplaints().isEmpty()) &&
                    (diagnoses.getDiagnosis() == null || diagnoses.getDiagnosis().isEmpty())){

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
               (diagnoses.getComplaints() == null || diagnoses.getComplaints().isEmpty()) &&
               (diagnoses.getDiagnosis() == null || diagnoses.getDiagnosis().isEmpty())){

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

    private Users userInDb(String login, String password){
        List<Users> usersList = (List<Users>) usersRepository.findAll();
        for(Users user : usersList){
            if(user.getLogin().equals(login) && user.getHash().equals(Hash.getHash(password + user.getSalt()))){
                return user;
            }
        }
        return null;
    }

    private Users getUserById(int id){
        return usersRepository.findOne(id);
    }
}
