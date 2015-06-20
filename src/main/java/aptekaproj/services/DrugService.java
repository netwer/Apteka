package aptekaproj.services;

import aptekaproj.viewModels.DrugToProduceViewModel;
import aptekaproj.viewModels.DrugViewModel;
import aptekaproj.controllers.repository.IDrugsRepository;
import aptekaproj.helpers.Enums.ProgressStatusEnum;
import aptekaproj.models.ConcreteDrug;
import aptekaproj.models.Drug;
import aptekaproj.models.Recipe;
import aptekaproj.models.RecipeHasDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class DrugService {

    @Autowired
    private IDrugsRepository drugsRepository;

    @Autowired
    private RecipeHasDrugsService recipeHasDrugsService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    @Autowired
    private IngredientService ingredientService;

    public List<Drug> getDrugs(){
        return (List<Drug>)drugsRepository.findAll();
    }

    public List<DrugViewModel> getDrugsForRecipe(Integer recipeId) {
        List<RecipeHasDrugs> recipeHasDrugs = recipeHasDrugsService.getAllRecipesHasDrugs();
        List<DrugViewModel> drugViewModels = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugs){
            if(recipeHasDrugs1.getRecipeId() == recipeId){
                DrugViewModel drugViewModel = new DrugViewModel();
                Drug drug = drugsRepository.findOne(recipeHasDrugs1.getDrugId());

                if(drug == null)
                    continue;

                drugViewModel.recipesHasDrugsId = recipeHasDrugs1.getId();
                drugViewModel.drugId = recipeHasDrugs1.getDrugId();
                drugViewModel.drugCount = recipeHasDrugs1.getCount();
                drugViewModel.drugName = drug.getName();
                drugViewModel.availabilityDate = concreteDrugService.GetAvailabilityDrugDate(recipeId,drug.getId());
                drugViewModel.needsToProduce = drug.getNeedToProduce();

                drugViewModels.add(drugViewModel);
            }
        }

        return drugViewModels;
    }

    public List<DrugViewModel> getDrugsNeedsToProduce(int recipeId) {
        List<RecipeHasDrugs> recipeHasDrugs = recipeHasDrugsService.getAllRecipesHasDrugs();
        List<DrugViewModel> drugViewModels = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugs){
            if(recipeHasDrugs1.getRecipeId() == recipeId){
                Drug drug = drugsRepository.findOne(recipeHasDrugs1.getDrugId());
                if(drug != null && drug.getNeedToProduce() == true) {
                    DrugViewModel drugViewModel = new DrugViewModel();

                    drugViewModel.needsToProduce = drug.getNeedToProduce();
                    drugViewModel.drugName = drug.getName();
                    drugViewModel.drugCount = recipeHasDrugs1.getCount();
                    drugViewModel.drugId = drug.getId();
                    drugViewModel.recipesHasDrugsId = recipeHasDrugs1.getId();

                    drugViewModels.add(drugViewModel);
                }
            }
        }

        return drugViewModels;
    }

    public Drug getDrugById(int drugId) {
        return drugsRepository.findOne(drugId);
    }

    public List<DrugToProduceViewModel> getDrugsToProduce(int pharmacyStaffId) {
        List<ConcreteDrug> concreteDrugList = concreteDrugService.getAllConcreteDrugs();
        List<DrugToProduceViewModel> drugsToProduce = new ArrayList<>();

        for (ConcreteDrug concreteDrug : concreteDrugList){
            Recipe recipe = recipeService.getRecipeById(concreteDrug.getRecipeId());

            if(recipe == null)
                continue;

            String recipeStatus = recipeProgressStatusService.getRecipeProgressStatusById(recipe.getRecipeProgressStatusId()).getName().toUpperCase();
            String progressStatus = ProgressStatusEnum.IN_PROCESS.toString().toUpperCase();

            if(concreteDrug.getPharmacyStaffId() == pharmacyStaffId && recipeStatus.equals(progressStatus)){
                DrugToProduceViewModel drugToProduceViewModel = new DrugToProduceViewModel();
                drugToProduceViewModel.drugViewModel = getDrug(concreteDrug.getRecipeId(),concreteDrug.getDrugId());
                drugToProduceViewModel.ingredientInDrugViewModels = ingredientService.getIngredientsForDrug(concreteDrug.getDrugId(),concreteDrug.getId());
                drugsToProduce.add(drugToProduceViewModel);
            }
        }

        return drugsToProduce;
    }

    public DrugToProduceViewModel getDrugToProduce(int pharmacyStaffId, int drugId) {
        List<ConcreteDrug> concreteDrugList = concreteDrugService.getAllConcreteDrugs();
        DrugToProduceViewModel drugToProduce = new DrugToProduceViewModel();

        for (ConcreteDrug concreteDrug : concreteDrugList){
            Recipe recipe = recipeService.getRecipeById(concreteDrug.getRecipeId());

            if(recipe == null)
                continue;

            String recipeStatus = recipeProgressStatusService.getRecipeProgressStatusById(recipe.getRecipeProgressStatusId()).getName().toUpperCase();
            String status = ProgressStatusEnum.IN_PROCESS.toString().toUpperCase();

            if(concreteDrug.getPharmacyStaffId() == pharmacyStaffId && recipeStatus.equals(status) && concreteDrug.getDrugId() == drugId){
                DrugToProduceViewModel drugToProduceViewModel = new DrugToProduceViewModel();
                drugToProduceViewModel.drugViewModel = getDrug(concreteDrug.getRecipeId(),concreteDrug.getDrugId());
                drugToProduceViewModel.ingredientInDrugViewModels = ingredientService.getIngredientsForDrug(concreteDrug.getDrugId(),concreteDrug.getId());
                drugToProduce = drugToProduceViewModel;
                break;
            }
        }

        return drugToProduce;
    }

    private DrugViewModel getDrug(int recipeId, int drugId){
        DrugViewModel drugViewModel = new DrugViewModel();
        List<RecipeHasDrugs> recipeHasDrugs = recipeHasDrugsService.getAllRecipesHasDrugs();

        for (RecipeHasDrugs recipeHasDrug : recipeHasDrugs){
            if(recipeHasDrug.getRecipeId() == recipeId && recipeHasDrug.getDrugId() == drugId){
                Drug drug = getDrugById(drugId);
                if (drug == null)
                    continue;

                drugViewModel.drugId = drug.getId();
                drugViewModel.drugName = drug.getName();
                drugViewModel.drugCount = recipeHasDrug.getCount();
                drugViewModel.recipesHasDrugsId = recipeHasDrug.getId();
                drugViewModel.needsToProduce = drug.getNeedToProduce();
                drugViewModel.availabilityDate = concreteDrugService.GetAvailabilityDrugDate(recipeId,drug.getId());
                return drugViewModel;
            }
        }

        return drugViewModel;
    }

    public RecipeHasDrugs drugToDone(int recipeHasDrugsId) {
        RecipeHasDrugs recipeHasDrugs = recipeHasDrugsService.getRecipeHasDrugsById(recipeHasDrugsId);
        recipeHasDrugs.setDone(true);
        RecipeHasDrugs recipeHasDrugs1 = recipeHasDrugsService.update(recipeHasDrugs);
        recipeHasDrugsService.checkAllDrugsInRecipeDone(recipeHasDrugs1.getRecipeId());
        return recipeHasDrugs1;
    }


}
