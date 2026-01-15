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

public class crudpenjual { // Nama kelas diubah menjadi crudpenjual

    private Connection Koneksidb;
    private String username = "root";
    private String password = "";
    private String dbname = "db_pbo_2310010047";
    private String urlKoneksi = "jdbc:mysql://localhost/" + dbname;
    public String CEK_ID_ADMIN, CEK_NAMA_TOKO, CEK_ALAMAT_TOKO=null;
    public boolean duplikasi=false;

    public crudpenjual() {
        try {
            // Memuat driver MySQL
            Driver dbdriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb = DriverManager.getConnection(urlKoneksi, username, password);
            System.out.println("Database Terkoneksi (penjual)"); // Pesan koneksi disesuaikan
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.toString());
        }
    }

    // --- OPERASI CRUD UNTUK TABEL PENJUAL ---

    // 1. Simpan data penjual baru
    public void simpanPenjual(String id_penjual, String id_admin, String nama_toko, String alamat_toko) {
        try {
            // Cek apakah ID penjual sudah terdaftar
            String sqlcari = "SELECT id_penjual FROM penjual WHERE id_penjual=?";
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_penjual);
            ResultSet data = cari.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Penjual sudah terdaftar!");
            } else {
                // Query INSERT disesuaikan untuk tabel 'penjual'
                String sqlsimpan = "INSERT INTO penjual (id_penjual, id_admin, nama_toko, alamat_toko) VALUES (?, ?, ?, ?)";
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_penjual);
                perintah.setString(2, id_admin);
                perintah.setString(3, nama_toko);
                perintah.setString(4, alamat_toko);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data penjual berhasil disimpan!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 2. Ubah data penjual
    public void ubahPenjual(String id_penjual, String id_admin_baru, String nama_toko_baru, String alamat_toko_baru) {
        try {
            // Query UPDATE disesuaikan untuk tabel 'penjual'
            String sqlubah = "UPDATE penjual SET id_admin=?, nama_toko=?, alamat_toko=? WHERE id_penjual=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, id_admin_baru);
            perintah.setString(2, nama_toko_baru);
            perintah.setString(3, alamat_toko_baru);
            perintah.setString(4, id_penjual); // Kunci untuk WHERE
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data penjual berhasil diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 3. Hapus data penjual
    public void hapusPenjual(String id_penjual) {
        try {
            // Query DELETE disesuaikan untuk tabel 'penjual'
            String sqlhapus = "DELETE FROM penjual WHERE id_penjual=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_penjual);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data penjual berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 4. Tampilkan data ke JTable
    public void tampilDataPenjual(JTable komponentabel, String SQL) {
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();

            DefaultTableModel modeltabel = new DefaultTableModel();
            // Header kolom disesuaikan untuk tabel 'penjual'
            modeltabel.addColumn("ID Penjual");
            modeltabel.addColumn("ID Admin");
            modeltabel.addColumn("Nama Toko");
            modeltabel.addColumn("Alamat Toko");

            while (data.next()) {
                // Urutan dan jumlah data disesuaikan dengan kolom tabel 'penjual'
                Object[] row = new Object[4];
                row[0] = data.getString("id_penjual");
                row[1] = data.getString("id_admin");
                row[2] = data.getString("nama_toko");
                row[3] = data.getString("alamat_toko");
                
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}