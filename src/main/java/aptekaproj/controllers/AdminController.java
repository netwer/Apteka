package aptekaproj.controllers;

import aptekaproj.viewModels.UserDoctorViewModel;
import aptekaproj.services.AdminService;
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
    private AdminService adminService;

    //Simple test message
    @ResponseBody
    @RequestMapping(value = "/")
    public String sayHello(){
        return "Hi!!!, This is Spring Boot Project for IS 'Apteka'";
    }

    //Get all Appointments
    @ResponseBody
    @RequestMapping(value = "/appointments",method = RequestMethod.GET)
    public List<UserDoctorViewModel> getAppointments(){
        return adminService.GetAppointments();
    }

    //saveDiagnose new appointment
    @ResponseBody
    @RequestMapping(value = "/appointments",method = RequestMethod.POST)
    public void saveAppointment(@RequestBody UserDoctorViewModel userDoctorViewModel){
        adminService.SaveAppointment(userDoctorViewModel);
    }

    //Delete appointment
    @ResponseBody
    @RequestMapping(value = "/appointments/{id}",method = RequestMethod.DELETE)
    public void deleteAppointment(@PathVariable int id){
        adminService.DeleteAppointment(id);
    }

}