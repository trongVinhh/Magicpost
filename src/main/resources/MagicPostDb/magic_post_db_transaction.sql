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
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mass` bigint DEFAULT NULL,
  `total_price` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  `transaction_office_id` bigint DEFAULT NULL,
  `order_code` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `receive_address` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_btane64ls5uag9j1yuq0yepow` (`customer_id`),
  KEY `FK89ucxidsdvr6yke0lek1upins` (`transaction_office_id`),
  KEY `FKdy6g6syi6g7v45nodi0whyy76` (`employee_id`),
  CONSTRAINT `FK89ucxidsdvr6yke0lek1upins` FOREIGN KEY (`transaction_office_id`) REFERENCES `transaction_office` (`id`),
  CONSTRAINT `FKdy6g6syi6g7v45nodi0whyy76` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKnbpjofb5abhjg5hiovi0t3k57` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,1,10000000,25,1,1,'OD1','123424','155 xuan thuy','Tvinh','2003-06-04 07:00:00.000000'),(6,12,1234,9,2,1,'OD2','1234567890','Nha toi','Me','2003-05-29 00:00:00.000000'),(8,1,100,11,2,1,'OD3','1234567890','Tan Quang Luc Ngan','Bo','2003-05-29 00:00:00.000000'),(9,10,1001,12,2,1,'OD4','1234567890','25 Quang Tien','Anh dat','2003-05-29 00:00:00.000000'),(10,1,1,13,2,1,'OD5','1234567890','25 Quang Tien','HIhi','2003-05-29 00:00:00.000000'),(11,1,1,14,2,1,'OD6','1234567890','25 Quang Tien','HIhi','2003-05-29 00:00:00.000000'),(12,1000,1000,15,2,1,'OD7','123','144 xuan thuy','NGu','2003-05-29 00:00:00.000000'),(13,1000,1000,16,2,1,'OD8','123424','145 xuan thuy','NGu','2003-05-29 00:00:00.000000'),(14,1000,1000,17,2,1,'OD9','123424','148 xuan thuy','Tvinh','2003-05-29 00:00:00.000000'),(15,10,10010,18,2,1,'OD10','123424','148 xuan thuy','Tvinh','2003-05-29 00:00:00.000000'),(16,1,11,19,2,1,'OD11','123424','155 xuan thuy','Tvinh','2003-05-29 00:00:00.000000'),(19,2,1,22,1,1,'OD12','0938800','thac do tan quang','trong vinh','2003-06-04 07:00:00.000000'),(20,2,1007686,23,1,1,'OD13','0938800','thac do tan quang','trong vinh','2003-06-04 07:00:00.000000'),(21,2,112,24,1,1,'OD14','0938800','thac do tan quang','trong vinh','2003-06-04 07:00:00.000000');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
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
