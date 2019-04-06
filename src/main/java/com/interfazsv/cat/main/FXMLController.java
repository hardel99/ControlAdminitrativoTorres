package com.interfazsv.cat.main;

import Entitys.cliente;
import Entitys.oferta;
import Entitys.sitio;
import TableData.ClientesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import com.interfazsv.cat.util.CATUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FXMLController implements Initializable {
    
    @FXML
    private StackPane canvas;
    
    @FXML
    private JFXButton btnDetalles;
    
    @FXML
    private HBox offerBox;

    @FXML
    private HBox sitiosBox;
    
    @FXML
    private HBox clientBox;

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
    private TableView<ClientesTable> clienteTable;

    @FXML
    private TableColumn<ClientesTable, String> nombreClienteCol;

    @FXML
    private TableColumn<ClientesTable, Float> torresClienteCol;

    @FXML
    private TableColumn<ClientesTable, Float> ofertasClienteCol;

    @FXML
    private TableColumn<ClientesTable, Float> llavesClienteCol;
    
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
    
    private final ObservableList<ClientesTable> dataClient = FXCollections.observableArrayList();
    private FilteredList<ClientesTable> filtradaClient;
    
    private long selected;
    
    private Predicate<MainOfferTable> predicate1, predicate2, predicate3;
    private Predicate<SitiosTable> predicate4, predicate5, predicate6;
    private Predicate<ClientesTable> predicate7;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillColumns();
        getTheData();
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
        
        filtradaClient = new FilteredList(dataClient);
        SortedList<ClientesTable> sortedClient = new SortedList(filtradaClient);
        sortedClient.comparatorProperty().bind(clienteTable.comparatorProperty());
        
        clienteTable.setItems(sortedClient);
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
        
        clienteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        clienteTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(clienteTable.getSelectionModel().getSelectedItem() != null){
                    btnDetalles.setDisable(false);
                    
                    selected = clienteTable.getSelectionModel().getSelectedItem().getId();
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
        
        nombreClienteCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        torresClienteCol.setCellValueFactory(new PropertyValueFactory<>("cantidadAntenas"));
        ofertasClienteCol.setCellValueFactory(new PropertyValueFactory<>("cantidadOfertas"));
        llavesClienteCol.setCellValueFactory(new PropertyValueFactory<>("cantidadLlaves"));
        
        offerBox.getStyleClass().add("itsSelected");
    }

    private void getTheData() {
        data.clear();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
        EntityManager em = emf.createEntityManager();
        List<oferta> rows = (List<oferta>) em.createQuery("FROM oferta").getResultList();
        
        rows.forEach((cell)->{
            data.add(new MainOfferTable(cell.getId(), cell.getLocacion().getNombre(), cell.getClienteOf().getNombre(), cell.getAlturaTorre(), cell.getFecha().format(DateTimeFormatter.ofPattern("uuuu/MM/d")), cell.getLocacion().getTorre().getAlturaPedida()));
        });
        
        List<sitio> sitRow = (List<sitio>) em.createQuery("FROM sitio").getResultList();
        
        sitRow.forEach((cell)->{
            dataSit.add(new SitiosTable(cell.getId(), cell.getNombre(), cell.getLatitud(), cell.getLongitud(), cell.getTorre().getAlturaPedida(), "NO", cell.getLicencia().getMonto(), cell.getLicencia().getDocumentPath(), cell.getArrendamiento().getCosto(), cell.getArrendamiento().getDocumentPath(), cell.getComent()));
        });
        
        List<cliente> clientRow = (List<cliente>) em.createQuery("FROM cliente").getResultList();
        
        clientRow.forEach((cell)->{
            dataClient.add(new ClientesTable(cell.getId(), cell.getNombre(), cell.getAntenaC().getClienteAn().size(), cell.getOfertaC().size(), cell.getLlaveC().size()));
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
        
        predicate7 = p7 -> p7.getNombre().contains(searchBar.getText().toLowerCase().trim());
    }
    
    private void diselectAll(){
        offerBox.getStyleClass().remove("itsSelected");
        sitiosBox.getStyleClass().remove("itsSelected");
        clientBox.getStyleClass().remove("itsSelected");
        
        mainTable.getSelectionModel().clearSelection();
        sitioTable.getSelectionModel().clearSelection();
        clienteTable.getSelectionModel().clearSelection();
        
        mainTable.setVisible(false);
        sitioTable.setVisible(false);
        clienteTable.setVisible(false);
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
            } else if(clienteTable.isVisible()){
                filtradaClient.setPredicate(predicate7);
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
        } else if(clienteTable.isVisible()){
            filtradaClient.setPredicate(p -> true);
        }
        
        searchBar.setText(null);
        cancel.setVisible(false);
    }
    
    @FXML
    private void showSitios(MouseEvent event) {
        diselectAll();
        sitioTable.setVisible(true);
        sitiosBox.getStyleClass().add("itsSelected");
        
        setPredicados();
    }
    
    @FXML
    private void showMain(MouseEvent event) {
        diselectAll();
        mainTable.setVisible(true);
        offerBox.getStyleClass().add("itsSelected");
        
        setPredicados();
    }
    
    @FXML
    private void showClient(MouseEvent event) {
        diselectAll();
        clienteTable.setVisible(true);
        clientBox.getStyleClass().add("itsSelected");
        
        setPredicados();
    }
    
    private void clearSelections(){
        mainTable.getSelectionModel().clearSelection();
        sitioTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void exportTable(ActionEvent event) {
        List<List> dataToPrint = new ArrayList();
        List<String> headers = new ArrayList();
        TableView<?> actualTable = null;
        
        if(mainTable.isVisible()){
            headers.add("         Sitio         ");
            headers.add("         Cliente         ");
            headers.add(" Altura Solicitada ");
            headers.add(" Altura Disponible ");
            headers.add("    Fecha    ");
            
            actualTable = mainTable;
        } else if(sitioTable.isVisible()){
            headers.add("         Nombre         ");
            headers.add("  Latitud  ");
            headers.add("  Longitud  ");
            headers.add(" Altura Disponible ");
            headers.add(" Disponible ");
            headers.add(" Costos Alcaldía ");
            headers.add(" Costos Arrendamiento ");
            
            actualTable = clienteTable;
        } else if(clienteTable.isVisible()){
            headers.add("         Nombre        ");
            headers.add("     Torres     ");
            headers.add("   Ofertas   ");
            headers.add("   Llaves   ");
            
            actualTable = sitioTable;
        }
        
        dataToPrint = mapDataToPrint(headers);
        
        CATUtil.initPDFExport(canvas, actualTable, (Stage) actualTable.getScene().getWindow(), dataToPrint);
        headers.clear();
    }
    
    private List<List> mapDataToPrint(List<String> headers){
        List<List> allRows = new ArrayList();
        allRows.add(headers);
        
        if(mainTable.isVisible()){
            data.stream().map((mot) -> {
            List<String> row = new ArrayList();
            row.add(mot.getSitio());
            row.add(mot.getCliente());
            row.add(mot.getAltura().toString());
            row.add(mot.getAlturaDis().toString());
            row.add(mot.getFecha());
            return row;            
            }).forEachOrdered((row) -> {
                allRows.add(row);
            });
        } else if(clienteTable.isVisible()){
            dataClient.stream().map((mot) -> {
            List<String> row = new ArrayList();
            row.add(mot.getNombre());
            row.add(mot.getCantidadAntenas().toString());
            row.add(mot.getCantidadOfertas().toString());
            row.add(mot.getCantidadLlaves().toString());
            return row;            
            }).forEachOrdered((row) -> {
                allRows.add(row);
            });
        } else if(sitioTable.isVisible()){
            dataSit.stream().map((mot) -> {
            List<String> row = new ArrayList();
            row.add(mot.getNombre());
            row.add(mot.getLatitud().toString());
            row.add(mot.getLongitud().toString());
            row.add(mot.getAlturaDisponible().toString());
            row.add(mot.getDisponible());
            row.add(mot.getCostosAlcadia().toString());
            row.add(mot.getCostosArrendamiento().toString());
            return row;            
            }).forEachOrdered((row) -> {
                allRows.add(row);
            });
        }
        
        return allRows;
    }
    
    @FXML
    private void openDetail(ActionEvent event) {
        Object cebo = null;
        String tableName = "";
        if(mainTable.isVisible()){
            MainOfferTable offer = data.stream().filter(oferta -> selected == oferta.getIdOferta()).findAny().orElse(null);
            cebo = offer;
            tableName = "Oferta";
        } else if(sitioTable.isVisible()){
            SitiosTable sit = dataSit.stream().filter(sitio -> selected == sitio.getId()).findAny().orElse(null);
            cebo = sit;
            tableName = "Sitio";
        } else if(clienteTable.isVisible()){
            ClientesTable client = dataClient.stream().filter(cliente -> selected == cliente.getId()).findAny().orElse(null);
            cebo = client;
            tableName = "Cliente";
        }
        
        CATUtil.loadWindow(getClass().getResource("/fxml/Detail.fxml"), "Detalles", null, cebo, tableName);
    }
}
