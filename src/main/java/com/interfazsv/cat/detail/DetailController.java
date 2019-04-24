package com.interfazsv.cat.detail;

import Entitys.llave;
import Entitys.oferta;
import Entitys.torre;
import TableData.ClientesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import TableData.VentasTable;
import callback.DataReturnCallback;
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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    *Make the user add fecha init and fin when complete the offer
    *Basically anithyng related to move a file
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
    private GridPane ofertaGrid;
    
    @FXML
    private GridPane ventaGrid;
    
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
    private JFXTextField montoOferta;

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
    
    @FXML
    private JFXDatePicker fechaFinVenta;

    @FXML
    private JFXDatePicker fechaInicioVenta;

    @FXML
    private JFXTextField canonActualVenta;
    
    private String pathToAlcaldia, pathToArrendamiento;
    private final String DEFAULT_IMAGE_PATH = "C:\\Users\\hardel\\Pictures\\prub.jpg";
    private final File temporary = new File(DEFAULT_IMAGE_PATH);
    
    //When change anything
    private String estado;
    private boolean isChanged;
    private DataReturnCallback callback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //dude idk
    }

    public void setCallback(DataReturnCallback callback) {
        this.callback = callback;
    }

    @Override
    public void initDataOffer(MainOfferTable rto, String table) {
        tableDisplay.setText(table);
        ofertaPane.setVisible(true);
        estadoComboBox.getItems().addAll("Completado", "Incompleto", "Pendiente");
        
        estadoComboBox.setValue(rto.getEstado());
        estado = rto.getEstado();
        sitioOferta.setText(rto.getSitio());
        clienteOferta.setText(rto.getCliente());
        montoOferta.setText(rto.getMonto().toString());
        alturaDisOferta.setText(rto.getAlturaDis().toString());
        alturaSolicOferta.setText(rto.getAltura().toString());
        
        /*imageDisplayOferta.setImage(new Image(rto.getImagePath()));<----ON REAL DATA USE THIS!!!*/
        imageDisplayOferta.setImage(new Image(temporary.toURI().toString()));
        
        fechaOferta.setValue(LocalDate.parse(rto.getFecha(), DateTimeFormatter.ofPattern("uuuu/MM/d")));
        
        ventaGrid.setVisible(false);
        ofertaGrid.setVisible(true);
    }
    
    @Override
    public void initDataVenta(VentasTable rto, String table) {
        tableDisplay.setText(table);
        ofertaPane.setVisible(true);
        estadoComboBox.getItems().addAll("Completado", "Incompleto", "Pendiente");
        estadoComboBox.setValue(rto.getEstado());
        estadoComboBox.setDisable(true);
        
        sitioOferta.setText(rto.getSitio());
        clienteOferta.setText(rto.getCliente());
        montoOferta.setText(rto.getMonto().toString());
        alturaSolicOferta.setText(rto.getAlturaDis().toString());
        
        /*imageDisplayOferta.setImage(new Image(rto.getImagePath()));<----ON REAL DATA USE THIS!!!*/
        imageDisplayOferta.setImage(new Image(temporary.toURI().toString()));
        
        fechaInicioVenta.setValue(rto.getFechaInicio());
        fechaFinVenta.setValue(rto.getFechaFin());
        canonActualVenta.setText(rto.getCanonActual().toString());
        
        ventaGrid.setVisible(true);
        ofertaGrid.setVisible(false);
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
    
    @FXML
    void saveChanges(ActionEvent event) {
        if(ofertaPane.isVisible()){
            if(ofertaGrid.isVisible()){
                if(!estado.equals(estadoComboBox.getSelectionModel().getSelectedItem())){
                    if(estadoComboBox.getValue().equals("Completado")){
                        showConfirmDialog();
                        if(isChanged){
                            System.out.println("Awebo lo hizo broder");
                        } else{
                            System.out.println("Detectado no cambio nada despues de su dialogo >:(");
                        }
                    } else{
                        System.out.println("Cambio pero no a completado!");
                    }
                } else{
                    System.out.println("No cambio nada broder");
                }
            } 
        } else if(clientePane.isVisible()){
            //clientePane stuffs
        } else if(sitioPane.isVisible()){
            //sitioPane stuffs
        }
    }

    private void showConfirmDialog() {
        isChanged = false;
        Dialog<DateSetters> dialog = new Dialog();
        dialog.setResizable(true);
        dialog.setTitle("Completar Oferta");
        dialog.setHeaderText("Por favor agregue…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Stage window = (Stage) dialogPane.getScene().getWindow();
        window.setMinWidth(400);
        CATUtil.setStageIcon(window);
        
        Label init = new Label("Fecha de Inicio del Contrato :");
        JFXDatePicker fechaInit = new JFXDatePicker(LocalDate.now());
        Label end = new Label("Fecha de Finalización del Contrato");
        JFXDatePicker fechaEnd = new JFXDatePicker(LocalDate.now());
        
        dialogPane.setContent(new VBox(30, init, fechaInit, end, fechaEnd));
        Platform.runLater(fechaInit::requestFocus);
        
        EventHandler<ActionEvent> filter = event -> {
            if(fechaInit.getValue().isAfter(fechaEnd.getValue())) {
                AlertFactory.showInfoMessage("Error al ingresar las fechas", "Revise que la fecha de inicio sea antes que la fecha final");
		event.consume();
            }
        };
        
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                if(fechaEnd.getValue().isAfter(fechaInit.getValue())){
                    isChanged = true;
                    fechaInicioVenta.setValue(fechaInit.getValue());
                    fechaFinVenta.setValue(fechaEnd.getValue());
                } 
                return new DateSetters(fechaInit.getValue(), fechaEnd.getValue());
            }
            
            return null;
        });
        
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, filter);
        dialog.showAndWait();
    }
    
    private class DateSetters{
        LocalDate inicio;
        LocalDate fin;

        public DateSetters(LocalDate inicio, LocalDate fin) {
            this.inicio = inicio;
            this.fin = fin;
        }
    }
}
