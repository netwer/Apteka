package aptekaproj.services;

import aptekaproj.helpers.enums.ProgressStatusEnum;
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

    public void saveRecipeHasDrugs(RecipeHasDrugs recipeHasDrugs) {
        recipesHasDrugsRepository.save(recipeHasDrugs);
    }

    public void updateRecipeHasDrugs(RecipeViewModel recipeViewModel, Recipe recipe){
        for (DrugViewModel drugViewModel : recipeViewModel.drugs){
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
            saveRecipeHasDrugs(recipeHasDrugs);
        }
    }

    public List<RecipeHasDrugs> getAllRecipesHasDrugs() {
        return (List<RecipeHasDrugs>) recipesHasDrugsRepository.findAll();
    }

    public RecipeHasDrugs getRecipeHasDrugsById(int recipeHasDrugsId) {
        return recipesHasDrugsRepository.findOne(recipeHasDrugsId);
    }

    public RecipeHasDrugs update(RecipeHasDrugs recipeHasDrugs) {
        return recipesHasDrugsRepository.save(recipeHasDrugs);
    }

    public void checkAllDrugsInRecipeDone(int recipeId) {
        List<RecipeHasDrugs> recipeHasDrugs = getAllRecipesHasDrugs();
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
            recipeService.changeStatus(recipeId, ProgressStatusEnum.PACKAGE.toString().toUpperCase());
        }
    }

    public RecipeHasDrugs getRecipeHasDrugsByRecipeAndDrugIds(int recipeId, int drugId) {
        List<RecipeHasDrugs> recipeHasDrugsList = getAllRecipesHasDrugs();
        RecipeHasDrugs recipeHasDrugs = new RecipeHasDrugs();

        for (RecipeHasDrugs recipeHasDrugs1 : recipeHasDrugsList){
            if(recipeHasDrugs1.getRecipeId() == recipeId && recipeHasDrugs1.getDrugId() == drugId){
                recipeHasDrugs = recipeHasDrugs1;
                break;
            }
        }

        return recipeHasDrugs;
    }

    public void saveRecipeHasDrugs(List<DrugViewModel> drugsInRecipe, int recipeId) {
        for (DrugViewModel drugViewModel : drugsInRecipe ){
            RecipeHasDrugs recipeHasDrugs = new RecipeHasDrugs();
            recipeHasDrugs.setRecipeId(recipeId);
            recipeHasDrugs.setCount(drugViewModel.drugCount);
            recipeHasDrugs.setDrugId(drugViewModel.drugId);
            recipeHasDrugs.setDone(false);
            recipeHasDrugs.setProgressStatusId(1);

            saveRecipeHasDrugs(recipeHasDrugs);
        }
    }

    public List<RecipeHasDrugs> getRecipesHasDrugsByRecipeId(int recipeId) {
        List<RecipeHasDrugs>  recipeHasDrugsList = getAllRecipesHasDrugs();
        List<RecipeHasDrugs> recipeHasDrugsList1 = new ArrayList<>();

        for (RecipeHasDrugs recipeHasDrugs : recipeHasDrugsList){
            if(recipeHasDrugs.getRecipeId() == recipeId){
                recipeHasDrugsList1.add(recipeHasDrugs);
            }
        }

        return recipeHasDrugsList1;
    }

    public void deleteRecipeHasDrugs(RecipeHasDrugs recipeHasDrugs) {
        recipesHasDrugsRepository.delete(recipeHasDrugs);
    }
}
