/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf.orm;

import com.xgraf.orm.dbobject.ForeignKeyViolationException;
import java.sql.SQLException;

/**
 *
 * @author nick
 */
public interface IDocumentItem {

    Integer getPK_ID();

    void setPK_ID(Integer id) throws ForeignKeyViolationException;

    Integer getDocId();
    
    void setDocId(Integer id) throws SQLException, ForeignKeyViolationException;
    
    String getDescr();
    
    void setDescr(String descr) throws SQLException, ForeignKeyViolationException;
    
    Integer getQty();
    
    void setQty(Integer qty) throws SQLException, ForeignKeyViolationException;
    
    Double getUnitPrice();
    
    void setUnitPrice(Double unitPrice) throws SQLException, ForeignKeyViolationException;
}
