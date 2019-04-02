package com.interfazsv.cat.detail;

import com.interfazsv.cat.util.ControllerDataComunication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author hardel
 */
public class DetailController extends ControllerDataComunication implements Initializable{
    @FXML
    private Label idDisplay;

    @FXML
    private Label tableDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //nothing till now
    }
    
    @FXML
    void closeThis(MouseEvent event) {
        Stage stage = (Stage) idDisplay.getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimizeThis(MouseEvent event) {
        Stage stage = (Stage) idDisplay.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initData(long id, String table) {
        receivedID = id;
        receivedTable = table;
        
        idDisplay.setText(id + "");
        tableDisplay.setText(table);
    }
}
