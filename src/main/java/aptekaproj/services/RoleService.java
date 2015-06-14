package aptekaproj.services;

import aptekaproj.controllers.repository.IRolesRepository;
import aptekaproj.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 */
@Service
public class RoleService{

    @Autowired
    private IRolesRepository repository;


    @Transactional
    public Role GetRoleByName(String name){
        List<Role> roleList = (List<Role>) repository.findAll();
        for(Role role : roleList){
            if(role.getName().equals(name)){
                return role;
            }
        }
        return new Role();
        //return rolesList;
    }

    @Transactional
    public void SaveRole(Role role){
        repository.save(role);
    }

    @Transactional
    public String GetRoleName(int id){
        return repository.findOne(id).getName();
    }
}
