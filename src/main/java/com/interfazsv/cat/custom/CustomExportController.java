package com.interfazsv.cat.custom;

import Entitys.sitio;
import Entitys.venta;
import TableData.CustomTable;
import com.interfazsv.cat.util.AlertFactory;
import com.interfazsv.cat.util.CATUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
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
    private JFXTextField futureYears;

    @FXML
    private JFXTextField futureMonths;
    
    @FXML
    private JFXChipView<String> chipView;
    
    @FXML
    private TableView<CustomTable> customTable;
    
    @FXML
    private TableColumn<CustomTable, String> clienteCol;

    @FXML
    private TableColumn<CustomTable, String> sitioCol;
    
    @FXML
    private JFXRadioButton gananciaButton;

    @FXML
    private JFXRadioButton costosButton;

    @FXML
    private JFXRadioButton gananciaNetaButton;
    
    @FXML
    private JFXToggleButton primerPagoLicencia;
    
    private final ObservableList<CustomTable> dinamicList = FXCollections.observableArrayList();
    
    private final JFXButton aceptBtn = new JFXButton("Ok");
    
    @FXML
    private JFXButton excel;
    
    private List<String> headers = new ArrayList();
    
    private int index = 0;
    
    private int realMonthValue = 0;
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CAT");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customTable.setItems(dinamicList);
        chipView.getSuggestions().add("Todos");
        primerPagoLicencia.setSelected(false);
        fillChipView(chipView);
        
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        sitioCol.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        
        fromDP.setValue(LocalDate.now());
        
        headers.add("Cliente");
        headers.add("Sitio");
    }   
    
    @FXML
    private void addFields(ActionEvent event) {
        customTable.getColumns().clear();
        customTable.getColumns().add(clienteCol);
        customTable.getColumns().add(sitioCol);
        
        customTable.getItems().clear();
        
        CustomTable.sitiosPrevios.clear();
        if(gananciaButton.isSelected() || gananciaNetaButton.isSelected()) {
            getPreviewData();
        }else if(costosButton.isSelected()) {
            getPreviewCostosData();
        }
        
        realMonthValue = 0;
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
        List<List> morData = new ArrayList();
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
        
        if(gananciaNetaButton.isSelected()) {
            getPreviewCostosData();
            
            DecimalFormat df = new DecimalFormat("##.00");
            List<Double> niuList = new ArrayList();
            for(int i = 0; i < dinamicList.get(0).getMonth().size(); i++) { //all got the same size
                double acumulado = 0;
                for(int j = 0; j < dinamicList.size(); j++) {  
                    acumulado += dinamicList.get(j).getMonth().get(i).get();
                }
                
                niuList.add(Double.parseDouble(df.format(acumulado)));
            }
            morData.add(niuList);
        }
        
        if(event.getSource() == excel) {
            CATUtil.initExcelExport(root, customTable, (Stage) customTable.getScene().getWindow(), data, morData, true);
        } else{
            CATUtil.initPDFExport(root, customTable, (Stage) customTable.getScene().getWindow(), data, true);
        }
    }
    
    private void getPreviewData() {
        if(!futureYears.getText().isEmpty()) {
            realMonthValue += (Integer.parseInt(futureYears.getText()) * 12);
        }
        if(!futureMonths.getText().isEmpty()) {
            realMonthValue += Integer.parseInt(futureMonths.getText());
        }
        
        if(realMonthValue > 0){
            dinamicList.clear();
            String fromDate = fromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
            String toDate = fromDP.getValue().plusMonths(realMonthValue).format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
        
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
            
            realList.forEach((row) -> {
                dinamicList.add(new CustomTable(row, fromDP.getValue(), fromDP.getValue().plusMonths(realMonthValue), realMonthValue, primerPagoLicencia.isSelected()));
            });

            em.close();
        } else{
            AlertFactory.showDialog(root, chipView.getParent(), Arrays.asList(aceptBtn), "Fechas erroneas", "Hay un problema con las fechas que ingreso, por favor asegurese de que estan correctas");
        }
    }
    
    @FXML
    private void activateToogle(ActionEvent event) {
        if(event.getSource() == costosButton || event.getSource() == gananciaNetaButton) {
            primerPagoLicencia.setVisible(true);
        } else{
            primerPagoLicencia.setVisible(false);
            primerPagoLicencia.setSelected(false);
        }
    }
    
    private void getPreviewCostosData() {
        if(!futureYears.getText().isEmpty()) {
            realMonthValue += (Integer.parseInt(futureYears.getText()) * 12);
        }
        if(!futureMonths.getText().isEmpty()) {
            realMonthValue += Integer.parseInt(futureMonths.getText());
        }
        
        if(realMonthValue > 0){
            dinamicList.clear();
            String fromDate = fromDP.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
            String toDate = fromDP.getValue().plusMonths(realMonthValue).format(DateTimeFormatter.ofPattern("yyyy-MM-d"));
        
            EntityManager em = emf.createEntityManager();
            String stat = "(s.arrendamiento.fechaFinArrendamiento >= '" + toDate + "' AND s.arrendamiento.fechaInicioArrendamiento <= '" + fromDate + "') OR ";
            String stat2 = "(s.arrendamiento.fechaInicioArrendamiento BETWEEN '" + fromDate + "' AND '" + toDate + "')";
            String stat3 = "";
            if(gananciaNetaButton.isSelected()) {
                stat3 = " AND s.idVenta.size > 0";
            }
            List<sitio> previewRows = em.createQuery("FROM sitio s WHERE (" + stat + "(s.arrendamiento.fechaInicioArrendamiento BETWEEN '" + fromDate + "' AND '" + toDate + "') OR " + stat2 + ") " + stat3).getResultList();
            
            previewRows.forEach((row) -> {
                dinamicList.add(new CustomTable(row, realMonthValue, primerPagoLicencia.isSelected()));
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
    
    public void prepareToClose() {
        root.getScene().getWindow().setOnCloseRequest(event -> {
            emf.close();
        });
    }
}
