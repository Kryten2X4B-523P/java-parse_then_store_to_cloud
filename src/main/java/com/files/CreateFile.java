package com.files;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CreateFile {
    public CreateFile(String text, String path) {
        BufferedWriter output = null;

        try {
            File myFile = new File(path);
            output = new BufferedWriter(new FileWriter(myFile));
            output.write(text);
            log.info("File created: " + myFile.getName());

        } catch (IOException e) {
            log.error("An error occurred.: " + e);
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("An error occurred when try close the file." + e);
                    e.printStackTrace();
                }
            }
        }
    }
}