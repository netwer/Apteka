package aptekaproj.controllers;

import aptekaproj.models.PharmacyStaff;
import aptekaproj.viewModels.*;
import aptekaproj.services.ConcreteDrugService;
import aptekaproj.services.PharmacyStaffService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 * Controller for pharmacies user (other name - provizor)
 */
@Controller
@PreAuthorize("hasAuthority('ROLE_PHARMACIST')")
@RequestMapping("/api/pharmacist")
public class PharmaciesController {

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipes", method = RequestMethod.GET)
    public List<RecipeViewModel> getRecipes(@PathVariable("pharmacistId") int pharmacistId,
                                            @RequestParam("status") int statusId){
        if(pharmacistId <= 0 || statusId <= 0)
            return new ArrayList<>();
        PharmacyStaff pharmacyStaff = pharmacyStaffService.getApothecariesByPharmacistId(pharmacistId);
        return recipeService.getRecipesForPharmacyByStatus(pharmacyStaff.getPharmacyId(), statusId);
    }

    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/staff",method = RequestMethod.GET)
    public List<ApothecaryViewModel> getApothecariesStaffs(@PathVariable("pharmacistId") int pharmacistId){
        if(pharmacistId <= 0)
            return new ArrayList<>();
        return pharmacyStaffService.getApothecariesStaffs(pharmacistId);
    }

    @ResponseBody
    @RequestMapping(value = "/concreteDrugs", method = RequestMethod.POST)
    public void drugsToProduce(@RequestBody List<RecipeDrugWithPharmacistViewModel> recipeDrugWithPharmacistViewModel) {
        if(recipeDrugWithPharmacistViewModel == null || recipeDrugWithPharmacistViewModel.size() == 0)
            return;
        concreteDrugService.drugsToProduce(recipeDrugWithPharmacistViewModel);
    }

    @ResponseBody
    @RequestMapping(value = "/concreteDrugs", method = RequestMethod.PUT)
    public void updateDrugToProduce(@RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
        if(recipeDrugWithPharmacistViewModel == null)
            return;
        concreteDrugService.updateDrugToProduce(recipeDrugWithPharmacistViewModel);
    }

    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipes/{recipeId}", method = RequestMethod.PUT)
    public void changeStatus(@PathVariable("pharmacistId") int pharmacistId,
                             @PathVariable("recipeId") int recipeId,
                             @RequestBody int statusId) {
        if(pharmacistId <= 0 || recipeId <= 0 || statusId <= 0)
            return;
        recipeService.changeStatus(recipeId, statusId);
    }
}
