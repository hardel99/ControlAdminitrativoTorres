package TableData;

import Entitys.llave;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hardel
 */
public class LlavesTable {
    private final long id;
    private final StringProperty personaReceptor;
    private final StringProperty cliente;
    private final StringProperty sitio;
    private final IntegerProperty cantidadLlaves;
    private final StringProperty subempresa;
    private final LocalDate fechaRetiro;
    private final LocalDate fechaDevolucion;

    public LlavesTable(long id, String personaReceptor, String cliente, String sitio, int cantidadLlaves, String subempresa, LocalDate fechaRetiro, LocalDate fechaDevolucion) {
        this.id = id;
        this.personaReceptor = new SimpleStringProperty(personaReceptor);
        this.cliente = new SimpleStringProperty(cliente);
        this.sitio = new SimpleStringProperty(sitio);
        this.cantidadLlaves = new SimpleIntegerProperty(cantidadLlaves);
        this.subempresa = new SimpleStringProperty(subempresa);
        this.fechaRetiro = fechaRetiro;
        this.fechaDevolucion = fechaDevolucion;
    }

    public LlavesTable(llave llave) {
        this.id = llave.getId();
        this.personaReceptor = new SimpleStringProperty(llave.getNombreP());
        this.cliente = new SimpleStringProperty(llave.getClienteY().getNombre());
        this.cantidadLlaves = new SimpleIntegerProperty(llave.getCantidadLlaves());
        this.sitio = new SimpleStringProperty(llave.getSitioY().getNombre());
        this.subempresa = new SimpleStringProperty(llave.getSubempresa());
        this.fechaRetiro = llave.getFechaRetiro();
        this.fechaDevolucion = llave.getFechaDevolucion();
    }

    public long getId() {
        return id;
    }

    public String getPersonaReceptor() {
        return personaReceptor.get();
    }

    public String getCliente() {
        return cliente.get();
    }

    public int getCantidadLlaves() {
        return cantidadLlaves.get();
    }

    public String getSubempresa() {
        return subempresa.get();
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getSitio() {
        return sitio.get();
    }
}
