package aptekaproj.controllers.auth;

import aptekaproj.helpers.currentuser.CurrentUser;
import aptekaproj.models.User;
import aptekaproj.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Admin on 23.06.2015.
 */
@ControllerAdvice
public class CurrentUserControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserControllerAdvice.class);

    @Autowired
    private UserService userService;
    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
        if(authentication == null) {
            return null;
        }
        System.out.println(authentication.getPrincipal());
        User user = userService.getUserByLogin(authentication.getPrincipal().toString());
        if(user == null){
            return new CurrentUser(new User());
        }
        return new CurrentUser(user);
                //(CurrentUser) authentication.getPrincipal();
        //return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }


}