package com.cafe.pos;

public class CartMdl {
    private String nama;
    private String harga;
    private String ket;
    private String id;
    private String jumlah;

    public  CartMdl(){}
    public CartMdl(String nama, String harga, String ket, String id, String jumlah){
        this.nama = nama;
        this.harga = harga;
        this.ket = ket;
        this.id = id;
        this.jumlah = jumlah;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKet(){
        return ket;
    }

    public void setKet(String jenis){
        this.ket = jenis;
    }

    public String  getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
