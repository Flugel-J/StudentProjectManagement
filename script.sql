CREATE DATABASE  IF NOT EXISTS `student_project_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `student_project_management`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: student_project_management
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `class_code` text,
  `trainer_id` int DEFAULT NULL,
  `subject_id` int DEFAULT NULL,
  `class_year` int DEFAULT NULL,
  `class_term` int DEFAULT NULL,
  `block5_class` tinyint DEFAULT '0',
  `status` int DEFAULT NULL,
  PRIMARY KEY (`class_id`),
  KEY `fk_class_subject_idx` (`subject_id`),
  KEY `fk_class_user_idx` (`trainer_id`),
  CONSTRAINT `fk_class_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
  CONSTRAINT `fk_class_user` FOREIGN KEY (`trainer_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'SE1610',32,1,2022,2,0,1),(2,'SE1549',32,1,2022,2,0,1),(3,'SE1602',32,1,2022,1,1,1),(4,'SE1402',32,2,2022,2,0,1),(5,'SE1611',13,2,2022,2,0,1),(6,'SE1420',13,2,2022,3,1,1),(7,'SE1701',13,2,2022,2,0,1),(8,'SE1520',13,2,2022,3,0,2),(9,'SE1509',13,3,2022,2,0,1),(10,'SE1549',13,3,2022,2,0,1),(11,'SE1616',32,4,2022,2,1,1),(12,'SE1703',14,6,2022,1,0,1),(13,'AI1710',14,6,2022,1,0,3),(14,'SE1599',32,6,2022,2,1,2),(15,'SE1682',13,7,2022,3,0,3),(16,'SE1987',32,1,2022,2,0,1);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_user`
--

DROP TABLE IF EXISTS `class_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_user` (
  `class_id` int NOT NULL,
  `team_id` int NOT NULL,
  `user_id` int NOT NULL,
  `team_leader` tinyint DEFAULT NULL,
  `dropout_date` date DEFAULT NULL,
  `user_notes` text,
  `ongoing_eval` float DEFAULT NULL,
  `final_pres_eval` float DEFAULT NULL,
  `final_topic_eval` float DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`class_id`,`team_id`,`user_id`),
  KEY `fk_classUser_user_idx` (`user_id`),
  KEY `fk_classU_team_idx` (`team_id`),
  CONSTRAINT `fk_classU_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`),
  CONSTRAINT `fk_classUser_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `fk_classUser_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_user`
--

LOCK TABLES `class_user` WRITE;
/*!40000 ALTER TABLE `class_user` DISABLE KEYS */;
INSERT INTO `class_user` VALUES (1,2,2,1,NULL,'',1.16,2.64,3.8,1),(1,2,3,0,NULL,'',2.25,2.64,4.89,1),(1,2,4,0,NULL,'',1.16,2.64,3.8,1),(1,2,5,0,NULL,'',1.16,2.64,3.8,1),(1,2,7,0,NULL,'',1.91,2.64,4.55,1),(2,3,3,1,NULL,'',0.72,0,0.72,1),(2,3,5,0,NULL,'',0.12,0,0.12,1),(2,5,6,0,NULL,'',NULL,NULL,NULL,1),(2,5,7,1,NULL,'',NULL,NULL,NULL,1),(2,5,16,0,NULL,'',NULL,NULL,NULL,1),(3,6,2,0,NULL,'',0.63,0,0.63,1),(3,6,3,0,NULL,'',0.63,0,0.63,1),(3,6,4,0,NULL,'',0.63,0,0.63,1),(3,6,7,0,NULL,'',0.63,0,0.63,1),(3,6,16,1,NULL,'',0.63,0,0.63,1);
/*!40000 ALTER TABLE `class_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eval_criteria`
--

DROP TABLE IF EXISTS `eval_criteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eval_criteria` (
  `criteria_id` int NOT NULL AUTO_INCREMENT,
  `interation_id` int DEFAULT NULL,
  `evaluation_title` text,
  `evaluation_weight` float DEFAULT NULL,
  `team_evaluation` tinyint DEFAULT NULL,
  `criteria_order` int DEFAULT NULL,
  `max_loc` int DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`criteria_id`),
  KEY `fk_evalCri_iteration_idx` (`interation_id`),
  CONSTRAINT `fk_evalCri_iteration` FOREIGN KEY (`interation_id`) REFERENCES `iteration` (`iteration_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eval_criteria`
--

LOCK TABLES `eval_criteria` WRITE;
/*!40000 ALTER TABLE `eval_criteria` DISABLE KEYS */;
INSERT INTO `eval_criteria` VALUES (1,1,'coding logic',40,0,1,0,1),(2,12,'team work',60,1,3,180,1),(3,1,'documentation',20,1,3,100,1),(4,1,'demo functions',20,0,4,200,1),(5,12,'demo function',35,0,5,720,1),(6,2,'demo screen',30,0,1,500,1),(7,11,'demo function',30,0,2,180,1),(8,11,'Q and A',30,1,3,180,1),(9,1,'testing',20,0,4,0,1),(10,17,'demo function',35,1,5,720,1),(11,19,'demo function',10,1,1,180,1),(12,3,'documentation 1',45,1,1,200,1),(13,3,'Video demo',55,0,3,122,1),(14,12,'Presentation',5,1,1,0,1),(15,2,'Team work',70,1,2,300,1),(16,21,'coding logic',50,1,1,200,1),(17,11,'coding logic',40,1,1,200,1),(18,16,'Coding logic',80,1,1,300,1);
/*!40000 ALTER TABLE `eval_criteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feature`
--

DROP TABLE IF EXISTS `feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feature` (
  `feature_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `feature_name` text,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`feature_id`),
  KEY `fk_feature_team_idx` (`team_id`),
  CONSTRAINT `fk_feature_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feature`
