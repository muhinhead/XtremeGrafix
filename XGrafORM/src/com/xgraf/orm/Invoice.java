// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Sat Oct 24 16:51:21 EEST 2015
// generated file: do not modify
package com.xgraf.orm;

import com.xgraf.orm.dbobject.DbObject;
import com.xgraf.orm.dbobject.ForeignKeyViolationException;
import com.xgraf.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Invoice extends DbObject implements IDocument {
    private static Triggers activeTriggers = null;
    private Integer invoiceId = null;
    private Integer companyId = null;
    private Integer contactId = null;
    private String serviceType = null;
    private String invoiceRef = null;
    private String orderNo = null;
    private Date invoiceDate = null;
    private Integer quoteId = null;
    private Double subTotal = null;
    private String bankAccHolder = null;
    private String bank = null;
    private String bankBranchCode = null;
    private String bankAccNo = null;
    private String bankAccType = null;
    private Date validTerm = null;
    private Integer depositPercent = null;
    private Integer refundBreakPercent = null;
    private Integer outbalanceWeeks = null;
    private String prefPayMethod = null;
    private String addConditions = null;

    public Invoice(Connection connection) {
        super(connection, "invoice", "invoice_id");
        setColumnNames(new String[]{"invoice_id", "company_id", "contact_id", "service_type", "invoice_ref", "order_no", "invoice_date", "quote_id", "sub_total", "bank_acc_holder", "bank", "bank_branch_code", "bank_acc_no", "bank_acc_type", "valid_term", "deposit_percent", "refund_break_percent", "outbalance_weeks", "pref_pay_method", "add_conditions"});
    }

    public Invoice(Connection connection, Integer invoiceId, Integer companyId, Integer contactId, String serviceType, String invoiceRef, String orderNo, Date invoiceDate, Integer quoteId, Double subTotal, String bankAccHolder, String bank, String bankBranchCode, String bankAccNo, String bankAccType, Date validTerm, Integer depositPercent, Integer refundBreakPercent, Integer outbalanceWeeks, String prefPayMethod, String addConditions) {
        super(connection, "invoice", "invoice_id");
        setNew(invoiceId.intValue() <= 0);
//        if (invoiceId.intValue() != 0) {
            this.invoiceId = invoiceId;
//        }
        this.companyId = companyId;
        this.contactId = contactId;
        this.serviceType = serviceType;
        this.invoiceRef = invoiceRef;
        this.orderNo = orderNo;
        this.invoiceDate = invoiceDate;
        this.quoteId = quoteId;
        this.subTotal = subTotal;
        this.bankAccHolder = bankAccHolder;
        this.bank = bank;
        this.bankBranchCode = bankBranchCode;
        this.bankAccNo = bankAccNo;
        this.bankAccType = bankAccType;
        this.validTerm = validTerm;
        this.depositPercent = depositPercent;
        this.refundBreakPercent = refundBreakPercent;
        this.outbalanceWeeks = outbalanceWeeks;
        this.prefPayMethod = prefPayMethod;
        this.addConditions = addConditions;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Invoice invoice = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT invoice_id,company_id,contact_id,service_type,invoice_ref,order_no,invoice_date,quote_id,sub_total,bank_acc_holder,bank,bank_branch_code,bank_acc_no,bank_acc_type,valid_term,deposit_percent,refund_break_percent,outbalance_weeks,pref_pay_method,add_conditions FROM invoice WHERE invoice_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                invoice = new Invoice(getConnection());
                invoice.setInvoiceId(new Integer(rs.getInt(1)));
                invoice.setCompanyId(new Integer(rs.getInt(2)));
                invoice.setContactId(new Integer(rs.getInt(3)));
                invoice.setServiceType(rs.getString(4));
                invoice.setInvoiceRef(rs.getString(5));
                invoice.setOrderNo(rs.getString(6));
                invoice.setInvoiceDate(rs.getDate(7));
                invoice.setQuoteId(new Integer(rs.getInt(8)));
                invoice.setSubTotal(rs.getDouble(9));
                invoice.setBankAccHolder(rs.getString(10));
                invoice.setBank(rs.getString(11));
                invoice.setBankBranchCode(rs.getString(12));
                invoice.setBankAccNo(rs.getString(13));
                invoice.setBankAccType(rs.getString(14));
                invoice.setValidTerm(rs.getDate(15));
                invoice.setDepositPercent(new Integer(rs.getInt(16)));
                invoice.setRefundBreakPercent(new Integer(rs.getInt(17)));
                invoice.setOutbalanceWeeks(new Integer(rs.getInt(18)));
                invoice.setPrefPayMethod(rs.getString(19));
                invoice.setAddConditions(rs.getString(20));
                invoice.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return invoice;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO invoice ("+(getInvoiceId().intValue()!=0?"invoice_id,":"")+"company_id,contact_id,service_type,invoice_ref,order_no,invoice_date,quote_id,sub_total,bank_acc_holder,bank,bank_branch_code,bank_acc_no,bank_acc_type,valid_term,deposit_percent,refund_break_percent,outbalance_weeks,pref_pay_method,add_conditions) values("+(getInvoiceId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getInvoiceId().intValue()!=0) {
                 ps.setObject(++n, getInvoiceId());
             }
             ps.setObject(++n, getCompanyId());
             ps.setObject(++n, getContactId());
             ps.setObject(++n, getServiceType());
             ps.setObject(++n, getInvoiceRef());
             ps.setObject(++n, getOrderNo());
             ps.setObject(++n, getInvoiceDate());
             ps.setObject(++n, getQuoteId());
             ps.setObject(++n, getSubTotal());
             ps.setObject(++n, getBankAccHolder());
             ps.setObject(++n, getBank());
             ps.setObject(++n, getBankBranchCode());
             ps.setObject(++n, getBankAccNo());
             ps.setObject(++n, getBankAccType());
             ps.setObject(++n, getValidTerm());
             ps.setObject(++n, getDepositPercent());
             ps.setObject(++n, getRefundBreakPercent());
             ps.setObject(++n, getOutbalanceWeeks());
             ps.setObject(++n, getPrefPayMethod());
             ps.setObject(++n, getAddConditions());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getInvoiceId().intValue()==0) {
             stmt = "SELECT max(invoice_id) FROM invoice";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setInvoiceId(new Integer(rs.getInt(1)));
                 }
             } finally {
                 try {
                     if (rs != null) rs.close();
                 } finally {
                     if (ps != null) ps.close();
                 }
             }
         }
         setNew(false);
         setWasChanged(false);
         if (getTriggers() != null) {
             getTriggers().afterInsert(this);
         }
    }

    public void save() throws SQLException, ForeignKeyViolationException {
        if (isNew()) {
            insert();
        } else {
            if (getTriggers() != null) {
                getTriggers().beforeUpdate(this);
            }
            PreparedStatement ps = null;
            String stmt =
                    "UPDATE invoice " +
                    "SET company_id = ?, contact_id = ?, service_type = ?, invoice_ref = ?, order_no = ?, invoice_date = ?, quote_id = ?, sub_total = ?, bank_acc_holder = ?, bank = ?, bank_branch_code = ?, bank_acc_no = ?, bank_acc_type = ?, valid_term = ?, deposit_percent = ?, refund_break_percent = ?, outbalance_weeks = ?, pref_pay_method = ?, add_conditions = ?" + 
                    " WHERE invoice_id = " + getInvoiceId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCompanyId());
                ps.setObject(2, getContactId());
                ps.setObject(3, getServiceType());
                ps.setObject(4, getInvoiceRef());
                ps.setObject(5, getOrderNo());
                ps.setObject(6, getInvoiceDate());
                ps.setObject(7, getQuoteId());
                ps.setObject(8, getSubTotal());
                ps.setObject(9, getBankAccHolder());
                ps.setObject(10, getBank());
                ps.setObject(11, getBankBranchCode());
                ps.setObject(12, getBankAccNo());
                ps.setObject(13, getBankAccType());
                ps.setObject(14, getValidTerm());
                ps.setObject(15, getDepositPercent());
                ps.setObject(16, getRefundBreakPercent());
                ps.setObject(17, getOutbalanceWeeks());
                ps.setObject(18, getPrefPayMethod());
                ps.setObject(19, getAddConditions());
                ps.execute();
            } finally {
                if (ps != null) ps.close();
            }
            setWasChanged(false);
            if (getTriggers() != null) {
                getTriggers().afterUpdate(this);
            }
        }
    }

    public void delete() throws SQLException, ForeignKeyViolationException {
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        {// delete cascade from invoiceitem
            Invoiceitem[] records = (Invoiceitem[])Invoiceitem.load(getConnection(),"invoice_id = " + getInvoiceId(),null);
            for (int i = 0; i<records.length; i++) {
                Invoiceitem invoiceitem = records[i];
                invoiceitem.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM invoice " +
                "WHERE invoice_id = " + getInvoiceId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setInvoiceId(new Integer(-getInvoiceId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getInvoiceId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT invoice_id,company_id,contact_id,service_type,invoice_ref,order_no,invoice_date,quote_id,sub_total,bank_acc_holder,bank,bank_branch_code,bank_acc_no,bank_acc_type,valid_term,deposit_percent,refund_break_percent,outbalance_weeks,pref_pay_method,add_conditions FROM invoice " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Invoice(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7),new Integer(rs.getInt(8)),rs.getDouble(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getDate(15),new Integer(rs.getInt(16)),new Integer(rs.getInt(17)),new Integer(rs.getInt(18)),rs.getString(19),rs.getString(20)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Invoice[] objects = new Invoice[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Invoice invoice = (Invoice) lst.get(i);
            objects[i] = invoice;
        }
        return objects;
    }

    public static boolean exists(Connection con, String whereCondition) throws SQLException {
        if (con == null) {
            return true;
        }
        boolean ok = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT invoice_id FROM invoice " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                "WHERE " + whereCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            ok = rs.next();
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return ok;
    }

    //public String toString() {
    //    return getInvoiceId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return invoiceId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setInvoiceId(id);
        setNew(prevIsNew);
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) throws ForeignKeyViolationException {
        setWasChanged(this.invoiceId != null && this.invoiceId != invoiceId);
        this.invoiceId = invoiceId;
        setNew(invoiceId.intValue() == 0);
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) throws SQLException, ForeignKeyViolationException {
        if (companyId!=null && !Company.exists(getConnection(),"company_id = " + companyId)) {
            throw new ForeignKeyViolationException("Can't set company_id, foreign key violation: invoice_company_fk");
        }
        setWasChanged(this.companyId != null && !this.companyId.equals(companyId));
        this.companyId = companyId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) throws SQLException, ForeignKeyViolationException {
        if (contactId!=null && !Contact.exists(getConnection(),"contact_id = " + contactId)) {
            throw new ForeignKeyViolationException("Can't set contact_id, foreign key violation: invoice_contact_fk");
        }
        setWasChanged(this.contactId != null && !this.contactId.equals(contactId));
        this.contactId = contactId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.serviceType != null && !this.serviceType.equals(serviceType));
        this.serviceType = serviceType;
    }

    public String getInvoiceRef() {
        return invoiceRef;
    }

    public void setInvoiceRef(String invoiceRef) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoiceRef != null && !this.invoiceRef.equals(invoiceRef));
        this.invoiceRef = invoiceRef;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.orderNo != null && !this.orderNo.equals(orderNo));
        this.orderNo = orderNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.invoiceDate != null && !this.invoiceDate.equals(invoiceDate));
        this.invoiceDate = invoiceDate;
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) throws SQLException, ForeignKeyViolationException {
        if (null != quoteId)
            quoteId = quoteId == 0 ? null : quoteId;
        if (quoteId!=null && !Quote.exists(getConnection(),"quote_id = " + quoteId)) {
            throw new ForeignKeyViolationException("Can't set quote_id, foreign key violation: invoice_quote_fk");
        }
        setWasChanged(this.quoteId != null && !this.quoteId.equals(quoteId));
        this.quoteId = quoteId;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.subTotal != null && !this.subTotal.equals(subTotal));
        this.subTotal = subTotal;
    }

    public String getBankAccHolder() {
        return bankAccHolder;
    }

    public void setBankAccHolder(String bankAccHolder) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bankAccHolder != null && !this.bankAccHolder.equals(bankAccHolder));
        this.bankAccHolder = bankAccHolder;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bank != null && !this.bank.equals(bank));
        this.bank = bank;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bankBranchCode != null && !this.bankBranchCode.equals(bankBranchCode));
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bankAccNo != null && !this.bankAccNo.equals(bankAccNo));
        this.bankAccNo = bankAccNo;
    }

    public String getBankAccType() {
        return bankAccType;
    }

    public void setBankAccType(String bankAccType) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.bankAccType != null && !this.bankAccType.equals(bankAccType));
        this.bankAccType = bankAccType;
    }

    public Date getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(Date validTerm) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.validTerm != null && !this.validTerm.equals(validTerm));
        this.validTerm = validTerm;
    }

    public Integer getDepositPercent() {
        return depositPercent;
    }

    public void setDepositPercent(Integer depositPercent) throws SQLException, ForeignKeyViolationException {
        if (null != depositPercent)
            depositPercent = depositPercent == 0 ? null : depositPercent;
        setWasChanged(this.depositPercent != null && !this.depositPercent.equals(depositPercent));
        this.depositPercent = depositPercent;
    }

    public Integer getRefundBreakPercent() {
        return refundBreakPercent;
    }

    public void setRefundBreakPercent(Integer refundBreakPercent) throws SQLException, ForeignKeyViolationException {
        if (null != refundBreakPercent)
            refundBreakPercent = refundBreakPercent == 0 ? null : refundBreakPercent;
        setWasChanged(this.refundBreakPercent != null && !this.refundBreakPercent.equals(refundBreakPercent));
        this.refundBreakPercent = refundBreakPercent;
    }

    public Integer getOutbalanceWeeks() {
        return outbalanceWeeks;
    }

    public void setOutbalanceWeeks(Integer outbalanceWeeks) throws SQLException, ForeignKeyViolationException {
        if (null != outbalanceWeeks)
            outbalanceWeeks = outbalanceWeeks == 0 ? null : outbalanceWeeks;
        setWasChanged(this.outbalanceWeeks != null && !this.outbalanceWeeks.equals(outbalanceWeeks));
        this.outbalanceWeeks = outbalanceWeeks;
    }

    public String getPrefPayMethod() {
        return prefPayMethod;
    }

    public void setPrefPayMethod(String prefPayMethod) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.prefPayMethod != null && !this.prefPayMethod.equals(prefPayMethod));
        this.prefPayMethod = prefPayMethod;
    }

    public String getAddConditions() {
        return addConditions;
    }

    public void setAddConditions(String addConditions) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.addConditions != null && !this.addConditions.equals(addConditions));
        this.addConditions = addConditions;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[20];
        columnValues[0] = getInvoiceId();
        columnValues[1] = getCompanyId();
        columnValues[2] = getContactId();
        columnValues[3] = getServiceType();
        columnValues[4] = getInvoiceRef();
        columnValues[5] = getOrderNo();
        columnValues[6] = getInvoiceDate();
        columnValues[7] = getQuoteId();
        columnValues[8] = getSubTotal();
        columnValues[9] = getBankAccHolder();
        columnValues[10] = getBank();
        columnValues[11] = getBankBranchCode();
        columnValues[12] = getBankAccNo();
        columnValues[13] = getBankAccType();
        columnValues[14] = getValidTerm();
        columnValues[15] = getDepositPercent();
        columnValues[16] = getRefundBreakPercent();
        columnValues[17] = getOutbalanceWeeks();
        columnValues[18] = getPrefPayMethod();
        columnValues[19] = getAddConditions();
        return columnValues;
    }

    public static void setTriggers(Triggers triggers) {
        activeTriggers = triggers;
    }

    public static Triggers getTriggers() {
        return activeTriggers;
    }

    //for SOAP exhange
    @Override
    public void fillFromString(String row) throws ForeignKeyViolationException, SQLException {
        String[] flds = splitStr(row, delimiter);
        try {
            setInvoiceId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setInvoiceId(null);
        }
        try {
            setCompanyId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setCompanyId(null);
        }
        try {
            setContactId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setContactId(null);
        }
        setServiceType(flds[3]);
        setInvoiceRef(flds[4]);
        setOrderNo(flds[5]);
        setInvoiceDate(toDate(flds[6]));
        try {
            setQuoteId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setQuoteId(null);
        }
        try {
            setSubTotal(Double.parseDouble(flds[8]));
        } catch(NumberFormatException ne) {
            setSubTotal(null);
        }
        setBankAccHolder(flds[9]);
        setBank(flds[10]);
        setBankBranchCode(flds[11]);
        setBankAccNo(flds[12]);
        setBankAccType(flds[13]);
        setValidTerm(toDate(flds[14]));
        try {
            setDepositPercent(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setDepositPercent(null);
        }
        try {
            setRefundBreakPercent(Integer.parseInt(flds[16]));
        } catch(NumberFormatException ne) {
            setRefundBreakPercent(null);
        }
        try {
            setOutbalanceWeeks(Integer.parseInt(flds[17]));
        } catch(NumberFormatException ne) {
            setOutbalanceWeeks(null);
        }
        setPrefPayMethod(flds[18]);
        setAddConditions(flds[19]);
    }

    @Override
    public void setDocRef(String dr) throws SQLException, ForeignKeyViolationException {
        setInvoiceRef(dr);
    }

    @Override
    public String getDocRef() {
        return getInvoiceRef();
    }

    @Override
    public void setIsProforma(Integer isProforma) throws SQLException, ForeignKeyViolationException {
    }

    @Override
    public Integer getIsProforma() {
        return 0;
    }

    @Override
    public Date getDocDate() {
        return getInvoiceDate();
    }

    @Override
    public void setDocDate(Date docDate) throws SQLException, ForeignKeyViolationException {
        setInvoiceDate(docDate);
    }
}