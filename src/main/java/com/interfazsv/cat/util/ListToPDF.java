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
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hardel
 */
public class ListToPDF {
    
    public enum Orientation {
        PORTRAIT, LANDSCAPE
    };
    
    private int columnIndex = 1;
    private int indexRow = 1;
    
    public boolean printIt(List<List> list, File saveItHere, Orientation orien){
        try {
            //For make sure!
            if(saveItHere == null){
                return false;
            }
            
            //Init the PDF File
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            
            //only portrait for this version
            if(orien == Orientation.PORTRAIT) {
                page.setMediaBox(PDRectangle.LETTER);
            } else{
                page.setMediaBox(new PDRectangle(PDRectangle.LETTER.getHeight(), PDRectangle.LETTER.getWidth()));
            }
            
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
    
    @SuppressWarnings("null")
    public boolean printAsXSSL(List<List> data, List<List> morData, File saveItHere, boolean numeric) {
        FileOutputStream fos = null;
        try {
            if(saveItHere == null){
                return false;
            }   
            
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet();
            
            XSSFCellStyle normalCell = book.createCellStyle();
            normalCell.setAlignment(CellStyle.ALIGN_CENTER);
            normalCell.getCoreXf().unsetBorderId();
            normalCell.getCoreXf().unsetFillId();
            normalCell.setFillPattern(XSSFCellStyle.NO_FILL);
            normalCell.getFont().setBold(false);
            
            XSSFCellStyle changedCell = book.createCellStyle();
            changedCell.cloneStyleFrom(normalCell);
            changedCell.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            changedCell.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            changedCell.getFont().setBold(true);
            
            boolean moment = false;
            data.forEach(listRow -> {
                XSSFRow row = sheet.createRow(indexRow);
                listRow.forEach(cell -> {
                    if(numeric) {
                        if(columnIndex > 2 && indexRow > 1) {
                            row.createCell(columnIndex).setCellValue(Double.parseDouble((String) cell));
                            if(columnIndex > 4){
                                if(row.getCell(columnIndex).getNumericCellValue() > row.getCell(columnIndex - 1).getNumericCellValue()) {
                                    if(!row.getCell(columnIndex - 1).getCellStyle().equals(changedCell)) {
                                        row.getCell(columnIndex).setCellStyle(changedCell);
                                    } else{
                                        row.getCell(columnIndex).setCellStyle(normalCell);
                                    }
                                } else{
                                    row.getCell(columnIndex).setCellStyle(normalCell);
                                }
                            }
                        } else{
                            row.createCell(columnIndex).setCellValue((String) cell);
                            row.getCell(columnIndex).setCellStyle(normalCell);
                        }
                        
                    } else{
                        row.createCell(columnIndex).setCellValue((String) cell);
                        row.getCell(columnIndex).setCellStyle(normalCell);
                    }
                    
                    sheet.autoSizeColumn(columnIndex);
                    columnIndex++;
                });
                row.getCell(columnIndex - 1).setCellStyle(changedCell);
                sheet.autoSizeColumn(columnIndex);
                
                columnIndex = 1;
                indexRow++;
            });
            int lastIndex = indexRow;
            
            if(!morData.isEmpty()) {
                indexRow++;
                XSSFRow rowCostos = sheet.createRow(indexRow);
                rowCostos.createCell(columnIndex).setCellValue("Costos");
                columnIndex = 3;
                
                morData.forEach(costoColumn -> {
                    costoColumn.forEach(costo -> {
                        if(columnIndex > 2) {
                            rowCostos.createCell(columnIndex).setCellValue((double) costo);
                        }
                        columnIndex++;
                    });
                    columnIndex = 3;
                });
                indexRow++;
                
                XSSFRow totales = sheet.createRow(indexRow);
                totales.createCell(1).setCellValue("Totales :");
                morData.get(0).forEach(costo -> {
                    String col = CellReference.convertNumToColString(columnIndex);
                    XSSFCell celda = totales.createCell(columnIndex);
                    celda.setCellType(XSSFCell.CELL_TYPE_FORMULA);
                    celda.setCellFormula("SUM(" + col + "3: " + col + lastIndex +") - " + col + (lastIndex + 2));
                    columnIndex++;
                });
                totales.getCell(columnIndex).setCellStyle(changedCell);
            }
            
            fos = new FileOutputStream(saveItHere);
            book.write(fos);
            
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListToPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListToPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ListToPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
}
