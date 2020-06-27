package com.cafe.pos;

public class FoodMdl {
    private String nama;
    private String harga;
    private String jenis;
    private String id;
    private byte[] img;
    private String hot;
    private String ice;


    public  FoodMdl(){}
    public FoodMdl(String nama, String harga, String jenis, String id, byte[] img, String hot, String ice){
        this.nama = nama;
        this.harga = harga;
        this.jenis = jenis;
        this.id = id;
        this.img = img;
        this.hot = hot;
        this.ice = ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getIce() {
        return ice;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getHot() {
        return hot;
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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