--

LOCK TABLES `feature` WRITE;
/*!40000 ALTER TABLE `feature` DISABLE KEYS */;
INSERT INTO `feature` VALUES (1,1,'Admin',1),(2,1,'Author',1),(3,1,'Student',1),(4,2,'Common',1),(5,2,'Admin',1),(6,2,'Trainer',1),(7,3,'Admin',1),(8,3,'Student',1),(9,3,'Special',1),(10,4,'Admin',1),(11,4,'Home',1),(12,4,'Working',1),(13,5,'Admin',1),(14,5,'User',1),(15,5,'Acc',1);
/*!40000 ALTER TABLE `feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `function`
--

DROP TABLE IF EXISTS `function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `function` (
  `function_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `function_name` text,
  `feature_id` int DEFAULT NULL,
  `access_roles` text,
  `description` text,
  `complexity_id` int DEFAULT NULL,
  `owner_id` int DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`function_id`),
  KEY `fk_func_team_idx` (`team_id`),
  KEY `fk_func_feature_idx` (`feature_id`),
  KEY `fk_func_subjectSetting_idx` (`complexity_id`),
  KEY `fk_func_user_idx` (`owner_id`),
  CONSTRAINT `fk_func_feature` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`feature_id`),
  CONSTRAINT `fk_func_subjectSetting` FOREIGN KEY (`complexity_id`) REFERENCES `subject_setting` (`setting_id`),
  CONSTRAINT `fk_func_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`),
  CONSTRAINT `fk_func_user` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `function`
--

LOCK TABLES `function` WRITE;
/*!40000 ALTER TABLE `function` DISABLE KEYS */;
/*!40000 ALTER TABLE `function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issue` (
  `issue_id` int NOT NULL AUTO_INCREMENT,
  `assignee_id` int DEFAULT NULL,
  `issue_type` int DEFAULT NULL,
  `issue_title` text,
  `descript` text,
  `gitlab_id` text,
  `gitlab_url` text,
  `created_at` date DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  `milestone_id` int DEFAULT NULL,
  `function_ids` text,
  `labels` text,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`issue_id`),
  KEY `fk_issue_user_idx` (`assignee_id`),
  KEY `fk_issue_team_idx` (`team_id`),
  KEY `fk_issue_milestone_idx` (`milestone_id`),
  KEY `fk_issue_setting_idx` (`issue_type`),
  CONSTRAINT `fk_issue_milestone` FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`milestone_id`),
  CONSTRAINT `fk_issue_setting` FOREIGN KEY (`issue_type`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_issue_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`),
  CONSTRAINT `fk_issue_user` FOREIGN KEY (`assignee_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iteration`
--

DROP TABLE IF EXISTS `iteration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iteration` (
  `iteration_id` int NOT NULL AUTO_INCREMENT,
  `subject_id` int DEFAULT NULL,
  `iter_name` text,
  `duration` int DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `eval_weight` float DEFAULT NULL,
  `is_ongoing` tinyint DEFAULT NULL,
  PRIMARY KEY (`iteration_id`),
  KEY `fk_iter_subject_idx` (`subject_id`),
  CONSTRAINT `fk_iter_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iteration`
--

LOCK TABLES `iteration` WRITE;
/*!40000 ALTER TABLE `iteration` DISABLE KEYS */;
INSERT INTO `iteration` VALUES (1,1,'Iter1',14,1,15,1),(2,1,'Iter2',14,1,10,1),(3,1,'Iter3',14,1,10,1),(11,1,'Iter4',14,1,10,1),(12,1,'Iter5',14,1,55,0),(13,2,'Iter1',14,1,15,1),(14,2,'Iter2',14,1,10,1),(15,2,'Iter3',14,1,10,1),(16,2,'Iter4',14,1,20,1),(17,2,'Iter5',14,1,45,0),(19,3,'Iter1',14,1,15,1),(20,3,'Iter2',14,1,15,1),(21,3,'Iter3',14,1,15,1),(22,3,'Iter4',14,1,15,1),(23,3,'Iter5',14,1,40,0),(24,4,'Iter1',14,1,20,1),(25,4,'Iter2',14,1,20,1),(26,4,'Iter3',14,1,20,1),(27,4,'Iter4',14,1,20,0),(28,4,'Iter5',14,1,20,1),(29,5,'Iter1',14,1,20,1),(30,5,'Iter2',14,1,20,1),(31,5,'Iter3',14,1,20,1),(32,5,'Iter4',14,1,10,1),(33,5,'Iter5',14,1,30,0);
/*!40000 ALTER TABLE `iteration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iteration_eval`
--

DROP TABLE IF EXISTS `iteration_eval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iteration_eval` (
  `eval_id` int NOT NULL AUTO_INCREMENT,
  `iteration_id` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `bonus` float DEFAULT NULL,
  `grade` float DEFAULT NULL,
  `note` text,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`eval_id`),
  KEY `fk_iterEval_classUser_idx` (`class_id`,`team_id`,`user_id`),
  KEY `fk_iterEval_iter_idx` (`iteration_id`),
  CONSTRAINT `fk_iterEval_classUser` FOREIGN KEY (`class_id`, `team_id`, `user_id`) REFERENCES `class_user` (`class_id`, `team_id`, `user_id`),
  CONSTRAINT `fk_iterEval_iter` FOREIGN KEY (`iteration_id`) REFERENCES `iteration` (`iteration_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iteration_eval`
--

LOCK TABLES `iteration_eval` WRITE;
/*!40000 ALTER TABLE `iteration_eval` DISABLE KEYS */;
INSERT INTO `iteration_eval` VALUES (1,1,1,2,3,3,3.9,'',1),(6,3,1,2,3,0,2.85,NULL,1),(7,2,1,2,3,0,5.5,NULL,1),(8,1,1,2,2,0,1.9,NULL,1),(9,1,1,2,4,0,1.9,NULL,1),(10,1,1,2,5,0,1.9,NULL,1),(11,1,1,2,7,5,1.9,'bad',1),(12,2,1,2,2,0,4.9,NULL,1),(13,2,1,2,4,0,4.9,NULL,1),(14,2,1,2,5,0,4.9,NULL,1),(15,2,1,2,7,0,4.9,NULL,1),(16,1,2,3,3,4,0.8,'',1),(17,1,2,3,5,0,0.8,NULL,1),(18,11,1,2,2,0,3.8,NULL,1),(19,11,1,2,3,0,3.8,NULL,1),(20,11,1,2,4,0,3.8,NULL,1),(21,11,1,2,5,0,3.8,NULL,1),(22,11,1,2,7,0,3.8,NULL,1),(23,2,3,6,2,0,6.3,NULL,1),(24,2,3,6,3,0,6.3,NULL,1),(25,2,3,6,4,0,6.3,NULL,1),(26,2,3,6,7,0,6.3,NULL,1),(27,2,3,6,16,0,6.3,NULL,1),(28,12,1,2,2,0,4.8,NULL,1),(29,12,1,2,3,0,4.8,NULL,1),(30,12,1,2,4,0,4.8,NULL,1),(31,12,1,2,5,0,4.8,NULL,1),(32,12,1,2,7,0,4.8,NULL,1);
/*!40000 ALTER TABLE `iteration_eval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loc_eval`
--

DROP TABLE IF EXISTS `loc_eval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loc_eval` (
  `evaluation_id` int NOT NULL AUTO_INCREMENT,
  `evaluation_time` date DEFAULT NULL,
  `evaluation_note` text,
  `complexity_id` int DEFAULT NULL,
  `quality_id` int DEFAULT NULL,
  `tracking_id` int DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`evaluation_id`),
  KEY `fk_locEval_tracking_idx` (`tracking_id`),
  KEY `fk_locEval_subjecSetting_idx` (`complexity_id`),
  KEY `fk_locEval_subjecSetting2_idx` (`quality_id`),
  CONSTRAINT `fk_locEval_subjecSetting1` FOREIGN KEY (`complexity_id`) REFERENCES `subject_setting` (`setting_id`),
  CONSTRAINT `fk_locEval_subjecSetting2` FOREIGN KEY (`quality_id`) REFERENCES `subject_setting` (`setting_id`),
  CONSTRAINT `fk_locEval_tracking` FOREIGN KEY (`tracking_id`) REFERENCES `tracking` (`tracking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loc_eval`
--

LOCK TABLES `loc_eval` WRITE;
/*!40000 ALTER TABLE `loc_eval` DISABLE KEYS */;
INSERT INTO `loc_eval` VALUES (45,'2022-06-26','bad',1,5,1,1),(46,'2022-06-26','good',2,4,2,1),(47,'2022-06-26','nice',1,4,3,1),(48,'2022-06-26','good',2,4,5,1),(49,'2022-06-26','normal',2,7,6,1);
/*!40000 ALTER TABLE `loc_eval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_eval`
--

DROP TABLE IF EXISTS `member_eval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_eval` (
  `member_eval_id` int NOT NULL AUTO_INCREMENT,
  `evaluation_id` int DEFAULT NULL,
  `criteria_id` int DEFAULT NULL,
  `converted_loc` int DEFAULT NULL,
  `grade` float DEFAULT NULL,
  `note` text,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`member_eval_id`),
  KEY `fk_memberEval_evalCriteria_idx` (`criteria_id`),
  KEY `fk_memEval_iterEval_idx` (`evaluation_id`),
  CONSTRAINT `fk_memberEval_evalCriteria` FOREIGN KEY (`criteria_id`) REFERENCES `eval_criteria` (`criteria_id`),
  CONSTRAINT `fk_memEval_iterEval` FOREIGN KEY (`evaluation_id`) REFERENCES `iteration_eval` (`eval_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_eval`
--

LOCK TABLES `member_eval` WRITE;
/*!40000 ALTER TABLE `member_eval` DISABLE KEYS */;
INSERT INTO `member_eval` VALUES (19,1,4,480,10,NULL,1),(20,7,6,120,2,NULL,1),(21,6,13,90,7,NULL,1);
/*!40000 ALTER TABLE `member_eval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milestone`
--

DROP TABLE IF EXISTS `milestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `milestone` (
  `milestone_id` int NOT NULL AUTO_INCREMENT,
  `milestone_name` text,
  `iteration_id` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`milestone_id`),
  KEY `fk_milestone_class_idx` (`class_id`),
  KEY `fk_milestone_iteration_idx` (`iteration_id`),
  CONSTRAINT `fk_milestone_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `fk_milestone_iteration` FOREIGN KEY (`iteration_id`) REFERENCES `iteration` (`iteration_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milestone`
--

LOCK TABLES `milestone` WRITE;
/*!40000 ALTER TABLE `milestone` DISABLE KEYS */;
INSERT INTO `milestone` VALUES (1,'milestone1',1,1,'2022-01-01','2022-01-22',1),(2,'milestone5',12,1,'2022-01-01','2022-01-22',2),(3,'milestone1',1,3,'2022-01-01','2022-01-22',2),(4,'milestone2',1,1,'2022-01-22','2022-02-12',2),(5,'milestone2',1,2,'2022-01-22','2022-02-12',2),(6,'milestone2',1,3,'2022-01-22','2022-02-12',2),(7,'milestone3',1,1,'2022-01-22','2022-02-12',2),(8,'milestone3',1,2,'2022-02-12','2022-02-28',2),(9,'milestone3',1,3,'2022-02-12','2022-02-28',2),(10,'milestone2',2,1,'2022-03-01','2022-03-15',2),(11,'milestone4',2,2,'2022-03-01','2022-03-15',2),(12,'milestone4',2,3,'2022-03-01','2022-03-15',2),(13,'milestone3',3,1,'2022-03-15','2022-03-31',2),(14,'milestone5',2,2,'2022-03-15','2022-03-31',2),(15,'milestone5',2,3,'2022-03-15','2022-03-31',2),(16,'milestone4',11,1,'2022-03-31','2022-04-14',2),(17,'milestone6',2,2,'2022-03-31','2022-04-14',2),(18,'milestone6',2,3,'2022-03-31','2022-04-14',2);
/*!40000 ALTER TABLE `milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `setting_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `setting_title` text,
  `setting_value` text,
  `display_order` int NOT NULL,
  `note` text,
  `status` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES (1,1,'student','sinh viên SE',6,'FPT University\r\n',1),(2,1,'admin',NULL,1,NULL,1),(3,1,'trainer',NULL,3,NULL,1),(4,1,'author',NULL,2,NULL,1),(5,2,'WP',NULL,1,NULL,1),(6,2,'Q&A',NULL,5,'Question and answer',1),(7,2,'Task',NULL,3,NULL,1),(8,2,'Defect',NULL,4,NULL,1),(9,2,'Leakage',NULL,5,NULL,1),(10,3,'To do',NULL,1,NULL,1),(11,3,'Doing',NULL,2,NULL,1),(12,3,'Done',NULL,3,NULL,1),(33,2,'bug','bugs',1,'bugs in code java',1);
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `subject_id` int NOT NULL AUTO_INCREMENT,
  `subject_code` text,
  `subject_name` text,
  `author_id` int DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`subject_id`),
  KEY `fk_subject_user_idx` (`author_id`),
  CONSTRAINT `fk_subject_user` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'PRO1','java',9,1),(2,'SSL','soft skill',8,1),(3,'MAD','math D',9,1),(4,'SWP','project',9,1),(5,'SSL','soft skill',8,1),(6,'DBI','database',8,1),(7,'SWR','requirement',10,1),(8,'SWT','testing',12,1),(9,'SSG','team work',8,1),(10,'MAS','statistic',10,1),(11,'ITE','ethics in IT',15,1),(12,'VOV','vovinam',12,1);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_setting`
--

DROP TABLE IF EXISTS `subject_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_setting` (
  `setting_id` int NOT NULL AUTO_INCREMENT,
  `subject_id` int DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  `setting_title` text,
  `setting_value` text,
  `display_order` int DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`setting_id`),
  KEY `fk_subjectsetting_subject_idx` (`subject_id`),
  CONSTRAINT `fk_subjectsetting_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_setting`
--

LOCK TABLES `subject_setting` WRITE;
/*!40000 ALTER TABLE `subject_setting` DISABLE KEYS */;
INSERT INTO `subject_setting` VALUES (1,1,4,'complex','240',4,1),(2,1,4,'medium','120',1,1),(3,1,4,'simple','60',3,1),(4,1,5,'high','1',2,1),(5,1,5,'low','0.5',4,1),(6,1,5,'zero','0',6,1),(7,1,5,'medium','0.75',1,1),(8,2,4,'complex','240',4,1),(9,2,4,'medium','120',1,1),(10,2,4,'simple','60',3,1),(11,2,5,'high','1',2,1),(12,2,5,'medium','0.75',4,1),(13,2,5,'low','0.5',6,1),(14,2,5,'zero','0',1,1),(15,3,4,'complex','240',4,1),(16,3,4,'medium','120',1,1),(17,3,4,'simple','60',3,1),(18,3,5,'high','1',2,1),(19,3,5,'medium','0.75',4,1),(20,3,5,'low','0.5',6,1),(21,3,5,'zero','0',1,1);
/*!40000 ALTER TABLE `subject_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `team_id` int NOT NULL AUTO_INCREMENT,
  `team_code` text NOT NULL,
  `class_id` int DEFAULT NULL,
  `topic_code` text,
  `topic_name` text,
  `gitlab_url` text,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`team_id`),
  KEY `fk_team_class_idx` (`class_id`),
  CONSTRAINT `fk_team_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'G1',1,'OSW','Online store website','',1),(2,'G2',1,'LEO','Learn english online',NULL,1),(3,'G1',2,'SMW','Student management website',NULL,1),(4,'G2',2,'WSF','Website selling furniture',NULL,1),(5,'G3',2,'AW','Automotive website',NULL,1),(6,'G1',3,'SBO','Website to sell books online',NULL,1),(7,'G3',1,'LTM','Website listen to music online',NULL,1),(8,'G4',1,'CFS','Coffee shop website',NULL,1),(9,'G5',1,'WWM','Website to watch movies',NULL,1),(10,'G1',4,'TW','Travel website',NULL,1),(11,'G1',5,'WRB','Website to read books online',NULL,1),(12,'G2',6,'OLW','Online learning website',NULL,1),(13,'G1',7,'SW','Securities website',NULL,1),(14,'G1',8,'REM','Real estate management website',NULL,1),(15,'G1',9,'HM','Hospital management website',NULL,1);
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_eval`
--

DROP TABLE IF EXISTS `team_eval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_eval` (
  `team_eval_id` int NOT NULL AUTO_INCREMENT,
  `criteria_id` int DEFAULT NULL,
  `team_id` int DEFAULT NULL,
  `grade` float DEFAULT NULL,
  `note` text,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`team_eval_id`),
  KEY `fk_teamEval_team_idx` (`team_id`),
  KEY `fk_teamEval_evelCriteria_idx` (`criteria_id`),
  CONSTRAINT `fk_teamEval_evelCriteria` FOREIGN KEY (`criteria_id`) REFERENCES `eval_criteria` (`criteria_id`),
  CONSTRAINT `fk_teamEval_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_eval`
--

LOCK TABLES `team_eval` WRITE;
/*!40000 ALTER TABLE `team_eval` DISABLE KEYS */;
INSERT INTO `team_eval` VALUES (1,3,2,9.5,'good',1),(3,15,2,7,'enough',1),(5,3,3,4,'',1),(6,8,2,6,'',1),(7,17,2,5,'',1),(8,15,6,9,'',1),(9,2,2,8,'',1);
/*!40000 ALTER TABLE `team_eval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tracking`
--

DROP TABLE IF EXISTS `tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tracking` (
  `tracking_id` int NOT NULL AUTO_INCREMENT,
  `team_id` int DEFAULT NULL,
  `milestone_id` int DEFAULT NULL,
  `function_id` int DEFAULT NULL,
  `assinger_id` int DEFAULT NULL,
  `assingee_id` int DEFAULT NULL,
  `tracking_note` text,
  `updates` text,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`tracking_id`),
  KEY `fk_tracking_team_idx` (`team_id`),
  KEY `fk_tracking_func_idx` (`function_id`),
  KEY `fk_tracking_milestone_idx` (`milestone_id`),
  KEY `fk_tracking_userAssigner_idx` (`assinger_id`),
  KEY `fk_tracking_userAssignee_idx` (`assingee_id`),
  KEY `fk_trackingStatus_setting_idx` (`status`),
  CONSTRAINT `fk_tracking_func` FOREIGN KEY (`function_id`) REFERENCES `function` (`function_id`),
  CONSTRAINT `fk_tracking_milestone` FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`milestone_id`),
  CONSTRAINT `fk_tracking_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`),
  CONSTRAINT `fk_tracking_userAssignee` FOREIGN KEY (`assingee_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_tracking_userAssigner` FOREIGN KEY (`assinger_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_trackingStatus_setting` FOREIGN KEY (`status`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tracking`
--

LOCK TABLES `tracking` WRITE;
/*!40000 ALTER TABLE `tracking` DISABLE KEYS */;
INSERT INTO `tracking` VALUES (1,2,1,NULL,2,3,NULL,NULL,7),(2,2,4,NULL,2,3,NULL,NULL,7),(3,2,7,NULL,2,3,NULL,NULL,7),(4,2,1,NULL,2,3,NULL,NULL,7),(5,2,10,NULL,2,3,NULL,NULL,7),(6,2,13,NULL,2,3,NULL,NULL,7),(7,2,16,NULL,2,3,NULL,NULL,7),(8,2,2,NULL,2,3,NULL,NULL,7);
/*!40000 ALTER TABLE `tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `roll_number` text NOT NULL,
  `full_name` text NOT NULL,
  `gender` tinyint NOT NULL,
  `dob` date DEFAULT NULL,
  `email` text NOT NULL,
  `mobile` text NOT NULL,
  `avatar_link` text,
  `facebook_link` text,
  `password` text NOT NULL,
  `role_id` int NOT NULL,
  `status` tinyint DEFAULT '1',
  PRIMARY KEY (`user_id`),
  KEY `fk_user_setting_idx` (`role_id`),
  CONSTRAINT `fk_user_setting` FOREIGN KEY (`role_id`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'he101000','Trai Chu Văn ',1,'2001-01-01','admin','0232321335',NULL,'','QftkHtTYzIuF4Ed2wrtsGg==',2,1),(2,'HE153002','Nguyen Ngoc Khanh',1,'2023-09-13','khanhnnhe153002@fpt.edu.vn','0912455777',NULL,'Khanhkhanh','QftkHtTYzIuF4Ed2wrtsGg==',1,1),(3,'HE153003','Nguyen Duy Thành',0,'2000-01-06','thanhnnhe153003@fpt.edu.vn','0912547892',NULL,'DuyThanh123','QftkHtTYzIuF4Ed2wrtsGg==',1,1),(4,'HE153004','Tran Viet Hoang',1,'2000-01-06','hoangtvhe153004@fpt.edu.vn','0123456789',NULL,'Hoangtviet1','QftkHtTYzIuF4Ed2wrtsGg==',1,1),(5,'HE153005','Tran Quoc Dat',1,'2001-01-01','dattqhe153004@fpt.edu.vn','0123456789',NULL,'TranDat456','QftkHtTYzIuF4Ed2wrtsGg==',1,1),(6,'HE153006','Dinh Tien Anh',0,'2000-01-06','anhdthe153006@fpt.edu.vn','0912547892',NULL,'0123456789','QftkHtTYzIuF4Ed2wrtsGg==',1,1),(7,'he163250','Khanh Toàn',1,'2000-01-08','khanhtqhe163250@fpt.edu.vn','0123456781','intro.png',NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(8,'he102993','lan',0,'2002-01-01','lanhe102993@fpt.edu.vn','0321939292',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',4,1),(9,'he123','hue',0,'1990-01-01','huehe123@fpt.edu.vn','0123456789',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',4,1),(10,'HE153010','Nguyen Ngoc Huyen',1,'2001-01-01','huyennnhe153010@fpt.edu.vn','0123456789',NULL,'0123456789','QftkHtTYzIuF4Ed2wrtsGg==',4,1),(11,'HE153011','Hoang Quynh Anh',0,'2001-01-01','anhhqhe153011@fpt.edu.vn','0123456789',NULL,'0123456789','QftkHtTYzIuF4Ed2wrtsGg==',3,1),(12,'HE153012','Tran Ngoc Anh',1,'2001-01-01','anhtnhe153012@fpt.edu.vn','0123456789',NULL,'0123456789','QftkHtTYzIuF4Ed2wrtsGg==',4,1),(13,'HE153013','Hoang Anh Ngoc',0,'2001-01-01','ngochahe153013@fpt.edu.vn','0123456789',NULL,'Kiennn1234','QftkHtTYzIuF4Ed2wrtsGg==',3,1),(14,'HE153014','Tran Viet Linh',1,'2001-01-01','linhtvhe153014@fpt.edu.vn','0123456789',NULL,'Kiennn1234','QftkHtTYzIuF4Ed2wrtsGg==',3,0),(15,'HE153015','Hoang Khanh Linh',1,'2001-01-01','linhhkhe153015@fpt.edu.vn','0123456789',NULL,'Kiennn1234','QftkHtTYzIuF4Ed2wrtsGg==',4,0),(16,'he153014','Trai',1,'2000-01-01','traicvhe153014@fpt.edu.vn','0123456666',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(17,'HE160089','Nam Nguyen',1,'2001-01-01','namnnHE160089@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(18,'HE160090','Lan anh',0,'2001-01-01','lanHE160090@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(19,'HE160091','Nam nguyen',1,'2001-01-01','namnnHE160091@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(20,'HE160092','linh ngoc',0,'2001-01-01','linhnHE160092@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(21,'HE160093','tran van quyet',1,'2001-01-01','quyetE160093@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(22,'HE160094','Trinh Trung Khoi',1,'2001-01-01','khoittHE160094@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(23,'HE160095','Cao Van Tai',1,'2001-01-01','taicvHE160095@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(24,'HE160096','Nha Nam',1,'2001-01-01','namnHE160096@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(25,'HE160097','Tinh Khiet',0,'2001-01-01','khietnHE160097@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(26,'HE160098','Doc Dao',1,'2001-01-01','daodHE160098@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(27,'HE160099','Tran Nam Anh',1,'2001-01-01','anhtnE160099@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(28,'HE161091','Do Thuy Linh',0,'2001-01-01','linhdtHE161091@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(29,'HE161089','Cao Nam Cuong',1,'2001-01-01','cuongcnHE161089@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(30,'HE161090','Tran Van Do',1,'2001-01-01','dotvHE161090@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(31,'HE161093','Nghien Muc',1,'2001-01-01','mucngHE161093@fpt.edu.vn','123456781',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',1,1),(32,'ES153014','GV Trai',1,'2000-01-01','0362351671trai@gmail.com','123345677',NULL,NULL,'QftkHtTYzIuF4Ed2wrtsGg==',3,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-28  8:11:49
