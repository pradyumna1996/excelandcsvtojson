package com.kwant.excelandcsvtojson.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Iterator;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ExcelCSVConverterTest {

    @Mock
    private Workbook workbook;

    @Mock
    private Sheet sheet;

    @InjectMocks
    private ExcelCSVConverter excelCsvConverter;


    @Test
    void excelToJson_shouldReturnJsonList() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode expected1 = objectMapper.createObjectNode();
        expected1.put("S.No.", "1");
        expected1.put("Customer Name", "John William");
        expected1.put("Account Number", "9999999");
        expected1.put("e-mail", "william.john@gmail.com");
        expected1.put("Balance", "700000.00");

        ObjectNode expected2 = objectMapper.createObjectNode();
        expected2.put("S.No.", "2");
        expected2.put("Customer Name", "Mathew Parker");
        expected2.put("Account Number", "22222222");
        expected2.put("e-mail", "parker.mathew@gmail.com");
        expected2.put("Balance", "200000.00");

        List<ObjectNode> expectedList = List.of(expected1, expected2);



        MultipartFile file = createExcelFile();

        // Test the method
        assert file != null;
        List<ObjectNode> result = excelCsvConverter.excelToJsonAsInputStream(file.getInputStream());

        System.out.println(result);

        // Verify the result
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        assertEquals(expectedList.size(), result.size());
        assertEquals(expectedList.get(0), result.get(0));
        assertEquals(expectedList.get(1), result.get(1));

    }


    public static MultipartFile createExcelFile() {
        try {
            // create workbook and sheet

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("January");


            // create rows and cells with sample data
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("S.No.");
            rowhead.createCell(1).setCellValue("Customer Name");
            rowhead.createCell(2).setCellValue("Account Number");
            rowhead.createCell(3).setCellValue("e-mail");
            rowhead.createCell(4).setCellValue("Balance");

            HSSFRow row1 = sheet.createRow((short) 1);
            row1.createCell(0).setCellValue("1");
            row1.createCell(1).setCellValue("John William");
            row1.createCell(2).setCellValue("9999999");
            row1.createCell(3).setCellValue("william.john@gmail.com");
            row1.createCell(4).setCellValue("700000.00");

            HSSFRow row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("2");
            row2.createCell(1).setCellValue("Mathew Parker");
            row2.createCell(2).setCellValue("22222222");
            row2.createCell(3).setCellValue("parker.mathew@gmail.com");
            row2.createCell(4).setCellValue("200000.00");

            // write workbook to ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelBytes = out.toByteArray();

            // create MultipartFile
            MultipartFile multipartFile = new MultipartFile() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getOriginalFilename() {
                    return "Balance.xlsx";
                }

                @Override
                public String getContentType() {
                    return "application/vnd.ms-excel";
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    return excelBytes.length;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return excelBytes;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return new ByteArrayInputStream(excelBytes);
                }

                @Override
                public void transferTo(File dest) throws IOException, IllegalStateException {
                    new FileOutputStream(dest).write(excelBytes);
                }
            };

            workbook.close(); // close workbook
            return multipartFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
