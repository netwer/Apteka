package hello.Services;

import hello.Helpers.Initializator;
import hello.Model.Role;
import hello.Model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 24.12.14
 * Time: 1:04
 * To change this template use File | Settings | File Templates.
 */
public class UserService {

    public User getUserById (long id){
        for (final User user : Initializator.getUsers()) {
            if (user.getId() != id) continue;
            return user;
        }
        return null;
    }
}
