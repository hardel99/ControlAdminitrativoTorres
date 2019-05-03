package com.interfazsv.cat.main;

import Entitys.cliente;
import Entitys.llave;
import Entitys.oferta;
import Entitys.sitio;
import TableData.ClientesTable;
import TableData.LlavesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import callback.DataReturnCallback;
import com.interfazsv.cat.createRegister.CreateRegisterController;
import com.interfazsv.cat.detail.DetailController;
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

public class FXMLController implements Initializable, DataReturnCallback {
    /**
     * TO-DO:
     * Canones
     * remove clientes table and makeit for canons
    **/
    
    @FXML
    private StackPane canvas;
    
    @FXML
    private JFXButton btnDetalles;
    
    @FXML
    private HBox offerBox;

    @FXML
    private HBox sitiosBox;
    
    @FXML
    private HBox canonBox;
    
    @FXML
    private HBox llavesBox;
    
    @FXML
    private JFXButton btnPrintActualTableExcel;
    
    @FXML
    private JFXButton btnPrintActualTable;

    @FXML
    private TableView<MainOfferTable> mainTable;
    
    @FXML
    private TableView<SitiosTable> sitioTable;
    
    @FXML
    private TableView<ClientesTable> clienteTable;
    
    @FXML
    private TableView<LlavesTable> llavesTable;
    
    @FXML
    private TableColumn<MainOfferTable, String> estadoCol;
    
    @FXML
    private TableColumn<MainOfferTable, String> sitioCol;

    @FXML
    private TableColumn<MainOfferTable, String> clienteCol;
    
    @FXML
    private TableColumn<MainOfferTable, Float> montoCol;

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
    private TableColumn<ClientesTable, String> nombreClienteCol;

    @FXML
    private TableColumn<ClientesTable, Float> torresClienteCol;

    @FXML
    private TableColumn<ClientesTable, Float> ofertasClienteCol;

    @FXML
    private TableColumn<ClientesTable, Float> llavesClienteCol;
    
    @FXML
    private TableColumn<LlavesTable, String> sitioLlavesCol;
    
    @FXML
    private TableColumn<LlavesTable, String> clienteLlavesCol;

    @FXML
    private TableColumn<LlavesTable, String> subempresaLlavesCol;

    @FXML
    private TableColumn<LlavesTable, String> personaLlavesCol;

    @FXML
    private TableColumn<LlavesTable, Integer> cantidadLlavesCol;
    
    @FXML
    private TableColumn<LlavesTable, String> fechaRetiroLlavesCol;

    @FXML
    private TableColumn<LlavesTable, String> fechaDevolucionLlavesCol;
    
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
    
    private final ObservableList<LlavesTable> dataLlaves = FXCollections.observableArrayList();
    private FilteredList<LlavesTable> filtradaLlaves;
    
    private long selected;
    
    private Predicate<MainOfferTable> predicate1, predicate2, predicate3;
    private Predicate<SitiosTable> predicate4, predicate5, predicate6;
    private Predicate<ClientesTable> predicate7;
    private Predicate<LlavesTable> predicate8, predicate9, predicate0;

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
        
        filtradaLlaves = new FilteredList(dataLlaves);
        SortedList<LlavesTable> sortedLlaves = new SortedList(filtradaLlaves);
        sortedLlaves.comparatorProperty().bind(llavesTable.comparatorProperty());
        
