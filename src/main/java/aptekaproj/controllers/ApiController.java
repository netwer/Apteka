package aptekaproj.controllers;

import aptekaproj.models.RoleService;
import aptekaproj.models.Roles;
import aptekaproj.models.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Admin on 21.03.2015.
 */
@Controller
@RequestMapping("/Api")
public class ApiController {

    //private RolesService rolesService;
    @Autowired
    private RoleService roleService;// = new RoleService();
    @Autowired
    private UserService userService;

    /*public ApiController(RoleService roleService) {
        this.roleService = roleService;
    }*/

    /*public ApiController(RoleService roleService){
        this.roleService = roleService;
    }*/


    @RequestMapping(value = "/")
    public @ResponseBody
    String sayHello(){
        return "Hi!!!, this is Spri2ng Boot Project for IS 'Apteka'";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public @ResponseBody
    Roles getRole(String name){
        return roleService.getRoleByName(name);
    }

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public @ResponseBody
    String getRoleName(){
        return roleService.getRoleName(1);
    }

    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    public @ResponseBody
    String getUserName(){
        return userService.getUserNameById(1);
    }

    @RequestMapping(value = "/put",method = RequestMethod.GET)
    public String putRole(Roles roles){
        roleService.saveRole(roles);
        return "redirect:/Api/test?name="+roles.getName();
    }

}