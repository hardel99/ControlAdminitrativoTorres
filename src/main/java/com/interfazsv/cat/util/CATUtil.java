package com.interfazsv.cat.util;

import com.interfazsv.cat.main.MainApp;
import com.jfoenix.controls.JFXButton;
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
    public static final String ICON_PATH = "/resources/icons/icon.png";
    
    public static void setStageIcon(Stage stage){
        stage.getIcons().add(new Image(ICON_PATH));
    }
    
    public static Object loadWindow(URL url, String title, Stage parentStage){
        Object controller = null;
        try{
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            
            if(parentStage != null){
                stage = parentStage;
            } else{
                stage = new Stage(StageStyle.UNDECORATED);
            }
            
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch(IOException e){
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
        }
        return controller;
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
        JFXButton openBtn = new JFXButton("Abrir Carpeta");
        openBtn.setOnAction((ActionEvent event1) -> {
            try {
                if(saveLoc.exists()){
                    Desktop.getDesktop().open(saveLoc.getCanonicalFile());
                } else{
                    Desktop.getDesktop().open(saveLoc.getParentFile());
                    System.out.println("nigga wtf!!");
                }
                
            } catch (Exception exp) {
                System.out.println(exp);
                AlertFactory.showErrorMessage("No se puede abrir el archivo", "Un error ha ocurrido no se puede abrir el archivo");
            }
        });
        if (flag) {
            AlertFactory.showDialog(rootPane, contentPane, Arrays.asList(okayBtn, openBtn), "Completado", "Los datos han sido exportados satisfactoriamente");
        }
    }
}
