package aptekaproj.services;

import aptekaproj.ViewModels.UserViewModel;
import aptekaproj.controllers.repository.IPharmacyStaffRepository;
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

    public List<UserViewModel> getPharmacists(int pharmacy_id) {
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
}
