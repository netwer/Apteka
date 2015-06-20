package aptekaproj.controllers;

import aptekaproj.models.Diagnoses;
import aptekaproj.viewModels.PostViewModel;
import aptekaproj.viewModels.UserDoctorViewModel;
import aptekaproj.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 21.03.2015.
 * Controller for administrator
 */
@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * Simple test message
     * @return test str
     */
    @ResponseBody
    @RequestMapping(value = "/")
    public String sayHello(){
        return "Hi!!!, This is Spring Boot Project for IS 'Apteka'";
    }

    /**
     * Get all Appointments
     * @return List<UserDoctorViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/appointments",method = RequestMethod.GET)
    public List<UserDoctorViewModel> getAppointments(){
        return adminService.getAppointments();
    }

    /**
     * saveDiagnose new appointment
     * @param userDoctorViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/appointment",method = RequestMethod.POST)
    public PostViewModel saveAppointment(@RequestBody UserDoctorViewModel userDoctorViewModel){
        return adminService.saveAppointment(userDoctorViewModel);
    }

    /**
     * Delete appointment
     * @param  id
     */
    @ResponseBody
    @RequestMapping(value = "/appointment/{id}",method = RequestMethod.DELETE)
    public void deleteAppointment(@PathVariable int id){
        adminService.deleteAppointment(id);
    }

}