package aptekaproj.services;

import aptekaproj.ViewModels.OrderMissingViewModel;
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

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    public void Save(RecipeViewModel recipeViewModel){
        //todo check!
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusByName(ProgressStatusEnum.CREATED.toString());
        Recipes recipes = new Recipes();
        recipes.setTitle(recipeViewModel.RecipeTitle);
        recipes.setRecipeProgressStatusId(recipeProgressStatus.getId());
        recipes.setPharmacyId(recipeViewModel.PharmacyId);
        recipes.setCreated_at(new Date());

        //todo CHECK!
        //Recipes recipes1 = recipesRepository.save(recipes);
        Recipes recipes1 = recipesRepository.save(recipes);
        diagnosesService.Update(recipeViewModel.DiagnosesId,recipes1.getId());
        recipesHasDrugsService.Update(recipeViewModel,recipes1);
    }

    public void Save(Recipes recipes){
        recipesRepository.save(recipes);
    }

    public List<PatientRecipeViewModel> GetRecipesForPatient(int userId) {
        List<Diagnoses> diagnosesList = diagnosesService.getDiagnosesForUser(userId);
        List<PatientRecipeViewModel> patientRecipeViewModels = new ArrayList<>();
        PatientRecipeViewModel patientRecipeViewModel;
        for (Diagnoses diagnoses : diagnosesList){

            patientRecipeViewModel = new PatientRecipeViewModel();

            if(diagnoses.getRecipe_id() == null)
                continue;

            Recipes recipes = recipesRepository.findOne(diagnoses.getRecipe_id());

            if(recipes == null)
                continue;

            Pharmacies pharmacies = pharmaciesService.getPharmacyById(recipes.getPharmacyId());
            Users users = userService.getUserById(diagnoses.getDoctor_user_id());
            RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusById(recipes.getRecipeProgressStatusId());

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
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusByName(ProgressStatusEnum.UPDATED.toString());

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

    public RecipeViewModel GetRecipe(int recipeId){
        Recipes recipes = GetRecipeById(recipeId);
        Diagnoses diagnoses = diagnosesService.GetDiagnoses(recipeId);
        RecipeViewModel recipeViewModel = new RecipeViewModel();

        if (recipes == null && diagnoses != null)
            return recipeViewModel;

        recipeViewModel.DiagnosesId = diagnoses.getId();
        recipeViewModel.PharmacyId = recipes.getPharmacyId();
        recipeViewModel.RecipeId = recipeId;
        recipeViewModel.RecipeTitle = recipes.getTitle();
        recipeViewModel.drugsViewModelList = drugsService.GetDrugsForRecipe(recipeId);
        return recipeViewModel;
    }

    public Recipes GetRecipeById(int recipeId){
        return recipesRepository.findOne(recipeId);
    }

    public List<Recipes> GetRecipesForPharmacyByStatus(int pharmacy_id, String status) {
        List<Recipes> recipes = new ArrayList<>();
        List<Recipes> currentRecipes = (List<Recipes>)recipesRepository.findAll();
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusByName(status);

        if (recipeProgressStatus == null)
            return recipes;

        for (Recipes recipe : currentRecipes){
            if(recipe.getPharmacyId() == pharmacy_id && recipe.getRecipeProgressStatusId() == recipeProgressStatus.getId()){
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public void ChangeStatus(int recipeId,String status) {
        Recipes recipe = GetRecipeById(recipeId);

        if (recipe == null)
            return;

        recipe.setRecipeProgressStatusId(recipeProgressStatusService.GetRecipeProgressStatusByName(status).getId());
        Save(recipe);
    }

    public OrderMissingViewModel GetOrderMissing(int pharmacistId) {
        List<Users> pharmacistsList = pharmacyStaffService.GetStaffs(pharmacistId);
        return null;
    }
}
