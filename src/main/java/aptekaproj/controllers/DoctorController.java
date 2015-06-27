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

    @ResponseBody
    @RequestMapping(value = "/{doctorId}/appointments", method = RequestMethod.GET)
    public List<PatientCardViewModel> getAppointmentsForPatient(@PathVariable Integer doctorId,
                                                                @RequestParam(value = "patientId", required = false) Integer patientId){
        if (patientId == null) {
            return userService.getDoctorAppointmentsCards(doctorId);
        }
        return userService.getPatientsCards(doctorId, patientId);
    }

    @ResponseBody
    @RequestMapping(value = "/{doctorId}/appointments/{diagnosisId}",method = RequestMethod.PUT)
    public void saveAppointment(@PathVariable("doctorId") int doctorId,
                                @PathVariable("diagnosisId")  int diagnosisId,
                                @RequestBody PatientCardViewModel patientCardViewModel){
        try{
            if(doctorId <= 0 || diagnosisId <= 0 || patientCardViewModel == null)
                throw new ProcessException("Bad parameters");
            diagnosesService.saveAppointment(patientCardViewModel,diagnosisId);
        } catch (ProcessException e){

        } catch (ParseException e) {

        }
    }

//    @ResponseBody
//    @RequestMapping(value = "/doctor/{doctorId}/appointments?patientId",method = RequestMethod.GET)
//    public List<PatientCardViewModel> getAppointmentsPatientHistory(@PathVariable("doctorId") int doctorId,
//                                                                    @RequestParam("patientId") int patientId) {
//        return userService.getPatientsCards(doctorId, patientId);
//    }

    @ResponseBody
    @RequestMapping(value = "/pharmacies",method = RequestMethod.GET)
    public List<Pharmacy> getPharmacies(){
        return pharmacyService.getPharmacies();
    }

    @ResponseBody
    @RequestMapping(value = "/drugs",method = RequestMethod.GET)
    public List<Drug> getDrugs(){
        return drugService.getDrugs();
    }


/*    @ResponseBody
    @RequestMapping(value = "/{doctorId}/appointments", method = RequestMethod.GET)
    public List<UserDoctorViewModel> getAppointments(@PathVariable int doctorId){
        return userService.getPatients(doctorId);
    }

    @ResponseBody
    @RequestMapping(value = "/{doctorId}/appointments/{diagnosisId}",method = RequestMethod.PUT)
    public void saveAppointment(@PathVariable("doctorId") int doctorId,
                                @PathVariable("diagnosisId")  int diagnosisId,
                                @RequestBody PatientCardViewModel patientCardViewModel){
        try{
            if(doctorId <= 0 || diagnosisId <= 0 || patientCardViewModel == null)
                throw new ProcessException("Bad parameters");
            diagnosesService.saveAppointment(patientCardViewModel,diagnosisId);
        } catch (ProcessException e){

        } catch (ParseException e) {

        }
    }

    @ResponseBody
    @RequestMapping(value = "/{doctorId}/appointments/",method = RequestMethod.GET)
    public List<PatientCardViewModel> getAppointmentsPatientHistory(@PathVariable("doctorId") int doctorId,
                                                                    @RequestParam("patientId") int patientId) {
        return userService.getPatientsCards(patientId);
    }

    @ResponseBody
    @RequestMapping(value = "/pharmacies",method = RequestMethod.GET)
    public List<Pharmacy> getPharmacies(){
        return pharmacyService.getPharmacies();
    }

    @ResponseBody
    @RequestMapping(value = "/drugs",method = RequestMethod.GET)
    public List<Drug> getDrugs(){
        return drugService.getDrugs();
    }*/


    /*//todo test
    *//**
     * Getting specific record for admission GET /diagnoses/{id}
     * localhost:8443/Doctor/getAppointment?patientId=4&doctorId=1
     * @param patientId
     * @param doctorId
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/appointment/{doctorId}/{patientId}",method = RequestMethod.GET)
    public PatientCardViewModel getAppointment(@PathVariable("doctorId") int doctorId,
                                               @PathVariable("patientId")  int patientId){
        return userService.getPatientCard(patientId, doctorId);
    }*/

    /*//todo test
    *//**
     * Creating a record of the results of the reception POST /diagnoses
     * saveRecipeHasDrugs diagnoses object for patient
     * @param diagnoses
     * @return
     * @throws ParseException
     *//*
    @ResponseBody
    @RequestMapping(value = "/diagnose/save",method = RequestMethod.POST)
    public PostViewModel saveDiagnose(@RequestBody Diagnoses diagnoses) throws ParseException {
        return diagnosesService.saveDiagnosis(diagnoses);
    }

    //todo test
    *//**
     * updateDiagnosis diagnoses
     * @param diagnoses
     *//*
    @ResponseBody
    @RequestMapping(value = "/diagnose/update",method = RequestMethod.PUT)
    public PostViewModel updateDiagnose(@RequestBody Diagnoses diagnoses){
        return diagnosesService.updateDiagnosis(diagnoses);
    }

    //todo test
    *//**
     * Creating a record of the results of the reception POST /diagnoses
     * saveRecipeHasDrugs recipe
     * @param recipeViewModel
     *//*
    @ResponseBody
    @RequestMapping(value = "/recipe/save",method = RequestMethod.POST)
    public PostViewModel saveRecipe(@RequestBody RecipeViewModel recipeViewModel){
        return recipeService.saveRecipe(recipeViewModel);
    }

    //todo test
    //todo what need to return?
    *//**
     * updateDiagnosis Recipe
     * @param recipeViewModel
     *//*
    @ResponseBody
    @RequestMapping(value = "/recipe/update",method = RequestMethod.PUT)
    public void updateRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.updateRecipe(recipeViewModel);
    }
*/

    /*@ResponseBody
    @RequestMapping(value = "/{doctorId}/recipes",method = RequestMethod.GET)
    public List<RecipeViewModel> getRecipesByStatus(
            @PathVariable("doctorId") int doctorId,
            @RequestParam(value = "status", required = true) String status){
        return recipeService.getRecipesByStatus(doctorId,status);
    }*/
}