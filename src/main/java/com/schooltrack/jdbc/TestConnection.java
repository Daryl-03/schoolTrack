package com.schooltrack.jdbc;

import com.schooltrack.models.Classe;
import com.schooltrack.models.datasource.ClasseDAO;

import java.sql.Connection;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DBManager.getConnection()) {
            ClasseDAO classeDAO = new ClasseDAO();
            List<Classe> classes = classeDAO.readAll();
            for (Classe classe : classes) {
                System.out.println("Classe: " + classe.getNom());
                for (int i = 0; i < classe.getEleves().size(); i++) {
                    System.out.println("Eleve: " + classe.getEleves().get(i).getNom());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
