// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Sep 23 08:58:59 EEST 2015
// generated file: do not modify
package com.xgraf.orm;

import com.xgraf.orm.dbobject.DbObject;
import com.xgraf.orm.dbobject.ForeignKeyViolationException;
import com.xgraf.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Quote extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer quoteId = null;
    private Integer companyId = null;
    private Integer contactId = null;
    private String serviceType = null;
    private String quoteRef = null;
    private String orderNo = null;
    private Date quoteDate = null;
    private Integer isProforma = null;
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

    public Quote(Connection connection) {
        super(connection, "quote", "quote_id");
        setColumnNames(new String[]{"quote_id", "company_id", "contact_id", "service_type", "quote_ref", "order_no", "quote_date", "is_proforma", "sub_total", "bank_acc_holder", "bank", "bank_branch_code", "bank_acc_no", "bank_acc_type", "valid_term", "deposit_percent", "refund_break_percent", "outbalance_weeks", "pref_pay_method", "add_conditions"});
    }

    public Quote(Connection connection, Integer quoteId, Integer companyId, Integer contactId, String serviceType, String quoteRef, String orderNo, Date quoteDate, Integer isProforma, Double subTotal, String bankAccHolder, String bank, String bankBranchCode, String bankAccNo, String bankAccType, Date validTerm, Integer depositPercent, Integer refundBreakPercent, Integer outbalanceWeeks, String prefPayMethod, String addConditions) {
        super(connection, "quote", "quote_id");
        setNew(quoteId.intValue() <= 0);
//        if (quoteId.intValue() != 0) {
            this.quoteId = quoteId;
//        }
        this.companyId = companyId;
        this.contactId = contactId;
        this.serviceType = serviceType;
        this.quoteRef = quoteRef;
        this.orderNo = orderNo;
        this.quoteDate = quoteDate;
        this.isProforma = isProforma;
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
        Quote quote = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT quote_id,company_id,contact_id,service_type,quote_ref,order_no,quote_date,is_proforma,sub_total,bank_acc_holder,bank,bank_branch_code,bank_acc_no,bank_acc_type,valid_term,deposit_percent,refund_break_percent,outbalance_weeks,pref_pay_method,add_conditions FROM quote WHERE quote_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                quote = new Quote(getConnection());
                quote.setQuoteId(new Integer(rs.getInt(1)));
                quote.setCompanyId(new Integer(rs.getInt(2)));
                quote.setContactId(new Integer(rs.getInt(3)));
                quote.setServiceType(rs.getString(4));
                quote.setQuoteRef(rs.getString(5));
                quote.setOrderNo(rs.getString(6));
                quote.setQuoteDate(rs.getDate(7));
                quote.setIsProforma(new Integer(rs.getInt(8)));
                quote.setSubTotal(rs.getDouble(9));
                quote.setBankAccHolder(rs.getString(10));
                quote.setBank(rs.getString(11));
                quote.setBankBranchCode(rs.getString(12));
                quote.setBankAccNo(rs.getString(13));
                quote.setBankAccType(rs.getString(14));
                quote.setValidTerm(rs.getDate(15));
                quote.setDepositPercent(new Integer(rs.getInt(16)));
                quote.setRefundBreakPercent(new Integer(rs.getInt(17)));
                quote.setOutbalanceWeeks(new Integer(rs.getInt(18)));
                quote.setPrefPayMethod(rs.getString(19));
                quote.setAddConditions(rs.getString(20));
                quote.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return quote;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO quote ("+(getQuoteId().intValue()!=0?"quote_id,":"")+"company_id,contact_id,service_type,quote_ref,order_no,quote_date,is_proforma,sub_total,bank_acc_holder,bank,bank_branch_code,bank_acc_no,bank_acc_type,valid_term,deposit_percent,refund_break_percent,outbalance_weeks,pref_pay_method,add_conditions) values("+(getQuoteId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getQuoteId().intValue()!=0) {
                 ps.setObject(++n, getQuoteId());
             }
             ps.setObject(++n, getCompanyId());
             ps.setObject(++n, getContactId());
             ps.setObject(++n, getServiceType());
             ps.setObject(++n, getQuoteRef());
             ps.setObject(++n, getOrderNo());
             ps.setObject(++n, getQuoteDate());
             ps.setObject(++n, getIsProforma());
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
         if (getQuoteId().intValue()==0) {
             stmt = "SELECT max(quote_id) FROM quote";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setQuoteId(new Integer(rs.getInt(1)));
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
                    "UPDATE quote " +
                    "SET company_id = ?, contact_id = ?, service_type = ?, quote_ref = ?, order_no = ?, quote_date = ?, is_proforma = ?, sub_total = ?, bank_acc_holder = ?, bank = ?, bank_branch_code = ?, bank_acc_no = ?, bank_acc_type = ?, valid_term = ?, deposit_percent = ?, refund_break_percent = ?, outbalance_weeks = ?, pref_pay_method = ?, add_conditions = ?" + 
                    " WHERE quote_id = " + getQuoteId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCompanyId());
                ps.setObject(2, getContactId());
                ps.setObject(3, getServiceType());
                ps.setObject(4, getQuoteRef());
                ps.setObject(5, getOrderNo());
                ps.setObject(6, getQuoteDate());
                ps.setObject(7, getIsProforma());
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
        {// delete cascade from quoteitem
            Quoteitem[] records = (Quoteitem[])Quoteitem.load(getConnection(),"quote_id = " + getQuoteId(),null);
            for (int i = 0; i<records.length; i++) {
                Quoteitem quoteitem = records[i];
                quoteitem.delete();
            }
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM quote " +
                "WHERE quote_id = " + getQuoteId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setQuoteId(new Integer(-getQuoteId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getQuoteId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT quote_id,company_id,contact_id,service_type,quote_ref,order_no,quote_date,is_proforma,sub_total,bank_acc_holder,bank,bank_branch_code,bank_acc_no,bank_acc_type,valid_term,deposit_percent,refund_break_percent,outbalance_weeks,pref_pay_method,add_conditions FROM quote " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Quote(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7),new Integer(rs.getInt(8)),rs.getDouble(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getDate(15),new Integer(rs.getInt(16)),new Integer(rs.getInt(17)),new Integer(rs.getInt(18)),rs.getString(19),rs.getString(20)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Quote[] objects = new Quote[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Quote quote = (Quote) lst.get(i);
            objects[i] = quote;
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
        String stmt = "SELECT quote_id FROM quote " +
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
    //    return getQuoteId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return quoteId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setQuoteId(id);
        setNew(prevIsNew);
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) throws ForeignKeyViolationException {
        setWasChanged(this.quoteId != null && this.quoteId != quoteId);
        this.quoteId = quoteId;
        setNew(quoteId.intValue() == 0);
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) throws SQLException, ForeignKeyViolationException {
        if (companyId!=null && !Company.exists(getConnection(),"company_id = " + companyId)) {
            throw new ForeignKeyViolationException("Can't set company_id, foreign key violation: quote_company_fk");
        }
        setWasChanged(this.companyId != null && !this.companyId.equals(companyId));
        this.companyId = companyId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) throws SQLException, ForeignKeyViolationException {
        if (contactId!=null && !Contact.exists(getConnection(),"contact_id = " + contactId)) {
            throw new ForeignKeyViolationException("Can't set contact_id, foreign key violation: quote_contact_fk");
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

    public String getQuoteRef() {
        return quoteRef;
    }

    public void setQuoteRef(String quoteRef) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quoteRef != null && !this.quoteRef.equals(quoteRef));
        this.quoteRef = quoteRef;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.orderNo != null && !this.orderNo.equals(orderNo));
        this.orderNo = orderNo;
    }

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.quoteDate != null && !this.quoteDate.equals(quoteDate));
        this.quoteDate = quoteDate;
    }

    public Integer getIsProforma() {
        return isProforma;
    }

    public void setIsProforma(Integer isProforma) throws SQLException, ForeignKeyViolationException {
        if (null != isProforma)
            isProforma = isProforma == 0 ? null : isProforma;
        setWasChanged(this.isProforma != null && !this.isProforma.equals(isProforma));
        this.isProforma = isProforma;
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
        columnValues[0] = getQuoteId();
        columnValues[1] = getCompanyId();
        columnValues[2] = getContactId();
        columnValues[3] = getServiceType();
        columnValues[4] = getQuoteRef();
        columnValues[5] = getOrderNo();
        columnValues[6] = getQuoteDate();
        columnValues[7] = getIsProforma();
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
            setQuoteId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setQuoteId(null);
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
        setQuoteRef(flds[4]);
        setOrderNo(flds[5]);
        setQuoteDate(toDate(flds[6]));
        try {
            setIsProforma(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setIsProforma(null);
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
}
