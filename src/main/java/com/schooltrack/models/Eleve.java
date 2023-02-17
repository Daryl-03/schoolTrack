package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Eleve {
	
	protected IntegerProperty id ;
	protected StringProperty matricule ;
	protected StringProperty nom ;
	protected StringProperty prenom ;
	protected StringProperty adresse ;
	protected ObjectProperty<LocalDate> birthday;
	protected StringProperty lieuDeNaissance;
	
	
	
	
	
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

	public Integer getId() {
		return id.get();
	}
	
	public void setId(Integer id) {
		this.id.set(id);
	}
	public IntegerProperty idProperty() {
		return id;
	}
	
	public String getMatricule() {
		return matricule.get();
	}
	public void setMatricule(String matricule) {
		this.matricule.set(matricule);
	}
	public StringProperty MatriculeProperty() {
		return matricule;
	}
	public String getNom() {
		return nom.get();
	}
	public void setNom(String nom) {
		this.nom.set(nom);
	}
	
	public StringProperty NomProperty() {
		return nom;
	}
	public String getPrenom() {
		return prenom.get();
	}
	public void setPrenom(String prenom) {
		this.prenom.set(prenom);
	}
	public StringProperty PrenomProperty() {
		return prenom;
	}
	public String getAdresse() {
		return adresse.get();
	}
	public void setAdresse(String adresse) {
		this.adresse.set(adresse);
	}
	public StringProperty AdresseProperty() {
		return adresse;
	}
	public String getLieuDeNaissance() {
		return lieuDeNaissance.get();
	}
	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance.set(lieuDeNaissance);
	}
	public StringProperty LieuDeNaissanceProperty() {
		return lieuDeNaissance;
	}
	

	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}

	public ObjectProperty<LocalDate> BirthdayProperty() {
		return birthday;
	}

}
