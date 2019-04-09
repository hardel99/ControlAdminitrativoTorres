//package Entitys;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
///**
// *
// * @author hardel
// */
//@Entity
//@Table(name="Antena")
//public class antena implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    
//    @Id
//    @Column(name="ID_Antena")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    
//    @OneToMany(mappedBy="antenaC", cascade = {CascadeType.ALL})
//    private List<cliente> clienteAn = new ArrayList<cliente>();
//    
//    @OneToMany(mappedBy="antenaT", cascade = {CascadeType.ALL})
//    private List<torre> torreAn = new ArrayList<torre>();
//    
//    public antena(){
//        //TODO
//    }
//    
//    public antena(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<cliente> getClienteAn() {
//        return clienteAn;
//    }
//
//    public List<torre> getTorreAn() {
//        return torreAn;
//    }
//
//    @Override
//    public String toString() {
//        return "antena{" + "id=" + id + '}';
//    }
//}
