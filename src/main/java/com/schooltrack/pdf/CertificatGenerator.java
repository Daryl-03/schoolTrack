package com.schooltrack.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.schooltrack.exceptions.PDFException;
import com.schooltrack.models.Eleve;
import com.schooltrack.models.datasource.EleveDAO;
import com.schooltrack.utils.Constantes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Génère un certificat de scolarité pour un élève
 * @author schooltrack
 * @version 1.0
 */
public class CertificatGenerator {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLDITALIC);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    
    /**
     * Génère un certificat de scolarité pour un élève en utilisant le chemin vers le fichier
     * @param path le chemin vers le fichier
     * @param eleve l'élève
     */
     public static void generate(String path, Eleve eleve) throws PDFException {
        Document document = new Document();
         try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            // création du certificat
//            String logoPath = "logo.png";
    //        Image logo = Image.getInstance(logoPath);
    //        logo.setAlignment(Element.ALIGN_CENTER);
    //        logo.scaleAbsolute(100, 100);
    //        document.add(logo);
    
            // Ajout du titre
            Paragraph title = new Paragraph("Certificat de scolarité", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
    
            // Ajout de la sous-titre
            Paragraph subtitle = new Paragraph("Année scolaire " + Constantes.CURRENT_YEAR.getIntitule(), SUBTITLE_FONT);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingBefore(10f);
            subtitle.setSpacingAfter(20f);
            document.add(subtitle);
            
             // ajouter les informations de l'école
            Paragraph ecole = new Paragraph("Je soussigné(e)_______________________, directeur(trice) de l'établissement XYZ, certifie que :", new Font(Font.FontFamily.TIMES_ROMAN, 12));
            ecole.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(ecole);
            
            // ajouter les informations de l'étudiant
            Paragraph etudiant = new Paragraph("Mme, M. "+ eleve.getPrenom() + " " + eleve.getNom() + "\n" + "né(e) le " + eleve.getDateDeNaissance() + " à " + eleve.getLieuDeNaissance() + "\n", new Font(Font.FontFamily.TIMES_ROMAN, 12));
            etudiant.setAlignment(Element.ALIGN_CENTER);
            etudiant.setSpacingBefore(20f);
            etudiant.setSpacingAfter(20f);
            document.add(etudiant);
            
            // informations de certificat
            Paragraph certificat = new Paragraph("est régulièrement inscrit(e) dans notre établissement depuis le " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(new EleveDAO().readDateInscription(eleve.getId())) + " et qu'il/elle est actuellement en classe de " + eleve.getClasse() + ".\n", new Font(Font.FontFamily.TIMES_ROMAN, 12));
            certificat.setAlignment(Element.ALIGN_BASELINE);
            certificat.setSpacingBefore(20f);
            certificat.setSpacingAfter(20f);
            certificat.setIndentationLeft(50f);
            certificat.setIndentationRight(50f);
            document.add(certificat);
            
            Paragraph certificat2 = new Paragraph("En foi de quoi, le présent lui est délivré sur sa demande pour servir et valoir ce que de droit.", new Font(Font.FontFamily.TIMES_ROMAN, 12));
            certificat2.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(certificat2);
            
            // Ajout de la signature et du cachet de l'établissement
            document.add(new Paragraph(" "));
            Paragraph date = new Paragraph("Fait à ___________, le "+ DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()), NORMAL_FONT);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);
            document.add(new Paragraph(" "));
            Paragraph signature = new Paragraph("Signature et cachet de l'établissement", NORMAL_FONT);
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);
            
         } catch (DocumentException e) {
             throw new PDFException(e.getMessage());
         } catch (FileNotFoundException e) {
             throw new PDFException(e.getMessage());
         } catch (Exception e) {
             throw new PDFException(e.getMessage());
         } finally {
             document.close();
         }
     }
    
}
