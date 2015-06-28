package aptekaproj.controllers;

import aptekaproj.viewModels.*;
import aptekaproj.services.ConcreteDrugService;
import aptekaproj.services.PharmacyStaffService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get apothecary list for appointment to the manufacturer
     * @param pharmacistId - id of pharmacy
     * @return List<UserViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/pharmacists", method = RequestMethod.GET)
    public List<UserViewModel> getPharmacists(@PathVariable("pharmacistId") int pharmacistId) {
        return pharmacyStaffService.getPharmacists(pharmacistId);
    }

    //todo test
    /**
     * Get recipes for pharmacist by apothecary id and status
     * url: localhost:8443/Pharmacies/pharmacies/2/recipes
     * @param status - recipe status
     * @return List<Recipe>
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipes", method = RequestMethod.GET)
    public List<RecipeViewModel> getRecipes(@PathVariable("pharmacistId") int pharmacistId,
                                   @RequestParam("status") String status){

        return recipeService.getRecipesForPharmacyByStatus(pharmacistId, status);
    }

    //todo test
    /**
     * Get recipe info for pharmacist by recipe ID
     * @param recipeId - recipe id
     * @return RecipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipe/{recipeId}", method = RequestMethod.GET)
    public RecipeViewModel getRecipe(@PathVariable("pharmacistId") int pharmacistId,
                                     @PathVariable("recipeId") int recipeId) {
        if(pharmacistId <= 0 || recipeId <= 0)
            return new RecipeViewModel();
        return recipeService.getRecipe(recipeId);
    }

    //todo test
    /**
     * updateRecipe recipe by pharmacist - the input parameter is ViewModel for recipe
     * @param recipeViewModel - recipe view model
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipe/update", method = RequestMethod.POST)
    public void updateRecipe(@PathVariable("pharmacistId") int pharmacistId,
                             @RequestBody RecipeViewModel recipeViewModel) {
        if(pharmacistId <= 0 || recipeViewModel == null)
            return;
        recipeService.updateRecipe(recipeViewModel);
    }

    /**
     * Change recipe status - the input parameter is ViewModel with recipe ID and new status
     * @param postViewModel - post view model
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/recipe/change", method = RequestMethod.POST)
    public void changeStatus(@PathVariable("pharmacistId") int pharmacistId,
                             @RequestBody PostViewModel postViewModel) {
        if(pharmacistId <= 0 || postViewModel == null)
            return;
        recipeService.changeStatus(postViewModel.id, postViewModel.status);
    }

    //todo test
    /**
     * Get List drugs for produce and List apothecaries in apothecary
     * Need one more parameter - apothecary id
     * @param pharmacistId - pharmacist id
     * @param recipeId - recipe id
     * @return OrderMissingViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/order/{recipeId}", method = RequestMethod.GET)
    public OrderMissingViewModel getOrderMissing(@PathVariable("pharmacistId") int pharmacistId,
                                                 @PathVariable("recipeId")  int recipeId ){
        if(pharmacistId <= 0 || recipeId <= 0)
            return new OrderMissingViewModel();
        return recipeService.getOrderMissing(pharmacistId, recipeId);
    }

    //todo test
    //todo what need to return?
    /**
     * Implementation of logic "send medicine to manufacturing"
     * Create instance for ConcreteDrugs and ConcreteIngredients
     * @param recipeDrugWithPharmacistViewModel recipe drug with pharmacist view model
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/order/save", method = RequestMethod.PUT)
    public void orderToProduce(@PathVariable("pharmacistId") int pharmacistId,
                               @RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {//need viewModel!!!
        if(pharmacistId <= 0 || recipeDrugWithPharmacistViewModel == null)
            return;
        concreteDrugService.DrugsToProduce(recipeDrugWithPharmacistViewModel);
    }

    //todo test
    /**
     * Updates the links between drugs and apothecaries who prepare them
     * sounds funny :)
     * @param recipeDrugWithPharmacistViewModel recipe drug with pharmacist view model
     */
    @ResponseBody
    @RequestMapping(value = "/{pharmacistId}/order/update", method = RequestMethod.POST)
    public void updateOrderToProduce(@PathVariable("pharmacistId") int pharmacistId,
                                     @RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
        if(pharmacistId <= 0 || recipeDrugWithPharmacistViewModel == null)
            return;
        concreteDrugService.updateDrugsToProduce(recipeDrugWithPharmacistViewModel);
    }

    /**
     * excess method
     * @param recipeId - recipe id
     * @return RecipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/recipe1/{recipeId}", method = RequestMethod.GET)
    public RecipeViewModel getRecipeInfo(@PathVariable("recipeId") int recipeId) {
        return recipeService.getRecipe(recipeId);
    }
}
