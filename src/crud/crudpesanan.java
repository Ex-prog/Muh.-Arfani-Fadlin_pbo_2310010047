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
import java.sql.Date; // Tambahkan import Date untuk penanganan tanggal

public class crudpesanan { // Nama kelas diubah menjadi crudpesanan

    private Connection Koneksidb;
    private String username = "root";
    private String password = "";
    private String dbname = "db_pbo_2310010047";
    private String urlKoneksi = "jdbc:mysql://localhost/" + dbname;
    public String CEK_ID_PEMBELI, CEK_ID_IKAN, CEK_JUMLAH, CEK_TANGGAL_PESANAN, CEK_TOTAL_HARGA, CEK_STATUS_PESANAN, CEK_METODE_PEMBAYARAN=null;
    public boolean duplikasi=false;

    public crudpesanan() {
        try {
            // Memuat driver MySQL
            Driver dbdriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb = DriverManager.getConnection(urlKoneksi, username, password);
            System.out.println("Database Terkoneksi (pesanan)"); // Pesan koneksi disesuaikan
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.toString());
        }
    }

    // --- OPERASI CRUD UNTUK TABEL PESANAN ---

    // 1. Simpan data pesanan baru
    public void simpanPesanan(String id_pesanan, String id_pembeli, String id_ikan, String jumlah, String tanggal_pesanan, String total_harga, String status_pesanan, String metode_pembayaran) {
        try {
            // Cek apakah ID pesanan sudah terdaftar
            String sqlcari = "SELECT id_pesanan FROM pesanan WHERE id_pesanan=?";
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_pesanan);
            ResultSet data = cari.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Pesanan sudah terdaftar!");
            } else {
                // Query INSERT disesuaikan untuk tabel 'pesanan'
                String sqlsimpan = "INSERT INTO pesanan (id_pesanan, id_pembeli, id_ikan, jumlah, tanggal_pesanan, total_harga, status_pesanan, metode_pembayaran) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_pesanan);
                perintah.setString(2, id_pembeli);
                perintah.setString(3, id_ikan);
                perintah.setString(4, jumlah); // Menggunakan setInt
                
                // Konversi String ke java.sql.Date
                java.sql.Date tglPesanan = java.sql.Date.valueOf(tanggal_pesanan); 
                perintah.setDate(5, tglPesanan); // Menggunakan setDate
                
                perintah.setString(6, total_harga); // Menggunakan setDouble
                perintah.setString(7, status_pesanan);
                perintah.setString(8, metode_pembayaran);

                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data pesanan berhasil disimpan!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IllegalArgumentException e) {
            // Menangkap error jika format tanggal salah
            JOptionPane.showMessageDialog(null, "Format Tanggal salah. Gunakan format YYYY-MM-DD.");
        }
    }

    // 2. Ubah data pesanan
    public void ubahPesanan(String id_pesanan, String id_pembeli_baru, String id_ikan_baru, String jumlah_baru, String tanggal_pesanan_baru, String total_harga_baru, String status_pesanan_baru, String metode_pembayaran_baru) {
        try {
            // Query UPDATE disesuaikan untuk tabel 'pesanan'
            String sqlubah = "UPDATE pesanan SET id_pembeli=?, id_ikan=?, jumlah=?, tanggal_pesanan=?, total_harga=?, status_pesanan=?, metode_pembayaran=? WHERE id_pesanan=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            
            perintah.setString(1, id_pembeli_baru);
            perintah.setString(2, id_ikan_baru);
            perintah.setString(3, jumlah_baru);
            
            // Konversi String ke java.sql.Date
            java.sql.Date tglPesanan = java.sql.Date.valueOf(tanggal_pesanan_baru); 
            perintah.setDate(4, tglPesanan);
            
            perintah.setString(5, total_harga_baru);
            perintah.setString(6, status_pesanan_baru);
            perintah.setString(7, metode_pembayaran_baru);
            perintah.setString(8, id_pesanan); // Kunci untuk WHERE

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data pesanan berhasil diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(null, "Format Tanggal salah. Gunakan format YYYY-MM-DD.");
        }
    }

    // 3. Hapus data pesanan
    public void hapusPesanan(String id_pesanan) {
        try {
            // Query DELETE disesuaikan untuk tabel 'pesanan'
            String sqlhapus = "DELETE FROM pesanan WHERE id_pesanan=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_pesanan);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data pesanan berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // 4. Tampilkan data ke JTable
    public void tampilDataPesanan(JTable komponentabel, String SQL) {
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();

            DefaultTableModel modeltabel = new DefaultTableModel();
            // Header kolom disesuaikan untuk tabel 'pesanan'
            modeltabel.addColumn("ID Pesanan");
            modeltabel.addColumn("ID Pembeli");
            modeltabel.addColumn("ID Ikan");
            modeltabel.addColumn("Jumlah");
            modeltabel.addColumn("Tanggal Pesanan");
            modeltabel.addColumn("Total Harga");
            modeltabel.addColumn("Status");
            modeltabel.addColumn("Metode Bayar");

            while (data.next()) {
                // Urutan dan jumlah data disesuaikan dengan kolom tabel 'pesanan'
                Object[] row = new Object[8];
                row[0] = data.getString("id_pesanan");
                row[1] = data.getString("id_pembeli");
                row[2] = data.getString("id_ikan");
                row[3] = data.getInt("jumlah");
                row[4] = data.getDate("tanggal_pesanan"); // Menggunakan getDate()
                row[5] = data.getDouble("total_harga");
                row[6] = data.getString("status_pesanan");
                row[7] = data.getString("metode_pembayaran");
                
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}