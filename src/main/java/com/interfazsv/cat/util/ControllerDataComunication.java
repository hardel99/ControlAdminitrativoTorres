package com.interfazsv.cat.util;

import TableData.LlavesTable;
import TableData.MainOfferTable;
import TableData.SitiosTable;

/**
 *
 * @author hardel
 */
public abstract class ControllerDataComunication {
    
    public abstract void initDataOffer(MainOfferTable rto, String table);
    public abstract void initDataSitio(SitiosTable rto, String table);
    public abstract void initDataLlave(LlavesTable rto, String table);
}
