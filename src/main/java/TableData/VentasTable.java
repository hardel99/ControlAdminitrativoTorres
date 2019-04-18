package TableData;

import java.time.LocalDate;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

/**
 *
 * @author hardel
 */
public class VentasTable extends MainOfferTable {
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final FloatProperty canonActual;

    public VentasTable(long idOferta, char estado, String sitio, String cliente, float altura, float monto, String fecha, float alturaDis, String path, LocalDate fechaInicio, LocalDate fechaFin, Float canonActual) {
        super(idOferta, estado, sitio, cliente, altura, monto, fecha, alturaDis, path);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.canonActual = new SimpleFloatProperty(canonActual);
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Float getCanonActual() {
        return canonActual.get();
    }
}
