package ProfilsExport;

import java.util.List;

/**
 * Created by Maxime on 02/02/2016.
 */
public interface ProfilManagerInterface {

    List<Profil> getAllProfils();

    Profil saveNewProfil(Profil p);

    void updateProfil(Profil p);

    boolean deleteProfil(Profil p);
}
