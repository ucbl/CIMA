package fr.liris.cima.nscl.profils;


import com.google.gson.*;
import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;
import fr.liris.cima.nscl.profils.profilsExport.ProfileMatching;
import fr.liris.cima.nscl.profils.profilsExport.ProfileMatchingManagerInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxime on 06/02/2016.
 */
public class ProfileMatchingManager implements ProfileMatchingManagerInterface {

    MongoDaoInterface mongoDaoInterface;
    Gson gson;

    public ProfileMatchingManager(MongoDaoInterface mongoDaoInterface) {
        this.mongoDaoInterface = mongoDaoInterface;
        gson = new Gson();
    }

    public boolean addProfileMatching(ProfileMatching p){

        try {
            //added to make persistable profiles from the interface that have not PersistableData from constructor
            p.setPersistableData(new PersistableData());
            mongoDaoInterface.persist(p);
            return true;
        }catch(IOException i){
            System.out.println("Error : impossible d'enregistrer l'objet en mémoire : " );
            i.printStackTrace();
            return false;
        }catch(Exception e){
            System.out.println("Error : impossible d'enregistrer l'objet en mémoire : " );
            e.printStackTrace();
            return false;
        }
    }
    public boolean addProfileMatchingFromJson(String json){
        ProfileMatching p = gson.fromJson(json, ProfileMatching.class);
        return this.addProfileMatching(p);
    }

    public List<ProfileMatching> getAllProfileMatching(){

        try {
            return mongoDaoInterface.getAll(ProfileMatching.class);
        }catch(ClassNotFoundException c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return new ArrayList<ProfileMatching>();
        }catch(Exception c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return new ArrayList<ProfileMatching>();
        }


    }

    public String getAllProfileMatchingToJson(){
        try {
            List<ProfileMatching> lp = this.getAllProfileMatching();
            String res = "[";
            for (ProfileMatching p : lp) {
                res += gson.toJson(p);
                res += ", ";
            }
            res = res.substring(0, res.length() - 2);
            res += "]";
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return "[]";
        }
    }

    public List<ProfileMatching> getProfileMatching(String deviceId){
        List<ProfileMatching> lp = this.getAllProfileMatching();
        //Java 8
        //return lp.stream().filter(p -> p.getDeviceId().equals(deviceId)).collect(Collectors.toList());
        //Java 7
        List<ProfileMatching> res = new ArrayList<>();
        Iterator<ProfileMatching> it = lp.iterator();
        while(it.hasNext()){
            ProfileMatching pm = it.next();
            if(deviceId.equals(pm.getDeviceId()))
                res.add(pm);
        }
        return res;
    }


    public String getProfileMatchingToJson(String deviceId){
        try {
            List<ProfileMatching> lp = this.getProfileMatching(deviceId);
            String res = "[";
            for (ProfileMatching p : lp) {
                res += gson.toJson(p);
                res += ", ";
            }
            res = res.substring(0, res.length() - 2);
            res += "]";
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return "[]";
        }
    }

    //unused
    public List<ProfileMatching> getProfileMatchingFromJson(String json){

        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  jobject = jelement.getAsJsonObject();
        String id = jobject.get("deviceId").toString();
        id = id.replace("\"", "");
        return this.getProfileMatching(id);
    }



    @Override
    public boolean deleteProfileMatching(ProfileMatching p) {
        try {
            return mongoDaoInterface.delete(p);
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProfileMatchingFromSimpleJson(String json) {

        //{"_id":"fghjklmjhgf","_etag":"fygjlkmlklkhjgf"}

        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  jobject = jelement.getAsJsonObject();
        String id = jobject.get("_id").toString();
        id = id.replace("\"", "");
        String etag = jobject.get("_etag").toString();
        etag = etag.replace("\"", "");

        ProfileMatching p = new ProfileMatching();
        p.setPersistableData(new PersistableData(id, etag));

        try {
            return mongoDaoInterface.delete(p);
        }catch(IOException i){
            i.printStackTrace();
            return false;
        }

    }


}
