package aptekaproj.services;

import aptekaproj.viewModels.PatientCardViewModel;
import aptekaproj.viewModels.UserViewModel;
import aptekaproj.viewModels.UserDoctorViewModel;
import aptekaproj.helpers.enums.RolesNameEnum;
import aptekaproj.helpers.Hash;
import aptekaproj.controllers.repository.IUsersRepository;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Role;
import aptekaproj.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 */
@Service
public class UserService {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private DiagnosesService diagnosesService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    @Autowired
    private DrugService drugService;
    private List<User> doctors;

    public UserViewModel getUser(String login, String password){
        User user = userInDb(login,password);
        UserViewModel responseViewModel = new UserViewModel();
        if(user != null ){
            responseViewModel.url = "/" + roleService.getRoleName(user.getRoleId()) + "/";
            responseViewModel.userFullName = user.getFullName();
            responseViewModel.userId = user.getId();
            responseViewModel.userLogin = user.getLogin();
            responseViewModel.userRole = roleService.getRoleName(user.getRoleId());
            responseViewModel.userRoleId = user.getRoleId();
            responseViewModel.errorMessage = "";
            //return"redirect:/" + roleService.getRoleName(user.getRoleId()) + "/Success?message=Welcome," + userName;
            return responseViewModel;
        }
        responseViewModel.errorMessage = "login or password incorrect";
        responseViewModel.url = "/Login/login";
        return responseViewModel;
    }

