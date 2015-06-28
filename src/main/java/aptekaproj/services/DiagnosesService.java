package aptekaproj.services;

import aptekaproj.models.Pharmacy;
import aptekaproj.models.Recipe;
import aptekaproj.viewModels.PatientCardViewModel;
import aptekaproj.viewModels.PostViewModel;
import aptekaproj.controllers.repository.IDiagnosesRepository;
import aptekaproj.models.Diagnoses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 26.05.2015.
 */
@Service
public class DiagnosesService {

    @Autowired
    private IDiagnosesRepository diagnosesRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    public List<Diagnoses> getAllDiagnoses() {
        return (List<Diagnoses>)diagnosesRepository.findAll();
    }

    public Diagnoses getDiagnosesById(int diagnosisId) {
        return diagnosesRepository.findOne(diagnosisId);
    }

    public PostViewModel saveDiagnosis(Diagnoses diagnoses) {
        PostViewModel postViewModel = new PostViewModel();
        try{
            Diagnoses diagnoses1 = diagnosesRepository.save(diagnoses);
            postViewModel.id = diagnoses1.getId();
            postViewModel.status = "OK";
            postViewModel.message = "Saved";
            return postViewModel;
        }catch (Exception e){
            postViewModel.status = "Error";
            postViewModel.message = e.getMessage();
            return postViewModel;
        }
    }

    public void updateDiagnosis(int diagnosesId, int recipeId) {
        Diagnoses diagnoses = diagnosesRepository.findOne(diagnosesId);

        if(diagnoses == null)
            return;

        Diagnoses diagnoses1 = new Diagnoses();
        diagnoses1.setId(diagnoses.getId());
        diagnoses1.setPatientUserId(diagnoses.getPatientUserId());
        diagnoses1.setComplaints(diagnoses.getComplaints());
        diagnoses1.setDiagnosis(diagnoses.getDiagnosis());
        diagnoses1.setDoctorUserId(diagnoses.getDoctorUserId());
        diagnoses1.setCreatedAt(diagnoses.getCreatedAt());
        diagnoses1.setRecipeId(recipeId);
        diagnosesRepository.save(diagnoses1);
    }

    public PostViewModel deleteDiagnosis(int id) {
        PostViewModel postViewModel = new PostViewModel();
        try{
            diagnosesRepository.delete(id);
            postViewModel.id = id;
            postViewModel.status = "OK";
            postViewModel.message = "Deleted";
            return postViewModel;
        }catch (Exception e){
            postViewModel.status = "Error";
            postViewModel.message = e.getMessage();
            return postViewModel;
        }


    }

    public PostViewModel updateDiagnosis(Diagnoses diagnoses) {
        Diagnoses diagnoses1 = new Diagnoses();
        diagnoses1.setId(diagnoses.getId());
        diagnoses1.setPatientUserId(diagnoses.getPatientUserId());
        diagnoses1.setComplaints(diagnoses.getComplaints());
        diagnoses1.setDiagnosis(diagnoses.getDiagnosis());
        diagnoses1.setDoctorUserId(diagnoses.getDoctorUserId());
        diagnoses1.setCreatedAt(diagnoses.getCreatedAt());
        diagnoses1.setRecipeId(diagnoses.getRecipeId());

        PostViewModel postViewModel = new PostViewModel();
        try{
            Diagnoses diagnoses2 = diagnosesRepository.save(diagnoses1);
            postViewModel.id = diagnoses2.getId();
            postViewModel.status = "OK";
            postViewModel.message = "Updated";
            return postViewModel;
        }catch (Exception e){
            postViewModel.status = "Error";
            postViewModel.message = e.getMessage();
            return postViewModel;
        }
    }

    public List<Diagnoses> getDiagnosisForUser(int userId) {
        List<Diagnoses> diagnosesList = (List<Diagnoses>)diagnosesRepository.findAll();
        List<Diagnoses> diagnosesForUser = new ArrayList<>();
        for (Diagnoses diagnoses:diagnosesList){
            if(diagnoses.getPatientUserId() == userId){
                diagnosesForUser.add(diagnoses);
            }
        }
        return diagnosesForUser;
    }

    public List<Diagnoses> getDiagnosisForDoctor(int userId) {
        List<Diagnoses> diagnosesList = (List<Diagnoses>)diagnosesRepository.findAll();
        List<Diagnoses> diagnosesForUser = new ArrayList<>();
        for (Diagnoses diagnoses:diagnosesList){
            if(diagnoses.getDoctorUserId() == userId){
                diagnosesForUser.add(diagnoses);
            }
        }
        return diagnosesForUser;
    }

    public Diagnoses getDiagnosis(Integer recipeId) {
        List<Diagnoses> diagnosesList = (List<Diagnoses>)diagnosesRepository.findAll();
        Diagnoses diagnoses = new Diagnoses();

        for (Diagnoses diagnoses1 : diagnosesList){
            if(diagnoses1.getRecipeId() == recipeId){
                diagnoses = diagnoses1;
                break;
            }
        }

        return diagnoses;
    }

    public void saveAppointment(PatientCardViewModel patientCardViewModel, int diagnosisId) throws ParseException {
        Diagnoses diagnoses = getDiagnosesById(diagnosisId);
        Pharmacy pharmacy = pharmacyService.getPharmacyById(patientCardViewModel.pharmacyId);
        Recipe recipe = recipeService.createRecipe(patientCardViewModel.visitDate,pharmacy.getId());

        if(diagnoses == null || pharmacy == null || recipe == null)
            return;

        diagnoses.setComplaints(patientCardViewModel.complaints);
        diagnoses.setDiagnosis(patientCardViewModel.diagnosis);
        diagnoses.setCreatedAt(new Date());
        diagnoses.setRecipeId(recipe.getId());
        //diagnoses.setCreatedAt(patientCardViewModel.visitDate);
        //diagnoses.setDoctorUserId(patientCardViewModel.doctorId);
        //diagnoses.setPatientUserId(patientCardViewModel.patientId);
        diagnosesRepository.save(diagnoses);

        recipeHasDrugsService.saveRecipeHasDrugs(patientCardViewModel.drugs,recipe.getId());
    }

    public Diagnoses getDiagnosesByRecipeId(int recipeId) {
        List<Diagnoses> diagnoseses = getAllDiagnoses();
        Diagnoses diagnoses = new Diagnoses();
        if(diagnoseses == null)
            return diagnoses;

        for(Diagnoses diagnoses1 : diagnoseses){
            if(diagnoses1.getRecipeId() == recipeId){
                diagnoses = diagnoses1;
                break;
            }
        }
        return diagnoses;
    }

    @Transactional
    public List<Diagnoses> getPatientHistory(int userId){
        List<Diagnoses> history = new ArrayList<>();
        List<Diagnoses> diagnoseses = (List<Diagnoses>) diagnosesRepository.findAll();
        for(Diagnoses diagnoses:diagnoseses){
            if(diagnoses.getPatientUserId() == userId){
                history.add(diagnoses);
            }
        }

        return history;
    }
}
