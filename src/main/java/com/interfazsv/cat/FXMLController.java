package com.interfazsv.cat;

import Entitys.oferta;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FXMLController implements Initializable {

    @FXML
    private TableView<MainOfferTable> mainTable;
    
    @FXML
    private TableColumn<MainOfferTable, String> sitioCol;

    @FXML
    private TableColumn<MainOfferTable, String> clienteCol;

    @FXML
    private TableColumn<MainOfferTable, Float> alturaCol;

    @FXML
    private TableColumn<MainOfferTable, String> fechaCol;

    @FXML
    private TableColumn<MainOfferTable, String> imageCol;
    
    private ObservableList<MainOfferTable> data = FXCollections.observableArrayList();;
    private List<oferta> rows;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillColumns();
        getTheData();
    }
    
    private void fillColumns(){
        sitioCol.setCellValueFactory(new PropertyValueFactory<>("sitio"));
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        alturaCol.setCellValueFactory(new PropertyValueFactory<>("altura"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        
        mainTable.setItems(data);
    }

    private void getTheData() {
        data.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
        EntityManager em = emf.createEntityManager();
        rows = (List<oferta>) em.createQuery("FROM oferta").getResultList();
        
        rows.forEach((cell)->{
            data.add(new MainOfferTable(cell.getLocacion().getNombre(), cell.getClienteOf().getNombre(), cell.getAlturaTorre(), cell.getFecha().format(DateTimeFormatter.ofPattern("uuuu/d/MM")), cell.getImagenRuta()));
        });
        
        em.close();
        emf.close();
    }
    
    public class MainOfferTable extends RecursiveTreeObject<MainOfferTable>{
        private final StringProperty sitio;
        private final StringProperty cliente;
        private final FloatProperty altura;
        private final StringProperty fecha;
        private final StringProperty imagePath;

        public MainOfferTable(String sitio, String cliente, float altura, String fecha, String imagePath) {
            this.sitio = new SimpleStringProperty(sitio);
            this.cliente = new SimpleStringProperty(cliente);
            this.altura = new SimpleFloatProperty(altura);
            this.fecha = new SimpleStringProperty(fecha);
            this.imagePath = new SimpleStringProperty(imagePath);
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

        public String getImagePath() {
            return imagePath.get();
        }
    }
}
