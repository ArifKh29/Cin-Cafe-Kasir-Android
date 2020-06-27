package com.cafe.pos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="pos.db";
    private static final int DATABASE_VERSION= 1;
    private static final String CREATE_TABLE1="CREATE TABLE menu(ID INTEGER PRIMARY KEY AUTOINCREMENT, nama_menu VARCHAR(255), harga INTEGER(15), jenis CHAR(20), `status` varchar(7), `image` blob)";
    private static final String CREATE_TABLE2="CREATE TABLE cart(id_cart INTEGER PRIMARY KEY AUTOINCREMENT, id_trx VARCHAR(7), nama_pesanan VURCHAR(255), harga INTEGER(15), ket VARCHAR(5),  jumlah INTEGER(3), subtotal INTEGER(10))";
    private static final String CREATE_TABLE5="CREATE TABLE `drink` ( `id` int(11) NOT NULL,`nama_drink` varchar(70) NOT NULL, `hot_price` int(12) NOT NULL,`ice_price` int(12) NOT NULL,`jenis` varchar(50),`status` varchar(7) NOT NULL, `image` varchar(40))";
    private static final String CREATE_TABLE3="CREATE TABLE transaksi(id_trx VARCHAR(7) PRIMARY KEY, id_user VARCHAR(7), tanggal DATE, total INTEGER, bayar INTEGER, kembalian INTEGER)";
    private static final String CREATE_TABLE4="CREATE TABLE user(id_user VARCHAR(7), username VARCHAR(10), nama CHAR, password VARCHAR(12), status CHAR(5))";
    //private static final String DROP_TABLE="DROP TABLE  IF EXISTS "+TABLE_NAME;
    public static final String KEY_ID = "id_user";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_NAMA = "nama";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    private Context context;
    public DataHelper (Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
        Massage.message(context,"Ready To Use");
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
            db.execSQL(CREATE_TABLE3);
            db.execSQL(CREATE_TABLE4);
            db.execSQL(CREATE_TABLE5);
            String create_admin = "INSERT INTO user (id_user, username, nama, password, status) VALUES ('US0001', 'admin','Admin','admin','admin'),('US0002', 'user','user','user','user')";
            db.execSQL(create_admin);

//            Massage.message(context,"onCreate dipanggil");
        } catch (SQLiteException e){
            Massage.message(context,""+e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
//        try{
//            Massage.message(context,"onUpgrade dipanggil");
//            db.execSQL(DROP_TABLE1);
//            onCreate(db);
//        }catch (SQLiteException e){
//            Massage.message(context,""+e);
//        }
    }

    public int getMenu() {
        String countQuery = "SELECT  * FROM menu";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void defaultMenu(){
        String insert = "INSERT INTO `menu` (`id`, `nama_menu`, `harga`, `jenis`, `status`, `image`) VALUES\n" +
                "(1, 'Ayam Gepuk Milenial', 15000, 'Food', 'ready', null),\n" +
                "(2, 'Nasi Kebuli', 20000, 'Food', 'ready', 'null'),\n" +
                "(3, 'Kentang Goreng', 10000, 'Food', 'ready', null),\n" +
                "(4, 'Spageti Carbonara', 24000, 'Food', 'ready', null),\n" +
                "(5, 'Pisang Goreng (biji)', 2000, 'Food', 'ready', null),\n" +
                "(6, 'Singkong Goreng (porsi)', 10000, 'Food', 'ready', null),\n" +
                "(7, 'Roti Bakar (biji)', 10000, 'Food', 'ready', null),\n" +
                "(8, 'Tahu Goreng (porsi)', 10000, 'Food', 'ready', null)";

        String drink = "INSERT INTO `drink` (`id`, `nama_drink`, `hot_price`, `ice_price`, `jenis`, `status`) VALUES\n" +
                "(1, 'Aren Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(2, 'Klepon Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(3, 'Banana Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(4, 'Melon Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(5, 'Vanilla Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(6, 'Caramel Machiato', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(7, 'Honey Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(8, 'Hazelnut Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(9, 'Marie Latte', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(10, 'Cafe Latee', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(11, 'Americano', 20000, 18000, 'Cinnar Espresso', 'ready'),\n" +
                "(12, 'Chocolate', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(13, 'Chocolate Banana', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(14, 'Matcha', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(15, 'Red Velvet', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(16, 'Taro', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(17, 'Banana Milkshake', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(18, 'Lemon Tea', 20000, 18000, 'Non Coffe', 'ready'),\n" +
                "(19, 'Thai Tea', 20000, 18000, 'Non Coffe', 'ready')";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(drink);
        db.execSQL(insert);
    }

    public void addtoCart(String idtrx, String idmenu, String nama, String harga,String ket, String dbjumlah, String subtotal){
        String insertcart = "INSERT INTO `cart` (`id_trx`, `nama_pesanan`, `harga`, `ket`, `jumlah`, subtotal) VALUES ('"+idtrx+"','"+nama+"','"+harga+"','"+ket+"','"+dbjumlah+"','"+subtotal+"')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertcart);
    }

    public String genID(){
        String idhasil = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT max(id_trx) as lastid FROM transaksi",null);
        while(cursor.moveToNext()){
            if (cursor.moveToLast() == false){
                idhasil = "TR-00001";

            }
            else {
                cursor.moveToLast();
                System.out.println("ahah"+cursor.moveToLast());
                String lastid = cursor.getString(0);
                System.out.println("LAST ID"+lastid);
                if(lastid==null){
                    idhasil = "TR-00001";
                }else {
                    String[] split = lastid.split("-");
                    String no = String.valueOf(Integer.parseInt(split[1])+1);
                    int nomor = no.length();
                    System.out.println("nomor "+nomor);
                    for(int i=0;i<5-nomor;i++){
                        no = "0"+ no;
                    }
                    idhasil = "TR-"+no;
                }

//                int lastid = cursor.getInt(0)+1;
//                System.out.println("LAST ID"+lastid);
//                String no = String.valueOf(lastid);
//                int nomor = no.length();
//                for(int i=0;i<5-nomor;i++){
//                    no = "0"+ no;
//                }
//                idhasil = "TR-"+no;
            }

        }
        return idhasil;
    }

    public Cursor showMenu(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM menu",null);
        return cursor;
    }

    public Cursor showCoffe(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM drink WHERE jenis='Cinnar Espresso'",null);
        return cursor;
    }

    public Cursor getUserdata(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username='"+username+"'",null);
        return cursor;
    }

    public Cursor getfood(String id_food){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM menu WHERE ID='"+id_food+"'",null);
        return cursor;
    }

    public Cursor showCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        String idtrx = genID();
        System.out.println(idtrx);
        Cursor cursor = db.rawQuery("SELECT id_cart,id_trx,nama_pesanan,harga,ket,jumlah,subtotal FROM cart WHERE id_trx ='"+idtrx+"'",null);
        return cursor;
    }

    public int countCart(){
        String idtrx = genID();
        String countQuery = "SELECT  * FROM cart WHERE id_trx='"+idtrx+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int totalCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        String idtrx = genID();
        System.out.println("alalala"+countCart());
        String selectQuery = "SELECT SUM(subtotal) FROM cart WHERE id_trx='"+idtrx+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int value = 0;
        int count = cursor.getCount();

        if(cursor.moveToNext()){
            value = Integer.parseInt(cursor.getString(0));
//            System.out.println("haha"+value);
//            Log.d("db", );
        }
        return value;
    }

    public void addTransaksi(String iduser, String tgl, String total, String bayar, String kembalian){
        String idtrx = genID();
        String insertTransaksi = "INSERT INTO TRANSAKSI (id_trx, id_user, tanggal, total, bayar, kembalian) VALUES ('"+idtrx+"', '"+iduser+"', '"+tgl+"', '"+total+"', '"+bayar+"', '"+kembalian+"')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertTransaksi);
    }




    public boolean addMenu(String nama, String harga, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String addmenu = "INSERT INTO menu (nama_menu, harga, image) VALUES ('"+nama+"', '"+harga+"', '"+image+"')";
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_menu", nama);
        contentValues.put("harga", harga);
        contentValues.put("image", image);
        long result = db.insert("menu",null,contentValues);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean addMinum(String namaminum, String hargahot,String hargaice, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String addmenu = "INSERT INTO menu (nama_menu, harga, image) VALUES ('"+nama+"', '"+harga+"', '"+image+"')";
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_drink", namaminum);
        contentValues.put("hot_price", hargahot);
        contentValues.put("ice_price", hargaice);
        contentValues.put("image", image);
        long result = db.insert("drink",null,contentValues);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user",// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_NAMA, KEY_PASSWORD, KEY_STATUS},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{user.userName},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public void hpsCart(String id_cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM cart WHERE id_cart='"+id_cart+"'";
        db.execSQL(query);

    }

    public void ubahCart(String id_cart, String jml, String subtotal) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE cart SET jumlah='"+jml+"', subtotal='"+subtotal+"' WHERE id_cart='"+id_cart+"'";
        db.execSQL(query);
    }

    public boolean updateMenu(String id_food, String nama, String harga, byte[] image) {
        String strFilter = "ID=" + id_food;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_menu", nama);
        contentValues.put("harga", harga);
        contentValues.put("image", image);
        long result = db.update("menu",contentValues, strFilter, null);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
//        String update = "UPDATE menu SET nama_menu='"+nama+"', harga='"+harga+"', image='"+image+"' WHERE ID='"+id_food+"'";
//        db.execSQL(update);
    }
}

