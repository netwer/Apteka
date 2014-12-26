package hello;

import com.fasterxml.jackson.annotation.JsonView;
import hello.Helpers.Initializator;
import hello.Model.*;
import hello.Services.*;
import hello.ViewModel.AptekaDrugView;
import hello.ViewModel.AptekaDrugViewModel;
import org.springframework.context.annotation.Bean;
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
    private PatientRecordsService patientRecordsService = new PatientRecordsService();
    private AptekaDrugService aptekaDrugService = new AptekaDrugService();

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

    @RequestMapping(value = "/PatientRecords", method = RequestMethod.GET)
    public @ResponseBody
    User PatientRecords(@RequestParam(value = "patientId",required = false,defaultValue = "0")long patientId){
        return userService.getUserById(patientId);
    }

    @RequestMapping(value = "/UpdatePatientRecors", method = RequestMethod.GET)
    public String UpdatePatientRecors(@RequestParam(value = "patientRecordId",required = false,defaultValue = "0")long patientRecordId,
                                      @RequestParam(value = "userId",required = false,defaultValue = "0") long userId,
                                      @RequestParam(value = "Complaints",required = false,defaultValue = "0")String complaints,
                                      @RequestParam(value = "Diagnosis",required = false,defaultValue = "0")String diagnosis){
        patientRecordsService.UpdatePatientRecordsById(patientRecordId,userId,complaints,diagnosis);
        return "redirect:/Api/Resept?userId="+Long.toString(userId);
    }

    @RequestMapping(value = "/Resept", method = RequestMethod.GET)
    public @ResponseBody
    AptekaDrugView Resept(@RequestParam(value = "userId",required = false,defaultValue = "0")long userId){
        return aptekaDrugService.getAptekaDrugs(userId);
    }

    @RequestMapping(value = "/CreateOrder",method = RequestMethod.POST)
    public @ResponseBody String CreateOrder(@RequestBody AptekaDrugView adv){
        aptekaDrugService.CreateOrder(adv);
        return "Ok";
    }
}

