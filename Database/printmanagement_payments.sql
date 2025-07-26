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
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `reference_id` varchar(50) NOT NULL,
  `amount` decimal(6,2) NOT NULL,
  `date_n_time` datetime NOT NULL,
  `payment_for` enum('PACKAGE','PRINTING') NOT NULL,
  `user_id` bigint NOT NULL,
  `op_id` bigint NOT NULL,
  `payment_status` enum('APPROVED','REJECTED','VERIFYING') NOT NULL DEFAULT 'VERIFYING',
  PRIMARY KEY (`reference_id`),
  KEY `user_id` (`user_id`),
  KEY `op_id` (`op_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`op_id`) REFERENCES `operators` (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES ('12sdf23YYte',150.00,'2025-07-04 11:50:56','PACKAGE',4,9,'APPROVED'),('Abdess123',8.00,'2025-06-16 19:07:14','PRINTING',1,1,'VERIFYING'),('adfu3812',113.00,'2025-06-28 12:33:08','PRINTING',3,1,'VERIFYING'),('ewrw',200.00,'2025-07-03 23:45:16','PACKAGE',1,3,'VERIFYING'),('REF001234',250.00,'2025-07-03 22:45:52','PACKAGE',1,5,'VERIFYING'),('REF123456789',299.99,'2025-06-14 00:58:52','PACKAGE',1,1,'VERIFYING'),('RTExgyTTa',150.00,'2025-07-03 23:52:55','PACKAGE',3,9,'APPROVED');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
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
