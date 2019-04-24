package TableData;

import Entitys.oferta;
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
    private StringProperty estado;
    private final StringProperty sitio;
    private final StringProperty cliente;
    private final FloatProperty altura;
    private final FloatProperty monto;
    private final StringProperty fecha;
    private final FloatProperty alturaDis;
    private final String imagePath;

    public MainOfferTable(long idOferta, char estado, String sitio, String cliente, float altura, float monto, String fecha, float alturaDis, String path) {
        this.idOferta = idOferta;
        this.sitio = new SimpleStringProperty(sitio);
        this.cliente = new SimpleStringProperty(cliente);
        this.monto = new SimpleFloatProperty(monto);
        this.altura = new SimpleFloatProperty(altura);
        this.fecha = new SimpleStringProperty(fecha);
        this.alturaDis = new SimpleFloatProperty(alturaDis);
        this.imagePath = path;
        
        switch (estado) {
            case 'I':
                this.estado = new SimpleStringProperty("Incompleto");
                break;
            case 'P':
                this.estado = new SimpleStringProperty("Pendiente");
                break;
            case 'C':
                this.estado = new SimpleStringProperty("Completado");
                break;
            default:
                break;
        }
    }

    public MainOfferTable(oferta oferta) {
        this.idOferta = oferta.getId();
        this.sitio = new SimpleStringProperty(oferta.getLocacion().getNombre());
        this.cliente = new SimpleStringProperty(oferta.getClienteOf().getNombre());
        this.monto = new SimpleFloatProperty(oferta.getMonto());
        this.altura = new SimpleFloatProperty(oferta.getAlturaTorre());
        this.fecha = new SimpleStringProperty(oferta.getFecha().toString());
        this.alturaDis = new SimpleFloatProperty(oferta.getLocacion().getTorre().getAlturaPedida());
        this.imagePath = oferta.getImagenRuta();
        
        switch (oferta.getEstado()) {
            case 'I':
                this.estado = new SimpleStringProperty("Incompleto");
                break;
            case 'P':
                this.estado = new SimpleStringProperty("Pendiente");
                break;
            case 'C':
                this.estado = new SimpleStringProperty("Completado");
                break;
            default:
                break;
        }
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

    public String getEstado() {
        return estado.get();
    }

    public String getImagePath() {
        return imagePath;
    }

    public Float getMonto() {
        return monto.get();
    }
}
