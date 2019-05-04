package com.interfazsv.cat.createRegister;

import Entitys.arrendamiento;
import Entitys.cliente;
import Entitys.licencia;
import Entitys.llave;
import Entitys.oferta;
import Entitys.sitio;
import Entitys.torre;
import TableData.LlavesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import callback.DataReturnCallback;
import com.interfazsv.cat.util.AlertFactory;
import com.interfazsv.cat.util.CATUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 *
 * @author hardel
 */
public class CreateRegisterController implements Initializable {

    /**
     * Comment Area on sitio
     */
    @FXML
    private StackPane root;
    
    @FXML
    private JFXTabPane tabPane;
    
    //Sitio Controllers
    @FXML
    private JFXTextField nombreSitio;

    @FXML
    private JFXTextField latitud;

    @FXML
    private JFXTextField longitud;

    @FXML
    private JFXTextField altura;

    @FXML
    private JFXTextField montoAlcaldia;
    
    @FXML
    private JFXTextField documentoAlcaldiaSitio;
    
    @FXML
    private JFXButton openAlcaldia;
    
    @FXML
    private JFXTextField montoArrendamiento;
    
    @FXML
    private JFXTextField documentoArrendamientoSitio;
    
    @FXML
    private JFXDatePicker fechaInicioArrendamiento;

    @FXML
    private JFXDatePicker fechaFinArrendamiento;
    
    @FXML
    private JFXMasonryPane imagesPaneSitio;
    
    @FXML
    private JFXButton selectImageSitio;
    
    @FXML
    private ScrollPane scrollSitio;
    
    @FXML
    private JFXButton openArrendamiento;
    
    //Oferta controllers
    @FXML
    private JFXComboBox<String> nombreSitioComboBoxOferta;

    @FXML
    private JFXComboBox<String> nombreClienteComboBoxOferta;
    
    @FXML
    private JFXTextField alturaSolicitadaOferta;

    @FXML
    private JFXTextField montoOferta;
    
    @FXML
    private JFXTextField canonAnualOferta;
    
    @FXML
    private JFXTextField documentoOferta;

    @FXML
    private JFXDatePicker fechaOferta;
    
    @FXML
    private JFXMasonryPane imagesPaneOferta;
    
    @FXML
    private JFXButton selectImageOferta;
    
    @FXML
    private ScrollPane scrollOferta;
    
    //Retiro de Llaves controllers
    @FXML
    private JFXComboBox<String> nombreClienteComboBoxLlave;

    @FXML
    private JFXComboBox<String> nombreSitioComboBoxLlave;
    
    @FXML
    private JFXComboBox<String> nombreSubordinadoComboBoxLlave;
    
    @FXML
    private JFXTextField cantidadLlaves;
    
    @FXML
    private JFXDatePicker fechaRetiroLlaves;

    @FXML
    private JFXDatePicker fechaDevolucionLlaves;

    @FXML
    private JFXTextField nombrePersonaRetira;

    @FXML
    private JFXTextField nombrePersonaEntrega;

    @FXML
    private JFXTextField telefonoLlave;

    @FXML
    private JFXTextField duiLlave;
    
    @FXML
    private JFXTextField documentoPersonalLlave;
    
    //atributtes
    private String selectedPane;
    private DataReturnCallback callback;
    private List<File> imagesOnSitioCache;
    private List<File> imagesOnOfertaCache;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
    
    final KeyCombination keyComb = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
    final KeyCombination keyCombCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
    
    private final String ICON_PATH = "/icons/delete.png";
    private final String IMAGES_PATH = System.getProperty("user.home") + "/Documents/CAT/Files/Imagenes/";
    private final String DOCUMENTS_PATH = System.getProperty("user.home") + "/Documents/CAT/Files/Documentos/";
    
    private final Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
    private final UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
        String text = t.getControlNewText();
        
