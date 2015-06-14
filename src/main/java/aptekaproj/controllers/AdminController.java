package aptekaproj.controllers;

import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 21.03.2015.
 */
@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private ApiService apiService;

    //Simple test message
    @ResponseBody
    @RequestMapping(value = "/")
    public String sayHello(){
        return "Hi!!!, This is Spring Boot Project for IS 'Apteka'";
    }

    //Get all Appointments
    @ResponseBody
    @RequestMapping(value = "/appointments",method = RequestMethod.GET)
    public List<UsersDoctorViewModel> getAppointments(){
        return apiService.GetAppointments();
    }

    //saveDiagnose new appointment
    @ResponseBody
    @RequestMapping(value = "/appointments",method = RequestMethod.POST)
    public void saveAppointment(@RequestBody UsersDoctorViewModel usersDoctorViewModel){
        apiService.SaveAppointment(usersDoctorViewModel);
    }

    //Delete appointment
    @ResponseBody
    @RequestMapping(value = "/appointments/{id}",method = RequestMethod.DELETE)
    public void deleteAppointment(@PathVariable int id){
        apiService.DeleteAppointment(id);
    }

}