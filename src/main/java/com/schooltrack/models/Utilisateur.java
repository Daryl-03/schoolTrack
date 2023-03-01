package com.schooltrack.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public  abstract class Utilisateur {
	protected IntegerProperty id;
	protected StringProperty nom;
	protected StringProperty prenom;
	protected StringProperty login;
	protected StringProperty password;
	protected StringProperty email;
	protected StringProperty telephone;
	
	public String getLogin() {
		return login.get();
	}
	public void setLogin(String login) {
		this.login.set(login); 
	}
	public StringProperty loginProperty() {
		return login;
	}
	
	
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public StringProperty codeproProperty() {
		return password;
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
	
	public StringProperty passwordProperty() {
		return password;
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
	
	public String getTelephone() {
		return telephone.get();
	}
	
	public StringProperty telephoneProperty() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone.set(telephone);
	}
	
	public Utilisateur() {
		this.id = new SimpleIntegerProperty();
		this.nom = new SimpleStringProperty();
		this.prenom = new SimpleStringProperty();
		this.login = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.telephone = new SimpleStringProperty();
	}
	
	public Utilisateur(String login, String password) {
		this.login = new SimpleStringProperty(login);
		this.password = new SimpleStringProperty(password);
	}
	
	public abstract String getType();
	
	// constructeur avec tous les parametres
	public Utilisateur(int id, String nom, String prenom, String login, String password, String email, String telephone) {
		this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.login = new SimpleStringProperty(login);
		this.password = new SimpleStringProperty(password);
		this.email = new SimpleStringProperty(email);
		this.telephone = new SimpleStringProperty(telephone);
	}
	
	@Override
	public String toString() {
		return "Utilisateur{" +
				"id=" + id +
				", nom=" + nom +
				", prenom=" + prenom +
				", login=" + login +
				", password=" + password +
				", email=" + email +
				", telephone=" + telephone +
				'}';
	}
}
