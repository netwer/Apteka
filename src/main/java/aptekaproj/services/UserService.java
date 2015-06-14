package aptekaproj.services;

import aptekaproj.viewModels.PatientCardViewModel;
import aptekaproj.viewModels.UserViewModel;
import aptekaproj.viewModels.UserDoctorViewModel;
import aptekaproj.helpers.Enums.RolesNameEnum;
import aptekaproj.helpers.Hash;
import aptekaproj.controllers.repository.IUsersRepository;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Role;
import aptekaproj.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private DiagnosesService diagnosesService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RecipeService recipeService;
    private List<User> doctors;

    public UserViewModel getUser(String login, String password){
        User user = userInDb(login,password);
        UserViewModel responseViewModel = new UserViewModel();
        if(user != null ){
            responseViewModel.Url = "/" + roleService.GetRoleName(user.getRoleId()) + "/";
            responseViewModel.UserFullName = user.getFullName();
            responseViewModel.UserId = user.getId();
            responseViewModel.UserLogin = user.getLogin();
            responseViewModel.UserRole = roleService.GetRoleName(user.getRoleId());
            responseViewModel.UserRoleId = user.getRoleId();
            responseViewModel.ErrorMessage = "";
            //return"redirect:/" + roleService.GetRoleName(user.getRoleId()) + "/Success?message=Welcome," + userName;
            return responseViewModel;
        }
        responseViewModel.ErrorMessage = "login or password incorrect";
        responseViewModel.Url = "/Login/login";
        return responseViewModel;
    }

    //todo add check is recipe_id is null ?!
    //todo add check is Complaints is null ?!
    //todo add check is Diagnosis is null?!
    public List<UserDoctorViewModel> getPatients(int userId){
        List<Diagnoses> diagnoseses = diagnosesService.GetAllDiagnoses();
        List<UserDoctorViewModel> userDoctorViewModels = new ArrayList<>();

        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctorUserId()  == userId &&
               diagnoses.getRecipeId()       == null   &&
                    (diagnoses.getComplaints() == null || diagnoses.getComplaints().isEmpty()) &&
                    (diagnoses.getDiagnosis() == null || diagnoses.getDiagnosis().isEmpty())){

                User patient = getUserById(diagnoses.getPatientUserId());
                User doctor = getUserById(userId);
                UserDoctorViewModel userDoctorViewModel = new UserDoctorViewModel();
                userDoctorViewModel.DoctorId = userId;
                userDoctorViewModel.PatientId = patient.getId();
                userDoctorViewModel.PatientFullName = patient.getFullName();
                userDoctorViewModel.DoctorFullName = doctor.getFullName();
                //todo right date?
                //usersDoctorViewModel.LastVisitDate = new SimpleDateFormat("MM/dd/yyyy").format(diagnoses.getCreatedAt()).toString();
                userDoctorViewModel.LastVisitDate = diagnoses.getCreatedAt().toString();
                userDoctorViewModel.DiagnosisId = diagnoses.getId();
                userDoctorViewModels.add(userDoctorViewModel);
            }
        }
        return userDoctorViewModels;
    }

    //todo add check is recipe_id is null ?!
    //todo add check is Complaints is null ?!
    //todo add check is Diagnosis is null?!
    public PatientCardViewModel getPatientCard(int patientId,int doctorId){
        PatientCardViewModel patientCardViewModel = new PatientCardViewModel();
        User patient = new User();
        List<Diagnoses> diagnoseses = diagnosesService.GetAllDiagnoses();
        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctorUserId()  == doctorId  &&
               diagnoses.getPatientUserId() == patientId &&
               diagnoses.getRecipeId()       == null      &&
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
                //todo need?
                //patientCardViewModel.recipeViewModel = recipeService.getRecipe(patientId,diagnoses.getId(),diagnoses.getRecipeId());

                //todo may be error
                break;
            }
        }
        return patientCardViewModel;
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
        Role role = roleService.GetRoleByName(RolesNameEnum.DOCTOR.toString());
        for(User user : userList){
            if (user.getRoleId() == role.getId()){
                doctors.add(user);
            }
        }

        return doctors;
    }

    public List<User> GetUsersByIds(List<Integer> pharmacistIdList, String roleName) {
        List<User> userList = GetUsers();
        List<User> apotekarys = new ArrayList<>();
        Role role = roleService.GetRoleByName(roleName);
        for (User user : userList){
            for (Integer pharmacistId : pharmacistIdList){
                if(user.getId() == pharmacistId && user.getRoleId()==role.getId()){
                    apotekarys.add(user);
                }
            }
        }

        return apotekarys;
    }

    public List<User> GetUsers(){
        return (List<User>)usersRepository.findAll();
    }
}
