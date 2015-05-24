package aptekaproj.services;

import aptekaproj.controllers.repository.IRolesRepository;
import aptekaproj.models.Roles;
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
    public Roles getRoleByName(String name){
        List<Roles> rolesList = (List<Roles>) repository.findAll();
        for(Roles roles: rolesList){
            if(roles.getName().equals(name)){
                return roles;
            }
        }
        return new Roles();
        //return rolesList;
    }

    @Transactional
    public void saveRole(Roles roles){
        repository.save(roles);
    }

    @Transactional
    public String getRoleName(int id){
        return repository.findOne(id).getName();
    }
}
