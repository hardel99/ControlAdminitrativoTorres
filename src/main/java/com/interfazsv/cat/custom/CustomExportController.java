package com.interfazsv.cat.custom;

import Entitys.cliente;
import Entitys.oferta;
import TableData.CustomTable;
import com.interfazsv.cat.util.AlertFactory;
import com.interfazsv.cat.util.CATUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 *
 * @author hardel
 */
public class CustomExportController implements Initializable {
    /**TO-DO:
     * ChipView to add client
     * Calculus for canons
     * Replace the other data
     **/
    @FXML
    private StackPane root;
    
    @FXML
    private JFXDatePicker fromDP;

    @FXML
    private JFXDatePicker toDP;
    
    @FXML
    private JFXChipView<String> chipView;
    
    @FXML
    private TableView<CustomTable> customTable;
    
    private final ObservableList<CustomTable> dinamicList = FXCollections.observableArrayList();
    
    private final JFXButton aceptBtn = new JFXButton("Ok");
    
    @FXML
    private JFXButton excel;
    
    private List<String> headers = new ArrayList();
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customTable.setItems(dinamicList);
        
        /*chipView.getSuggestions().addAll(
                "Lugar",
                "Latitud", 
                "Longitud", 
                "Altura Disponible",
                "Altura Solicitada",
                "Nombre Arrendatario", 
                "Costo Alcaldia", 
                "Costo Arrendamiento", 
                "Disponible", 
                "Fecha Oferta", 
                "Cliente", 
                "Estado Oferta",
                "Monto"
        );*/
        fillChipView(chipView);
        
