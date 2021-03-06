-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 11, 2018 at 05:20 PM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_student`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_student`
--

DROP TABLE IF EXISTS `tbl_student`;
CREATE TABLE IF NOT EXISTS `tbl_student` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FIRST NAME` varchar(30) NOT NULL,
  `SURNAME` varchar(15) NOT NULL,
  `BIRTHDATE` date NOT NULL,
  `FATHER'S NAME` varchar(35) NOT NULL,
  `MOTHER'S NAME` varchar(35) NOT NULL,
  `GENDER` varchar(20) NOT NULL,
  `MOBILE NUMBER` varchar(20) NOT NULL,
  `CLASS` varchar(20) NOT NULL,
  `ROLL` int(11) NOT NULL,
  `ADMISSION DATE` date NOT NULL,
  `ADDRESS` text NOT NULL,
  `IMAGES` blob NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
