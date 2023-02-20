package com.schooltrack.runtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Stage primaryStage;
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SchoolTrack");
        
        initRootLayout();
    }
    
    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
    }
}
