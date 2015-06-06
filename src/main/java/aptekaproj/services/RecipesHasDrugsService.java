package aptekaproj.services;

import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IRecipesHasDrugsRepository;
import aptekaproj.models.Recipes;
import aptekaproj.models.RecipesHasDrugs;
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

    public void Save(RecipesHasDrugs recipesHasDrugs) {
        recipesHasDrugsRepository.save(recipesHasDrugs);
    }

    public void Update(RecipeViewModel recipeViewModel,Recipes recipes){
        for (DrugsViewModel drugsViewModel : recipeViewModel.drugsViewModelList){
            RecipesHasDrugs recipesHasDrugs = new RecipesHasDrugs();
            if(drugsViewModel.RecipesHasDrugsId != null){
                recipesHasDrugs = recipesHasDrugsRepository.findOne(drugsViewModel.RecipesHasDrugsId);
                if(recipesHasDrugs == null)
                    continue;
                recipesHasDrugs.setId(drugsViewModel.RecipesHasDrugsId);
            }
            recipesHasDrugs.setCount(drugsViewModel.DrugCount);
            recipesHasDrugs.setDrug_id(drugsViewModel.DrugId);
            recipesHasDrugs.setRecipe_id(recipes.getId());
            recipesHasDrugs.setDone(false);
            Save(recipesHasDrugs);
        }
    }

    public List<RecipesHasDrugs> getAll() {
        return (List<RecipesHasDrugs>) recipesHasDrugsRepository.findAll();
    }
}
