package fr.liris.cima.nscl.users;

import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import fr.liris.cima.nscl.users.UsersExport.User;
import fr.liris.cima.nscl.users.UsersExport.UserManagerInterface;

import java.io.IOException;
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


        //TODO : remove !!
        User u = new User("admin", "21232f297a57a5a743894a0e4a801fc3");
        try {
            mongoDaoInterface.persist(u);
        }
        catch(IOException e)
        {
            System.out.println("ERROR : IMPOSSIBLE D'AJOUTER L'UTILISATEUR ADMIN");
            e.printStackTrace();
        }
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
