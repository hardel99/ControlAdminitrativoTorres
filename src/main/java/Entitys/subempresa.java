package Entitys;

import java.io.Serializable;
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
@Table(name="Subempresa")
public class subempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Subempresa")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="nombre")
    private String nombre;
    
    @OneToOne(mappedBy = "subempresa", fetch = FetchType.LAZY)
    private llave llaveS;

    public subempresa(String nombre) {
        this.nombre = nombre;
    }

    public subempresa() {
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

    public llave getLlaveS() {
        return llaveS;
    }

    public void setLlaveS(llave llaveS) {
        this.llaveS = llaveS;
    }

    @Override
    public String toString() {
        return "subempresa{" + "id=" + id + ", nombre=" + nombre + '}';
    }
}
