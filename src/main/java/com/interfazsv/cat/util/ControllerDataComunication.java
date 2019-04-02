package com.interfazsv.cat.util;

/**
 *
 * @author hardel
 */
public abstract class ControllerDataComunication {
    public long receivedID;
    public String receivedTable;

    /*public ControllerDataComunication(long receivedID, String receivedTable) {
        this.receivedID = receivedID;
        this.receivedTable = receivedTable;
    }*/
    
    public abstract void initData(long id, String table);

    public long getReceivedID() {
        return receivedID;
    }

    public String getReceivedTable() {
        return receivedTable;
    }
}
