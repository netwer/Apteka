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
    //url example: http://localhost:8080/Doctor/?userId=1
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    List<UsersDoctorViewModel> Patients(@RequestParam(value = "userId",required = true) int userId){
        return userService.getPatients(userId);
    }

    //Получение конкретной записи о приеме GET /diagnoses/{id}
    //localhost:8080/Doctor/PatientCard?patientId=4&doctorId=1
    @RequestMapping(value = "/PatientCard",method = RequestMethod.GET)
    public @ResponseBody
    PatientCardViewModel PatientCard(@RequestParam(value = "patientId",required = true) int patientId,
                                     @RequestParam(value = "doctorId",required = true) int doctorId){
        return userService.getPatientCard(patientId,doctorId);

    }

    //Получение всех записей приема пациента GET /diagnoses?user_id={user_id}
    @RequestMapping(value = "/diagnoses",method = RequestMethod.GET)
    public @ResponseBody
    List<Diagnoses> PatientHistory(@RequestParam(value = "user_id",required = true)int user_id){
        return diagnosesService.getPatientHistory(user_id);
    }

    //Создание записи о результатах приема POST /diagnoses
    //Сохранение diagnoses объекта для пациента
    @RequestMapping(value = "/diagnoses/save",method = RequestMethod.POST)
    public @ResponseBody PostViewModel save(@RequestBody Diagnoses diagnoses) throws ParseException {
        return diagnosesService.SaveDiagnoses(diagnoses);
    }

    //Создание записи о результатах приема POST /diagnoses
    //Сохранение рецепта
    @RequestMapping(value = "/recipe/save",method = RequestMethod.POST)
    public void saveRecipe(@RequestBody RecipeViewModel recipeViewModel){
        recipeService.Save(recipeViewModel);
    }

    //Получение списка аптек: GET /pharmacies
    @RequestMapping(value = "/recipe/pharmacies",method = RequestMethod.GET)
    public @ResponseBody
    List<Pharmacies> getPharmacies(){
        return pharmaciesService.getPharmacies();
    }


    @RequestMapping(value = "/recipe/drugs",method = RequestMethod.GET)
    public @ResponseBody
    List<Drugs> getDrugs(){
        return drugsService.getDrugs();
    }
}
