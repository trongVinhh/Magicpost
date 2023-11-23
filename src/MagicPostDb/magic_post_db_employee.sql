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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `storage_id` bigint DEFAULT NULL,
  `transaction_office_id` bigint DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl3db9pp6kvfjvjum0s3pfc2sc` (`storage_id`),
  KEY `FKjtcyh369gekexgnxll099mrqr` (`transaction_office_id`),
  CONSTRAINT `FKjtcyh369gekexgnxll099mrqr` FOREIGN KEY (`transaction_office_id`) REFERENCES `transaction_office` (`id`),
  CONSTRAINT `FKl3db9pp6kvfjvjum0s3pfc2sc` FOREIGN KEY (`storage_id`) REFERENCES `storage_office` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'HN-VN','210-208003@gmail.com','Nguyen Van','A','12345678',1,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh0'),(2,'HN-VN','abdsa@gmail.com','Tran Duc','B','1234145678',1,NULL,'$2a$10$6x.UhZDlhZ7GHJOKLxE3..ScO9fPW3Eoa1UJ3TeAadaVzp8C7/AW2','trongvinh'),(4,'Luc ngan - Bac Giang','dsda@gmail.com','Van','B','01231',1,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh1'),(5,'Luc ngan - Bac Giang','dsda@gmail.com','Van','C','01231',1,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh2'),(6,'Luc ngan - Bac Giang','dsda@gmail.com','Van','D','01231',1,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh3'),(7,'Luc ngan - Bac Giang','dsda@gmail.com','Van','E','01231',1,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh4'),(8,'Luc ngan - Bac Giang','dsda@gmail.com','Van','F','01231',NULL,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh5'),(103,'Luc ngan - Bac Giang','abcxyz@gmail.com','Tỏn vinh lam','a','01231',NULL,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh6'),(104,'Luc ngan - Bac Giang','abcxyz@gmail.com','Trong vinh lam','a','01231',NULL,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh7'),(153,'Luc ngan - Bac Giang','abcxyz@gmail.com','shipper vinh','a','01231',NULL,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','trongvinh8'),(252,'Luc ngan - Bac Giang','ADMIN@magicpost.com','','ADMIN','88888',NULL,NULL,'$2a$10$vJM72x6Z59BgB8vsFtu5pOnti93wdVeWeM5NQGmX6H.m4TdVJQxQq','admin');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
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
