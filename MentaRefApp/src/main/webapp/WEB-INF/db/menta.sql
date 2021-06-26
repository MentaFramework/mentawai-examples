-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Menta
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.10












--
-- Current Database: `Menta`
--



--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;


CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `locale` varchar(10) NOT NULL,
  `value` varchar(50) NOT NULL,
  PRIMARY KEY (`id`,`locale`)
);


--
-- Dumping data for table `groups`
--


INSERT INTO `groups` VALUES (1,'en_US','Guest'),(2,'en_US','Admin'),(3,'en_US','Master'),(1,'pt_BR','Convidado'),(2,'pt_BR','Administrador'),(3,'pt_BR','Mestre');


--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;


CREATE TABLE `languages` (
  `id` int(11) NOT NULL,
  `locale` varchar(10) NOT NULL DEFAULT '',
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`,`locale`)
);


--
-- Dumping data for table `languages`
--


INSERT INTO `languages` VALUES (1,'en_US','English'),(2,'en_US','Portuguese'),(1,'pt_BR','Inglês'),(2,'pt_BR','Português');


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;


CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `language_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_username` (`username`)
);


--
-- Dumping data for table `users`
--


INSERT INTO `users` VALUES (2,'soliveira','abc123','sergio@email.com.br',2,2),(3,'saoj','abc123','abc@abc.com',1,1),(5,'pelasaco','abc123','pela@pela.com.br',3,1),(6,'dilma','abc123','dilma@adsf.com',2,2),(7,'diego','abc123','de@de.com',2,2),(8,'adriano','abc123','sergio@email.com',2,2),(9,'adriano2','abc123','sergio@email.com',2,2),(10,'adriano3','sergio','sergio@email.com',3,2),(11,'porta','abc123','porta@porta.com.br',3,2),(12,'porta1111','abc123','porta@porta.com.br',3,2),(13,'porta22','abc123','sergio.oliveira.jr@gmail.com',2,2),(14,'asdfasdf','abc','asdf@asdf423.com',3,1);











-- Dump completed on 2011-10-13 12:53:37
