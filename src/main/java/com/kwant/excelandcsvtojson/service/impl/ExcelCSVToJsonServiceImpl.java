package com.kwant.excelandcsvtojson.service.impl;
import com.kwant.excelandcsvtojson.service.ExcelCSVToJsonService;
import com.kwant.excelandcsvtojson.util.ExcelCSVConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Here all the methods of service interface (ExcelCSVToJsonService) is implemented
 */

@Service
public class ExcelCSVToJsonServiceImpl implements ExcelCSVToJsonService {



    // 1. Excel To JSON

    /**
     * Implementation of Excel To JSON
     * @param uploadedFile parameter as Multipart File
     * @return list
     */
    @Override
    public List<Object> excelToJson(MultipartFile uploadedFile) {

        ExcelCSVConverter converter = new ExcelCSVConverter();

        List data = converter.excelToJson(uploadedFile);

        System.out.println("Excel file contains the Data:\n" + data);

        if(!(data==null)){
            return data;
        }

        return null;
    }


    // 2. Excel to CSV

    /**
     * Implementation of Excel To CSV
     * @param uploadedFile Upload Excel File
     * @return csv
     */

    @Override
    public List excelToCSV(MultipartFile uploadedFile) {


        ExcelCSVConverter converter = new ExcelCSVConverter();

        List data = converter.excelToCSV(uploadedFile);

        System.out.println("Excel file contains the Data:\n" + data);

        if(!(data==null)){
            return data;
        }

        return null;

    }


    // 3. CS TO Json

    /**
     * Implementation of CSV To JSON
     * @param file Upload CSV File
     * @return list json
     */
    @Override
    public List csvToJson(MultipartFile file) {

        ExcelCSVConverter converter = new ExcelCSVConverter();

        List data = converter.csvToJson(file);

        System.out.println("Excel file contains the Data:\n" + data);

        return data;

    }


    /**
     * Converts EXCEL to JSON using InputStream
     * @param inputStream Input Stream file
     * @return json list
     */

    @Override
    public List<Object> excelToJsonConsumeInputStream(InputStream inputStream) {

        ExcelCSVConverter converter = new ExcelCSVConverter();

        List data = converter.excelToJsonAsInputStream(inputStream);

        System.out.println("Excel file contains the Data:\n" + data);

        if(!(data==null)){
            return data;
        }

        return null;
    }




}
