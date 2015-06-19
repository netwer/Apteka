package aptekaproj.controllers;

import aptekaproj.viewModels.*;
import aptekaproj.models.Recipe;
import aptekaproj.services.ConcreteDrugService;
import aptekaproj.services.PharmacyStaffService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 * Controller for pharmacies user (other name - provizor)
 */
@Controller
@RequestMapping("/Pharmacies")
public class PharmaciesController {

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ConcreteDrugService concreteDrugService;

    /**
     * Get apotekary list for appointment to the manufacturer
     * @param pharmacy_id
     * @return List<UserViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacists", method = RequestMethod.GET)
    public List<UserViewModel> getPharmacists(@RequestParam(value = "pharmacy", required = true) int pharmacy_id) {
        return pharmacyStaffService.getPharmacists(pharmacy_id);
    }

    //todo test
    /**
     * Get recipes for pharmacist by apothecary id and status
     * url: localhost:8443/Pharmacies/pharmacies/2/recipes
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes", method = RequestMethod.GET)
    public List<Recipe> getRecipes(@RequestParam(value = "s", required = true) String status) {
        return recipeService.getRecipesForPharmacyByStatus(2, status);
    }

    //todo test
    /**
     * Get recipe info for pharmacist by recipe ID
     * @param id
     * @return RecipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/", method = RequestMethod.GET)
    public RecipeViewModel getRecipe(@RequestParam(value = "id", required = true) int id) {
        return recipeService.getRecipe(id);
    }

    //todo test
    /**
     * updateRecipe recipe by pharmacist - the input parameter is ViewModel for recipe
     * @param recipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/", method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel) {
        recipeService.updateRecipe(recipeViewModel);
    }

    /**
     * Change recipe status - the input parameter is ViewModel with recipe ID and new status
     * @param postViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/", method = RequestMethod.POST)
    public void changeStatus(@RequestBody PostViewModel postViewModel) {//,@RequestBody String status){
        recipeService.changeStatus(postViewModel.id, postViewModel.status);
    }

    //todo test
    /**
     * Get List drugs for produce and List apothecaries in apothecary
     * Need one more parameter - apothecary id
     * @param pharmacistId
     * @param recipeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/order", method = RequestMethod.GET)
    public OrderMissingViewModel getOrderMissing(@RequestParam(value = "pharmacistId", required = true) int pharmacistId,
                                                 @RequestParam(value = "recipeId", required = true) int recipeId) {
        return recipeService.getOrderMissing(pharmacistId, recipeId);
    }

    //todo test
    /**
     * Implementation of logic "send medicine to manufacturing"
     * Create instance for ConcreteDrugs and ConcreteIngredients
     * @param recipeDrugWithPharmacistViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/drugs/", method = RequestMethod.POST)
    public void orderToProduce(@RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {//need viewModel!!!
        concreteDrugService.DrugsToProduce(recipeDrugWithPharmacistViewModel);
    }

    //todo test
    /**
     * Updates the links between drugs and apothecaries who prepare them
     * sounds funny :)
     * @param recipeDrugWithPharmacistViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/drugs/", method = RequestMethod.PUT)
    public void updateOrderToProduce(@RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {
        concreteDrugService.updateDrugsToProduce(recipeDrugWithPharmacistViewModel);
    }


    /**
     * excess method
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipe/{id}", method = RequestMethod.GET)
    public RecipeViewModel getRecipeInfo(@PathVariable int id) {
        return recipeService.getRecipe(id);
    }
}
