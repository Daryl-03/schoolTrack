package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Certificat {
	protected IntegerProperty id;
	protected ObjectProperty<LocalDate> date;

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);;
	}
	public IntegerProperty IdProperty() {
		return id;
	}

	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate date) {
		this.date.set(date);;
	}
	
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
		
	}

	public Certificat(Integer id, LocalDate date) {
		this.id = new SimpleIntegerProperty(id);
		this.date =new SimpleObjectProperty<LocalDate>(date);
	}
}
