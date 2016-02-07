package fr.liris.cima.nscl.profils.profilsExport;

import java.util.List;

/**
 * Created by Maxime on 06/02/2016.
 */
public interface ProfileMatchingManagerInterface {

    public boolean addProfileMatching(ProfileMatching p);
    public boolean addProfileMatchingFromJson(String json);

    public List<ProfileMatching> getAllProfileMatching();
    public String getAllProfileMatchingToJson();

    public List<ProfileMatching> getProfileMatching(String deviceId);
    public String getProfileMatchingToJson(String deviceId);

    
    public boolean deleteProfileMatchingFromSimpleJson(String json);
    public boolean deleteProfileMatching(ProfileMatching p);
}
