package com.example.AgendaSoftware.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "chat")
@NamedQueries({
        @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c"),
        @NamedQuery(name = "Chat.findByIdChat", query = "SELECT c FROM Chat c WHERE c.idChat = :idChat"),
        @NamedQuery(name = "Chat.findByInMessage", query = "SELECT c FROM Chat c WHERE c.inMessage = :inMessage"),
        @NamedQuery(name = "Chat.findByOutMessage", query = "SELECT c FROM Chat c WHERE c.outMessage = :outMessage"),
        @NamedQuery(name = "Chat.findByDateMessage", query = "SELECT c FROM Chat c WHERE c.dateMessage = :dateMessage"),
        @NamedQuery(name = "Chat.findByTxUser", query = "SELECT c FROM Chat c WHERE c.txUser = :txUser"),
        @NamedQuery(name = "Chat.findByTxHost", query = "SELECT c FROM Chat c WHERE c.txHost = :txHost"),
        @NamedQuery(name = "Chat.findByTxDate", query = "SELECT c FROM Chat c WHERE c.txDate = :txDate")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chat")
    private Integer idChat;
    @Basic(optional = false)
    @Column(name = "in_message")
    private String inMessage;
    @Basic(optional = false)
    @Column(name = "out_message")
    private String outMessage;
    @Basic(optional = false)
    @Column(name = "date_message")
    @Temporal(TemporalType.DATE)
    private Date dateMessage;
    @Basic(optional = false)
    @Column(name = "tx_user")
    private String txUser;
    @Basic(optional = false)
    @Column(name = "tx_host")
    private String txHost;
    @Basic(optional = false)
    @Column(name = "tx_date")
    @Temporal(TemporalType.DATE)
    private Date txDate;
    @JoinColumn(name = "id_user_chat", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User idUserChat;

    public Chat() {
    }

    public Chat(Integer idChat) {
        this.idChat = idChat;
    }

    public Chat(Integer idChat, String inMessage, String outMessage, Date dateMessage, String txUser, String txHost, Date txDate) {
        this.idChat = idChat;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
        this.dateMessage = dateMessage;
        this.txUser = txUser;
        this.txHost = txHost;
        this.txDate = txDate;
    }

    public Integer getIdChat() {
        return idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public String getInMessage() {
        return inMessage;
    }

    public void setInMessage(String inMessage) {
        this.inMessage = inMessage;
    }

    public String getOutMessage() {
        return outMessage;
    }

    public void setOutMessage(String outMessage) {
        this.outMessage = outMessage;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getTxUser() {
        return txUser;
    }

    public void setTxUser(String txUser) {
        this.txUser = txUser;
    }

    public String getTxHost() {
        return txHost;
    }

    public void setTxHost(String txHost) {
        this.txHost = txHost;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public User getIdUserChat() {
        return idUserChat;
    }

    public void setIdUserChat(User idUserChat) {
        this.idUserChat = idUserChat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChat != null ? idChat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.idChat == null && other.idChat != null) || (this.idChat != null && !this.idChat.equals(other.idChat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyectokajoy.ucb.edu.bo.clases.Chat[ idChat=" + idChat + " ]";
    }

}