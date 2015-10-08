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
        super(exchanger, getSELECT().replaceAll("=0", "=1").replaceAll("Quote Ref", "Pro-Forma Inv #"));
    }

    public ProFormaInvoiceGrid(IMessageSender exchanger, int company_id) throws RemoteException {
        super(exchanger, getSELECT().replaceAll("=0", "=1").replaceAll("Quote Ref", "Pro-Forma Inv #") + " and company_id=" + company_id);
    }

    public ProFormaInvoiceGrid(int contact_id, IMessageSender exchanger) throws RemoteException {
        super(exchanger, getSELECT().replaceAll("=0", "=1").replaceAll("Quote Ref", "Pro-Forma Inv #") + " and contact_id=" + contact_id);
    }
}
