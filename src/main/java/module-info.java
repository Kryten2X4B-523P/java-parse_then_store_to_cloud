module moduleGui {
    exports com.gui;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    requires com.google.api.services.calendar;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.auth;
    requires jdk.httpserver;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires log4j;


    opens com.gui to javafx.graphics, javafx.fxml;
    opens com.files to javafx.graphics;
    opens com.calendar to javafx.graphics;
}

