package com.cafe.pos;

public class CartMdl {
    private String nama;
    private String harga;
    private String jenis;
    private String id;


    public  CartMdl(){}
    public CartMdl(String nama, String harga, String jenis, String id){
        this.nama = nama;
        this.harga = harga;
        this.jenis = jenis;
        this.id = id;
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

    public String getJenis(){
        return jenis;
    }

    public void setJenis(String jenis){
        this.jenis = jenis;
    }

    public String  getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
