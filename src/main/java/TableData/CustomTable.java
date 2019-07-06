package TableData;

import Entitys.sitio;
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
    /**TO-DO:
     * 
     * make another object with the cost and add it to the xslx file
     * make the another row for costos
     **/
    private final DecimalFormat df = new DecimalFormat("##.00");
    private String cliente;
    private String lugar;
    private Float total = 0f;
    private boolean primerPago;
    private float sustraendo, pagoLic;
    private LocalDate vInit, vFin;
    private final List<FloatProperty> month = new ArrayList();
    public static List<String> sitiosPrevios = new ArrayList();

    public CustomTable(venta v, LocalDate inicio, LocalDate finPeriodo, int numeroMeses, boolean primerPago) {
        df.setRoundingMode(RoundingMode.CEILING);
        this.cliente = v.getClienteV().getNombre();
        this.lugar = v.getSitioV().getNombre();
        
        this.primerPago = primerPago;
        this.sustraendo = v.getSitioV().getArrendamiento().getCosto() + v.getSitioV().getLicencia().getMonto();
        this.pagoLic = v.getSitioV().getLicencia().getCostoLicencia();
        
        this.vInit = v.getFechaInicio().plusDays(1L);
        this.vFin = v.getFechaFin().plusDays(1L);
        
        float monto = v.getOfertaVenta().getMonto();
        float can = v.getOfertaVenta().getCanon();
        float bfMonto = 0;
        int transcurred = Period.between(vInit, finPeriodo).getYears();
        boolean changingMonth = false;
        
        if(Period.between(inicio, finPeriodo).getYears() > 0) {
            //Por cada año extra hay que restar uno
            for(int i=0; i < Period.between(inicio, finPeriodo).getYears(); i++) {
                transcurred--;
            }
        } else{
            System.out.println("MENOS DE UN AÑO");
        }
        
        Period p = Period.between(vInit, vFin);
        for(int i=0; i <= numeroMeses; i++) {
            
            if(inicio.plusMonths(i).getMonth() == vInit.getMonth()) {
                transcurred++;
                changingMonth = true;
                if(month.isEmpty()) {
                    bfMonto = calculateCanon(monto, can, transcurred - 1);
                }
            }
            float value = calculateCanon(monto, can, transcurred);
            
            
            if(finPeriodo.isAfter(vFin)) {
                //Contrato expira enmedio del periodo solicitado
                
                if(transcurred <= p.getYears() || vFin.getMonth() == inicio.plusMonths(i).getMonth() && vFin.getYear() == inicio.plusMonths(i).getYear()) {
                    if(vFin.getMonth() == inicio.plusMonths(i).getMonth() &&
                            vFin.getYear() == inicio.plusMonths(i).getYear()) {
                        
                        month.add(new SimpleFloatProperty(value - calculateDailyCanon(value, vInit.minusDays(1L))));
                    } else{
                        float changedValue = 0;
                        if(changingMonth) {
                            changedValue = calculateChanginCanon(bfMonto, value, inicio.plusMonths(i));
                        }
                        month.add(new SimpleFloatProperty((changingMonth)? changedValue: value));
                    }
                } else{
                    month.add(new SimpleFloatProperty(0));
                }
            } else if(vInit.isAfter(inicio)) {
                //Contrato inicia en medio del periodo solicitado
                
                if(transcurred < p.getYears()) {
                    
                    if(vInit.getMonth() == inicio.plusMonths(i).getMonth() && 
                            vInit.getYear() == inicio.plusMonths(i).getYear()) {
                        
                        month.add(new SimpleFloatProperty(calculateDailyCanon(value, vFin)));
                    } else{
                        float changedValue = 0;
                        if(changingMonth) {
                            changedValue = calculateChanginCanon(bfMonto, value, inicio.plusMonths(i));
                        }
                        month.add(new SimpleFloatProperty((changingMonth)? changedValue: value));
                    }
                } else{
                    month.add(new SimpleFloatProperty(0));
                }
            } else{
                if(vInit.getMonth() == inicio.plusMonths(i).getMonth() && 
                        vInit.getYear() == inicio.plusMonths(i).getYear()) {
                    
                    month.add(new SimpleFloatProperty(calculateDailyCanon(value, vFin)));
                } else if(vFin.getMonth() == inicio.plusMonths(i).getMonth() &&
                        vFin.getYear() == inicio.plusMonths(i).getYear()) {
                    
                    month.add(new SimpleFloatProperty(value - calculateDailyCanon(value, vInit.minusDays(1L))));
                } else{
                    float changedValue = 0;
                    if(changingMonth) {
                        changedValue = calculateChanginCanon(bfMonto, value, inicio.plusMonths(i));
                    }
                    
                    month.add(new SimpleFloatProperty((changingMonth)? changedValue: value));
                }
            }
            
            changingMonth = false;
            bfMonto = value;
        }
        
        month.forEach(mont -> {
            this.total = this.total + mont.get();
        });
        total = Float.parseFloat(df.format(total));
    }
    
    public CustomTable(sitio s, int numeroMeses, boolean primerPago) {
        this.lugar = s.getNombre();
        float costoTotal = s.getArrendamiento().getCosto() + s.getLicencia().getMonto();
        float formatted = Float.parseFloat(df.format(costoTotal));
        
        for(int i = 0; i <= numeroMeses; i++) {
            if(s.getLicencia().isAnual()) {
                if(i%12 == 0) {
                    month.add(new SimpleFloatProperty(formatted + s.getLicencia().getCostoLicencia()));
                } else{
                    if(primerPago && i == 0) {
                        month.add(new SimpleFloatProperty(formatted + s.getLicencia().getCostoLicencia()));
                    } else{
                        month.add(new SimpleFloatProperty(formatted));
                    }
                }
            } else{
                month.add(new SimpleFloatProperty(formatted));
            }
        }
        
        
        month.forEach(mont -> {
            this.total = this.total + mont.get();
        });
        
        total = Float.parseFloat(df.format(total));
        month.add(new SimpleFloatProperty(total));
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
        
        float result = monto;
        /*Not here but still been util
        if(!sitiosPrevios.contains(lugar)) {
            if(restar) {
                if(primerPago) {
                    result = monto - sustraendo - pagoLic;
                    primerPago = false;
                } else{
                    result = monto - sustraendo;
                }
            }
            sitiosPrevios.add(lugar);
        }*/
        
        String s = df.format(result);
        return Float.parseFloat(s);
    }
    
    private float calculateDailyCanon(float monto, LocalDate ch) {
        float costoDiario = monto/ch.lengthOfMonth();//preguntar como se cobra este caso
        float realMonthValue = costoDiario * (ch.lengthOfMonth() - ch.getDayOfMonth());
        
        String s = df.format(realMonthValue);
        return Float.parseFloat(s);
    }
    
    private float calculateChanginCanon(float monto, float montoActualizado, LocalDate meta) {
        int normal, canonActualizado;
        float daily, actualDaily;
        
        normal = vInit.minusDays(1L).getDayOfMonth();
        canonActualizado = meta.lengthOfMonth() - normal;
        daily = monto / meta.lengthOfMonth();
        actualDaily = montoActualizado / meta.lengthOfMonth();
        
        float resultado = (normal * daily) + (canonActualizado * actualDaily);
        return Float.parseFloat(df.format(resultado));
    }
}
