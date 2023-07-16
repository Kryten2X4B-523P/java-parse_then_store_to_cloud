package com.gui;

import com.calendar.GoogleApi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;

public class Gui extends Application {

    final static Logger log = Logger.getLogger(Gui.class);

    public static void main(String[] args) {

        File file = new File(GoogleApi.TOKENS_DIRECTORY_PATH);

        try {
            new GoogleApi().build();
        } catch (GeneralSecurityException | IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }

        launch();
    }

    static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("/guiData/gui.fxml");
        Parent root = FXMLLoader.load(url);
        stage.setTitle("nahraj odjezdy vláčků");
        Scene scene = new Scene(root, 350, 220);
        stage.setScene(scene);

        Controller r = new Controller();
        r.setStage(stage);

        stage.show();
    }

}