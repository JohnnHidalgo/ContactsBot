package com.example.AgendaSoftware.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "contact")
@NamedQueries({
        @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
        @NamedQuery(name = "Contact.findByIdContact", query = "SELECT c FROM Contact c WHERE c.idContact = :idContact"),
        @NamedQuery(name = "Contact.findByFirstName", query = "SELECT c FROM Contact c WHERE c.firstName = :firstName"),
        @NamedQuery(name = "Contact.findBySecondName", query = "SELECT c FROM Contact c WHERE c.secondName = :secondName"),
        @NamedQuery(name = "Contact.findByFirstLastName", query = "SELECT c FROM Contact c WHERE c.firstLastName = :firstLastName"),
        @NamedQuery(name = "Contact.findBySecondLastName", query = "SELECT c FROM Contact c WHERE c.secondLastName = :secondLastName"),
        @NamedQuery(name = "Contact.findByMail", query = "SELECT c FROM Contact c WHERE c.mail = :mail"),
        @NamedQuery(name = "Contact.findByDateBorn", query = "SELECT c FROM Contact c WHERE c.dateBorn = :dateBorn"),
        @NamedQuery(name = "Contact.findByImage", query = "SELECT c FROM Contact c WHERE c.image = :image"),
        @NamedQuery(name = "Contact.findByStatus", query = "SELECT c FROM Contact c WHERE c.status = :status")})
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contact")
    private Integer idContact;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Basic(optional = false)
    @Column(name = "first_last_name")
    private String firstLastName;
    @Basic(optional = false)
    @Column(name = "second_last_name")
    private String secondLastName;
    @Basic(optional = false)
    @Column(name = "mail")
    private String mail;
    @Basic(optional = false)
    @Column(name = "date_born")
    @Temporal(TemporalType.DATE)
    private Date dateBorn;
    @Basic(optional = false)
    @Column(name = "image")
    private String image;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContactPhone")
    private List<Phone> phoneList;
    @JoinColumn(name = "id_user_contact", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User idUserContact;

    public Contact() {
    }

    public Contact(Integer idContact) {
        this.idContact = idContact;
    }

    public Contact(Integer idContact, String firstName, String firstLastName, String secondLastName, String mail, Date dateBorn, String image, int status) {
        this.idContact = idContact;
        this.firstName = firstName;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.mail = mail;
        this.dateBorn = dateBorn;
        this.image = image;
        this.status = status;
    }

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public User getIdUserContact() {
        return idUserContact;
    }

    public void setIdUserContact(User idUserContact) {
        this.idUserContact = idUserContact;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContact != null ? idContact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.idContact == null && other.idContact != null) || (this.idContact != null && !this.idContact.equals(other.idContact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "idContact=" + idContact +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", firstLastName='" + firstLastName + '\'' +
                ", secondLastName='" + secondLastName + '\'' +
                ", mail='" + mail + '\'' +
                ", dateBorn='" + dateBorn + '\'' +
                ", image=" + image +
                ", status='" + status + '\'' +
                ", phoneList='" + phoneList  + '\'' +
                ", idUserContact=" + idUserContact +
                '}';
    }

}