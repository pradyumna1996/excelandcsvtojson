package com.kwant.excelandcsvtojson.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public interface ExcelCSVToJsonService {

    // 1.Excel To Json
    List<Object> excelToJson(MultipartFile uploadedFile);


    // 2. Excel To CSV
    List<Object> excelToCSV(MultipartFile uploadedFile);


    // 3. CSV TO JSON
    List<Object> csvToJson(MultipartFile file);


    List<Object> excelToJsonConsumeInputStream(InputStream inputStream);


}
