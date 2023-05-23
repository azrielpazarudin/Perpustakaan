/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.*;
import javax.swing.JOptionPane;

public class Database {
    public Connection Conn;
    public Connection callConn(){
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan","root","");
            System.out.println("Koneksi Berhasil");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Koneksi gagal");
        }
        return Conn;
    }
    
    
}
