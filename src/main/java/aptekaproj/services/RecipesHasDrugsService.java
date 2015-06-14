package aptekaproj.services;

import aptekaproj.viewModels.DrugViewModel;
import aptekaproj.viewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IRecipesHasDrugsRepository;
import aptekaproj.models.Recipe;
import aptekaproj.models.RecipeHasDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class RecipesHasDrugsService {

    @Autowired
    private IRecipesHasDrugsRepository recipesHasDrugsRepository;

    public void SaveRecipeHasDrugs(RecipeHasDrugs recipeHasDrugs) {
        recipesHasDrugsRepository.save(recipeHasDrugs);
    }

    public void UpdateRecipeHasDrugs(RecipeViewModel recipeViewModel, Recipe recipe){
        for (DrugViewModel drugViewModel : recipeViewModel.drugViewModelList){
            RecipeHasDrugs recipeHasDrugs = new RecipeHasDrugs();
            if(drugViewModel.RecipesHasDrugsId != null){
                recipeHasDrugs = recipesHasDrugsRepository.findOne(drugViewModel.RecipesHasDrugsId);
                if(recipeHasDrugs == null)
                    continue;
                recipeHasDrugs.setId(drugViewModel.RecipesHasDrugsId);
            }
            recipeHasDrugs.setCount(drugViewModel.DrugCount);
            recipeHasDrugs.setDrugId(drugViewModel.DrugId);
            recipeHasDrugs.setRecipeId(recipe.getId());
            recipeHasDrugs.setDone(false);
            recipeHasDrugs.setProgressStatusId(recipe.getRecipeProgressStatusId());
            SaveRecipeHasDrugs(recipeHasDrugs);
        }
    }

    public List<RecipeHasDrugs> GetAllRecipesHasDrugs() {
        return (List<RecipeHasDrugs>) recipesHasDrugsRepository.findAll();
    }
}
