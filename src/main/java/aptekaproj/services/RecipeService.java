package aptekaproj.services;

import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.ViewModels.PatientRecipeViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IRecipesRepository;
import aptekaproj.helpers.Enums.ProgressStatusEnum;
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
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(ProgressStatusEnum.CREATED.toString());
        Recipes recipes = new Recipes();
        recipes.setTitle(recipeViewModel.RecipeTitle);
        recipes.setRecipeProgressStatusId(recipeProgressStatus.getId());
        recipes.setPharmacyId(recipeViewModel.PharmacyId);
        recipes.setCreated_at(new Date());

        //todo CHECK!
        Recipes recipes1 = recipesRepository.save(recipes);
        diagnosesService.Update(recipeViewModel.DiagnosesId,recipes1.getId());
        recipesHasDrugsService.Update(recipeViewModel,recipes1);
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

    public void Update(RecipeViewModel recipeViewModel) {
        Recipes recipes = recipesRepository.findOne(recipeViewModel.RecipeId);
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(ProgressStatusEnum.UPDATED.toString());

        if(recipes == null || recipeProgressStatus == null)
            return;

        recipes.setId(recipeViewModel.RecipeId);
        recipes.setTitle(recipeViewModel.RecipeTitle);
        recipes.setPharmacyId(recipeViewModel.PharmacyId);
        recipes.setCreated_at(new Date());
        recipes.setRecipeProgressStatusId(recipeProgressStatus.getId());

        recipesRepository.save(recipes);
        recipesHasDrugsService.Update(recipeViewModel,recipes);
    }

    public RecipeViewModel GetRecipeForUser(int userId){
        return null;
    }
}
