# 💰 Sistem Informasi Penggajian Karyawan

Aplikasi desktop berbasis **Java NetBeans (Java Swing)** yang digunakan untuk mengelola data karyawan, jabatan, tunjangan, KPI (Key Performance Indicator), potongan gaji, serta proses penggajian secara terkomputerisasi menggunakan database **MySQL**.

---

# 📋 Daftar Isi

- Tentang Aplikasi
- Fitur Utama
- Teknologi yang Digunakan
- Struktur Database
- Instalasi
- Cara Menjalankan
- Struktur Project
- Perhitungan Penggajian
- Laporan
- Screenshot
- Author

---

# 📖 Tentang Aplikasi

Sistem Informasi Penggajian Karyawan dibuat untuk membantu perusahaan dalam mengelola proses penggajian secara cepat, akurat, dan efisien.

Aplikasi ini mampu:

- Mengelola data master karyawan
- Mengelola jabatan beserta gaji pokok
- Mengelola tunjangan
- Mengelola potongan gaji
- Mengelola KPI (Key Performance Indicator)
- Menghitung total gaji secara otomatis
- Mencetak laporan penggajian menggunakan JasperReport

---

# ✨ Fitur Utama

## 🔐 Login

- Login menggunakan username dan password
- Hak akses Admin

---

## 👨‍💼 Master Data

### Data Karyawan

- Tambah Data
- Edit Data
- Hapus Data
- Cari Data

### Data Jabatan

- Gaji Pokok
- Nama Jabatan

### Data Tunjangan

- Nominal Tunjangan

### Data Potongan

- Jenis Potongan
- Nominal Potongan

### Data KPI

- Skor Minimum
- Skor Maksimum
- Bonus KPI

---

## 💵 Transaksi Penggajian

- Pilih Karyawan
- Pilih Tunjangan
- Pilih Potongan
- Pilih KPI
- Hitung Gaji Otomatis
- Simpan Transaksi

Rumus Penggajian:

```
Total Gaji =
Gaji Pokok
+ Tunjangan
+ Bonus KPI
- Potongan
```

---

## 📊 Laporan

- Laporan Data Karyawan
- Laporan Data Jabatan
- Laporan Penggajian
- Rekap Penggajian

---

# 🛠 Teknologi

| Komponen | Teknologi |
|-----------|-----------|
| Bahasa | Java |
| IDE | NetBeans |
| GUI | Java Swing |
| Database | MySQL |
| Reporting | JasperReport |
| Connector | MySQL JDBC |
| Build | Ant |

---

# 🗄 Struktur Database

Database :

```
sistempenggajiankaryawan
```

## Tabel

- karyawan
- jabatan
- tunjangan
- tb_potongan
- tb_kpi
- tb_penggajian

---

## Relasi Database

```
            jabatan
              |
              | 1
              |
              | N
           karyawan
              |
              | 1
              |
              | N
        tb_penggajian
-----------------------------
|              |            |
|              |            |
|              |            |
tunjangan  tb_potongan    tb_kpi
```

---

# 📂 Struktur Project

```
src
│
├── FormLogin.java
├── MenuUtama.java
│
├── Master
│   ├── FormDataKaryawan.java
│   ├── FormDataJabatan.java
│   ├── FormDataTunjangan.java
│   ├── FormDataPotongan.java
│   └── FormDataKPI.java
│
├── Transaksi
│   └── FormPenggajian.java
│
├── Report
│   ├── LaporanKaryawan.jrxml
│   ├── LaporanJabatan.jrxml
│   ├── LaporanPenggajian.jrxml
│   └── RekapPenggajian.jrxml
│
└── Koneksi
    └── db.java
```

---

# ⚙ Instalasi

## 1. Clone Project

```
git clone https://github.com/username/sistem-penggajian.git
```

atau download ZIP.

---

## 2. Import Database

Import file

```
sistempenggajiankaryawan.sql
```

menggunakan phpMyAdmin atau MySQL.

---

## 3. Konfigurasi Database

Edit file

```
Koneksi/db.java
```

```java
String url = "jdbc:mysql://localhost/sistempenggajiankaryawan";
String user = "root";
String password = "";
```

Sesuaikan dengan konfigurasi MySQL Anda.

---

## 4. Jalankan Project

Buka menggunakan NetBeans.

Klik

```
Run Project (F6)
```

---

# 🧮 Perhitungan Gaji

```
Total Gaji

=

Gaji Pokok
+ Tunjangan
+ Bonus KPI
- Potongan
```

Contoh

```
Gaji Pokok    : Rp5.000.000
Tunjangan     : Rp1.000.000
Bonus KPI     : Rp500.000
Potongan      : Rp200.000

----------------------------

Total Gaji    : Rp6.300.000
```

---

# 📈 KPI (Key Performance Indicator)

| Nama KPI | Skor | Bonus |
|----------|-------|--------|
| Sangat Baik | 90-100 | Rp500.000 |
| Baik | 80-89 | Rp300.000 |
| Cukup | 70-79 | Rp100.000 |
| Kurang | <70 | Rp0 |

---

# 📌 Pengembangan Selanjutnya

- Export PDF
- Export Excel
- Grafik Rekap Gaji
- Multi User
- Backup Database
- Hak Akses Admin & HRD

---

# 👨‍💻 Author

Nama : **Muhammad Rizki Rabbani dan Kelompok 4**

Program Studi : **Teknik Informatika**

Universitas : **Universitas Indraprasta PGRI**

Tahun : **2026**

---

# 📄 License

Project ini dibuat untuk keperluan **Tugas Kelompok Pemrograman Visual - Kelompok 4 R6V** dan dapat digunakan sebagai media pembelajaran.
