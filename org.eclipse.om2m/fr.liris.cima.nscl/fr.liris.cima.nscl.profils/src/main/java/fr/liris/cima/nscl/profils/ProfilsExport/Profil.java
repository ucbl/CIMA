package fr.liris.cima.nscl.profils.profilsExport;


import fr.liris.cima.nscl.mongodao.persistance.Persistable;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;

/**
 * Created by Maxime on 02/02/2016.
 */
public class Profil implements Persistable {

    String name, description;

    String capabilities;

    PersistableData persistibleData;


    public Profil(String name, String description, String jsonCapabilities) {
        this.name = name;
        this.description = description;
        this.capabilities = jsonCapabilities;
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
        return capabilities;
    }

    public void setJsonCapabilities(String jsonCapabilities) {
        this.capabilities = jsonCapabilities;
    }

    public PersistableData getPersistableData(){
        return persistibleData;
    }
    public void setPersistableData(PersistableData p){
        persistibleData = p;
    }

    public String toJson(){
        return "{ \"_id\" : \""+this.persistibleData.get_id()+"\", \"name\" : \""+this.name+"\", \"description\" : \""+this.description+"\", \"capabilities\" : \""+this.capabilities+"\" }";
    }

    @Override
    public String toString() {
        return "Profil{" +
                "jsonCapabilities='" + capabilities + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
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
