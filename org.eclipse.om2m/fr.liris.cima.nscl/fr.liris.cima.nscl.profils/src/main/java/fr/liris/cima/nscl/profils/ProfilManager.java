package fr.liris.cima.nscl.profils;

import fr.liris.cima.nscl.profils.profilsExport.Profil;
import fr.liris.cima.nscl.profils.profilsExport.ProfilManagerInterface;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;

import java.io.IOException;
import java.util.List;


import com.google.gson.*;

import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;


/**
 * Created by Maxime on 02/02/2016.
 */
public class ProfilManager implements ProfilManagerInterface {

    MongoDaoInterface mongoDaoInterface;


    public ProfilManager(MongoDaoInterface mongoDaoInterface) {
        this.mongoDaoInterface = mongoDaoInterface;
    }

    @Override
    public List<Profil> getAllProfils() {
        try {
            return mongoDaoInterface.getAll(Profil.class);
        }catch(ClassNotFoundException c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return null;
        }catch(Exception c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAllProfilsToJson() {

        Gson gson = new Gson();
        List<Profil> lp = this.getAllProfils();
        String res = "[";
        for(Profil p : lp) {
            res += gson.toJson(p);
            res += ", ";
        }
        res = res.substring(0, res.length() - 2);
        res += "]";
        return res;
    }

    @Override
    public Profil saveNewProfil(Profil p) {
        try {
            //added to make persistable profiles from the interface that have not PersistableData from constructor
            p.setPersistableData(new PersistableData());
            mongoDaoInterface.persist(p);
            return p; //to show that p was modified
        }catch(IOException i){
            System.out.println("Error : impossible d'enregistrer l'objet en mémoire : " );
            i.printStackTrace();
            return null;
        }catch(Exception e){
            System.out.println("Error : impossible d'enregistrer l'objet en mémoire : " );
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String saveNewProfilFromJson(String json) {
        return this.jsonFromProfil(this.saveNewProfil(this.profilFromJson(json)));
    }

    @Override
    public void updateProfil(Profil p) {
        try {
            mongoDaoInterface.save(p);
        }catch(Exception e){
            System.out.println("Error : impossible de mettre a jour l'ibjet : " + p);
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfilFromJson(String json) {
        this.updateProfil(this.profilFromJson(json));
    }

    @Override
    public boolean deleteProfil(Profil p) {
        try {
            return mongoDaoInterface.delete(p);
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProfilFromSimpleJson(String json) {

        //{"_id":"fghjklmjhgf","_etag":"fygjlkmlklkhjgf"}

        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  jobject = jelement.getAsJsonObject();
        String id = jobject.get("_id").toString();
        id = id.replace("\"", "");
        String etag = jobject.get("_etag").toString();
        etag = etag.replace("\"", "");

        Profil p = new Profil();
        p.setPersistableData(new PersistableData(id, etag));

        try {
            return mongoDaoInterface.delete(p);
        }catch(IOException i){
            i.printStackTrace();
            return false;
        }

    }

    @Override
    public Profil profilFromJson(String json) {
        return (Profil) new Gson().fromJson(json, Profil.class);
    }

    @Override
    public String jsonFromProfil(Profil p) {
        return p.toJson();
    }

}
