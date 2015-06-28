package aptekaproj.services;

import aptekaproj.viewModels.UserViewModel;
import aptekaproj.controllers.repository.IPharmacyStaffRepository;
import aptekaproj.helpers.enums.RolesNameEnum;
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

    public List<UserViewModel> getPharmacists(int pharmacy_id) {
        List<UserViewModel> users = new ArrayList<>();
        List<PharmacyStaff> pharmacyStaffs = (List<PharmacyStaff>)pharmacyStaffRepository.findAll();

        for(PharmacyStaff pharmacyStaff : pharmacyStaffs){
            if(pharmacyStaff.getPharmacyId() == pharmacy_id){
                UserViewModel userViewModel = new UserViewModel();
                User user1 = new User();
                user1 = userService.getUserById(pharmacyStaff.getUserId());
                userViewModel.userId = user1.getId();
                userViewModel.userRole = roleService.getRoleName(user1.getRoleId());
                userViewModel.userFullName = user1.getFullName();
                userViewModel.userLogin = user1.getLogin();
                users.add(userViewModel);
            }
        }

        return users;
    }

    public PharmacyStaff getApothecariesByPharmacistId(int pharmacistId) {
        List<PharmacyStaff> pharmacyStaffs = getPharmacyStaffs();
        PharmacyStaff currentPharmacyStaff = new PharmacyStaff();
        for (PharmacyStaff pharmacyStaff : pharmacyStaffs){
            if(pharmacyStaff.getUserId()==pharmacistId){
                currentPharmacyStaff = pharmacyStaff;
                break;
            }
        }

        return currentPharmacyStaff;
    }

    public List<PharmacyStaff> getPharmacyStaffs(){
        return (List<PharmacyStaff>)pharmacyStaffRepository.findAll();
    }

    public List<User> getApothecariesStaffs(int pharmacistId) {
        int pharmacyId = getApothecariesByPharmacistId(pharmacistId).getPharmacyId();
        List<Integer> apothecariesIdList = getPharmacistsIdsInPharmacy(pharmacyId);
        return userService.getUsersByIds(apothecariesIdList, RolesNameEnum.APOTHECARY.toString());
    }

    private List<Integer> getPharmacistsIdsInPharmacy(int pharmacyId) {
        List<Integer> integers = new ArrayList<>();
        List<PharmacyStaff> pharmacyStaffList = getPharmacyStaffs();
        for(PharmacyStaff pharmacyStaff : pharmacyStaffList){
            if(pharmacyStaff.getPharmacyId() == pharmacyId){
                integers.add(pharmacyStaff.getUserId());
            }
        }

        return integers;
    }


}
