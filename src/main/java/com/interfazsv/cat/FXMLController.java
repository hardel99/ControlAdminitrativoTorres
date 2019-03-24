package com.interfazsv.cat;

import Entitys.oferta;
import Entitys.sitio;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FXMLController implements Initializable {
    
    @FXML
    private JFXButton btnDetalles;
    
    @FXML
    private HBox offerBox;

    @FXML
    private HBox sitiosBox;

    @FXML
    private TableView<MainOfferTable> mainTable;
    
    @FXML
    private TableView<SitiosTable> sitioTable;
    
    @FXML
    private TableColumn<MainOfferTable, String> sitioCol;

    @FXML
    private TableColumn<MainOfferTable, String> clienteCol;

    @FXML
    private TableColumn<MainOfferTable, Float> alturaCol;

    @FXML
    private TableColumn<MainOfferTable, String> fechaCol;

    @FXML
    private TableColumn<MainOfferTable, String> alturaDisCol;
    
    @FXML
    private TableColumn<SitiosTable, String> nombreSitCol;

    @FXML
    private TableColumn<SitiosTable, Float> alturaDisSitioCol;
    
    @FXML
    private TableColumn<SitiosTable, Float> latitudSitioCol;

    @FXML
    private TableColumn<SitiosTable, Float> longitudSitioCol;

    @FXML
    private TableColumn<SitiosTable, String> dispSitioCol;

    @FXML
    private TableColumn<SitiosTable, Float> costosSitioCol;

    @FXML
    private TableColumn<SitiosTable, Float> costosArrendSitioCol;
    
    @FXML
    private JFXTextField searchBar;
    
    @FXML
    private JFXButton cancel;
    
    @FXML
    private Label btnClose;
    
    private final ObservableList<MainOfferTable> data = FXCollections.observableArrayList();
    private FilteredList<MainOfferTable> filtrada;
    
    private final ObservableList<SitiosTable> dataSit = FXCollections.observableArrayList();
    private FilteredList<SitiosTable> filtradaSit;
    
    private long selected;
    
    private Predicate<MainOfferTable> predicate1, predicate2, predicate3;
    private Predicate<SitiosTable> predicate4, predicate5, predicate6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillColumns();
        getTheOfferData();
        quickConfigs();
        setPredicados();
        filtrada = new FilteredList(data);
        
        SortedList<MainOfferTable> sortedData = new SortedList<>(filtrada);
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());
        
        mainTable.setItems(sortedData);
        
        filtradaSit = new FilteredList(dataSit);
        SortedList<SitiosTable> sortedSits = new SortedList(filtradaSit);
        sortedSits.comparatorProperty().bind(sitioTable.comparatorProperty());
        
        sitioTable.setItems(sortedSits);
    }
    
    private void quickConfigs(){
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        mainTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(mainTable.getSelectionModel().getSelectedItem() != null){
                    btnDetalles.setDisable(false);
                    
                    selected = mainTable.getSelectionModel().getSelectedItem().getIdOferta();
                    System.out.print(selected);
                }else {
                    btnDetalles.setDisable(true);
                }
            }
        });
        
        sitioTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sitioTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(sitioTable.getSelectionModel().getSelectedItem() != null){
                    btnDetalles.setDisable(false);
                    
                    selected = sitioTable.getSelectionModel().getSelectedItem().getId();
                    System.out.print(selected);
                }else {
                    btnDetalles.setDisable(true);
                }
            }
        });
    }
    
    private void fillColumns(){
        sitioCol.setCellValueFactory(new PropertyValueFactory<>("sitio"));
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        alturaCol.setCellValueFactory(new PropertyValueFactory<>("altura"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        alturaDisCol.setCellValueFactory(new PropertyValueFactory<>("alturaDis"));
        
        nombreSitCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        alturaDisSitioCol.setCellValueFactory(new PropertyValueFactory<>("alturaDisponible"));
        latitudSitioCol.setCellValueFactory(new PropertyValueFactory<>("latitud"));
        longitudSitioCol.setCellValueFactory(new PropertyValueFactory<>("longitud"));
        dispSitioCol.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        costosSitioCol.setCellValueFactory(new PropertyValueFactory<>("costosAlcadia"));
        costosArrendSitioCol.setCellValueFactory(new PropertyValueFactory<>("costosArrendamiento"));
        
        offerBox.getStyleClass().add("itsSelected");
    }

    private void getTheOfferData() {
        data.clear();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
        EntityManager em = emf.createEntityManager();
        List<oferta> rows = (List<oferta>) em.createQuery("FROM oferta").getResultList();
        
        rows.forEach((cell)->{
            data.add(new MainOfferTable(cell.getId(), cell.getLocacion().getNombre(), cell.getClienteOf().getNombre(), cell.getAlturaTorre(), cell.getFecha().format(DateTimeFormatter.ofPattern("uuuu/d/MM")), cell.getLocacion().getTorre().getAlturaPedida()));
        });
        
        List<sitio> sitRow = (List<sitio>) em.createQuery("FROM sitio").getResultList();
        
        sitRow.forEach((cell)->{
            dataSit.add(new SitiosTable(cell.getId(), cell.getNombre(), cell.getLatitud(), cell.getLongitud(), cell.getTorre().getAlturaPedida(), "NO", cell.getLicencia().getMonto(), cell.getArrendamiento().getCosto()));
        });
        
        em.close();
        emf.close();
    }
    
    private void setPredicados(){
        predicate1 = p1 -> p1.getSitio().toLowerCase().contains(searchBar.getText().toLowerCase().trim());
        predicate2 = p2 -> p2.getAltura().toString().contains(searchBar.getText().toLowerCase().trim());
        predicate3 = p3 -> p3.getCliente().toLowerCase().contains(searchBar.getText().toLowerCase().trim());
        
        predicate4 = p4 -> p4.getNombre().toLowerCase().contains(searchBar.getText().toLowerCase().trim());
        predicate5 = p5 -> p5.getLatitud().toString().contains(searchBar.getText().toLowerCase().trim());
        predicate6 = p6 -> p6.getLongitud().toString().contains(searchBar.getText().toLowerCase().trim());
    }
    
    @FXML
    private void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    private void closeWindow(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void filterData(KeyEvent event) {
        if(searchBar.getText().length() > 0){
            if(mainTable.isVisible()){
                filtrada.setPredicate(predicate1.or(predicate2.or(predicate3)));
            } else if(sitioTable.isVisible()){
                filtradaSit.setPredicate(predicate4.or(predicate5.or(predicate6)));
            }
            
            cancel.setVisible(true);
        }
    }
    
    @FXML
    private void backToNormalData(ActionEvent event) {
        if(mainTable.isVisible()){
            filtrada.setPredicate(p -> true);
        } else if(sitioTable.isVisible()){
            filtradaSit.setPredicate(p -> true);
        }
        
        searchBar.setText(null);
        cancel.setVisible(false);
    }
    
    @FXML
    private void showSitios(MouseEvent event) {
        mainTable.setVisible(false);
        sitioTable.setVisible(true);
        
        sitiosBox.getStyleClass().add("itsSelected");
        offerBox.getStyleClass().remove("itsSelected");
        
        mainTable.getSelectionModel().clearSelection();
        
        setPredicados();
    }
    
    @FXML
    private void showMain(MouseEvent event) {
        sitioTable.setVisible(false);
        mainTable.setVisible(true);
        
        offerBox.getStyleClass().add("itsSelected");
        sitiosBox.getStyleClass().remove("itsSelected");
        
        setPredicados();
    }
    
    private void clearSelections(){
        mainTable.getSelectionModel().clearSelection();
        sitioTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    void openDetail(ActionEvent event) {
        //Open detail window
    }
    
    public class MainOfferTable extends RecursiveTreeObject<MainOfferTable>{
        private final long idOferta;
        private final StringProperty sitio;
        private final StringProperty cliente;
        private final FloatProperty altura;
        private final StringProperty fecha;
        private final FloatProperty alturaDis;

        public MainOfferTable(long idOferta, String sitio, String cliente, float altura, String fecha, float alturaDis) {
            this.idOferta = idOferta;
            this.sitio = new SimpleStringProperty(sitio);
            this.cliente = new SimpleStringProperty(cliente);
            this.altura = new SimpleFloatProperty(altura);
            this.fecha = new SimpleStringProperty(fecha);
            this.alturaDis = new SimpleFloatProperty(alturaDis);
        }

        public String getSitio() {
            return sitio.get();
        }

        public String getCliente() {
            return cliente.get();
        }

        public Float getAltura() {
            return altura.get();
        }

        public String getFecha() {
            return fecha.get();
        }

        public float getImagePath() {
            return alturaDis.get();
        }

        public long getIdOferta() {
            return idOferta;
        }
    }
    
    public class SitiosTable extends RecursiveTreeObject<SitiosTable>{
        private final long id;
        private final StringProperty nombre;
        private final FloatProperty latitud;
        private final FloatProperty longitud;
        private final FloatProperty alturaDisponible;
        private final StringProperty disponible;
        private final FloatProperty costosAlcadia;
        private final FloatProperty costosArrendamiento;

        public SitiosTable(long id, String nombre, float latitud, float longitud, float alturaDisponible, String disponible, float costosAlcadia, float costosArrendamiento) {
            this.id = id;
            this.nombre = new SimpleStringProperty(nombre);
            this.latitud = new SimpleFloatProperty(latitud);
            this.longitud = new SimpleFloatProperty(longitud);
            this.alturaDisponible = new SimpleFloatProperty(alturaDisponible);
            this.disponible = new SimpleStringProperty(disponible);
            this.costosAlcadia = new SimpleFloatProperty(costosAlcadia);
            this.costosArrendamiento = new SimpleFloatProperty(costosArrendamiento);
        }

        public String getNombre() {
            return nombre.get();
        }

        public Float getLatitud() {
            return latitud.get();
        }

        public Float getLongitud() {
            return longitud.get();
        }

        public Float getAlturaDisponible() {
            return alturaDisponible.get();
        }

        public String getDisponible() {
            return disponible.get();
        }

        public Float getCostosAlcadia() {
            return costosAlcadia.get();
        }

        public Float getCostosArrendamiento() {
            return costosArrendamiento.get();
        }

        public long getId() {
            return id;
        }
    }
}
