package aptekaproj.controllers;

import aptekaproj.helpers.exeptions.ProcessException;
import aptekaproj.models.Diagnoses;
import aptekaproj.viewModels.PostViewModel;
import aptekaproj.viewModels.UserDoctorViewModel;
import aptekaproj.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return "Hi! This is Spring Boot Project for IS 'Apteka'";
    }

    /**
     * Get all Appointments
     * @return List<UserDoctorViewModel>
     */
    @ResponseBody
    @RequestMapping(value = "/appointments",method = RequestMethod.GET)
    public List<UserDoctorViewModel> getAppointments(){
        try{
            return adminService.getAppointments();
        }
        catch (ProcessException e){
            return new ArrayList<>();
        }
    }

    /**
     * saveDiagnose new appointment
     * @param userDoctorViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/appointment",method = RequestMethod.POST)
    public PostViewModel saveAppointment(@RequestBody UserDoctorViewModel userDoctorViewModel){
        try{
            if(userDoctorViewModel == null)
                throw new ProcessException("The data from user is empty!");
            return adminService.saveAppointment(userDoctorViewModel);
        }catch (ProcessException e){
            return new PostViewModel();
        }
    }

    /**
     * Delete appointment
     * @param  id
     */
    @ResponseBody
        @RequestMapping(value = "/appointment/{id}",method = RequestMethod.DELETE)
    public void deleteAppointment(@PathVariable int id){
        try {
            if(id <= 0 || String.valueOf(id).isEmpty() || !String.valueOf(id).isEmpty())
                throw new ProcessException("Wrong id parameter!");
            adminService.deleteAppointment(id);
        }catch (ProcessException e){

        }
    }

}