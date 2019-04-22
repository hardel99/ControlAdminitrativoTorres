package com.interfazsv.cat.createRegister;

import Entitys.arrendamiento;
import Entitys.licencia;
import Entitys.sitio;
import Entitys.torre;
import com.interfazsv.cat.util.AlertFactory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
     * Make the sitio and cliente a combo box on ofertasPane
     * dialog for moving the respective documents to an special folder
     * Callbacks TIME
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
    private JFXMasonryPane imagesPaneSitio;
    
    @FXML
    private ScrollPane scrollSitio;
    
    @FXML
    private JFXButton openArrendamiento;
    
    //Oferta controllers
    @FXML
    private JFXTextField alturaSolicitadaOferta;

    @FXML
    private JFXTextField montoOferta;
    
    @FXML
    private JFXTextField documentoOferta;

    @FXML
    private JFXDatePicker fechaOferta;
    
    @FXML
    private JFXMasonryPane imagesPaneOferta;
    
    @FXML
    private ScrollPane scrollOferta;

    //Cliente controllers
    @FXML
    private JFXTextField nombreEmpresaCLiente;

    @FXML
    private JFXTextField canonCliente;

    @FXML
    private JFXTextField telefonoCliente;

    @FXML
    private JFXTextField correoCliente;
    
    //Retiro de Llaves controllers
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
    
    //atributtes
    private String selectedPane;
    
    private final String ICON_PATH = "/icons/delete.png";
    private final String IMAGES_PATH = System.getProperty("user.home") + "/Documents/CAT/Files/Imagenes";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaDevolucionLlaves.setValue(LocalDate.now());
        fechaRetiroLlaves.setValue(LocalDate.now());
        fechaOferta.setValue(LocalDate.now());
        
        selectedPane = "Sitio";
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedPane = tabPane.getSelectionModel().getSelectedItem().getText();
                System.out.println(selectedPane);
            }
        });
        
        setTooltips();
        
        latitud.addEventFilter(KeyEvent.KEY_PRESSED, (Event event) -> {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
            
            if(keyComb.match((KeyEvent) event)){
                Clipboard clip = Clipboard.getSystemClipboard();
                latitud.setText(clip.getString());
            } else if(keyCombCopy.match((KeyEvent) event)){
                Clipboard clip = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(latitud.getText());
                clip.setContent(content);
            }
        });
        
        longitud.addEventFilter(KeyEvent.KEY_PRESSED, (Event event) -> {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
            if(keyComb.match((KeyEvent) event)){
                Clipboard clip = Clipboard.getSystemClipboard();
                longitud.setText(clip.getString());
            }
        });
        
        /*Window w = root.getScene().getWindow();
        w.setOnCloseRequest(e -> {
            emf.close();
        });*/
    }
    
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
        
        canonCliente.setTooltip(tool);
        telefonoCliente.setTooltip(tool);
        
        telefonoLlave.setTooltip(tool);
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
                default:
                    System.out.println("HACKER MAN!");
                    break;
            }
        } else{
            AlertFactory.showInfoMessage("Problema al Abrir el Archivo", "Hay un error al tratar de abrir el archivo, asegurate de seleccionar el correcto");
        }
    }
 
    @FXML
    private void agregarImagenes(ActionEvent event) {
        FileChooser choosa = new FileChooser();
        choosa.setTitle("Agregar Documento");
        choosa.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif");
        choosa.getExtensionFilters().addAll(extFilter);
        
        List<File> docLocation = choosa.showOpenMultipleDialog((Stage) tabPane.getScene().getWindow());
        
        if(selectedPane.equals("Sitio")) {
            addImageToPane(docLocation, imagesPaneSitio, scrollSitio,(JFXButton) event.getSource());
        } else if(selectedPane.equals("Oferta")) {
            addImageToPane(docLocation, imagesPaneOferta, scrollOferta,(JFXButton) event.getSource());
        }
    }
    
    private void addImageToPane(List<File> images, JFXMasonryPane pane, ScrollPane scroll, JFXButton button){
        if(images != null){
            scroll.setVisible(true);
            button.setVisible(false);
            ImageView remov = new ImageView(new Image(ICON_PATH));
            
            images.forEach((imageSelected) -> {
                Label imagePort = new Label();
                imagePort.setPrefSize(150, 150);
                imagePort.setStyle("-fx-background-image: url('" + imageSelected.toURI().toString() + "');"
                        + "-fx-background-size: 100%;");
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
        } else{
            AlertFactory.showInfoMessage("Problema al Abrir el Archivo", "Hay un error al tratar de abrir el archivo, asegurate de seleccionar el correcto");
        }
    }
    
    @FXML
    void validateNumber(KeyEvent event) {
        if(event.getCode().isLetterKey()){
            event.consume();
        }
    }

    @FXML
    private void saveData(ActionEvent event) {
        //this thing doesnt save!
        JFXButton ok = new JFXButton("Ok");
        switch (selectedPane) {
            case "Sitio":
                if(validateFields("Sitio")){
                    torre torreRegister = new torre(Float.valueOf(altura.getText()), null);
                    
                    arrendamiento arrendamientoRegister = new arrendamiento();
                    arrendamientoRegister.setCosto(Float.parseFloat(montoArrendamiento.getText()));
                    arrendamientoRegister.setDocumentPath(documentoArrendamientoSitio.getText());
                    
                    licencia licenciaRegister = new licencia(Float.valueOf(montoAlcaldia.getText()), documentoAlcaldiaSitio.getText());
                    
                    sitio sitioRegister = new sitio(nombreSitio.getText(), Float.valueOf(latitud.getText()), Float.valueOf(longitud.getText()));
                    sitioRegister.setLicencia(licenciaRegister);
                    sitioRegister.setTorre(torreRegister);
                    sitioRegister.setArrendamiento(arrendamientoRegister);
                    
                    System.out.print(sitioRegister.toString());
                    
                    persist(sitioRegister);
                    
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Guardado Exitosamente", "Se ha guardado el registro exitosamente!");
                } else{
                    AlertFactory.showDialog(root, tabPane, Arrays.asList(ok), "Falta Información", "Para continuar es necesario que todos los campos sin un * sean llenados con la informacion debida");
                }
                break;
            case "Oferta":
                System.out.println("Guardar en Oferta");
                break;
            case "Cliente":
                System.out.println("Guardar en Cliente");
                break;
            case "Retiro de Llaves":
                System.out.println("Guardar en Retiro de Llaves");
                break;
            default:
                System.out.print("nigga this thing is very creizi");
                break;
        }
    }

    private void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
            System.out.println("I mean, this say that commit the persistance thing and all u know");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("This thing doesn't work man");
        } finally {
            em.close();
            emf.close();
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
                            !montoArrendamiento.getText().isEmpty());
                
                break;
            case "Oferta":
                System.out.println("Guardar en Oferta");
                break;
            case "Cliente":
                System.out.println("Guardar en Cliente");
                break;
            case "Retiro de Llaves":
                System.out.println("Guardar en Retiro de Llaves");
                break;
            default:
                System.out.print("nigga this thing is very creizi");
                break;
        }
        
        return isReady;
    }
}
