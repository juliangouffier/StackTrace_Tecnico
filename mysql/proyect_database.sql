CREATE DATABASE  IF NOT EXISTS `wallet_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wallet_db`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: wallet_db
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `criptodivisas`
--

DROP TABLE IF EXISTS `criptodivisas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criptodivisas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_divisa` float DEFAULT NULL,
  `nombre_divisa` varchar(255) DEFAULT NULL,
  `valor_divisa` float DEFAULT NULL,
  `billetera_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaodhga6vuifo08gqnxdlskokb` (`billetera_id`),
  CONSTRAINT `FKaodhga6vuifo08gqnxdlskokb` FOREIGN KEY (`billetera_id`) REFERENCES `wallets` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criptodivisas`
--

LOCK TABLES `criptodivisas` WRITE;
/*!40000 ALTER TABLE `criptodivisas` DISABLE KEYS */;
INSERT INTO `criptodivisas` VALUES (1,0,'BTC',100,1),(2,0,'USDT',2,1),(3,0,'BTC',100,2),(4,0,'USDT',2,2),(5,2,'BTC',100,3),(6,0,'USDT',2,3),(7,3,'BTC',100,4),(8,10,'USDT',2,4);
/*!40000 ALTER TABLE `criptodivisas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'ROLE_USER'),(2,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposcriptos`
--

DROP TABLE IF EXISTS `tiposcriptos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposcriptos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_cripto` varchar(255) DEFAULT NULL,
  `valor_cripto` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3rt9tdrt16yppt26bcxuokx0l` (`nombre_cripto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposcriptos`
--

LOCK TABLES `tiposcriptos` WRITE;
/*!40000 ALTER TABLE `tiposcriptos` DISABLE KEYS */;
INSERT INTO `tiposcriptos` VALUES (1,'BTC',100),(2,'USDT',2);
/*!40000 ALTER TABLE `tiposcriptos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transacciones`
--

DROP TABLE IF EXISTS `transacciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transacciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `billetera_Destino` varchar(255) DEFAULT NULL,
  `billetera_Origen` varchar(255) DEFAULT NULL,
  `cantidad` float NOT NULL,
  `criptomoneda` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `operacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transacciones`
--

LOCK TABLES `transacciones` WRITE;
/*!40000 ALTER TABLE `transacciones` DISABLE KEYS */;
INSERT INTO `transacciones` VALUES (1,'5327982137708348332','-',5,'BTC','2023-06-18','18:16:06.438','DEPOSITO'),(2,'5327982137708348332','-',10,'USDT','2023-06-18','18:16:34.589','DEPOSITO'),(3,'2301274267673533090','5327982137708348332',2,'BTC','2023-06-18','18:17:57.911','TRANSFERENCIA');
/*!40000 ALTER TABLE `transacciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test','222321333','test@hotmail.com','test','$2a$10$WNameRih9obr9NCq3Y3rWeEooPBCc/Y8pErMMJfKyNLh7slU8gXYS','hombre','2554324455'),(2,'pruebaa','123112331','prueba@hotmail.com','prueba','$2a$10$iBe7tLYX9N6LtFjaP7I1euFFhyEqo.ysZbuO92TDTsYHwqZ.Av.EG','hombre','123123123'),(3,'jackson','23123231','mjackson@gmail.com','michael','$2a$10$GIziTmhSYVkKnu8JM6rbSO1iWylJZXevZLD23mHF37f/wlGPkjW4y','hombre','231221313'),(4,'casan','12857952','moricasan@hotmail.com','moria','$2a$10$8PZrViUAgrzyz1L4GqqX9ul2DJACuGuJcW70n7aAx2F4p.uHEmxva','mujer','2554324455');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_roles` (
  `usuario_id` bigint NOT NULL,
  `rol_id` bigint NOT NULL,
  KEY `FK6yxg1lhuv5nievqea7rvn6afm` (`rol_id`),
  KEY `FKgsye0r4nsxdmosxec7dv6wl8` (`usuario_id`),
  CONSTRAINT `FK6yxg1lhuv5nievqea7rvn6afm` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`),
  CONSTRAINT `FKgsye0r4nsxdmosxec7dv6wl8` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_roles`
--

LOCK TABLES `usuarios_roles` WRITE;
/*!40000 ALTER TABLE `usuarios_roles` DISABLE KEYS */;
INSERT INTO `usuarios_roles` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `usuarios_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallets`
--

DROP TABLE IF EXISTS `wallets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hash` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfgwp895e4t5pptd38sn6ddh8l` (`hash`),
  KEY `FK31vt0ptq98jwwxqxpix5ll6ws` (`usuario_id`),
  CONSTRAINT `FK31vt0ptq98jwwxqxpix5ll6ws` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallets`
--

LOCK TABLES `wallets` WRITE;
/*!40000 ALTER TABLE `wallets` DISABLE KEYS */;
INSERT INTO `wallets` VALUES (1,2512280096171240421,1),(2,3429793609625317492,2),(3,2301274267673533090,3),(4,5327982137708348332,4);
/*!40000 ALTER TABLE `wallets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-18 18:19:46
