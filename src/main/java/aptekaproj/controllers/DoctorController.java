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
}