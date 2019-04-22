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
@Table(name="Licencia")
public class licencia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Licencia")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Monto")
    private float monto;
    
    @Column(name="Documento")
    private String documentPath;
    
    @OneToOne(mappedBy="licencia", fetch = FetchType.LAZY)
    private sitio sitioLic;

    public licencia() {
        //TODO
    }

    public licencia( float monto, String documentPath) {
        this.monto = monto;
        this.documentPath = documentPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMonto() {
        return monto;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public sitio getSitioLic() {
        return sitioLic;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public void setSitioLic(sitio sitioLic) {
        this.sitioLic = sitioLic;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Float.floatToIntBits(this.monto);
        hash = 43 * hash + Objects.hashCode(this.documentPath);
        hash = 43 * hash + Objects.hashCode(this.sitioLic);
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
        final licencia other = (licencia) obj;
        if (Float.floatToIntBits(this.monto) != Float.floatToIntBits(other.monto)) {
            return false;
        }
        if (!Objects.equals(this.documentPath, other.documentPath)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sitioLic, other.sitioLic)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "licencia{" + "id=" + id + ", monto=" + monto + ", documentPath=" + documentPath + ", sitioLic=" + sitioLic.getId() + '}';
    }

}
