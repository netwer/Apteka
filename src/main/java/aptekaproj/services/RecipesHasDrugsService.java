package aptekaproj.services;

import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
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
        for (DrugsViewModel drugsViewModel : recipeViewModel.drugsViewModelList){
            RecipeHasDrugs recipeHasDrugs = new RecipeHasDrugs();
            if(drugsViewModel.RecipesHasDrugsId != null){
                recipeHasDrugs = recipesHasDrugsRepository.findOne(drugsViewModel.RecipesHasDrugsId);
                if(recipeHasDrugs == null)
                    continue;
                recipeHasDrugs.setId(drugsViewModel.RecipesHasDrugsId);
            }
            recipeHasDrugs.setCount(drugsViewModel.DrugCount);
            recipeHasDrugs.setDrugId(drugsViewModel.DrugId);
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
