package TableData;

import Entitys.llave;
import Entitys.oferta;
import Entitys.torre;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hardel
 */
public class ClientesTable extends RecursiveTreeObject<ClientesTable> {
    private final long id;
    private final StringProperty nombre;
    private final IntegerProperty cantidadAntenas;
    private final IntegerProperty cantidadOfertas;
    private final IntegerProperty cantidadLlaves;
    private final List<llave> llaves;
    private final List<torre> torres;
    private final List<oferta> ofertas;

    public ClientesTable(long id, String nombre, int cantidadAntenas, int cantidadOfertas, int cantidadLlaves, List<llave> llaves, List<torre> torres, List<oferta> ofertas) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidadAntenas = new SimpleIntegerProperty(cantidadAntenas);
        this.cantidadOfertas = new SimpleIntegerProperty(cantidadOfertas);
        this.cantidadLlaves = new SimpleIntegerProperty(cantidadLlaves);
        
        this.llaves = llaves;
        this.torres = torres;
        this.ofertas = ofertas;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public Integer getCantidadAntenas() {
        return cantidadAntenas.get();
    }

    public Integer getCantidadOfertas() {
        return cantidadOfertas.get();
    }

    public Integer getCantidadLlaves() {
        return cantidadLlaves.get();
    }

    public List<llave> getLlaves() {
        return llaves;
    }

    public List<torre> getTorres() {
        return torres;
    }

    public List<oferta> getOfertas() {
        return ofertas;
    }
}
