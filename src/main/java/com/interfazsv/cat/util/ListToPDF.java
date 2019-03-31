package com.interfazsv.cat.util;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 *
 * @author hardel
 */
public class ListToPDF {
    
    public boolean printIt(List<List> list, File saveItHere){
        try {
            //For make sure!
            if(saveItHere == null){
                return false;
            }
            if(!saveItHere.getName().endsWith(".pfd")){
                saveItHere = new File(saveItHere.getAbsolutePath() + ".pdf");
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
}
