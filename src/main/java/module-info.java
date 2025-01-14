module com.schooltrack.schooltrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires itextpdf;
    
    opens com.schooltrack.view to javafx.fxml;
    exports com.schooltrack;
    opens com.schooltrack to javafx.fxml, javafx.graphics;
    opens com.schooltrack.controller.secretaire to javafx.fxml;
    opens com.schooltrack.controller.caissier to javafx.fxml;
    opens com.schooltrack.controller.administateur to javafx.fxml;
    opens com.schooltrack.controller to javafx.fxml;
}