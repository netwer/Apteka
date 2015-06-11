package aptekaproj.services;

import aptekaproj.ViewModels.PostViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IDiagnosesRepository;
import aptekaproj.models.Diagnoses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 26.05.2015.
 */
@Service
public class DiagnosesService {

    @Autowired
    private IDiagnosesRepository diagnosesRepository;

    @Transactional
    public List<Diagnoses> GetPatientHistory(int userId){
        List<Diagnoses> history = new ArrayList<>();
        List<Diagnoses> diagnoseses = (List<Diagnoses>) diagnosesRepository.findAll();
        for(Diagnoses diagnoses:diagnoseses){
            if(diagnoses.getPatient_user_id() == userId){
                history.add(diagnoses);
            }
        }

        return history;
    }

    public PostViewModel SaveDiagnosis(Diagnoses diagnoses) {
        PostViewModel postViewModel = new PostViewModel();
        try{
            Diagnoses diagnoses1 = diagnosesRepository.save(diagnoses);
            postViewModel.Id = diagnoses1.getId();
            postViewModel.status = "OK";
            postViewModel.message = "Saved";
            return postViewModel;
        }catch (Exception e){
            postViewModel.status = "Error";
            postViewModel.message = e.getMessage();
            return postViewModel;
        }
    }

    public List<Diagnoses> GetDiagnosisForUser(int userId) {
        List<Diagnoses> diagnosesList = (List<Diagnoses>)diagnosesRepository.findAll();
        List<Diagnoses> diagnosesForUser = new ArrayList<>();
        for (Diagnoses diagnoses:diagnosesList){
            if(diagnoses.getPatient_user_id() == userId){
                diagnosesForUser.add(diagnoses);
            }
        }
        return diagnosesForUser;
    }

    public void UpdateDiagnosis(int diagnosesId, int recipeId) {
        Diagnoses diagnoses = diagnosesRepository.findOne(diagnosesId);

        if(diagnoses == null)
            return;

        Diagnoses diagnoses1 = new Diagnoses();
        diagnoses1.setId(diagnoses.getId());
        diagnoses1.setPatient_user_id(diagnoses.getPatient_user_id());
        diagnoses1.setComplaints(diagnoses.getComplaints());
        diagnoses1.setDiagnosis(diagnoses.getDiagnosis());
        diagnoses1.setDoctor_user_id(diagnoses.getDoctor_user_id());
        diagnoses1.setCreated_at(diagnoses.getCreated_at());
        diagnoses1.setRecipe_id(recipeId);
        diagnosesRepository.save(diagnoses1);
    }

    public void UpdateDiagnosis(Diagnoses diagnoses) {
        Diagnoses diagnoses1 = new Diagnoses();
        diagnoses1.setId(diagnoses.getId());
        diagnoses1.setPatient_user_id(diagnoses.getPatient_user_id());
        diagnoses1.setComplaints(diagnoses.getComplaints());
        diagnoses1.setDiagnosis(diagnoses.getDiagnosis());
        diagnoses1.setDoctor_user_id(diagnoses.getDoctor_user_id());
        diagnoses1.setCreated_at(diagnoses.getCreated_at());
        diagnoses1.setRecipe_id(diagnoses.getRecipe_id());
        diagnosesRepository.save(diagnoses1);
    }

    public List<Diagnoses> GetAllDiagnoses() {
        return (List<Diagnoses>)diagnosesRepository.findAll();
    }

    public void DeleteDiagnosis(int id) {
        diagnosesRepository.delete(id);
    }

    public Diagnoses GetDiagnosis(Integer recipeId) {
        List<Diagnoses> diagnosesList = (List<Diagnoses>)diagnosesRepository.findAll();
        Diagnoses diagnoses = new Diagnoses();

        for (Diagnoses diagnoses1 : diagnosesList){
            if(diagnoses1.getRecipe_id() == recipeId){
                diagnoses = diagnoses1;
                break;
            }
        }

        return diagnoses;
    }
}
