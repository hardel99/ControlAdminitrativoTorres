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
    private final DecimalFormat df = new DecimalFormat("##.00");
    private String cliente;
    private String lugar;
    private Float total = 0f;
    private final LocalDate inicio;
    private final LocalDate vInit, vFin;
    private final List<FloatProperty> month = new ArrayList();

    public CustomTable(venta v, LocalDate inicio, LocalDate finPeriodo, int numeroMeses) {
        df.setRoundingMode(RoundingMode.CEILING);
        this.cliente = v.getClienteV().getNombre();
        this.lugar = v.getSitioV().getNombre();
        this.inicio = inicio;
        this.vInit = v.getFechaInicio().plusDays(1L);
        this.vFin = v.getFechaFin().plusDays(1L);
        
        float monto = v.getOfertaVenta().getMonto();
        float can = v.getOfertaVenta().getCanon();
        int transcurred = Period.between(vInit, finPeriodo).getYears();
        
        if(Period.between(inicio, finPeriodo).getYears() > 0) {
            //Por cada año extra hay que restar uno
            for(int i=0; i < Period.between(inicio, finPeriodo).getYears(); i++) {
                transcurred--;
            }
        } else{
            System.out.println("MENOS DE UN AÑO");
        }
        
        if(Period.between(vInit, inicio).getYears() == 0) {
            transcurred--;
        }
        
        Period p = Period.between(vInit, vFin);
        for(int i=0; i <= numeroMeses; i++) {
            
            if(inicio.plusMonths(i).getMonth() == vInit.getMonth()) {
                transcurred++;
            }
            float value = calculateCanon(monto, can, transcurred);
            
            if(finPeriodo.isAfter(vFin)) {
                //Contrato expira enmedio del periodo solicitado
                
                if(transcurred <= p.getYears()) {
                    if(vFin.getMonth() == this.inicio.plusMonths(i).getMonth() &&
                            vFin.getYear() == this.inicio.plusMonths(i).getYear()) {
                        
                        month.add(new SimpleFloatProperty(value - calculateDailyCanon(value, vInit.minusDays(1L))));
                    } else{
                        month.add(new SimpleFloatProperty(value));
                    }
                } else{
                    month.add(new SimpleFloatProperty(0));
                }
            } else if(vInit.isAfter(inicio)) {
                //Contrato inicia en medio del periodo solicitado
                
                if(transcurred >= p.getYears()) {
                    month.add(new SimpleFloatProperty(value));
                } else{
                    if(vInit.getMonth() == this.inicio.plusMonths(i).getMonth() && 
                            vInit.getYear() == this.inicio.plusMonths(i).getYear()) {
                        
                        month.add(new SimpleFloatProperty(calculateDailyCanon(value, vFin)));
                        System.out.println("THERE IT IS!!");
                    }
                    month.add(new SimpleFloatProperty(0));
                }
            } else{
                if(vInit.getMonth() == this.inicio.plusMonths(i).getMonth() && 
                        vInit.getYear() == this.inicio.plusMonths(i).getYear()) {
                    
                    month.add(new SimpleFloatProperty(calculateDailyCanon(value, vFin)));
                } else if(vFin.getMonth() == this.inicio.plusMonths(i).getMonth() &&
                        vFin.getYear() == this.inicio.plusMonths(i).getYear()) {
                    
                    month.add(new SimpleFloatProperty(value - calculateDailyCanon(value, vInit.minusDays(1L))));
                } else{
                    month.add(new SimpleFloatProperty(value));
                }
            }
        }
        
        month.forEach(mont -> {
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
    
    private float calculateCanon(float monto, float canon, int numYear) {
        for(int i = 0; i < numYear; i++) {
            monto += (monto * (canon/100));
        }
        
        String s = df.format(monto);
        return Float.parseFloat(s);
    }
    
    private float calculateDailyCanon(float monto, LocalDate ch){
        float costoDiario = monto/ch.lengthOfMonth();
        float realMonthValue = costoDiario * (ch.lengthOfMonth() - ch.getDayOfMonth()); //make some prubs with this
        
        String s = df.format(realMonthValue);
        return Float.parseFloat(s);
    }
}
