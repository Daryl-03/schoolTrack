package com.schooltrack.csv;

import com.schooltrack.exceptions.CSVReaderException;
import com.schooltrack.models.Eleve;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EleveReader implements CSVReader<Eleve> {
    
    @Override
    public List<String> readFile(File file) throws CSVReaderException {
        return readFile(file, 0);
    }
    
    /**
     * lis des lignes depuis un fichier csv à partir d'une certaine ligne offset et retourne une liste de chaînes de caractères
     * @param file
     * @param offset
     * @return une liste de chaînes de caractères
     */
    public List<String> readFile(File file, int offset) throws CSVReaderException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (offset-- > 0) continue;
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new CSVReaderException(e.getMessage());
        }
        return lines;
    }
    
    /**
     * Traite les données contenues dans les lignes
     * @param lines une liste de chaînes de caractères au format csv
     * @return une liste de tableau chaînes de caractères représentant les données traitées
     */
    @Override
    public List<String[]> getData(List<String> lines) {
        List<String[]> data = new ArrayList<>();
        for (String line : lines) {
            data.add(line.split(";"));
        }
        return data;
    }
    
    /**
     * Crée des objets contact à partir de données csv traitées
     * @param data Une liste de chaîne de caractères contenant les données traitées
     * @return une liste de contacts
     */
    @Override
    public List<Eleve> csvToObjects(List<String[]> data) throws CSVReaderException {
        List<Eleve> eleves = new ArrayList<>();
        try{
            for (String[] row : data) {
                // vérifier si les données sont valides
                String nom = row[0];
                String prenom = row[1];
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CSVReaderException("données manquantes");
        } catch (Exception e) {
            throw new CSVReaderException(e.getMessage());
        }
        return null;
    }
}
