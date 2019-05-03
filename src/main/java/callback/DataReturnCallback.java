package callback;

import TableData.ClientesTable;
import TableData.LlavesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;

/**
 *
 * @author hardel
 */
public interface DataReturnCallback {
    public void refreshMainData(MainOfferTable mot);
    public void refreshSitioData(SitiosTable st);
    public void refreshClienteData(ClientesTable ct);
    public void refreshLlaveData(LlavesTable lt);
}