        llavesTable.setItems(sortedLlaves);
    }
    
    private void quickConfigs(){
        selectionConf(mainTable);
        selectionConf(sitioTable);
        selectionConf(clienteTable);
        selectionConf(llavesTable);
    }
    
    private void selectionConf(TableView tv){
        tv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(tv.getSelectionModel().getSelectedItem() != null){
                    btnDetalles.setDisable(false);
                    
                    if(tv == sitioTable){
                        selected = sitioTable.getSelectionModel().getSelectedItem().getId();
                    } else if(tv == clienteTable){
                        selected = clienteTable.getSelectionModel().getSelectedItem().getId();
                    } else if(tv == mainTable){
                        selected = mainTable.getSelectionModel().getSelectedItem().getIdOferta();
                    } else if(tv == llavesTable) {
                        selected = llavesTable.getSelectionModel().getSelectedItem().getId();
                    }
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
        montoCol.setCellValueFactory(new PropertyValueFactory<>("monto"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        alturaDisCol.setCellValueFactory(new PropertyValueFactory<>("alturaDis"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
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
        
        sitioLlavesCol.setCellValueFactory(new PropertyValueFactory<>("sitio"));
        clienteLlavesCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        subempresaLlavesCol.setCellValueFactory(new PropertyValueFactory<>("subempresa"));
        personaLlavesCol.setCellValueFactory(new PropertyValueFactory<>("personaReceptor"));
        cantidadLlavesCol.setCellValueFactory(new PropertyValueFactory<>("cantidadLlaves"));
        fechaRetiroLlavesCol.setCellValueFactory(new PropertyValueFactory<>("fechaRetiro"));
        fechaDevolucionLlavesCol.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
        
        offerBox.getStyleClass().add("itsSelected");
    }

    private void getTheData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
        EntityManager em = emf.createEntityManager();
        List<oferta> rows = (List<oferta>) em.createQuery("FROM oferta").getResultList();
        
        rows.forEach((cell)->{
            data.add(new MainOfferTable(cell.getId(), cell.getEstado(),cell.getLocacion().getNombre(), cell.getClienteOf().getNombre(), cell.getAlturaTorre(), cell.getMonto(), cell.getFecha().format(DateTimeFormatter.ofPattern("uuuu/MM/d")), cell.getLocacion().getTorre().getAlturaPedida(), cell.getImagenRuta(), cell.getCanon(), cell.getDocumentPath()));
        });
        
        List<sitio> sitRow = (List<sitio>) em.createQuery("FROM sitio").getResultList();
        
        sitRow.forEach((cell)->{
            dataSit.add(new SitiosTable(cell));
        });
        
        List<cliente> clientRow = (List<cliente>) em.createQuery("FROM cliente").getResultList();
        
        clientRow.forEach((cell)->{
            dataClient.add(new ClientesTable(cell.getId(), cell.getNombre(), cell.getTorreC().size(), cell.getOfertaC().size(), cell.getLlaveC().size(), cell.getLlaveC(), cell.getTorreC(), cell.getOfertaC()));
        });
        
        List<llave> llaveRow = (List<llave>)em.createQuery("FROM llave").getResultList();
        
        llaveRow.forEach((cell) -> {
            dataLlaves.add(new LlavesTable(cell.getId(), cell.getPersonaResponsable(), cell.getNombreP(), cell.getSitioY().getNombre(), cell.getCantidadLlaves(), cell.getSubempresa(), cell.getFechaRetiro(), cell.getFechaDevolucion()));
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
        
        predicate8 = p8 -> p8.getSitio().contains(searchBar.getText().toLowerCase().trim());
        predicate9 = p9 -> p9.getSubempresa().contains(searchBar.getText().toLowerCase().trim());
        predicate0 = p0 -> p0.getPersonaReceptor().contains(searchBar.getText().toLowerCase().trim());
    }
    
    private void diselectAll(){
        offerBox.getStyleClass().remove("itsSelected");
        sitiosBox.getStyleClass().remove("itsSelected");
        canonBox.getStyleClass().remove("itsSelected");
        llavesBox.getStyleClass().remove("itsSelected");
        
        mainTable.getSelectionModel().clearSelection();
        sitioTable.getSelectionModel().clearSelection();
        clienteTable.getSelectionModel().clearSelection();
        llavesTable.getSelectionModel().clearSelection();
        
        mainTable.setVisible(false);
        sitioTable.setVisible(false);
        clienteTable.setVisible(false);
        llavesTable.setVisible(false);
    }
    
    private String itsDisponible(int torres){
        String disponible;
        disponible = (torres < 3)?"Si":"No";
        return disponible;
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
            } else if(llavesTable.isVisible()){
                filtradaLlaves.setPredicate(predicate8.or(predicate9).or(predicate0));
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
        } else if(llavesTable.isVisible()){
            filtradaLlaves.setPredicate(p -> true);
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
        canonBox.getStyleClass().add("itsSelected");
        
        setPredicados();
    }
    
    @FXML
    private void showLlaves(MouseEvent event) {
        diselectAll();
        llavesTable.setVisible(true);
        llavesBox.getStyleClass().add("itsSelected");
        
        setPredicados();
    }
    
    
    private void clearSelections(){
        mainTable.getSelectionModel().clearSelection();
        sitioTable.getSelectionModel().clearSelection();
        clienteTable.getSelectionModel().clearSelection();
        llavesTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void exportTable(ActionEvent event) {
        List<List> dataToPrint = new ArrayList();
        List<String> headers = new ArrayList();
        TableView<?> actualTable = null;
        
        if(mainTable.isVisible()){
            headers.add("     Estado     ");
            headers.add("         Sitio         ");
            headers.add("         Cliente         ");
            headers.add("    Monto   ");
            headers.add(" Altura Solicitada ");
            headers.add(" Altura Disponible ");
            headers.add("    Fecha Oferta   ");
            
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
        } else if(llavesTable.isVisible()){
            headers.add("         Cliente         ");
            headers.add("     Sub-empresa     ");
            headers.add("    Sitio   ");
            headers.add("   Retirada Por   ");
            headers.add("    Llaves Retiradas    ");
            headers.add("    Fecha de Retiro   ");
            headers.add("    Fecha de Devolucion   ");
            
            actualTable = llavesTable;
        }
        
        dataToPrint = mapDataToPrint(headers);
        
        if(event.getSource() == btnPrintActualTableExcel) {
            CATUtil.initExcelExport(canvas, actualTable, (Stage) actualTable.getScene().getWindow(), dataToPrint);
        } else if(event.getSource() == btnPrintActualTable) {
            CATUtil.initPDFExport(canvas, actualTable, (Stage) actualTable.getScene().getWindow(), dataToPrint);
        }
        
        
        headers.clear();
    }
    
    private List<List> mapDataToPrint(List<String> headers){
        List<List> allRows = new ArrayList();
        allRows.add(headers);
        
        if(mainTable.isVisible()){
            data.stream().map((mot) -> {
            List<String> row = new ArrayList();
            row.add(mot.getEstado());
            row.add(mot.getSitio());
            row.add(mot.getCliente());
            row.add(mot.getMonto().toString());
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
        } else if(llavesTable.isVisible()){
            dataLlaves.stream().map((mot) -> {
                List<String> row = new ArrayList();
                row.add(mot.getCliente());
                row.add(mot.getSubempresa());
                row.add(mot.getSitio());
                row.add(mot.getPersonaReceptor());
                row.add(String.valueOf(mot.getCantidadLlaves()));
                row.add(mot.getFechaRetiro().toString());
                row.add(mot.getFechaDevolucion().toString());
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
        } else if(llavesTable.isVisible()){
            LlavesTable llave = dataLlaves.stream().filter(llaves -> selected == llaves.getId()).findAny().orElse(null);
            cebo = llave;
            tableName = "Llave";
        }
        
        Object modifying = CATUtil.loadWindow(getClass().getResource("/fxml/Detail.fxml"), "Detalles", null, cebo, tableName);
        if(modifying != null) {
            DetailController detail = (DetailController) modifying;
            detail.setCallback(this);
            detail.setOnClose();
        }
    }
    
    @FXML
    private void openDocumentWindow(ActionEvent event) {
        CATUtil.loadWindow(getClass().getResource("/fxml/CustomExport.fxml"), "Generar Documento", null);
    }
    
    @FXML
    private void agregarRegistro(ActionEvent event) {
        Object addRegister = CATUtil.loadWindow(getClass().getResource("/fxml/CreateRegister.fxml"), "Crear Nuevo Registro", null);
        if(addRegister != null){
            CreateRegisterController crc = (CreateRegisterController) addRegister;
            crc.setCallback(this);
            crc.prepareFromClose();
        }
    }

    @Override
    public void refreshMainData(MainOfferTable mot) {
        data.add(mot);
    }

    @Override
    public void refreshSitioData(SitiosTable st) {
        dataSit.add(st);
    }

    @Override
    public void refreshClienteData(ClientesTable ct) {
        dataClient.add(ct);
    }

    @Override
    public void refreshLlaveData(LlavesTable lt) {
        dataLlaves.add(lt);
    }
    
}
