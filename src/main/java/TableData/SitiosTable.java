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
public class SitiosTable extends RecursiveTreeObject<SitiosTable>{
    private final long id;
    private final StringProperty nombre;
    private final FloatProperty latitud;
    private final FloatProperty longitud;
    private final FloatProperty alturaDisponible;
    private final StringProperty disponible;
    private final FloatProperty costosAlcadia;
    private final String documentoAlcaldia;
    private final FloatProperty costosArrendamiento;
    private final String documentoArrendamiento;
    private final String coment;

    public SitiosTable(long id, String nombre, float latitud, float longitud, float alturaDisponible, String disponible, float costosAlcadia, String docAlcaldia, float costosArrendamiento, String docArrendamiento, String comentario) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.latitud = new SimpleFloatProperty(latitud);
        this.longitud = new SimpleFloatProperty(longitud);
        this.alturaDisponible = new SimpleFloatProperty(alturaDisponible);
        this.disponible = new SimpleStringProperty(disponible);
        this.costosAlcadia = new SimpleFloatProperty(costosAlcadia);
        this.costosArrendamiento = new SimpleFloatProperty(costosArrendamiento);
        this.coment = comentario;
        this.documentoAlcaldia = docAlcaldia;
        this.documentoArrendamiento = docArrendamiento;
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

    public String getDisponible() {
        return disponible.get();
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
}
