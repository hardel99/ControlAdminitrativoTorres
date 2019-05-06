package com.interfazsv.cat.detail;

import Entitys.cliente;
import Entitys.llave;
import Entitys.oferta;
import Entitys.sitio;
import Entitys.venta;
import TableData.VentasTable;
import TableData.LlavesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import callback.DataReturnCallback;
import com.interfazsv.cat.util.AlertFactory;
import com.interfazsv.cat.util.CATUtil;
import com.interfazsv.cat.util.ControllerDataComunication;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hardel
 */
public class DetailController extends ControllerDataComunication implements Initializable{
    /*
    *TO-DO:
    *validate fields
    **/
    @FXML
    private StackPane root;
    
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
    private ScrollPane scrollSitio;

    @FXML
    private JFXMasonryPane imageDisplaySitio;
    
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
    private JFXComboBox<String> estadoComboBox;

    @FXML
    private JFXComboBox<String> sitioOferta;

    @FXML
    private JFXComboBox<String> clienteOferta;
    
    @FXML
    private JFXTextField montoOferta;

    @FXML
    private JFXTextField alturaDisOferta;
    
    @FXML
    private JFXTextField alturaSolicOferta;
    
    @FXML
    private ScrollPane scrollOferta;

    @FXML
    private JFXMasonryPane imageDisplayOferta;

    @FXML
    private JFXDatePicker fechaOferta;
    
    @FXML
    private JFXComboBox<String> clienteLlave;

    @FXML
    private JFXComboBox<String> sitioLlave;

    @FXML
    private JFXComboBox<String> subempresaLlave;

    @FXML
    private JFXTextField cantidadLlave;

    @FXML
    private JFXDatePicker retiroLlave;

    @FXML
    private JFXDatePicker devolucionLlave;

    @FXML
    private JFXTextField nombreRetiraLlave;

    @FXML
    private JFXTextField encargadoLlave;

    @FXML
    private JFXTextField telefonoLlave;

    @FXML
    private JFXTextField duiLlave;
    
    @FXML
    private JFXDatePicker fechaFinVenta;

    @FXML
    private JFXDatePicker fechaInicioVenta;
    
    @FXML
    private JFXTextField canonActualVenta;
    
    private String pathToAlcaldia, pathToArrendamiento, pathToOferta, pathToDUI;
    private File imageFolder;
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
    private long idSelected;
    
    private final Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
    private final UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
        String text = t.getControlNewText();
        