        return (validEditingState.matcher(text).matches())?t:null;
    };
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaDevolucionLlaves.setValue(LocalDate.now());
        fechaRetiroLlaves.setValue(LocalDate.now());
        fechaOferta.setValue(LocalDate.now());
        fechaInicioArrendamiento.setValue(LocalDate.now());
        fechaFinArrendamiento.setValue(LocalDate.now());
        
        selectedPane = "Sitio";
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedPane = tabPane.getSelectionModel().getSelectedItem().getText();
                System.out.println(selectedPane);
            }
        });
        
        setTooltips();
        setDataForComboBox();
        setPanelsReady();
    }
    
    private void setDataForComboBox() {
        EntityManager em = emf.createEntityManager();
        
        List<sitio> sitios = em.createQuery("FROM sitio").getResultList();
        List clientes = em.createQuery("SELECT c.nombre FROM cliente c").getResultList();
        
        ObservableList<String> sitiosName = FXCollections.observableArrayList();
        
        sitios.forEach(element -> {
            if(element.getTorre().getClienteT().size() < 3) {
                sitiosName.add(element.getNombre());
            }
        });
        
        nombreSitioComboBoxOferta.setItems(sitiosName);
        nombreClienteComboBoxOferta.getItems().addAll(clientes);
        nombreSitioComboBoxLlave.setItems(sitiosName);
        nombreClienteComboBoxLlave.getItems().addAll(clientes);
        nombreSubordinadoComboBoxLlave.getItems().addAll("MULTITEC S.A DE C.V.", "INTESAL S.A.DE C.V.", "SERPROFIN S.A.DE C.V", "DHL S.A. DE C.V.");
 
        em.close();
    }

    private void setPanelsReady() {
        imagesPaneSitio.getChildren().addListener((ListChangeListener.Change<? extends Object> change) ->{
            if(imagesPaneSitio.getChildren().isEmpty()) {
                scrollSitio.setVisible(false);
                selectImageSitio.setVisible(true);
            }
        });
        
        imagesPaneOferta.getChildren().addListener((ListChangeListener.Change<? extends Object> change) ->{
            if(imagesPaneOferta.getChildren().isEmpty()) {
                scrollOferta.setVisible(false);
                selectImageOferta.setVisible(true);
            }
        });
    }
    
    public void setCallback(DataReturnCallback callback) {
        this.callback = callback;
    }
    
    public void prepareFromClose() {
        root.getScene().getWindow().setOnCloseRequest(event -> {
            emf.close();
        });
    }
    /*FOR SURE
    private void enablePasteFromClipboard(JFXTextField field){
        field.addEventFilter(KeyEvent.KEY_PRESSED, (Event event) -> {
            if(keyComb.match((KeyEvent) event)){
                Clipboard clip = Clipboard.getSystemClipboard();
                field.setText(clip.getString());
            } else if(keyCombCopy.match((KeyEvent) event)) {
                Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(field.getSelectedText());
                clip.setContent(content);
            }
        });
    }*/
    
    private void setTooltips() {
        final Tooltip tool = new Tooltip();
        tool.setText("Este campo solo admite carácteres numéricos");
        
        longitud.setTooltip(tool);
        latitud.setTooltip(tool);
        altura.setTooltip(tool);
        montoArrendamiento.setTooltip(tool);
        montoAlcaldia.setTooltip(tool);
        
        alturaSolicitadaOferta.setTooltip(tool);
        montoOferta.setTooltip(tool);
        canonAnualOferta.setTooltip(tool);
        
        telefonoLlave.setTooltip(tool);
        cantidadLlaves.setTooltip(tool);
        
        //apllying filters
        longitud.setTextFormatter(new TextFormatter<>(filter));
        latitud.setTextFormatter(new TextFormatter<>(filter));
        altura.setTextFormatter(new TextFormatter<>(filter));
        montoArrendamiento.setTextFormatter(new TextFormatter<>(filter));
        montoAlcaldia.setTextFormatter(new TextFormatter<>(filter));
        alturaSolicitadaOferta.setTextFormatter(new TextFormatter<>(filter));
        montoOferta.setTextFormatter(new TextFormatter<>(filter));
        telefonoLlave.setTextFormatter(new TextFormatter<>(filter));
        canonAnualOferta.setTextFormatter(new TextFormatter<>(filter));
        cantidadLlaves.setTextFormatter(new TextFormatter<>(filter));
        
        //enable clipping
        /*enablePasteFromClipboard(nombreSitio);
        enablePasteFromClipboard(latitud);
        enablePasteFromClipboard(longitud);
        enablePasteFromClipboard(altura);
        enablePasteFromClipboard(montoAlcaldia);
        enablePasteFromClipboard(montoArrendamiento);
        enablePasteFromClipboard(alturaSolicitadaOferta);
        enablePasteFromClipboard(nombrePersonaRetira);
        enablePasteFromClipboard(nombrePersonaEntrega);
        enablePasteFromClipboard(telefonoLlave);
        enablePasteFromClipboard(duiLlave);
        enablePasteFromClipboard(canonAnualOferta);
        enablePasteFromClipboard(cantidadLlaves);*/
    }

    @FXML
    private void agregarDocumento(ActionEvent event) {
        FileChooser choosa = new FileChooser();
        choosa.setTitle("Agregar Documento");
        choosa.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));
        File docLocation = choosa.showOpenDialog((Stage) tabPane.getScene().getWindow());
        
        if(docLocation != null) {
            switch(selectedPane) {
                case "Sitio":
                    if(event.getSource() == openArrendamiento) {
                        documentoArrendamientoSitio.setText(docLocation.getAbsolutePath());
                    } else if(event.getSource() == openAlcaldia) {
                        documentoAlcaldiaSitio.setText(docLocation.getAbsolutePath());
                    }
                    break;
                case "Oferta":
                    documentoOferta.setText(docLocation.getAbsolutePath());
                    break;
                case "Retiro de Llaves":
                    documentoPersonalLlave.setText(docLocation.getAbsolutePath());
                    break;
                default:
                    System.out.println("HACKER MAN!");
                    break;
            }
        }
    }
 
    @FXML
    private void agregarImagenes(ActionEvent event) {
        FileChooser choosa = new FileChooser();
        choosa.setTitle("Agregar Imagen");
        choosa.setInitialDirectory(new File(System.getProperty("user.home") + "/Pictures"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif");
        choosa.getExtensionFilters().addAll(extFilter);
        
        List<File> docLocation = choosa.showOpenMultipleDialog((Stage) tabPane.getScene().getWindow());
        
        if(selectedPane.equals("Sitio")) {
            addImageToPane(docLocation, imagesPaneSitio, scrollSitio,(JFXButton) event.getSource());
            imagesOnSitioCache = docLocation;
        } else if(selectedPane.equals("Oferta")) {
            addImageToPane(docLocation, imagesPaneOferta, scrollOferta,(JFXButton) event.getSource());
            imagesOnOfertaCache = docLocation;
        }
    }
    
    private void addImageToPane(List<File> images, JFXMasonryPane pane, ScrollPane scroll, JFXButton button){
        if(images != null){
            scroll.setVisible(true);
            button.setVisible(false);
            ImageView remov = new ImageView(new Image(ICON_PATH));
            
            images.forEach((imageSelected) -> {
                Label imagePort = new Label();
                imagePort.setPrefSize(160, 160);
                imagePort.setStyle("-fx-background-image: url('" + imageSelected.toURI().toString() + "');"
                        + "-fx-background-size: 100%;"
                        + "-fx-background-repeat: no-reapeat;");
                imagePort.setCursor(Cursor.HAND);
                imagePort.setOnMouseClicked((event) -> {
                    pane.getChildren().remove(imagePort);
                });
                //efects for remove
                imagePort.setOnMouseEntered((event) -> {
                    imagePort.setGraphic(remov);
                });
                imagePort.setOnMouseExited((event) -> {
                    imagePort.setGraphic(null);
                });
                pane.getChildren().add(imagePort);
            });
        }
    }
    
    @FXML
    private void resetFields(ActionEvent event) {
        clearAllFields();
    }
    
    private void clearAllFields(){
        switch (selectedPane) {
            case "Sitio":
                CATUtil.clearFields(Arrays.asList(nombreSitio, latitud, longitud, altura, montoAlcaldia, montoArrendamiento, documentoArrendamientoSitio, documentoAlcaldiaSitio));
                
                fechaInicioArrendamiento.setValue(LocalDate.now());
                fechaFinArrendamiento.setValue(LocalDate.now());
                scrollSitio.setVisible(false);
                imagesPaneSitio.getChildren().clear();
                selectImageSitio.setVisible(true);
                break;
            case "Oferta":
                CATUtil.clearFields(Arrays.asList(alturaSolicitadaOferta, montoOferta, documentoOferta, canonAnualOferta));
                fechaOferta.setValue(LocalDate.now());
                nombreSitioComboBoxOferta.getSelectionModel().clearSelection();
                nombreClienteComboBoxOferta.getSelectionModel().clearSelection();

                scrollOferta.setVisible(false);
                imagesPaneOferta.getChildren().clear();
                selectImageOferta.setVisible(true);
                break;
            case "Retiro de Llaves":
                CATUtil.clearFields(Arrays.asList(nombrePersonaRetira, nombrePersonaEntrega, telefonoLlave, duiLlave, documentoPersonalLlave, cantidadLlaves));
                nombreClienteComboBoxLlave.getSelectionModel().clearSelection();
                nombreSitioComboBoxLlave.getSelectionModel().clearSelection();
                nombreSubordinadoComboBoxLlave.getSelectionModel().clearSelection();
                fechaRetiroLlaves.setValue(LocalDate.now());
                fechaDevolucionLlaves.setValue(LocalDate.now());
                break;
            default:
                System.out.print("nigga this thing is very creizi");
                break;
        }
    }
    
    @FXML
    private void saveData(ActionEvent event) {
        JFXButton ok = new JFXButton("Ok");
        ok.setCursor(Cursor.HAND);
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        File saveLocationDocument = new File(DOCUMENTS_PATH + timeStamp);
        
        switch (selectedPane) {
            case "Sitio":
                if(validateFields(selectedPane)){
                    torre torreRegister = new torre(Float.valueOf(altura.getText()), null);
                    
                    arrendamiento arrendamientoRegister = new arrendamiento();
                    arrendamientoRegister.setCosto(Float.parseFloat(montoArrendamiento.getText()));
                    arrendamientoRegister.setDocumentPath(documentoArrendamientoSitio.getText());
                    arrendamientoRegister.setFechaInicioArrendamiento(fechaInicioArrendamiento.getValue());
                    arrendamientoRegister.setFechaFinArrendamiento(fechaFinArrendamiento.getValue());
                    
                    licencia licenciaRegister = new licencia();
                    licenciaRegister.setMonto(Float.valueOf(montoAlcaldia.getText()));
                    
                    moveDocuments(documentoArrendamientoSitio, arrendamientoRegister, saveLocationDocument);
                    moveDocuments(documentoAlcaldiaSitio, licenciaRegister, saveLocationDocument);
                    
                    sitio sitioRegister = new sitio(nombreSitio.getText(), Float.valueOf(latitud.getText()), Float.valueOf(longitud.getText()));
                    sitioRegister.setLicencia(licenciaRegister);
                    sitioRegister.setTorre(torreRegister);
                    sitioRegister.setArrendamiento(arrendamientoRegister);
                    
                    if(imagesOnSitioCache != null && !imagesOnSitioCache.isEmpty()) {
                        File saveLocationImage = new File(IMAGES_PATH + timeStamp);
                        if(!saveLocationImage.exists()){
                            try {
                                Files.createDirectories(saveLocationImage.toPath());
                            } catch (IOException ex) {
                                Logger.getLogger(CreateRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        imagesOnSitioCache.forEach(imageFile -> {
                            File finalFile = new File(saveLocationImage.getAbsolutePath() + "/" + imageFile.getName());
                            try {
                                Files.copy(imageFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException ex) {
                                Logger.getLogger(CreateRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        
                        sitioRegister.setImagePath(saveLocationImage.getAbsolutePath());
                    }
                    
                    persist(sitioRegister);
                    
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Guardado Exitosamente", "Se ha guardado el registro exitosamente!");
                    
                    callback.refreshSitioData(new SitiosTable(sitioRegister));
                    nombreSitioComboBoxOferta.getItems().add(sitioRegister.getNombre());
                    
                    CATUtil.clearFields(Arrays.asList(nombreSitio, latitud, longitud, altura, montoAlcaldia, montoArrendamiento, documentoArrendamientoSitio, documentoAlcaldiaSitio));
                    fechaInicioArrendamiento.setValue(LocalDate.now());
                    fechaFinArrendamiento.setValue(LocalDate.now());
                    scrollSitio.setVisible(false);
                    imagesPaneSitio.getChildren().clear();
                    selectImageSitio.setVisible(true);
                } else{
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Falta Información", "Para continuar es necesario que todos los campos sin un * sean llenados con la informacion debida");
                }
                break;
            case "Oferta":
                if(validateFields(selectedPane)) {
                    File saveLocationImage = new File(IMAGES_PATH + timeStamp);
                    if(!saveLocationImage.exists()){
                        try {
                            Files.createDirectories(saveLocationImage.toPath());
                        } catch (IOException ex) {
                            Logger.getLogger(CreateRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    imagesOnOfertaCache.forEach(imageFile -> {
                        File finalFile = new File(saveLocationImage.getAbsolutePath() + "/" + imageFile.getName());
                        try {
                            Files.copy(imageFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ex) {
                            Logger.getLogger(CreateRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                    moveDocuments(documentoOferta, null, saveLocationDocument);
                    
                    oferta ofertaRegister = new oferta(Float.parseFloat(alturaSolicitadaOferta.getText()), fechaOferta.getValue(), 
                                                       saveLocationImage.getAbsolutePath(), Float.parseFloat(montoOferta.getText()),
                                                       Float.parseFloat(canonAnualOferta.getText()), 'P', documentoOferta.getText(), 
                                                        (sitio) findElement(nombreSitioComboBoxOferta.getValue(), sitio.class), 
                                                        (cliente) findElement(nombreClienteComboBoxOferta.getValue(), cliente.class));
                    
                    persist(ofertaRegister);
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Guardado Exitosamente", "Se ha guardado el registro exitosamente!");
                    
                    callback.refreshMainData(new MainOfferTable(ofertaRegister));
                    
                    CATUtil.clearFields(Arrays.asList(alturaSolicitadaOferta, montoOferta));
                    fechaOferta.setValue(LocalDate.now());
                    nombreSitioComboBoxOferta.getSelectionModel().clearSelection();
                    nombreClienteComboBoxOferta.getSelectionModel().clearSelection();
                    
                    scrollOferta.setVisible(false);
                    imagesPaneOferta.getChildren().clear();
                    selectImageOferta.setVisible(true);
                } else{
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Falta Información", "Para continuar es necesario que todos los campos sin un * sean llenados con la informacion debida");
                }
                break;
            case "Retiro de Llaves":
                if(validateFields(selectedPane)) {
                    llave llaveRegister = new llave(nombrePersonaRetira.getText(), nombrePersonaEntrega.getText(), telefonoLlave.getText(), 
                                                    nombreSubordinadoComboBoxLlave.getValue(), Integer.parseInt(cantidadLlaves.getText()), duiLlave.getText(), fechaRetiroLlaves.getValue(), 
                                                    fechaDevolucionLlaves.getValue(), (cliente) findElement(nombreClienteComboBoxLlave.getValue(), cliente.class),
                                                    (sitio) findElement(nombreSitioComboBoxLlave.getValue(), sitio.class));
                    
                    moveDocuments(documentoPersonalLlave, llaveRegister, saveLocationDocument);
                    
                    persist(llaveRegister);
                    
                    callback.refreshLlaveData(new LlavesTable(llaveRegister));
                    
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Guardado Exitosamente", "Se ha guardado el registro exitosamente!");
                    
                    CATUtil.clearFields(Arrays.asList(nombrePersonaRetira, nombrePersonaEntrega, telefonoLlave, duiLlave, documentoPersonalLlave, cantidadLlaves));
                    nombreClienteComboBoxLlave.getSelectionModel().clearSelection();
                    nombreSitioComboBoxLlave.getSelectionModel().clearSelection();
                    nombreSubordinadoComboBoxLlave.getSelectionModel().clearSelection();
                    fechaRetiroLlaves.setValue(LocalDate.now());
                    fechaDevolucionLlaves.setValue(LocalDate.now());
                } else{
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Falta Información", "Para continuar es necesario que todos los campos sin un * sean llenados con la informacion debida");
                }
                break;
            default:
                System.out.print("nigga this thing is very creizi");
                break;
        }
    }
    
    private Object findElement(String element, Class type) {
        EntityManager em = emf.createEntityManager();
        Object found = null;
        if(type == sitio.class) {
            found = (sitio) em.createQuery("FROM sitio s WHERE s.nombre = '" + element + "'").getSingleResult();
        } else if(type == cliente.class) {
            found = (cliente) em.createQuery("FROM cliente c WHERE c.nombre = '" + element + "'").getSingleResult();
        }
        em.close();
        
        return found;
    }

    private void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("This thing doesn't work man");
        } finally {
            em.close();
        }
    }

    private boolean validateFields(String panel) {
        boolean isReady = false;
        switch(panel){
            case "Sitio":
                isReady = (!nombreSitio.getText().isEmpty() && 
                            !latitud.getText().isEmpty() && 
                            !longitud.getText().isEmpty() && 
                            !altura.getText().isEmpty() && 
                            !montoAlcaldia.getText().isEmpty() &&
                            !montoArrendamiento.getText().isEmpty() &&
                            fechaInicioArrendamiento.getValue() != null &&
                            fechaFinArrendamiento.getValue() != null);
                break;
            case "Oferta":
                isReady = (nombreSitioComboBoxOferta.getValue() != null &&
                            nombreClienteComboBoxOferta.getValue() != null &&
                            !alturaSolicitadaOferta.getText().isEmpty() &&
                            !montoOferta.getText().isEmpty() &&
                            fechaOferta.getValue() != null && 
                            imagesOnOfertaCache != null && 
                            !imagesOnOfertaCache.isEmpty() &&
                            !canonAnualOferta.getText().isEmpty());
                break;
            case "Retiro de Llaves":
                isReady = (nombreClienteComboBoxLlave.getValue() != null && 
                            nombreSitioComboBoxLlave.getValue() != null &&
                            nombreSubordinadoComboBoxLlave.getValue() != null &&
                            !nombrePersonaRetira.getText().isEmpty() && 
                            !nombrePersonaEntrega.getText().isEmpty() &&
                            !telefonoLlave.getText().isEmpty() && 
                            !duiLlave.getText().isEmpty() && 
                            fechaRetiroLlaves.getValue() != null && 
                            fechaDevolucionLlaves.getValue() != null &&
                            !cantidadLlaves.getText().isEmpty());
                break;
            default:
                System.out.print("nigga this thing is very creizi");
                break;
        }
        
        return isReady;
    }
    
    private void moveDocuments(JFXTextField field, Object reg, File saveLocationDocument) {
        if(field.getText() != null && !field.getText().isEmpty()) {
            if(!saveLocationDocument.exists()){
                try {
                    Files.createDirectories(saveLocationDocument.toPath());
                } catch (IOException ex) {
                    Logger.getLogger(CreateRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            File originalFile = new File(field.getText());
            File finalFile = new File(saveLocationDocument.getAbsolutePath() + "/" + originalFile.getName());
            System.out.println(finalFile.getAbsolutePath());
            try {
                Files.copy(originalFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(CreateRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(reg instanceof licencia) {
                ((licencia) reg).setDocumentPath(finalFile.getAbsolutePath());
            } else if(reg instanceof arrendamiento) {
                ((arrendamiento) reg).setDocumentPath(finalFile.getAbsolutePath());
            } else if(reg instanceof llave) {
                ((llave) reg).setDocumentPath(finalFile.getAbsolutePath());
            }
        }
    }
}
