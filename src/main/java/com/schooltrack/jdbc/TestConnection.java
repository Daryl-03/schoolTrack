package com.schooltrack.jdbc;

import com.schooltrack.Main;
import com.schooltrack.models.Bulletin;
import com.schooltrack.models.Classe;
import com.schooltrack.models.Matiere;
import com.schooltrack.models.Note;
import com.schooltrack.models.datasource.BulletinDAO2;
import com.schooltrack.models.datasource.ClasseDAO;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DBManager.getConnection()) {
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
