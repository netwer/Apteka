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
 * Controller for apothecary user
 */
@Controller
@RequestMapping("/Apothecary")
public class ApothecaryController {

    @Autowired
    private DrugService drugService;

    /**
     * Get list DrugToProduceViewModel
     * @param pharmacyStaffId
     * @return List<DrugToProduceViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/drugs/{pharmacyStaffId}",method = RequestMethod.GET)
    public List<DrugToProduceViewModel> getDrugsToProduce(@PathVariable("pharmacyStaffId") int pharmacyStaffId){
        return drugService.getDrugsToProduce(pharmacyStaffId);
    }

    /**
     * Get one record DrugToProduceViewModel
     * @param pharmacyStaffId
     * @param drugId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/drug/{pharmacyStaffId}/{drugId}",method = RequestMethod.GET)
    public DrugToProduceViewModel getDrugToProduce(@PathVariable("pharmacyStaffId") int pharmacyStaffId,
                                                   @PathVariable("drugId")  int drugId){
        return drugService.getDrugToProduce(pharmacyStaffId,drugId);
    }

    /**
     * Change drug and recipe status
     * @param recipeHasDrugsId1
     * @return RecipeHasDrugs
     */
    @ResponseBody
    @RequestMapping(value = "/drug/{recipeHasDrugsId1}",method = RequestMethod.GET)
    public RecipeHasDrugs drugToDone(@PathVariable("recipeHasDrugsId1") int recipeHasDrugsId1){
        return drugService.drugToDone(recipeHasDrugsId1);
    }
}
