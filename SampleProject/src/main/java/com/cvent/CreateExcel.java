package com.cvent;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by a.srivastava on 6/16/17.
 */
public class CreateExcel {

    public static void main(String[] args) {
        CreateExcel ce = new CreateExcel();
        ce.createExcelFile();
    }
    
    
    public void createExcelFile() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        Object[][] createdDataTypes = createDataContactSearch();
        int rowNum = 0;
        System.out.println("Creating excel");

        for (Object[] datatype : createdDataTypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("/Users/a.srivastava/cvent.git/tmp/CreateContact.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    private Object[][] createDataContactSearch() {
        int contactCount = 50000;
        String[] headerArray = new String[]{"Email Address","First Name  Last Name","Source ID","CC Email Address","Nickname"," Company Title","Social Security Number  National Identification Number  Passport Number Gender  Date of Birth","Passport Country"," Prefix  Work Address 1  Work Address 2  Work Address 3  Work City","Work State  Work ZIP/Postal Code"," Work Country"," Work Phone  Home Address 1  Home Address 2  Home Address 3  Home City","Home State  Home ZIP/Postal Code"," Home Country"," Home Phone  Work Fax"," Mobile Phone"," Home Fax"," Contact Type (Code or Name) Middle Name Designation Pager Number"," Opted-Out","Facebook URL"," LinkedIn URL"," Twitter URL Primary Address Membership Code Join Date","Expiration Date Hotel Billing InstructionsAAAAAAAAAA"," Type a Number other than 1 edited","Department Name (e.g. Finance, R&D, Sales, etc.);","Single choice vertical  Mohit's single choice vertical custom field Long Long Long Long Long"," 6.1 Release Nite_100 choices"," issue 27088 Choice - Single answer (Verticle)","WHat is you age Date/time","Decimal English-C_CUSTOM1","English-C_CUSTOM2","C_CUSTOM3","Contact Custom Email Address"," Contact Custom Email Address 2  Multiple Answers"," Category"," CONTACT INFORMATION test"," test_neha","test format Test019 Davey Test  Isemailverfied  usertype"," test drop down  Email - Open ended one line US Phone Number - Ope ended text"," Date and Time (Month) - Open ended Date/Time"," Date and Time (Day) - Open ended Date/Time  Comment box Single ans - Drop down  Single ans - Radio button - Vertical"," Single ans - Radio button - Horizontal  Multiple ans - Multi select box Multiple ans - Check box - vertical Multiple ans - Check box - horizontal","is your name bob"," Newsletters Best Practices & Content"," S Number"," Choice Single Answer DD-1 Parent"," Choice Single Answer DD-2 child testmaster  MM :: Field Z","AG::Contact custom  AG::after","En:aaaa GD Single Answe CCF AB-DateTime AB-OneLine  AB-commentBox","AB-SingleAns-DD AB-SingleAns-Hor"," AB-SingleAns-Ver"," AB-MultiAns-Box AB-MultiAns-Ver AB-MultiAns-Hor"};
        Object[][] createdDataTypes = new Object[contactCount][headerArray.length];
        createdDataTypes[0] = headerArray;
        for (int i = 1; i <= contactCount-1; i++) {
            String first = "R" + i;
            String last = "N" + i;
            String email = first + last + "@j.mail";
            Object[] row = createdDataTypes[i];
            int index = 0;
            for (String headerName : headerArray) {
                if (index == 0) {
                    row[index] = email;
                } else if (index == 1) {
                    row[index] = first;
                } else if (index == 2) {
                    row[index] = last;
                } else {
                    row[index] = StringUtils.EMPTY;
                }
                index += 1;
            }
        }
        return createdDataTypes;
    }

}
