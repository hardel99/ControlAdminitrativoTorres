package com.interfazsv.cat.custom;

import com.interfazsv.cat.util.AlertFactory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

/**
 * 
 *
 * @author hardel
 */
public class CustomExportController implements Initializable {
    /**
     * TO-DO:
     * 
     * Actually the columns causs this thing doent work pretty well whit widths
     * Rows of the fields
     * Conditioners
     * */
    @FXML
    private StackPane root;
    
    @FXML
    private JFXChipView<String> chipView;
    
    @FXML
    private TableView<String> customTable;
    
    private JFXButton aceptBtn = new JFXButton("Ok");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chipView.getSuggestions().addAll("Lugar", "Latitud", "Longitud", "Ofertas", "Costo Alcaldia", "Costo Arrendamiento", "Disponible", "Fecha Oferta", "Cliente");
        
        chipView.getChips();
    }   
    
    @FXML
    private void addFields(ActionEvent event) {
        customTable.getColumns().clear();
        ObservableList<String> chips = chipView.getChips();
        if(chips.size() > 0){
            chips.forEach((field) -> {
                TableColumn tc = new TableColumn(field);
                tc.setPrefWidth(200);
                customTable.getColumns().add(tc);
            });
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Datos en blanco", "Por favor añada almenos un campo a exportar");
        }
    }
    
    @FXML
    private void clearFields(ActionEvent event) {
        if(customTable.getColumns().size() > 0){
            chipView.getChips().clear();
            customTable.getColumns().clear();
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Datos en blanco", "No ha agregado ninguna columna aun");
        }
    }
    
    @FXML
    private void exportIt(ActionEvent event) {
        //u know what 2 do
    }
}
