package com.kwant.excelandcsvtojson.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


/**
 * This is a service interface that provides the methods for implementation.
 */
@Service
public interface ExcelCSVToJsonService {

    // 1.Excel To Json

    /**
     * Excel To JSON
     * @param uploadedFile parameter as Multipart File
     * @return list json
     */
    List<Object> excelToJson(MultipartFile uploadedFile);


    // 2. Excel To CSV


    /**
     * Converts Excel To CSV
     * @param uploadedFile Upload Excel File
     * @return csv
     */
    List<Object> excelToCSV(MultipartFile uploadedFile);


    // 3. CSV TO JSON

    /**
     * Converts CSV to JSON
     * @param file Upload CSV File
     * @return json
     */
    List<Object> csvToJson(MultipartFile file);

    //4. Consume file as InputStream

    /**
     * Converts Excel to JSON using Input Stream
     * @param inputStream Input Stream file
     * @return json list
     */
    List<Object> excelToJsonConsumeInputStream(InputStream inputStream);


}
