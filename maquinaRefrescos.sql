-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 31, 2017 at 02:34 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `maquinaRefrescos`
--

-- --------------------------------------------------------

--
-- Table structure for table `depositos`
--

CREATE TABLE `depositos` (
  `valor` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `nombreMoneda` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `depositos`
--

INSERT INTO `depositos` (`valor`, `cantidad`, `nombreMoneda`) VALUES
(10, 8, 'Diez cÃ©ntimos'),
(20, 2, 'Veinte cÃ©ntimos'),
(50, 3, 'Cincuenta centimos'),
(100, 15, 'Un euro'),
(200, 2, 'Dos euros');

-- --------------------------------------------------------

--
-- Table structure for table `dispensadores`
--

CREATE TABLE `dispensadores` (
  `clave` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `precio` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dispensadores`
--

INSERT INTO `dispensadores` (`clave`, `nombre`, `precio`, `cantidad`) VALUES
('caldo', 'Caldo', 50, 1),
('coca', 'Coca', 100, 0),
('fanta', 'Fanta de Naranja', 60, 3),
('gazpacho', 'Pipas', 12, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `depositos`
--
ALTER TABLE `depositos`
  ADD PRIMARY KEY (`valor`);

--
-- Indexes for table `dispensadores`
--
ALTER TABLE `dispensadores`
  ADD PRIMARY KEY (`clave`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
