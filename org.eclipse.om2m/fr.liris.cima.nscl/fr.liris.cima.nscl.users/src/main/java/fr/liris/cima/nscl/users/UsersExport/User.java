package fr.liris.cima.nscl.users.UsersExport;

import fr.liris.cima.nscl.mongodao.persistance.*;

/**
 * Define an user of CIMA as a persistable object so it can be save in mongo data base
 * Created by Maxime on 04/02/2016.
 */
public class User implements Persistable{

    String username, userpassword;

    PersistableData persistableData;


    /**
     * Create a new user
     * @param username  the user name
     * @param userpassword the user password
     */
    public User(String username, String userpassword) {
        this.username = username;
        this.userpassword = userpassword;
        this.persistableData = new PersistableData();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;
        return userpassword.equals(user.userpassword);

    }

    /**
     * Hash user data
     * @return an hashcode
     */
    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + userpassword.hashCode();
        return result;
    }

    public PersistableData getPersistableData(){
        return persistableData;
    }
    public void setPersistableData(PersistableData p){
        persistableData = p;
    }

    public String toStringPersistance(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "userpassword='" + userpassword + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
