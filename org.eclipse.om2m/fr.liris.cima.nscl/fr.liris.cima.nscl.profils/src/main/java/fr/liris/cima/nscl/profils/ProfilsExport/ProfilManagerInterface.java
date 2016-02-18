package fr.liris.cima.nscl.profils.profilsExport;

import java.util.List;

/**
 * Interface to manage profile
 * Created by Maxime on 02/02/2016.
 */
public interface ProfilManagerInterface {

    /**
     * Function to get all profile
     * @return list of all profile
     */
    List<Profil> getAllProfils();

    /**
     * Convert all profile into json file
     * @return String json which describe all profile
     */
    String getAllProfilsToJson();

    /**
     * Save a new profile
     * @param p a profile
     * @return the profile saved
     */
    Profil saveNewProfil(Profil p);

    /**
     * Save new profile into json structure
     * @param json a String Json
     * @return the String json structure updated with the new profile
     */
    String saveNewProfilFromJson(String json);

    /**
     * Update an existing profile
     * @param p an profile
     */
    void updateProfil(Profil p);

    /**
     * Update the profile in a string json structure
     * @param json  the json structure string which contains the profile to update
     */
    void updateProfilFromJson(String json);

    /**
     * Delete the giving profile p
     * @param p the profile
     * @return true if the profile has been deleted, else return false
     */
    boolean deleteProfil(Profil p);

    /**
     * Delete the given profile in the string json structure
     * @param json the string json from which we have to delete the profil
     * @return true if the profile has been deleted, else return false
     */
    boolean deleteProfilFromSimpleJson(String json);

    /**
     * Get the profile from the string json
     * @param json String json
     * @return the extract profile
     */
    Profil profilFromJson(String json);

    /**
     * Encode profile into a String json
     * @param p profile
     * @return the encoded profile in json string
     */
    String jsonFromProfil(Profil p);


}
