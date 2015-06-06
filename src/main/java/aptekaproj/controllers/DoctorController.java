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
import java.text.SimpleDateFormat;
import java.util.Date;
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
        return diagnosesService.getPatientHistory(user_id);
    }

    //Creating a record of the results of the reception POST /diagnoses
    //Save diagnoses object for patient
    @RequestMapping(value = "/diagnoses/save",method = RequestMethod.POST)
    public @ResponseBody PostViewModel save(@RequestBody Diagnoses diagnoses) throws ParseException {
        return diagnosesService.SaveDiagnoses(diagnoses);
    }

    //Update diagnoses
    @RequestMapping(value = "/diagnoses/update",method = RequestMethod.PUT)
    public @ResponseBody void updateDiagnoses(@RequestBody Diagnoses diagnoses){
        diagnosesService.Update(diagnoses);
    }

    //Creating a record of the results of the reception POST /diagnoses
    //Save recipe
    @RequestMapping(value = "/recipe/save",method = RequestMethod.POST)
    public void saveRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Save(recipeViewModel);
    }

    //Update Recipe
    @RequestMapping(value = "/recipe/update",method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Update(recipeViewModel);
    }

    //Get pharmacies list: GET /pharmacies
    @RequestMapping(value = "/recipe/pharmacies",method = RequestMethod.GET)
    public @ResponseBody
    List<Pharmacies> getPharmacies(){
        return pharmaciesService.getPharmacies();
    }

    //Get the drugs list
    @RequestMapping(value = "/recipe/drugs",method = RequestMethod.GET)
    public @ResponseBody
    List<Drugs> getDrugs(){
        return drugsService.getDrugs();
    }
}
