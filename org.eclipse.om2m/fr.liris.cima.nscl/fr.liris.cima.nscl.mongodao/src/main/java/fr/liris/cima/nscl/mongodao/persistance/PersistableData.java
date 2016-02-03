package fr.liris.cima.nscl.mongodao.persistance;


import java.util.UUID;

/**
 * Created by Maxime on 07/01/2016.
 */
public class PersistableData {

    public PersistableData(String _id, String _etag) {
        this._id = _id;
        this._etag = _etag;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }

    private String _id, _etag;

    public PersistableData(){
        this._id = UUID.randomUUID().toString();
        this._etag = "";

    }

    @Override
    public String toString() {
        return "PersistableData{" +
                "_id='" + _id + '\'' +
                ", _etag='" + _etag + '\'' +
                '}';
    }
}
