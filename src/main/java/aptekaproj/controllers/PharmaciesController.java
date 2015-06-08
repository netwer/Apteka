package aptekaproj.controllers;

import aptekaproj.ViewModels.PostViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.models.Recipes;
import aptekaproj.models.Users;
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
    @RequestMapping(value = "/pharmacists",method = RequestMethod.GET)
    public @ResponseBody
    //List<Users> getPharmacists(@PathVariable int pharmacy_id){
    List<UserViewModel> getPharmacists(@RequestParam(value = "pharmacy",required = true) int pharmacy_id){
        return pharmacyStaffService.getPharmacists(pharmacy_id);
    }

    //@RequestMapping(value = "/pharmacies/{pharmacy_id}/recipes?s={status}",method = RequestMethod.GET)
    //todo
    @RequestMapping(value = "/pharmacies/2/recipes",method = RequestMethod.GET)
    public @ResponseBody
    //List<Recipes> getRecipes(@PathVariable("pharmacy_id") int pharmacy_id,@PathVariable("status") String status){
    List<Recipes> getRecipes(@RequestParam(value = "s",required = true) String status){
        return recipeService.GetRecipesForPharmacyByStatus(2,status);
    }

    //todo
    @RequestMapping(value = "/pharmacies/2/recipes/",method = RequestMethod.GET)
    public @ResponseBody
    //List<Recipes> getRecipes(@PathVariable("pharmacy_id") int pharmacy_id,@PathVariable("status") String status){
    RecipeViewModel getRecipe(@RequestParam(value = "id",required = true) int id){
        return recipeService.GetRecipe(id);//GetRecipeForPharmacyByStatus(2,status);
    }

    //todo
    @RequestMapping(value = "/pharmacies/2/recipes/",method = RequestMethod.PUT)
    public @ResponseBody void updateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Update(recipeViewModel);
    }

    @RequestMapping(value = "/pharmacies/2/recipes/",method = RequestMethod.POST)
    public @ResponseBody void changeStatus(@RequestBody PostViewModel postViewModel){//,@RequestBody String status){
        recipeService.ChangeStatus(postViewModel.Id,postViewModel.status);
    }
}
