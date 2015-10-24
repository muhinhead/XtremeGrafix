/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf.orm;

import com.xgraf.orm.dbobject.DbObject;
import com.xgraf.orm.dbobject.ForeignKeyViolationException;
import com.xgraf.remote.IMessageSender;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * @author nick
 */
public class InvoiceFromQuote extends Invoice {

    private Quote papa;
    private final IMessageSender exchanger;

    public InvoiceFromQuote(Quote quote, IMessageSender exch) throws SQLException, ForeignKeyViolationException {
        super(quote.getConnection());
        setInvoiceId(0);
        setQuoteId(quote.getQuoteId());
        setAddConditions(quote.getAddConditions());
        setBank(quote.getBank());
        setBankAccHolder(quote.getBankAccHolder());
        setBankAccNo(quote.getBankAccNo());
        setBankAccType(quote.getBankAccType());
        setBankBranchCode(quote.getBankBranchCode());
        setCompanyId(quote.getCompanyId());
        setContactId(quote.getContactId());
        setDepositPercent(quote.getDepositPercent());
        setDocDate(quote.getDocDate());
        setInvoiceDate(quote.getQuoteDate());
        setInvoiceRef(quote.getQuoteRef());
        setOrderNo(quote.getOrderNo());
        setOutbalanceWeeks(quote.getOutbalanceWeeks());
        setPrefPayMethod(quote.getPrefPayMethod());
        setRefundBreakPercent(quote.getRefundBreakPercent());
        setServiceType(quote.getServiceType());
        setSubTotal(quote.getSubTotal());
        setValidTerm(quote.getValidTerm());
        papa = quote;
        exchanger = exch;
    }

    @Override
    public void save() throws SQLException, ForeignKeyViolationException {
        boolean wasNew = isNew();
        super.save();
        if (wasNew) {
            fillItemList();
        }
    }

    private void fillItemList() throws SQLException, ForeignKeyViolationException {
        if (exchanger != null) {
            try {
                DbObject[] itms = exchanger.getDbObjects(Quoteitem.class, "quote_id=" + papa.getQuoteId(), null);
                for (DbObject itm : itms) {
                    Quoteitem qitm = (Quoteitem) itm;
                    Invoiceitem iitm = new Invoiceitem(qitm.getConnection());
                    iitm.setDescr(qitm.getDescr());
                    iitm.setQty(qitm.getQty());
                    iitm.setUnitPrice(qitm.getUnitPrice());
                    iitm.setInvoiceitemId(0);
                    iitm.setInvoiceId(getInvoiceId());
                    exchanger.saveDbObject(iitm);
                }
            } catch (RemoteException ex) {
                throw new SQLException(ex);
            }
        }
    }
}
