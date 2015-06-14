package aptekaproj.services;

import aptekaproj.ViewModels.PatientCardViewModel;
import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.helpers.Enums.RolesNameEnum;
import aptekaproj.helpers.Hash;
import aptekaproj.controllers.repository.IUsersRepository;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Roles;
import aptekaproj.models.Users;
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
    private List<Users> doctors;

    public UserViewModel getUser(String login, String password){
        Users user = userInDb(login,password);
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
    public List<UsersDoctorViewModel> getPatients(int userId){
        List<Diagnoses> diagnoseses = diagnosesService.GetAllDiagnoses();
        List<UsersDoctorViewModel> usersDoctorViewModels = new ArrayList<>();

        for(Diagnoses diagnoses : diagnoseses){
            if(diagnoses.getDoctor_user_id()  == userId &&
               diagnoses.getRecipe_id()       == null   &&
                    (diagnoses.getComplaints() == null || diagnoses.getComplaints().isEmpty()) &&
                    (diagnoses.getDiagnosis() == null || diagnoses.getDiagnosis().isEmpty())){

                Users patient = getUserById(diagnoses.getPatient_user_id());
                Users doctor = getUserById(userId);
                UsersDoctorViewModel usersDoctorViewModel = new UsersDoctorViewModel();
                usersDoctorViewModel.DoctorId = userId;
                usersDoctorViewModel.PatientId = patient.getId();
                usersDoctorViewModel.PatientFullName = patient.getFullName();
                usersDoctorViewModel.DoctorFullName = doctor.getFullName();
                //todo right date?
                //usersDoctorViewModel.LastVisitDate = new SimpleDateFormat("MM/dd/yyyy").format(diagnoses.getCreated_at()).toString();
                usersDoctorViewModel.LastVisitDate = diagnoses.getCreated_at().toString();
                usersDoctorViewModel.DiagnosisId = diagnoses.getId();
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
        List<Diagnoses> diagnoseses = diagnosesService.GetAllDiagnoses();
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
                //todo need?
                //patientCardViewModel.recipeViewModel = recipeService.getRecipe(patientId,diagnoses.getId(),diagnoses.getRecipe_id());

                //todo may be error
                break;
            }
        }
        return patientCardViewModel;
    }

    private Users userInDb(String login, String password){
        List<Users> usersList = (List<Users>) usersRepository.findAll();
        for(Users user : usersList){
            if(user.getLogin().equals(login) && user.getHash().equals(Hash.GetHash(password + user.getSalt()))){
                return user;
            }
        }
        return null;
    }

    public Users getUserById(int id){
        return usersRepository.findOne(id);
    }

    public List<Users> getDoctors() {
        List<Users> usersList = (List<Users>)usersRepository.findAll();
        List<Users> doctors = new ArrayList<>();
        Roles roles = roleService.GetRoleByName(RolesNameEnum.DOCTOR.toString());
        for(Users user : usersList){
            if (user.getRoleId() == roles.getId()){
                doctors.add(user);
            }
        }

        return doctors;
    }

    public List<Users> GetUsersByIds(List<Integer> pharmacistIdList, String roleName) {
        List<Users> usersList = GetUsers();
        List<Users> apotekarys = new ArrayList<>();
        Roles role = roleService.GetRoleByName(roleName);
        for (Users user : usersList){
            for (Integer pharmacistId : pharmacistIdList){
                if(user.getId() == pharmacistId && user.getRoleId()==role.getId()){
                    apotekarys.add(user);
                }
            }
        }

        return apotekarys;
    }

    public List<Users> GetUsers(){
        return (List<Users>)usersRepository.findAll();
    }
}
