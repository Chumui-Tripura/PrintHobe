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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,0,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-15 13:59:46',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\eb72a2c8-adce-47af-9404-70175844d0d1.pdf',1,1,NULL,1,NULL),(2,12,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-15 14:02:41',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\d6901a38-1bbd-4fef-97f7-25143c4b8e6e.pdf',1,1,NULL,1,NULL),(3,2,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-16 18:58:42',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Assignment_-_Report_1750078722451.pdf',1,1,NULL,1,NULL),(4,2,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-16 19:01:44',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Assignment_-_Report_1750078904375.pdf',1,1,NULL,1,NULL),(5,2,2,'SINGLE_SIDED','COLOR','YES','VERIFYING',NULL,NULL,'NO','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Assignment_-_Report_1750079233727.pdf',1,1,'Abdess123',1,NULL),(6,13,2,'SINGLE_SIDED','COLOR','YES','VERIFYING','2025-06-28 12:29:26',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751092165805.pdf',1,1,NULL,3,'DataFlowDiagramFinal.pdf'),(7,13,2,'SINGLE_SIDED','COLOR','YES','VERIFYING',NULL,NULL,'NO','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751092388365.pdf',1,1,'adfu3812',3,'DataFlowDiagramFinal.pdf'),(8,5,1,'SINGLE_SIDED','COLOR','NO','VERIFYING','2025-06-29 09:18:05',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_Draft_1751167084640.pdf',6,6,NULL,1,'DataFlowDiagramFinal_Draft.pdf'),(9,13,1,'SINGLE_SIDED','COLOR','NO','REJECTED','2025-06-29 11:24:35',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751174675048.pdf',9,9,NULL,3,'DataFlowDiagramFinal.pdf'),(10,13,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-06-29 11:25:30',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\DataFlowDiagramFinal_1751174730320.pdf',9,9,NULL,3,'DataFlowDiagramFinal.pdf'),(11,8,1,'SINGLE_SIDED','COLOR','NO','REJECTED','2025-06-29 11:33:22',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\Final_Part_1751175201788.pdf',9,9,NULL,3,'Final_Part.pdf'),(12,12,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-06-29 11:49:23',NULL,'YES','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\UseCase_Turab_Final_1751176162867.pdf',9,9,NULL,4,'UseCase_Turab_Final.pdf'),(13,21,1,'SINGLE_SIDED','COLOR','NO','REJECTED','2025-07-26 03:32:17','2025-07-26 03:38:41','NO','D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files\\IP_Report_FInal_1753476715421.pdf',9,9,'RTE76rtjfOOP',3,'IP_Report_FInal.pdf'),(14,12,1,'SINGLE_SIDED','BW','YES','COMPLETED','2025-07-26 10:04:37','2025-07-26 10:10:16','NO','/files/CFG_1753502676520.pdf',9,9,'UY12093BBtd',6,'CFG.pdf'),(15,56,1,'SINGLE_SIDED','BW','NO','COMPLETED','2025-07-26 10:12:13','2025-07-26 10:20:17','NO','/files/Nondeterministic_Finite_Automata_1753503132910.pdf',9,9,'O98TTWq2',7,'Nondeterministic Finite Automata.pdf'),(16,46,1,'SINGLE_SIDED','BW','NO','REJECTED','2025-07-26 10:27:35','2025-07-26 10:35:06','NO','/files/ML_Report_Id_2104113_2104114_2104131_1753504054668.pdf',9,9,'TYe5544a',6,'ML_Report_Id_2104113_2104114_2104131.pdf'),(17,9,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-26 18:24:39','2025-07-26 18:24:43','NO','/files/Ask_Chum_1753532392408.pdf',9,9,'34YYET6789',6,'Ask_Chum.pdf'),(18,6,10,'SINGLE_SIDED','COLOR','NO','REJECTED',NULL,NULL,'YES','/files/Line_coding_Eid_1753532664827.pdf',9,9,NULL,6,'Line coding_Eid.pdf'),(19,6,10,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-26 18:25:34','2025-07-26 18:25:58','YES','/files/Line_coding_Eid_1753532664892.pdf',9,9,NULL,6,'Line coding_Eid.pdf'),(20,6,4,'SINGLE_SIDED','COLOR','YES','COMPLETED','2025-07-26 18:47:49','2025-07-26 18:47:55','YES','/files/Delta_Turab_1753534048675.pdf',9,9,NULL,6,'Delta_Turab.pdf'),(21,6,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-27 12:21:56','2025-07-27 12:34:18','NO','/files/Line_coding_Eid_1753597309110.pdf',9,9,'Tuew23(TCCio',3,'Line coding_Eid.pdf'),(22,6,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-27 21:30:53','2025-07-27 21:30:55','YES','/files/Delta_Turab_1753629778244.pdf',9,9,NULL,5,'Delta_Turab.pdf'),(23,9,1,'SINGLE_SIDED','COLOR','NO','COMPLETED','2025-07-27 21:30:58','2025-07-27 21:31:01','YES','/files/Ask_Chum_1753630075759.pdf',9,9,NULL,5,'Ask_Chum.pdf');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
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
