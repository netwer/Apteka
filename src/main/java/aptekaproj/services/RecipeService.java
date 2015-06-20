package aptekaproj.services;

import aptekaproj.viewModels.*;
import aptekaproj.controllers.repository.IRecipesRepository;
import aptekaproj.helpers.enums.ProgressStatusEnum;
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
    private DrugService drugService;

    @Autowired
    private DiagnosesService diagnosesService;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private UserService userService;

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    public PostViewModel saveRecipe(RecipeViewModel recipeViewModel){
        //todo check!
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(ProgressStatusEnum.CREATED.toString());
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeViewModel.recipeTitle);
        recipe.setRecipeProgressStatusId(recipeProgressStatus.getId());
        recipe.setPharmacyId(recipeViewModel.pharmacyId);
        recipe.setCreatedAt(new Date());

        //todo CHECK!
        PostViewModel postViewModel = new PostViewModel();
        try {
            Recipe recipe1 = recipesRepository.save(recipe);
            diagnosesService.updateDiagnosis(recipeViewModel.diagnosesId, recipe1.getId());
            recipeHasDrugsService.updateRecipeHasDrugs(recipeViewModel, recipe1);
            postViewModel.id = recipe1.getId();
            postViewModel.message = "Saved";
            postViewModel.status = "OK";

        }catch (Exception e){
            postViewModel.status = "Error";
            postViewModel.message = e.getMessage();
        }
        return postViewModel;
    }

    public void saveRecipe(Recipe recipe){
        recipesRepository.save(recipe);
    }

    public List<PatientRecipeViewModel> getRecipesForPatient(int userId) {
        List<Diagnoses> diagnosesList = diagnosesService.getDiagnosisForUser(userId);
        List<PatientRecipeViewModel> patientRecipeViewModels = new ArrayList<>();
        PatientRecipeViewModel patientRecipeViewModel;
        for (Diagnoses diagnoses : diagnosesList){

            patientRecipeViewModel = new PatientRecipeViewModel();

            if(diagnoses.getRecipeId() == null)
                continue;

            Recipe recipe = recipesRepository.findOne(diagnoses.getRecipeId());

            if(recipe == null)
                continue;

            Pharmacy pharmacy = pharmacyService.getPharmacyById(recipe.getPharmacyId());
            User user = userService.getUserById(diagnoses.getDoctorUserId());
            RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(recipe.getRecipeProgressStatusId());

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

    public void updateRecipe(RecipeViewModel recipeViewModel) {
        Recipe recipe = recipesRepository.findOne(recipeViewModel.recipeId);
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(ProgressStatusEnum.UPDATED.toString());

        if(recipe == null || recipeProgressStatus == null)
            return;

        recipe.setId(recipeViewModel.recipeId);
        recipe.setTitle(recipeViewModel.recipeTitle);
        recipe.setPharmacyId(recipeViewModel.pharmacyId);
        recipe.setCreatedAt(new Date());
        recipe.setRecipeProgressStatusId(recipeProgressStatus.getId());

        recipesRepository.save(recipe);
        recipeHasDrugsService.updateRecipeHasDrugs(recipeViewModel, recipe);
    }

    public RecipeViewModel getRecipe(int recipeId){
        Recipe recipe = getRecipeById(recipeId);
        Diagnoses diagnoses = diagnosesService.getDiagnosis(recipeId);
        RecipeViewModel recipeViewModel = new RecipeViewModel();

        if (recipe == null && diagnoses != null)
            return recipeViewModel;

        recipeViewModel.diagnosesId = diagnoses.getId();
        recipeViewModel.pharmacyId = recipe.getPharmacyId();
        recipeViewModel.recipeId = recipeId;
        recipeViewModel.recipeTitle = recipe.getTitle();
        recipeViewModel.availabilityDate = concreteDrugService.GetAvailabilityRecipeDate(recipeId);
        recipeViewModel.drugViewModels = drugService.getDrugsForRecipe(recipeId);
        return recipeViewModel;
    }

    public Recipe getRecipeById(int recipeId){
        return recipesRepository.findOne(recipeId);
    }

    public List<Recipe> getRecipesForPharmacyByStatus(int pharmacy_id, String status) {
        List<Recipe> recipes = new ArrayList<>();
        List<Recipe> currentRecipes = (List<Recipe>)recipesRepository.findAll();
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(status);

        if (recipeProgressStatus == null)
            return recipes;

        for (Recipe recipe : currentRecipes){
            if(recipe.getPharmacyId() == pharmacy_id && recipe.getRecipeProgressStatusId() == recipeProgressStatus.getId()){
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public void changeStatus(int recipeId, String status) {
        Recipe recipe = getRecipeById(recipeId);

        if (recipe == null)
            return;

        recipe.setRecipeProgressStatusId(recipeProgressStatusService.getRecipeProgressStatusByName(status).getId());
        saveRecipe(recipe);
    }

    public OrderMissingViewModel getOrderMissing(int pharmacistId, int recipeId) {
        List<User> pharmacistsList = pharmacyStaffService.getStaffs(pharmacistId);
        List<DrugViewModel> drugViewModelList = drugService.getDrugsNeedsToProduce(recipeId);
        OrderMissingViewModel orderMissing = new OrderMissingViewModel();
        orderMissing.apothecaryUsers = pharmacistsList;
        orderMissing.drugViewModels = drugViewModelList;
        return orderMissing;
    }
}