        fromDP.setValue(LocalDate.now());
        toDP.setValue(LocalDate.now());
    }   
    
    @FXML
    private void addFields(ActionEvent event) {
        customTable.getColumns().clear();
        customTable.getItems().clear();
        
        ObservableList<String> chips = chipView.getChips();
        if(chips.size() > 0){
            chips.forEach((field) -> {
                TableColumn<CustomTable, String> tc = new TableColumn<>(field);
                headers.add(field);
                
                if(field.contains(" ")){
                    String first = field.substring(0, field.indexOf(" ")).toLowerCase();
                    String second = field.substring(field.indexOf(" ") + 1, field.length());
                    
                    field = first + second;
                } else{
                    field = field.toLowerCase();
                }
                
                tc.setCellValueFactory(new PropertyValueFactory<>(field));
                tc.setPrefWidth(170);
                customTable.getColumns().add(tc);
                
            });
            
            getPreviewData();
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Datos en blanco", "Por favor añada almenos un campo a exportar");
        }
    }
    
    @FXML
    private void clearFields(ActionEvent event) {
        if(customTable.getColumns().size() > 0){
            chipView.getChips().clear();
            customTable.getColumns().clear();
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Datos en blanco", "No ha agregado ninguna columna aún");
        }
    }
    
    @FXML
    private void exportIt(ActionEvent event) {
        List<List> data = new ArrayList();
        Collections.sort(headers);
        data.add(headers);
        
        dinamicList.stream().map((mapper) ->{
            return formatedData(mapper);
        }).forEachOrdered((row) -> {
            data.add(row);
        });
        
        if(event.getSource() == excel) {
            CATUtil.initExcelExport(root, customTable, (Stage) customTable.getScene().getWindow(), data);
        } else{
            CATUtil.initPDFExport(root, customTable, (Stage) customTable.getScene().getWindow(), data);
        }
        
        headers.clear();
    }
    
    private void getPreviewData(){
        Period deltaT = Period.between(fromDP.getValue(), toDP.getValue());
        if(deltaT.getDays() >= 0){
            dinamicList.clear();
            String fromDate = fromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
            String toDate = toDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
        
            EntityManager em = emf.createEntityManager();

            List<oferta> previewRows = em.createQuery("FROM oferta o WHERE o.Fecha BETWEEN '" + fromDate + "' AND '" + toDate + "'").getResultList();

            previewRows.forEach((row) ->{
                dinamicList.add(customData(row));
            });

            em.close();
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Fechas erroneas", "Hay un problema con las fechas que ingreso, por favor asegurese de que estan correctas");
        }
    }
    
    private CustomTable customData(oferta row){
        CustomTable custom = new CustomTable();
        List<String> chips = chipView.getChips();
        
        if(chips.contains("Lugar")){
            custom.setLugar(row.getLocacion().getNombre());
        }
        if(chips.contains("Latitud")){
            custom.setLatitud(row.getLocacion().getLatitud());
        }
        if(chips.contains("Longitud")){
            custom.setLongitud(row.getLocacion().getLongitud());
        }
        if(chips.contains("Monto")){
            custom.setMonto(row.getMonto());
        }
        if(chips.contains("Costo Alcaldia")){
            custom.setCostoAlcaldia(row.getLocacion().getLicencia().getMonto());
        }
        if(chips.contains("Costo Arrendamiento")){
            custom.setCostoArrendamiento(row.getLocacion().getArrendamiento().getCosto());
        }
        if(chips.contains("Disponible")){
            custom.setDisponible((row.getLocacion().getTorre().getClienteT().size() < 3)?"Si":"No");
        }
        if(chips.contains("Fecha Oferta")){
            custom.setFechaOferta(row.getFecha());
        }
        if(chips.contains("Cliente")){
            custom.setCliente(row.getClienteOf().getNombre());
        }
        if(chips.contains("Altura Disponible")){
            custom.setAlturaDisponible(row.getLocacion().getTorre().getAlturaPedida());
        }
        if(chips.contains("Altura Solicitada")){
            custom.setAlturaSolicitada(row.getAlturaTorre());
        }
        if(chips.contains("Nombre Arrendatario")){
            custom.setNombreArrendatario(row.getLocacion().getArrendamiento().getNombreArrendatario());
        }
        if(chips.contains("Estado Oferta")){
            String es = "";
            switch(row.getEstado()){
                case 'I':
                    es = "Incompleto";
                    break;
                case 'P':
                    es = "Pendiente";
                    break;
                case 'C':
                    es = "Completo";
                    break;
                default:
                    es = "nothing";
            }
            
            custom.setEstadoOferta(es);
        }
        
        return custom;
    }
    
    private List<String> formatedData(CustomTable mapp){
        List<String> row = new ArrayList();
        
        /**
         * IMPORTANT!!!!
         * 
         * This has to be sorted by the contains clausule
         **/
        
        if(headers.contains("Altura Disponible")){
            row.add(mapp.getAlturaDisponible().toString());
        }
        if(headers.contains("Altura Solicitada")){
            row.add(mapp.getAlturaSolicitada().toString());
        }
        if(headers.contains("Cliente")){
            row.add(mapp.getCliente());
        }
        if(headers.contains("Costo Alcaldia")){
            row.add(mapp.getCostoAlcaldia().toString());
        }
        if(headers.contains("Costo Arrendamiento")){
            row.add(mapp.getCostoArrendamiento().toString());
        }
        if(headers.contains("Disponible")){
            row.add(mapp.getDisponible());
        }
        if(headers.contains("Estado Oferta")){
            row.add(mapp.getEstadoOferta());
        }
        if(headers.contains("Fecha Oferta")){
            row.add(mapp.getFechaOferta().toString());
        }
        if(headers.contains("Latitud")){
            row.add(mapp.getLatitud().toString());
        }
        if(headers.contains("Longitud")){
            row.add(mapp.getLongitud().toString());
        }
        if(headers.contains("Lugar")){
            row.add(mapp.getLugar());
        }
        if(headers.contains("Monto")){
            row.add(mapp.getMonto().toString());
        }
        if(headers.contains("Nombre Arrendatario")){
            row.add(mapp.getNombreArrendatario());
        }
        
        return row;
    }
    
    private void fillChipView(JFXChipView chip) {
        EntityManager em = emf.createEntityManager();
        List<String> clientes = em.createQuery("SELECT c.nombre FROM cliente c").getResultList();
        chip.getSuggestions().addAll(clientes);
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
    
    public void prepareToClose(){
        root.getScene().getWindow().setOnCloseRequest(event -> {
            emf.close();
        });
    }
}
