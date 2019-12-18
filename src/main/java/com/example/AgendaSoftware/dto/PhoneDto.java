package com.example.AgendaSoftware.dto;

import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.domain.Phone;

public class PhoneDto {

    private Integer idPhone;
    private String numberPhone;
    private int status;
    private Contact idContactPhone;

    public PhoneDto() {
    }

    public PhoneDto(Phone phone){
        this.idPhone=phone.getIdPhone();
        this.numberPhone=phone.getNumberPhone();
        this.status= phone.getStatus();
        this.idContactPhone = phone.getIdContactPhone();
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
}
