package com.schooltrack.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public  abstract class Utilisateurs {
	protected StringProperty login;
	protected StringProperty code;
	
	public String getLogin() {
		return login.get();
	}
	public void setLogin(String login) {
		this.login.set(login); 
	}
	public StringProperty loginProperty() {
		return login;
	}
	
	
	public String getCode() {
		return code.get();
	}
	public void setCode(String code) {
		this.code.set(code);
	}
	public StringProperty codeproProperty() {
		return code;
	}
	public Utilisateurs(String login, String code) {
		
		this.login = new SimpleStringProperty(login);
		this.code = new SimpleStringProperty(code);
	}
	
	

	}
