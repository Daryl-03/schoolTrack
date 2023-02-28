package com.schooltrack.jdbc;


import com.schooltrack.models.AnneeScolaire;
import com.schooltrack.models.EcheancierPaiement;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.models.datasource.EcheancierPaiementDAO;
import com.schooltrack.utils.Constantes;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DBManager.getConnection()) {
//            AnneeScolaire anneeScolaire = new AnneeScolaireDAO().readLast();
//            Constantes.CURRENT_YEAR = anneeScolaire;
//            EcheancierPaiementDAO echeancierPaiementDAO = new EcheancierPaiementDAO();
//            echeancierPaiementDAO.createEcheancierPaiementScolarite();
//            System.out.println("Echeancier de paiement de scolarité créé avec succès");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
