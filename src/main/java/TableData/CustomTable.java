package TableData;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.time.LocalDate;

/**
 *
 * @author hardel
 */
public class CustomTable extends RecursiveTreeObject<CustomTable>{
    //Campos posibles de sitio
    private String cliente;
    private String lugar;
    
    private Float costoAlcaldia;
    private Float costoArrendamiento;
    private String nombreArrendatario;
    private Float latitud;
    private Float longitud;
    private Float alturaDisponible;
    private String disponible;
    //Campos posibles de oferta
    private Float monto;
    private Float alturaSolicitada;
    private LocalDate fechaOferta;
    private String estadoOferta;
    //Campos posibles de venta(oferta -> completada)
    /*private LocalDate fechaInicio;
    private LocalDate fechaFin;*/
    
    
    
    //Preguntar
    /*private LocalDate fechaRetiroLlave;
    private LocalDate fechaDevolucionLlave;
    private String nombreRetiraLlave;*/

    public CustomTable() { }

    public String getLugar() {
        return lugar;
    }

    public Float getCostoAlcaldia() {
        return costoAlcaldia;
    }

    public Float getCostoArrendamiento() {
        return costoArrendamiento;
    }

    public String getEstadoOferta() {
        return estadoOferta;
    }

    public String getNombreArrendatario() {
        return nombreArrendatario;
    }

    public Float getLatitud() {
        return latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public Float getAlturaDisponible() {
        return alturaDisponible;
    }

    public String getDisponible() {
        return disponible;
    }

    public Float getMonto() {
        return monto;
    }

    public Float getAlturaSolicitada() {
        return alturaSolicitada;
    }

    public LocalDate getFechaOferta() {
        return fechaOferta;
    }

    /*public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }*/

    public String getCliente() {
        return cliente;
    }

    /*public LocalDate getFechaRetiroLlave() {
        return fechaRetiroLlave;
    }

    public LocalDate getFechaDevolucionLlave() {
        return fechaDevolucionLlave;
    }

    public String getNombreRetiraLlave() {
        return nombreRetiraLlave;
    }*/

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setCostoAlcaldia(Float costoAlcaldia) {
        this.costoAlcaldia = costoAlcaldia;
    }

    public void setCostoArrendamiento(Float costoArrendamiento) {
        this.costoArrendamiento = costoArrendamiento;
    }

    public void setNombreArrendatario(String nombreArrendatario) {
        this.nombreArrendatario = nombreArrendatario;
    }

    public void setEstadoOferta(String estadoOferta) {
        this.estadoOferta = estadoOferta;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public void setAlturaDisponible(Float alturaDisponible) {
        this.alturaDisponible = alturaDisponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public void setAlturaSolicitada(Float alturaSolicitada) {
        this.alturaSolicitada = alturaSolicitada;
    }

    public void setFechaOferta(LocalDate fechaOferta) {
        this.fechaOferta = fechaOferta;
    }

    /*public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }*/

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /*public void setFechaRetiroLlave(LocalDate fechaRetiroLlave) {
        this.fechaRetiroLlave = fechaRetiroLlave;
    }

    public void setFechaDevolucionLlave(LocalDate fechaDevolucionLlave) {
        this.fechaDevolucionLlave = fechaDevolucionLlave;
    }

    public void setNombreRetiraLlave(String nombreRetiraLlave) {
        this.nombreRetiraLlave = nombreRetiraLlave;
    }*/
}
