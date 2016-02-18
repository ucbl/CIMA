package fr.liris.cima.nscl.mongodao.persistance;

import java.io.IOException;
import java.util.List;

/**
 * The mongoDB interface
 * Created by Maxime on 02/02/2016.
 */
public interface MongoDaoInterface {


    /**
     * Persist an object if this object is not perisited yet ...
     * AND ONLY IN THIS CASE
     * @param o : the object that is wanted to be persist
     * @throws IOException //TODO : exception s'il existe déjà
     */
    public void persist(Persistable o) throws IOException;


    public void save(Persistable o);

    public <T> List<T> getAll(Class<T> askedClass) throws ClassNotFoundException;

    public <T> List<T> getAll(T object) throws ClassNotFoundException;

    public boolean delete(Persistable p) throws IOException;






}
