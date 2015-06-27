package aptekaproj.controllers;

import aptekaproj.viewModels.UserViewModel;
import aptekaproj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Admin on 29.03.2015.
 * Controller for login/logout into Apteka
 */

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Login into apteka
     * http://localhost:8080/Login/?login=doctor1&password=doctor1
     * @param login
     * @param password
     * @return UserViewModel
     */
    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public UserViewModel login(@RequestParam(value = "login", required = true) String login,
                               @RequestParam(value = "password", required = true) String password){

        UserViewModel userViewModel = userService.getUser(login,password);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userViewModel.userLogin, "", AuthorityUtils.createAuthorityList("ROLE_"+userViewModel.userRole)));
        System.out.println("LOGIN->" + SecurityContextHolder.getContext().getAuthentication().getName());
        return userViewModel;
    }

    @ResponseBody
    @RequestMapping(value = "/login/",method = RequestMethod.GET)
    public String login(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login/error/",method = RequestMethod.GET)
    public String error(){
        return "Error";
    }

    @ResponseBody
    @RequestMapping(value = "/logout/", method = RequestMethod.GET)
    public void logout(){
        SecurityContextHolder.clearContext();
    }
}
