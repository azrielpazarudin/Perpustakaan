/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DAO {

    Connection Conn;
    Database myDB = new Database();
    Statement stm;
    ResultSet rs;
    DefaultTableModel dtm;
    ArrayList<String> myBuff = new ArrayList<>();

    public DAO() {
        Conn = myDB.callConn();
    }

    public boolean login(Model mod) throws SQLException {
        boolean found = false;
        boolean succes = false;
        stm = Conn.createStatement();
        rs = stm.executeQuery("select *from akun where username='" + mod.getUsername() + "' and password='" + mod.getPassword() + "'");
        while (rs.next()) {
        
            found = true;
        }
        if (found) {
            JOptionPane.showMessageDialog(null, "LOGIN BERHASIL", "PERPUSTAKAAN KU : LOGIN", JOptionPane.INFORMATION_MESSAGE);
            succes = true;
        } else {
            JOptionPane.showMessageDialog(null, "LOGIN GAGAL", "PERPUSTAKAAN KU : LOGIN", JOptionPane.WARNING_MESSAGE);;
        }
        return succes;
    }

    public boolean statusChecker(String x) {
        boolean buffer =false;
        try {
            stm = Conn.createStatement();
            rs = stm.executeQuery("select status from petugas where username='" + x + "'");
            while (rs.next()) {
                buffer =true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("nilia x = "+x);

        return buffer;
    }

    public ArrayList<String> namaPeminjam() throws SQLException {
        stm = Conn.createStatement();
        myBuff.clear();
        rs = stm.executeQuery("select nama from anggota");
        while (rs.next()) {
            myBuff.add(rs.getString("nama"));

        }
        return myBuff;

    }

    public ArrayList<String> namaBuku() throws SQLException {
        stm = Conn.createStatement();
        myBuff.clear();
        rs = stm.executeQuery("select namaBuku from buku");
        while (rs.next()) {
            myBuff.add(rs.getString("namaBuku"));

        }
        return myBuff;

    }

    public int createPeminjaman(Model mod) throws SQLException {

        stm = Conn.createStatement();

        rs = stm.executeQuery("select id from anggota where nama = '" + mod.getNama() + "'");
        while (rs.next()) {
            mod.setId(rs.getString("id"));
        }
        int result = 0;
        result = stm.executeUpdate("INSERT IGNORE INTO buku (idBuku,namaBuku) VALUES ('" + mod.getIdBuku() + "', '" + mod.getNamaBuku() + "')");
        result = stm.executeUpdate("insert into peminjaman values ('" + mod.getIdPeminjaman() + "','" + mod.getIdBuku() + "','" + mod.getNamaBuku() + "','" + mod.getId() + "','" + mod.getNama() + "')");
        return result;
    }

    public DefaultTableModel showData() throws SQLException {
        try {
            stm = Conn.createStatement();
            dtm = new DefaultTableModel(new String[]{"Id", "Nama", "Jurusan"}, 0);
            rs = stm.executeQuery("select *from anggota");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getString("id"), rs.getString("nama"), rs.getString("jurusan")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dtm;
    }
     public DefaultTableModel showBuku() throws SQLException {
        try {
            stm = Conn.createStatement();
            dtm = new DefaultTableModel(new String[]{"Id", "Nama", "Status"}, 0);
            rs = stm.executeQuery("select *from buku");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getString("idBuku"), rs.getString("namaBuku"), rs.getString("status")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dtm;
    }
    
     public DefaultTableModel searchData(String by) throws SQLException {
        try {
            stm = Conn.createStatement();
            dtm = new DefaultTableModel(new String[]{"Id", "Nama", "Jurusan"}, 0);
            rs = stm.executeQuery ("SELECT *FROM anggota WHERE id LIKE '%"+by+"%' OR nama LIKE '%"+by+"%';");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getString("id"), rs.getString("nama"), rs.getString("jurusan")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dtm;
    }

    public DefaultTableModel showPeminjaman() throws SQLException {
        try {
            stm = Conn.createStatement();
            dtm = new DefaultTableModel(new String[]{"Id Peminjaman", "Id Buku", "Nama Buku", "Nama Peminjam"}, 0);
            rs = stm.executeQuery("select *from peminjaman");
            while (rs.next()) {
                dtm.addRow(new Object[]{rs.getString("idPeminjaman"), rs.getString("idBuku"), rs.getString("namaBuku"), rs.getString("namaAnggota")});
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dtm;
    }
    
    
    
    

    public String idChecker(Model mod) throws SQLException {
        int counter = 1;
        boolean found = false;
        String seeker = "";
        for (int x = 0; x < 1;) {
            if (counter < 10) {
                seeker = "AG00" + counter;
            } else if (counter < 100) {
                seeker = "AG0" + counter;
            } else {
                seeker = "AG" + counter;
            }

            stm = Conn.createStatement();
            rs = stm.executeQuery("select *from anggota where id = '" + seeker + "'");
            while (rs.next()) {
                found = true;
            }
            if (found) {
                counter++;
                System.out.println(counter);
                found = false;
            } else {
                x++;
                mod.setId(seeker);
            }
        }
        return seeker;
    }

    public boolean usernameChecker(Model mod) {
        boolean found = false;
        try {
            stm = Conn.createStatement();
            rs = stm.executeQuery("select *from akun where username = '" + mod.getUsername() + "'");
            while (rs.next()) {
                found = true;
            }
            if (!found) {
                found = true;
            } else {
                JOptionPane.showMessageDialog(null, "Username Tidak Boleh Sama", "PERPUSTAKAAN KU : INSERT", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    public int insertData(Model mod) {
        int result = 0;

        try {
            result = stm.executeUpdate("insert into akun values ('" + mod.getUsername() + "','" + mod.getPassword() + "')");
            result = stm.executeUpdate("insert into anggota values ('" + mod.getId() + "','" + mod.getNama() + "','" + mod.getJurusan() + "','" + mod.getUsername() + "','" + mod.getPassword() + "','ST003','ANGGOTA')");
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int editData(Model mod) {
        int result = 0;
        try {
            stm = Conn.createStatement();
            result = stm.executeUpdate("update anggota set nama='" + mod.getNama() + "',jurusan='" + mod.getJurusan() + "' where id = '" + mod.getId() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int deleteData(Model mod) {
        int result = 0;
        try {
            stm = Conn.createStatement();
            result = stm.executeUpdate("delete from anggota where id = '" + mod.getId() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
