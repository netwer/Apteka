package aptekaproj.controllers;

import aptekaproj.ViewModels.PatientRecipeViewModel;
import aptekaproj.models.RecipeProgressStatus;
import aptekaproj.services.RecipeProgressStatusService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 */
@Controller
@RequestMapping("/Patient")
public class PatientController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    //Получение рецептов для пациента GET /
    //http://localhost:8443/Patient/?userId=4
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    List<PatientRecipeViewModel> Patients(@PathVariable int userId){
        return recipeService.GetRecipesForPatient(userId);
    }

    //Получение статусов GET /statuses
    //http://localhost:8443/Patient/statuses
    @RequestMapping(value = "/statuses", method = RequestMethod.GET)
    public @ResponseBody
    List<RecipeProgressStatus> getRecipeStatuses(){
        return recipeProgressStatusService.GetRecipeProgressStatuses();
    }

}
