package com.digimaster.digicourse.digicyber.Model;

/**
 * Created by Dell on 22-Mar-18.
 */

public class PaymentItem {
    public String nama_bank, logoImg;

    public PaymentItem(String nama_bank, String logoImg) {
        this.nama_bank = nama_bank;
        this.logoImg = logoImg;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }
}
