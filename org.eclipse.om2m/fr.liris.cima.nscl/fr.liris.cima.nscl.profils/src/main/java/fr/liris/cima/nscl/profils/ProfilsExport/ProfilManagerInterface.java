package fr.liris.cima.nscl.profils.profilsExport;

import java.util.List;

/**
 * Created by Maxime on 02/02/2016.
 */
public interface ProfilManagerInterface {

    List<Profil> getAllProfils();
    String getAllProfilsToJson();

    Profil saveNewProfil(Profil p);
    String saveNewProfilFromJson(String json);

    void updateProfil(Profil p);
    void updateProfilFromJson(String json);

    boolean deleteProfil(Profil p);
    boolean deleteProfilFromJson(String json);

    Profil profilFromJson(String json);
    String jsonFromProfil(Profil p);
}
