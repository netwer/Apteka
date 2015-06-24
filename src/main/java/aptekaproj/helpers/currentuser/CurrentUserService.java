package aptekaproj.helpers.currentuser;

/**
 * Created by Admin on 23.06.2015.
 */
public interface CurrentUserService {
    boolean canAccessUser(CurrentUser currentUser, Long userId);
}
