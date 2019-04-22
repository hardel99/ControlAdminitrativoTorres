package Entitys;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hardel
 */
@Entity
@Table(name="Arrendamiento")
public class arrendamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Arrendamiento")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Costo")
    private float costo;
    
    @Column(name="Nombre_Arrendatario")
    private String nombreArrendatario;
    
    @Column(name="NIT")
    private String NIT;
    
    @Column(name="DUI")
    private String DUI;
    
    @Column(name="Documento")
    private String documentPath;
    
    @OneToOne(mappedBy="arrendamiento", fetch = FetchType.LAZY)
    private sitio sitioArrendado;

    public arrendamiento() {
    }

    public arrendamiento(float costo, String nombreArrendatario, String NIT, String DUI, String document) {
        this.costo = costo;
        this.nombreArrendatario = nombreArrendatario;
        this.NIT = NIT;
        this.DUI = DUI;
        this.documentPath = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getCosto() {
        return costo;
    }

    public String getNombreArrendatario() {
        return nombreArrendatario;
    }

    public String getNIT() {
        return NIT;
    }

    public String getDUI() {
        return DUI;
    }

    public sitio getSitioArrendado() {
        return sitioArrendado;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setNombreArrendatario(String nombreArrendatario) {
        this.nombreArrendatario = nombreArrendatario;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public void setDUI(String DUI) {
        this.DUI = DUI;
    }

    public void setSitioArrendado(sitio sitioArrendado) {
        this.sitioArrendado = sitioArrendado;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public String toString() {
        return "arrendamiento{" + "id=" + id + ", costo=" + costo + ", nombreArrendatario=" + nombreArrendatario + ", NIT=" + NIT + ", DUI=" + DUI + ", sitioArrendado=" + sitioArrendado.getId() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Float.floatToIntBits(this.costo);
        hash = 59 * hash + Objects.hashCode(this.nombreArrendatario);
        hash = 59 * hash + Objects.hashCode(this.NIT);
        hash = 59 * hash + Objects.hashCode(this.DUI);
        hash = 59 * hash + Objects.hashCode(this.sitioArrendado);
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
        final arrendamiento other = (arrendamiento) obj;
        if (Float.floatToIntBits(this.costo) != Float.floatToIntBits(other.costo)) {
            return false;
        }
        if (!Objects.equals(this.nombreArrendatario, other.nombreArrendatario)) {
            return false;
        }
        if (!Objects.equals(this.NIT, other.NIT)) {
            return false;
        }
        if (!Objects.equals(this.DUI, other.DUI)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sitioArrendado, other.sitioArrendado)) {
            return false;
        }
        return true;
    }
}
