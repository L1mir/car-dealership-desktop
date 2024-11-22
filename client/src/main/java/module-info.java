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
    requires java.desktop;
    requires annotations;

    opens org.limir to javafx.fxml;
    opens org.limir.models.entities to  javafx.base, com.google.gson;
    opens org.limir.models.enums to com.google.gson;
    opens org.limir.models.tcp to com.google.gson;
    opens org.limir.models.dto to javafx.base, com.google.gson;
    opens org.limir.utility to javafx.fxml;

    exports org.limir;
    exports org.limir.utility;
    exports org.limir.controllers;
    opens org.limir.controllers to javafx.fxml;
    exports org.limir.controllers.car;
    opens org.limir.controllers.car to javafx.fxml;
    exports org.limir.controllers.company;
    opens org.limir.controllers.company to javafx.fxml;
    exports org.limir.controllers.auth;
    opens org.limir.controllers.auth to javafx.fxml;
}
