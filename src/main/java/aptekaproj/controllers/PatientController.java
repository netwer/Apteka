package aptekaproj.controllers;

import aptekaproj.viewModels.PatientRecipeViewModel;
import aptekaproj.models.RecipeProgressStatus;
import aptekaproj.services.RecipeProgressStatusService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 * Controller for patient user
 */
@Controller
@PreAuthorize("hasAuthority('ROLE_PATIENT')")
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeProgressStatusService recipeProgressStatusService;

    /**
     * Get recipe for patient
     * http://localhost:8443/Patient/?userId=4
     * @param patientId
     * @return PatientRecipeViewModel
     */
    @RequestMapping(value = "/{patientId}", method = RequestMethod.GET)
    public @ResponseBody
    List<PatientRecipeViewModel> getRecipes(@PathVariable int patientId){
        if(patientId <= 0)
            return new ArrayList<>();
        return recipeService.getRecipesForPatient(patientId);
    }

    /**
     * Get statuses
     * http://localhost:8443/Patient/statuses
     * @return
     */
    @RequestMapping(value = "/{patientId}/statuses", method = RequestMethod.GET)
    public @ResponseBody
    List<RecipeProgressStatus> GetRecipeStatuses(@PathVariable int patientId){
        if(patientId <= 0)
            return new ArrayList<>();
        return recipeProgressStatusService.getRecipeProgressStatuses();
    }

}
