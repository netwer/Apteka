package aptekaproj.controllers;

import aptekaproj.ViewModels.UsersDoctorViewModel;
import aptekaproj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 */
@Controller
@RequestMapping("/Doctor")
public class DoctorController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/Success",method = RequestMethod.GET)
    public @ResponseBody
    String Success(String message){
        return message;
    }

    //url example: http://localhost:8080/Doctor/?userId=1
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    List<UsersDoctorViewModel> Patients(@RequestParam(value = "userId",required = true) int userId){
        return userService.getPatients(userId);
    }
}
