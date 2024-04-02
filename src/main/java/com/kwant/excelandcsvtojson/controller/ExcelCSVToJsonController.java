package com.kwant.excelandcsvtojson.controller;

import com.kwant.excelandcsvtojson.responsehandler.responsehandler.MyResponseHandler;
import com.kwant.excelandcsvtojson.service.ExcelCSVToJsonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This is the Rest Controller for converting EXCEL to JSON, CSV
 * And, CSV to Json is also implemented
 * Contains both methods that Consumes File using Multipart nad InputStream
 */

@RestController
public class ExcelCSVToJsonController {


    /**
     * Object Declaration
     */
    private final ExcelCSVToJsonService excelCSVToJsonService;

    /**
     *Constructor Injection
     * @param excelCSVToJsonService Service Class injected
     */

    public ExcelCSVToJsonController(ExcelCSVToJsonService excelCSVToJsonService) {
        this.excelCSVToJsonService = excelCSVToJsonService;
    }


    //1. Converting excel to json
    /**
     * Post Request
     * Converting Excel To JSON
     * @param uploadedFile Multipart File
     * @return response using custom response handler
     */
    @PostMapping("/spreadsheets/parse")
    public ResponseEntity<Object> convertToJsonUploadFileCustomResponseAsListConsumingException(@RequestParam("file") MultipartFile uploadedFile){



        List<Object> data = excelCSVToJsonService.excelToJson(uploadedFile);

        System.out.println("Excel file contains the Data:\n" + data);

      /*  if(data ==null){

            throw new RuntimeException();
        }*/



        if(!(data ==null)){

            return  MyResponseHandler.generateResponse(Boolean.TRUE, HttpStatus.OK, uploadedFile.getOriginalFilename() , uploadedFile.getContentType(),  uploadedFile.getSize(), data);
        }

        return  MyResponseHandler.generateResponse(Boolean.TRUE, HttpStatus.OK, uploadedFile.getOriginalFilename() , uploadedFile.getContentType(),  uploadedFile.getSize(), data);


    }


    // 2. Converting excel to CSV

    /**
     * Post Request
     * Converting Excel To CSV
     * @param uploadedFile Multipart File
     * @return CSV
     */

    @PostMapping("/spreadsheets/parse/csv")
    public ResponseEntity<Object> convertExcelToCsv(@RequestParam("file") MultipartFile uploadedFile) throws Exception {


        List<Object> data = excelCSVToJsonService.excelToCSV(uploadedFile);



        if(!(data ==null)){

            return  MyResponseHandler.generateResponse(Boolean.TRUE, HttpStatus.OK, uploadedFile.getOriginalFilename() , uploadedFile.getContentType(),  uploadedFile.getSize(), data);
        }

        return MyResponseHandler.generateResponse(Boolean.FALSE, HttpStatus.NO_CONTENT, uploadedFile.getOriginalFilename() , uploadedFile.getContentType(),  uploadedFile.getSize(), data);


    }



    // 3. Converting CSV TO JSON

    /**
     * Post Request
     * Converting CSV To JSON
     * @param uploadedFile Multipart File
     * @return response using custom response handler
     */
    @PostMapping("/convert/csv/json")
    public ResponseEntity<Object>     convertToJsonUploadCSVFileCustomResponseAsListConsumingFile(@RequestPart("file") MultipartFile uploadedFile) throws Exception {

        List<Object> data = excelCSVToJsonService.csvToJson(uploadedFile);

        System.out.println("CSV file contains the Data:\n" + data);

        if (!(data == null)) {
            return MyResponseHandler.generateResponse(Boolean.TRUE, HttpStatus.OK, uploadedFile.getOriginalFilename(), uploadedFile.getContentType(), uploadedFile.getSize(), data);
        }
        return MyResponseHandler.generateResponse(Boolean.FALSE, HttpStatus.NO_CONTENT, uploadedFile.getOriginalFilename(), uploadedFile.getContentType(), uploadedFile.getSize(), data);
    }


    /**
     * Post Request
     * Converting Excel To JSON using Input Stream
     * @param uploadedFile Multipart File
     * @return json response using custom response handler
     */

    @PostMapping("/spreadsheets/parse/stream")
    public ResponseEntity<Object> convertToJsonUploadFileCustomResponseAsListConsumingInputStreamThrowingException(@RequestParam("file") MultipartFile uploadedFile) throws IOException {


        InputStream inputStream = uploadedFile.getInputStream();

        List<Object> data = excelCSVToJsonService.excelToJsonConsumeInputStream(inputStream);

        System.out.println("Excel file contains the Data:\n" + data);

      /*  if(data ==null){

            throw new RuntimeException();
        }*/



        if(!(data ==null)){
            inputStream.close();
            return  MyResponseHandler.generateResponse(Boolean.TRUE, HttpStatus.OK, uploadedFile.getOriginalFilename() , uploadedFile.getContentType(),  uploadedFile.getSize(), data);
        }
        inputStream.close();
        return  MyResponseHandler.generateResponse(Boolean.TRUE, HttpStatus.OK, uploadedFile.getOriginalFilename() , uploadedFile.getContentType(),  uploadedFile.getSize(), data);


    }




}



