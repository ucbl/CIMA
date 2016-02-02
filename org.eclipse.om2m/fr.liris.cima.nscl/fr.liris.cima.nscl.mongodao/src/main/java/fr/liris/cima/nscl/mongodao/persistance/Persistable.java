package fr.liris.cima.nscl.mongodao.persistance;

/**
 * Created by Maxime on 29/12/2015.
 */
public interface Persistable {

    public PersistableData getPersistableData();
    public void setPersistableData(PersistableData p);

    public String toStringPersistance();

}
