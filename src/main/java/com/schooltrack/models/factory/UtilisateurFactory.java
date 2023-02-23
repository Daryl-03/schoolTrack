package com.schooltrack.models.factory;

import com.schooltrack.models.Administrateur;
import com.schooltrack.models.Caissier;
import com.schooltrack.models.Secretaire;
import com.schooltrack.models.Utilisateur;

public class UtilisateurFactory {
    public static Utilisateur getUtilisateur(String type) {
        if (type.equals("Secretaire") ){
            return new Secretaire();
        } else if (type.equals("Caissier")) {
            return new Caissier();
        } else if (type.equals("Administrateur")) {
            return new Administrateur();
        }
        return null;
    }
}
