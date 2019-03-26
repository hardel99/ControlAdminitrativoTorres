package TableData;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hardel
 */
public class MainOfferTable extends RecursiveTreeObject<MainOfferTable>{
    private final long idOferta;
    private final StringProperty sitio;
    private final StringProperty cliente;
    private final FloatProperty altura;
    private final StringProperty fecha;
    private final FloatProperty alturaDis;

    public MainOfferTable(long idOferta, String sitio, String cliente, float altura, String fecha, float alturaDis) {
        this.idOferta = idOferta;
        this.sitio = new SimpleStringProperty(sitio);
        this.cliente = new SimpleStringProperty(cliente);
        this.altura = new SimpleFloatProperty(altura);
        this.fecha = new SimpleStringProperty(fecha);
        this.alturaDis = new SimpleFloatProperty(alturaDis);
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

    public Float getAlturaDis() {
        return alturaDis.get();
    }

    public long getIdOferta() {
        return idOferta;
    }
}
