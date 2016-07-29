-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 27, 2016 at 06:43 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cour_monitor`
--

-- --------------------------------------------------------

--
-- Table structure for table `daftar_obat`
--

CREATE TABLE IF NOT EXISTS `daftar_obat` (
`id` int(11) NOT NULL,
  `nama_obat` varchar(60) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `daftar_obat`
--

INSERT INTO `daftar_obat` (`id`, `nama_obat`) VALUES
(1, 'Paramex 50ms g20soo'),
(2, 'Paramex 60ms g60soo'),
(3, 'Panadol 50ms g20soo'),
(4, 'Panadol 750ms g20soo'),
(5, 'Bodrex 320ms g20soo'),
(18, 'Atorvastatin HJ'),
(19, 'Amlodipine OGB HJ'),
(20, 'Asam Tranexamat OGB HJ'),
(21, 'Acyclovir OGB HJ'),
(22, 'Allopurinol OGB HJ'),
(23, 'Bisoprolol OGB HJ'),
(24, 'Cisplatin'),
(25, 'Carboplatin'),
(26, 'Citicoline OGB HJ'),
(27, 'Captopril OGB HJ'),
(28, 'Clopidogrel OGB HJ'),
(29, 'Cefadroxil OGB HJ'),
(30, 'Cefepime OGB HJ'),
(31, 'Cefixime OGB HJ'),
(32, 'Cefoperazone/Sulbactam OGB HJ'),
(33, 'Cefotaxime OGB HJ'),
(34, 'Cefpirome OGB HJ'),
(35, 'Ceftazidime OGB HJ'),
(36, 'Ceftriaxone OGB HJ'),
(37, 'Cetirizine OGB HJ'),
(38, 'Ciprofloxacin OGB HJ'),
(39, 'Doxorubicin'),
(40, 'Domperidone OGB HJ'),
(41, 'Epirubicin'),
(42, 'Fluorouracil'),
(43, 'Fenofibrate OGB HJ'),
(44, 'Glimepiride OGB HJ'),
(45, 'Glucosamine OGB HJ'),
(46, 'Hydrocortisone OGB HJ'),
(47, 'Ketolorac OGB HJ'),
(48, 'Ketoprofen OGB HJ'),
(49, 'Ketoconazole Tablet OGB HJ'),
(50, 'Ketoconazole Krim OGB HJ'),
(51, 'Kalium Diklofenak OGB HJ'),
(52, 'Levofloxacin HJ'),
(53, 'Leucovorin Calcium'),
(54, 'Lansoprazole OGB HJ'),
(55, 'Losartan OGB HJ'),
(56, 'Loratadine OGB HJ'),
(57, 'Metformin OGB HJ'),
(58, 'Metronidazole OGB HJ'),
(59, 'Methylprednisolone OGB HJ'),
(60, 'Meropenem OGB HJ'),
(61, 'Meloxicam OGB HJ'),
(62, 'Omeprazole OGB HJ'),
(63, 'Paclitaxel'),
(64, 'Piracetam OGB HJ'),
(65, 'Ranitidine OGB HJ'),
(66, 'Simvastatin OGB HJ'),
(67, 'Tramadol OGB HJ');

-- --------------------------------------------------------

--
-- Table structure for table `kurir`
--

CREATE TABLE IF NOT EXISTS `kurir` (
`id` int(5) NOT NULL,
  `user` varchar(20) NOT NULL,
  `no_hp` int(20) NOT NULL,
  `gcm_token` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kurir`
--

