package aptekaproj.services;

import aptekaproj.viewModels.*;
import aptekaproj.controllers.repository.IRecipesRepository;
import aptekaproj.helpers.enums.ProgressStatusEnum;
import aptekaproj.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    public List<Recipe> getAllRecipes()         { return (List<Recipe>)recipesRepository.findAll();}
    public Recipe getRecipeById(int recipeId)   { return recipesRepository.findOne(recipeId);      }
    public void saveRecipe(Recipe recipe)       { recipesRepository.save(recipe);                  }
    public void deleteRecipe(Recipe recipe)     { recipesRepository.delete(recipe);                }

    public List<PatientRecipeViewModel> getRecipesForPatient(int patientId) {
        List<Diagnoses> diagnosesList = diagnosesService.getDiagnosisForUser(patientId);
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
            patientRecipeViewModel.patientId = patientId;
            patientRecipeViewModel.doctorName = user.getFullName();
            patientRecipeViewModel.pharmaciesAddress = pharmacy.getAddress();
            patientRecipeViewModel.pharmaciesName = pharmacy.getName();
            patientRecipeViewModel.recipeCreated = recipe.getCreatedAt().toString();
            patientRecipeViewModel.recipeTitle = recipe.getTitle();
            patientRecipeViewModel.recipeId = diagnoses.getRecipeId();
            patientRecipeViewModel.recipeStatusId = recipeProgressStatus.getId();
            patientRecipeViewModel.recipeStatusName = recipeProgressStatus.getName();
            patientRecipeViewModel.complaints = diagnoses.getComplaints();
            patientRecipeViewModel.diagnosis = diagnoses.getDiagnosis();
            patientRecipeViewModel.drugs = drugService.getDrugsForRecipe(recipe.getId());

            patientRecipeViewModels.add(patientRecipeViewModel);
        }
        return patientRecipeViewModels;
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
        recipeViewModel.recipeProgressStatusId = recipe.getRecipeProgressStatusId();
        recipeViewModel.availabilityDate = concreteDrugService.getAvailabilityRecipeDate(recipeId);
        recipeViewModel.drugs = drugService.getDrugsForRecipe(recipeId);
        return recipeViewModel;
    }

    public List<RecipeViewModel> getRecipesForPharmacyByStatus(int pharmacyId, int statusId) {
        List<RecipeViewModel> recipes = new ArrayList<>();
        List<Recipe> currentRecipes = (List<Recipe>)recipesRepository.findAll();
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusById(statusId);

        if (recipeProgressStatus == null)
            return recipes;

        for (Recipe recipe : currentRecipes){
            if(recipe.getPharmacyId() == pharmacyId && recipe.getRecipeProgressStatusId() == recipeProgressStatus.getId()){
                Diagnoses diagnoses = diagnosesService.getDiagnosesByRecipeId(recipe.getId());
                if(diagnoses.getId() == 0)
                    continue;
                RecipeViewModel recipeViewModel = new RecipeViewModel();
                recipeViewModel.recipeTitle = recipe.getTitle();
                recipeViewModel.recipeId = recipe.getId();
                recipeViewModel.pharmacyId = recipe.getPharmacyId();
                recipeViewModel.patientFullName = userService.getUserById(diagnoses.getPatientUserId()).getFullName();
                recipeViewModel.diagnosesId = diagnoses.getId();
                recipeViewModel.availabilityDate = concreteDrugService.getAvailabilityRecipeDate(recipe.getId());
                recipeViewModel.drugs = drugService.getDrugsForRecipe(recipe.getId());
                recipes.add(recipeViewModel);
            }
        }

        return recipes;
    }

    public boolean changeStatus(int recipeId, int statusId) {
        Recipe recipe = getRecipeById(recipeId);

        if (recipe == null)
            return false;

        recipe.setRecipeProgressStatusId(recipeProgressStatusService.getRecipeProgressStatusById(statusId).getId());
        saveRecipe(recipe);

        return true;
    }

    public boolean changeStatus(int recipeId, String status) {
        Recipe recipe = getRecipeById(recipeId);

        if (recipe == null)
            return false;

        recipe.setRecipeProgressStatusId(recipeProgressStatusService.getRecipeProgressStatusByName(status).getId());
        saveRecipe(recipe);

        return true;
    }

    public Recipe createRecipe(Date visitDate, int pharmacyId) throws ParseException {
        Recipe recipe = new Recipe();
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(ProgressStatusEnum.CREATED.toString());

        recipe.setCreatedAt(visitDate);
        recipe.setPharmacyId(pharmacyId);
        recipe.setRecipeProgressStatusId(recipeProgressStatus.getId());
        int recipeNumber = getAllRecipes().size()+1;
        recipe.setTitle("Рецепт № " + recipeNumber);
        recipe.setCreatedAt(visitDate);

        return recipesRepository.save(recipe);
    }

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

    public List<RecipeViewModel> getRecipesByStatus(int doctorId, String status) {
        List<RecipeViewModel> recipeViewModels = new ArrayList<>();
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName(status);
        List<Diagnoses> diagnoseses = diagnosesService.getDiagnosisForDoctor(doctorId);
        if(recipeProgressStatus == null)
            return recipeViewModels;
        for(Diagnoses diagnoses : diagnoseses){
            Recipe recipe = getRecipeById(diagnoses.getRecipeId());
            if(recipe.getRecipeProgressStatusId() == recipeProgressStatus.getId()){
                RecipeViewModel recipeViewModel = new RecipeViewModel();
                recipeViewModel.diagnosesId = diagnoses.getId();
                recipeViewModel.recipeId = recipe.getId();
                recipeViewModel.recipeTitle = recipe.getTitle();
                recipeViewModel.pharmacyId = recipe.getPharmacyId();
                recipeViewModel.patientFullName = userService.getUserById(diagnoses.getPatientUserId()).getFullName();

                recipeViewModels.add(recipeViewModel);
            }
        }

        return recipeViewModels;
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

    /*public OrderMissingViewModel getOrderMissing(int pharmacistId, int recipeId) {
        List<User> apothecariesList = pharmacyStaffService.getApothecariesStaffs(pharmacistId);
        List<DrugViewModel> drugViewModelList = drugService.getDrugsNeedsToProduce(recipeId);
        OrderMissingViewModel orderMissing = new OrderMissingViewModel();
        orderMissing.apothecaryUsers = apothecariesList;
        orderMissing.drugViewModels = drugViewModelList;
        return orderMissing;
    }*/
}
