package com.cafe.pos;

public class UserMdl {
    private String nama;
    private String iduser;
    private String username;
//    private String id;
    private String status;

    public UserMdl(){}
    public UserMdl(String iduser, String nama, String username, String status){
        this.nama = nama;
        this.iduser = iduser;
        this.username = username;
//        this.id = id;
        this.status = status;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getIduser() {
        return iduser;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
