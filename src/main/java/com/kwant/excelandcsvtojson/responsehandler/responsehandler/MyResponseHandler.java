package com.kwant.excelandcsvtojson.responsehandler.responsehandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


/**
 * Response Handler Class
 * Custom Handling
 * Returning Custom Response
 */
public class MyResponseHandler {


    /**
     * A method That Provides Custom Response
     * @param success Server Response as True/False
     * @param status Server Status as HTTP Status
     * @param name   Name of the uploaded Exel File
     * @param type  Type of the file uploaded
     * @param size  Size of the file uploaded
     * @param responseObj   A list that is being passed and returned
     * @return Custom Response adding to the list
     */
    public static ResponseEntity<Object> generateResponse(Boolean success, HttpStatus status, String name,String type,double size, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", success);
        map.put("name", name);
        map.put("type", type);
        map.put("size", size);

        map.put("records", responseObj);

        return new ResponseEntity<Object>(map, status);
    }

}
