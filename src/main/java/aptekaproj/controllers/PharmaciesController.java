package aptekaproj.controllers;

import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.models.Users;
import aptekaproj.services.PharmacyStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Admin on 25.05.2015.
 */

//Provizor
@Controller
@RequestMapping("/Pharmacies")
public class PharmaciesController {

    @Autowired
    private PharmacyStaffService pharmacyStaffService;

    @RequestMapping(value = "/pharmacists",method = RequestMethod.GET)
    public @ResponseBody
    //List<Users> getPharmacists(@PathVariable int pharmacy_id){
    List<UserViewModel> getPharmacists(@RequestParam(value = "pharmacy",required = true) int pharmacy_id){
        return pharmacyStaffService.getPharmacists(pharmacy_id);
    }
}
