package fr.liris.cima.nscl.profils.profilsExport;


import fr.liris.cima.nscl.mongodao.persistance.Persistable;
import fr.liris.cima.nscl.mongodao.persistance.PersistableData;

/**
 * This class is responsible for matching profiles and devices
 * Created by Maxime on 06/02/2016.
 */
public class ProfileDeviceAssociating implements Persistable {

    String profileId, deviceId;


    PersistableData persistibleData;


    public ProfileDeviceAssociating() {
    }

    public ProfileDeviceAssociating(String profileId, String deviceId) {
        this.profileId = profileId;
        this.deviceId = deviceId;
        this.persistibleData = new PersistableData();
    }

    public PersistableData getPersistableData() {
        return persistibleData;
    }

    public void setPersistableData(PersistableData p) {
        persistibleData = p;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        ProfileDeviceAssociating that = (ProfileDeviceAssociating) object;

        if (!profileId.equals(that.profileId)) return false;
        return deviceId.equals(that.deviceId);

    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + profileId.hashCode();
        result = 31 * result + deviceId.hashCode();
        return result;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String toStringPersistance() {
        return "Profil : {\n" +
                " Persistance : {\"" +
                "  " + persistibleData.toString() + "\n" +
                "  }\n" +
                " Data : {\n" +
                "   " + this.toString() + "" +
                " }";
    }


}
