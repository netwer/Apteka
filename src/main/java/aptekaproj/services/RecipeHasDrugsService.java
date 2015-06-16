package aptekaproj.services;

import aptekaproj.helpers.Enums.ProgressStatusEnum;
import aptekaproj.viewModels.DrugViewModel;
import aptekaproj.viewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IRecipesHasDrugsRepository;
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
public class RecipeHasDrugsService {

    @Autowired
    private IRecipesHasDrugsRepository recipesHasDrugsRepository;

    @Autowired
    private RecipeService recipeService;

    public void SaveRecipeHasDrugs(RecipeHasDrugs recipeHasDrugs) {
        recipesHasDrugsRepository.save(recipeHasDrugs);
    }

    public void UpdateRecipeHasDrugs(RecipeViewModel recipeViewModel, Recipe recipe){
        for (DrugViewModel drugViewModel : recipeViewModel.drugViewModels){
            RecipeHasDrugs recipeHasDrugs = new RecipeHasDrugs();
            if(drugViewModel.recipesHasDrugsId != null){
                recipeHasDrugs = recipesHasDrugsRepository.findOne(drugViewModel.recipesHasDrugsId);
                if(recipeHasDrugs == null)
                    continue;
                recipeHasDrugs.setId(drugViewModel.recipesHasDrugsId);
            }
            recipeHasDrugs.setCount(drugViewModel.drugCount);
            recipeHasDrugs.setDrugId(drugViewModel.drugId);
            recipeHasDrugs.setRecipeId(recipe.getId());
            recipeHasDrugs.setDone(false);
            recipeHasDrugs.setProgressStatusId(recipe.getRecipeProgressStatusId());
            SaveRecipeHasDrugs(recipeHasDrugs);
        }
    }

    public List<RecipeHasDrugs> GetAllRecipesHasDrugs() {
        return (List<RecipeHasDrugs>) recipesHasDrugsRepository.findAll();
    }

    public RecipeHasDrugs getRecipeHasDrugsById(int recipeHasDrugsId) {
        return recipesHasDrugsRepository.findOne(recipeHasDrugsId);
    }

    public RecipeHasDrugs update(RecipeHasDrugs recipeHasDrugs) {
        return recipesHasDrugsRepository.save(recipeHasDrugs);
    }

    public void checkAllDrugsInRecipeDone(int recipeId) {
        List<RecipeHasDrugs> recipeHasDrugs = GetAllRecipesHasDrugs();
        int countRecipesDone = 0;
        int countDrugsInRecipe = 0;

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugs){
            if(recipeHasDrugs1.getRecipeId()==recipeId){
                countDrugsInRecipe++;
                if(recipeHasDrugs1.getDone()){
                    countRecipesDone++;
                }
            }
        }

        if (countRecipesDone == countDrugsInRecipe){
            recipeService.ChangeStatus(recipeId, ProgressStatusEnum.PACKAGE.toString().toUpperCase());
        }
    }

    public RecipeHasDrugs getRecipeHasDrugsByRecipeAndDrugIds(int recipeId, int drugId) {
        List<RecipeHasDrugs> recipeHasDrugsList = GetAllRecipesHasDrugs();
        RecipeHasDrugs recipeHasDrugs = new RecipeHasDrugs();

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugsList){
            if(recipeHasDrugs1.getRecipeId() == recipeId && recipeHasDrugs1.getDrugId() == drugId){
                recipeHasDrugs = recipeHasDrugs1;
                break;
            }
        }

        return recipeHasDrugs;
    }
}
