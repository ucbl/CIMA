package fr.liris.cima.nscl.users.UsersExport;

/**
 * Interface to manage CIMA user
 * Created by Maxime on 04/02/2016.
 */
public interface UserManagerInterface {

    /**
     * To check if an user is already logged
     * @param u the user
     * @return true if the user is data base, if not, false
     */
    boolean checkLongin(User u);
    boolean checkLoginFromJson(String json);

}
