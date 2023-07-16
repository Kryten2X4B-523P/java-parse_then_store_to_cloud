package com.gui;

import com.calendar.UploadCalendar;
import com.files.ImportFile;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;

public class Controller {
    final static Logger log = Logger.getLogger(Controller.class);
    private static final String pathToBaseInitialConfiguartion = "./path.txt";
    final FileChooser fileChooser = new FileChooser();
    private final Desktop desktop = Desktop.getDesktop();
    private File propertyFile;
    private ImportFile importFile;
    private Stage stage;
    @FXML
    private Button chooseFile, uploadToWeb;
    @FXML
    private Label ourLabel;
    @FXML
    private ProgressBar updateProgress;

    private static void configureFileChooser(final FileChooser fileChooser) {
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void onButtonEvent(ActionEvent e) throws GeneralSecurityException, IOException, ParseException, InterruptedException {
    }

    private Runnable task2(ImportFile importFile) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        UploadCalendar uploadCalendar = null;
                        try {
                            uploadCalendar = new UploadCalendar();
                            uploadCalendar.build();
                            for (int i = 0; i < importFile.times.size(); i++) {
                                List<String> time = importFile.times.get(i);

                                for (String getTime : time) {
                                    uploadCalendar.createEvent(getTime, importFile.year, importFile.dates.get(i), 30, importFile.namesStations.get(i));
                                }
                            }
                            ourLabel.setText("Nahrání na web dokončeno.");
                        } catch (Exception exception) {
                            try {
                            } catch (Exception e) {
                                log.error("Chyba v mazání eventu" + e);
                            } finally {
                                log.error(exception);
                                ourLabel.setText("špatná data, zkontrolujte prosím excel");
                            }
                        } finally {
                            updateProgress.setVisible(false);
                            ourLabel.setVisible(true);
                        }
                    }
                });
            }
        };
        return task;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void openFile(File file) {
        try {
            this.importFile = new ImportFile();
            this.importFile.build(file);

            ourLabel.setVisible(false);
            uploadToWeb.setDisable(false);
        } catch (Exception ex) {
            ourLabel.setText("Chyba v excelu. Nahrajte správný excel dle předlohy.");
            ourLabel.setVisible(true);
            log.error(ex);
        }
    }


}
