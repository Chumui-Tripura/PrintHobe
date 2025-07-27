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
-- Table structure for table `printers`
--

DROP TABLE IF EXISTS `printers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `printers` (
  `printer_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `location` varchar(100) NOT NULL,
  `status` enum('AVAILABLE','NOT_AVAILABLE') DEFAULT 'NOT_AVAILABLE',
  `time_per_page_bw` double NOT NULL,
  `time_per_page_color` double NOT NULL,
  `busy_till` datetime DEFAULT NULL,
  `cost_bw` decimal(4,2) NOT NULL,
  `cost_color` decimal(4,2) NOT NULL,
  `op_id` bigint NOT NULL,
  `has_package` enum('YES','NO') DEFAULT 'NO',
  `package_price` decimal(6,2) DEFAULT NULL,
  `package_page` int DEFAULT NULL,
  `available_till` datetime DEFAULT NULL,
  PRIMARY KEY (`printer_id`),
  KEY `op_id` (`op_id`),
  CONSTRAINT `printers_ibfk_1` FOREIGN KEY (`op_id`) REFERENCES `operators` (`operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `printers`
--

LOCK TABLES `printers` WRITE;
/*!40000 ALTER TABLE `printers` DISABLE KEYS */;
INSERT INTO `printers` VALUES (1,'HP DeskJet','Room 101','NOT_AVAILABLE',2.5,4,NULL,3.50,4.00,1,'NO',NULL,NULL,NULL),(2,'HP SuperJet','Room 527','NOT_AVAILABLE',5.5,4,NULL,3.50,3.70,2,'NO',NULL,NULL,NULL),(3,'Vlo Printer','CUET Library','NOT_AVAILABLE',1.5,2.5,NULL,2.00,5.00,3,'YES',150.00,100,NULL),(4,'Nosto','Panchalaish','NOT_AVAILABLE',2.2,2.7,NULL,2.40,2.30,4,'NO',NULL,NULL,NULL),(5,'Rahim\'s Printer','Golchattar','NOT_AVAILABLE',4,5,NULL,2.00,3.00,5,'NO',NULL,NULL,NULL),(6,'Karim\'s Printer','Golchattar','NOT_AVAILABLE',4,5,NULL,3.00,5.00,6,'NO',NULL,NULL,NULL),(7,'Slow','Golchattar','NOT_AVAILABLE',3,4,NULL,3.00,4.00,7,'NO',NULL,NULL,NULL),(8,'Sugar Sweet','Os Lab','NOT_AVAILABLE',4,5,NULL,3.00,4.00,8,'NO',NULL,NULL,NULL),(9,'OS Lab Printer','GEC','AVAILABLE',3.3,4,'2025-07-27 21:33:32',3.00,4.00,9,'YES',150.00,45,'2025-07-27 22:48:00'),(10,'South','South Hall','NOT_AVAILABLE',10,12,NULL,3.00,3.50,10,'YES',100.00,40,NULL);
/*!40000 ALTER TABLE `printers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-28  0:35:45
