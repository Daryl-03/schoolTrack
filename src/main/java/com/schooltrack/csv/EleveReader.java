package com.schooltrack.csv;

import com.schooltrack.exceptions.CSVReaderException;
import com.schooltrack.models.Eleve;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
     * Crée des objets Eleve à partir de données csv traitées
     * @param data Une liste de chaîne de caractères contenant les données traitées
     * @return une liste d'Eleve
     */
    @Override
    public List<Eleve> csvToObjects(List<String[]> data) throws CSVReaderException {
        List<Eleve> eleves = new ArrayList<>();
        try{
            for (String[] row : data) {
                String nom = row[0];
                String prenom = row[1];
                String sexe = row[2];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateNaissance = formatter.parse(row[3], LocalDate::from);
                String lieuNaissance = row[4];
                String adresse = row[5];
                String telephone = row[6];
                String email = row[7];
                
                //vérifier si les données sont valides
                if (nom.isBlank() || prenom.isBlank())
                    continue;
                if(!sexe.equals("Masculin") && !sexe.equals("Féminin"))
                    continue;
                if (lieuNaissance.isBlank() || adresse.isBlank() || telephone.isBlank() || email.isBlank())
                    continue;
                // checker le format de l'email
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
                    continue;
                // checker le format du numéro de téléphone
                if (!telephone.matches("^[0-9]{7,}$"))
                    continue;
                if (dateNaissance.isAfter(LocalDate.now()))
                    continue;
                    
                Eleve eleve = new Eleve();
                eleve.setNom(nom);
                eleve.setPrenom(prenom);
                eleve.setSexe(sexe);
                eleve.setDateDeNaissance(dateNaissance);
                eleve.setLieuDeNaissance(lieuNaissance);
                eleve.setAdresse(adresse);
                eleve.setNumtelephone(telephone);
                eleve.setEmail(email);
                eleves.add(eleve);
            }
        } catch (DateTimeParseException e) {
            throw new CSVReaderException("date invalide");
        } catch (IndexOutOfBoundsException e) {
            throw new CSVReaderException("données manquantes");
        } catch (Exception e) {
            throw new CSVReaderException(e.getMessage());
        }
        return eleves;
    }
}
