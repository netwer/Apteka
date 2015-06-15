package aptekaproj.controllers;

import aptekaproj.models.RecipeHasDrugs;
import aptekaproj.viewModels.DrugToProduceViewModel;
import aptekaproj.services.DrugService;
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
    private DrugService drugService;

    @Autowired
    private RecipeService recipeService;

    @ResponseBody
    @RequestMapping(value = "/drugs/",method = RequestMethod.GET)
    public List<DrugToProduceViewModel> getDrugsToProduce(@RequestParam(value = "pharmacyStaffId", required = true) int pharmacyStaffId){
        return drugService.getDrugsToProduce(pharmacyStaffId);
    }

    @ResponseBody
    @RequestMapping(value = "/drug/",method = RequestMethod.GET)
    public DrugToProduceViewModel getDrugToProduce(@RequestParam(value = "pharmacyStaffId", required = true) int pharmacyStaffId,
                                                   @RequestParam(value = "drugId", required = true) int drugId){
        return drugService.getDrugToProduce(pharmacyStaffId,drugId);
    }

    @ResponseBody
    @RequestMapping(value = "/drug/done/",method = RequestMethod.GET)
    public RecipeHasDrugs drugToDone(@RequestParam(value = "recipeHasDrugsId") int recipeHasDrugsId1){
        return drugService.drugToDone(recipeHasDrugsId1);
    }
    //Change recipe status - the input parameter is ViewModel with recipe ID and new status
    /*@ResponseBody
    @RequestMapping(value = "/recipes/", method = RequestMethod.POST)
    public void changeStatus(@RequestBody PostViewModel postViewModel) {//,@RequestBody String status){
        recipeService.ChangeStatus(postViewModel.id, postViewModel.status);
    }*/
}
