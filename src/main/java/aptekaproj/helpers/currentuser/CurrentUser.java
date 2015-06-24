package aptekaproj.helpers.currentuser;

import aptekaproj.models.User;
import aptekaproj.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by Admin on 23.06.2015.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    @Autowired
    private RoleService roleService;


    public CurrentUser(User user) {
        super(user.getLogin(), user.getHash(), AuthorityUtils.createAuthorityList(user.role.getName()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return (user.getId());
    }

    public String getRole() {
        return user.role.getName();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}