package aptekaproj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Admin on 29.03.2015.
 */
@Controller
@RequestMapping("/Doctor")
public class DoctorController {

    @RequestMapping(value = "/Success",method = RequestMethod.GET)
    public @ResponseBody
    String Success(String message){
        return message;
    }
}
