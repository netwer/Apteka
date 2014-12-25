package hello;

import com.fasterxml.jackson.annotation.JsonView;
import hello.Helpers.Initializator;
import hello.Model.DoctorAppointments;
import hello.Model.Role;
import hello.Model.User;
import hello.Model.UserRole;
import hello.Services.DoctorAppointmentsService;
import hello.Services.RoleService;
import hello.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 21:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/Api")
public class ApiController{

    private List<User> userList;
    private List<Role> roleList;
    private List<UserRole> userRoleList;
    private RoleService roleService = new RoleService();
    private UserService userService = new UserService();
    private DoctorAppointmentsService doctorAppointmentsService = new DoctorAppointmentsService();

    @RequestMapping("/")
    public @ResponseBody String sayHello(){
        return "redirect:/Api/error";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(@RequestParam(value="login", required=false, defaultValue="Stranger")String login, @RequestParam(value="pass", required=false, defaultValue="Stranger")String password){
        userList = Initializator.getUsers();
        roleList = Initializator.getRoles();
        userRoleList = Initializator.getUserRoles();

        for (final User user : userList) {
            if(user.getName().equals(login) && user.getPassword().equals(password)){
                if(roleService.getRole(user.getId()) == "patient"){
                    return "redirect:/Api/OrderList?id="+Long.toString(user.getId());
                }
                if(roleService.getRole(user.getId()) == "doctor"){
                    return "redirect:/Api/DocAppoint?id="+Long.toString(user.getId());
                }
            }
        }
        return "redirect:/Api/error";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public @ResponseBody String Login(){
         return "test";
    }

    @RequestMapping(value = "/OrderList", method = RequestMethod.GET)
    public @ResponseBody User OrderList(@RequestParam(value="id", required=false, defaultValue="0")long userId)
    {
         return userService.getUserById(userId);
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public @ResponseBody String error (){
        return "Error no User";
    }

    @RequestMapping(value = "/DocAppoint", method = RequestMethod.GET)
    public @ResponseBody
    DoctorAppointments DocAppoint(@RequestParam(value = "id",required = false,defaultValue = "0") long doctorId){
        return doctorAppointmentsService.getAppointmentsByDoctorId(doctorId);
    }
}

