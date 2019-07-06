package com.interfazsv.cat.detail;

import Entitys.cliente;
import Entitys.llave;
import Entitys.oferta;
import Entitys.sitio;
import Entitys.subempresa;
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
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
    *Eliminar Registros
    *cambio a cese de oferta(cambiar fecha fin?)
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
    private JFXTextField costoLicenciaSitio;

    @FXML
    private JFXToggleButton pagoAnualSitio;

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
    private JFXTextField canonInicialVenta;
    
    @FXML
    private JFXTextField canonActualVenta;
    
    private String pathToAlcaldia, pathToArrendamiento, pathToOferta, pathToDUI;
    private File imageFolder;
    
    final KeyCombination keyComb = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
    final KeyCombination keyCombCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
    
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
        estadoComboBox.getItems().addAll("Completado", "Incompleto", "Pendiente", "Cese");
        
        estadoComboBox.setValue(rto.getEstado());
        estado = rto.getEstado();
        
        fillComboBox();
        sitioOferta.setValue(rto.getSitio());
        clienteOferta.setValue(rto.getCliente());
        montoOferta.setText(rto.getMonto().toString());
        montoOferta.setTextFormatter(new TextFormatter<>(filter));
        alturaDisOferta.setText(rto.getAlturaDis().toString());
        alturaDisOferta.setTextFormatter(new TextFormatter<>(filter));
        alturaSolicOferta.setText(rto.getAltura().toString());
        alturaSolicOferta.setTextFormatter(new TextFormatter<>(filter));
        canonInicialVenta.setText(rto.getCanon().toString());
        canonInicialVenta.setTextFormatter(new TextFormatter<>(filter));
        
        //addImageToPane(imageDisplayOferta, rto.getImagePath(), scrollOferta);
        addImageToPane(imageDisplayOferta, rto.getImagePath());
        imageFolder = new File(rto.getImagePath());
        pathToOferta = rto.getDocumentPath();
        fechaOferta.setValue(LocalDate.parse(rto.getFecha(), DateTimeFormatter.ofPattern("uuuu/MM/d")));
        
        if(estado.equals("Completado") || estado.equals("Cese")) {
            ventaGrid.setVisible(true);
            ofertaGrid.setVisible(false);
            
            oferta o = (oferta) findInDB(oferta.class, rto.getIdOferta());
            fechaInicioVenta.setValue(o.getVentaO().getFechaInicio().plusDays(1L));
            fechaFinVenta.setValue(o.getVentaO().getFechaFin().plusDays(1L));
            canonActualVenta.setText(String.valueOf(calcActualCanon(rto.getCanon(), rto.getMonto(), Period.between(o.getVentaO().getFechaInicio(), LocalDate.now()).getYears(), o.getVentaO())));
        } else{
            ventaGrid.setVisible(false);
            ofertaGrid.setVisible(true);
        }
        
        CATUtil.enablePasteFromClipboard(montoOferta);
        CATUtil.enablePasteFromClipboard(alturaDisOferta);
        CATUtil.enablePasteFromClipboard(alturaSolicOferta);
        CATUtil.enablePasteFromClipboard(canonInicialVenta);
    }
    
    private void fillComboBox() {
        EntityManager em = emf.createEntityManager();
        
        List<String> sitios = em.createQuery("SELECT s.nombre FROM sitio s").getResultList();
        List<String> clientes = em.createQuery("SELECT c.nombre FROM cliente c").getResultList();
        List<String> subempresas = em.createQuery(("SELECT s.nombre FROM subempresa s")).getResultList();
            
        if(ofertaPane.isVisible()) {
            sitioOferta.getItems().addAll(sitios);
            clienteOferta.getItems().addAll(clientes);
        } else if(clientePane.isVisible()) {
            sitioLlave.getItems().addAll(sitios);
            clienteLlave.getItems().addAll(clientes);
            subempresaLlave.getItems().addAll(subempresas);
        }
        
        em.close();
    }
    
    @Override
    public void initDataLlave(LlavesTable rto, String table) {
        tableDisplay.setText(table);
        idSelected = rto.getId();
        
        clientePane.setVisible(true);
        fillComboBox();
        
        llave ven = (llave) findInDB(llave.class, idSelected);
        
        clienteLlave.setValue(rto.getCliente());
        sitioLlave.setValue(rto.getSitio());
        subempresaLlave.setValue(rto.getSubempresa());
        cantidadLlave.setText(String.valueOf(rto.getCantidadLlaves()));
        cantidadLlave.setTextFormatter(new TextFormatter<>(filter));
        retiroLlave.setValue(rto.getFechaRetiro());
        devolucionLlave.setValue(LocalDate.now());
        nombreRetiraLlave.setText(rto.getPersonaReceptor());
        encargadoLlave.setText(ven.getPersonaResponsable());
        telefonoLlave.setText(ven.getTelefono());
        telefonoLlave.setTextFormatter(new TextFormatter<>(filter));
        duiLlave.setText(ven.getDUI());
        
        if(ven.getFechaDevolucion() != null) {
            devolucionLlave.setValue(ven.getFechaDevolucion().plusDays(1L));
        }
        pathToDUI = ven.getDocumentPath();
        
        CATUtil.enablePasteFromClipboard(cantidadLlave);
        CATUtil.enablePasteFromClipboard(nombreRetiraLlave);
        CATUtil.enablePasteFromClipboard(encargadoLlave);
        CATUtil.enablePasteFromClipboard(telefonoLlave);
        CATUtil.enablePasteFromClipboard(duiLlave);
    }

    @Override
    public void initDataSitio(SitiosTable rto, String table) {
        tableDisplay.setText(table);
        idSelected = rto.getId();
        sitioPane.setVisible(true);
        
        float costoTotal = rto.getCostosAlcadia() + rto.getCostosArrendamiento() + rto.getCostosLicencia();
        
        nombreSitio.setText(rto.getNombre());
        latitudSitio.setText(rto.getLatitud().toString());
        latitudSitio.setTextFormatter(new TextFormatter<>(filter));
        longitudSitio.setText(rto.getLongitud().toString());
        longitudSitio.setTextFormatter(new TextFormatter<>(filter));
        comentarioSitio.setText(rto.getComent());
        totalCostSitio.setText(costoTotal + "");
        costoAlcaldiaSitio.setText(rto.getCostosAlcadia().toString());
        costoAlcaldiaSitio.setTextFormatter(new TextFormatter<>(filter));
        costoLicenciaSitio.setText(rto.getCostosLicencia().toString());
        costoLicenciaSitio.setTextFormatter(new TextFormatter<>(filter));
        costoArrendamientoSitio.setText(rto.getCostosArrendamiento().toString());
        costoArrendamientoSitio.setTextFormatter(new TextFormatter<>(filter));
        pagoAnualSitio.setSelected(rto.isAnual());
        
        if(rto.getImagePath() != null) {
            addImageToPane(imageDisplaySitio, rto.getImagePath());
            imageFolder = new File(rto.getImagePath());
        }
        if(rto.getDocumentoAlcaldia() != null) {
            pathToAlcaldia = rto.getDocumentoAlcaldia();
        }
        if(rto.getDocumentoArrendamiento() != null) {
            pathToArrendamiento = rto.getDocumentoArrendamiento();
        }
        
        CATUtil.enablePasteFromClipboard(nombreSitio);
        CATUtil.enablePasteFromClipboard(latitudSitio);
        CATUtil.enablePasteFromClipboard(longitudSitio);
        CATUtil.enablePasteFromClipboard(comentarioSitio);
        CATUtil.enablePasteFromClipboard(costoAlcaldiaSitio);
        CATUtil.enablePasteFromClipboard(costoLicenciaSitio);
        CATUtil.enablePasteFromClipboard(costoArrendamientoSitio);
    }
    
    private void addImageToPane(JFXMasonryPane pane, String imagePath) {
        try {
            Stream<Path> paths = Files.walk(Paths.get(imagePath));
            paths.filter(Files::isRegularFile).forEach(image -> {
                Label imagePort = new Label();
                imagePort.setPrefSize(100, 100);
                imagePort.setStyle("-fx-background-image: url('" + image.toUri().toString() + "');"
                        + "-fx-background-size: 100%;"
                        + "-fx-background-repeat: no-repeat;");
                
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
            oferta ofer = (oferta) findInDB(oferta.class, idSelected);
            venta vent = null;
            
            if(ofertaGrid.isVisible()){
                if(estadoComboBox.getValue().equals("Completado")){
                    if(!estado.equals(estadoComboBox.getSelectionModel().getSelectedItem())){
                        showConfirmDialog();
                        if(isChanged){
                            vent = new venta(fechaInicioVenta.getValue(), fechaFinVenta.getValue(), (sitio) findInDB(sitio.class, sitioOferta.getValue()), (cliente) findInDB(cliente.class, clienteOferta.getValue()), ofer);
                            
                            //persist(vent);
                            ofer.setEstado('C');
                            callback.refreshVentaData(new VentasTable(vent));
                            //AlertFactory.showDialog(root, ofertaPane, Arrays.asList(ok), "Guardado Exitosamente", "Se han guardado los cambios exitosamente");
                        } else{
                            AlertFactory.showDialog(root, ofertaPane, Arrays.asList(ok), "Cancelado", "Se han cancelado los cambios que estaba efectuando");
                        }
                    } else{
                        ofer.getVentaO().setFechaInicio(fechaInicioVenta.getValue());
                        ofer.getVentaO().setFechaFin(fechaFinVenta.getValue());
                    }
                }
            }
            
            switch(estadoComboBox.getValue()) {
                case "Incompleto":
                    ofer.setEstado('I');
                    break;
                case "Pendiente":
                    ofer.setEstado('P');
                    break;
                case "Cese":
                    ofer.setEstado('S');
                    break;
                default:
                    break;
            }
            
            if(vent != null) {
                ofer.setVentaO(vent);
                ofer.setFecha(ofer.getFecha());
            } else{
                ofer.setFecha(fechaOferta.getValue());
            }
            ofer.setClienteOf((cliente) findInDB(cliente.class, clienteOferta.getValue()));
            ofer.setLocacion((sitio) findInDB(sitio.class, sitioOferta.getValue()));
            ofer.setCanon(Float.parseFloat(canonInicialVenta.getText()));
            ofer.setMonto(Float.parseFloat(montoOferta.getText()));
            ofer.setAlturaTorre(Float.parseFloat(alturaSolicOferta.getText()));
            ofer.getLocacion().getTorre().setAlturaPedida(Float.parseFloat(alturaDisOferta.getText()));

            persist(ofer);
            callback.refreshMainData(new MainOfferTable(ofer));
            AlertFactory.showDialog(root, sitioPane, Arrays.asList(ok), "Datos modificados", "Los datos fueron modificados exitosamente");
        } else if(clientePane.isVisible()){
            llave lave = (llave) findInDB(llave.class, idSelected);
            lave.setCantidadLlaves(Integer.parseInt(cantidadLlave.getText()));
            lave.setSubempresa((subempresa) findInDB(subempresa.class ,subempresaLlave.getValue()));
            lave.setFechaRetiro(retiroLlave.getValue());
            lave.setFechaDevolucion(devolucionLlave.getValue());
            lave.setNombreP(nombreRetiraLlave.getText());
            lave.setPersonaResponsable(encargadoLlave.getText());
            lave.setTelefono(telefonoLlave.getText());
            lave.setDUI(duiLlave.getText());
            lave.setDocumentPath(telefonoLlave.getText());
            lave.setSitioY((sitio) findInDB(sitio.class, sitioLlave.getValue()));
            lave.setClienteY((cliente) findInDB(cliente.class, clienteLlave.getValue()));
            
            persist(lave);
            callback.refreshLlaveData(new LlavesTable(lave));
            
            AlertFactory.showDialog(root, sitioPane, Arrays.asList(ok), "Datos modificados", "Los datos fueron modificados exitosamente");
        } else if(sitioPane.isVisible()){
            sitio sit = (sitio) findInDB(sitio.class, idSelected);
            sit.setNombre(nombreSitio.getText());
            sit.setLatitud(Float.parseFloat(latitudSitio.getText()));
            sit.setLongitud(Float.parseFloat(longitudSitio.getText()));
            sit.getArrendamiento().setCosto(Float.parseFloat(costoArrendamientoSitio.getText()));
            sit.getLicencia().setMonto(Float.parseFloat(costoAlcaldiaSitio.getText()));
            sit.getLicencia().setCostoLicencia(Float.parseFloat(costoLicenciaSitio.getText()));
            sit.setComent(comentarioSitio.getText());
            sit.getLicencia().setAnual(pagoAnualSitio.isSelected());
            
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
        Label generalEnd = new Label("Duración del Contrato :");
        Label yearsEnd = new Label("Años :");
        JFXTextField years = new JFXTextField();
        Label monthsEnd = new Label("Meses :");
        JFXTextField months = new JFXTextField();
        
        //JFXDatePicker fechaEnd = new JFXDatePicker(LocalDate.now());
        
        dialogPane.setContent(new VBox(30, init, fechaInit, generalEnd, yearsEnd, years, monthsEnd, months));
        Platform.runLater(fechaInit::requestFocus);
        
        EventHandler<ActionEvent> filter = event -> {
            /*if(fechaInit.getValue().isAfter(fechaEnd.getValue())) {
                AlertFactory.showInfoMessage("Error al ingresar las fechas", "Revise que la fecha de inicio sea antes que la fecha final");
		event.consume();
            }*/
            if(Integer.parseInt(years.getText()) < 0) {
                AlertFactory.showInfoMessage("Error al ingresar las fechas", "Revise que la fecha de inicio sea antes que la fecha final");
		event.consume();
            }
        };
        
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                LocalDate endingDate = fechaInit.getValue().plusYears(Integer.parseInt(years.getText())).plusMonths(Integer.parseInt(months.getText()));
                
                isChanged = true;
                fechaInicioVenta.setValue(fechaInit.getValue());
                fechaFinVenta.setValue(endingDate.minusDays(1l));

                return new DateSetters(fechaInit.getValue(), endingDate);
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
    private void deleteThis(ActionEvent event) {
        JFXButton ok = new JFXButton("Si");
        JFXButton cancel = new JFXButton("No");
        
        //dis isn't happen >:(
        ok.setOnAction(ae -> {
            if(ofertaPane.isVisible()) {
                oferta er = (oferta) findInDB(oferta.class, idSelected);
                delete(er);
            } else if(sitioPane.isVisible()) {
                sitio si = (sitio) findInDB(sitio.class, idSelected);
                delete(si);
            } else if(clientePane.isVisible()) {
                llave ll = (llave) findInDB(llave.class, idSelected);
                delete(ll);
            }
            
            AlertFactory.showInfoMessage("Registro Eliminado", "El registro ha sido completamente eliminado");
            
            emf.close();
            Stage actual = (Stage) root.getScene().getWindow();
            actual.close();
        });
        
        AlertFactory.showDialog(root, ofertaPane, Arrays.asList(ok, cancel), "Eliminar Registro", "¿Esta seguro que quiere eliminar el registro? Esto puede afectar a otros datos que se relacoinen con el");
    }
    
    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(object);
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
            if(object != null) {
                em.remove(em.contains(object) ? object: em.merge(object));
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            callback.refreshAllTableData();
        }
    }
    
    private Object findInDB(Class type, long id) {
        EntityManager em = emf.createEntityManager();
        Object found = em.find(type, id);
        em.close();
        return found;
    }
    
    private Object findInDB(Class type, String identifier) {
        EntityManager em = emf.createEntityManager();
        Object found = null;
        try{
            if(type == sitio.class) {
                found = (sitio) em.createQuery("FROM sitio s WHERE s.nombre = '" + identifier + "'").getSingleResult();
            } else if(type == cliente.class) {
                found = (cliente) em.createQuery("FROM cliente c WHERE c.nombre = '" + identifier + "'").getSingleResult();
            } else if(type == subempresa.class) {
                found = em.createQuery("FROM subempresa s WHERE s.nombre = '" + identifier + "'").getSingleResult();
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("donnou men :(((");
        }
        em.close();
        
        System.out.println("OBJECT FOUNDED = " + found.toString());
        return found;
    }
    
    public void setOnClose() {
        ofertaPane.getScene().getWindow().setOnCloseRequest(event -> {
            emf.close();
        });
    }
    
    private float calcActualCanon(float canon, float monto, int yearDifference, venta ven) {
        final DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.CEILING);
        
        if(yearDifference > 0) {
            for(int i = 0; i < yearDifference - 1; i++) {
                monto += (monto * (canon/100));
            }
        }
        
        if(ven != null) {
            if(ven.getFechaFin().plusDays(1L).getYear() == LocalDate.now().getYear() && ven.getFechaFin().plusDays(1L).getMonth() == LocalDate.now().getMonth()) {
                monto = (monto / LocalDate.now().lengthOfMonth()) * ven.getFechaFin().plusDays(1L).getDayOfMonth();
            } else if(ven.getFechaInicio().plusDays(1L).getYear() == LocalDate.now().getYear() && ven.getFechaInicio().plusDays(1L).getMonth() == LocalDate.now().getMonth()) {
                monto = (monto / LocalDate.now().lengthOfMonth()) * (LocalDate.now().lengthOfMonth() - ven.getFechaInicio().plusDays(1L).getDayOfMonth());
            }
        }
        
        return Float.valueOf(df.format(monto));
    }
    
    private class DateSetters{
        LocalDate inicio, finaL;

        public DateSetters(LocalDate inicio, LocalDate finaL) {
            this.inicio = inicio;
            this.finaL = finaL;
        }
    }
}
