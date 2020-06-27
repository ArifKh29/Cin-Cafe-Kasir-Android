package com.cafe.pos;

public class HistoryMdl {
    private String nama;
    private String idtrx;
    private String tanggal;
//    private String id;
    private String jumlah;

    public  HistoryMdl(){}
    public HistoryMdl(String idtrx, String nama, String tanggal, String total){
        this.nama = nama;
        this.idtrx = idtrx;
        this.tanggal = tanggal;
//        this.id = id;
        this.jumlah = total;
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

    public String getIdtrx() {
        return idtrx;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setIdtrx(String idtrx) {
        this.idtrx = idtrx;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
