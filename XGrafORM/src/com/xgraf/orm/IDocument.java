/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf.orm;

import com.xgraf.orm.dbobject.ForeignKeyViolationException;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author nick
 */
public interface IDocument {

    void setPK_ID(Integer id) throws ForeignKeyViolationException;

    Integer getPK_ID();

    void setCompanyId(Integer id) throws SQLException, ForeignKeyViolationException ;

    Integer getCompanyId();

    void setContactId(Integer id) throws SQLException, ForeignKeyViolationException ;

    Integer getContactId();
    
    void setServiceType(String serviceType) throws SQLException, ForeignKeyViolationException;

    String getServiceType();
    
    void setDocRef(String dr) throws SQLException, ForeignKeyViolationException;

    String getDocRef();
    
    void setIsProforma(Integer isProforma) throws SQLException, ForeignKeyViolationException;
            
    Integer getIsProforma();
    
    Double getSubTotal();

    public void setSubTotal(Double subTotal) throws SQLException, ForeignKeyViolationException;
    
    String getBankAccHolder();
    
    void setBankAccHolder(String bankAccHolder) throws SQLException, ForeignKeyViolationException;
    
    String getBank();
    
    void setBank(String bank) throws SQLException, ForeignKeyViolationException;
    
    String getBankBranchCode();
    
    void setBankBranchCode(String bankBranchCode) throws SQLException, ForeignKeyViolationException;
    
    String getBankAccNo();
    
    void setBankAccNo(String bankAccNo) throws SQLException, ForeignKeyViolationException;
    
    String getBankAccType();
    
    void setBankAccType(String bankAccType) throws SQLException, ForeignKeyViolationException;
    
    Date getValidTerm();
    
    void setValidTerm(Date validTerm) throws SQLException, ForeignKeyViolationException;
    
    Integer getDepositPercent();
    
    void setDepositPercent(Integer depositPercent) throws SQLException, ForeignKeyViolationException;
    
    Integer getRefundBreakPercent();
    
    void setRefundBreakPercent(Integer refundBreakPercent) throws SQLException, ForeignKeyViolationException;
    
    Integer getOutbalanceWeeks();
    
    void setOutbalanceWeeks(Integer outbalanceWeeks) throws SQLException, ForeignKeyViolationException;
    
    String getPrefPayMethod();
    
    void setPrefPayMethod(String prefPayMethod) throws SQLException, ForeignKeyViolationException;
    
    String getAddConditions();
    
    void setAddConditions(String addConditions) throws SQLException, ForeignKeyViolationException;
    
    Date getDocDate();
    
    void setDocDate(Date docDate) throws SQLException, ForeignKeyViolationException;
    
    String getOrderNo();
    
    void setOrderNo(String ordNo) throws SQLException, ForeignKeyViolationException;
}
