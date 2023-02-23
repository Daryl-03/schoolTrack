package com.schooltrack.models;

public class Administrateur extends Utilisateur {

	public Administrateur() {
		super();
	}
	public Administrateur(String login, String code) {
		super(login, code);
	}
	
	public Administrateur(int id, String nom, String prenom, String login, String password, String email, String telephone) {
		super(id, nom, prenom, login, password, email, telephone);
	}
	
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

}
