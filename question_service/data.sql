-- MySQL dump 10.16  Distrib 10.1.48-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	10.1.48-MariaDB-0+deb9u2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `HTE_question`
--

DROP TABLE IF EXISTS `HTE_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HTE_question` (
  `rn_` varchar(0) DEFAULT NULL,
  `id` varchar(0) DEFAULT NULL,
  `hib_sess_id` varchar(0) DEFAULT NULL,
  `answer` varchar(0) DEFAULT NULL,
  `description` varchar(0) DEFAULT NULL,
  `options` varchar(0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HTE_question`
--

LOCK TABLES `HTE_question` WRITE;
/*!40000 ALTER TABLE `HTE_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `HTE_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` tinyint(4) DEFAULT NULL,
  `answer` varchar(1) DEFAULT NULL,
  `description` varchar(49) DEFAULT NULL,
  `options` varchar(88) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'a','What is the capital of France?','{\"a\":\"Paris\", \"b\":\"London\", \"c\":\"Berlin\", \"d\":\"Madrid\"}'),(2,'b','Which planet is known as the Red Planet?','{\"a\":\"Earth\", \"b\":\"Mars\", \"c\":\"Jupiter\", \"d\":\"Saturn\"}'),(3,'d','What is the largest ocean on Earth?','{\"a\":\"Atlantic Ocean\", \"b\":\"Indian Ocean\", \"c\":\"Arctic Ocean\", \"d\":\"Pacific Ocean\"}'),(4,'a','Who wrote the play \'Romeo and Juliet\'?','{\"a\":\"William Shakespeare\", \"b\":\"Charles Dickens\", \"c\":\"J.K. Rowling\", \"d\":\"Mark Twain\"}'),(5,'a','What is the chemical symbol for water?','{\"a\":\"H2O\", \"b\":\"O2\", \"c\":\"CO2\", \"d\":\"H2\"}'),(6,'b','What is the smallest prime number?','{\"a\":\"1\", \"b\":\"2\", \"c\":\"3\", \"d\":\"5\"}'),(7,'a','What is the capital city of Japan?','{\"a\":\"Tokyo\", \"b\":\"Beijing\", \"c\":\"Seoul\", \"d\":\"Bangkok\"}'),(8,'c','Which element has the atomic number 1?','{\"a\":\"Helium\", \"b\":\"Oxygen\", \"c\":\"Hydrogen\", \"d\":\"Nitrogen\"}'),(9,'a','Who is known as the \'Father of Computer Science\'?','{\"a\":\"Alan Turing\", \"b\":\"Bill Gates\", \"c\":\"Steve Jobs\", \"d\":\"Tim Berners-Lee\"}'),(10,'c','What is the largest planet in our Solar System?','{\"a\":\"Earth\", \"b\":\"Venus\", \"c\":\"Jupiter\", \"d\":\"Mars\"}'),(11,'c','What is the square root of 64?','{\"a\":\"6\", \"b\":\"7\", \"c\":\"8\", \"d\":\"9\"}');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_seq`
--

DROP TABLE IF EXISTS `question_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_seq` (
  `next_val` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_seq`
--

LOCK TABLES `question_seq` WRITE;
/*!40000 ALTER TABLE `question_seq` DISABLE KEYS */;
INSERT INTO `question_seq` VALUES (101);
/*!40000 ALTER TABLE `question_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-30 16:42:41
