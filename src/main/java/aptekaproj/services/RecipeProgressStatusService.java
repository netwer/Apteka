package aptekaproj.services;

import aptekaproj.controllers.repository.IRecipeProgressStatusRepository;
import aptekaproj.models.RecipeProgressStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by Admin on 28.05.2015.
 */
@Service
public class RecipeProgressStatusService {

    @Autowired
    private IRecipeProgressStatusRepository recipeProgressStatusRepository;

    public RecipeProgressStatus getRecipeProgressStatusByName(String name){
        List<RecipeProgressStatus> recipeProgressStatuses = (List<RecipeProgressStatus>)recipeProgressStatusRepository.findAll();
        RecipeProgressStatus currentRecipeProgressStatus = new RecipeProgressStatus();
        for(RecipeProgressStatus recipeProgressStatus : recipeProgressStatuses){
            if(Objects.equals(recipeProgressStatus.getName().toUpperCase(), name.toUpperCase())) {
                currentRecipeProgressStatus = recipeProgressStatus;
            }
        }
        return currentRecipeProgressStatus;
    }

    public RecipeProgressStatus getRecipeProgressStatusById(int recipeProgressStatusId) {
        return recipeProgressStatusRepository.findOne(recipeProgressStatusId);
    }

    public List<RecipeProgressStatus> getRecipeProgressStatuses() {
        return (List<RecipeProgressStatus>)recipeProgressStatusRepository.findAll();
    }
}
