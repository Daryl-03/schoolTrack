package com.schooltrack.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class EcheanceTableModel {
    
    private ObjectProperty<LocalDate> date;
    private StringProperty label;
    
    public EcheanceTableModel(LocalDate date, String label) {
        this.date = new SimpleObjectProperty<>(date);
        this.label = new SimpleStringProperty(label);
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
    
    public String getLabel() {
        return label.get();
    }
    
    public StringProperty labelProperty() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label.set(label);
    }
}
