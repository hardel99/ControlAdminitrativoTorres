package com.interfazsv.cat.detail;

import Entitys.llave;
import Entitys.oferta;
import Entitys.torre;
import TableData.ClientesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import com.interfazsv.cat.util.AlertFactory;
import com.interfazsv.cat.util.CATUtil;
import com.interfazsv.cat.util.ControllerDataComunication;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author hardel
 */
public class DetailController extends ControllerDataComunication implements Initializable{
    /*
    *TO-DO:
    *Validate to add something
    *Save the currentLists
    *Modify image on ofertaPane
    */
    @FXML
    private Label tableDisplay;
    
    @FXML
    private AnchorPane sitioPane;
    
    @FXML
    private AnchorPane ofertaPane;
    
    @FXML
    private AnchorPane clientePane;
    
    @FXML
    private ImageView imageDisplaySitio;
    
    @FXML
    private JFXTextArea comentarioSitio;

    @FXML
    private JFXTextField longitudSitio;

    @FXML
    private JFXTextField latitudSitio;

    @FXML
    private JFXTextField nombreSitio;

    @FXML
    private JFXTextField totalCostSitio;

    @FXML
    private JFXTextField costoAlcaldiaSitio;

    @FXML
    private JFXTextField costoArrendamientoSitio;
    
    @FXML
    private JFXTextField sitioOferta;
    
    @FXML
    private JFXComboBox<String> estadoComboBox;

    @FXML
    private JFXTextField clienteOferta;

    @FXML
    private JFXTextField alturaDisOferta;
    
    @FXML
    private JFXTextField alturaSolicOferta;
    
    @FXML
    private ImageView imageDisplayOferta;

    @FXML
    private JFXDatePicker fechaOferta;
    
    @FXML
    private JFXTextField nombreCliente;
    
    @FXML
    private JFXListView<String> torresList;

    @FXML
    private JFXListView<String> llavesList;

    @FXML
    private JFXListView<String> ofertasList;
    
    @FXML
    private JFXTextField addOferta;
    
    @FXML
    private JFXButton eliminarOferta;
    
    @FXML
    private JFXTextField addLlave;
    
    @FXML
    private JFXButton eliminarLlave;
    
    @FXML
    private JFXTextField addTorre;
    
    @FXML
    private JFXButton eliminarTorre;
    
