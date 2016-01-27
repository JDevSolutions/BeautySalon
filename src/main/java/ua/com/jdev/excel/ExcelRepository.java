package ua.com.jdev.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import ua.com.jdev.dbase.DBHelper;
import ua.com.jdev.model.Goods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by Jurimik on 27.01.2016.
 */
public class ExcelRepository {
    private static void insertAllGoodsFromFile(String pathFile) {
        try {
            FileInputStream file = new FileInputStream(new File(pathFile));
//           FileInputStream file = new FileInputStream(new File("D:\\Download\\Skype\\price.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                Cell code = row.getCell(0);
                Cell name = row.getCell(1);
                Cell amount = row.getCell(2);
                Cell price = row.getCell(6);
                try {
                    //TODO: Реаализовать проверку на сущесттвующий товар по коду товара
                    DBHelper.insert(new Goods(code.toString(), name.toString(), price.toString(), amount.toString()));
//                   System.out.println(goods.getName() + ", код: " + goods.getCode() + ", есть в наличии: "
                    //                         + goods.getAmount() + ", цена: " + goods.getPrice());
                } catch (NullPointerException npe) {

                }
            }
            file.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}