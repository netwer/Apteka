package aptekaproj.controllers;

import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Admin on 29.03.2015.
 */

@Controller
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private UserService userService;

    //http://localhost:8080/Login/?login=doctor1&password=doctor1
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public @ResponseBody
    UserViewModel sigin(@RequestParam(value = "login",required = true) String login,
                        @RequestParam(value = "password",required = true) String password){
        return userService.getUser(login,password);
    }
}
