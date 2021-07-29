-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: db_latihan
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `table_divisi`
--

DROP TABLE IF EXISTS `table_divisi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_divisi` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Kode_Jabatan` varchar(20) NOT NULL,
  `Nama_Jabatan` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_divisi`
--

LOCK TABLES `table_divisi` WRITE;
/*!40000 ALTER TABLE `table_divisi` DISABLE KEYS */;
INSERT INTO `table_divisi` VALUES (1,'01','Direktur'),(2,'','Direktur'),(3,'02',''),(4,'','Staff Ahli'),(5,'00','Direktur'),(6,'','Direktur'),(7,'02',''),(8,'','Staff Ahli'),(9,'03',''),(10,'','Direktur'),(11,'04',''),(12,'','Staff Ahli'),(13,'05',''),(14,'','Direktur'),(15,'06',''),(16,'','Staff Ahli'),(17,'07',''),(18,'','Direktur'),(19,'08',''),(20,'','Staff Ahli'),(21,'11',''),(22,'','Direktur'),(23,'12',''),(24,'','Staff Ahli'),(25,'09',''),(26,'','Direktur'),(27,'10',''),(28,'','Staff Ahli'),(29,'13',''),(30,'','Direktur'),(61,'01','Direktur'),(62,'','Direktur'),(63,'02','Staf Ahli'),(64,'','Staff Ahli');
/*!40000 ALTER TABLE `table_divisi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_karyawan`
--

DROP TABLE IF EXISTS `table_karyawan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_karyawan` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Nama_Karyawan` varchar(45) DEFAULT NULL,
  `No_Ktp` varchar(45) NOT NULL,
  `Jenis_Kelamin` tinyint(1) DEFAULT NULL,
  `Tempat_Lahir` varchar(45) DEFAULT NULL,
  `Tanggal_Lahir` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_karyawan`
--

LOCK TABLES `table_karyawan` WRITE;
/*!40000 ALTER TABLE `table_karyawan` DISABLE KEYS */;
INSERT INTO `table_karyawan` VALUES (1,'Daniel Zola','36740322122',1,'Jakarta','2020-06-28'),(3,'Jihansyah','3674031401920003',1,'Tegal','1993-01-12');
/*!40000 ALTER TABLE `table_karyawan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vlo_otp`
--

DROP TABLE IF EXISTS `vlo_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vlo_otp` (
  `IID` int NOT NULL AUTO_INCREMENT,
  `User_Id` tinyint(1) DEFAULT NULL,
  `Otp_Number` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`IID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vlo_otp`
--

LOCK TABLES `vlo_otp` WRITE;
/*!40000 ALTER TABLE `vlo_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `vlo_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vlo_roles`
--

DROP TABLE IF EXISTS `vlo_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vlo_roles` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Nama_Roles` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vlo_roles`
--

LOCK TABLES `vlo_roles` WRITE;
/*!40000 ALTER TABLE `vlo_roles` DISABLE KEYS */;
INSERT INTO `vlo_roles` VALUES (1,'Recruiter Admin'),(2,'Recruiter'),(3,'Igniters'),(4,'Fuelers');
/*!40000 ALTER TABLE `vlo_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vlo_user`
--

DROP TABLE IF EXISTS `vlo_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vlo_user` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Email_Address` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Date_Of_Birth` date DEFAULT NULL,
  `Gender` tinyint(1) DEFAULT NULL,
  `Province` varchar(45) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `District` varchar(45) DEFAULT NULL,
  `Address` text,
  `Phone_Number` varchar(15) DEFAULT NULL,
  `Referal_Code` varchar(6) DEFAULT NULL,
  `Kode_Roles` tinyint NOT NULL,
  `Id_Card_Number` varchar(15) DEFAULT NULL,
  `Id_Card_Image` blob,
  `Is_Valid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vlo_user`
--

LOCK TABLES `vlo_user` WRITE;
/*!40000 ALTER TABLE `vlo_user` DISABLE KEYS */;
INSERT INTO `vlo_user` VALUES (1,'mail@mails.com','$2a$10$DqTFdZblfgl293N3kfeP6uYG9op7clRo91KYHKtznIxEZ4clKjibO','fateh','1992-02-12',0,NULL,NULL,NULL,NULL,NULL,'dYurNU',3,NULL,NULL,0),(2,'fl01@mail.com','$2a$10$RlINpDk5yJe9E4/M9OLMT.d/kMwyQrph2kveNJJbrnHcdMQfBg8RK','kono','1993-02-01',0,NULL,NULL,NULL,NULL,NULL,'dYurNU',4,NULL,NULL,1),(3,'kimi@mails.com','$2a$10$iIBVAUnvQmQd5NlDHMsk8uHdIJaVDP.u3CcyO6TLUF2xlCwwiHSAe','namess','1994-04-22',NULL,NULL,NULL,NULL,NULL,NULL,'Xh0Chu',3,NULL,NULL,1),(4,'fl2@mail.com','$2a$10$tIUyeJQHmxFozOZZGCBfRuTYh8rl4a/7kcST7geCG2mjj4JA4ef2m','tis','1995-01-12',0,NULL,NULL,NULL,NULL,NULL,'Xh0Chu',4,NULL,NULL,1),(5,'ign1@mail.com','$2a$10$uU4Vs/6ydzEoHbAzEvwmSub1o9OSJL0LWSVCN24hs3gicYpVmPILa','test','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'CPKlo7',3,NULL,NULL,1),(6,'fl4@mail.com','$2a$10$RGpzcN/A7MiOhZypBq4/vecbPsCjXZa.JNvOj.0T/PdPD.AqjOwqu','bedjo','1992-01-03',NULL,NULL,NULL,NULL,NULL,NULL,'CPKlo7',4,NULL,NULL,1),(7,'ign2@mail.com','$2a$10$EevJe3j3hCs1aSuOhTQspOvbPRsNbWLT3EjBsJaZUvn082/yU5md.','test1','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'117xhz',3,NULL,NULL,1),(8,'ign3@mail.com','$2a$10$6whE1AJMaHa6Ht3iQg2bHuh.vsxkpDOD/w8k.Cz0BIfU0Z/kTOD9W','test1','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'hDrZGI',3,NULL,NULL,1),(9,'ign4@mail.com','$2a$10$z3Vmc2xJVipBkjjW4cmuve8SFklgIV612uduiAK/R.dsUx63mAWKm','test1','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'6aja7c',3,NULL,NULL,1),(10,'ign5@mail.com','$2a$10$nKOBqg.48Kf54YcJZLQbT.tl.6/Il4b3w3vHjxvseO0S9X7j/IZji','test1','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'VZ4JRj',3,NULL,NULL,1),(11,'ign6@mail.com','$2a$10$h9lbfezWxtb0z52qIrrkDO9YSXzV1ShFKk.RlPT1iPbu0Rn/wJzQG','test1','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'Wdbqu6',3,NULL,NULL,1),(12,'ign7@mail.com','$2a$10$YV3XwwJJxeNm57ZnuOzisOQoSTqkIrdUkM76kTxrcDnqUgWwhtWIW','test1','1994-08-08',0,NULL,NULL,NULL,NULL,NULL,'3uQJEo',3,NULL,NULL,1);
/*!40000 ALTER TABLE `vlo_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-26 19:40:23
