package TableData;

import Entitys.venta;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hardel
 */
public class VentasTable extends RecursiveTreeObject<VentasTable> {
    private final long id;
    private final StringProperty cliente;
    private final StringProperty sitio;
    private final IntegerProperty duracion;
    private final IntegerProperty ejecutado;
    private final IntegerProperty restante;
    private final FloatProperty canon;
    private final FloatProperty porcentaje;
    private final StringProperty desde;
    private final StringProperty hasta;

    public VentasTable(long id, StringProperty cliente, StringProperty sitio, IntegerProperty duracion, IntegerProperty ejecutado, IntegerProperty restante, FloatProperty canon, FloatProperty porcentaje, StringProperty desde, StringProperty hasta) {
        this.id = id;
        this.cliente = cliente;
        this.sitio = sitio;
        this.duracion = duracion;
        this.ejecutado = ejecutado;
        this.restante = restante;
        this.canon = canon;
        this.porcentaje = porcentaje;
        this.desde = desde;
        this.hasta = hasta;
    }

    public VentasTable(venta venta) {
        Period p = Period.between(venta.getFechaInicio(), LocalDate.now());
        this.id = venta.getOfertaVenta().getId();
        this.cliente = new SimpleStringProperty(venta.getClienteV().getNombre());
        this.sitio = new SimpleStringProperty(venta.getSitioV().getNombre());
        this.duracion = new SimpleIntegerProperty(venta.getFechaFin().getYear() - venta.getFechaInicio().getYear());
        this.ejecutado = new SimpleIntegerProperty(p.getYears());
        this.restante = new SimpleIntegerProperty(Period.between(LocalDate.now(), venta.getFechaFin()).getYears());
        this.canon = new SimpleFloatProperty(calcActualCanon(venta.getOfertaVenta().getCanon(), venta.getOfertaVenta().getMonto(), p.getYears()));
        this.porcentaje = new SimpleFloatProperty(venta.getOfertaVenta().getCanon());
        this.desde = new SimpleStringProperty(venta.getFechaInicio().format(DateTimeFormatter.ofPattern("uuuu-MM-d")));
        this.hasta = new SimpleStringProperty(venta.getFechaFin().format(DateTimeFormatter.ofPattern("uuuu-MM-d")));
    }

    public long getId() {
        return id;
    }

    public String getCliente() {
        return cliente.get();
    }

    public String getSitio() {
        return sitio.get();
    }

    public Integer getDuracion() {
        return duracion.get();
    }

    public Integer getEjecutado() {
        return ejecutado.get();
    }

    public Integer getRestante() {
        return restante.get();
    }

    public Float getCanon() {
        return canon.get();
    }

    public Float getPorcentaje() {
        return porcentaje.get();
    }

    public String getDesde() {
        return desde.get();
    }

    public String getHasta() {
        return hasta.get();
    }
    //offset of 1 year plus here
    private float calcActualCanon(float canon, float monto, int yearDifference) {
        final DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.CEILING);
        
        if(yearDifference > 0) {
            for(int i = 0; i < yearDifference - 1; i++) {
                monto += (monto * (canon/100));
            }
        }
        
        return Float.valueOf(df.format(monto));
    }
}
