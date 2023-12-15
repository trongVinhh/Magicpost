CREATE DATABASE  IF NOT EXISTS `magic_post_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `magic_post_db`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: magic_post_db
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2,'Nam Tu Liem-HN','21020800@vnu.edu.vn','Trong','Vinh','0367782965'),(3,'Cau giay-HN','21020800@vnu.edu.vn','Vinh','Trong','0367782965'),(4,'70 Quang tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(9,'1010 Dinh thon','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(11,'105 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(12,'105 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(13,'105 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(14,'105 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(15,'101 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(16,'1012 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(17,'1012 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(18,'10121 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(19,'1 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965'),(20,'Mi Dinh-HN','21020800@vnu.edu.vn','Vinh','Trong','0367782965'),(21,'Mi Dinh-HN','21020800@vnu.edu.vn','Vinh Lam','Trong','0367782965'),(22,'Mi Dinh-HN','21020800@vnu.edu.vn','Vinh Lam','Trong','0367782965'),(23,'Mi Dinh-HN','21020800@vnu.edu.vn','Vinh Lam','Trong','0367782965'),(24,'Mi Dinh-HN','21020800@vnu.edu.vn','Nguyen van','Trong','0367782965'),(25,'1 Quang Tien','lamtrongvinh2905@gmail.com','Lam Trong','Vinh','0367782965');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-23 12:21:57
