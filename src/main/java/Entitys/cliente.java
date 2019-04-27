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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author hardel
 */
@Entity
@Table(name="Cliente")
public class cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Cliente")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Nombre")
    private String nombre;
    
    @OneToMany(mappedBy="clienteV", cascade = {CascadeType.ALL})
    private List<venta> ventaC = new ArrayList<venta>();
    
    @OneToMany(mappedBy="clienteOf", cascade = {CascadeType.ALL})
    private List<oferta> ofertaC = new ArrayList<oferta>();
    
    @OneToMany(mappedBy="clienteY", cascade = {CascadeType.ALL})
    private List<llave> llaveC = new ArrayList<llave>();
    
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="ID_Torre")
    private List<torre> torreC;
    
    public cliente(){
        //TODO
    }
    
    public cliente(String nombre) {
        this.nombre = nombre;
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

    public List<venta> getVentaC() {
        return ventaC;
    }

    public List<oferta> getOfertaC() {
        return ofertaC;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<llave> getLlaveC() {
        return llaveC;
    }

    public void setVentaC(List<venta> ventaC) {
        this.ventaC = ventaC;
    }

    public void setOfertaC(List<oferta> ofertaC) {
        this.ofertaC = ofertaC;
    }

    public void setLlaveC(List<llave> llaveC) {
        this.llaveC = llaveC;
    }
    
    public List<torre> getTorreC() {
        return torreC;
    }

    public void setTorreC(List<torre> torreC) {
        this.torreC = torreC;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.nombre);
        hash = 41 * hash + Objects.hashCode(this.ventaC);
        hash = 41 * hash + Objects.hashCode(this.ofertaC);
        hash = 41 * hash + Objects.hashCode(this.llaveC);
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
        final cliente other = (cliente) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ventaC, other.ventaC)) {
            return false;
        }
        if (!Objects.equals(this.ofertaC, other.ofertaC)) {
            return false;
        }
        if (!Objects.equals(this.llaveC, other.llaveC)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cliente{" + "id=" + id + ", nombre=" + nombre + '}';
    }
}
