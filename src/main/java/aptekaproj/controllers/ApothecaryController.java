package aptekaproj.controllers;

import aptekaproj.models.RecipeHasDrugs;
import aptekaproj.services.RecipeService;
import aptekaproj.viewModels.DrugToProduceViewModel;
import aptekaproj.services.DrugService;
import aptekaproj.viewModels.PostViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 * Controller for apothecary user
 */
@Controller
@PreAuthorize("hasAuthority('ROLE_APOTHECARY')")
@RequestMapping("/api/apothecary")
public class ApothecaryController {

    @Autowired
    private DrugService drugService;

    @Autowired
    private RecipeService recipeService;

    /**
     * Get list DrugToProduceViewModel
     * @param apothecaryId
     * @return List<DrugToProduceViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/{apothecaryId}/drugs",method = RequestMethod.GET)
    public List<DrugToProduceViewModel> getDrugsToProduce(@PathVariable("apothecaryId") int apothecaryId){
        return drugService.getDrugsToProduce(apothecaryId);
    }

    /**
     * Change drug and recipe status
     * @param recipeHasDrugsId
     * @return RecipeHasDrugs
     */
    @ResponseBody
    @RequestMapping(value = "/{apothecaryId}/drug/{recipeHasDrugsId}",method = RequestMethod.GET)
    public RecipeHasDrugs drugToDone(@PathVariable("apothecaryId") int apothecaryId,
                                     @PathVariable("recipeHasDrugsId") int recipeHasDrugsId){
        if(apothecaryId <= 0 || recipeHasDrugsId <= 0)
            return new RecipeHasDrugs();
        return drugService.drugToDone(recipeHasDrugsId);
    }

    @ResponseBody
    @RequestMapping(value = "/{apothecaryId}/recipe/change", method = RequestMethod.POST)
    public void changeStatus(@PathVariable("apothecaryId") int apothecaryId,
                             @RequestBody PostViewModel postViewModel) {
        if(apothecaryId <= 0 || postViewModel == null)
            return;
        recipeService.changeStatus(postViewModel.id, postViewModel.status);
    }


    /*//todo not
    *//**
     * Get one record DrugToProduceViewModel
     * @param pharmacyStaffId
     * @param drugId
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/drug/{pharmacyStaffId}/{drugId}",method = RequestMethod.GET)
    public DrugToProduceViewModel getDrugToProduce(@PathVariable("pharmacyStaffId") int pharmacyStaffId,
                                                   @PathVariable("drugId")  int drugId){
        return drugService.getDrugToProduce(pharmacyStaffId,drugId);
    }*/
}
