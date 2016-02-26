package fr.liris.cima.nscl.profils;


import com.google.gson.*;
import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;
import fr.liris.cima.nscl.profils.profilsExport.ProfileDeviceAssociating;
import fr.liris.cima.nscl.profils.profilsExport.ProfileDeviceAssociatingManagerInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxime on 06/02/2016.
 */
public class ProfileDeviceAssociatingManager implements ProfileDeviceAssociatingManagerInterface {

    MongoDaoInterface mongoDaoInterface;
    Gson gson;

    public ProfileDeviceAssociatingManager(MongoDaoInterface mongoDaoInterface) {
        this.mongoDaoInterface = mongoDaoInterface;
        gson = new Gson();

        this.removeAllProfileDeviceAssociating();
    }


    private void removeAllProfileDeviceAssociating(){
        List<ProfileDeviceAssociating> lpm = this.getAllProfileDeviceAssociating();
        Iterator<ProfileDeviceAssociating> it = lpm.iterator();
        while(it.hasNext())
        {
            ProfileDeviceAssociating p = it.next();
            p.getPersistableData().set_etag(p.getPersistableData().get_etag().split(":")[1].replace("\"", "").replace("}", ""));
            this.deleteProfileDeviceAssociating(p);
        }
    }



    public boolean addProfileDeviceAssociating(ProfileDeviceAssociating p){

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
    public boolean addProfileDeviceAssociatingFromJson(String json){


        boolean r = true;

        JsonElement jelement = new JsonParser().parse(json);
        JsonArray  jArray = jelement.getAsJsonArray();
        Iterator<JsonElement> it = jArray.iterator();
        while(it.hasNext())
        {
            JsonObject jsonObject = it.next().getAsJsonObject();
            ProfileDeviceAssociating p = gson.fromJson(jsonObject, ProfileDeviceAssociating.class);
            if(this.addProfileDeviceAssociating(p) == false)
                r = false;
        }

        return r;





    }

    public List<ProfileDeviceAssociating> getAllProfileDeviceAssociating(){

        try {
            return mongoDaoInterface.getAll(ProfileDeviceAssociating.class);
        }catch(ClassNotFoundException c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return new ArrayList<ProfileDeviceAssociating>();
        }catch(Exception c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return new ArrayList<ProfileDeviceAssociating>();
        }


    }

    public String getAllProfileDeviceAssociatingToJson(){
        try {
            List<ProfileDeviceAssociating> lp = this.getAllProfileDeviceAssociating();
            String res = "[";
            for (ProfileDeviceAssociating p : lp) {
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

    public List<ProfileDeviceAssociating> getProfileDeviceAssociating(String deviceId){
        List<ProfileDeviceAssociating> lp = this.getAllProfileDeviceAssociating();
        //Java 8
        //return lp.stream().filter(p -> p.getDeviceId().equals(deviceId)).collect(Collectors.toList());
        //Java 7
        List<ProfileDeviceAssociating> res = new ArrayList<>();
        Iterator<ProfileDeviceAssociating> it = lp.iterator();
        while(it.hasNext()){
            ProfileDeviceAssociating pm = it.next();
            if(deviceId.equals(pm.getDeviceId()))
                res.add(pm);
        }
        return res;
    }


    public String getProfileDeviceAssociatingToJson(String deviceId){
        try {
            List<ProfileDeviceAssociating> lp = this.getProfileDeviceAssociating(deviceId);
            String res = "[";
            for (ProfileDeviceAssociating p : lp) {
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
    public List<ProfileDeviceAssociating> getProfileDeviceAssociatingFromJson(String json){

        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  jobject = jelement.getAsJsonObject();
        String id = jobject.get("deviceId").toString();
        id = id.replace("\"", "");
        return this.getProfileDeviceAssociating(id);
    }



    @Override
    public boolean deleteProfileDeviceAssociating(ProfileDeviceAssociating p) {
        try {
            return mongoDaoInterface.delete(p);
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProfileDeviceAssociatingFromSimpleJson(String json) {

        boolean r = true;

        JsonElement jelement = new JsonParser().parse(json);
        JsonArray  jArray = jelement.getAsJsonArray();
        Iterator<JsonElement> it = jArray.iterator();
        while(it.hasNext())
        {
            JsonObject jsonObject = it.next().getAsJsonObject();
            String id = jsonObject.get("_id").toString();
            id = id.replace("\"", "");
            String etag = jsonObject.get("_etag").toString();
            etag = etag.replace("\"", "");

            ProfileDeviceAssociating p = new ProfileDeviceAssociating();
            p.setPersistableData(new PersistableData(id, etag));

            try {
                if(mongoDaoInterface.delete(p) == false)
                    r = false;
            }catch(IOException i){
                i.printStackTrace();
                return false;
            }
        }

        return r;

    }


}
