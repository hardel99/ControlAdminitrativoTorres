package Entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hardel
 */
@Entity
@Table(name="Sitio")
public class sitio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_Sitio")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Nombre")
    private String nombre;
    
    @Column(name="Latitud")
    private float latitud;
    
    @Column(name="Longitud")
    private float longitud;
    
    @Lob
    @Column(name = "Comentario")
    private String coment;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="ID_Torre")
    private torre torre;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="Id_Arrendamiento")
    private arrendamiento arrendamiento;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="ID_Licencia")
    private licencia licencia;
    
    @OneToMany(mappedBy = "sitioV", cascade = {CascadeType.ALL})
    private List<venta> idVenta = new ArrayList<venta>();
    
    @OneToMany(mappedBy = "locacion", cascade = {CascadeType.ALL})
    private List<oferta> idOferta = new ArrayList<oferta>();

    public sitio(Long id, String nombre, float latitud, float longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    public sitio(){
        //TODO
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLatitud() {
        return latitud;
    }
    
    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }
    
    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public torre getTorre() {
        return torre;
    }

    public void setTorre(torre torre) {
        this.torre = torre;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public arrendamiento getArrendamiento() {
        return arrendamiento;
    }

    public licencia getLicencia() {
        return licencia;
    }

    public List<venta> getIdVenta() {
        return idVenta;
    }

    public List<oferta> getIdOferta() {
        return idOferta;
    }

    public void setArrendamiento(arrendamiento arrendamiento) {
        this.arrendamiento = arrendamiento;
    }

    public void setLicencia(licencia licencia) {
        this.licencia = licencia;
    }

    public void setIdVenta(List<venta> idVenta) {
        this.idVenta = idVenta;
    }

    public void setIdOferta(List<oferta> idOferta) {
        this.idOferta = idOferta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.nombre);
        hash = 17 * hash + Float.floatToIntBits(this.latitud);
        hash = 17 * hash + Float.floatToIntBits(this.longitud);
        hash = 17 * hash + Objects.hashCode(this.torre);
        hash = 17 * hash + Objects.hashCode(this.arrendamiento);
        hash = 17 * hash + Objects.hashCode(this.licencia);
        hash = 17 * hash + Objects.hashCode(this.idVenta);
        hash = 17 * hash + Objects.hashCode(this.idOferta);
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
        final sitio other = (sitio) obj;
        if (Float.floatToIntBits(this.latitud) != Float.floatToIntBits(other.latitud)) {
            return false;
        }
        if (Float.floatToIntBits(this.longitud) != Float.floatToIntBits(other.longitud)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.torre, other.torre)) {
            return false;
        }
        if (!Objects.equals(this.arrendamiento, other.arrendamiento)) {
            return false;
        }
        if (!Objects.equals(this.licencia, other.licencia)) {
            return false;
        }
        if (!Objects.equals(this.idVenta, other.idVenta)) {
            return false;
        }
        if (!Objects.equals(this.idOferta, other.idOferta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sitio{" + "id=" + id + ", nombre=" + nombre + ", latitud=" + latitud + ", longitud=" + longitud + ", torre=" + torre.getId() + ", arrendamiento=" + arrendamiento + ", licencia=" + licencia + '}';
    }
}
