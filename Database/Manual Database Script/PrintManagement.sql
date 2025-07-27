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
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `doc_id` bigint NOT NULL AUTO_INCREMENT,
  `pages` int NOT NULL,
  `copies` int NOT NULL,
  `sides` enum('SINGLE_SIDED','DOUBLE_SIDED') NOT NULL,
  `color` enum('BW','COLOR') NOT NULL,
  `punching` enum('YES','NO') NOT NULL,
  `status` enum('COMPLETED','REJECTED','APPROVED','VERIFYING') DEFAULT 'VERIFYING',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `used_package` enum('YES','NO') DEFAULT 'NO',
  `file_path` varchar(255) DEFAULT NULL,
  `printer_id` bigint NOT NULL,
  `op_id` bigint NOT NULL,
  `reference_id` varchar(50) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `original_file_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`doc_id`),
  KEY `printer_id` (`printer_id`),
  KEY `op_id` (`op_id`),
  KEY `reference_id` (`reference_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `documents_ibfk_1` FOREIGN KEY (`printer_id`) REFERENCES `printers` (`printer_id`),
  CONSTRAINT `documents_ibfk_2` FOREIGN KEY (`op_id`) REFERENCES `operators` (`operator_id`),
  CONSTRAINT `documents_ibfk_3` FOREIGN KEY (`reference_id`) REFERENCES `payments` (`reference_id`),
  CONSTRAINT `documents_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,0,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-15 13:59:46',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\eb72a2c8-adce-47af-9404-70175844d0d1.pdf',1,1,NULL,1,NULL),(2,12,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-15 14:02:41',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\d6901a38-1bbd-4fef-97f7-25143c4b8e6e.pdf',1,1,NULL,1,NULL),(3,2,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-16 18:58:42',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Assignment_-_Report_1750078722451.pdf',1,1,NULL,1,NULL),(4,2,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-16 19:01:44',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Assignment_-_Report_1750078904375.pdf',1,1,NULL,1,NULL),(5,2,2,'SINGLE_SIDED','COLOR','YES','VERIFYING',NULL,NULL,'NO','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Assignment_-_Report_1750079233727.pdf',1,1,'Abdess123',1,NULL),(6,13,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-28 12:29:26',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751092165805.pdf',1,1,NULL,3,'DataFlowDiagramFinal.pdf'),(7,13,2,'SINGLE_SIDED','COLOR','YES','VERIFYING',NULL,NULL,'NO','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751092388365.pdf',1,1,'adfu3812',3,'DataFlowDiagramFinal.pdf'),(8,5,1,'SINGLE_SIDED','COLOR','NO','VERIFYING','2025-06-29 09:18:05',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_Draft_1751167084640.pdf',6,6,NULL,1,'DataFlowDiagramFinal_Draft.pdf'),(9,13,1,'SINGLE_SIDED','COLOR','NO','REJECTED','2025-06-29 11:24:35',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751174675048.pdf',9,9,NULL,3,'DataFlowDiagramFinal.pdf'),(10,13,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-06-29 11:25:30',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751174730320.pdf',9,9,NULL,3,'DataFlowDiagramFinal.pdf'),(11,8,1,'SINGLE_SIDED','COLOR','NO','REJECTED','2025-06-29 11:33:22',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Final_Part_1751175201788.pdf',9,9,NULL,3,'Final_Part.pdf'),(12,12,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-06-29 11:49:23',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\UseCase_Turab_Final_1751176162867.pdf',9,9,NULL,4,'UseCase_Turab_Final.pdf'),(13,21,1,'SINGLE_SIDED','COLOR','NO','REJECTED','2025-07-26 03:32:17','2025-07-26 03:38:41','NO','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\IP_Report_FInal_1753476715421.pdf',9,9,'RTE76rtjfOOP',3,'IP_Report_FInal.pdf'),(14,12,1,'SINGLE_SIDED','BW','YES','COMPLETED','2025-07-26 10:04:37','2025-07-26 10:10:16','NO','/files/CFG_1753502676520.pdf',9,9,'UY12093BBtd',6,'CFG.pdf'),(15,56,1,'SINGLE_SIDED','BW','NO','COMPLETED','2025-07-26 10:12:13','2025-07-26 10:20:17','NO','/files/Nondeterministic_Finite_Automata_1753503132910.pdf',9,9,'O98TTWq2',7,'Nondeterministic Finite Automata.pdf'),(16,46,1,'SINGLE_SIDED','BW','NO','REJECTED','2025-07-26 10:27:35','2025-07-26 10:35:06','NO','/files/ML_Report_Id_2104113_2104114_2104131_1753504054668.pdf',9,9,'TYe5544a',6,'ML_Report_Id_2104113_2104114_2104131.pdf'),(17,9,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-26 18:24:39','2025-07-26 18:24:43','NO','/files/Ask_Chum_1753532392408.pdf',9,9,'34YYET6789',6,'Ask_Chum.pdf'),(18,6,10,'SINGLE_SIDED','COLOR','NO','REJECTED',NULL,NULL,'YES','/files/Line_coding_Eid_1753532664827.pdf',9,9,NULL,6,'Line coding_Eid.pdf'),(19,6,10,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-26 18:25:34','2025-07-26 18:25:58','YES','/files/Line_coding_Eid_1753532664892.pdf',9,9,NULL,6,'Line coding_Eid.pdf'),(20,6,4,'SINGLE_SIDED','COLOR','YES','COMPLETED','2025-07-26 18:47:49','2025-07-26 18:47:55','YES','/files/Delta_Turab_1753534048675.pdf',9,9,NULL,6,'Delta_Turab.pdf');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `payments` VALUES ('12sdf23YYte',150.00,'2025-07-04 11:50:56','PACKAGE',4,9,'APPROVED'),('34YYET6789',36.00,'2025-07-26 18:19:52','PRINTING',6,9,'APPROVED'),('Abdess123',8.00,'2025-06-16 19:07:14','PRINTING',1,1,'VERIFYING'),('adfer223XXWe',150.00,'2025-07-22 00:34:25','PACKAGE',3,9,'REJECTED'),('adfu3812',113.00,'2025-06-28 12:33:08','PRINTING',3,1,'VERIFYING'),('ewrw',200.00,'2025-07-03 23:45:16','PACKAGE',1,3,'VERIFYING'),('O98TTWq2',168.00,'2025-07-26 10:12:13','PRINTING',7,9,'APPROVED'),('REF001234',250.00,'2025-07-03 22:45:52','PACKAGE',1,5,'VERIFYING'),('REF123456789',299.99,'2025-06-14 00:58:52','PACKAGE',1,1,'VERIFYING'),('RTE76rtjfOOP',84.00,'2025-07-26 02:51:55','PRINTING',3,9,'REJECTED'),('RTExgyTTa',150.00,'2025-07-03 23:52:55','PACKAGE',3,9,'APPROVED'),('TRw22761cce',150.00,'2025-07-26 18:48:52','PACKAGE',3,9,'REJECTED'),('TTybvsgw1098',150.00,'2025-07-25 23:03:56','PACKAGE',5,9,'APPROVED'),('TYe5544a',138.00,'2025-07-26 10:27:35','PRINTING',6,9,'REJECTED'),('UY12093BBtd',36.00,'2025-07-26 10:04:37','PRINTING',6,9,'APPROVED');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `printers` VALUES (1,'HP DeskJet','Room 101','NOT_AVAILABLE',2.5,4,NULL,3.50,4.00,1,'NO',NULL,NULL,NULL),(2,'HP SuperJet','Room 527','NOT_AVAILABLE',5.5,4,NULL,3.50,3.70,2,'NO',NULL,NULL,NULL),(3,'Vlo Printer','CUET Library','NOT_AVAILABLE',1.5,2.5,NULL,2.00,5.00,3,'YES',150.00,100,NULL),(4,'Nosto','Panchalaish','NOT_AVAILABLE',2.2,2.7,NULL,2.40,2.30,4,'NO',NULL,NULL,NULL),(5,'Rahim\'s Printer','Golchattar','NOT_AVAILABLE',4,5,NULL,2.00,3.00,5,'NO',NULL,NULL,NULL),(6,'Karim\'s Printer','Golchattar','NOT_AVAILABLE',4,5,NULL,3.00,5.00,6,'NO',NULL,NULL,NULL),(7,'Slow','Golchattar','NOT_AVAILABLE',3,4,NULL,3.00,4.00,7,'NO',NULL,NULL,NULL),(8,'Sugar Sweet','Os Lab','NOT_AVAILABLE',4,5,NULL,3.00,4.00,8,'NO',NULL,NULL,NULL),(9,'OS Lab Printer','GEC','AVAILABLE',3.3,4,'2025-07-26 18:25:28',3.00,4.00,9,'YES',150.00,45,'2025-07-26 23:44:00'),(10,'South','South Hall','NOT_AVAILABLE',10,12,NULL,3.00,3.50,10,'YES',100.00,40,NULL);
/*!40000 ALTER TABLE `printers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `package_page` int DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Chumui','Tripura','chumui@example.com','$2a$10$QaGg5y1nYXLBQ47zXEQo7.HnXXUFohJMHX5BTxCftI2cHj06HeOKS',13),(3,'Tahmima Hoque','Eid','eid@gmail.com','$2a$10$zaUiaq8RWoVovFqFatIgaeDQHa6en8UomdeGCMrKAiKPUc8k3O30.',0),(4,'Pritam','Biswas','pritam@gmail.com','$2a$10$eebs3pFF1ptiPYyBEt0Pt.ZMocWz05tTup9fDw7pd6U4chTFdIB42',45),(5,'Sahriar','Mahmud','turab@gmail.com','$2a$10$RHM/64v/rzNRso6dDeH7y.JTbpEUFUhVmObLRWBxq.DJCkERJkEYm',45),(6,'Santu','Das','santu@gmail.com','$2a$10$BXEKKmd8HGXJFoQnyyov.OM0PFhssHulGjZ1MSsmATCihgCOOl.Je',276),(7,'Arpan','Paul','arpan@gmail.com','$2a$10$tSBhYm47HI.6nvPfeLCzE.H/JQJd3ymeHl9U5ye2H6J/K2tQYPcKC',135);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-26 19:27:26
