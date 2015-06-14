package aptekaproj.services;

import aptekaproj.ViewModels.DrugToProduceViewModel;
import aptekaproj.ViewModels.DrugsViewModel;
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

    public List<DrugsViewModel> GetDrugsForRecipe(Integer recipeId) {
        List<RecipeHasDrugs> recipeHasDrugses = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugsViewModel> drugsViewModels = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs : recipeHasDrugses){
            if(recipeHasDrugs.getRecipeId() == recipeId){
                DrugsViewModel drugsViewModel = new DrugsViewModel();
                Drug drug = drugsRepository.findOne(recipeHasDrugs.getDrugId());

                if(drug == null)
                    continue;

                drugsViewModel.RecipesHasDrugsId = recipeHasDrugs.getId();
                drugsViewModel.DrugId = recipeHasDrugs.getDrugId();
                drugsViewModel.DrugCount = recipeHasDrugs.getCount();
                drugsViewModel.DrugName = drug.getName();
                drugsViewModel.AvailabilityDate = concreteDrugsService.GetAvailabilityDrugDate(recipeId,drug.getId());
                drugsViewModel.NeedsToProduce = drug.getNeedToProduce();

                drugsViewModels.add(drugsViewModel);
            }
        }

        return drugsViewModels;
    }

    public List<DrugsViewModel> GetDrugsNeedsToProduce(int recipeId) {
        List<RecipeHasDrugs> recipeHasDrugs = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugsViewModel> drugsViewModels = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugs){
            if(recipeHasDrugs1.getRecipeId() == recipeId){
                Drug drug = drugsRepository.findOne(recipeHasDrugs1.getDrugId());
                if(drug != null && drug.getNeedToProduce() == true) {
                    DrugsViewModel drugsViewModel = new DrugsViewModel();

                    drugsViewModel.NeedsToProduce = drug.getNeedToProduce();
                    drugsViewModel.DrugName = drug.getName();
                    drugsViewModel.DrugCount = recipeHasDrugs1.getCount();
                    drugsViewModel.DrugId = drug.getId();
                    drugsViewModel.RecipesHasDrugsId = recipeHasDrugs1.getId();

                    drugsViewModels.add(drugsViewModel);
                }
            }
        }

        return drugsViewModels;
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
                drugToProduceViewModel.drugsViewModel = getDrug(concreteDrug.getRecipeId(),concreteDrug.getDrugId());
                drugToProduceViewModel.ingredientInDrugViewModels = ingredientsService.getIngredientsForDrug(concreteDrug.getDrugId(),concreteDrug.getId());
                drugsToProduce.add(drugToProduceViewModel);
            }
        }

        return drugsToProduce;
    }

    private DrugsViewModel getDrug(int recipeId, int drugId){
        DrugsViewModel drugViewModel = new DrugsViewModel();
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
