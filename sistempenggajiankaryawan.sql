-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 22 Jun 2026 pada 16.02
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistempenggajiankaryawan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `jabatan`
--

CREATE TABLE `jabatan` (
  `id_jabatan` varchar(5) NOT NULL,
  `nm_jabatan` varchar(100) NOT NULL,
  `gaji_pokok` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jabatan`
--

INSERT INTO `jabatan` (`id_jabatan`, `nm_jabatan`, `gaji_pokok`) VALUES
('J001', 'Direktur', 25000000.00),
('J002', 'Manajer', 12000000.00),
('J003', 'Supervisor', 8000000.00),
('J004', 'Staf Administrasi', 5000000.00),
('J005', 'Operator/Produksi', 4500000.00),
('J006', 'Office Boy', 2500000.00),
('J007', 'Security', 3000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(5) NOT NULL,
  `nm_karyawan` varchar(200) NOT NULL,
  `jk` varchar(25) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `telp` varchar(16) NOT NULL,
  `id_jabatan` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nm_karyawan`, `jk`, `alamat`, `telp`, `id_jabatan`) VALUES
('K001', 'Andi', 'Laki-Laki', 'Manggarai', '081234567890', 'J001'),
('K002', 'Amelia', 'Perempuan', 'Bandung', '080987654321', 'J002'),
('K003', 'Yanti', 'Perempuan', 'Bogor', '081123456789', 'J003'),
('K004', 'Robby', 'Laki-Laki', 'Jakarta', '081223456789', 'J004'),
('K005', 'Jamal', 'Laki-Laki', 'Bandung', '081233456789', 'J005'),
('K006', 'Saitama', 'Laki-Laki', 'Saitama', '012345678901', 'J007');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_kpi`
--

CREATE TABLE `tb_kpi` (
  `id_kpi` varchar(10) NOT NULL,
  `nama_kpi` varchar(50) NOT NULL,
  `skor_min` int(11) NOT NULL,
  `skor_max` int(11) NOT NULL,
  `bonus_kpi` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_kpi`
--

