package aptekaproj.controllers;

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
        return recipeService.getRecipesForPharmacyByStatus(pharmacistId, statusId);
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

    /*//todo test
    *//**
     * Get List drugs for produce and List apothecaries in apothecary
     * Need one more parameter - apothecary id
     * @param pharmacistId - pharmacist id
     * @param recipeId - recipe id
     * @return OrderMissingViewModel
     *//*
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/order/{recipeId}", method = RequestMethod.GET)
    public OrderMissingViewModel getOrderMissing(@PathVariable("pharmacistId") int pharmacistId,
                                                 @PathVariable("recipeId")  int recipeId ){
        if(pharmacistId <= 0 || recipeId <= 0)
            return new OrderMissingViewModel();
        return recipeService.getOrderMissing(pharmacistId, recipeId);
    }*/

    /*//todo test
    *//**
     * updateRecipe recipe by pharmacist - the input parameter is ViewModel for recipe
     * @param recipeViewModel - recipe view model
     *//*
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipe/update", method = RequestMethod.POST)
    public void updateRecipe(@PathVariable("pharmacistId") int pharmacistId,
                             @RequestBody RecipeViewModel recipeViewModel) {
        if(pharmacistId <= 0 || recipeViewModel == null)
            return;
        recipeService.updateRecipe(recipeViewModel);
    }*/

    /**
     * Get apothecary list for appointment to the manufacturer
     * @param pharmacistId - id of pharmacy
     * @return List<UserViewModel>
     *//*
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/pharmacists", method = RequestMethod.GET)
    public List<UserViewModel> getPharmacists(@PathVariable("pharmacistId") int pharmacistId) {
        return pharmacyStaffService.getPharmacists(pharmacistId);
    }*/

    /*//todo test
    *//**
     * Get recipe info for pharmacist by recipe ID
     * @param recipeId - recipe id
     * @return RecipeViewModel
     *//*
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipe/{recipeId}", method = RequestMethod.GET)
    public RecipeViewModel getRecipe(@PathVariable("pharmacistId") int pharmacistId,
                                     @PathVariable("recipeId") int recipeId) {
        if(pharmacistId <= 0 || recipeId <= 0)
            return new RecipeViewModel();
        return recipeService.getRecipe(recipeId);
    }*/
}
