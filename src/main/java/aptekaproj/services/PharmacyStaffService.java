package aptekaproj.services;

import aptekaproj.viewModels.UserViewModel;
import aptekaproj.controllers.repository.IPharmacyStaffRepository;
import aptekaproj.helpers.Enums.RolesNameEnum;
import aptekaproj.models.PharmacyStaff;
import aptekaproj.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 06.06.2015.
 */
@Service
public class PharmacyStaffService {

    @Autowired
    private IPharmacyStaffRepository pharmacyStaffRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public List<UserViewModel> GetPharmacists(int pharmacy_id) {
        List<UserViewModel> users = new ArrayList<>();
        List<PharmacyStaff> pharmacyStaffs = (List<PharmacyStaff>)pharmacyStaffRepository.findAll();

        for(PharmacyStaff pharmacyStaff : pharmacyStaffs){
            if(pharmacyStaff.getPharmacyId() == pharmacy_id){
                UserViewModel userViewModel = new UserViewModel();
                User user1 = new User();
                user1 = userService.getUserById(pharmacyStaff.getUserId());
                userViewModel.userId = user1.getId();
                userViewModel.userRole = roleService.GetRoleName(user1.getRoleId());
                userViewModel.userFullName = user1.getFullName();
                userViewModel.userLogin = user1.getLogin();
                users.add(userViewModel);
            }
        }

        return users;
    }

    public PharmacyStaff GetPharmacyByPharmacistId(int pharmacistId) {
        List<PharmacyStaff> pharmacyStaffs = GetPharmacyStaffs();
        PharmacyStaff currentPharmacyStaff = new PharmacyStaff();
        for (PharmacyStaff pharmacyStaff : pharmacyStaffs){
            if(pharmacyStaff.getUserId()==pharmacistId){
                currentPharmacyStaff = pharmacyStaff;
                break;
            }
        }

        return currentPharmacyStaff;
    }

    public List<PharmacyStaff> GetPharmacyStaffs(){
        return (List<PharmacyStaff>)pharmacyStaffRepository.findAll();
    }

    public List<User> GetStaffs(int pharmacistId) {
        int pharmacyId = GetPharmacyByPharmacistId(pharmacistId).getPharmacyId();
        List<Integer> pharmacistIdList = GetPharmacistsIdsInPharmacy(pharmacyId);
        return userService.GetUsersByIds(pharmacistIdList, RolesNameEnum.APOTEKARY.toString());
    }

    private List<Integer> GetPharmacistsIdsInPharmacy(int pharmacyId) {
        List<Integer> integers = new ArrayList<>();
        List<PharmacyStaff> pharmacyStaffList = GetPharmacyStaffs();
        for(PharmacyStaff pharmacyStaff : pharmacyStaffList){
            if(pharmacyStaff.getPharmacyId() == pharmacyId){
                integers.add(pharmacyStaff.getUserId());
            }
        }

        return integers;
    }


}
