package aptekaproj.services;

import aptekaproj.controllers.repository.IRecipeProgressStatusRepository;
import aptekaproj.models.RecipeProgressStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class RecipeProgressStatusService {

    @Autowired
    private IRecipeProgressStatusRepository recipeProgressStatusRepository;

    public RecipeProgressStatus getRecipeProgressStatusByName(String name){
        List<RecipeProgressStatus> recipeProgressStatuses = (List<RecipeProgressStatus>)recipeProgressStatusRepository.findAll();
        for(RecipeProgressStatus recipeProgressStatus : recipeProgressStatuses){
            if(recipeProgressStatus.getName() == name)
                return recipeProgressStatus;
        }
        return new RecipeProgressStatus();
    }
}
