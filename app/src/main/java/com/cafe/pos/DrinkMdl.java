package com.cafe.pos;

public class DrinkMdl {
    private String nama;
    private String hot;
    private String ice;
    private String jenis;
    private String id;
    private byte[] img;

    public DrinkMdl(){}
    public DrinkMdl(String nama, String hot, String ice, String jenis, String id, byte[] img){
        this.nama = nama;
        this.hot = hot;
        this.ice = ice;
        this.jenis = jenis;
        this.id = id;
        this.img = img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public String getNama() {
        return nama;
    }

    public String getHot() {
        return hot;
    }

    public String getIce() {
        return ice;
    }

    public String getId() {
        return id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
