package aptekaproj.services;

import aptekaproj.models.*;
import aptekaproj.viewModels.ApothecaryViewModel;
import aptekaproj.viewModels.UserViewModel;
import aptekaproj.controllers.repository.IPharmacyStaffRepository;
import aptekaproj.helpers.enums.RolesNameEnum;
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

    @Autowired
    private ConcreteDrugService concreteDrugService;

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

    public List<ApothecaryViewModel> getApothecariesStaffs(int pharmacistId) {
        List<ApothecaryViewModel> apothecaryViewModels = new ArrayList<>();
        ApothecaryViewModel apothecaryViewModel = new ApothecaryViewModel();
        PharmacyStaff pharmacyStaff  = getApothecariesByPharmacistId(pharmacistId);
        List<PharmacyStaff> apothecaryPharmacyStaff = getApothecaryPharmacyStaffByPharmacist(pharmacyStaff.getPharmacyId());
        for (PharmacyStaff staff : apothecaryPharmacyStaff){
            List<ConcreteDrug> concreteDrugsForApothecary = concreteDrugService.getConcreteDrugsForApothecary(staff.getId());
            apothecaryViewModel.drugsInProgress = concreteDrugsForApothecary.size();
            apothecaryViewModel.apothecaryId = staff.getUserId();
            apothecaryViewModel.apothecaryFullName = userService.getUserById(staff.getUserId()).getFullName();
            apothecaryViewModels.add(apothecaryViewModel);
        }

        return apothecaryViewModels;
    }

    private List<PharmacyStaff> getApothecaryPharmacyStaffByPharmacist(int pharmacyId) {
        List<PharmacyStaff> pharmacyStaffs = getPharmacyStaffs();
        List<PharmacyStaff> apothecaryPharmacyStaffList = new ArrayList<>();
        for(PharmacyStaff pharmacyStaff : pharmacyStaffs){
            User user = userService.getUserById(pharmacyStaff.getUserId());
            Role role = roleService.getRoleById(user.getRoleId());
            if(pharmacyStaff.getPharmacyId() == pharmacyId && role.getName().equals(RolesNameEnum.APOTHECARY.toString())){
                apothecaryPharmacyStaffList.add(pharmacyStaff);
            }
        }

        return apothecaryPharmacyStaffList;
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
