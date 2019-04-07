package Entitys;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
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
@Table(name="Oferta")
public class oferta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Oferta")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Altura")
    private float alturaTorre;
    
    @Column(name="Fecha")
    private LocalDate Fecha;
    
    @Column(name="Imagen")
    private String imagenRuta;
    
    @Column(name="Estado")
    private char estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_sitio")
    private sitio locacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente")
    private cliente clienteOf;
    
    @OneToOne(mappedBy = "ofertaVenta", fetch = FetchType.LAZY)
    private venta ventaO;

    public oferta(){
        //TODO
    }

    public oferta(Long id, float alturaTorre, LocalDate Fecha, String imagenRuta, char estado, sitio locacion, cliente clienteOf) {
        this.id = id;
        this.alturaTorre = alturaTorre;
        this.Fecha = Fecha;
        this.imagenRuta = imagenRuta;
        this.estado = estado;
        this.locacion = locacion;
        this.clienteOf = clienteOf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAlturaTorre() {
        return alturaTorre;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setAlturaTorre(float alturaTorre) {
        this.alturaTorre = alturaTorre;
    }

    public void setFecha(LocalDate Fecha) {
        this.Fecha = Fecha;
    }

    public String getImagenRuta() {
        return imagenRuta;
    }

    public void setImagenRuta(String imagenRuta) {
        this.imagenRuta = imagenRuta;
    }

    public venta getVentaO() {
        return ventaO;
    }

    public void setVentaO(venta ventaO) {
        this.ventaO = ventaO;
    }

    public sitio getLocacion() {
        return locacion;
    }

    public void setLocacion(sitio locacion) {
        this.locacion = locacion;
    }

    public cliente getClienteOf() {
        return clienteOf;
    }

    public void setClienteOf(cliente clienteOf) {
        this.clienteOf = clienteOf;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Float.floatToIntBits(this.alturaTorre);
        hash = 59 * hash + Objects.hashCode(this.Fecha);
        hash = 59 * hash + Objects.hashCode(this.imagenRuta);
        hash = 59 * hash + Objects.hashCode(this.locacion);
        hash = 59 * hash + Objects.hashCode(this.clienteOf);
        hash = 59 * hash + Objects.hashCode(this.ventaO);
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
        final oferta other = (oferta) obj;
        if (Float.floatToIntBits(this.alturaTorre) != Float.floatToIntBits(other.alturaTorre)) {
            return false;
        }
        if (!Objects.equals(this.imagenRuta, other.imagenRuta)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.Fecha, other.Fecha)) {
            return false;
        }
        if (!Objects.equals(this.locacion, other.locacion)) {
            return false;
        }
        if (!Objects.equals(this.clienteOf, other.clienteOf)) {
            return false;
        }
        if (!Objects.equals(this.ventaO, other.ventaO)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "oferta{" + "id=" + id + ", alturaTorre=" + alturaTorre + ", Fecha=" + Fecha + ", imagenRuta=" + imagenRuta + ", locacion=" + locacion.getId() + ", clienteOf=" + clienteOf.getId() + ", ventaO=" + ventaO + '}';
    }
}