    //todo add check is recipe_id is null ?!
    //todo add check is complaints is null ?!
    //todo add check is diagnosis is null?!
    public List<UserDoctorViewModel> getPatients(int userId){
        List<Diagnoses> diagnoseses = diagnosesService.getAllDiagnoses();
        List<UserDoctorViewModel> userDoctorViewModels = new ArrayList<>();

        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctorUserId()  == userId &&
               diagnoses.getRecipeId()       == null   &&
                    (diagnoses.getComplaints() == null || diagnoses.getComplaints().isEmpty()) &&
                    (diagnoses.getDiagnosis() == null || diagnoses.getDiagnosis().isEmpty())){

                User patient = getUserById(diagnoses.getPatientUserId());
                User doctor = getUserById(userId);
                UserDoctorViewModel userDoctorViewModel = new UserDoctorViewModel();
                userDoctorViewModel.doctorId = userId;
                userDoctorViewModel.patientId = patient.getId();
                userDoctorViewModel.patientFullName = patient.getFullName();
                userDoctorViewModel.doctorFullName = doctor.getFullName();
                //todo right date?
                //usersDoctorViewModel.lastVisitDate = new SimpleDateFormat("MM/dd/yyyy").format(diagnoses.getCreatedAt()).toString();
                //userDoctorViewModel.lastVisitDate = diagnoses.getCreatedAt().toString();
                userDoctorViewModel.diagnosisId = diagnoses.getId();
                userDoctorViewModel.patientPoliceNumber = patient.getMedicalPolicyNumber();
                userDoctorViewModels.add(userDoctorViewModel);
            }
        }
        return userDoctorViewModels;
    }

    public PatientCardViewModel getPatientCard(int patientId,int doctorId){
        PatientCardViewModel patientCardViewModel = new PatientCardViewModel();
        User patient = getUserById(patientId);

        if(patient == null)
            return patientCardViewModel;

        patientCardViewModel.patientFullName = patient.getFullName();
        patientCardViewModel.patientAddress = patient.getAddress();
        patientCardViewModel.patientEmail = patient.getEmail();
        patientCardViewModel.patientPoliceNumber = patient.getMedicalPolicyNumber();
        patientCardViewModel.patientId = patient.getId();
        patientCardViewModel.doctorId = doctorId;
        patientCardViewModel.visitDate = new Date().toString();

        return patientCardViewModel;
        /*List<Diagnoses> diagnoseses = diagnosesService.getAllDiagnoses();
        //todo add check is recipe_id is null ?!
        //todo add check is complaints is null ?!
        //todo add check is diagnosis is null?!
        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctorUserId()  == doctorId  &&
               diagnoses.getPatientUserId() == patientId &&
               diagnoses.getRecipeId()       == null      &&
               (diagnoses.getComplaints() == null || diagnoses.getComplaints().isEmpty()) &&
               (diagnoses.getDiagnosis() == null || diagnoses.getDiagnosis().isEmpty())){

                patient = getUserById(patientId);
                patientCardViewModel.complaints = diagnoses.getComplaints();
                patientCardViewModel.diagnosis = diagnoses.getDiagnosis();
                patientCardViewModel.doctorId = doctorId;
                patientCardViewModel.patientId = patientId;
                patientCardViewModel.patientAddress = patient.getAddress();
                patientCardViewModel.patientFullName = patient.getFullName();
                patientCardViewModel.patientPoliceNumber = patient.getMedicalPolicyNumber();
                patientCardViewModel.patientEmail = patient.getEmail();
                //todo need?
                //patientCardViewModel.recipeViewModel = recipeService.getRecipe(patientId,diagnoses.getId(),diagnoses.getRecipeId());

                //todo may be error
                break;
            }
        }
        return patientCardViewModel;*/
    }

    private User userInDb(String login, String password){
        List<User> userList = (List<User>) usersRepository.findAll();
        for(User user : userList){
            if(user.getLogin().equals(login) && user.getHash().equals(Hash.GetHash(password + user.getSalt()))){
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id){
        return usersRepository.findOne(id);
    }

    public List<User> getDoctors() {
        List<User> userList = (List<User>)usersRepository.findAll();
        List<User> doctors = new ArrayList<>();
        Role role = roleService.getRoleByName(RolesNameEnum.DOCTOR.toString());
        for(User user : userList){
            if (user.getRoleId() == role.getId()){
                doctors.add(user);
            }
        }

        return doctors;
    }

    public List<User> getUsersByIds(List<Integer> pharmacistIdList, String roleName) {
        List<User> userList = getUsers();
        List<User> apotekarys = new ArrayList<>();
        Role role = roleService.getRoleByName(roleName);
        for (User user : userList){
            for (Integer pharmacistId : pharmacistIdList){
                if(user.getId() == pharmacistId && user.getRoleId()==role.getId()){
                    apotekarys.add(user);
                }
            }
        }

        return apotekarys;
    }

    public List<User> getUsers(){
        return (List<User>)usersRepository.findAll();
    }

    public User getUserByLogin(String login) {
        List<User> userList = getUsers();
        User userByLogin = new User();
        for (User user : userList){
            if(user.getLogin().equals(login)){
                userByLogin = user;
            }
        }
        userByLogin.role = roleService.getRoleById(userByLogin.getRoleId());
        return userByLogin;
    }

    public List<PatientCardViewModel> getPatientsCards(int userId) {
        List<PatientCardViewModel> history = new ArrayList<>();
        User patient = getUserById(userId);
        if(patient == null)
            return history;

        List<Diagnoses> diagnoseses = diagnosesService.getAllDiagnoses();

        if(diagnoseses == null)
            return history;

        for(Diagnoses diagnoses:diagnoseses){
            if(diagnoses.getPatientUserId() == userId && diagnoses.getRecipeId() != null){
                PatientCardViewModel patientCardViewModel = new PatientCardViewModel();
                patientCardViewModel.patientId = patient.getId();
                patientCardViewModel.patientPoliceNumber = patient.getMedicalPolicyNumber();
                patientCardViewModel.patientEmail = patient.getEmail();
                patientCardViewModel.patientAddress = patient.getAddress();
                patientCardViewModel.patientFullName = patient.getFullName();
                patientCardViewModel.doctorId = diagnoses.getDoctorUserId();
                patientCardViewModel.diagnosis = diagnoses.getDiagnosis();
                patientCardViewModel.complaints = diagnoses.getComplaints();
                patientCardViewModel.visitDate = diagnoses.getCreatedAt();
                patientCardViewModel.recipeId = diagnoses.getRecipeId();
                patientCardViewModel.apothecaryName = "";
                patientCardViewModel.drugsInRecipe = drugService.getDrugsForRecipe(diagnoses.getRecipeId());
                history.add(patientCardViewModel);
            }
        }

        return history;
    }
}
