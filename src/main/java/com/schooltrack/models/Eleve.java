package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Eleve {
	
	private IntegerProperty id;
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty adresse;
	private ObjectProperty<LocalDate> dateDeNaissance;
	private StringProperty lieuDeNaissance;
	
	private StringProperty numtelephone;
	private StringProperty email;
	private StringProperty sexe;
	private ListProperty<Paiement> paiements;
	
	public Eleve() {
	}

	public Eleve( String matricule, String nom,
			String prenom, String adresse, LocalDate birthday) {
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse =new SimpleStringProperty(adresse);
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>(birthday);
	}
	
	public Eleve(int id, String nom, String prenom, String adresse, LocalDate dateDeNaissance, String lieuDeNaissance, String numtelephone, String email, String sexe) {
		this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse = new SimpleStringProperty(adresse);
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>(dateDeNaissance);
		this.lieuDeNaissance = new SimpleStringProperty(lieuDeNaissance);
		this.numtelephone = new SimpleStringProperty(numtelephone);
		this.email = new SimpleStringProperty(email);
		this.sexe = new SimpleStringProperty(sexe);
	}
	
	public Eleve(int id, String nom, String prenom, String adresse, LocalDate dateDeNaissance, String lieuDeNaissance, String numtelephone, String email, String sexe, ObservableList<Paiement> paiements) {
		this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse = new SimpleStringProperty(adresse);
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>(dateDeNaissance);
		this.lieuDeNaissance = new SimpleStringProperty(lieuDeNaissance);
		this.numtelephone = new SimpleStringProperty(numtelephone);
		this.email = new SimpleStringProperty(email);
		this.sexe = new SimpleStringProperty(sexe);
		this.paiements = new SimpleListProperty<Paiement>(paiements);
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

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance.get();
	}

	public ObjectProperty<LocalDate> dateDeNaissanceProperty() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		this.dateDeNaissance.set(dateDeNaissance);
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
	
	public String getNumtelephone() {
		return numtelephone.get();
	}
	
	public StringProperty numtelephoneProperty() {
		return numtelephone;
	}
	
	public void setNumtelephone(String numtelephone) {
		this.numtelephone.set(numtelephone);
	}
	
	
	public String getEmail() {
		return email.get();
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public String getSexe() {
		return sexe.get();
	}
	
	public StringProperty sexeProperty() {
		return sexe;
	}
	
	public void setSexe(String sexe) {
		this.sexe.set(sexe);
	}
	
	public ObservableList<Paiement> getPaiements() {
		return paiements.get();
	}
	
	public ListProperty<Paiement> paiementsProperty() {
		return paiements;
	}
	
	public void setPaiements(ObservableList<Paiement> paiements) {
		this.paiements.set(paiements);
	}
}
