package hello.Services;

import hello.Helpers.Initializator;
import hello.Model.Role;
import hello.Model.User;
import hello.Model.UserRole;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class RoleService {

    public String getRole (long userId){
        long roleId = 0;
        for (final UserRole userRole : Initializator.getUserRoles()) {
            if (userRole.getUserId() != userId) continue;
            roleId = userRole.getRoleId();
        }

        for (final Role role : Initializator.getRoles()) {
            if (role.getId() != roleId) continue;
            return role.getName();
        }
        return null;
    }
}
