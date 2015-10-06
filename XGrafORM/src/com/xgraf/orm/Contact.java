// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Oct 06 17:26:15 EEST 2015
// generated file: do not modify
package com.xgraf.orm;

import com.xgraf.orm.dbobject.DbObject;
import com.xgraf.orm.dbobject.ForeignKeyViolationException;
import com.xgraf.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Contact extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer contactId = null;
    private Integer companyId = null;
    private String name = null;
    private String phone = null;
    private String email = null;
    private String email1 = null;
    private String comments = null;
    private Object photo = null;

    public Contact(Connection connection) {
        super(connection, "contact", "contact_id");
        setColumnNames(new String[]{"contact_id", "company_id", "name", "phone", "email", "email1", "comments", "photo"});
    }

    public Contact(Connection connection, Integer contactId, Integer companyId, String name, String phone, String email, String email1, String comments, Object photo) {
        super(connection, "contact", "contact_id");
        setNew(contactId.intValue() <= 0);
//        if (contactId.intValue() != 0) {
            this.contactId = contactId;
//        }
        this.companyId = companyId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.email1 = email1;
        this.comments = comments;
        this.photo = photo;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Contact contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT contact_id,company_id,name,phone,email,email1,comments,photo FROM contact WHERE contact_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                contact = new Contact(getConnection());
                contact.setContactId(new Integer(rs.getInt(1)));
                contact.setCompanyId(new Integer(rs.getInt(2)));
                contact.setName(rs.getString(3));
                contact.setPhone(rs.getString(4));
                contact.setEmail(rs.getString(5));
                contact.setEmail1(rs.getString(6));
                contact.setComments(rs.getString(7));
                contact.setPhoto(rs.getObject(8));
                contact.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return contact;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO contact ("+(getContactId().intValue()!=0?"contact_id,":"")+"company_id,name,phone,email,email1,comments,photo) values("+(getContactId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getContactId().intValue()!=0) {
                 ps.setObject(++n, getContactId());
             }
             ps.setObject(++n, getCompanyId());
             ps.setObject(++n, getName());
             ps.setObject(++n, getPhone());
             ps.setObject(++n, getEmail());
             ps.setObject(++n, getEmail1());
             ps.setObject(++n, getComments());
             ps.setObject(++n, getPhoto());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getContactId().intValue()==0) {
             stmt = "SELECT max(contact_id) FROM contact";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setContactId(new Integer(rs.getInt(1)));
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
                    "UPDATE contact " +
                    "SET company_id = ?, name = ?, phone = ?, email = ?, email1 = ?, comments = ?, photo = ?" + 
                    " WHERE contact_id = " + getContactId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCompanyId());
                ps.setObject(2, getName());
                ps.setObject(3, getPhone());
                ps.setObject(4, getEmail());
                ps.setObject(5, getEmail1());
                ps.setObject(6, getComments());
                ps.setObject(7, getPhoto());
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
        if (Invoice.exists(getConnection(),"contact_id = " + getContactId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: invoice_contact_fk");
        }
        if (Quote.exists(getConnection(),"contact_id = " + getContactId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: quote_contact_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM contact " +
                "WHERE contact_id = " + getContactId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setContactId(new Integer(-getContactId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getContactId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT contact_id,company_id,name,phone,email,email1,comments,photo FROM contact " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Contact(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getObject(8)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Contact[] objects = new Contact[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Contact contact = (Contact) lst.get(i);
            objects[i] = contact;
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
        String stmt = "SELECT contact_id FROM contact " +
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
    //    return getContactId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return contactId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setContactId(id);
        setNew(prevIsNew);
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) throws ForeignKeyViolationException {
        setWasChanged(this.contactId != null && this.contactId != contactId);
        this.contactId = contactId;
        setNew(contactId.intValue() == 0);
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) throws SQLException, ForeignKeyViolationException {
        if (companyId!=null && !Company.exists(getConnection(),"company_id = " + companyId)) {
            throw new ForeignKeyViolationException("Can't set company_id, foreign key violation: contact_company_fk");
        }
        setWasChanged(this.companyId != null && !this.companyId.equals(companyId));
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.name != null && !this.name.equals(name));
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone != null && !this.phone.equals(phone));
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.email != null && !this.email.equals(email));
        this.email = email;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.email1 != null && !this.email1.equals(email1));
        this.email1 = email1;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.comments != null && !this.comments.equals(comments));
        this.comments = comments;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.photo != null && !this.photo.equals(photo));
        this.photo = photo;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getContactId();
        columnValues[1] = getCompanyId();
        columnValues[2] = getName();
        columnValues[3] = getPhone();
        columnValues[4] = getEmail();
        columnValues[5] = getEmail1();
        columnValues[6] = getComments();
        columnValues[7] = getPhoto();
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
            setContactId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setContactId(null);
        }
        try {
            setCompanyId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setCompanyId(null);
        }
        setName(flds[2]);
        setPhone(flds[3]);
        setEmail(flds[4]);
        setEmail1(flds[5]);
        setComments(flds[6]);
        setPhoto(flds[7]);
    }
}
