package aptekaproj.controllers;

import aptekaproj.models.User;
import aptekaproj.services.UserService;
import aptekaproj.viewModels.PatientRecipeViewModel;
import aptekaproj.models.RecipeProgressStatus;
import aptekaproj.services.RecipeProgressStatusService;
import aptekaproj.services.RecipeService;
import aptekaproj.viewModels.UserViewModel;
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
    private UserService userService;

    /**
     * Get recipe for patient
     * http://localhost:8443/api/patient/{patientId}/recipes
     * @param patientId
     * @return PatientRecipeViewModel
     */
    @RequestMapping(value = "/{patientId}/recipes", method = RequestMethod.GET)
    public @ResponseBody
    List<PatientRecipeViewModel> getRecipes(@PathVariable int patientId){
        if(patientId <= 0)
            return new ArrayList<>();
        return recipeService.getRecipesForPatient(patientId);
    }

    @RequestMapping(value = "/{patientId}", method = RequestMethod.GET)
    public @ResponseBody
     User getPatient(@PathVariable int patientId){
        if(patientId <= 0)
            return null;
        return userService.getPublicUserById(patientId);
    }
}
