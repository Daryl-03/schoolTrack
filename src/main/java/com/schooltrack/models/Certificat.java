package com.schooltrack.models;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;


public class Certificat {
	private final IntegerProperty id;
	private final ObjectProperty<LocalDate> date;


	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public LocalDate getDate() {
		return date.get();
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public Certificat(Integer id, LocalDate date) {
		this.id = new SimpleIntegerProperty(id);
		this.date =new SimpleObjectProperty<LocalDate>(date);
	}
}
