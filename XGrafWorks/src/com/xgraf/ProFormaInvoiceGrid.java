/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.remote.IMessageSender;
import java.rmi.RemoteException;

/**
 *
 * @author nick
 */
public class ProFormaInvoiceGrid extends QuoteGrid {

    public ProFormaInvoiceGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, getSELECT().replaceAll("=0", "=1"));
    }
    
}
