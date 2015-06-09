package aptekaproj.services;

import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.controllers.repository.IPharmacyStaffRepository;
import aptekaproj.helpers.Enums.RolesNameEnum;
import aptekaproj.models.PharmacyStaff;
import aptekaproj.models.Users;
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
                Users users1 = new Users();
                users1 = userService.getUserById(pharmacyStaff.getUserId());
                userViewModel.UserId = users1.getId();
                userViewModel.UserRole = roleService.getRoleName(users1.getRoleId());
                userViewModel.UserFullName = users1.getFullName();
                userViewModel.UserLogin = users1.getLogin();
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

    public List<Users> GetStaffs(int pharmacistId) {
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
