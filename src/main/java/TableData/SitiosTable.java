package TableData;

import Entitys.sitio;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
public class SitiosTable extends RecursiveTreeObject<SitiosTable>{
    private final long id;
    private final StringProperty nombre;
    private final FloatProperty latitud;
    private final FloatProperty longitud;
    private final FloatProperty alturaDisponible;
    private final FloatProperty costosAlcadia;
    private final Float costosLicencia;
    private final String documentoAlcaldia;
    private final FloatProperty costosArrendamiento;
    private final String documentoArrendamiento;
    private final String coment;
    private final String imagePath;
    private final boolean anual;
    private final IntegerProperty antena;

    public SitiosTable(long id, String nombre, float latitud, float longitud, int antena, float alturaDisponible, String disponible, float costosAlcadia, float costosLicencia, String docAlcaldia, float costosArrendamiento, String docArrendamiento, String comentario, String imagePath, boolean anual) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.latitud = new SimpleFloatProperty(latitud);
        this.longitud = new SimpleFloatProperty(longitud);
        this.alturaDisponible = new SimpleFloatProperty(alturaDisponible);
        this.costosAlcadia = new SimpleFloatProperty(costosAlcadia);
        this.costosLicencia = costosLicencia;
        this.costosArrendamiento = new SimpleFloatProperty(costosArrendamiento);
        this.coment = comentario;
        this.documentoAlcaldia = docAlcaldia;
        this.documentoArrendamiento = docArrendamiento;
        this.imagePath = imagePath;
        this.anual = anual;
        this.antena = new SimpleIntegerProperty(antena);
    }

    public SitiosTable(sitio sitio) {
        this.id = sitio.getId();
        this.nombre = new SimpleStringProperty(sitio.getNombre());
        this.latitud = new SimpleFloatProperty(sitio.getLatitud());
        this.longitud = new SimpleFloatProperty(sitio.getLongitud());
        this.alturaDisponible = new SimpleFloatProperty(sitio.getTorre().getAlturaPedida());
        this.costosAlcadia = new SimpleFloatProperty(sitio.getLicencia().getMonto());
        this.costosLicencia = sitio.getLicencia().getCostoLicencia();
        this.costosArrendamiento = new SimpleFloatProperty(sitio.getArrendamiento().getCosto());
        this.coment = sitio.getComent();
        this.documentoAlcaldia = sitio.getLicencia().getDocumentPath();
        this.documentoArrendamiento = sitio.getArrendamiento().getDocumentPath();
        this.imagePath = sitio.getImagePath();
        this.anual = sitio.getLicencia().isAnual();
        this.antena = new SimpleIntegerProperty(sitio.getIdVenta().size());
    }

    public String getNombre() {
        return nombre.get();
    }

    public Float getLatitud() {
        return latitud.get();
    }

    public Float getLongitud() {
        return longitud.get();
    }

    public Float getAlturaDisponible() {
        return alturaDisponible.get();
    }

    public Float getCostosAlcadia() {
        return costosAlcadia.get();
    }

    public Float getCostosArrendamiento() {
        return costosArrendamiento.get();
    }

    public long getId() {
        return id;
    }
    
    public String getComent() {
        return coment;
    }

    public String getDocumentoAlcaldia() {
        return documentoAlcaldia;
    }

    public String getDocumentoArrendamiento() {
        return documentoArrendamiento;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Float getCostosLicencia() {
        return costosLicencia;
    }

    public boolean isAnual() {
        return anual;
    }
}
