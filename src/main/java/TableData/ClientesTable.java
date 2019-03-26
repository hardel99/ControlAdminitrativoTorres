package TableData;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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

    public ClientesTable(long id, String nombre, int cantidadAntenas, int cantidadOfertas, int cantidadLlaves) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidadAntenas = new SimpleIntegerProperty(cantidadAntenas);
        this.cantidadOfertas = new SimpleIntegerProperty(cantidadOfertas);
        this.cantidadLlaves = new SimpleIntegerProperty(cantidadLlaves);
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
}
