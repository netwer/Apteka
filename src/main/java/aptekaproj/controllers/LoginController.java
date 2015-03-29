package aptekaproj.controllers;

import aptekaproj.models.RoleService;
import aptekaproj.models.Roles;
import aptekaproj.models.UserService;
import aptekaproj.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Admin on 29.03.2015.
 */

@Controller
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/sigin",method = RequestMethod.GET)
    public String sigin(String login, String password){
        Users user = userService.userInDb(login,password);
        //return userService.userInDb(login,password);
        if(user != null ){
            //return new ModelAndView("redirect: /"+roleService.getRoleName(user.getRoleId()) +"/Success?message=Welcome, "+user.getName());
            //return new DoctorController().Success("Welcome, "+user.getName());
            String userName = user.getName();
            return"redirect:/" + roleService.getRoleName(user.getRoleId()) + "/Success?message=Welcome," + userName;
        }
        return "redirect:/Error/Show?message=No,user";
    }


}
