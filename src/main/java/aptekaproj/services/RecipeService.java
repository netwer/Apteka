package aptekaproj.services;

import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.ViewModels.PatientRecipeViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IRecipesRepository;
import aptekaproj.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class RecipeService {
    @Autowired
    private IRecipesRepository recipesRepository;

    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    @Autowired
    private DrugsService drugsService;

    @Autowired
    private DiagnosesService diagnosesService;

    @Autowired
    private RecipesHasDrugsService recipesHasDrugsService;

    @Autowired
    private PharmaciesService pharmaciesService;

    @Autowired
    private UserService userService;

    public void Save(RecipeViewModel recipeViewModel){
        //todo check!
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName("СОЗДАН");
        Recipes recipes = new Recipes();
        recipes.setTitle(recipeViewModel.RecipeTitle);
        recipes.setRecipeProgressStatusId(recipeProgressStatus.getId());
        recipes.setPharmacyId(recipeViewModel.PharmacyId);
        recipes.setCreated_at(new Date());

        //todo CHECK!
        Recipes recipes1 = recipesRepository.save(recipes);
        for (DrugsViewModel drugsViewModel : recipeViewModel.drugsViewModelList){
            RecipesHasDrugs recipesHasDrugs = new RecipesHasDrugs();
            recipesHasDrugs.setCount(drugsViewModel.DrugCount);
            recipesHasDrugs.setDrug_id(drugsViewModel.DrugId);
            recipesHasDrugs.setRecipe_id(recipes1.getId());
            recipesHasDrugs.setDone(false);
            recipesHasDrugsService.Save(recipesHasDrugs);
        }
    }

    public List<PatientRecipeViewModel> GetRecipesForPatient(int userId) {
        List<Diagnoses> diagnosesList = diagnosesService.getDiagnosesForUser(userId);
        List<PatientRecipeViewModel> patientRecipeViewModels = new ArrayList<>();
        PatientRecipeViewModel patientRecipeViewModel;
        for (Diagnoses diagnoses : diagnosesList){

            patientRecipeViewModel = new PatientRecipeViewModel();
            Recipes recipes = recipesRepository.findOne(diagnoses.getRecipe_id());
            Pharmacies pharmacies = pharmaciesService.getPharmacyById(recipes.getPharmacyId());
            Users users = userService.getUserById(diagnoses.getDoctor_user_id());
            RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(recipes.getRecipeProgressStatusId());

            patientRecipeViewModel.DoctorId = diagnoses.getDoctor_user_id();
            patientRecipeViewModel.DoctorName = users.getFullName();
            patientRecipeViewModel.PharmaciesAddress = pharmacies.getAddress();
            patientRecipeViewModel.PharmaciesName = pharmacies.getName();
            patientRecipeViewModel.RecipeCreated = recipes.getCreated_at().toString();
            patientRecipeViewModel.RecipeTitle = recipes.getTitle();
            patientRecipeViewModel.RecipeId = diagnoses.getRecipe_id();
            patientRecipeViewModel.RecipeStatusId = recipeProgressStatus.getId();
            patientRecipeViewModel.RecipeStatusName = recipeProgressStatus.getName();

            patientRecipeViewModels.add(patientRecipeViewModel);
        }
        return patientRecipeViewModels;
    }
}