INSERT INTO `kurir` (`id`, `user`, `no_hp`, `gcm_token`) VALUES
(1, 'user1', 124124124, 'd5ZfuksPxsw:APA91bGHQ6TD-sSLZOoZ0dewKOyx7Skuvc9Go7rr-7NBukZAFpdpauBA5vcjs8GZ8YncwXWmvg4kJQaD80KrY7R3-C-Rg1-kLT3SV_V4JzyjH0a87XRkgOngcJz1WUB7Y1SAmTiHaj8I'),
(5, 'kurir1', 123, 'd9HGAzT7vIs:APA91bFNwi9HD0D4B-ooWSyGK5CDT8vVzhuHSjlPDTEm2y7NliuzkdGySCyxzcpXu0icfYsg6cBmXFI93u-Qp2bQhAPFDAngJTCNUqk7dJTORuO9TlRspX4RQxWFGk_ID-PPz189YRtK'),
(6, 'kurir3', 2147483647, ''),
(7, 'kurir4', 2147483647, '');

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_pemesan`
--

CREATE TABLE IF NOT EXISTS `lokasi_pemesan` (
`id` int(11) NOT NULL,
  `id_pesanan` int(5) NOT NULL,
  `geo_lat` float(10,6) NOT NULL,
  `geo_long` float(10,6) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lokasi_pemesan`
--

INSERT INTO `lokasi_pemesan` (`id`, `id_pesanan`, `geo_lat`, `geo_long`) VALUES
(1, 17, -7.267374, 112.756653),
(2, 12, 123.500000, 45.700001),
(3, 13, 123.209999, 222.330002),
(4, 21, -7.290651, 112.800232),
(5, 22, -7.290439, 112.799934),
(6, 23, -7.290206, 112.799484),
(7, 24, -7.288517, 112.800560),
(8, 25, -7.290654, 112.800484),
(9, 26, -7.275342, 112.792732),
(10, 27, -7.289836, 112.797714),
(11, 28, -7.288740, 112.798416);

-- --------------------------------------------------------

--
-- Table structure for table `orderan`
--

