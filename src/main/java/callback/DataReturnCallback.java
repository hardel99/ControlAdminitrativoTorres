package callback;

import TableData.LlavesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;
import TableData.VentasTable;

/**
 *
 * @author hardel
 */
public interface DataReturnCallback {
    public void refreshMainData(MainOfferTable mot);
    public void refreshSitioData(SitiosTable st);
    public void refreshVentaData(VentasTable ct);
    public void refreshLlaveData(LlavesTable lt);
    
    public void refreshAllTableData();
}
