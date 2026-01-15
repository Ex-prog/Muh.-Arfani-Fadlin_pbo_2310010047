package crud;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;

public class crudadmin {
   
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="db_pbo_2310010047"; // disesuaikan dengan nama database kamu
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_EMAIL, CEK_PASSWORD=null;
    public boolean duplikasi=false;
    
    public crudadmin(){
        try {
            Driver dbdriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.println("Database Terkoneksi");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    // Simpan data admin
    public void simpanAdmin(String id_admin, String nama, String email, String pass){
        try {
            String sqlcari="SELECT * FROM admin WHERE id_admin=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_admin);
            ResultSet data=cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Admin sudah terdaftar!");
            } else {
                String sqlsimpan="INSERT INTO admin (id_admin, nama, email, password) VALUES (?, ?, ?, ?)";
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_admin);
                perintah.setString(2, nama);
                perintah.setString(3, email);
                perintah.setString(4, pass);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    // Ubah data admin
    public void ubahAdmin(String id_admin, String nama, String email, String pass){
        try {
            String sqlubah="UPDATE admin SET nama=?, email=?, password=? WHERE admin_id=?";
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nama);
            perintah.setString(2, email);
            perintah.setString(3, pass);
            perintah.setString(4, id_admin);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Hapus data admin
    public void hapusAdmin(String id_admin){
        try {
            String sqlhapus="DELETE FROM admin WHERE id_admin=?";
            PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_admin);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Tampilkan data ke JTable
    public void tampilDataAdmin(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();

            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("id_admin");
            modeltabel.addColumn("nama");
            modeltabel.addColumn("email");
            modeltabel.addColumn("password");

            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