CREATE TABLE IF NOT EXISTS `orderan` (
`id_pesanan` int(5) NOT NULL,
  `nama_penerima` varchar(20) NOT NULL,
  `kurir` varchar(20) NOT NULL,
  `status` varchar(10) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `bukti` varchar(400) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orderan`
--

INSERT INTO `orderan` (`id_pesanan`, `nama_penerima`, `kurir`, `status`, `alamat`, `bukti`) VALUES
(1, 'apotek budi', 'kurir1', 'done', 'keputih', 'http://192.168.1.22/cour-monitor/android/bukti/1.png'),
(2, 'Apotek Gebang', 'user1', 'done', 'Gebang Lor no.25', './bukti/domain.jpg'),
(3, 'Apotek Nanyang', 'kurir1', 'done', 'Mulyosari', 'http://192.168.1.22/cour-monitor/android/bukti/3.png'),
(4, 'Apotek Bahagia', 'kurir1', 'done', 'Keputih Perintis 2', 'http://192.168.1.22/cour-monitor/android/bukti/4.png'),
(5, 'RS Ajibata', 'kurir1', 'done', 'Ajibata', 'http://192.168.1.22/cour-monitor/android/bukti/5.png'),
(6, 'RS Kalibiru', 'kurir1', 'done', 'Kalibiru', 'http://192.168.1.22/cour-monitor/android/bukti/6.png'),
(7, 'RS Priangan', 'kurir1', 'done', 'priangan', 'http://192.168.1.22/cour-monitor/android/bukti/7.png'),
(8, 'RS Manyar', 'kurir1', 'done', 'Manyar', 'http://192.168.1.22/cour-monitor/android/bukti/8.png'),
(9, 'RS Kalibuto', 'kurir1', 'done', 'Kalibuto', 'http://192.168.1.22/cour-monitor/android/bukti/9.png'),
(10, 'RS Kalibrasi', 'kurir1', 'proses', 'Kalibrasi', ''),
(11, 'RS Kaliputung', 'kurir1', 'proses', 'Kaliputung', ''),
(12, 'RS Kaliaja', 'kurir1', 'proses', 'Kaliaja', ''),
(13, 'Apotek Gebang', 'kurir1', 'proses', 'keputih', ''),
(14, 'Apotek Guyon', 'kurir1', 'proses', 'keputih', ''),
(15, 'Apotek Capjay', 'kurir1', 'proses', 'keputih', ''),
(16, 'Apotek Nguyen', 'kurir1', 'proses', 'keputih', ''),
(17, 'Apotek Dajen', 'user1', 'done', 'keputih', 'http://192.168.1.22/cour-monitor/android/bukti/17.png'),
(18, 'Apotek Jenfu', 'kurir1', 'proses', 'keputih', ''),
(19, 'Apotek Jenfa', 'kurir1', 'done', 'keputih', 'http://192.168.1.22/cour-monitor/android/bukti/19.png'),
(20, 'Apotek Slesi', 'kurir1', 'proses', 'keputih', ''),
(21, 'Apotek Damai', 'kurir1', 'proses', 'Jalan Keputih Tegal No.9', ''),
(22, 'Apotek Bahagia', 'kurir1', 'proses', 'Jalan Keputih Tegal No.3 A', ''),
(23, 'RS Priangan', 'kurir1', 'proses', 'Jalan Arief Rachman Hakim No.57', ''),
(24, 'Testing local host', 'kurir1', 'done', 'Jalan Keputih Utara No.70', 'http://192.168.1.22/cour-monitor/android/bukti/24.png'),
(25, 'Apotek Keputih', 'kurir1', 'proses', 'Jalan Keputih Tegal No.20 C', ''),
(26, 'nur', 'kurir1', 'done', 'Jalan Tegal Mulyorejo Baru No.79-80', 'http://192.168.42.99/cour-monitor/android/bukti/26.png'),
(27, 'Apotek Keputih', 'kurir1', 'done', 'Jalan Arief Rachman Hakim No.27', 'http://192.168.42.99/cour-monitor/android/bukti/27.png'),
(28, 'Apotek Keputih Baru', 'kurir1', 'done', 'Keputih Gang IA No.29', 'http://192.168.42.99/cour-monitor/android/bukti/28.png');

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE IF NOT EXISTS `pesanan` (
`id` int(5) NOT NULL,
  `id_pesanan` int(5) NOT NULL,
  `nama_obat` varchar(60) NOT NULL,
  `jumlah` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`id`, `id_pesanan`, `nama_obat`, `jumlah`) VALUES
(1, 3, 'Paramex', '100 Tablet'),
(2, 3, 'Generic 150', '200 tablet'),
(3, 3, 'Generic 250', '200 tablet'),
(4, 3, 'Generic 350', '200 tablet'),
(5, 3, 'Generic 450', '200 tablet'),
(6, 3, 'Generic 550', '200 tablet'),
(7, 3, 'Generic 110', '200 tablet'),
(8, 3, 'Generic 130', '200 tablet'),
(9, 3, 'Generic 140', '200 tablet'),
(10, 3, 'Generic 160', '200 tablet'),
(11, 3, 'Generic 170', '200 tablet'),
(12, 14, 'Generic 1', '100 Tablet'),
(15, 21, 'Paramex 50ms g20soo', '200'),
(16, 21, 'Bodrex 320ms g20soo', '222'),
(17, 22, 'Paramex multivitamin autofixin', '200 Tablet'),
(18, 22, 'Bodrex 320ms g20soo', '3490'),
(19, 22, 'Fear Factor', '4078'),
(20, 23, 'Paramex multivitamin autofixing headache K100-SP', '400 Lembar'),
(21, 23, 'Paramex 60ms g60soo', '200 Tablet'),
(22, 23, 'Bodrex 320ms g20soo', '200 Pet'),
(23, 24, 'Ketolorac OGB HJ', '100'),
(24, 24, 'Bodrex 320ms g20soo', '200 tablet'),
(25, 25, 'Bodrex 320ms g20soo', '200 tablet'),
(26, 25, 'Epirubicin', '300 tablet'),
(27, 25, 'Methylprednisolone OGB HJ', '200 tablet'),
(28, 25, 'Cefoperazone/Sulbactam OGB HJ', '200 tablet'),
(29, 26, 'Paramex 60ms g60soo', '100'),
(30, 27, 'Atorvastatin HJ', '100 Tablet'),
(31, 27, 'Carboplatin', '200 Tablet'),
(32, 27, 'Domperidone OGB HJ', '200 Botol'),
(33, 28, 'Atorvastatin HJ', '100 Tablet'),
(34, 28, 'Carboplatin', '200 Tablet'),
(35, 28, 'Clopidogrel OGB HJ', '100 Botol');

-- --------------------------------------------------------

--
-- Table structure for table `track`
--

CREATE TABLE IF NOT EXISTS `track` (
`id` int(11) NOT NULL,
  `id_pesanan` int(5) NOT NULL,
  `lat` float(10,6) NOT NULL,
  `lng` float(10,6) NOT NULL,
  `kurir` varchar(20) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `track`
--

INSERT INTO `track` (`id`, `id_pesanan`, `lat`, `lng`, `kurir`, `tanggal`) VALUES
(1, 3, -7.289965, 112.797211, 'user1', '2016-05-15'),
(2, 3, -7.290290, 112.799568, 'user1', '2016-05-15'),
(4, 10, -7.280120, 112.768997, 'kurir1', '2016-05-24'),
(5, 10, -7.280120, 112.768997, 'kurir1', '2016-05-24'),
(6, 10, -7.280120, 112.768997, 'kurir1', '2016-05-24'),
(7, 10, -7.279870, 112.766998, 'kurir1', '2016-05-24'),
(8, 10, -7.278760, 112.759003, 'kurir1', '2016-05-24'),
(9, 10, -7.277420, 112.751999, 'kurir1', '2016-05-24'),
(10, 10, -7.277100, 112.744003, 'kurir1', '2016-05-24'),
(11, 17, -7.288510, 112.797997, 'user1', '2016-05-25'),
(12, 17, -7.288510, 112.797997, 'user1', '2016-05-25'),
(13, 17, -7.289910, 112.796997, 'user1', '2016-05-25'),
(14, 17, -7.290470, 112.793999, 'user1', '2016-05-25'),
(15, 17, -7.290660, 112.792000, 'user1', '2016-05-25'),
(16, 17, -7.290000, 112.786003, 'user1', '2016-05-25'),
(17, 17, -7.289400, 112.781998, 'user1', '2016-05-25'),
(18, 17, -7.289450, 112.779999, 'user1', '2016-05-25'),
(19, 17, -7.280740, 112.780998, 'user1', '2016-05-25'),
(20, 17, -7.277270, 112.780998, 'user1', '2016-05-25'),
(21, 17, -7.272820, 112.781998, 'user1', '2016-05-25'),
(22, 17, -7.271690, 112.774002, 'user1', '2016-05-25'),
(23, 17, -7.272030, 112.774002, 'user1', '2016-05-25'),
(24, 17, -7.268630, 112.774002, 'user1', '2016-05-25'),
(25, 17, -7.268290, 112.772003, 'user1', '2016-05-25'),
(26, 17, -7.266480, 112.767998, 'user1', '2016-05-25'),
(27, 17, -7.265880, 112.762001, 'user1', '2016-05-25'),
(28, 17, -7.265880, 112.762001, 'user1', '2016-05-25'),
(29, 17, -7.265910, 112.759003, 'user1', '2016-05-25'),
(30, 17, -7.266160, 112.757004, 'user1', '2016-05-25'),
(31, 17, -7.268180, 112.757004, 'user1', '2016-05-25'),
(32, 22, -7.289910, 112.797997, 'kurir1', '2016-05-28'),
(33, 22, -7.289910, 112.797997, 'kurir1', '2016-05-28'),
(34, 22, -7.289940, 112.799004, 'kurir1', '2016-05-28'),
(35, 22, -7.290170, 112.799004, 'kurir1', '2016-05-28'),
(36, 22, -7.290400, 112.800003, 'kurir1', '2016-05-28'),
(37, 24, -7.289130, 112.797997, 'kurir1', '2016-07-21'),
(38, 24, -7.289120, 112.797997, 'kurir1', '2016-07-21'),
(39, 26, -7.278430, 112.792999, 'kurir1', '2016-07-21'),
(40, 26, -7.278430, 112.792999, 'kurir1', '2016-07-21'),
(41, 26, -7.278430, 112.792999, 'kurir1', '2016-07-21'),
(42, 27, -7.289110, 112.797997, 'kurir1', '2016-07-27'),
(43, 27, -7.289450, 112.796997, 'kurir1', '2016-07-27'),
(44, 27, -7.289110, 112.797997, 'kurir1', '2016-07-27'),
(45, 27, -7.289450, 112.796997, 'kurir1', '2016-07-27'),
(46, 27, -7.289110, 112.797997, 'kurir1', '2016-07-27'),
(47, 27, -7.289450, 112.796997, 'kurir1', '2016-07-27'),
(48, 27, -7.289120, 112.797997, 'kurir1', '2016-07-27'),
(49, 28, -7.289100, 112.797997, 'kurir1', '2016-07-27'),
(50, 28, -7.289210, 112.796997, 'kurir1', '2016-07-27'),
(51, 28, -7.289110, 112.797997, 'kurir1', '2016-07-27'),
(52, 28, -7.289270, 112.796997, 'kurir1', '2016-07-27'),
(53, 28, -7.289090, 112.797997, 'kurir1', '2016-07-27');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
`id` int(11) NOT NULL,
  `user` varchar(15) NOT NULL,
  `password` varchar(32) NOT NULL,
  `level` int(2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `user`, `password`, `level`) VALUES
(1, 'user1', '24c9e15e52afc47c225b757e7bee1f9d', 1),
(2, 'user2', '7e58d63b60197ceb55a1c487989a3720', 2),
(3, 'admin', '21232f297a57a5a743894a0e4a801fc3', 0),
(12, 'sales1', '00db8f14ff00dd9a2e707391332c3447', 2),
(20, 'kurir1', 'e0b1a63f9ff0250a8bc5b2ebb6897feb', 1),
(21, 'sales2', 'bc62e62c719e0185b0874a4c8d4f87cf', 2),
(22, 'kurir3', 'b31ed1101d30ccb2e71355c8931199a2', 1),
(23, 'sales3', 'ff78458499976fb53f2b5bc7a0dec0d7', 2),
(24, 'kurir4', '8225ebfe7db4f93d5b2646d502c09567', 1),
(25, 'sales4', '6c67e376369e3af09b4ad0957c80cb25', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `daftar_obat`
--
ALTER TABLE `daftar_obat`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `kurir`
--
ALTER TABLE `kurir`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `user` (`user`);

--
-- Indexes for table `lokasi_pemesan`
--
ALTER TABLE `lokasi_pemesan`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderan`
--
ALTER TABLE `orderan`
 ADD PRIMARY KEY (`id_pesanan`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `track`
--
ALTER TABLE `track`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `user` (`user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `daftar_obat`
--
ALTER TABLE `daftar_obat`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=68;
--
-- AUTO_INCREMENT for table `kurir`
--
ALTER TABLE `kurir`
MODIFY `id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `lokasi_pemesan`
--
ALTER TABLE `lokasi_pemesan`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `orderan`
--
ALTER TABLE `orderan`
MODIFY `id_pesanan` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
MODIFY `id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `track`
--
ALTER TABLE `track`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=54;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=26;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `kurir`
--
ALTER TABLE `kurir`
ADD CONSTRAINT `kurir_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`user`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
