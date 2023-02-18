package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Eleve {
	
	private IntegerProperty id ;
	private StringProperty matricule ;
	private StringProperty nom ;
	private StringProperty prenom ;
	private StringProperty adresse ;
	private ObjectProperty<LocalDate> birthday;
	private StringProperty lieuDeNaissance;
	
	
	
	
	
	public Eleve() {
		super();
	}

	public Eleve( String matricule, String nom,
			String prenom, String adresse, LocalDate birthday) {


		this.matricule =new SimpleStringProperty(matricule);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse =new SimpleStringProperty(adresse);
		this.birthday = new SimpleObjectProperty<LocalDate>(birthday);
	}

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty() {
		return id;
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getMatricule() {
		return matricule.get();
	}
	public StringProperty matriculeProperty() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule.set(matricule);
	}
	public String getNom() {
		return nom.get();
	}

	public StringProperty nomProperty() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}

	public String getPrenom() {
		return prenom.get();
	}

	public StringProperty prenomProperty() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom.set(prenom);
	}

	public String getAdresse() {
		return adresse.get();
	}

	public StringProperty adresseProperty() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse.set(adresse);
	}

	public LocalDate getBirthday() {
		return birthday.get();
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}

	public String getLieuDeNaissance() {
		return lieuDeNaissance.get();
	}

	public StringProperty lieuDeNaissanceProperty() {
		return lieuDeNaissance;
	}

	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance.set(lieuDeNaissance);
	}
}
