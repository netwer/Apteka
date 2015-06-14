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
public class DrugsService {

    @Autowired
    private IDrugsRepository drugsRepository;

    @Autowired
    private RecipesHasDrugsService recipesHasDrugsService;

    @Autowired
    private ConcreteDrugsService concreteDrugsService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    @Autowired
    private IngredientsService ingredientsService;

    public List<Drug> GetDrugs(){
        return (List<Drug>)drugsRepository.findAll();
    }

    public List<DrugViewModel> GetDrugsForRecipe(Integer recipeId) {
        List<RecipeHasDrugs> recipeHasDrugses = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugViewModel> drugViewModels = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs : recipeHasDrugses){
            if(recipeHasDrugs.getRecipeId() == recipeId){
                DrugViewModel drugViewModel = new DrugViewModel();
                Drug drug = drugsRepository.findOne(recipeHasDrugs.getDrugId());

                if(drug == null)
                    continue;

                drugViewModel.RecipesHasDrugsId = recipeHasDrugs.getId();
                drugViewModel.DrugId = recipeHasDrugs.getDrugId();
                drugViewModel.DrugCount = recipeHasDrugs.getCount();
                drugViewModel.DrugName = drug.getName();
                drugViewModel.AvailabilityDate = concreteDrugsService.GetAvailabilityDrugDate(recipeId,drug.getId());
                drugViewModel.NeedsToProduce = drug.getNeedToProduce();

                drugViewModels.add(drugViewModel);
            }
        }

        return drugViewModels;
    }

    public List<DrugViewModel> GetDrugsNeedsToProduce(int recipeId) {
        List<RecipeHasDrugs> recipeHasDrugs = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugViewModel> drugViewModels = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugs){
            if(recipeHasDrugs1.getRecipeId() == recipeId){
                Drug drug = drugsRepository.findOne(recipeHasDrugs1.getDrugId());
                if(drug != null && drug.getNeedToProduce() == true) {
                    DrugViewModel drugViewModel = new DrugViewModel();

                    drugViewModel.NeedsToProduce = drug.getNeedToProduce();
                    drugViewModel.DrugName = drug.getName();
                    drugViewModel.DrugCount = recipeHasDrugs1.getCount();
                    drugViewModel.DrugId = drug.getId();
                    drugViewModel.RecipesHasDrugsId = recipeHasDrugs1.getId();

                    drugViewModels.add(drugViewModel);
                }
            }
        }

        return drugViewModels;
    }

    public Drug GetDrugById(int drugId) {
        return drugsRepository.findOne(drugId);
    }

    public List<DrugToProduceViewModel> getDrugsToProduce(int pharmacyStaffId) {
        List<ConcreteDrug> concreteDrugList = concreteDrugsService.getAllConcreteDrugs();
        List<DrugToProduceViewModel> drugsToProduce = new ArrayList<>();

        for (ConcreteDrug concreteDrug : concreteDrugList){
            Recipe recipe = recipeService.GetRecipeById(concreteDrug.getRecipeId());

            if(recipe == null)
                continue;

            String recipeStatus = recipeProgressStatusService.GetRecipeProgressStatusById(recipe.getRecipeProgressStatusId()).getName();
            String tt = ProgressStatusEnum.IN_PROCESS.toString().toUpperCase();

            if(concreteDrug.getPharmacyStaffId() == pharmacyStaffId && recipeStatus.toUpperCase().equals(tt)){
                DrugToProduceViewModel drugToProduceViewModel = new DrugToProduceViewModel();
                drugToProduceViewModel.drugViewModel = getDrug(concreteDrug.getRecipeId(),concreteDrug.getDrugId());
                drugToProduceViewModel.ingredientInDrugViewModels = ingredientsService.getIngredientsForDrug(concreteDrug.getDrugId(),concreteDrug.getId());
                drugsToProduce.add(drugToProduceViewModel);
            }
        }

        return drugsToProduce;
    }

    private DrugViewModel getDrug(int recipeId, int drugId){
        DrugViewModel drugViewModel = new DrugViewModel();
        List<RecipeHasDrugs> recipeHasDrugs = recipesHasDrugsService.GetAllRecipesHasDrugs();

        for (RecipeHasDrugs recipeHasDrug : recipeHasDrugs){
            if(recipeHasDrug.getRecipeId() == recipeId && recipeHasDrug.getDrugId() == drugId){
                Drug drug = GetDrugById(drugId);
                if (drug == null)
                    continue;

                drugViewModel.DrugId = drug.getId();
                drugViewModel.DrugName = drug.getName();
                drugViewModel.DrugCount = recipeHasDrug.getCount();
                drugViewModel.RecipesHasDrugsId = recipeHasDrug.getId();
                drugViewModel.NeedsToProduce = drug.getNeedToProduce();
                drugViewModel.AvailabilityDate = concreteDrugsService.GetAvailabilityDrugDate(recipeId,drug.getId());
                return drugViewModel;
            }
        }

        return drugViewModel;
    }
}
