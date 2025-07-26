CREATE DATABASE  IF NOT EXISTS `printmanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `printmanagement`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: printmanagement
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `operators`
--

DROP TABLE IF EXISTS `operators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operators` (
  `operator_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(14) NOT NULL,
  PRIMARY KEY (`operator_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operators`
--

LOCK TABLES `operators` WRITE;
/*!40000 ALTER TABLE `operators` DISABLE KEYS */;
INSERT INTO `operators` VALUES (1,'John','Doe','john@example.com','$2a$10$01YANj7.s.ZrXltR5ArU4uK.CEi8Q8a0NLfTiMeRZP7i5pwaw4Lrq','01623112341'),(2,'Chumui','Tripura','chum@example.com','$2a$10$/QUXe0D0r..ABNpWXIVkjeqHr1EjH3ZSFZAGgBCMbFI.MRMNrbwGO','01623112341'),(3,'Amit','Roy','amit.roy@example.com','$2a$10$vI/VIf8.A3sCnQmG3GZ0duhhlDNgOsch4VM9Xn14uS5feHUGeuLk.','01700000000'),(4,'Oarisa','Rebayet','oarisa@gmail.com','$2a$10$oblbFbeqm8eJ6gkbzdThN.90IelidPibAdawiwsn1KkuowjOv9u2O','01550011001'),(5,'Rahim','Bhai','rahim@gmail.com','$2a$10$ubDRwdQzc2eLKbYSmMpy9.JEQaqCjO8L/Y96YQ1GrdmHoXQ8NIgQi','01537677823'),(6,'Karim','Bhai','karim@gmail.com','$2a$10$BWdTA/PGuydk0TCtMig9Q...8Lwl4G9f2vBb4KCR8NDxhdUspg8Q2','01887623447'),(7,'Nani','Dadi','nani@gmail.com','$2a$10$ff//9lH73heEZZnSBW9CkuMPUVb3.0xNMhyRQaGI5Fo7clECXQEEm','01995243178'),(8,'Sugar','Coat','sugar@gmail.com','$2a$10$EeGJCSv3wqkrqoVT0ZNBquAt.HRqPCoJtgTuQUxbbJO/xzWQAkqeC','01986655128'),(9,'Sanjida','Eva','eva@gmail.com','$2a$10$Dr2x9FxXPuphNYsr6IdRIeqE1HmkTuSzfmaxWtFe8q2UH7Uj16B/C','01876349234'),(10,'Abdul','Manik','abdul@gmail.com','$2a$10$381CuMR7Iw7L6dbCfVcHq.RcqEdhTOYpYRNaY64FYts2k81vW4CM2','01964218754');
/*!40000 ALTER TABLE `operators` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-04 14:46:42
