package Entitys;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hardel
 */
@Entity
@Table(name="Venta")
public class venta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Venta")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Inicio")
    private LocalDate fechaInicio;
    
    @Column(name="Final")
    private LocalDate fechaFin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_sitio")
    private sitio sitioV;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente")
    private cliente clienteV;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="id_oferta")
    private oferta ofertaVenta;
    
    public venta(){
        //TODO
    }

    public venta(Long id, LocalDate fechaInicio, LocalDate fechaFin, sitio sitioV, cliente clienteV) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sitioV = sitioV;
        this.clienteV = clienteV;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public sitio getSitioV() {
        return sitioV;
    }

    public void setSitioV(sitio sitioV) {
        this.sitioV = sitioV;
    }

    public oferta getOfertaVenta() {
        return ofertaVenta;
    }

    public void setOfertaVenta(oferta ofertaVenta) {
        this.ofertaVenta = ofertaVenta;
    }

    public void setClienteV(cliente clienteV) {
        this.clienteV = clienteV;
    }

    public cliente getClienteV() {
        return clienteV;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.fechaInicio);
        hash = 73 * hash + Objects.hashCode(this.fechaFin);
        hash = 73 * hash + Objects.hashCode(this.sitioV);
        hash = 73 * hash + Objects.hashCode(this.clienteV);
        hash = 73 * hash + Objects.hashCode(this.ofertaVenta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final venta other = (venta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaFin, other.fechaFin)) {
            return false;
        }
        if (!Objects.equals(this.sitioV, other.sitioV)) {
            return false;
        }
        if (!Objects.equals(this.clienteV, other.clienteV)) {
            return false;
        }
        if (!Objects.equals(this.ofertaVenta, other.ofertaVenta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "venta{" + "id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", sitioV=" + sitioV.getId() + ", clienteV=" + clienteV.getId() + ", ofertaVenta=" + ofertaVenta + '}';
    }
}
