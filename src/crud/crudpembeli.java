package crud;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;

public class crudpembeli { // Nama kelas diubah menjadi crudpembeli

    private Connection Koneksidb;
    private String username = "root";
    private String password = "";
    private String dbname = "db_pbo_2310010047";
    private String urlKoneksi = "jdbc:mysql://localhost/" + dbname;
    public String CEK_ID_PEMBELI, CEK_ALAMAT_PENGIRIMAN;
    public boolean duplikasi=false;

    public crudpembeli() {
        try {
            // Memuat driver MySQL
            Driver dbdriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb = DriverManager.getConnection(urlKoneksi, username, password);
            System.out.println("Database Terkoneksi (pembeli)"); // Pesan koneksi disesuaikan
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.toString());
        }
    }

    // --- OPERASI CRUD UNTUK TABEL PEMBELI ---

    // 1. Simpan data pembeli baru
    public void simpanPembeli(String id_pembeli, String id_admin, String alamat_pengiriman) {
        try {
            // Cek apakah ID pembeli sudah terdaftar
            String sqlcari = "SELECT id_pembeli FROM pembeli WHERE id_pembeli=?";
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_pembeli);
            ResultSet data = cari.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Pembeli sudah terdaftar!");
            } else {
                // Query INSERT disesuaikan untuk tabel 'pembeli'
                String sqlsimpan = "INSERT INTO pembeli (id_pembeli, id_admin, alamat_pengiriman) VALUES (?, ?, ?)";
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_pembeli);
                perintah.setString(2, id_admin);
                perintah.setString(3, alamat_pengiriman);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data pembeli berhasil disimpan!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 2. Ubah data pembeli
    public void ubahPembeli(String id_pembeli, String id_admin_baru, String alamat_pengiriman_baru) {
        try {
            // Query UPDATE disesuaikan untuk tabel 'pembeli'
            String sqlubah = "UPDATE pembeli SET id_admin=?, alamat_pengiriman=? WHERE id_pembeli=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, id_admin_baru);
            perintah.setString(2, alamat_pengiriman_baru);
            perintah.setString(3, id_pembeli); // Kunci untuk WHERE
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data pembeli berhasil diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 3. Hapus data pembeli
    public void hapusPembeli(String id_pembeli) {
        try {
            // Query DELETE disesuaikan untuk tabel 'pembeli'
            String sqlhapus = "DELETE FROM pembeli WHERE id_pembeli=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_pembeli);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data pembeli berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 4. Tampilkan data ke JTable
    public void tampilDataPembeli(JTable komponentabel, String SQL) {
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            // ResultSetMetaData meta = data.getMetaData(); // Tidak digunakan lagi untuk menentukan jumlah kolom

            DefaultTableModel modeltabel = new DefaultTableModel();
            // Header kolom disesuaikan untuk tabel 'pembeli'
            modeltabel.addColumn("ID Pembeli");
            modeltabel.addColumn("ID Admin");
            modeltabel.addColumn("Alamat Pengiriman");

            while (data.next()) {
                // Urutan dan jumlah data disesuaikan dengan kolom tabel 'pembeli'
                Object[] row = new Object[4]; // Sesuaikan jumlah kolom
                row[0] = data.getString("id_pembeli");
                row[1] = data.getString("id_admin");
                row[2] = data.getString("alamat_pengiriman");
                
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}