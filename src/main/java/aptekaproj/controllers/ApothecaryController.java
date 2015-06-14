package aptekaproj.controllers;

import aptekaproj.ViewModels.DrugToProduceViewModel;
import aptekaproj.ViewModels.PostViewModel;
import aptekaproj.services.DrugsService;
import aptekaproj.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 */
@Controller
@RequestMapping("/Apothecary")
public class ApothecaryController {

    @Autowired
    private DrugsService drugsService;

    @Autowired
    private RecipeService recipeService;

    @ResponseBody
    @RequestMapping(value = "/drugs/",method = RequestMethod.GET)
    public List<DrugToProduceViewModel> getDrugsToProduce(@RequestParam(value = "pharmacyStaffId", required = true) int pharmacyStaffId){
        return drugsService.getDrugsToProduce(pharmacyStaffId);
    }

    //Change recipe status - the input parameter is ViewModel with recipe ID and new status
    /*@ResponseBody
    @RequestMapping(value = "/recipes/", method = RequestMethod.POST)
    public void changeStatus(@RequestBody PostViewModel postViewModel) {//,@RequestBody String status){
        recipeService.ChangeStatus(postViewModel.Id, postViewModel.status);
    }*/
}
