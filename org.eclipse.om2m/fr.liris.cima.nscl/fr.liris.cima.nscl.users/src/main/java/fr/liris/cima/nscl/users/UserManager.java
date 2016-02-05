package fr.liris.cima.nscl.users;

import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import fr.liris.cima.nscl.users.UsersExport.User;
import fr.liris.cima.nscl.users.UsersExport.UserManagerInterface;

import java.util.Iterator;
import java.util.List;


import com.google.gson.*;

/**
 * Created by Maxime on 04/02/2016.
 */
public class UserManager implements UserManagerInterface{

    MongoDaoInterface mongoDaoInterface;


    public UserManager(MongoDaoInterface mongoDaoInterface) {
        this.mongoDaoInterface = mongoDaoInterface;
    }


    @Override
    public boolean checkLongin(User u) {

        try {
            List<User> lu = mongoDaoInterface.getAll(User.class);

            Iterator<User> it = lu.iterator();
            while (it.hasNext()) {
                if (it.next().equals(u))
                    return true;
            }
            return false;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkLoginFromJson(String json){

        Gson gson = new Gson();
        return this.checkLongin((User) gson.fromJson(json, User.class));
    }
}
