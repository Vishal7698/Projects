-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.30-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema brain_db
--

CREATE DATABASE IF NOT EXISTS brain_db;
USE brain_db;

--
-- Definition of table `dataset`
--

DROP TABLE IF EXISTS `dataset`;
CREATE TABLE `dataset` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Area` int(10) unsigned NOT NULL,
  `Stage` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dataset`
--

/*!40000 ALTER TABLE `dataset` DISABLE KEYS */;
INSERT INTO `dataset` (`id`,`Area`,`Stage`) VALUES 
 (1,0,'Healthy'),
 (2,1,'Initial'),
 (3,2,'Initial'),
 (4,3,'Initial'),
 (5,4,'Initial'),
 (6,5,'Initial'),
 (7,6,'Initial'),
 (8,7,'Initial'),
 (9,8,'Initial'),
 (10,9,'Initial'),
 (11,10,'Initial'),
 (12,11,'Initial'),
 (13,12,'Initial'),
 (14,13,'Initial'),
 (15,14,'Initial'),
 (16,15,'Initial'),
 (17,16,'Initial'),
 (18,17,'Initial'),
 (19,18,'Initial'),
 (20,19,'Initial'),
 (21,20,'Initial'),
 (22,21,'Initial'),
 (23,22,'Initial'),
 (24,23,'Initial'),
 (25,24,'Initial'),
 (26,25,'Initial'),
 (27,26,'Initial'),
 (28,27,'Initial'),
 (29,28,'Initial'),
 (30,29,'Initial'),
 (31,30,'Initial'),
 (32,31,'Initial'),
 (33,32,'Initial'),
 (34,33,'Initial'),
 (35,34,'Initial'),
 (36,35,'Critical'),
 (37,36,'Critical'),
 (38,37,'Critical');
/*!40000 ALTER TABLE `dataset` ENABLE KEYS */;


--
-- Definition of table `tbl_doctor`
--

DROP TABLE IF EXISTS `tbl_doctor`;
CREATE TABLE `tbl_doctor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `doctor_email` varchar(45) NOT NULL,
  `doctor_pass` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_doctor`
--

/*!40000 ALTER TABLE `tbl_doctor` DISABLE KEYS */;
INSERT INTO `tbl_doctor` (`id`,`doctor_email`,`doctor_pass`) VALUES 
 (1,'doctor1@gmail.com','doc123');
/*!40000 ALTER TABLE `tbl_doctor` ENABLE KEYS */;


--
-- Definition of table `tbl_patient`
--

DROP TABLE IF EXISTS `tbl_patient`;
CREATE TABLE `tbl_patient` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `patient_name` varchar(45) NOT NULL,
  `patient_address` varchar(45) NOT NULL,
  `patient_age` varchar(45) NOT NULL,
  `patient_email` varchar(45) NOT NULL,
  `patient_mob` varchar(45) NOT NULL,
  `patient_password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_patient`
--

/*!40000 ALTER TABLE `tbl_patient` DISABLE KEYS */;
INSERT INTO `tbl_patient` (`id`,`patient_name`,`patient_address`,`patient_age`,`patient_email`,`patient_mob`,`patient_password`) VALUES 
 (1,'Bhagyashri','Pune','24','warkhadeb@gmail.com','9876543210','123'),
 (2,'Bhagyashri','Pune','24','abc3@gmail.com','9876543210','123'),
 (3,'bhagyashri','PuNE','24','warkhadeb3@gmail.com','7387186508','123');
/*!40000 ALTER TABLE `tbl_patient` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
