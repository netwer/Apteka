package aptekaproj.services;

import aptekaproj.ViewModels.DrugsViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.controllers.repository.IRecipesRepository;
import aptekaproj.models.RecipeProgressStatus;
import aptekaproj.models.Recipes;
import aptekaproj.models.RecipesHasDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    private DrugsService drugsService;

    @Autowired
    private RecipesHasDrugsService recipesHasDrugsService;

    public void Save(RecipeViewModel recipeViewModel){
        //todo check!
        RecipeProgressStatus recipeProgressStatus = recipeProgressStatusService.getRecipeProgressStatusByName("СОЗДАН");
        Recipes recipes = new Recipes();
        recipes.setTitle(recipeViewModel.RecipeTitle);
        recipes.setRecipeProgressStatusId(recipeProgressStatus.getId());
        recipes.setPharmacyId(recipeViewModel.PharmacyId);
        recipes.setCreated_at(new Date());

        //todo CHECK!
        Recipes recipes1 = recipesRepository.save(recipes);
        for (DrugsViewModel drugsViewModel : recipeViewModel.drugsViewModelList){
            RecipesHasDrugs recipesHasDrugs = new RecipesHasDrugs();
            recipesHasDrugs.setCount(drugsViewModel.DrugCount);
            recipesHasDrugs.setDrug_id(drugsViewModel.DrugId);
            recipesHasDrugs.setRecipe_id(recipes1.getId());
            recipesHasDrugs.setDone(false);
            recipesHasDrugsService.Save(recipesHasDrugs);
        }
    }
}
