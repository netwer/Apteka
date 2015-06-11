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
@RequestMapping("/Api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/")
    public @ResponseBody
    String sayHello(){
        return "Hi!!!, This is Spring Boot Project for IS 'Apteka'";
    }

    @RequestMapping(value = "/appointments",method = RequestMethod.GET)
    public @ResponseBody
    List<UsersDoctorViewModel> GetAppointments(){
        return apiService.GetAppointments();
    }

    @RequestMapping(value = "/appointments",method = RequestMethod.POST)
    public @ResponseBody void SaveAppointment(@RequestBody UsersDoctorViewModel usersDoctorViewModel){
        apiService.SaveAppointment(usersDoctorViewModel);
    }

    @RequestMapping(value = "/appointments/{id}",method = RequestMethod.DELETE)
    public @ResponseBody void DeleteAppointment(@PathVariable int id){
        apiService.DeleteAppointment(id);
    }

}