INSERT INTO `tb_kpi` (`id_kpi`, `nama_kpi`, `skor_min`, `skor_max`, `bonus_kpi`) VALUES
('KPI01', 'Sangat Baik', 90, 100, 500000.00),
('KPI02', 'Baik', 80, 89, 300000.00),
('KPI03', 'Cukup', 70, 79, 100000.00),
('KPI04', 'Kurang', 60, 69, 20000.00),
('KPI05', 'Sangat Kuran', 0, 59, 0.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_penggajian`
--

CREATE TABLE `tb_penggajian` (
  `id_gaji` varchar(10) NOT NULL,
  `id_karyawan` varchar(10) NOT NULL,
  `id_tunjangan` varchar(10) DEFAULT NULL,
  `id_potongan` varchar(10) DEFAULT NULL,
  `id_kpi` varchar(10) DEFAULT NULL,
  `bulan` varchar(15) NOT NULL,
  `tahun` year(4) NOT NULL,
  `tanggal_gaji` date NOT NULL,
  `gaji_pokok` decimal(12,2) NOT NULL DEFAULT 0.00,
  `tunjangan` decimal(12,2) NOT NULL DEFAULT 0.00,
  `bonus_kpi` decimal(12,2) NOT NULL DEFAULT 0.00,
  `potongan` decimal(12,2) NOT NULL DEFAULT 0.00,
  `total_gaji` decimal(12,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_penggajian`
--

INSERT INTO `tb_penggajian` (`id_gaji`, `id_karyawan`, `id_tunjangan`, `id_potongan`, `id_kpi`, `bulan`, `tahun`, `tanggal_gaji`, `gaji_pokok`, `tunjangan`, `bonus_kpi`, `potongan`, `total_gaji`) VALUES
('PGJN0001', 'K001', 'TJ001', 'POT001', NULL, 'Januari', '2026', '2026-06-21', 25000000.00, 500000.00, 0.00, 50000.00, 25450000.00),
('PGJN0002', 'K002', 'TJ001', 'POT004', NULL, 'Januari', '2026', '2026-06-21', 12000000.00, 500000.00, 0.00, 100000.00, 12400000.00),
('PGJN0003', 'K003', 'TJ003', 'POT003', NULL, 'Januari', '2026', '2026-06-21', 8000000.00, 200000.00, 0.00, 25000.00, 8175000.00),
('PGJN0004', 'K004', NULL, 'POT005', NULL, 'Januari', '2026', '2026-06-21', 5000000.00, 0.00, 0.00, 200000.00, 4800000.00),
('PGJN0005', 'K005', NULL, NULL, NULL, 'Januari', '2026', '2026-06-21', 4500000.00, 0.00, 0.00, 0.00, 4500000.00),
('PGJN0006', 'K006', 'TJ002', NULL, NULL, 'Januari', '2026', '2026-06-21', 3000000.00, 750000.00, 0.00, 0.00, 3750000.00),
('PGJN0007', 'K001', 'TJ001', 'POT001', NULL, 'Februari', '2026', '2026-06-21', 25000000.00, 500000.00, 0.00, 50000.00, 25450000.00),
('PGJN0008', 'K002', 'TJ001', NULL, NULL, 'Februari', '2026', '2026-06-21', 12000000.00, 500000.00, 0.00, 0.00, 12500000.00),
('PGJN0009', 'K003', 'TJ003', 'POT002', NULL, 'Februari', '2026', '2026-06-21', 8000000.00, 200000.00, 0.00, 75000.00, 8125000.00),
('PGJN0010', 'K004', NULL, 'POT003', NULL, 'Februari', '2026', '2026-06-21', 5000000.00, 0.00, 0.00, 25000.00, 4975000.00),
('PGJN0011', 'K005', 'TJ003', 'POT004', NULL, 'Februari', '2026', '2026-06-21', 4500000.00, 200000.00, 0.00, 100000.00, 4600000.00),
('PGJN0012', 'K006', 'TJ002', NULL, NULL, 'Februari', '2026', '2026-06-21', 3000000.00, 750000.00, 0.00, 0.00, 3750000.00),
('PGJN0013', 'K001', 'TJ001', NULL, NULL, 'Maret', '2026', '2026-06-21', 25000000.00, 500000.00, 0.00, 0.00, 25500000.00),
('PGJN0014', 'K002', 'TJ001', NULL, NULL, 'Maret', '2026', '2026-06-21', 12000000.00, 500000.00, 0.00, 0.00, 12500000.00),
('PGJN0015', 'K003', 'TJ003', NULL, NULL, 'Maret', '2026', '2026-06-21', 8000000.00, 200000.00, 0.00, 0.00, 8200000.00),
('PGJN0016', 'K004', 'TJ004', NULL, NULL, 'Maret', '2026', '2026-06-21', 5000000.00, 300000.00, 0.00, 0.00, 5300000.00),
('PGJN0017', 'K005', 'TJ005', NULL, NULL, 'Maret', '2026', '2026-06-21', 4500000.00, 400000.00, 0.00, 0.00, 4900000.00),
('PGJN0018', 'K006', 'TJ002', NULL, NULL, 'Maret', '2026', '2026-06-21', 3000000.00, 750000.00, 0.00, 0.00, 3750000.00),
('PGJN0019', 'K001', 'TJ001', NULL, NULL, 'April', '2026', '2026-06-21', 25000000.00, 500000.00, 0.00, 0.00, 25500000.00),
('PGJN0020', 'K002', NULL, 'POT003', NULL, 'April', '2026', '2026-06-21', 12000000.00, 0.00, 0.00, 25000.00, 11975000.00),
('PGJN0021', 'K003', 'TJ002', NULL, NULL, 'April', '2026', '2026-06-21', 8000000.00, 750000.00, 0.00, 0.00, 8750000.00),
('PGJN0022', 'K004', NULL, 'POT004', NULL, 'April', '2026', '2026-06-21', 5000000.00, 0.00, 0.00, 100000.00, 4900000.00),
('PGJN0023', 'K005', NULL, 'POT003', NULL, 'April', '2026', '2026-06-21', 4500000.00, 0.00, 0.00, 25000.00, 4475000.00),
('PGJN0024', 'K006', NULL, 'POT003', NULL, 'April', '2026', '2026-06-21', 3000000.00, 0.00, 0.00, 25000.00, 2975000.00),
('PGJN0025', 'K001', 'TJ001', 'POT001', 'KPI01', 'Mei', '2026', '2026-06-22', 25000000.00, 500000.00, 500000.00, 50000.00, 25950000.00),
('PGJN0026', 'K002', 'TJ001', 'POT003', 'KPI01', 'Mei', '2026', '2026-06-22', 12000000.00, 500000.00, 500000.00, 25000.00, 12975000.00),
('PGJN0027', 'K003', 'TJ003', 'POT004', 'KPI02', 'Mei', '2026', '2026-06-22', 8000000.00, 200000.00, 300000.00, 100000.00, 8400000.00),
('PGJN0028', 'K004', 'TJ003', 'POT001', 'KPI03', 'Mei', '2026', '2026-06-22', 5000000.00, 200000.00, 100000.00, 50000.00, 5250000.00),
('PGJN0029', 'K005', NULL, 'POT003', 'KPI02', 'Mei', '2026', '2026-06-22', 4500000.00, 0.00, 300000.00, 25000.00, 4775000.00),
('PGJN0030', 'K006', 'TJ001', NULL, 'KPI01', 'Mei', '2026', '2026-06-22', 3000000.00, 500000.00, 500000.00, 0.00, 4000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_potongan`
--

CREATE TABLE `tb_potongan` (
  `id_potongan` varchar(10) NOT NULL,
  `jenis_potongan` varchar(50) NOT NULL,
  `nominal_potongan` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_potongan`
--

INSERT INTO `tb_potongan` (`id_potongan`, `jenis_potongan`, `nominal_potongan`) VALUES
('POT001', 'BPJS Kesehatan', 50000.00),
('POT002', 'BPJS Ketenagakerjaan', 75000.00),
('POT003', 'Keterlambatan', 25000.00),
('POT004', 'Alfa (Tidak Hadir)', 100000.00),
('POT005', 'Pinjaman Karyawan', 200000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tunjangan`
--

CREATE TABLE `tunjangan` (
  `id_tunjangan` varchar(10) NOT NULL,
  `nama_tunjangan` varchar(100) NOT NULL,
  `nominal` decimal(12,2) NOT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tunjangan`
--

INSERT INTO `tunjangan` (`id_tunjangan`, `nama_tunjangan`, `nominal`, `keterangan`) VALUES
('TJ001', 'Tunjangan Transportasi', 500000.00, 'Biaya transportasi'),
('TJ002', 'Tunjangan Makan', 750000.00, 'Biaya makan'),
('TJ003', 'Tunjangan Komunikasi', 200000.00, 'Biaya telepon dan internet'),
('TJ004', 'Tunjangan Kehadiran', 300000.00, 'Bonus kehadiran'),
('TJ005', 'Tunjangan Kesehatan', 400000.00, 'Tambahan kesehatan');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `jabatan`
--
ALTER TABLE `jabatan`
  ADD PRIMARY KEY (`id_jabatan`);

--
-- Indeks untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`),
  ADD KEY `fk_karyawan_jabatan` (`id_jabatan`);

--
-- Indeks untuk tabel `tb_kpi`
--
ALTER TABLE `tb_kpi`
  ADD PRIMARY KEY (`id_kpi`);

--
-- Indeks untuk tabel `tb_penggajian`
--
ALTER TABLE `tb_penggajian`
  ADD PRIMARY KEY (`id_gaji`),
  ADD KEY `fk_gaji_karyawan` (`id_karyawan`),
  ADD KEY `fk_gaji_tunjangan` (`id_tunjangan`),
  ADD KEY `fk_gaji_potongan` (`id_potongan`),
  ADD KEY `fk_penggajian_kpi` (`id_kpi`);

--
-- Indeks untuk tabel `tb_potongan`
--
ALTER TABLE `tb_potongan`
  ADD PRIMARY KEY (`id_potongan`);

--
-- Indeks untuk tabel `tunjangan`
--
ALTER TABLE `tunjangan`
  ADD PRIMARY KEY (`id_tunjangan`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD CONSTRAINT `fk_karyawan_jabatan` FOREIGN KEY (`id_jabatan`) REFERENCES `jabatan` (`id_jabatan`) ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tb_penggajian`
--
ALTER TABLE `tb_penggajian`
  ADD CONSTRAINT `fk_gaji_karyawan` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`),
  ADD CONSTRAINT `fk_gaji_potongan` FOREIGN KEY (`id_potongan`) REFERENCES `tb_potongan` (`id_potongan`),
  ADD CONSTRAINT `fk_gaji_tunjangan` FOREIGN KEY (`id_tunjangan`) REFERENCES `tunjangan` (`id_tunjangan`),
  ADD CONSTRAINT `fk_penggajian_kpi` FOREIGN KEY (`id_kpi`) REFERENCES `tb_kpi` (`id_kpi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
