package fr.liris.cima.nscl.profils.profilsExport;

import java.util.List;

/**
 * Created by Maxime on 06/02/2016.
 */
public interface ProfileDeviceAssociatingManagerInterface {

    public boolean addProfileDeviceAssociating(ProfileDeviceAssociating p);
    public boolean addProfileDeviceAssociatingFromJson(String json);

    public List<ProfileDeviceAssociating> getAllProfileDeviceAssociating();
    public String getAllProfileDeviceAssociatingToJson();

    public List<ProfileDeviceAssociating> getProfileDeviceAssociating(String deviceId);
    public String getProfileDeviceAssociatingToJson(String deviceId);

    
    public boolean deleteProfileDeviceAssociatingFromSimpleJson(String json);
    public boolean deleteProfileDeviceAssociating(ProfileDeviceAssociating p);
}
