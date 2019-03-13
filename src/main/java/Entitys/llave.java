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
import javax.persistence.Table;

/**
 *
 * @author hardel
 */
@Entity
@Table(name="Llave")
public class llave implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="ID_Llave")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="Nombre_Persona")
    private String nombreP;
    
    @Column(name="Telefono")
    private String telefono;
    
    @Column(name="DUI")
    private String DUI;
    
    @Column(name="Fecha_Retiro")
    private LocalDate fechaRetiro;
    
    @Column(name="Fecha_Devolucion")
    private LocalDate fechaDevolucion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente")
    private cliente clienteY;

    public llave() {
        //TODO
    }

    public llave(Long id, String nombreP, String telefono, String DUI, LocalDate fechaRetiro, LocalDate fechaDevolucion) {
        this.id = id;
        this.nombreP = nombreP;
        this.telefono = telefono;
        this.DUI = DUI;
        this.fechaRetiro = fechaRetiro;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreP() {
        return nombreP;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDUI() {
        return DUI;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public cliente getClienteY() {
        return clienteY;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDUI(String DUI) {
        this.DUI = DUI;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setClienteY(cliente clienteY) {
        this.clienteY = clienteY;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.nombreP);
        hash = 41 * hash + Objects.hashCode(this.telefono);
        hash = 41 * hash + Objects.hashCode(this.DUI);
        hash = 41 * hash + Objects.hashCode(this.fechaRetiro);
        hash = 41 * hash + Objects.hashCode(this.fechaDevolucion);
        hash = 41 * hash + Objects.hashCode(this.clienteY);
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
        final llave other = (llave) obj;
        if (!Objects.equals(this.nombreP, other.nombreP)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.DUI, other.DUI)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fechaRetiro, other.fechaRetiro)) {
            return false;
        }
        if (!Objects.equals(this.fechaDevolucion, other.fechaDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.clienteY, other.clienteY)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "llave{" + "id=" + id + ", nombreP=" + nombreP + ", telefono=" + telefono + ", DUI=" + DUI + ", fechaRetiro=" + fechaRetiro + ", fechaDevolucion=" + fechaDevolucion + ", clienteY=" + clienteY.getId() + '}';
    }
}
