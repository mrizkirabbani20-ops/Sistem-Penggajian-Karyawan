/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Transaksi;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import Koneksi.db;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
/**
 *
 * @author ASUS
 */
public class FormPenggajianKaryawan extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormPenggajianKaryawan.class.getName());
    public String id_karyawan, nama_karyawan, nama_jabatan, gaji_pokok;
    private Connection conn = new db().connect();
    private DefaultTableModel tabmode;
    private HashMap<String,String> mapTunjangan = new HashMap<>();
    private HashMap<String,String> mapPotongan = new HashMap<>();
    private HashMap<String, String> mapKpi = new HashMap<>();
    /**
     * Creates new form FormPenggajianKaryawan
     */
    public FormPenggajianKaryawan() {
        initComponents();
        aktif();
        loadTunjangan();
        loadKpi();
        loadPotongan();
        kosong();
        autonumber();
        datatable();
        tampilDataPenggajian();
    }
    
    protected void aktif(){
        txtidk.requestFocus();
        tglGaji.setEditor(new JSpinner.DateEditor(tglGaji, "yyyy/MM/dd"));
        txtidgaji.setEditable(false);
        txtidk.setEditable(false);
        txtnamak.setEditable(false);
        jabatan.setEditable(false);
        gajip.setEditable(false);
        totalgaji.setEditable(false);
        Object[] Baris = {"ID Gaji", "ID Karyawan", "Nama Karyawan", "Bulan", "Tahun", "Total Gaji"};
        tabmode = new DefaultTableModel(null, Baris);
        tbdatap.setModel(tabmode);
    }
    
    protected void kosong(){
        txtidk.setText("");
        txtnamak.setText("");
        jabatan.setText("");
        txtTahun.setText("");
        gajip.setText("");
        tunjangan.setSelectedIndex(0);
        kpi.setSelectedIndex(0);
        potongan.setSelectedIndex(0);
        totalgaji.setText("");
        cmbBulan.removeAllItems();
        cmbBulan.addItem("Januari");
        cmbBulan.addItem("Februari");
        cmbBulan.addItem("Maret");
        cmbBulan.addItem("April");
        cmbBulan.addItem("Mei");
        cmbBulan.addItem("Juni");
        cmbBulan.addItem("Juli");
        cmbBulan.addItem("Agustus");
        cmbBulan.addItem("September");
        cmbBulan.addItem("Oktober");
        cmbBulan.addItem("November");
        cmbBulan.addItem("Desember");
    }
    
    protected void autonumber(){
        try{
            String sql = "SELECT id_gaji FROM tb_penggajian ORDER BY id_gaji ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            txtidgaji.setText("PGJN0001");
            while (rs.next()){
                String id_gaji = rs.getString("id_gaji").substring(4);
                int AN = Integer.parseInt(id_gaji)+1;
                String Nol = "";
                if(AN<10){
                    Nol = "000";
                }else if(AN<100){
                    Nol = "00";
                }else if(AN<1000){
                    Nol = "0";
                }else if(AN<10000){
                    Nol = "";
                }
                txtidgaji.setText("PGJN"+Nol+AN);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Auto Number Gagal" +e);
        }
    }
    
    protected void datatable(){
        Object[] Baris = {"ID Gaji", "ID Karyawan", "Nama Karyawan", "Bulan", "Tahun", "Total Gaji"};
        tabmode = new DefaultTableModel(null, Baris);
        try{
            String sql = "SELECT p.id_gaji,k.id_karyawan,"+
                        "k.nm_karyawan,p.bulan,"+
                        "p.tahun,p.total_gaji "+
                        "FROM tb_penggajian p "+
                        "JOIN karyawan k "+
                        "ON p.id_karyawan=k.id_karyawan "+
                        "WHERE k.nm_karyawan LIKE '%"+
                        txtcari.getText()+"%'"+
                        "ORDER BY CAST(SUBSTRING(p.id_gaji,5) AS UNSIGNED) ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            DecimalFormatSymbols simbol = new DecimalFormatSymbols();
            simbol.setCurrencySymbol("Rp. ");
            simbol.setGroupingSeparator('.');
            simbol.setDecimalSeparator(',');
            DecimalFormat rupiah = new DecimalFormat("'Rp. '###,###,###.00", simbol);
            while (rs.next()) {
                double totalGaji = rs.getDouble("total_gaji");
                String[] data = {
                    rs.getString("id_gaji"),
                    rs.getString("id_karyawan"),
                    rs.getString("nm_karyawan"),
                    rs.getString("bulan"),
                    rs.getString("tahun"),
                    rupiah.format(totalGaji)
                };
                tabmode.addRow(data);
            }
            tbdatap.setModel(tabmode);
            rs.close();
            st.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void DataTerpilih(){
        txtidk.setText(id_karyawan);
        txtnamak.setText(nama_karyawan);
        jabatan.setText(nama_jabatan);
        gajip.setText(gaji_pokok);
    }
    
    private void loadTunjangan(){
        try{
            tunjangan.removeAllItems();
            tunjangan.addItem("-- Pilih Tunjangan --");
            mapTunjangan.clear();
            String sql = "SELECT id_tunjangan, nama_tunjangan FROM tunjangan";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id_tunjangan");
                String nama = rs.getString("nama_tunjangan");
                tunjangan.addItem(nama);
                mapTunjangan.put(nama,id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void loadPotongan(){
        try{
            potongan.removeAllItems();
            potongan.addItem("-- Pilih Potongan --");
            mapPotongan.clear();
            String sql = "SELECT id_potongan, jenis_potongan FROM tb_potongan";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id_potongan");
                String jenis = rs.getString("jenis_potongan");
                potongan.addItem(jenis);
                mapPotongan.put(jenis,id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private double getNominalTunjangan(){
        if(tunjangan.getSelectedIndex() == 0){
            return 0;
        }
        double nominal = 0;
        try{
            String sql = "SELECT nominal " +
                        "FROM tunjangan " +
                        "WHERE nama_tunjangan=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tunjangan.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nominal = rs.getDouble("nominal");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return nominal;
    }
    
    private double getNominalPotongan(){
        if(potongan.getSelectedIndex() == 0){
            return 0;
        }
        double nominal = 0;
        try{
            String sql = "SELECT nominal_potongan " +
                        "FROM tb_potongan " +
                        "WHERE jenis_potongan=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, potongan.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nominal = rs.getDouble("nominal_potongan");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return nominal;
    }
    
    private void loadKpi(){
        try{
            kpi.removeAllItems();
            kpi.addItem("-- Pilih KPI --");
            mapKpi.clear();
            String sql = "SELECT id_kpi, nama_kpi FROM tb_kpi";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("id_kpi");
                String nama = rs.getString("nama_kpi");
                kpi.addItem(nama);
                mapKpi.put(nama, id);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private double getNominalKpi(){
        if(kpi.getSelectedIndex() == 0){
            return 0;
        }
        double nilai = 0;
        try{
            String sql = """
                SELECT bonus_kpi 
                FROM tb_kpi 
                WHERE nama_kpi = ?
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kpi.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nilai = rs.getDouble("bonus_kpi");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return nilai;
    }
    
    private boolean validasi(){
        if(txtidk.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih Karyawan Terlebih Dahulu");
            return false;
        }
        if(txtTahun.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Tahun Belum Diisi");
            return false;
        }
        if(gajip.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Gaji Pokok Belum Ada");
            return false;
        }
        if(totalgaji.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Klik Tombol Hitung Terlebih Dahulu");
            return false;
        }
        if(!txtTahun.getText().matches("\\d{4}")){
            JOptionPane.showMessageDialog(null, "Tahun harus 4 digit");
            return false;
        }
        return true;
    }
    
    private double parseRupiah(String nilai){
        if(nilai == null || nilai.trim().isEmpty()){
            return 0;
        }
        return Double.parseDouble(
            nilai.replace("Rp", "")
                 .replace("Rp.", "")
                 .replace(".", "")
                 .replace(",", ".")
                 .replace(" ", "")
                 .trim()
        );
    }
    
    private String formatRupiah(double nilai) {
        DecimalFormat df = new DecimalFormat("#,###");
        return "Rp. " + df.format(nilai).replace(",", ".");
    }
    
    private void tampilDataPenggajian() {
        DefaultTableModel model = (DefaultTableModel) tbdatap.getModel();
        model.setRowCount(0);
        try {
            String sql = """
                SELECT 
                    p.id_gaji,
                    p.id_karyawan,
                    k.nm_karyawan,
                    p.bulan,
                    p.tahun,
                    p.total_gaji
                FROM tb_penggajian p
                JOIN karyawan k ON p.id_karyawan = k.id_karyawan
                ORDER BY p.id_gaji
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // 🔴 DI SINILAH LETAKNYA
                Object[] data = {
                    rs.getString("id_gaji"),
                    rs.getString("id_karyawan"),
                    rs.getString("nm_karyawan"),
                    rs.getString("bulan"),
                    rs.getInt("tahun"),                  // ⬅️ hanya tahun
                    formatRupiah(rs.getDouble("total_gaji"))
                };
                model.addRow(data); // ⬅️ masukkan ke JTable
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbBulan = new javax.swing.JComboBox<>();
        txtTahun = new javax.swing.JTextField();
        tglGaji = new javax.swing.JSpinner();
        gajip = new javax.swing.JTextField();
        potongan = new javax.swing.JComboBox<>();
        bhit = new javax.swing.JButton();
        tunjangan = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        kpi = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtidk = new javax.swing.JTextField();
        txtnamak = new javax.swing.JTextField();
        jabatan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        bcarik = new javax.swing.JButton();
        txtidgaji = new javax.swing.JTextField();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbdatap = new javax.swing.JTable();
        bcaridatak = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        bkembali = new javax.swing.JButton();
        totalgaji = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Penggajian Karyawan");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Penggajian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tunjangan");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Potongan");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Bulan");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Tahun");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Tanggal Gaji");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Gaji Pokok");

        tglGaji.setModel(new javax.swing.SpinnerDateModel());

        bhit.setText("Hitung");
        bhit.addActionListener(this::bhitActionPerformed);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("KPI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(bhit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gajip)
                            .addComponent(cmbBulan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTahun, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tglGaji, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(potongan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tunjangan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kpi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tglGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(gajip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tunjangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(kpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(potongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bhit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Karyawan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("ID Gaji");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("ID Karyawan");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Nama Karyawan");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Jabatan");

        bcarik.setText("Cari");
        bcarik.addActionListener(this::bcarikActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtidk, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcarik))
                    .addComponent(jabatan)
                    .addComponent(txtnamak, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtidgaji))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtidgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtidk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcarik))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtnamak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bsimpan.setText("Simpan");
        bsimpan.addActionListener(this::bsimpanActionPerformed);

        bubah.setText("Ubah");
        bubah.addActionListener(this::bubahActionPerformed);

        bhapus.setText("Hapus");
        bhapus.addActionListener(this::bhapusActionPerformed);

        bbatal.setText("Batal");
        bbatal.addActionListener(this::bbatalActionPerformed);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Penggajian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbdatap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbdatap.setShowGrid(true);
        tbdatap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbdatapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbdatap);

        bcaridatak.setText("Cari");
        bcaridatak.addActionListener(this::bcaridatakActionPerformed);

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bcaridatak)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcari)
                    .addComponent(bcaridatak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        bkembali.setText("Kembali");
        bkembali.addActionListener(this::bkembaliActionPerformed);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Total Gaji");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bsimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bhapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bbatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bkembali)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalgaji)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bsimpan)
                                .addComponent(bubah)
                                .addComponent(bhapus)
                                .addComponent(bbatal)
                                .addComponent(bkembali)
                                .addComponent(jLabel12))
                            .addComponent(totalgaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkembaliActionPerformed
        Login.MainMenu mn = new Login.MainMenu();
        mn.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_bkembaliActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        kosong();
        autonumber();
    }//GEN-LAST:event_bbatalActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        int bar = tbdatap.getSelectedRow();
        if(bar == -1){
            JOptionPane.showMessageDialog(null, "Pilih data dulu!");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(null, 
                "Yakin ingin menghapus data?", 
                "Konfirmasi", 
                JOptionPane.YES_NO_OPTION);
        if(ok == JOptionPane.YES_OPTION){
            try{
                String id = tabmode.getValueAt(bar, 0).toString();
                String sql = "DELETE FROM tb_penggajian WHERE id_gaji=?";
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, id);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                kosong();
                txtidgaji.requestFocus();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Data gagal dihapus: " + e);
            }
            datatable();
            autonumber();
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bcaridatakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcaridatakActionPerformed
        datatable();
    }//GEN-LAST:event_bcaridatakActionPerformed

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            datatable();
        }
    }//GEN-LAST:event_txtcariKeyPressed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        if(!validasi()){
            return;
        }
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sql = """
                UPDATE tb_penggajian SET
                    id_karyawan=?,
                    id_tunjangan=?,
                    id_potongan=?,
                    id_kpi=?,
                    bulan=?,
                    tahun=?,
                    tanggal_gaji=?,
                    gaji_pokok=?,
                    tunjangan=?,
                    bonus_kpi=?,
                    potongan=?,
                    total_gaji=?
                WHERE id_gaji=?
            """;
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtidk.getText());
            String idTunjangan = null;
            String idPotongan = null;
            String idKpi = null;
            if(tunjangan.getSelectedIndex() > 0){
                idTunjangan = mapTunjangan.get(tunjangan.getSelectedItem().toString());
            }
            if(potongan.getSelectedIndex() > 0){
                idPotongan = mapPotongan.get(potongan.getSelectedItem().toString());
            }
            if(kpi.getSelectedIndex() > 0){
                idKpi = mapKpi.get(kpi.getSelectedItem().toString());
            }
            stat.setString(2, idTunjangan);
            stat.setString(3, idPotongan);
            stat.setString(4, idKpi);
            stat.setString(5, cmbBulan.getSelectedItem().toString());
            stat.setString(6, txtTahun.getText());
            stat.setString(7, sdf.format(tglGaji.getValue()));
            stat.setDouble(8, parseRupiah(gajip.getText()));
            stat.setDouble(9, getNominalTunjangan());
            stat.setDouble(10, getNominalKpi());
            stat.setDouble(11, getNominalPotongan());
            stat.setDouble(12, Double.parseDouble(totalgaji.getText()));
            stat.setString(13, txtidgaji.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            kosong();
            autonumber();
            datatable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        if(!validasi()){
            return;
        }
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sql = """
                INSERT INTO tb_penggajian (
                    id_gaji,
                    id_karyawan,
                    id_tunjangan,
                    id_kpi,
                    id_potongan,
                    bulan,
                    tahun,
                    tanggal_gaji,
                    gaji_pokok,
                    tunjangan,
                    bonus_kpi,
                    potongan,
                    total_gaji
                ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtidgaji.getText());
            stat.setString(2, txtidk.getText());
            String idTunjangan = null;
            String idPotongan = null;
            String idKpi = null;
            if(tunjangan.getSelectedIndex() > 0){
                idTunjangan = mapTunjangan.get(tunjangan.getSelectedItem().toString());
            }
            if(potongan.getSelectedIndex() > 0){
                idPotongan = mapPotongan.get(potongan.getSelectedItem().toString());
            }
            if(kpi.getSelectedIndex() > 0){
                idKpi = mapKpi.get(kpi.getSelectedItem().toString());
            }
            stat.setString(3,idTunjangan);
            stat.setString(5,idPotongan);
            stat.setString(4,idKpi);
            stat.setString(6, cmbBulan.getSelectedItem().toString());
            stat.setString(7, txtTahun.getText());
            stat.setString(8, sdf.format(tglGaji.getValue()));
            stat.setDouble(9, parseRupiah(gajip.getText()));
            stat.setDouble(10, getNominalTunjangan());
            stat.setDouble(11, getNominalKpi());
            stat.setDouble(12, getNominalPotongan());
            stat.setDouble(13, Double.parseDouble(totalgaji.getText()));
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            kosong();
            autonumber();
            datatable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bhitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhitActionPerformed
        double gp  = parseRupiah(gajip.getText());
        double tj  = getNominalTunjangan();
        double kpiVal = getNominalKpi();
        double pt  = getNominalPotongan();
        double total = gp + tj + kpiVal - pt;
        totalgaji.setText(String.format("%.0f", total));
    }//GEN-LAST:event_bhitActionPerformed

    private void tbdatapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbdatapMouseClicked
        int bar = tbdatap.getSelectedRow();
        if (bar == -1) return;
        String id = tabmode.getValueAt(bar, 0).toString();
        try {
            String sql = """
                SELECT 
                    p.*,
                    k.nm_karyawan,
                    j.nm_jabatan,
                    t.nama_tunjangan,
                    pt.jenis_potongan,
                    kp.nama_kpi
                FROM tb_penggajian p
                JOIN karyawan k ON p.id_karyawan = k.id_karyawan
                JOIN jabatan j ON k.id_jabatan = j.id_jabatan
                LEFT JOIN tunjangan t ON p.id_tunjangan = t.id_tunjangan
                LEFT JOIN tb_kpi kp ON p.id_kpi = kp.id_kpi
                LEFT JOIN tb_potongan pt ON p.id_potongan = pt.id_potongan
                WHERE p.id_gaji = ?
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtidgaji.setText(rs.getString("id_gaji"));
                txtidk.setText(rs.getString("id_karyawan"));
                txtnamak.setText(rs.getString("nm_karyawan"));
                jabatan.setText(rs.getString("nm_jabatan"));

                cmbBulan.setSelectedItem(rs.getString("bulan"));
                txtTahun.setText(String.valueOf(rs.getInt("tahun")));
                tglGaji.setValue(rs.getDate("tanggal_gaji"));

                gajip.setText(formatRupiah(rs.getDouble("gaji_pokok")));
                // Tunjangan
                tunjangan.setSelectedItem(
                    rs.getString("nama_tunjangan") != null 
                    ? rs.getString("nama_tunjangan") 
                    : "-- Pilih Tunjangan --"
                );
                // KPI
                kpi.setSelectedItem(
                    rs.getString("nama_kpi") != null 
                    ? rs.getString("nama_kpi") 
                    : "-- Pilih KPI --"
                );
                // Potongan
                potongan.setSelectedItem(
                    rs.getString("jenis_potongan") != null 
                    ? rs.getString("jenis_potongan") 
                    : "-- Pilih Potongan --"
                );
                // ❌ Jangan isi total otomatis
                totalgaji.setText("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_tbdatapMouseClicked

    private void bcarikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarikActionPerformed
        popupTabelKaryawan pop = new popupTabelKaryawan();
        pop.pgaji = this;
        pop.setVisible(true);
        pop.setLocationRelativeTo(null);
    }//GEN-LAST:event_bcarikActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormPenggajianKaryawan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bcaridatak;
    private javax.swing.JButton bcarik;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bhit;
    private javax.swing.JButton bkembali;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton bubah;
    private javax.swing.JComboBox<String> cmbBulan;
    private javax.swing.JTextField gajip;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jabatan;
    private javax.swing.JComboBox<String> kpi;
    private javax.swing.JComboBox<String> potongan;
    private javax.swing.JTable tbdatap;
    private javax.swing.JSpinner tglGaji;
    private javax.swing.JTextField totalgaji;
    private javax.swing.JComboBox<String> tunjangan;
    private javax.swing.JTextField txtTahun;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtidgaji;
    private javax.swing.JTextField txtidk;
    private javax.swing.JTextField txtnamak;
    // End of variables declaration//GEN-END:variables
}
