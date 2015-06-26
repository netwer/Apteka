package aptekaproj.controllers;

import aptekaproj.helpers.exeptions.ProcessException;
import aptekaproj.viewModels.*;
import aptekaproj.models.Diagnoses;
import aptekaproj.models.Drug;
import aptekaproj.models.Pharmacy;
import aptekaproj.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 * Controller for doctor user
 */
@Controller
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
@RequestMapping("/api/doctor")
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
    @RequestMapping(value = "/appointments/{doctorId}", method = RequestMethod.GET)
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
    @RequestMapping(value = "/appointment/{doctorId}/{patientId}",method = RequestMethod.GET)
    public PatientCardViewModel getAppointment(@PathVariable("doctorId") int doctorId,
                                               @PathVariable("patientId")  int patientId){
        return userService.getPatientCard(patientId, doctorId);
    }

    @ResponseBody
    @RequestMapping(value = "/appointment/{doctorId}/{patientId}/save",method = RequestMethod.POST)
    public void saveAppointment(@PathVariable("doctorId") int doctorId,
                                @PathVariable("patientId")  int patientId,
                                @RequestBody PatientCardViewModel patientCardViewModel){
        try{
            if(doctorId <= 0 || patientId <= 0 || patientCardViewModel == null)
                throw new ProcessException("Bad parameters");
            diagnosesService.saveAppointment(patientCardViewModel);
        } catch (ProcessException e){

        } catch (ParseException e) {

        }
    }

   //todo test
    /**
     * Getting all of the patient admission records GET /diagnoses/4
     * @param user_id - is patient id;
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/diagnoses/{user_id}",method = RequestMethod.GET)
    public List<Diagnoses> appointmentsHistory(@PathVariable int user_id){
        return diagnosesService.getPatientHistory(user_id);
    }

    //todo test
    /**
     * Creating a record of the results of the reception POST /diagnoses
     * saveRecipeHasDrugs diagnoses object for patient
     * @param diagnoses
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/diagnose/save",method = RequestMethod.POST)
    public PostViewModel saveDiagnose(@RequestBody Diagnoses diagnoses) throws ParseException {
        return diagnosesService.saveDiagnosis(diagnoses);
    }

    //todo test
    /**
     * updateDiagnosis diagnoses
     * @param diagnoses
     */
    @ResponseBody
    @RequestMapping(value = "/diagnose/update",method = RequestMethod.PUT)
    public PostViewModel updateDiagnose(@RequestBody Diagnoses diagnoses){
        return diagnosesService.updateDiagnosis(diagnoses);
    }

    //todo test
    /**
     * Creating a record of the results of the reception POST /diagnoses
     * saveRecipeHasDrugs recipe
     * @param recipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/recipe/save",method = RequestMethod.POST)
    public PostViewModel saveRecipe(@RequestBody RecipeViewModel recipeViewModel){
        return recipeService.saveRecipe(recipeViewModel);
    }

    //todo test
    //todo what need to return?
    /**
     * updateDiagnosis Recipe
     * @param recipeViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/recipe/update",method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.updateRecipe(recipeViewModel);
    }

    //todo test
    /**
     * Get pharmacies list: GET /pharmacies
     * url: localhost:8443/Doctor/recipe/pharmacies
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pharmacies",method = RequestMethod.GET)
    public List<Pharmacy> getPharmacies(){
        return pharmacyService.getPharmacies();
    }

    //todo test
    /**
     * Get the drugs list
     * url: localhost:8443/Doctor/recipe/drugs
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/drugs",method = RequestMethod.GET)
    public List<Drug> getDrugs(){
        return drugService.getDrugs();
    }

    @ResponseBody
    @RequestMapping(value = "/{doctorId}/recipes",method = RequestMethod.GET)
    public List<RecipeViewModel> getRecipesByStatus(
            @PathVariable("doctorId") int doctorId,
            @RequestParam(value = "status", required = true) String status){
        return recipeService.getRecipesByStatus(doctorId,status);
    }
}