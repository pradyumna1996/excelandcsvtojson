package com.kwant.excelandcsvtojson.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a Core class that has the main logic to convert the excel to Json and CSV.
 * Also, CSV to JSON is also implemented.
 * Both Functions consuming file with file input and input stream is done.
 */

@Service
public class ExcelCSVConverter {



    // 1. Excel To Json

    /**
     * Converts Excel To JSON
     * @param file Input Excel File Using MultipartFile
     * @return json list
     */

    public List<ObjectNode> excelToJson(MultipartFile file) {




        try (InputStream inputStream = file.getInputStream();


             Workbook workbook = new XSSFWorkbook(inputStream)) {


            Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet

            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return null;
            }

            List<ObjectNode> jsonObjectList = new ArrayList<>();
            Row headerRow = rowIterator.next(); // Assuming the first row is the header row


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                ObjectNode jsonObject = new ObjectMapper().createObjectNode();

                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        String header = headerRow.getCell(i).getStringCellValue();
                        String cellValue = getCellValueAsString(cell);
                        if (cellValue != null && !cellValue.isEmpty()) {
                            jsonObject.put(header, cellValue);
                        }
                    }
                }

                if (!jsonObject.isEmpty()) {
                    jsonObjectList.add(jsonObject);
                }
            }

            if (!jsonObjectList.isEmpty()) {
                return jsonObjectList;
            }

            return null;

        } catch (IOException e) {
            e.getMessage();
            return null;
        }

    }


    // 2. excel to csv completed

    /**
     * Converts EXCEL to CSV
     * @param file Upload Excel File using Multipart File
     * @return CSV fike
     */
    public List excelToCSV(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return null;
            }

            List<String> csvDataList = new ArrayList<>();


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                StringBuilder csvRow = new StringBuilder();
                Iterator<Cell> cellIterator = row.cellIterator();

                boolean hasData = false;

                boolean isFirstCell = true;


                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    String cellValue = getCellValueAsString(cell);
                    if (cellValue != null && !cellValue.isEmpty()) {
                        if (!isFirstCell) {
                            csvRow.append(",");
                        }
                        csvRow.append(cellValue);
                        hasData = true;
                    }
                    isFirstCell = false;
                }

                if (hasData) {
                    csvDataList.add(csvRow.toString());
                }
            }

            return csvDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    // 3. csv to json completed

    /**
     * Converts CSV to JSON
     * @param file Upload File and Consumed Using Multipart File
     * @return list csv
     */

    public List csvToJson(MultipartFile file) {


        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            CSVParser csvParser = null;
            try {
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            List<ObjectNode> jsonDataList = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                ObjectNode jsonData = new ObjectMapper().createObjectNode();
                csvRecord.toMap().forEach((key, value) -> {
                    if (!value.isEmpty()) {
                        jsonData.put(key, value);
                    }
                });
                if (jsonData.size() > 0) {
                    jsonDataList.add(jsonData);
                }
            }
            return jsonDataList;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Converts Excel To JSON
     * Uses Input Stream as Parameter
     * @param inputStream Input Stream
     * @return json response
     */

    // 1 . Extended Using Input Stream To Consume File
    public List<ObjectNode> excelToJsonAsInputStream(InputStream inputStream) {




        try (Workbook workbook = WorkbookFactory.create(inputStream)) {


            Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet

            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return null;
            }

            List<ObjectNode> jsonObjectList = new ArrayList<>();
            Row headerRow = rowIterator.next(); // Assuming the first row is the header row


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                ObjectNode jsonObject = new ObjectMapper().createObjectNode();

                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        String header = headerRow.getCell(i).getStringCellValue();
                        String cellValue = getCellValueAsString(cell);
                        if (cellValue != null && !cellValue.isEmpty()) {
                            jsonObject.put(header, cellValue);
                        }
                    }
                }

                if (!jsonObject.isEmpty()) {
                    jsonObjectList.add(jsonObject);
                }
            }

            if (!jsonObjectList.isEmpty()) {
                return jsonObjectList;
            }

            return null;

        } catch (IOException e) {
            e.getMessage();
            return null;
        }

    }

    /**
     * A method that determines type of cell value
     * @param cell A Cell is passed
     * @return Value in String
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                DataFormatter dataFormatter = new DataFormatter();
                yield dataFormatter.formatCellValue(cell);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };

    }


}