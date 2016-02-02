package fr.liris.cima.nscl.profils.profilsExport;


import fr.liris.cima.nscl.mongodao.persistance.Persistable;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;

/**
 * Created by Maxime on 02/02/2016.
 */
public class Profil implements Persistable {

    String name, description;

    String jsonCapabilities;

    PersistableData persistibleData;


    public Profil(String name, String description, String jsonCapabilities) {
        this.name = name;
        this.description = description;
        this.jsonCapabilities = jsonCapabilities;
        persistibleData = new PersistableData();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJsonCapabilities() {
        return jsonCapabilities;
    }

    public void setJsonCapabilities(String jsonCapabilities) {
        this.jsonCapabilities = jsonCapabilities;
    }

    public PersistableData getPersistableData(){
        return persistibleData;
    }
    public void setPersistableData(PersistableData p){
        persistibleData = p;
    }


    public String toStringPersistance(){
        return "Profil : {\n" +
                " Persistance : {\"" +
                "  "+persistibleData.toString() +"\n" +
                "  }\n" +
                " Data : {\n" +
                "   "+this.toString()+"" +
                " }";
    }
}
