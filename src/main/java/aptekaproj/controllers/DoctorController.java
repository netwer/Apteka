package aptekaproj.controllers;

import aptekaproj.ViewModels.PatientCardViewModel;
import aptekaproj.ViewModels.PostViewModel;
import aptekaproj.ViewModels.RecipeViewModel;
import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Drugs;
import aptekaproj.models.Pharmacies;
import aptekaproj.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 */
@Controller
@RequestMapping("/Doctor")
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiagnosesService diagnosesService;

    @Autowired
    private PharmaciesService pharmaciesService;

    @Autowired
    private DrugsService drugsService;

    @Autowired
    private RecipeService recipeService;

    //Получение списка заявок на прием GET /appointments
    //url example: http://localhost:8443/Doctor/1
    @RequestMapping(value = "/{doctorId}", method = RequestMethod.GET)
    public @ResponseBody
    List<UsersDoctorViewModel> Patients(@PathVariable int doctorId){
        return userService.getPatients(doctorId);
    }

    //Getting specific record for admission GET /diagnoses/{id}
    //localhost:8443/Doctor/PatientCard?patientId=4&doctorId=1
    @RequestMapping(value = "/PatientCard",method = RequestMethod.GET)
    public @ResponseBody
    PatientCardViewModel PatientCard(@RequestParam(value = "patientId",required = true) int patientId,
                                     @RequestParam(value = "doctorId",required = true) int doctorId){
        return userService.getPatientCard(patientId,doctorId);

    }

    //Getting all of the patient admission records GET /diagnoses/4
    @RequestMapping(value = "/diagnoses/{user_id}",method = RequestMethod.GET)
    public @ResponseBody
    List<Diagnoses> PatientHistory(@PathVariable int user_id){
        return diagnosesService.GetPatientHistory(user_id);
    }

    //Creating a record of the results of the reception POST /diagnoses
    //SaveRecipeHasDrugs diagnoses object for patient
    @RequestMapping(value = "/diagnoses/save",method = RequestMethod.POST)
    public @ResponseBody PostViewModel Save(@RequestBody Diagnoses diagnoses) throws ParseException {
        return diagnosesService.SaveDiagnosis(diagnoses);
    }

    //UpdateDiagnosis diagnoses
    @RequestMapping(value = "/diagnoses/update",method = RequestMethod.PUT)
    public @ResponseBody void UpdateDiagnoses(@RequestBody Diagnoses diagnoses){
        diagnosesService.UpdateDiagnosis(diagnoses);
    }

    //Creating a record of the results of the reception POST /diagnoses
    //SaveRecipeHasDrugs recipe
    @RequestMapping(value = "/recipe/save",method = RequestMethod.POST)
    public void SaveRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Save(recipeViewModel);
    }

    //UpdateDiagnosis Recipe
    @RequestMapping(value = "/recipe/update",method = RequestMethod.PUT)
    public void UpdateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Update(recipeViewModel);
    }

    //Get pharmacies list: GET /pharmacies
    //url: localhost:8443/Doctor/recipe/pharmacies
    @RequestMapping(value = "/recipe/pharmacies",method = RequestMethod.GET)
    public @ResponseBody
    List<Pharmacies> GetPharmacies(){
        return pharmaciesService.GetPharmacies();
    }

    //Get the drugs list
    //url: localhost:8443/Doctor/recipe/drugs
    @RequestMapping(value = "/recipe/drugs",method = RequestMethod.GET)
    public @ResponseBody
    List<Drugs> GetDrugs(){
        return drugsService.GetDrugs();
    }
}