    private String pathToAlcaldia, pathToArrendamiento;
    private final String DEFAULT_IMAGE_PATH = "C:\\Users\\hardel\\Pictures\\prub.jpg";
    private final File temporary = new File(DEFAULT_IMAGE_PATH);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //dude idk
    }

    @Override
    public void initDataOffer(MainOfferTable rto, String table) {
        tableDisplay.setText(table);
        ofertaPane.setVisible(true);
        estadoComboBox.getItems().addAll("Completado", "Incompleto", "Pendiente");
        
        estadoComboBox.setValue(rto.getEstado());
        sitioOferta.setText(rto.getSitio());
        clienteOferta.setText(rto.getCliente());
        alturaDisOferta.setText(rto.getAlturaDis().toString());
        alturaSolicOferta.setText(rto.getAltura().toString());
        
        /*imageDisplayOferta.setImage(new Image(rto.getImagePath()));<----ON REAL DATA USE THIS!!!*/
        imageDisplayOferta.setImage(new Image(temporary.toURI().toString()));
        
        fechaOferta.setValue(LocalDate.parse(rto.getFecha(), DateTimeFormatter.ofPattern("uuuu/MM/d")));
    }

    @Override
    public void initDataClient(ClientesTable rto, String table) {
        tableDisplay.setText(table);
        clientePane.setVisible(true);
        
        nombreCliente.setText(rto.getNombre());
        addItemsFromList(rto.getOfertas(), ofertasList);
        addItemsFromList(rto.getLlaves(), llavesList);
        addItemsFromList(rto.getTorres(), torresList);
        
        setSelectionConfigs(ofertasList, eliminarOferta);
        setSelectionConfigs(llavesList, eliminarLlave);
        setSelectionConfigs(torresList, eliminarTorre);
    }
    
    private void setSelectionConfigs(JFXListView lv, JFXButton button){
        lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(lv.getSelectionModel().getSelectedItem() != null){
                    button.setDisable(false);
                }else{
                    button.setDisable(true);
                }
            }
        });
    }

    @Override
    public void initDataSitio(SitiosTable rto, String table) {
        tableDisplay.setText(table);
        sitioPane.setVisible(true);
        
        float costoTotal = rto.getCostosAlcadia() + rto.getCostosArrendamiento();
        
        nombreSitio.setText(rto.getNombre());
        latitudSitio.setText(rto.getLatitud().toString());
        longitudSitio.setText(rto.getLongitud().toString());
        comentarioSitio.setText(rto.getComent());
        totalCostSitio.setText(costoTotal + "");
        costoAlcaldiaSitio.setText(rto.getCostosAlcadia().toString());
        costoArrendamientoSitio.setText(rto.getCostosArrendamiento().toString());
        
        
        imageDisplaySitio.setImage(new Image(temporary.toURI().toString()));
        
        pathToAlcaldia = rto.getDocumentoAlcaldia();
        pathToArrendamiento = rto.getDocumentoArrendamiento();
    }
    
    private void addItemsFromList(List list, JFXListView jfx){
        if(!list.isEmpty()){
            Object typing = list.get(0);
            if(typing instanceof llave){
                List<llave> realList = list;
                for(int i=0; i< list.size(); i++){
                    jfx.getItems().add(realList.get(i).getId());
                }
            } else if(typing instanceof oferta){
                List<oferta> realList = list;
                for(int i=0; i< list.size(); i++){
                    jfx.getItems().add(realList.get(i).getLocacion().getNombre());
                }
            } else if(typing instanceof torre){
                List<torre> realList = list;
                for(int i=0; i< list.size(); i++){
                    jfx.getItems().add(realList.get(i).getLocalidad());
                }
            }
        }
    }
    
    private void addItemManually(JFXTextField textField, JFXListView listView){
        if(textField.getText() != null && !textField.getText().isEmpty()){
            listView.getItems().add(textField.getText());
        } else{
            AlertFactory.showInfoMessage("Campo en blanco", "El regitro esta vacio, por favor completar");
        }
    }
    
    private void deleteItem(JFXListView list){
        int selectedIndex = list.getSelectionModel().getSelectedIndex();
        list.getItems().remove(selectedIndex);
    }
    
    @FXML
    void openAlcaldia(ActionEvent event) {
        CATUtil.openFileOnDesktop(new File(pathToAlcaldia));
    }

    @FXML
    void openArrendamiento(ActionEvent event) {
        CATUtil.openFileOnDesktop(new File(pathToArrendamiento));
    }
    
    @FXML
    void openOferta(ActionEvent event) {
        CATUtil.openFileOnDesktop(new File("wait until i set this"));
    }
    
    @FXML
    void openImage(MouseEvent event) {
        CATUtil.openFileOnDesktop(new File(DEFAULT_IMAGE_PATH));
    }
    
    @FXML
    void openImageFolder(ActionEvent event) {
        CATUtil.openFileOnDesktop(temporary.getParentFile());
    }
    
    @FXML
    void addToLlavesList(ActionEvent event) {
        addItemManually(addLlave, llavesList);
    }

    @FXML
    void addToOfferList(ActionEvent event) {
        addItemManually(addOferta, ofertasList);
    }

    @FXML
    void addToTorreList(ActionEvent event) {
        addItemManually(addTorre, torresList);
    }
    
    @FXML
    void deleteLlave(ActionEvent event) {
        deleteItem(llavesList);
    }

    @FXML
    void deleteOferta(ActionEvent event) {
        deleteItem(ofertasList);
    }

    @FXML
    void deleteTorre(ActionEvent event) {
        deleteItem(torresList);
    }
}