        return (validEditingState.matcher(text).matches())?t:null;
    };
    
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
        idSelected = rto.getIdOferta();
        estadoComboBox.getItems().addAll("Completado", "Incompleto", "Pendiente");
        
        estadoComboBox.setValue(rto.getEstado());
        estado = rto.getEstado();
        
        fillComboBox();
        montoOferta.setText(rto.getMonto().toString());
        montoOferta.setTextFormatter(new TextFormatter<>(filter));
        alturaDisOferta.setText(rto.getAlturaDis().toString());
        alturaDisOferta.setTextFormatter(new TextFormatter<>(filter));
        alturaSolicOferta.setText(rto.getAltura().toString());
        alturaSolicOferta.setTextFormatter(new TextFormatter<>(filter));
        canonActualVenta.setText(rto.getCanon().toString());
        canonActualVenta.setTextFormatter(new TextFormatter<>(filter));
        
        //addImageToPane(imageDisplayOferta, rto.getImagePath(), scrollOferta);
        addImageToPane(imageDisplayOferta, rto.getImagePath());
        imageFolder = new File(rto.getImagePath());
        pathToOferta = rto.getDocumentPath();
        
        fechaOferta.setValue(LocalDate.parse(rto.getFecha(), DateTimeFormatter.ofPattern("uuuu/MM/d")));
        
        ventaGrid.setVisible(false);
        ofertaGrid.setVisible(true);
    }
    
    private void fillComboBox() {
        EntityManager em = emf.createEntityManager();
        
        List<String> sitios = em.createQuery("SELECT s.nombre FROM sitio s").getResultList();
        List<String> clientes = em.createQuery("SELECT c.nombre FROM cliente").getResultList();
            
        if(ofertaPane.isVisible()) {
            sitioOferta.getItems().addAll(sitios);
            clienteOferta.getItems().addAll(clientes);
        } else if(clientePane.isVisible()) {
            sitioLlave.getItems().addAll(sitios);
            clienteLlave.getItems().addAll(clientes);
            subempresaLlave.getItems().addAll("MULTITEC S.A DE C.V.", "INTESAL S.A.DE C.V.", "SERPROFIN S.A.DE C.V", "DHL S.A. DE C.V.");
        }
        
        em.close();
    }
    
    @Override
    public void initDataLlave(LlavesTable rto, String table) {
        tableDisplay.setText(table);
        idSelected = rto.getId();
        
        clientePane.setVisible(true);
        
        llave ven = (llave) findInDB(llave.class, idSelected);
        
        clienteLlave.setValue(rto.getCliente());
        sitioLlave.setValue(rto.getSitio());
        subempresaLlave.setValue(rto.getSubempresa());
        cantidadLlave.setText(String.valueOf(rto.getCantidadLlaves()));
        cantidadLlave.setTextFormatter(new TextFormatter<>(filter));
        retiroLlave.setValue(rto.getFechaRetiro());
        devolucionLlave.setValue(rto.getFechaDevolucion());
        nombreRetiraLlave.setText(rto.getPersonaReceptor());
        encargadoLlave.setText(ven.getPersonaResponsable());
        telefonoLlave.setText(ven.getTelefono());
        telefonoLlave.setTextFormatter(new TextFormatter<>(filter));
        duiLlave.setText(ven.getDUI());
        
        pathToDUI = ven.getDocumentPath();
    }

    @Override
    public void initDataSitio(SitiosTable rto, String table) {
        tableDisplay.setText(table);
        idSelected = rto.getId();
        sitioPane.setVisible(true);
        
        float costoTotal = rto.getCostosAlcadia() + rto.getCostosArrendamiento();
        
        nombreSitio.setText(rto.getNombre());
        latitudSitio.setText(rto.getLatitud().toString());
        latitudSitio.setTextFormatter(new TextFormatter<>(filter));
        longitudSitio.setText(rto.getLongitud().toString());
        longitudSitio.setTextFormatter(new TextFormatter<>(filter));
        comentarioSitio.setText(rto.getComent());
        totalCostSitio.setText(costoTotal + "");
        costoAlcaldiaSitio.setText(rto.getCostosAlcadia().toString());
        costoAlcaldiaSitio.setTextFormatter(new TextFormatter<>(filter));
        costoArrendamientoSitio.setText(rto.getCostosArrendamiento().toString());
        costoArrendamientoSitio.setTextFormatter(new TextFormatter<>(filter));
        
        //addImageToPane(imageDisplaySitio, rto.getImagePath(), scrollSitio);
        addImageToPane(imageDisplaySitio, rto.getImagePath());
        imageFolder = new File(rto.getImagePath());
        
        pathToAlcaldia = rto.getDocumentoAlcaldia();
        pathToArrendamiento = rto.getDocumentoArrendamiento();
    }
    
    private void addImageToPane(JFXMasonryPane pane, String imagePath) {
        try {
            Stream<Path> paths = Files.walk(Paths.get(imagePath));
            paths.filter(Files::isRegularFile).forEach(image -> {
                Label imagePort = new Label();
                imagePort.setPrefSize(100, 100);
                imagePort.setStyle("-fx-background-image: url('" + image.toUri().toString() + "');"
                        + "-fx-background-size: 100%;"
                        + "-fx-background-repeat: no-reapeat;");
                
                imagePort.setCursor(Cursor.HAND);
                imagePort.setOnMouseClicked((event) -> {
                    CATUtil.openFileOnDesktop(image.toFile());
                });
                pane.getChildren().add(imagePort);
            });
        } catch (IOException ex) {
            Logger.getLogger(DetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    void openDUI(ActionEvent event) {
        CATUtil.openFileOnDesktop(new File(pathToDUI));
    }
    
    @FXML
    void openOferta(ActionEvent event) {
        CATUtil.openFileOnDesktop(new File(pathToOferta));
    }
    
    @FXML
    void openImageFolder(ActionEvent event) {
        CATUtil.openFileOnDesktop(imageFolder);
    }
    
    @FXML
    void saveChanges(ActionEvent event) {
        JFXButton ok = new JFXButton("Ok");
        if(ofertaPane.isVisible()){
            if(ofertaGrid.isVisible()){
                oferta ofer = (oferta) findInDB(oferta.class, idSelected);
                
                if(estadoComboBox.getValue().equals("Completado")){
                    if(!estado.equals(estadoComboBox.getSelectionModel().getSelectedItem())){
                        showConfirmDialog();
                        if(isChanged){
                            venta vent = new venta(fechaInicioVenta.getValue(), fechaFinVenta.getValue(), (sitio) findInDB(sitio.class, sitioOferta.getValue()), (cliente) findInDB(cliente.class, clienteOferta.getValue()));
                            vent.setOfertaVenta(ofer);
                            
                            persist(vent);
                            callback.refreshVentaData(new VentasTable(vent));
                            AlertFactory.showDialog(root, ofertaPane, Arrays.asList(ok), "Guardado Exitosamente", "Se han guardado los cambios exitosamente");
                        } else{
                            AlertFactory.showDialog(root, ofertaPane, Arrays.asList(ok), "Cancelado", "Se han cancelado los cambios que estaba efectuando");
                        }
                    } else{
                        ofer.getVentaO().setFechaInicio(fechaInicioVenta.getValue());
                        ofer.getVentaO().setFechaFin(fechaFinVenta.getValue());
                    }
                }
                
                //doesnt change the value of oferta state
                ofer.setClienteOf((cliente) findInDB(cliente.class, clienteOferta.getValue()));
                ofer.setLocacion((sitio) findInDB(sitio.class, sitioOferta.getValue()));
                ofer.setCanon(Float.parseFloat(canonActualVenta.getText()));
                ofer.setMonto(Float.parseFloat(montoOferta.getText()));
                ofer.setAlturaTorre(Float.parseFloat(alturaSolicOferta.getText()));
                ofer.getLocacion().getTorre().setAlturaPedida(Float.parseFloat(alturaDisOferta.getText()));
                ofer.setFecha(fechaOferta.getValue());
                
                persist(ofer);
                callback.refreshMainData(new MainOfferTable(ofer));
                AlertFactory.showDialog(root, sitioPane, Arrays.asList(ok), "Datos modificados", "Los datos fueron modificados exitosamente");
            } 
        } else if(clientePane.isVisible()){
            llave llave = (llave) findInDB(venta.class, idSelected);
            llave.setClienteY((cliente) findInDB(venta.class, clienteLlave.getValue()));
            llave.setSitioY((sitio) findInDB(sitio.class, sitioLlave.getValue()));
            llave.setCantidadLlaves(Integer.parseInt(cantidadLlave.getText()));
            llave.setSubempresa(subempresaLlave.getValue());
            llave.setFechaRetiro(retiroLlave.getValue());
            llave.setFechaDevolucion(devolucionLlave.getValue());
            llave.setNombreP(nombreRetiraLlave.getText());
            llave.setPersonaResponsable(encargadoLlave.getText());
            llave.setTelefono(telefonoLlave.getText());
            llave.setDUI(duiLlave.getText());
            llave.setDocumentPath(telefonoLlave.getText());
            
            persist(llave);
            callback.refreshLlaveData(new LlavesTable(llave));
            
            AlertFactory.showDialog(root, sitioPane, Arrays.asList(ok), "Datos modificados", "Los datos fueron modificados exitosamente");
        } else if(sitioPane.isVisible()){
            sitio sit = (sitio) findInDB(sitio.class, idSelected);
            sit.setNombre(nombreSitio.getText());
            sit.setLatitud(Float.parseFloat(latitudSitio.getText()));
            sit.setLongitud(Float.parseFloat(longitudSitio.getText()));
            sit.getArrendamiento().setCosto(Float.parseFloat(costoArrendamientoSitio.getText()));
            sit.getLicencia().setMonto(Float.parseFloat(costoAlcaldiaSitio.getText()));
            sit.setComent(comentarioSitio.getText());
            
            persist(sit);
            callback.refreshSitioData(new SitiosTable(sit));
            AlertFactory.showDialog(root, sitioPane, Arrays.asList(ok), "Datos modificados", "Los datos fueron modificados exitosamente");
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
    
    @FXML
    void changedDocument(ActionEvent event) {
        if(ofertaPane.isVisible()) {
            CATUtil.openFileOnDesktop(new File(pathToOferta));
        } /*else if(sitioPane.isVisible()) {
            if(event.getSource() == )
            CATUtil.openFileOnDesktop(new File(pathToAlcaldia));
            CATUtil.openFileOnDesktop(new File(pathToArrendamiento));
        }*/
    }
    
    @FXML
    void deleteThis(ActionEvent event) {
        JFXButton ok = new JFXButton("No");
        JFXButton cancel = new JFXButton("Si");
        
        ok.setOnAction(ae -> {
            if(ofertaPane.isVisible()) {
                oferta er = (oferta) findInDB(oferta.class, idSelected);
                delete(er);
            } else if(sitioPane.isVisible()) {
                sitio si = (sitio) findInDB(sitio.class, idSelected);
                delete(si);
            } else if(clientePane.isVisible()) {
                cliente cl = (cliente) findInDB(sitio.class, idSelected);
                delete(cl);
            }
            AlertFactory.showInfoMessage("Registro Eliminado", "El registro ha sido completamente eliminado");
            
            Stage actual = (Stage) root.getScene().getWindow();
            actual.close();
        });
        
        AlertFactory.showDialog(root, root, Arrays.asList(ok, cancel), "Eliminar Registro", "¿Esta seguro que quiere eliminar el registro? Esto puede afectar a otros datos que se relacoinen con el");
    }
    
    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            callback.refreshAllTableData();
        }
    }
    
    public Object findInDB(Class type, long id) {
        EntityManager em = emf.createEntityManager();
        Object found = em.find(type, id);
        return found;
    }
    
    public Object findInDB(Class type, String identifier) {
        EntityManager em = emf.createEntityManager();
        Object found = null;
        if(type == sitio.class) {
            found = em.createQuery("FROM sitio s WHERE s.nombre = '" + identifier + "'").getSingleResult();
        } else if(type == cliente.class) {
            found = em.createQuery("FROM cliente c WHERE c.nombre = '" + identifier + "'").getSingleResult();
        }
        return found;
    }
    
    public void setOnClose() {
        ofertaPane.getScene().getWindow().setOnCloseRequest(event -> {
            emf.close();
        });
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
