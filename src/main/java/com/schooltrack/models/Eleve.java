package com.schooltrack.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Eleve {
	private IntegerProperty id;
	private StringProperty matricule;
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty adresse;
	private ObjectProperty<LocalDate> dateDeNaissance;
	private StringProperty lieuDeNaissance;
	
	private StringProperty numtelephone;
	private StringProperty email;
	private StringProperty sexe;
	private ListProperty<Paiement> paiements;
	private ListProperty<Bulletin> bulletins;
	private IntegerProperty id_classe;
	private StringProperty classe;
	
	public Eleve() {
		this.id = new SimpleIntegerProperty();
		this.matricule = new SimpleStringProperty();
		this.nom = new SimpleStringProperty();
		this.prenom = new SimpleStringProperty();
		this.adresse = new SimpleStringProperty();
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>();
		this.lieuDeNaissance = new SimpleStringProperty();
		this.numtelephone = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.sexe = new SimpleStringProperty();
		this.paiements = new SimpleListProperty<Paiement>();
		this.bulletins = new SimpleListProperty<Bulletin>();
		this.id_classe = new SimpleIntegerProperty();
		this.classe = new SimpleStringProperty();
	}

	public Eleve( String matricule, String nom,
			String prenom, String adresse, LocalDate birthday) {
		this.id = new SimpleIntegerProperty();
		this.matricule = new SimpleStringProperty(matricule);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse = new SimpleStringProperty(adresse);
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>(birthday);
		this.lieuDeNaissance = new SimpleStringProperty();
		this.numtelephone = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.sexe = new SimpleStringProperty();
		this.paiements = new SimpleListProperty<Paiement>();
		this.bulletins = new SimpleListProperty<Bulletin>();
		this.id_classe = new SimpleIntegerProperty();
		this.classe = new SimpleStringProperty();
	}
	
	public Eleve(int id, String nom, String prenom, String adresse, LocalDate dateDeNaissance, String lieuDeNaissance, String numtelephone, String email, String sexe) {
		this.id = new SimpleIntegerProperty(id);
		this.matricule = new SimpleStringProperty();
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse = new SimpleStringProperty(adresse);
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>(dateDeNaissance);
		this.lieuDeNaissance = new SimpleStringProperty(lieuDeNaissance);
		this.numtelephone = new SimpleStringProperty(numtelephone);
		this.email = new SimpleStringProperty(email);
		this.sexe = new SimpleStringProperty(sexe);
		this.classe = new SimpleStringProperty();
	}
	
	public Eleve(int id, String matricule, String nom, String prenom, String adresse, LocalDate dateDeNaissance, String lieuDeNaissance, String numtelephone, String email, String sexe, ObservableList<Paiement> paiements, ObservableList<Bulletin> bulletins, int id_classe) {
		this.id = new SimpleIntegerProperty(id);
		this.matricule = new SimpleStringProperty(matricule);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.adresse = new SimpleStringProperty(adresse);
		this.dateDeNaissance = new SimpleObjectProperty<LocalDate>(dateDeNaissance);
		this.lieuDeNaissance = new SimpleStringProperty(lieuDeNaissance);
		this.numtelephone = new SimpleStringProperty(numtelephone);
		this.email = new SimpleStringProperty(email);
		this.sexe = new SimpleStringProperty(sexe);
		this.paiements = new SimpleListProperty<Paiement>(paiements);
		this.bulletins = new SimpleListProperty<Bulletin>(bulletins);
		this.id_classe = new SimpleIntegerProperty(id_classe);
		this.classe = new SimpleStringProperty();
	}
	
	public String getClasse() {
		return classe.get();
	}
	
	public StringProperty classeProperty() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe.set(classe);
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
	
	public ObservableList<Bulletin> getBulletins() {
		return bulletins.get();
	}
	
	public ListProperty<Bulletin> bulletinsProperty() {
		return bulletins;
	}
	
	public void setBulletins(ObservableList<Bulletin> bulletins) {
		this.bulletins.set(bulletins);
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
	
	@Override
	public String toString() {
		return "Eleve [id=" + id.get() + "\nnom=" + nom.get() + "\nprenom=" + prenom.get() + "\nadresse=" + adresse.get() + "\ndateDeNaissance=" + dateDeNaissance.get() + "\nlieuDeNaissance=" + lieuDeNaissance.get() + "\nnumtelephone=" + numtelephone.get() + "\nemail=" + email.get() + "\nsexe=" + sexe.get() + "\npaiements=" + paiements.get() + "\nbulletins=" + bulletins.get() + "\nid_classe=" + id_classe.get() + "]";
	}
}
