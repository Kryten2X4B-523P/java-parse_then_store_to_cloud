package com.files;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ImportFile {
    public List<String> namesStations = new ArrayList<>();
    public List<String> dates = new ArrayList<>();
    public List<List<String>> times = new ArrayList<>();

    public void build(File openFile) {
        try {
            while (rowIterator.hasNext()) {
                i++;

                String nameOfStation = this.logicParse("A" + i);
                if (nameOfStation != null) {
                    if (!nameOfStation.equals("NE")) {
                        if (!nameOfStation.equals("ne")) {
                            String date = this.logicParse("C" + i);
                            if (date != null) {
                                List<String> times = this.getAllTimeStamps(new String[]{"D" + i, "E" + i, "F" + i, "G" + i, "H" + i, "I" + i, "J" + i, "K" + i});
                                this.namesStations.add(nameOfStation);
                                this.dates.add(date);
                                this.times.add(times);
                            }
                        }
                    }

                } else break;
            }

            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getAllTimeStamps(String[] references) {
        List<String> addElements = new ArrayList<>();
        for (String referenceAtExcel : references) {
            String result = this.logicParse(referenceAtExcel);
            if (result != null) {
                addElements.add(result);
            }
        }
        addElements.removeAll(List.of(""));
        return addElements;
    }

    private Cell getCellFromReference(String referenceAtExcel) {
        CellReference cr = new CellReference(referenceAtExcel);
        var row = this.sheet.getRow(cr.getRow());
        if (row != null) {
            var cell = row.getCell(cr.getCol());
            return cell;
        } else return null;
    }
}
