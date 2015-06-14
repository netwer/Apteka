package aptekaproj.services;

import aptekaproj.ViewModels.DrugToProduceViewModel;
import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.controllers.repository.IDrugsRepository;
import aptekaproj.helpers.Enums.ProgressStatusEnum;
import aptekaproj.models.ConcreteDrugs;
import aptekaproj.models.Drugs;
import aptekaproj.models.Recipes;
import aptekaproj.models.RecipesHasDrugs;
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

    public List<Drugs> GetDrugs(){
        return (List<Drugs>)drugsRepository.findAll();
    }

    public List<DrugsViewModel> GetDrugsForRecipe(Integer recipeId) {
        List<RecipesHasDrugs> recipesHasDrugses = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugsViewModel> drugsViewModels = new ArrayList<>();

        for (RecipesHasDrugs recipesHasDrugs : recipesHasDrugses){
            if(recipesHasDrugs.getRecipe_id() == recipeId){
                DrugsViewModel drugsViewModel = new DrugsViewModel();
                Drugs drug = drugsRepository.findOne(recipesHasDrugs.getDrug_id());

                if(drug == null)
                    continue;

                drugsViewModel.RecipesHasDrugsId = recipesHasDrugs.getId();
                drugsViewModel.DrugId = recipesHasDrugs.getDrug_id();
                drugsViewModel.DrugCount = recipesHasDrugs.getCount();
                drugsViewModel.DrugName = drug.getName();
                drugsViewModel.AvailabilityDate = concreteDrugsService.GetAvailabilityDrugDate(recipeId,drug.getId());
                drugsViewModel.NeedsToProduce = drug.getNeedsToProduce();

                drugsViewModels.add(drugsViewModel);
            }
        }

        return drugsViewModels;
    }

    public List<DrugsViewModel> GetDrugsNeedsToProduce(int recipeId) {
        List<RecipesHasDrugs> recipesHasDrugs = recipesHasDrugsService.GetAllRecipesHasDrugs();
        List<DrugsViewModel> drugsViewModels = new ArrayList<>();

        for (RecipesHasDrugs recipesHasDrugs1 : recipesHasDrugs){
            if(recipesHasDrugs1.getRecipe_id() == recipeId){
                Drugs drug = drugsRepository.findOne(recipesHasDrugs1.getDrug_id());
                if(drug != null && drug.getNeedsToProduce() == true) {
                    DrugsViewModel drugsViewModel = new DrugsViewModel();

                    drugsViewModel.NeedsToProduce = drug.getNeedsToProduce();
                    drugsViewModel.DrugName = drug.getName();
                    drugsViewModel.DrugCount = recipesHasDrugs1.getCount();
                    drugsViewModel.DrugId = drug.getId();
                    drugsViewModel.RecipesHasDrugsId = recipesHasDrugs1.getId();

                    drugsViewModels.add(drugsViewModel);
                }
            }
        }

        return drugsViewModels;
    }

    public Drugs GetDrugById(int drugId) {
        return drugsRepository.findOne(drugId);
    }

    public List<DrugToProduceViewModel> getDrugsToProduce(int pharmacyStaffId) {
        List<ConcreteDrugs> concreteDrugsList = concreteDrugsService.getAllConcreteDrugs();
        List<DrugToProduceViewModel> drugsToProduce = new ArrayList<>();

        for (ConcreteDrugs concreteDrug : concreteDrugsList){
            Recipes recipe = recipeService.GetRecipeById(concreteDrug.getRecipeId());

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
        List<RecipesHasDrugs> recipesHasDrugs = recipesHasDrugsService.GetAllRecipesHasDrugs();

        for (RecipesHasDrugs recipeHasDrug : recipesHasDrugs){
            if(recipeHasDrug.getRecipe_id() == recipeId && recipeHasDrug.getDrug_id() == drugId){
                Drugs drug = GetDrugById(drugId);
                if (drug == null)
                    continue;

                drugViewModel.DrugId = drug.getId();
                drugViewModel.DrugName = drug.getName();
                drugViewModel.DrugCount = recipeHasDrug.getCount();
                drugViewModel.RecipesHasDrugsId = recipeHasDrug.getId();
                drugViewModel.NeedsToProduce = drug.getNeedsToProduce();
                drugViewModel.AvailabilityDate = concreteDrugsService.GetAvailabilityDrugDate(recipeId,drug.getId());
                return drugViewModel;
            }
        }

        return drugViewModel;
    }
}
