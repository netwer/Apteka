package aptekaproj.controllers;

import aptekaproj.viewModels.*;
import aptekaproj.models.Recipe;
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
@RequestMapping("/api/pharmacies")
public class PharmaciesController {

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    /**
     * Get apothecary list for appointment to the manufacturer
     * @param pharmacy_id - id of pharmacy
     * @return List<UserViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacists/{pharmacy_id}", method = RequestMethod.GET)
    public List<UserViewModel> getPharmacists(@PathVariable("pharmacy_id") int pharmacy_id) {
        return pharmacyStaffService.getPharmacists(pharmacy_id);
    }

    //todo test
    /**
     * Get recipes for pharmacist by apothecary id and status
     * url: localhost:8443/Pharmacies/pharmacies/2/recipes
     * @param status - recipe status
     * @return List<Recipe>
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/{pharmacy_id}/recipes", method = RequestMethod.GET)
    public List<RecipeViewModel> getRecipes(@PathVariable("pharmacy_id") int pharmacy_id,
                                   @RequestParam("status") String status){

        return recipeService.getRecipesForPharmacyByStatus(pharmacy_id, status);
    }

    //todo test
    /**
     * Get recipe info for pharmacist by recipe ID
     * @param recipeId - recipe id
     * @return RecipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/recipe/{recipeId}", method = RequestMethod.GET)
    public RecipeViewModel getRecipe(@PathVariable("recipeId") int recipeId) {
        return recipeService.getRecipe(recipeId);
    }

    //todo test
    /**
     * updateRecipe recipe by pharmacist - the input parameter is ViewModel for recipe
     * @param recipeViewModel - recipe view model
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/recipe/update", method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel) {
        recipeService.updateRecipe(recipeViewModel);
    }

    /**
     * Change recipe status - the input parameter is ViewModel with recipe ID and new status
     * @param postViewModel - post view model
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/recipe/change", method = RequestMethod.POST)
    public void changeStatus(@RequestBody PostViewModel postViewModel) {//,@RequestBody String status){
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
    @RequestMapping(value = "/pharmacies/order/{pharmacistId}/{recipeId}", method = RequestMethod.GET)
    public OrderMissingViewModel getOrderMissing(@PathVariable("pharmacistId") int pharmacistId,
                                                 @PathVariable("recipeId")  int recipeId ){
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
    @RequestMapping(value = "/pharmacies/order/", method = RequestMethod.POST)
    public void orderToProduce(@RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {//need viewModel!!!
        concreteDrugService.DrugsToProduce(recipeDrugWithPharmacistViewModel);
    }

    //todo test
    /**
     * Updates the links between drugs and apothecaries who prepare them
     * sounds funny :)
     * @param recipeDrugWithPharmacistViewModel recipe drug with pharmacist view model
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/drugs/update", method = RequestMethod.PUT)
    public void updateOrderToProduce(@RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
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
