package com.glg204.wotstore.client.domain;

import com.glg204.wotstore.authentification.domain.WOTUser;

public class Client {

    private WOTUser wotUser;

    private String telephone;

    private String address;

    public Client(WOTUser wotUser) {
        this.wotUser = wotUser;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public WOTUser getWotUser() {
        return wotUser;
    }

    public void setWotUser(WOTUser wotUser) {
        this.wotUser = wotUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
