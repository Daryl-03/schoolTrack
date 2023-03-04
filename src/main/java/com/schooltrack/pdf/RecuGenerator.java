package com.schooltrack.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.schooltrack.exceptions.PDFException;
import com.schooltrack.models.AnneeScolaire;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.Paiement;
import com.schooltrack.models.Rubrique;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.models.datasource.EleveDAO;
import com.schooltrack.models.datasource.PaiementDAO;
import com.schooltrack.models.datasource.RubriqueDAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class RecuGenerator {
    
    /**
     * Génère un reçu au format pdf à partir d'un paiement
     *
     * @param path     le chemin vers le fichier
     * @param paiement le paiement
     * @throws PDFException
     */
    public void generate(String path, Paiement paiement) throws PDFException {
        Document document = new Document();
        
        try {
            Eleve eleve = new EleveDAO().read(paiement.getId_eleve());
            Rubrique rubrique = new RubriqueDAO().read(paiement.getId_rubrique());
            AnneeScolaire anneeScolaire = new AnneeScolaireDAO().read(paiement.getId_annee());
            PdfWriter.getInstance(document, new FileOutputStream(path));
            // open document
            document.open();
            
            // set font for document
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            
            // add school name to document
            Paragraph schoolParagraph = new Paragraph("Etablissement XYZ", font);
            schoolParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(schoolParagraph);
            
            // add receipt header to document
            Paragraph receiptHeader = new Paragraph("REÇU DE PAIEMENT", font);
            receiptHeader.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(receiptHeader);
            
             // Ajout du contenu
            document.add(new Paragraph("--------------------------------------------"));
            document.add(new Paragraph("Numéro de reçu: "+paiement.getNumero()));
            Paragraph dateParagraph = new Paragraph("Date: "+paiement.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            dateParagraph.setSpacingAfter(10);
            document.add(dateParagraph);
            
            document.add(new Paragraph("Nom de l'élève: "+ eleve.getNom()+" "+eleve.getPrenom()));
            document.add(new Paragraph("Année scolaire: "+anneeScolaire.getIntitule()));
            document.add(new Paragraph("--------------------------------------------"));
            document.add(new Paragraph("Détails de paiement:"));
            
            int tranche = 0;
            if(rubrique.getIntitule().equals("Scolarité"))
                tranche = new PaiementDAO().getTranche(paiement);
            document.add(new Paragraph(rubrique.getIntitule()+ (tranche!=0?" Tranche "+tranche:"") +": "+paiement.getMontant()+" FCFA"));
        } catch (DocumentException e) {
            throw new PDFException(e);
        } catch (FileNotFoundException e) {
            throw new PDFException(e);
        } catch (Exception e) {
            throw new PDFException(e);
        } finally {
            document.close();
        }
        
    }
}
