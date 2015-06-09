package aptekaproj.controllers;

import aptekaproj.ViewModels.OrderMissingViewModel;
import aptekaproj.ViewModels.PostViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.models.Recipes;
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

    //Get apotekary list for appointment to the manufacturer
    @ResponseBody
    @RequestMapping(value = "/pharmacists",method = RequestMethod.GET)
    //List<Users> GetPharmacists(@PathVariable int pharmacy_id){
    public List<UserViewModel> getPharmacists(@RequestParam(value = "pharmacy",required = true) int pharmacy_id){
        return pharmacyStaffService.GetPharmacists(pharmacy_id);
    }

    //@RequestMapping(value = "/pharmacies/{pharmacy_id}/recipes?s={status}",method = RequestMethod.GET)
    //todo
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes",method = RequestMethod.GET)
    //List<Recipes> getRecipes(@PathVariable("pharmacy_id") int pharmacy_id,@PathVariable("status") String status){
    public List<Recipes> getRecipes(@RequestParam(value = "s",required = true) String status){
        return recipeService.GetRecipesForPharmacyByStatus(2,status);
    }

    //todo
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/",method = RequestMethod.GET)
    //List<Recipes> getRecipes(@PathVariable("pharmacy_id") int pharmacy_id,@PathVariable("status") String status){
    public RecipeViewModel getRecipe(@RequestParam(value = "id",required = true) int id){
        return recipeService.GetRecipe(id);//GetRecipeForPharmacyByStatus(2,status);
    }

    //todo
    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/",method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Update(recipeViewModel);
    }

    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/recipes/",method = RequestMethod.POST)
    public void changeStatus(@RequestBody PostViewModel postViewModel){//,@RequestBody String status){
        recipeService.ChangeStatus(postViewModel.Id,postViewModel.status);
    }

    @ResponseBody
    @RequestMapping(value = "/pharmacies/2/order/",method = RequestMethod.GET)
    public OrderMissingViewModel getOrderMissing(@RequestParam(value = "pharmacistId",required = true) int pharmacistId){
        return recipeService.GetOrderMissing(pharmacistId);
    }
}
