package fr.liris.cima.nscl.users.UsersExport;

/**
 * Created by Maxime on 04/02/2016.
 */
public interface UserManagerInterface {

    boolean checkLongin(User u);
    boolean checkLoginFromJson(String json);

}
