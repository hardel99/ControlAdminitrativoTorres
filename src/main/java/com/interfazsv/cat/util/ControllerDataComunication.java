package com.interfazsv.cat.util;

import TableData.ClientesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;

/**
 *
 * @author hardel
 */
public abstract class ControllerDataComunication {
    
    public abstract void initDataOffer(MainOfferTable rto, String table);
    public abstract void initDataClient(ClientesTable rto, String table);
    public abstract void initDataSitio(SitiosTable rto, String table);
}
