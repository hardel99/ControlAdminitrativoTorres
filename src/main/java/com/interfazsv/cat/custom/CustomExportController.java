package com.interfazsv.cat.custom;

import Entitys.venta;
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
    
    @FXML
    private TableColumn<CustomTable, String> clienteCol;

    @FXML
    private TableColumn<CustomTable, String> sitioCol;
    
    private final ObservableList<CustomTable> dinamicList = FXCollections.observableArrayList();
    
    private final JFXButton aceptBtn = new JFXButton("Ok");
    
    @FXML
    private JFXButton excel;
    
    private List<String> headers = new ArrayList();
    
    private int index = 0, column;
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customTable.setItems(dinamicList);
        chipView.getSuggestions().add("Todos");
        fillChipView(chipView);
        
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        sitioCol.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        
        fromDP.setValue(LocalDate.now());
        toDP.setValue(LocalDate.now());
        
        headers.add("Cliente");
        headers.add("Sitio");
    }   
    
    @FXML
    private void addFields(ActionEvent event) {
        customTable.getColumns().clear();
        customTable.getColumns().add(clienteCol);
        customTable.getColumns().add(sitioCol);
        
        customTable.getItems().clear();
        
        getPreviewData();
        
        if(chipView.getChips().size() > 0) {
            CustomTable ct = dinamicList.get(0);
           
            //PREVIEW JUST GIVE ONE RESULT
            ct.getMonth().forEach(month -> {
                String nombreColumna = setColumnName(index);
                TableColumn<CustomTable, Float> tc = new TableColumn<>(nombreColumna);
                headers.add(nombreColumna);
                tc.setCellValueFactory(cellData -> month.asObject());
                tc.setPrefWidth(150);
                customTable.getColumns().add(tc);
                index++;
            });
            
            index = 0;
            
            TableColumn<CustomTable, Float> total = new TableColumn<>("Total");
            total.setCellValueFactory(new PropertyValueFactory<>("total"));
            total.setPrefWidth(200);
            customTable.getColumns().add(total);
            headers.add("Total");
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Datos en blanco", "Por favor añada almenos un campo a exportar");
        }
    }
    
    private String setColumnName(int mes) {
        String nombreMes = "";
        LocalDate refreshDate = fromDP.getValue().plusMonths(mes);
        
        switch(refreshDate.getMonthValue()) {
            case 1:
                nombreMes = "Enero - ";
                break;
            case 2:
                nombreMes = "Febrero - ";
                break;
            case 3:
                nombreMes = "Marzo - ";
                break;
            case 4:
                nombreMes = "Abril - ";
                break;
            case 5:
                nombreMes = "Mayo - ";
                break;
            case 6:
                nombreMes = "Junio - ";
                break;
            case 7:
                nombreMes = "Julio - ";
                break;
            case 8:
                nombreMes = "Agosto - ";
                break;
            case 9:
                nombreMes = "Septiembre - ";
                break;
            case 10:
                nombreMes = "Octubre - ";
                break;
            case 11:
                nombreMes = "Noviembre - ";
                break;
            case 12:
                nombreMes = "Diciembre - ";
                break;
        }
        nombreMes += refreshDate.getYear();
        
        return nombreMes;
    }
    
    @FXML
    private void clearFields(ActionEvent event) {
        if(customTable.getColumns().size() > 0){
            chipView.getChips().clear();
            customTable.getColumns().clear();
            headers.clear();
            headers.add("Cliente");
            headers.add("Sitio");
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Datos en blanco", "No ha agregado ninguna columna aún");
        }
    }
    
    @FXML
    private void exportIt(ActionEvent event) {
        List<List> data = new ArrayList();
        data.add(headers);
        
        dinamicList.stream().map((mapper) -> {
            List<String> row = new ArrayList();
            row.add(mapper.getCliente());
            row.add(mapper.getLugar());
            mapper.getMonth().forEach((column) -> {
                row.add(String.valueOf(column.get()));
            });
            row.add(mapper.getTotal().toString());
            return row;
        }).forEachOrdered((row) -> {
            data.add(row);
        });
        
        if(event.getSource() == excel) {
            CATUtil.initExcelExport(root, customTable, (Stage) customTable.getScene().getWindow(), data, true);
        } else{
            CATUtil.initPDFExport(root, customTable, (Stage) customTable.getScene().getWindow(), data, true);
        }
    }
    
    private void getPreviewData(){
        Period deltaT = Period.between(fromDP.getValue(), toDP.getValue());
        int realMonthValue = (deltaT.getYears() * 12) + deltaT.getMonths();
        
        if(realMonthValue > 0){
            dinamicList.clear();
            String fromDate = fromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
            String toDate = toDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
        
            EntityManager em = emf.createEntityManager();
            String stat = "(v.fechaFin >= '" + toDate + "' AND v.fechaInicio <= '" + fromDate + "') OR ";
            String stat2 = "(v.fechaInicio BETWEEN '" + fromDate + "' AND '" + toDate + "')";
            List<venta> previewRows = em.createQuery("FROM venta v WHERE " + stat + "(v.fechaFin BETWEEN '" + fromDate + "' AND '" + toDate + "') OR " + stat2).getResultList();
            List<venta> realList = new ArrayList();
            
            if(chipView.getChips().contains("Todos")) {
                realList = previewRows;
            } else{
                List<venta> cebo = new ArrayList();
                if(chipView.getChips().contains("Tigo")) {
                    previewRows.stream().filter((v) -> (v.getClienteV().getNombre().equals("Tigo"))).forEachOrdered((v) -> {
                        cebo.add(v);
                    });
                    realList.addAll(cebo);
                }
                if(chipView.getChips().contains("Tesco")) {
                    previewRows.stream().filter((v) -> (v.getClienteV().getNombre().equals("Tesco"))).forEachOrdered((v) -> {
                        cebo.add(v);
                    });
                    realList.addAll(cebo);
                }
                if(chipView.getChips().contains("CableSal")) {
                    previewRows.stream().filter((v) -> (v.getClienteV().getNombre().equals("CableSal"))).forEachOrdered((v) -> {
                        cebo.add(v);
                    });
                    realList.addAll(cebo);
                }
                if(chipView.getChips().contains("Movistar")) {
                    previewRows.stream().filter((v) -> (v.getClienteV().getNombre().equals("Movistar"))).forEachOrdered((v) -> {
                        cebo.add(v);
                    });
                    realList.addAll(cebo);
                }
                if(chipView.getChips().contains("Digicel")) {
                    previewRows.stream().filter((v) -> (v.getClienteV().getNombre().equals("Digicel"))).forEachOrdered((v) -> {
                        cebo.add(v);
                    });
                    realList.addAll(cebo);
                }
                if(chipView.getChips().contains("Claro")) {
                    previewRows.stream().filter((v) -> (v.getClienteV().getNombre().equals("Claro"))).forEachOrdered((v) -> {
                        cebo.add(v);
                    });
                    realList.addAll(cebo);
                }
            }
            
            realList.forEach((row) ->{
                dinamicList.add(new CustomTable(row, fromDP.getValue(), toDP.getValue(), realMonthValue));
            });

            em.close();
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Fechas erroneas", "Hay un problema con las fechas que ingreso, por favor asegurese de que estan correctas");
        }
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
