package com.interfazsv.cat.util;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hardel
 */
public class ListToPDF {
    
    private int columnIndex = 1;
    
    public boolean printIt(List<List> list, File saveItHere){
        try {
            //For make sure!
            if(saveItHere == null){
                return false;
            }
            
            //Init the PDF File
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            
            //only portrait for this version
            page.setMediaBox(PDRectangle.LETTER);
            doc.addPage(page);
            
            //pageValues
            float margin = 20.0f;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
            float yOffset = page.getMediaBox().getHeight() - (2 * margin);
            
            //now get the data from the table
            BaseTable base = new BaseTable(yOffset, yOffset, 0, tableWidth, margin, doc, page, true, true);
            DataTable data = new DataTable(base, page);
            data.addListToTable(list, DataTable.HASHEADER);
            base.draw();
            doc.save(saveItHere);
            doc.close();
            
            return true;
        } catch (IOException ex) {
            AlertFactory.showErrorMessage("Error al exportar!", ex.toString());
        }
        
        return false;
    }
    
    public boolean printAsXSSL(List<List> data, File saveItHere) {
        FileOutputStream fos = null;
        try {
            if(saveItHere == null){
                return false;
            }   
            
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet();
            
            XSSFFont normal = book.createFont();
            normal.setFontName("Calibri");
            normal.setFontHeight((short)16);
            normal.setColor(IndexedColors.BLACK.getIndex());
            
            CellStyle normalCell = book.createCellStyle();
            normalCell.setAlignment(CellStyle.ALIGN_CENTER);
            normalCell.setFillPattern(CellStyle.SOLID_FOREGROUND);
            normalCell.setFont(normal);
            
            XSSFFont changed = normal;
            changed.setBold(true);
            
            CellStyle changedCell = normalCell;
            changedCell.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            changedCell.setFont(changed);
            
            int indexRow = 1;
            data.forEach(listRow -> {
                XSSFRow row = sheet.createRow(indexRow);
                listRow.forEach(cell -> {
                    row.createCell(columnIndex).setCellValue((String) cell);
                    row.getCell(columnIndex).setCellStyle(normalCell);
                    if(columnIndex > 1 && indexRow > 2) {
                        if(row.getCell(columnIndex) != row.getCell(columnIndex - 1)) {
                            row.getCell(columnIndex).setCellStyle(changedCell);
                        }
                    } else if(indexRow == 1){
                        sheet.autoSizeColumn(columnIndex);
                        //sheet.setColumnWith(index, width)
                    }
                    
                    columnIndex++;
                });
                columnIndex = 1;
            });
            
            fos = new FileOutputStream(saveItHere);
            book.write(fos);
            
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListToPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListToPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ListToPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
}
