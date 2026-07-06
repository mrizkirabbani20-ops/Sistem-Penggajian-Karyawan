package Koneksi;
import java.sql.*;
public class db {
    private Connection koneksi;
    public Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil Koneksi");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Gagal Koneksi");
        }
        String url="jdbc:mysql://localhost/sistempenggajiankaryawan";
        try{
            koneksi = DriverManager.getConnection(url, "root", "");
            System.out.println("Berhasil Koneksi Database");
        }
        catch(SQLException ex){
            System.out.println("Gagal Koneksi Database");
        }
        return koneksi;
    }
}