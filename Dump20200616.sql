CREATE DATABASE  IF NOT EXISTS `serasa` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `serasa`;
-- MySQL dump 10.13  Distrib 5.7.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: serasa
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.11-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contratos`
--

DROP TABLE IF EXISTS `contratos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contratos` (
  `ncont` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(30) DEFAULT NULL,
  `situacao` varchar(30) DEFAULT NULL,
  `parte` varchar(15) NOT NULL,
  `vencimento` date DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `data_cont` timestamp NOT NULL DEFAULT current_timestamp(),
  `cadast_por` varchar(35) NOT NULL,
  `idempresa` int(11) NOT NULL,
  PRIMARY KEY (`ncont`),
  KEY `idempresa` (`idempresa`),
  CONSTRAINT `contratos_ibfk_1` FOREIGN KEY (`idempresa`) REFERENCES `empresa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contratos`
--

LOCK TABLES `contratos` WRITE;
/*!40000 ALTER TABLE `contratos` DISABLE KEYS */;
INSERT INTO `contratos` VALUES (15,'Debito','Aberto','joao',NULL,120.00,'2020-06-15 01:25:51','Administrador',4),(16,'Debito','Aberto','jose',NULL,12320.00,'2020-06-15 01:28:04','Administrador',4),(17,'Debito','Aberto','maria',NULL,4123.00,'2020-06-15 01:28:37','Administrador',4),(18,'Credito','Aberto','karol',NULL,4440.00,'2020-06-15 01:30:10','Administrador',4),(19,'Credito','Aberto','karol com k',NULL,65432.00,'2020-06-15 01:30:28','Administrador',4),(20,'Credito','Aberto','zezinho',NULL,2330.00,'2020-06-15 15:19:41','Administrador',4),(21,'Debito','Aberto','maria',NULL,220.00,'2020-06-15 20:42:05','Administrador',5),(22,'Debito','Aberto','carlao',NULL,324321.00,'2020-06-15 20:42:11','Administrador',5),(23,'Debito','Aberto','joana',NULL,654.00,'2020-06-15 20:42:17','Administrador',5),(24,'Credito','Aberto','paula',NULL,21312.00,'2020-06-15 20:42:27','Administrador',5),(25,'Credito','Aberto','joao silva',NULL,332.00,'2020-06-15 20:42:37','Administrador',5),(26,'Debito','Aberto','jose',NULL,1230.00,'2020-06-15 21:05:25','Administrador',4),(27,'Debito','Aberto','kaka',NULL,1230.00,'2020-06-16 16:21:08','Administrador',4),(28,'Debito','Aberto','21321',NULL,213120.00,'2020-06-16 16:21:51','Administrador',4),(29,'Debito','Aberto','12e123',NULL,123.00,'2020-06-16 16:21:59','Administrador',4);
/*!40000 ALTER TABLE `contratos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empresa` varchar(50) NOT NULL,
  `cnpj` varchar(50) DEFAULT NULL,
  `endereco` varchar(40) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `pontos` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'Oi','121312312','Rua da Oi','9999-9999','tchau@oi.tdbem',50.00),(2,'Vivo','121312312','Rua da Vivo','8888-8888','Vivo@Morto.Vivo',50.00),(3,'Tim','3123123','Avenida Tim','8888-8888','Tim@Tim.Saude',50.00),(4,'empresa1','1111111','Rua 1 de Janeiro, 1111','1111-1111','empresa1@empresa.um',39.87),(5,'empresa2','2222222','Rua 2 de fevereiro, 2222','2222-2222','vice@segundo.prata',46.02),(6,'empresa da mariazinha','21312312','Rua da casa da maria','23232-222','hcahsadh@hashas.com',50.00);
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(30) DEFAULT NULL,
  `fone` varchar(10) DEFAULT NULL,
  `login` varchar(10) DEFAULT NULL,
  `senha` varchar(10) DEFAULT NULL,
  `perfil` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Administrador','0000-0000','admin','admin','admin'),(10,'Carlos Sedlacek','2132-3123','carlos','jiujiteiro','admin');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'serasa'
--

--
-- Dumping routines for database 'serasa'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-16 13:53:13
