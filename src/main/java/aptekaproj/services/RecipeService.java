package aptekaproj.services;

import aptekaproj.viewModels.DrugViewModel;
import aptekaproj.viewModels.OrderMissingViewModel;
import aptekaproj.viewModels.PatientRecipeViewModel;
import aptekaproj.viewModels.RecipeViewModel;
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

    @Autowired
    private ConcreteDrugsService concreteDrugsService;

    public void Save(RecipeViewModel recipeViewModel){
        //todo check!
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusByName(ProgressStatusEnum.CREATED.toString());
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeViewModel.recipeTitle);
        recipe.setRecipeProgressStatusId(recipeProgressStatus.getId());
        recipe.setPharmacyId(recipeViewModel.pharmacyId);
        recipe.setCreatedAt(new Date());

        //todo CHECK!
        //Recipes recipes1 = recipesRepository.SaveRecipeHasDrugs(recipes);
        Recipe recipe1 = recipesRepository.save(recipe);
        diagnosesService.UpdateDiagnosis(recipeViewModel.diagnosesId, recipe1.getId());
        recipesHasDrugsService.UpdateRecipeHasDrugs(recipeViewModel, recipe1);
    }

    public void Save(Recipe recipe){
        recipesRepository.save(recipe);
    }

    public List<PatientRecipeViewModel> GetRecipesForPatient(int userId) {
        List<Diagnoses> diagnosesList = diagnosesService.GetDiagnosisForUser(userId);
        List<PatientRecipeViewModel> patientRecipeViewModels = new ArrayList<>();
        PatientRecipeViewModel patientRecipeViewModel;
        for (Diagnoses diagnoses : diagnosesList){

            patientRecipeViewModel = new PatientRecipeViewModel();

            if(diagnoses.getRecipeId() == null)
                continue;

            Recipe recipe = recipesRepository.findOne(diagnoses.getRecipeId());

            if(recipe == null)
                continue;

            Pharmacy pharmacy = pharmaciesService.GetPharmacyById(recipe.getPharmacyId());
            User user = userService.getUserById(diagnoses.getDoctorUserId());
            RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusById(recipe.getRecipeProgressStatusId());

            patientRecipeViewModel.doctorId = diagnoses.getDoctorUserId();
            patientRecipeViewModel.doctorName = user.getFullName();
            patientRecipeViewModel.pharmaciesAddress = pharmacy.getAddress();
            patientRecipeViewModel.pharmaciesName = pharmacy.getName();
            patientRecipeViewModel.recipeCreated = recipe.getCreatedAt().toString();
            patientRecipeViewModel.recipeTitle = recipe.getTitle();
            patientRecipeViewModel.recipeId = diagnoses.getRecipeId();
            patientRecipeViewModel.recipeStatusId = recipeProgressStatus.getId();
            patientRecipeViewModel.recipeStatusName = recipeProgressStatus.getName();

            patientRecipeViewModels.add(patientRecipeViewModel);
        }
        return patientRecipeViewModels;
    }

    public void Update(RecipeViewModel recipeViewModel) {
        Recipe recipe = recipesRepository.findOne(recipeViewModel.recipeId);
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusByName(ProgressStatusEnum.UPDATED.toString());

        if(recipe == null || recipeProgressStatus == null)
            return;

        recipe.setId(recipeViewModel.recipeId);
        recipe.setTitle(recipeViewModel.recipeTitle);
        recipe.setPharmacyId(recipeViewModel.pharmacyId);
        recipe.setCreatedAt(new Date());
        recipe.setRecipeProgressStatusId(recipeProgressStatus.getId());

        recipesRepository.save(recipe);
        recipesHasDrugsService.UpdateRecipeHasDrugs(recipeViewModel, recipe);
    }

    public RecipeViewModel GetRecipe(int recipeId){
        Recipe recipe = GetRecipeById(recipeId);
        Diagnoses diagnoses = diagnosesService.GetDiagnosis(recipeId);
        RecipeViewModel recipeViewModel = new RecipeViewModel();

        if (recipe == null && diagnoses != null)
            return recipeViewModel;

        recipeViewModel.diagnosesId = diagnoses.getId();
        recipeViewModel.pharmacyId = recipe.getPharmacyId();
        recipeViewModel.recipeId = recipeId;
        recipeViewModel.recipeTitle = recipe.getTitle();
        recipeViewModel.availabilityDate = concreteDrugsService.GetAvailabilityRecipeDate(recipeId);
        recipeViewModel.drugViewModels = drugsService.GetDrugsForRecipe(recipeId);
        return recipeViewModel;
    }

    public Recipe GetRecipeById(int recipeId){
        return recipesRepository.findOne(recipeId);
    }

    public List<Recipe> GetRecipesForPharmacyByStatus(int pharmacy_id, String status) {
        List<Recipe> recipes = new ArrayList<>();
        List<Recipe> currentRecipes = (List<Recipe>)recipesRepository.findAll();
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.GetRecipeProgressStatusByName(status);

        if (recipeProgressStatus == null)
            return recipes;

        for (Recipe recipe : currentRecipes){
            if(recipe.getPharmacyId() == pharmacy_id && recipe.getRecipeProgressStatusId() == recipeProgressStatus.getId()){
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public void ChangeStatus(int recipeId,String status) {
        Recipe recipe = GetRecipeById(recipeId);

        if (recipe == null)
            return;

        recipe.setRecipeProgressStatusId(recipeProgressStatusService.GetRecipeProgressStatusByName(status).getId());
        Save(recipe);
    }

    public OrderMissingViewModel GetOrderMissing(int pharmacistId, int recipeId) {
        List<User> pharmacistsList = pharmacyStaffService.GetStaffs(pharmacistId);
        List<DrugViewModel> drugViewModelList = drugsService.GetDrugsNeedsToProduce(recipeId);
        OrderMissingViewModel orderMissing = new OrderMissingViewModel();
        orderMissing.apothecaryUsers = pharmacistsList;
        orderMissing.drugViewModels = drugViewModelList;
        return orderMissing;
    }
}
