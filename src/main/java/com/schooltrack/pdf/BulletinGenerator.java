package com.schooltrack.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.schooltrack.exceptions.PDFException;
import com.schooltrack.models.*;
import com.schooltrack.models.datasource.AnneeScolaireDAO;
import com.schooltrack.models.datasource.EleveDAO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class BulletinGenerator {
    
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font subTitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

    
    /**
     * Génère un Bulletin pdf pour un élève en utilisant le chemin vers le fichier
     * @param path  le chemin vers le fichier
     * @param bulletin le bulletin
     * @throws PDFException
     */
    public void generate(String path, Bulletin bulletin) throws PDFException {
        Document document = new Document();
        try {
            Eleve eleve = new EleveDAO().read(bulletin.getId_eleve());
            AnneeScolaire anneeScolaire = new AnneeScolaireDAO().read(bulletin.getId_annee());
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            
            // Ajouter le nom de l'établissement
            Paragraph schoolName = new Paragraph("Etablissement XYZ", titleFont);
            schoolName.setAlignment(Element.ALIGN_CENTER);
            document.add(schoolName);
            document.add(Chunk.NEWLINE);
            
            // Informations sur le bulletin
            Paragraph bulletinInfo = new Paragraph(
                    "Année scolaire: " + anneeScolaire.getIntitule() + "\n" +
                            "Trimestre: " + bulletin.getTrimestre() + "\n" +
                            "Elève: " + eleve.getNom() + " " + eleve.getPrenom() + "\n\n",
                    subTitleFont);
            document.add(bulletinInfo);
            
            // Tableau des notes par matière
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            PdfPCell cell = new PdfPCell(new Phrase("Matière", boldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase("Note", boldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase("Coef.", boldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            List<Note> notes = bulletin.getNotes();
            for (Note note : notes) {
                table.addCell(note.getMatiere().getNom());
                table.addCell(String.valueOf(note.getValeur()));
                table.addCell(String.valueOf(note.getMatiere().getCoefficient()));
            }
            
            // Ajouter la moyenne générale
            PdfPCell moyenneCell = new PdfPCell(new Phrase("Moyenne générale : " + bulletin.getMoyenne(), boldFont));
            moyenneCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            moyenneCell.setColspan(3);
            table.addCell(moyenneCell);
            
            document.add(table);
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
