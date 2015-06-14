package aptekaproj.controllers;

import aptekaproj.viewModels.*;
import aptekaproj.models.Recipe;
import aptekaproj.services.ConcreteDrugsService;
import aptekaproj.services.PharmacyStaffService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 */

//Provizor
@Controller
@RequestMapping("/Pharmacies")
public class PharmaciesController {

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ConcreteDrugsService concreteDrugsService;

    //Get apotekary list for appointment to the manufacturer
    @ResponseBody
    @RequestMapping(value = "/pharmacists", method = RequestMethod.GET)
    public List<UserViewModel> getPharmacists(@RequestParam(value = "pharmacy", required = true) int pharmacy_id) {
        return pharmacyStaffService.GetPharmacists(pharmacy_id);
    }

    //Get recipes for pharmacist by apothecary id and status
    //url: localhost:8443/Pharmacies/pharmacies/2/recipes
    //todo test
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes", method = RequestMethod.GET)
    public List<Recipe> getRecipes(@RequestParam(value = "s", required = true) String status) {
        return recipeService.GetRecipesForPharmacyByStatus(2, status);
    }

    //Get recipe info for pharmacist by recipe ID
    //todo test
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/", method = RequestMethod.GET)
    public RecipeViewModel getRecipe(@RequestParam(value = "id", required = true) int id) {
        return recipeService.GetRecipe(id);
    }

    //Update recipe by pharmacist - the input parameter is ViewModel for recipe
    //todo test
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/", method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel) {
        recipeService.Update(recipeViewModel);
    }

    //Change recipe status - the input parameter is ViewModel with recipe ID and new status
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/", method = RequestMethod.POST)
    public void changeStatus(@RequestBody PostViewModel postViewModel) {//,@RequestBody String status){
        recipeService.ChangeStatus(postViewModel.id, postViewModel.status);
    }

    //Get List drugs for produce and List apothecaries in apothecary
    //Need one more parameter - apothecary id
    //todo test
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/order", method = RequestMethod.GET)
    public OrderMissingViewModel getOrderMissing(@RequestParam(value = "pharmacistId", required = true) int pharmacistId,
                                                 @RequestParam(value = "recipeId", required = true) int recipeId) {
        return recipeService.GetOrderMissing(pharmacistId, recipeId);
    }

    //Implementation of logic "send medicine to manufacturing"
    //Create instance for ConcreteDrugs and ConcreteIngredients
    //todo test
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/drugs/", method = RequestMethod.POST)
    public void orderToProduce(@RequestBody RecipeDrugWithPharmacistViewModel recipeDrugWithPharmacistViewModel) {//need viewModel!!!
        concreteDrugsService.DrugsToProduce(recipeDrugWithPharmacistViewModel);
    }

    //Updates the links between drugs and apothecaries who prepare them
    //sounds funny :)
    //todo test
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/drugs/", method = RequestMethod.PUT)
    public void updateOrderToProduce(@RequestBody List<DrugWithPharmacistViewModel> drugWithPharmacists) {
        concreteDrugsService.UpdateDrugsToProduce(drugWithPharmacists);
    }


    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipe/{id}", method = RequestMethod.GET)
    public RecipeViewModel getRecipeInfo(@PathVariable int id) {
        return recipeService.GetRecipe(id);
    }
}
