module org.limir.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;

    opens org.limir to javafx.fxml;
    opens org.limir.models.entities to com.google.gson;
    opens org.limir.models.enums to com.google.gson;
    opens org.limir.models.tcp to com.google.gson;
    opens org.limir.utility to javafx.fxml;

    exports org.limir;
    exports org.limir.utility;
    exports org.limir.controllers;
    opens org.limir.controllers to javafx.fxml;
}
