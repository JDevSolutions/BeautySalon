package ua.com.jdev.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ua.com.jdev.entity.DBEntity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateXlsFile {

    public static void generateFile(DBEntity entity) {

    }

    public static void generateFile(DBEntity entity, String path) {

    }


    public static void testFileCreate(String path) {
        try {
            //String fileName = "H:/Temp/TestFile.xls" ;

            HSSFWorkbook book = new HSSFWorkbook();
            HSSFSheet sheet = book.createSheet("Mounth");

            HSSFRow rowhead0 = sheet.createRow(0);
            rowhead0.createCell(0).setCellValue("firstName");
            rowhead0.createCell(1).setCellValue("lastName");
            rowhead0.createCell(2).setCellValue("phone");
            rowhead0.createCell(3).setCellValue("profession");
            rowhead0.createCell(4).setCellValue("salary");

            HSSFRow row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("Anastasia");
            row1.createCell(1).setCellValue("Kirichenko");
            row1.createCell(2).setCellValue("+38(055)120-3-456");
            row1.createCell(3).setCellValue("HairDresser");
            row1.createCell(4).setCellValue("5000");
            HSSFRow row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("Kristina");
            row2.createCell(1).setCellValue("Tokar");
            row2.createCell(2).setCellValue("+38(055)123-78-14");
            row2.createCell(3).setCellValue("Manicurist");
            row2.createCell(4).setCellValue("4500");
            HSSFRow row3 = sheet.createRow(3);
            row3.createCell(0).setCellValue("Irina");
            row3.createCell(1).setCellValue("Yareschenko");
            row3.createCell(2).setCellValue("+38(055)331-3403");
            row3.createCell(3).setCellValue("Administrator");
            row3.createCell(4).setCellValue("5300");

            FileOutputStream fileOut = new FileOutputStream(path);
            book.write(fileOut);
            fileOut.close();
            System.out.println("Excel file has been created!");
        } catch (FileNotFoundException fEx) {
            //TODO: insert log
            System.out.println("Неверно указан путь к файлу!");
        } catch (IOException ex) {
            //TODO: insert log
            System.out.println("Error! " + ex.toString());
        }
    }
}
