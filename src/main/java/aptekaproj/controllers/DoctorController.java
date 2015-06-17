package aptekaproj.controllers;

import aptekaproj.viewModels.PatientCardViewModel;
import aptekaproj.viewModels.PostViewModel;
import aptekaproj.viewModels.RecipeViewModel;
import aptekaproj.viewModels.UserDoctorViewModel;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Drug;
import aptekaproj.models.Pharmacy;
import aptekaproj.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 * Controller for doctor user
 */
@Controller
@RequestMapping("/Doctor")
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiagnosesService diagnosesService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private RecipeService recipeService;

    /**
     * Get appointments list
     * url example: http://localhost:8443/Doctor/1
     * @param doctorId
     * @return List<UserDoctorViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/{doctorId}", method = RequestMethod.GET)
    public List<UserDoctorViewModel> getAppointments(@PathVariable int doctorId){
        return userService.getPatients(doctorId);
    }

    //todo test
    /**
     * Getting specific record for admission GET /diagnoses/{id}
     * localhost:8443/Doctor/getAppointment?patientId=4&doctorId=1
     * @param patientId
     * @param doctorId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/PatientCard",method = RequestMethod.GET)
    public PatientCardViewModel getAppointment(@RequestParam(value = "patientId", required = true) int patientId,
                                               @RequestParam(value = "doctorId", required = true) int doctorId) {
        return userService.getPatientCard(patientId, doctorId);
    }

   //todo test
    /**
     * Getting all of the patient admission records GET /diagnoses/4
     * @param user_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/diagnoses/{user_id}",method = RequestMethod.GET)
    public List<Diagnoses> appointmentsHistory(@PathVariable int user_id){
        return diagnosesService.GetPatientHistory(user_id);
    }

    //todo test
    /**
     * Creating a record of the results of the reception POST /diagnoses
     * SaveRecipeHasDrugs diagnoses object for patient
     * @param diagnoses
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/diagnose/save",method = RequestMethod.POST)
    public PostViewModel saveDiagnose(@RequestBody Diagnoses diagnoses) throws ParseException {
        return diagnosesService.SaveDiagnosis(diagnoses);
    }

    //todo test
    /**
     * UpdateDiagnosis diagnoses
     * @param diagnoses
     */
    @ResponseBody
    @RequestMapping(value = "/diagnose/update",method = RequestMethod.PUT)
    public void updateDiagnose(@RequestBody Diagnoses diagnoses){
        diagnosesService.UpdateDiagnosis(diagnoses);
    }

    //todo test
    /**
     * Creating a record of the results of the reception POST /diagnoses
     * SaveRecipeHasDrugs recipe
     * @param recipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/recipe/save",method = RequestMethod.POST)
    public void saveRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Save(recipeViewModel);
    }

    //todo test

    /**
     * UpdateDiagnosis Recipe
     * @param recipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/recipe/update",method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Update(recipeViewModel);
    }

    //todo test

    /**
     * Get pharmacies list: GET /pharmacies
     * url: localhost:8443/Doctor/recipe/pharmacies
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recipe/pharmacies",method = RequestMethod.GET)
    public List<Pharmacy> getPharmacies(){
        return pharmacyService.GetPharmacies();
    }

    //todo test
    /**
     * Get the drugs list
     * url: localhost:8443/Doctor/recipe/drugs
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recipe/drugs",method = RequestMethod.GET)
    public List<Drug> getDrugs(){
        return drugService.GetDrugs();
    }
}