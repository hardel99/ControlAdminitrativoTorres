package com.interfazsv.cat.util;

import TableData.ClientesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import TableData.VentasTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author hardel
 */
public class CATUtil {
    public static final String ICON_PATH = "/icons/icon.png";
    private static Stage stage = null;
    
    public static void setStageIcon(Stage stage){
        stage.getIcons().add(new Image(ICON_PATH));
    }
    
    public static Object loadWindow(URL url, String title, Stage parentStage){
        Object controller = null;
        try {
            
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            controller = loader.getController();
            
            starConfigs(parentStage, parent, title, loader);
        } catch (IOException ex) {
            Logger.getLogger(CATUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return controller;
    }
    
    public static Object loadWindow(URL url, String title, Stage parentStage, Object rto, String tableName){
        ControllerDataComunication cdc = null;
        try {
            FXMLLoader loader = new FXMLLoader(url);
            
            Parent parent = loader.load();
            cdc = loader.getController();
            
            if(tableName.equalsIgnoreCase("oferta")){
                cdc.initDataOffer((MainOfferTable) rto, tableName);
            } else if(tableName.equalsIgnoreCase("sitio")){
                cdc.initDataSitio((SitiosTable) rto, tableName);
            } else if(tableName.equalsIgnoreCase("cliente")){
                cdc.initDataClient((ClientesTable) rto, tableName);
            } else if(tableName.equalsIgnoreCase("venta")){
                cdc.initDataVenta((VentasTable) rto, tableName);
            }
            
            starConfigs(parentStage, parent, title, loader);
        } catch (IOException ex) {
            Logger.getLogger(CATUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cdc;
    }
    
    private static void starConfigs(Stage parentStage, Parent parent, String title, FXMLLoader loader) {
        if(parentStage != null){
            stage = parentStage;
        } else{
            stage = new Stage(StageStyle.DECORATED);
        }
         
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.show();
        setStageIcon(stage);
    }
    
    public static void openFileOnDesktop(File file){
        try {
            Desktop desk = Desktop.getDesktop();
            desk.open(file);
        } catch (IOException ex) {
            Logger.getLogger(CATUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void initPDFExport(StackPane rootPane, Node contentPane, Stage stage, List<List> data) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como PDF");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));
        File saveLoc = fileChooser.showSaveDialog(stage);
        ListToPDF ltp = new ListToPDF();
        
        boolean flag = ltp.printIt(data, saveLoc);
        JFXButton okayBtn = new JFXButton("Ok");
        JFXButton openBtn = new JFXButton("Abrir");
        openBtn.setOnAction((ActionEvent event1) -> {
            try {
                if(saveLoc.exists()){
                    Desktop.getDesktop().open(saveLoc);
                }
            } catch (IOException exp) {
                System.out.println(exp);
                AlertFactory.showErrorMessage("No se puede abrir el archivo", "Un error ha ocurrido no se puede abrir el archivo");
            }
        });
        JFXButton openFolderBtn = new JFXButton("Abrir Carpeta");
        openFolderBtn.setOnAction((ActionEvent event2) -> {
            try {
                Desktop.getDesktop().open(saveLoc.getParentFile());
            } catch (IOException exp) {
                System.out.println(exp);
                AlertFactory.showErrorMessage("No se puede abrir el directorio", "Un error ha ocurrido no se puede abrir el archivo");
            }
        });
        
        if (flag) {
            AlertFactory.showDialog(rootPane, contentPane, Arrays.asList(okayBtn, openBtn, openFolderBtn), "Completado", "Los datos han sido exportados satisfactoriamente");
        }
    }
    
    public static void clearFields(List<JFXTextField> fields) {
        fields.forEach(field -> {
            field.setText("");
        });
    }
}
