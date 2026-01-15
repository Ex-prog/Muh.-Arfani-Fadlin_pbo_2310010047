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

public class crudjenisikan {

    private Connection Koneksidb;
    private String username = "root";
    private String password = "";
    private String dbname = "db_pbo_2310010047"; // disesuaikan
    private String urlKoneksi = "jdbc:mysql://localhost/" + dbname;
        public String CEK_NAMA_IKAN, CEK_KATEGORI,CEK_HARGA, CEK_STOK;
    public boolean duplikasi=false;

    public crudjenisikan() {
        try {
            Driver dbdriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb = DriverManager.getConnection(urlKoneksi, username, password);
            System.out.println("Database Terkoneksi (jenis_ikan)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    // Simpan data ikan baru
    public void simpanIkan(String id_ikan, String id_penjual, String nama_ikan, String kategori, String harga, String stok) {
        try {
            String sqlcari = "SELECT * FROM jenis_ikan WHERE id_ikan=?";
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id_ikan);
            ResultSet data = cari.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Ikan sudah terdaftar!");
            } else {
                String sqlsimpan = "INSERT INTO jenis_ikan (id_ikan, id_penjual, nama_ikan, kategori, harga, stok) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id_ikan);
                perintah.setString(2, id_penjual);
                perintah.setString(3, nama_ikan);
                perintah.setString(4, kategori);
                perintah.setString(5, harga);
                perintah.setString(6, stok);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data ikan berhasil disimpan!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Ubah data ikan
    public void ubahIkan(String id_ikan, String id_penjual, String nama_ikan, String kategori, double harga, int stok) {
        try {
            String sqlubah = "UPDATE jenis_ikan SET id_penjual=?, nama_ikan=?, kategori=?, harga=?, stok=? WHERE id_ikan=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, id_penjual);
            perintah.setString(2, nama_ikan);
            perintah.setString(3, kategori);
            perintah.setDouble(4, harga);
            perintah.setInt(5, stok);
            perintah.setString(6, id_ikan);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data ikan berhasil diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Hapus data ikan
    public void hapusIkan(String id_ikan) {
        try {
            String sqlhapus = "DELETE FROM jenis_ikan WHERE id_ikan=?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id_ikan);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data ikan berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Tampilkan data ke JTable
    public void tampilDataIkan(JTable komponentabel, String SQL) {
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();

            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("id_ikan");
            modeltabel.addColumn("id_penjual");
            modeltabel.addColumn("nama_ikan");
            modeltabel.addColumn("kategori");
            modeltabel.addColumn("harga");
            modeltabel.addColumn("stok");

            while (data.next()) {
                Object[] row = new Object[jumlahkolom];
                for (int i = 1; i <= jumlahkolom; i++) {
                    row[i - 1] = data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
