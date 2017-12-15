CREATE DATABASE  IF NOT EXISTS `eventDB` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `eventDB`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: eventDB
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

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
-- Table structure for table `db_article`
--

DROP TABLE IF EXISTS `db_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_article` (
  `id` char(40) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `newPriceAmount` decimal(19,2) DEFAULT NULL,
  `newPriceCurrency` varchar(255) DEFAULT NULL,
  `rentPriceAmount` decimal(19,2) DEFAULT NULL,
  `rentPriceCurrency` varchar(255) DEFAULT NULL,
  `category_id` char(40) DEFAULT NULL,
  `color_id` char(40) DEFAULT NULL,
  `group_id` char(40) DEFAULT NULL,
  `length_id` char(40) DEFAULT NULL,
  `typ_id` char(40) DEFAULT NULL,
  `typGroup_id` char(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2cwpbxensttx19pdmj4j2rgsf` (`category_id`),
  KEY `FKgcqtma6qc9nl3vnsi702eupnq` (`color_id`),
  KEY `FKab7ox5vqechnvb3qtya835eyw` (`group_id`),
  KEY `FK38w80pbo6gmqurim88cxkvxo3` (`length_id`),
  KEY `FKlwf4p9otwfq8joelb165484e3` (`typ_id`),
  KEY `FKpdvvk3o39fglifie7mr3qh887` (`typGroup_id`),
  CONSTRAINT `FK2cwpbxensttx19pdmj4j2rgsf` FOREIGN KEY (`category_id`) REFERENCES `db_category` (`id`),
  CONSTRAINT `FK38w80pbo6gmqurim88cxkvxo3` FOREIGN KEY (`length_id`) REFERENCES `db_length` (`id`),
  CONSTRAINT `FKab7ox5vqechnvb3qtya835eyw` FOREIGN KEY (`group_id`) REFERENCES `db_groups` (`id`),
  CONSTRAINT `FKgcqtma6qc9nl3vnsi702eupnq` FOREIGN KEY (`color_id`) REFERENCES `db_color` (`id`),
  CONSTRAINT `FKlwf4p9otwfq8joelb165484e3` FOREIGN KEY (`typ_id`) REFERENCES `db_typ` (`id`),
  CONSTRAINT `FKpdvvk3o39fglifie7mr3qh887` FOREIGN KEY (`typGroup_id`) REFERENCES `db_typegroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_category`
--

DROP TABLE IF EXISTS `db_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_category` (
  `id` char(40) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k3fxaq49doia8fk9c912iqho1` (`code`),
  UNIQUE KEY `UK_me18hdsnu4y2simrqupsdmv40` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_color`
--

DROP TABLE IF EXISTS `db_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_color` (
  `id` char(40) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_429l9195u60bmpmtd06uqxtqq` (`code`),
  UNIQUE KEY `UK_l82qck6dgq072eofl7igiqa4u` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_groups`
--

DROP TABLE IF EXISTS `db_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_groups` (
  `id` char(40) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `category_id` char(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmi42g5i88wpmiw28pmlselpsm` (`category_id`),
  CONSTRAINT `FKmi42g5i88wpmiw28pmlselpsm` FOREIGN KEY (`category_id`) REFERENCES `db_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_length`
--

DROP TABLE IF EXISTS `db_length`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_length` (
  `id` char(40) NOT NULL,
  `code` varchar(20) NOT NULL,
  `size` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k855ujjqdei9pecle0bopu9gq` (`code`),
  UNIQUE KEY `UK_6tfib9fmnhhi1g5mjctq66dvi` (`size`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_location`
--

DROP TABLE IF EXISTS `db_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_location` (
  `id` char(40) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pxvkcufu27k9wjeg54nl4v6ua` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_product`
--

DROP TABLE IF EXISTS `db_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_product` (
  `id` char(40) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `article_id` char(40) DEFAULT NULL,
  `location_id` char(40) DEFAULT NULL,
  `supplier_id` char(40) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4e78enmr24tqpjuey998a0fnh` (`code`),
  KEY `FKiuonaxdxmm3aopnf2jskifbfl` (`article_id`),
  KEY `FK1h19wyamb7pw7ew1j3dbc33u6` (`location_id`),
  KEY `FKdbne4eksyvrmoloxej1qkn461` (`supplier_id`),
  CONSTRAINT `FK1h19wyamb7pw7ew1j3dbc33u6` FOREIGN KEY (`location_id`) REFERENCES `db_location` (`id`),
  CONSTRAINT `FKdbne4eksyvrmoloxej1qkn461` FOREIGN KEY (`supplier_id`) REFERENCES `db_supplier` (`id`),
  CONSTRAINT `FKiuonaxdxmm3aopnf2jskifbfl` FOREIGN KEY (`article_id`) REFERENCES `db_article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_supplier`
--

DROP TABLE IF EXISTS `db_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_supplier` (
  `id` char(40) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o4x4e3m9wed1ghea59kostbrt` (`code`),
  UNIQUE KEY `UK_9b4n9qwf6u6nfivwpobm3vdou` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_typ`
--

DROP TABLE IF EXISTS `db_typ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_typ` (
  `id` char(40) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `typGroup_id` char(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ieslxnawi9wjdmedjskdcpm1a` (`name`),
  KEY `FK4247tpymj6wuycnw873fq4hfn` (`typGroup_id`),
  CONSTRAINT `FK4247tpymj6wuycnw873fq4hfn` FOREIGN KEY (`typGroup_id`) REFERENCES `db_typegroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_typegroup`
--

DROP TABLE IF EXISTS `db_typegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_typegroup` (
  `id` char(40) NOT NULL,
  `code` varchar(40) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ipt76ki0rl5rqdotoefnm0o6k` (`code`),
  UNIQUE KEY `UK_rid6u337tmhn7obkyimyxstvn` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_user`
--

DROP TABLE IF EXISTS `db_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_user` (
  `id` varchar(40) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(80) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `role_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_db_user_1_idx` (`role_id`),
  CONSTRAINT `fk_db_user_1` FOREIGN KEY (`role_id`) REFERENCES `db_user_role` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `db_user_role`
--

DROP TABLE IF EXISTS `db_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `db_user_role` (
  `id` varchar(40) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-15 22:39:57
