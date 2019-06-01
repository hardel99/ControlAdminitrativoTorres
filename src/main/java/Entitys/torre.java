package Entitys;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hardel
 */
@Entity
@Table(name="Torre")
public class torre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Torre")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Altura_Solicitada")
    private float alturaPedida;
    
    @Column(name="Cotizacion")
    private String cotizacion;
    
    @OneToOne(mappedBy="torre", fetch = FetchType.LAZY)
    private sitio localidad;
    
    /*@ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_antena")
    private antena antenaT;*/
    
    @ManyToMany(cascade=CascadeType.ALL)
    //@JoinColumn(name = "Cliente")
    private List<cliente> clienteT;
    
    public torre(){
        //TODO
    }

    /*public torre(Long id, float alturaPedida, String cotizacion, sitio localidad, antena antenaT) {
        this.id = id;
        this.alturaPedida = alturaPedida;
        this.cotizacion = cotizacion;
        this.localidad = localidad;
        //this.antenaT = antenaT;
    }*/
    
    public torre(float alturaPedida, String cotizacion) {
        this.alturaPedida = alturaPedida;
        this.cotizacion = cotizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAlturaPedida() {
        return alturaPedida;
    }

    public String getCotizacion() {
        return cotizacion;
    }

    public sitio getLocalidad() {
        return localidad;
    }

    public void setAlturaPedida(float alturaPedida) {
        this.alturaPedida = alturaPedida;
    }

    public void setCotizacion(String cotizacion) {
        this.cotizacion = cotizacion;
    }

    public void setLocalidad(sitio localidad) {
        this.localidad = localidad;
    }

    /*public antena getAntenaT() {
        return antenaT;
    }

    public void setAntenaT(antena antenaT) {
        this.antenaT = antenaT;
    }*/

    public List<cliente> getClienteT() {
        return clienteT;
    }

    public void setClienteT(List<cliente> clienteT) {
        this.clienteT = clienteT;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Float.floatToIntBits(this.alturaPedida);
        hash = 37 * hash + Objects.hashCode(this.cotizacion);
        hash = 37 * hash + Objects.hashCode(this.localidad);
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
        final torre other = (torre) obj;
        if (Float.floatToIntBits(this.alturaPedida) != Float.floatToIntBits(other.alturaPedida)) {
            return false;
        }
        if (!Objects.equals(this.cotizacion, other.cotizacion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "torre{" + "id=" + id + ", alturaPedida=" + alturaPedida + ", localidad=" + localidad + '}';
    }
}
