package aptekaproj.services;

import aptekaproj.ViewModels.PostViewModel;
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
    public List<Diagnoses> getPatientHistory(int userId){
        List<Diagnoses> history = new ArrayList<>();
        List<Diagnoses> diagnoseses = (List<Diagnoses>) diagnosesRepository.findAll();
        for(Diagnoses diagnoses:diagnoseses){
            if(diagnoses.getPatient_user_id() == userId){
                history.add(diagnoses);
            }
        }

        return history;
    }

    public PostViewModel SaveDiagnoses(Diagnoses diagnoses) {
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

    public List<Diagnoses> getDiagnosesForUser(int userId) {
        List<Diagnoses> diagnosesList = (List<Diagnoses>)diagnosesRepository.findAll();
        List<Diagnoses> diagnosesForUser = new ArrayList<>();
        for (Diagnoses diagnoses:diagnosesList){
            if(diagnoses.getPatient_user_id() == userId){
                diagnosesForUser.add(diagnoses);
            }
        }
        return diagnosesForUser;
    }
}
