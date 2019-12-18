package com.example.AgendaSoftware.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "phone")
@NamedQueries({
        @NamedQuery(name = "Phone.findAll", query = "SELECT p FROM Phone p"),
        @NamedQuery(name = "Phone.findByIdPhone", query = "SELECT p FROM Phone p WHERE p.idPhone = :idPhone"),
        @NamedQuery(name = "Phone.findByNumberPhone", query = "SELECT p FROM Phone p WHERE p.numberPhone = :numberPhone"),
        @NamedQuery(name = "Phone.findByStatus", query = "SELECT p FROM Phone p WHERE p.status = :status")})
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_phone")
    private Integer idPhone;
    @Basic(optional = false)
    @Column(name = "number_phone")
    private String numberPhone;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "id_contact_phone", referencedColumnName = "id_contact")
    @ManyToOne(optional = false)
    private Contact idContactPhone;

    public Phone() {
    }

    public Phone(Integer idPhone) {
        this.idPhone = idPhone;
    }

    public Phone(Integer idPhone, String numberPhone, int status) {
        this.idPhone = idPhone;
        this.numberPhone = numberPhone;
        this.status = status;
    }

    public Integer getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(Integer idPhone) {
        this.idPhone = idPhone;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Contact getIdContactPhone() {
        return idContactPhone;
    }

    public void setIdContactPhone(Contact idContactPhone) {
        this.idContactPhone = idContactPhone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPhone != null ? idPhone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Phone)) {
            return false;
        }
        Phone other = (Phone) object;
        if ((this.idPhone == null && other.idPhone != null) || (this.idPhone != null && !this.idPhone.equals(other.idPhone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectokajoy.ucb.edu.bo.clases.Phone[ idPhone=" + idPhone + " ]";
    }

}