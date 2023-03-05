package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Bulletin {
	private IntegerProperty id;
	private IntegerProperty trimestre;
	private FloatProperty moyenne;
	private ListProperty<Note> notes;
	private IntegerProperty id_eleve;
	private IntegerProperty id_classe;
	private IntegerProperty id_annee;
	
	public int getId() {
		return id.get();
	}
	
	public IntegerProperty idProperty() {
		return id;
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public int getTrimestre() {
		return trimestre.get();
	}
	
	public IntegerProperty trimestreProperty() {
		return trimestre;
	}
	
	public void setTrimestre(int trimestre) {
		this.trimestre.set(trimestre);
	}
	
	public float getMoyenne() {
		// calculer la moyenne en tenant compte des coefficients
		float somme = 0;
		float sommeCoef = 0;
		for (Note note : notes) {
			somme += note.getValeur() * note.getMatiere().getCoefficient();
			sommeCoef += note.getMatiere().getCoefficient();
		}
		return somme / sommeCoef;
	}
	
	public FloatProperty moyenneProperty() {
		return moyenne;
	}
	
	public void setMoyenne(float moyenne) {
		this.moyenne.set(moyenne);
	}
	
	public ObservableList<Note> getNotes() {
		return notes.get();
	}
	
	public ListProperty<Note> notesProperty() {
		return notes;
	}
	
	public void setNotes(ObservableList<Note> notes) {
		this.notes.set(notes);
	}
	
	public int getId_eleve() {
		return id_eleve.get();
	}
	
	public IntegerProperty id_eleveProperty() {
		return id_eleve;
	}
	
	public void setId_eleve(int id_eleve) {
		this.id_eleve.set(id_eleve);
	}
	
	public int getId_classe() {
		return id_classe.get();
	}
	
	public IntegerProperty id_classeProperty() {
		return id_classe;
	}
	
	public void setId_classe(int id_classe) {
		this.id_classe.set(id_classe);
	}
	
	public int getId_annee() {
		return id_annee.get();
	}
	
	public IntegerProperty id_anneeProperty() {
		return id_annee;
	}
	
	public void setId_annee(int id_annee) {
		this.id_annee.set(id_annee);
	}
	
	public Bulletin() {
		this.id = new SimpleIntegerProperty();
		this.trimestre = new SimpleIntegerProperty();
		this.moyenne = new SimpleFloatProperty();
		this.notes = new SimpleListProperty<Note>();
		this.id_eleve = new SimpleIntegerProperty();
		this.id_classe = new SimpleIntegerProperty();
		this.id_annee = new SimpleIntegerProperty();
	}
	
	public Bulletin(int id, int trimestre, ObservableList<Note> notes, int id_eleve, int id_classe, int id_annee) {
		
		this.id =new SimpleIntegerProperty(id);
		this.trimestre = new SimpleIntegerProperty(trimestre);
		this.moyenne = new SimpleFloatProperty();
		this.notes = new SimpleListProperty<Note>(notes);
		this.id_eleve = new SimpleIntegerProperty(id_eleve);
		this.id_classe = new SimpleIntegerProperty(id_classe);
		this.id_annee = new SimpleIntegerProperty(id_annee);
		
	}
	
	public Bulletin(int id, int trimestre, float moyenne, ObservableList<Note> notes, int id_eleve, int id_classe, int id_annee) {
		
		this.id =new SimpleIntegerProperty(id);
		this.trimestre = new SimpleIntegerProperty(trimestre);
		this.moyenne = new SimpleFloatProperty(moyenne);
		this.notes = new SimpleListProperty<Note>(notes);
		this.id_eleve = new SimpleIntegerProperty(id_eleve);
		this.id_classe = new SimpleIntegerProperty(id_classe);
		this.id_annee = new SimpleIntegerProperty(id_annee);
		
	}
	
	@Override
	public String toString() {
		return "Bulletin{" +
				"id=" + id +
				", trimestre=" + trimestre +
				", moyenne=" + moyenne +
				", notes=" + notes +
				", id_eleve=" + id_eleve +
				", id_classe=" + id_classe +
				", id_annee=" + id_annee +
				'}';
	}
}
