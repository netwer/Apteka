package aptekaproj.controllers;

import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.services.RoleService;
import aptekaproj.services.UserService;
import aptekaproj.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public @ResponseBody
    UserViewModel sigin(String login, String password){
        Users user = userService.userInDb(login,password);
        UserViewModel responseViewModel = new UserViewModel();
        if(user != null ){
            responseViewModel.Url = "/" + roleService.getRoleName(user.getRoleId()) + "/";
            responseViewModel.UserFullName = user.getFullName();
            responseViewModel.UserId = user.getId();
            responseViewModel.UserLogin = user.getLogin();
            responseViewModel.UserRole = roleService.getRoleName(user.getRoleId());
            responseViewModel.UserRoleId = user.getRoleId();
            responseViewModel.ErrorMessage = "";
            //return"redirect:/" + roleService.getRoleName(user.getRoleId()) + "/Success?message=Welcome," + userName;
            return responseViewModel;
        }
        responseViewModel.ErrorMessage = "login or password incorrect";
        responseViewModel.Url = "/Login/sigin";
        return responseViewModel;
    }


}
