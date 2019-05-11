package TableData;

import Entitys.venta;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

/**
 *
 * @author hardel
 */
public class CustomTable extends RecursiveTreeObject<CustomTable> {
    private final DecimalFormat df = new DecimalFormat("##.##");
    private String cliente;
    private String lugar;
    private Float total = 0f;
    
    private List<FloatProperty> month = new ArrayList();

    public CustomTable(venta v, LocalDate inicio, LocalDate finPeriodo, int numeroMeses) {
        df.setRoundingMode(RoundingMode.CEILING);
        this.cliente = v.getClienteV().getNombre();
        this.lugar = v.getSitioV().getNombre();
        
        float monto = v.getOfertaVenta().getMonto();
        float can = v.getOfertaVenta().getCanon();
        int transcurred = Period.between(v.getFechaInicio(), finPeriodo).getYears();
        for(int i=0; i < numeroMeses; i++) {
            Period p;
            if(inicio.plusMonths(i).getMonth() == v.getFechaInicio().getMonth()) {
                transcurred++;
            }
            
            if(finPeriodo.isAfter(v.getFechaFin())) {
                //Contrato expira enmedio del periodo solicitado
                p = Period.between(v.getFechaFin(), finPeriodo);
                
                if(i <= p.getMonths()) {
                    month.add(new SimpleFloatProperty(calculateCanon(monto, can, transcurred)));
                } else{
                    month.add(new SimpleFloatProperty(0));
                }
            } else if(v.getFechaInicio().isAfter(inicio)) {
                //Contrato inicia en medio del periodo solicitado
                p = Period.between(inicio, v.getFechaInicio());
                
                if(i > p.getMonths()) {
                    month.add(new SimpleFloatProperty(calculateCanon(monto, can, transcurred)));
                } else{
                    month.add(new SimpleFloatProperty(0));
                }
            } else{
                month.add(new SimpleFloatProperty(calculateCanon(monto, can, transcurred)));
            }
        }
        
        month.forEach(mont -> {
            System.out.println("MONTO " + mont.get());
            this.total = this.total + mont.get();
        });
        total = Float.parseFloat(df.format(total));
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public Float getTotal() {
        return total;
    }

    public List<FloatProperty> getMonth() {
        return month;
    }
    
    //Calculate monto in the real time, later quet the specific period
    private float calculateCanon(float monto, float canon, int numYear) {
        for(int i = 1; i < numYear; i++) {
            monto += (monto * (canon/100));
        }
        
        String s = df.format(monto);
        return Float.parseFloat(s);
    }
}
