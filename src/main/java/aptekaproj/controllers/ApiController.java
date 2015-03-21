package aptekaproj.controllers;

import aptekaproj.models.IRolesRepository;
import aptekaproj.models.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Admin on 21.03.2015.
 */
@Controller
@RequestMapping("/Api")
public class ApiController {

    //private RolesService rolesService;
    @Autowired
    private IRolesRepository repository;

    /*@Autowired
    public ApiController(IRolesRepository repository)
    {
        this.repository = repository;
    }*/

    @RequestMapping("/")
    public @ResponseBody
    String sayHello(){
        return "Hi!!!, this is Spri2ng Boot Project for IS 'Apteka'";
    }

    @RequestMapping("/test")
    public @ResponseBody
    Roles getRole(int id){
        return repository.findOne(id);
    }

}