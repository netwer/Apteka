package aptekaproj.models;

import aptekaproj.helpers.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 29.03.2015.
 */
@Service
public class UserService {

    @Autowired
    private IUsersRepository repository;

    public Users userInDb(String login, String password){
        List<Users> usersList = (List<Users>) repository.findAll();
        for(Users user : usersList){
            if(user.getLogin().equals(login) && user.getHash().equals(Hash.getHash(password + user.getSalt()))){
                return user;
            }
        }
        return null;
    }

    public String getUserNameById(int id){
        return repository.findOne(id).getName();
    }
}
