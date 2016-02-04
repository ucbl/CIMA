package fr.liris.cima.nscl.mongodao;

import com.google.gson.*;
import fr.liris.cima.nscl.mongodao.persistance.MongoDaoInterface;
import fr.liris.cima.nscl.mongodao.persistance.Persistable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manage data persistence using a dao in mongo data base
 * Created by Maxime on 02/02/2016.
 */
public class MongoDao implements MongoDaoInterface {

        private final String host;
        private final int port;
        private final String database;


        private Gson gson = new Gson();

        /**
         * Constructor, used to intialize the DAO before using
         * Parameteres are used to connect with resthart that should be conected to mongo databse
         * @param host : host where restheart is running
         * @param port : the port used by restheart
         * @param database : the database name used for the DAO
         */
        public MongoDao(String host, int port, String database) {
            this.host = host;
            this.port = port;
            this.database = database;
        }


        /**
         * Return the etag of a persisted object by getting it and extracting it etag
         * @param simpleClassName : the class of this object, should be the same name used by getCollecNameByClassName
         * @param _id : the id of the object, to get it
         * @return String : the etag
         */
        private String getEtagFromId(String simpleClassName, String _id){
            String r = callURL("http://"+host+":"+port+"/"+database+"/"+simpleClassName+"/"+_id);
            return this.extractEtagFromJson(r);
        }


    /**
     * Delete object from the database
     * @param p a persistable object
     * @return true if the object has been deleted, if not, return false
     * @throws IOException
     */
    public boolean delete(Persistable p) throws IOException {

        String url = "http://"+host+":"+port+"/"+database+"/"+this.getCollecNameByClassName(p)+"/"+p.getPersistableData().get_id();

        //sending to database

        try {


            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("If-Match",p.getPersistableData().get_etag()); //important to delete !
            conn.setDoOutput(true);

            conn.setRequestMethod("DELETE");


            try {
                //restheart normally give no result
                new InputStreamReader(conn.getInputStream());
            }catch (FileNotFoundException f){
                f.printStackTrace();
            }


            return conn.getResponseCode() == 204;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }


    /**
         * Persist an object if this object is not persisted yet ...
         * AND ONLY IN THIS CASE
         * @param o : the object that is wanted to be persist
         * @throws IOException //TODO : exception if there is already
         */
        public void persist(Persistable o) throws IOException {

            //serialize to json
            String json = gson.toJson(o, o.getClass());

            //add the _id
            json = json.substring(0, json.length() - 1);
            json += ",\"_id\":\""+o.getPersistableData().get_id()+"\"}";

            String url = "http://"+host+":"+port+"/"+database+"/"+this.getCollecNameByClassName(o);



            //sending to database

            try {


                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");


                String data =  json;
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.close();

                try {
                    //restheart normally give no result
                    new InputStreamReader(conn.getInputStream());
                }catch (FileNotFoundException f){ //If the collection not exist, create it.
                    this.createCollection(this.getCollecNameByClassName(o));
                    this.persist(o);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            //add the etag
            String etag = this.getEtagFromId(this.getCollecNameByClassName(o), o.getPersistableData().get_id());
            o.getPersistableData().set_etag(etag);
            System.out.println("ETAG RECUP : " + etag);

            this.save(o);

            System.out.println("APRE SAVE : " + o.getPersistableData().get_etag());


        }

    /**
     * Create a collection in mongo (standard collection without particular parameter)
     * @param collectionName the collection name
     */
    private void createCollection(String collectionName){

            try {

                String url = "http://"+host+":"+port+"/"+database+"/"+ collectionName;

                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("If-Match", o.getPersistableData().get_etag()); //important to update !
                conn.setDoOutput(true);

                conn.setRequestMethod("PUT");


                String data =  "";
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.close();

                new InputStreamReader(conn.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * Extract tag from Json
         * @param json
         * @return a String tag
         */
        private String extractEtagFromJson(String json){
            JsonElement jsonElement = new JsonParser().parse(json);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("_etag");
            String etag = jsonObject.get("$oid").toString();
            return etag.replace("\"", "");
        }

    /**
     * Get all objects of the giving type existing in the base
     * @param askedClass an object class
     * @param <T> ??
     * @return a list of object having the same type
     * @throws ClassNotFoundException if class is not found
     */
        public <T> List<T> getAll(Class<T> askedClass) throws ClassNotFoundException {

            List<T> res = new ArrayList<T>();
            //Class askedClass = Class.forName(classSimpleName);

            String url = "http://"+host+":"+port+"/"+database+"/"+ askedClass.getName();

            String result = callURL(url);

            JsonElement rootElement = new JsonParser().parse(result);
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonObject embebed = rootObject.getAsJsonObject("_embedded");
            JsonArray objectsList = embebed.getAsJsonArray("rh:doc");
            Iterator<JsonElement> it = objectsList.iterator();
            while(it.hasNext())
            {
                JsonElement object = it.next();
                T p = (T) gson.fromJson(object,  askedClass);
                ((Persistable) p).getPersistableData().set_etag(object.getAsJsonObject().get("_etag").toString());//UPDATE THE ETAG
                res.add(p);
            }
            return res;
        }

    /**
     * Template which returns all objects of the giving type existing in the base
     * @param object an object
     * @param <T>
     * @return a list of object having the same type
     * @throws ClassNotFoundException
     */
        public <T> List<T> getAll(T object) throws ClassNotFoundException {
            return (List<T>) this.getAll(object.getClass());
        }


        /**
         * Do not change
         * @param o an Object
         * @return the object class
         */
        private String getCollecNameByClassName(Object o){
            return o.getClass().getName();
        }


    /**
     * Save an object into the database
     * @param o Persistable object
     */
        public void save(Persistable o){
            //serialize to json
            String json = gson.toJson(o, o.getClass());

            //add the _id
            //json = json.substring(0, json.length() - 1);
            //json += ",\"_id\":\""+o.getPersistableData().get_id()+"\"}";

            String url = "http://"+host+":"+port+"/"+database+"/"+this.getCollecNameByClassName(o)+"/"+o.getPersistableData().get_id();


            System.out.println("TRYING WITH : ");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Urls : " + url);
            System.out.println("JSON : " + json);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            //sending to database

            try {


                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("If-Match", o.getPersistableData().get_etag()); //important to update !
                conn.setDoOutput(true);

                conn.setRequestMethod("PUT");


                String data =  json;
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.close();

                new InputStreamReader(conn.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    /**
     * Call an URL using GET method
     * @param myURL - the URL to call
     * @return the result
     */
    public static String callURL(String myURL) {
            StringBuilder sb = new StringBuilder();
            URLConnection urlConn = null;
            InputStreamReader in = null;
            try {
                URL url = new URL(myURL);
                urlConn = url.openConnection();
                if (urlConn != null)
                    urlConn.setReadTimeout(60 * 1000);
                if (urlConn != null && urlConn.getInputStream() != null) {
                    in = new InputStreamReader(urlConn.getInputStream(),
                            Charset.defaultCharset());
                    BufferedReader bufferedReader = new BufferedReader(in);
                    if (bufferedReader != null) {
                        int cp;
                        while ((cp = bufferedReader.read()) != -1) {
                            sb.append((char) cp);
                        }
                        bufferedReader.close();
                    }
                }
                in.close();
            } catch (Exception e) {
                throw new RuntimeException("Exception while calling URL:"+ myURL, e);
            }

            return sb.toString();
        }


}
