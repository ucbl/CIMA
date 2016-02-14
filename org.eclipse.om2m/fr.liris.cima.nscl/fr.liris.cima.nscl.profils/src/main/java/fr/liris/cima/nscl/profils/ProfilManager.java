package fr.liris.cima.nscl.profils;

import com.google.gson.*;
import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;
import fr.liris.cima.nscl.profils.profilsExport.Profil;
import fr.liris.cima.nscl.profils.profilsExport.ProfilManagerInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
            return new ArrayList<Profil>();
        }catch(Exception c){
            System.out.println("Error : impossible de trouver la classe à rcéupérer dans la base mongo.");
            c.printStackTrace();
            return new ArrayList<Profil>();
        }
    }

    @Override
    public String getAllProfilsToJson() {

        Gson gson = new Gson();
        List<Profil> lp = this.getAllProfils();

        if(lp.isEmpty())
            return "[]";
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
        Profil p = (Profil) new Gson().fromJson(json, Profil.class);
        //To correct the proble of added "objectid" or $oid in the json from the interface
        if(json.contains("$oid")){
            JsonElement jsonElement = new JsonParser().parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject etagObject = jsonObject.getAsJsonObject("_etag");
            JsonObject persistObject = jsonObject.getAsJsonObject("persistibleData");
            String _etag =  persistObject.get("_etag").toString();
            _etag = _etag.split(":")[1].replace("}", "").replace("\"", "").replace("\\", "");
            System.out.println("ETAG : " + _etag);
            p.getPersistableData().set_etag(_etag);
        }
        return p;
    }

    @Override
    public String jsonFromProfil(Profil p) {
        return p.toJson();
    }

}
