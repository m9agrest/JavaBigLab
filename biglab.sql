-- --------------------------------------------------------
-- Хост:                         81.90.182.112
-- Версия сервера:               11.2.2-MariaDB - MariaDB Server
-- Операционная система:         Linux
-- HeidiSQL Версия:              12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Дамп структуры базы данных biglab
CREATE DATABASE IF NOT EXISTS `biglab` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;
USE `biglab`;

-- Дамп структуры для таблица biglab.automobile
CREATE TABLE IF NOT EXISTS `automobile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mark` varchar(255) NOT NULL DEFAULT '0',
  `model` varchar(255) DEFAULT NULL,
  `gosnumber` varchar(255) NOT NULL DEFAULT '0',
  `idclient` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Дамп данных таблицы biglab.automobile: ~1 rows (приблизительно)
INSERT INTO `automobile` (`id`, `mark`, `model`, `gosnumber`, `idclient`) VALUES
	(1, 'Форд', 'Мустанг', 'о000оо', 1);

-- Дамп структуры для таблица biglab.client
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `surname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `patronym` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Дамп данных таблицы biglab.client: ~1 rows (приблизительно)
INSERT INTO `client` (`id`, `surname`, `name`, `patronym`, `phone`) VALUES
	(1, 'Абрамов', 'Игорь', 'Викторович', '8 900 222 33 44');

-- Дамп структуры для таблица biglab.employer
CREATE TABLE IF NOT EXISTS `employer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `surname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `patronym` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `house` int(11) DEFAULT NULL,
  `appartment` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Дамп данных таблицы biglab.employer: ~1 rows (приблизительно)
INSERT INTO `employer` (`id`, `surname`, `name`, `patronym`, `phone`, `city`, `street`, `house`, `appartment`) VALUES
	(2, 'Васичкин', 'Петр', 'Васильевич', '8 800 555 35 35', 'Москва', 'Пушкина', 228, 69);

-- Дамп структуры для таблица biglab.provided_service
CREATE TABLE IF NOT EXISTS `provided_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idservice` bigint(20) DEFAULT NULL,
  `idemployer` bigint(20) DEFAULT NULL,
  `idautomobile` bigint(20) DEFAULT NULL,
  `datatime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `service_id` (`idservice`) USING BTREE,
  KEY `employer_id` (`idemployer`) USING BTREE,
  KEY `client_id` (`idautomobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Дамп данных таблицы biglab.provided_service: ~1 rows (приблизительно)
INSERT INTO `provided_service` (`id`, `idservice`, `idemployer`, `idautomobile`, `datatime`) VALUES
	(1, 1, 2, 1, '2024-09-29');

-- Дамп структуры для таблица biglab.service
CREATE TABLE IF NOT EXISTS `service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Дамп данных таблицы biglab.service: ~1 rows (приблизительно)
INSERT INTO `service` (`id`, `name`, `price`, `duration`, `description`) VALUES
	(1, 'тест', 15000.00, 100, 'что-то делаем');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
