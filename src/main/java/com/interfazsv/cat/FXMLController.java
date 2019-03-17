package com.interfazsv.cat;

import Entitys.oferta;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FXMLController implements Initializable {

    @FXML
    private JFXTreeTableView<MainOfferTable> mainTable;
    
    private ObservableList<MainOfferTable> data;
    private List<oferta> rows;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillColumns();
        getTheData();
    }
    
    private void fillColumns(){
        JFXTreeTableColumn<MainOfferTable, String> sitioCol = new JFXTreeTableColumn<>("Sitio");
        JFXTreeTableColumn<MainOfferTable, String> clienteCol = new JFXTreeTableColumn<>("Cliente");
        JFXTreeTableColumn<MainOfferTable, String> alturaCol = new JFXTreeTableColumn<>("Altura");
        JFXTreeTableColumn<MainOfferTable, String> fechaCol = new JFXTreeTableColumn<>("Fecha");
        JFXTreeTableColumn<MainOfferTable, String> imageCol = new JFXTreeTableColumn<>("Imagen");
        
        sitioCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MainOfferTable, String> param) -> param.getValue().getValue().sitio);
        clienteCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MainOfferTable, String> param) -> param.getValue().getValue().cliente);
        alturaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MainOfferTable, String> param) -> param.getValue().getValue().altura);
        fechaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MainOfferTable, String> param) -> param.getValue().getValue().fecha);
        imageCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<MainOfferTable, String> param) -> param.getValue().getValue().imagePath);
        
        mainTable.getColumns().addAll(sitioCol, clienteCol, alturaCol, fechaCol, imageCol);
    }

    private void getTheData() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");
        EntityManager em = emf.createEntityManager();
        rows = (List<oferta>) em.createQuery("FROM oferta").getResultList();
        
        data = FXCollections.observableArrayList();
        
        rows.forEach((cell)->{
            data.add(new MainOfferTable(cell.getLocacion().getNombre(), cell.getClienteOf().getNombre(), "" + cell.getAlturaTorre(), cell.getFecha().toString(), cell.getImagenRuta()));
        });
        
        final TreeItem<MainOfferTable> root = new RecursiveTreeItem<MainOfferTable>(data, RecursiveTreeObject::getChildren);
        mainTable.setRoot(root);
        mainTable.setShowRoot(false);
        
        em.close();
        emf.close();
    }
    
    class MainOfferTable extends RecursiveTreeObject<MainOfferTable>{
        StringProperty sitio;
        StringProperty cliente;
        StringProperty altura;
        StringProperty fecha;
        StringProperty imagePath;

        public MainOfferTable(String sitio, String cliente, String altura, String fecha, String imagePath) {
            this.sitio = new SimpleStringProperty(sitio);
            this.cliente = new SimpleStringProperty(cliente);
            this.altura = new SimpleStringProperty(altura);
            this.fecha = new SimpleStringProperty(fecha);
            this.imagePath = new SimpleStringProperty(imagePath);
        }
    }
}
