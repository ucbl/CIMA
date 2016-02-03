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
        List<Profil> lp = this.getAllProfils();
        String res = "[";
        for(Profil p : lp)
            res+= p.toJson() + ", ";
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
        return false;//TODO
    }

    @Override
    public boolean deleteProfilFromJson(String json) {
        return this.deleteProfil(this.profilFromJson(json));
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
