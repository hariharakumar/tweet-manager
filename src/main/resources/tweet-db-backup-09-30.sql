# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.22)
# Database: tweets
# Generation Time: 2018-10-01 02:01:18 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tweets
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tweets`;

CREATE TABLE `tweets` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tweetCreatedAt` varchar(256) NOT NULL,
  `tweetId` bigint(20) unsigned NOT NULL,
  `userId` bigint(20) unsigned NOT NULL,
  `text` text,
  `source` varchar(1024) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `retweetCount` bigint(20) NOT NULL,
  `favoriteCount` bigint(20) NOT NULL,
  `favorited` bit(1) DEFAULT b'0',
  `retweeted` bit(1) DEFAULT b'0',
  `truncated` bit(1) DEFAULT b'0',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tweetId_2` (`tweetId`),
  KEY `fk_tweets_users_userId` (`userId`),
  CONSTRAINT `fk_tweets_users_userId` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tweets` WRITE;
/*!40000 ALTER TABLE `tweets` DISABLE KEYS */;

INSERT INTO `tweets` (`id`, `tweetCreatedAt`, `tweetId`, `userId`, `text`, `source`, `language`, `retweetCount`, `favoriteCount`, `favorited`, `retweeted`, `truncated`, `createdAt`, `updatedAt`)
VALUES
	(24,'Tue Sep 11 13:10:23 +0000 2018',1039501407005626373,23,'Eng 4, Software Dev &amp; Engineering at Comcast (@comcastcareers) [Mount Laurel, NJ] https://t.co/dKIZKmDEvC #webservices','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(25,'Tue Sep 11 13:07:48 +0000 2018',1039500759711272961,24,'Packet hauls in $25M Series B as customized cloud vision takes shape https://t.co/I7i9sz0FVl by @ron_miller','<a href=\"https://tapbots.com/software/tweetbot/mac\" rel=\"nofollow\">Tweetbot for Mac</a>','en',3,7,b'0',b'0',b'0','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(26,'Tue Sep 11 13:01:44 +0000 2018',1039499229843124226,25,'Teens are hooked on social media. But how does it make them feel about themselves?\nhttps://t.co/RYOzneoNcY https://t.co/S87HY2Uux0','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',1,2,b'0',b'0',b'0','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(27,'Tue Sep 11 13:01:40 +0000 2018',1039499215125323776,26,'Reazon – miniKanren for Emacs https://t.co/dJBws48P75','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','de',0,1,b'0',b'0',b'0','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(29,'Tue Sep 11 13:20:34 +0000 2018',1039503972644605953,27,'RT @RealShennaFox: @realDonaldTrump 17 years on. We will never forget #September11th #NeverForget From Scotland UK  https://t.co/yWBrTdCmfS','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',263,0,b'0',b'0',b'0','2018-09-11 08:27:07','2018-09-11 08:27:07'),
	(30,'Tue Sep 11 13:20:06 +0000 2018',1039503855929774080,28,'A Boglehead explains the simplest way to manage your money https://t.co/h1mjbYStdS','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',2,7,b'0',b'0',b'0','2018-09-11 08:27:59','2018-09-11 08:27:59'),
	(32,'Tue Sep 11 13:30:08 +0000 2018',1039506378824531969,23,'Principal Platform Architect Engineer at Comcast (@comcastcareers) [West Chester, PA] https://t.co/vjBXk8ZOJG #cloud','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(33,'Tue Sep 11 13:29:28 +0000 2018',1039506210020646912,24,'RT @zackwhittaker: New: Veeam, a data security giant, exposed more than 440 million email addresses after a server lapse — one of the large…','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',8,0,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(34,'Tue Sep 11 13:29:00 +0000 2018',1039506094354137088,28,'America is moving closer to becoming a cashless society https://t.co/TjtLor0YLH https://t.co/Coe8BIfn6C','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',0,1,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(37,'Tue Sep 11 13:20:00 +0000 2018',1039503828876443649,23,'Engineer 4, Software Dev &amp; Engineering at Comcast (@comcastcareers) [Mount Laurel, NJ] https://t.co/eQxUjPwpUR #webservices','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(38,'Tue Sep 11 13:19:00 +0000 2018',1039503579147591680,28,'Tesla shares down 2.5% in premarket following downgrade https://t.co/i8T7gfA8Kd','<a href=\"http://www.marketwatch.com\" rel=\"nofollow\">MarketWatch</a>','en',1,9,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(39,'Tue Sep 11 13:18:59 +0000 2018',1039503571669200896,24,'Packet hauls in $25M Series B as customized cloud vision takes shape https://t.co/ZC8BPRaLM7 by @ron_miller','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',9,13,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(40,'Tue Sep 11 13:16:23 +0000 2018',1039502919916314624,28,'Mark Carney will remain at the helm of the Bank of England through January 2020 https://t.co/XFIrBmI1At','<a href=\"http://www.marketwatch.com\" rel=\"nofollow\">MarketWatch</a>','en',2,4,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(41,'Tue Sep 11 13:13:03 +0000 2018',1039502080946388992,25,'Recode Daily: The truth about Les Moonves; inside Elon Musk’s brain https://t.co/xo8zmDOrzt https://t.co/PjHGEeRvEk','<a href=\"http://www.voxmedia.com\" rel=\"nofollow\">Vox Media</a>','en',0,2,b'0',b'0',b'0','2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(42,'Mon Sep 17 13:05:44 +0000 2018',1041674567553609728,24,'Zortrax launches a new high-speed, high-resolution printer, the Inkspire https://t.co/gZ5qjAQVRt by @johnbiggs https://t.co/KejyweHCSX','<a href=\"http://publicize.wp.com/\" rel=\"nofollow\">WordPress.com</a>','en',0,0,b'0',b'0',b'0','2018-09-17 08:06:44','2018-09-17 08:06:44'),
	(43,'Mon Sep 17 13:05:00 +0000 2018',1041674380470824960,24,'OnePlus is developing its own smart TV https://t.co/ZiqewH1tKw by @jonrussell','<a href=\"http://publicize.wp.com/\" rel=\"nofollow\">WordPress.com</a>','en',1,2,b'0',b'0',b'0','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(44,'Mon Sep 17 13:02:03 +0000 2018',1041673639194705921,26,'JavaScript Equality Minesweeper https://t.co/hQDvpv6YiA','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','en',1,2,b'0',b'0',b'0','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(45,'Mon Sep 17 13:02:02 +0000 2018',1041673633775665153,26,'Funding Choices – Google’s new tool for GDPR compliance and content monetization https://t.co/LgZd8X51WQ','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','en',0,1,b'0',b'0',b'0','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(46,'Mon Sep 17 13:02:01 +0000 2018',1041673630713892864,29,'Join us tomorrow for #AWSTechConnect in Boston with @cloudtp to learn how your system could benefit from a cloud-ba… https://t.co/tMu23U95EQ','<a href=\"https://www.sprinklr.com\" rel=\"nofollow\">Sprinklr</a>','en',0,2,b'0',b'0',b'1','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(47,'Mon Sep 17 13:01:17 +0000 2018',1041673444335575042,23,'Document Manager (m/w/x) in an agile project organisation at CORE (Berlin, Deutschland) https://t.co/GiWKjEOB1g #agile','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','de',0,1,b'0',b'0',b'0','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(48,'Mon Sep 17 13:01:12 +0000 2018',1041673424475762690,30,'Using Stencil to build a web component library.  This article walks through:\n\n- Project structure\n- The src/compone… https://t.co/KMGY9VcOBE','<a href=\"https://buffer.com\" rel=\"nofollow\">Buffer</a>','en',2,3,b'0',b'0',b'1','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(49,'Mon Sep 17 12:57:05 +0000 2018',1041672390823079936,31,'Department Of Transportation Allocates $400 Million For National Shortcut https://t.co/wyFxFc4T9f https://t.co/yWdzQ58Qre','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',22,121,b'0',b'0',b'0','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(50,'Mon Sep 17 12:56:35 +0000 2018',1041672261743329280,24,'Altaba will spend $47 million settling three lawsuits relating to Yahoo\'s data breaches. https://t.co/NYHVWfnkxa by… https://t.co/A0g7hbneF9','<a href=\"http://publicize.wp.com/\" rel=\"nofollow\">WordPress.com</a>','en',5,6,b'0',b'0',b'1','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(51,'Mon Sep 17 12:53:16 +0000 2018',1041671430210027520,28,'Wealthy Americans may be warning of the next stock market collapse to come https://t.co/sOHzWNHiRQ https://t.co/qbM3uN7xfd','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',14,6,b'0',b'0',b'0','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(52,'Sat Sep 29 22:03:17 +0000 2018',1046158500643180544,32,'Women do not cause unwanted pregnancies. Men who ejaculate irresponsibly do, writes Gabrielle Blair https://t.co/U9Fn94bzNO','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',7,22,b'0',b'0',b'0','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(53,'Sat Sep 29 22:01:24 +0000 2018',1046158025969598464,26,'Elon Musk Settles SEC Fraud Charges https://t.co/AL0TU0Etu4','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','fr',2,2,b'0',b'0',b'0','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(54,'Sat Sep 29 21:57:59 +0000 2018',1046157163314515969,28,'5 entry-level jobs that pay more than $100,000 per year https://t.co/XDZByKUCdv https://t.co/bew9KpQT80','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',5,7,b'0',b'0',b'0','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(55,'Sat Sep 29 21:52:00 +0000 2018',1046155658226278401,33,'Riding in @kenkousen Tesla with @ntschutta','<a href=\"http://www.echofon.com/\" rel=\"nofollow\">Echofon</a>','en',1,2,b'0',b'0',b'0','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(56,'Sat Sep 29 21:50:15 +0000 2018',1046155217702711297,23,'Infrastructure Developer at JP Morgan Chase (Houston, TX) https://t.co/HIXchFke98 #java','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(57,'Sat Sep 29 22:10:03 +0000 2018',1046160201123082240,23,'Arcadia Group is hiring https://t.co/d09Az3lUpi #html5 #reactjs','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-29 17:12:14','2018-09-29 17:12:14'),
	(58,'Sat Sep 29 22:08:30 +0000 2018',1046159813892349952,28,'Stock investors ask, can the U.S. stay afloat while the rest of the world sinks? https://t.co/twmMz6LT6H','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',2,2,b'0',b'0',b'0','2018-09-29 17:12:14','2018-09-29 17:12:14'),
	(59,'Sat Sep 29 22:36:19 +0000 2018',1046166810612158464,28,'10 ‘poor man’s habits’ that anyone looking to save a buck could probably use https://t.co/APeRxODBCT','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',2,0,b'0',b'0',b'0','2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(60,'Sat Sep 29 22:30:55 +0000 2018',1046165454342037505,27,'RT @el33th4xor: Cartels are a thing. If you only have 21 participants, as in EOS, they\'ll form cartels. \n\nPoW miners also form cartels, wit…','<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone</a>','en',41,0,b'0',b'0',b'0','2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(61,'Sat Sep 29 22:26:14 +0000 2018',1046164273540550657,28,'The SEC\'s settlement of fraud charges with Elon Musk forces his removal as chairman of the Tesla board and the paym… https://t.co/PmdbGiqYIS','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',13,14,b'0',b'0',b'1','2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(62,'Sat Sep 29 22:23:50 +0000 2018',1046163671586664448,28,'The price of palladium has climbed nearly 30% over the past six weeks https://t.co/KSBSbZ81nE https://t.co/2IBIzvVoeK','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',10,13,b'0',b'0',b'0','2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(63,'Sat Sep 29 22:18:20 +0000 2018',1046162285574729728,33,'@kenkousen @ntschutta https://t.co/YgRdPln63f','<a href=\"http://www.echofon.com/\" rel=\"nofollow\">Echofon</a>','und',2,2,b'0',b'0',b'0','2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(64,'Sat Sep 29 22:46:47 +0000 2018',1046169444874178560,28,'Musk to remain CEO and board member at Tesla after fraud-case settlement https://t.co/PnQHzAZTJ9','<a href=\"http://www.marketwatch.com\" rel=\"nofollow\">MarketWatch</a>','en',0,0,b'0',b'0',b'0','2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(65,'Sat Sep 29 22:43:51 +0000 2018',1046168707972648960,28,'The 10 best cities for new grads starting out:\n\nNo. 3: Seattle\nNo. 2: Arlington, Va.\n\nCan you guess No. 1? https://t.co/1OdUiTtlUa','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',2,8,b'0',b'0',b'0','2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(66,'Sat Sep 29 22:43:23 +0000 2018',1046168591475888129,24,'Solve, MIT’s take on social innovation challenges, may be different enough to work https://t.co/ZUsxO1TO20 https://t.co/u0hSTvevcd','<a href=\"http://publicize.wp.com/\" rel=\"nofollow\">WordPress.com</a>','en',2,6,b'0',b'0',b'0','2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(67,'Sat Sep 29 22:39:04 +0000 2018',1046167506174570498,24,'Betterment keeps growing as fintech competitors rise https://t.co/ik9kEkzANT','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',6,7,b'0',b'0',b'0','2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(68,'Sun Sep 30 20:02:00 +0000 2018',1046490365333852160,30,'RT @bendhalpern: All good points here.\n\n\"In defence of vanilla JavaScript\" by John Kazer #DEVcommunity https://t.co/mlDiWxkRDl','<a href=\"https://buffer.com\" rel=\"nofollow\">Buffer</a>','en',6,0,b'0',b'0',b'0','2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(69,'Sun Sep 30 20:00:45 +0000 2018',1046490048244404224,26,'Cincinnati Joins the List of Cities Saying ‘No’ to Parking Minimums https://t.co/p8gis5ZVBh','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','en',0,1,b'0',b'0',b'0','2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(70,'Sun Sep 30 19:51:04 +0000 2018',1046487611542568973,28,'Michael Avenatti, a legitimate candidate in 2020? Steve Bannon seems to think so https://t.co/LSzsPBJDGp','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',4,5,b'0',b'0',b'0','2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(71,'Sun Sep 30 19:37:57 +0000 2018',1046484312550518784,32,'46 high school seniors. Recently voting age, or on the cusp. From nearly all corners of the country. This is life o… https://t.co/7OTja8CJJJ','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',4,19,b'0',b'0',b'1','2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(72,'Sun Sep 30 19:33:06 +0000 2018',1046483093446709249,31,'Churchgoer Blanks On Why She Is Lighting Votive Candle https://t.co/aVmxTJtZ6T https://t.co/dVnEdVoaOj','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',30,323,b'0',b'0',b'0','2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(73,'Sun Sep 30 20:12:10 +0000 2018',1046492921879515136,28,'This crazy stat shows just how dominant Amazon has become https://t.co/D8i39Dflv5 https://t.co/x5Fyobs6W2','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',3,4,b'0',b'0',b'0','2018-09-30 15:16:58','2018-09-30 15:16:58'),
	(74,'Sun Sep 30 22:07:12 +0000 2018',1046521872995799041,34,'Listen to Burzin Engineer, Founder and Chief Reliability Officer at #PhonePe talk about their journey and how Aeros… https://t.co/jGX0qcnHE7','<a href=\"https://www.socialreport.com\" rel=\"nofollow\">SocialReport.com</a>','en',0,0,b'0',b'0',b'1','2018-09-30 17:07:59','2018-09-30 17:07:59'),
	(75,'Sun Sep 30 22:05:09 +0000 2018',1046521357830434817,28,'Tesla ‘very close’ to profitability, Elon Musk tells employees https://t.co/vDZIeisAKi','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',1,6,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(76,'Sun Sep 30 22:02:00 +0000 2018',1046520563295027202,30,'RT @ThePracticalDev: \"Many people might already known that there is a command called git rebase, but most of people don\'t know how to use i…','<a href=\"https://buffer.com\" rel=\"nofollow\">Buffer</a>','en',89,0,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(77,'Sun Sep 30 22:01:22 +0000 2018',1046520406033747970,26,'Questions https://t.co/CVlcRtrSme','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','en',0,1,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(78,'Sun Sep 30 22:01:22 +0000 2018',1046520404989419522,26,'How companies use fake sites and backdated articles to censor Google\'s results https://t.co/ihWw3KXH55','<a href=\"http://www.steer.me\" rel=\"nofollow\">newsycombinator</a>','en',2,3,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(79,'Sun Sep 30 21:58:56 +0000 2018',1046519790192463873,32,'RT @abbymnorman: \"That’s how I read; as though my life depended on it. Maybe it did.\"\nhttps://t.co/sKZFWZ3fro','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',2,0,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(80,'Sun Sep 30 21:50:14 +0000 2018',1046517602825252864,23,'iOS Software Developer (Contract) at Substantial (Seattle, WA) https://t.co/ZHzv6eIUcT #ios','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','ro',0,0,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(81,'Sun Sep 30 21:44:13 +0000 2018',1046516090208231425,28,'In Indonesia, a desperate scramble to find survivors as death toll from quake, tsunami rises https://t.co/HKCqr3x5dS','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',4,4,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(82,'Sun Sep 30 21:44:03 +0000 2018',1046516048021868544,24,'Y Combinator is changing up the way it invests https://t.co/1Z6a9aLQUZ','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',9,28,b'0',b'0',b'0','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(83,'Sun Sep 30 21:37:00 +0000 2018',1046514272061345795,35,'Young people make up 25% of the entire population but 100% of our future. Catch @stephpalmeri, @km, @amitku, Adeyem… https://t.co/rzg6cQbrsW','<a href=\"https://about.twitter.com/products/tweetdeck\" rel=\"nofollow\">TweetDeck</a>','en',1,8,b'0',b'0',b'1','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(85,'Mon Oct 01 01:01:34 +0000 2018',1046565752365633538,23,'hte GmbH (@hteGmbH) is hiring https://t.co/MOMzYBHWM4 #userexperience #dotnet','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','de',0,0,b'0',b'0',b'0','2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(86,'Mon Oct 01 01:00:10 +0000 2018',1046565403022036992,25,'How Facebook could screw up Instagram: https://t.co/ooee8MBZMQ','<a href=\"https://sproutsocial.com\" rel=\"nofollow\">Sprout Social</a>','en',0,0,b'0',b'0',b'0','2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(87,'Mon Oct 01 00:59:30 +0000 2018',1046565233488203776,33,'RT @eric_amell: @venkat_s Thank you and respect for you too @venkat_s! And respect to those willing to put their careers on hold to take ca…','<a href=\"http://www.echofon.com/\" rel=\"nofollow\">Echofon</a>','en',1,0,b'0',b'0',b'0','2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(88,'Mon Oct 01 00:52:56 +0000 2018',1046563578650783744,28,'Soccer star Cristiano Ronaldo accused of 2009 rape https://t.co/GrPYlbIWiA','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',9,8,b'0',b'0',b'0','2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(89,'Mon Oct 01 00:40:00 +0000 2018',1046560325183700992,23,'BBM (@BBM) is hiring https://t.co/6lIm7EhfoO #tdd #amazonwebservices','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','in',0,0,b'0',b'0',b'0','2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(90,'Mon Oct 01 00:37:00 +0000 2018',1046559572427714601,30,'$ git commit --fixup &lt;COMMIT THAT NEEDS FIXING&gt;\n$ git rebase -i --autosquash &lt;FIRST COMMIT THAT NEEDS FIXING&gt;~1 https://t.co/GoTBmtP44b','<a href=\"https://buffer.com\" rel=\"nofollow\">Buffer</a>','en',5,23,b'0',b'0',b'0','2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(91,'Mon Oct 01 00:31:56 +0000 2018',1046558295224446991,28,'Canada’s Husky offers to buy rival oil firm MEG Energy https://t.co/KT6WgsrBo9','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',2,5,b'0',b'0',b'0','2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(92,'Mon Oct 01 00:24:04 +0000 2018',1046556317954043905,31,'Visit Home Reveals Parents Currently Watching Previously Undiscovered Game Show https://t.co/4ZXfpg8mqH https://t.co/Two5U2rIvF','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',31,272,b'0',b'0',b'0','2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(93,'Mon Oct 01 00:21:17 +0000 2018',1046555613709430784,32,'\"What I want my neighbors to know about me is that I’m so excited to be getting an education. I was dreaming and th… https://t.co/XBVC8tdigW','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',12,35,b'0',b'0',b'1','2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(94,'Mon Oct 01 00:10:38 +0000 2018',1046552936325804032,28,'California becomes first state to require female board members https://t.co/Yy4YuEeST9','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',14,23,b'0',b'0',b'0','2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(96,'Mon Oct 01 01:50:00 +0000 2018',1046577940815900672,23,'Senior Data Engineer at @thoughtworks (Melbourne, Australia) https://t.co/0tnHcPZGdJ #hadoop','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(97,'Mon Oct 01 01:48:45 +0000 2018',1046577627358609409,36,'What I find most inspiring about @pinboard isn\'t the selective distribution of tech money to political campaigns --… https://t.co/SNkeJPkFyz','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',7,37,b'0',b'0',b'1','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(98,'Mon Oct 01 01:40:00 +0000 2018',1046575424980090881,23,'MSSQL Database Developer and Web Analytics Guru at @ScholarlyIQ (Remote) https://t.co/7kM6bheeoa #sql','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','en',0,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(99,'Mon Oct 01 01:35:00 +0000 2018',1046574167489802240,30,'Awww ?? https://t.co/9CNgYliaW9','<a href=\"https://buffer.com\" rel=\"nofollow\">Buffer</a>','und',7,18,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(100,'Mon Oct 01 01:32:44 +0000 2018',1046573598570401795,32,'The adolescent brain should not be relied upon for eyewitness testimony https://t.co/UY6cBTZASH','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',5,15,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(101,'Mon Oct 01 01:30:00 +0000 2018',1046572908213080064,23,'Senior Architect (m/f) at @clarkgermany (Frankfurt am Main, Deutschland) https://t.co/MOQ4GaYEkY #rubyonrails3','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','de',0,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(102,'Mon Oct 01 01:24:24 +0000 2018',1046571498918502400,36,'This article by @sarahlovescali was definitely better than The English Patient https://t.co/7UhT7etmq8','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',0,2,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(103,'Mon Oct 01 01:23:01 +0000 2018',1046571150896189440,29,'Seattle is home for many AWS teams, including the group that considers candidates that hold a Top Secret clearance… https://t.co/XzFVMKrmCv','<a href=\"https://www.sprinklr.com\" rel=\"nofollow\">Sprinklr</a>','en',4,12,b'0',b'0',b'1','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(104,'Mon Oct 01 01:20:00 +0000 2018',1046570391198728192,23,'Senior Frontend Developer (m/f) at @clarkgermany (Frankfurt am Main, Deutschland) https://t.co/P4dkn8QzWA #rubyonrails','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','de',0,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(105,'Mon Oct 01 01:14:41 +0000 2018',1046569052783726593,28,'California approves nation\'s toughest net-neutrality rules https://t.co/LFIKVReTBJ','<a href=\"http://www.socialflow.com\" rel=\"nofollow\">SocialFlow</a>','en',10,10,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(106,'Mon Oct 01 01:14:06 +0000 2018',1046568909200084992,37,'RT @LeeMickus: Another good review. I really liked this film. \"Monsters and Men\": John David Washington drama offers a thought-provoking ta…','<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone</a>','en',5,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(107,'Mon Oct 01 01:10:00 +0000 2018',1046567874477936641,23,'Senior Software Engineer (m/f) at @clarkgermany (Frankfurt am Main, Deutschland) https://t.co/INppt5IHE0 #rubyonrails','<a href=\"https://careers.stackoverflow.com\" rel=\"nofollow\">Careers 2.0 bot</a>','de',0,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(108,'Mon Oct 01 01:09:14 +0000 2018',1046567681703391232,36,'RT @ftrain: #archivepix (saved 14 May 2012) https://t.co/7IAEUWJLdL','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',38,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(109,'Mon Oct 01 01:08:13 +0000 2018',1046567427243311104,36,'RT @TimSweeneyEpic: @mspoweruser Changing search engines is just 4 taps away on iOS, so that shows the value of being the default!','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',12,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(110,'Mon Oct 01 01:07:01 +0000 2018',1046567123974144000,36,'RT @dosnostalgic: The U.N. building has always reminded me of Scandisk/Defrag in progress https://t.co/pgpWEAgEaf','<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>','en',253,0,b'0',b'0',b'0','2018-09-30 20:57:47','2018-09-30 20:57:47');

/*!40000 ALTER TABLE `tweets` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table urls
# ------------------------------------------------------------

DROP TABLE IF EXISTS `urls`;

CREATE TABLE `urls` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(1024) DEFAULT NULL,
  `expandedUrl` varchar(1024) DEFAULT NULL,
  `displayUrl` varchar(1024) DEFAULT NULL,
  `userId` bigint(20) unsigned DEFAULT NULL,
  `tweetId` bigint(20) unsigned DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `tweetId` (`tweetId`),
  CONSTRAINT `fk_urls_tweets_tweetId` FOREIGN KEY (`tweetId`) REFERENCES `tweets` (`id`),
  CONSTRAINT `fk_urls_users_userId` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `urls` WRITE;
/*!40000 ALTER TABLE `urls` DISABLE KEYS */;

INSERT INTO `urls` (`id`, `url`, `expandedUrl`, `displayUrl`, `userId`, `tweetId`, `createdAt`, `updatedAt`)
VALUES
	(42,'https://t.co/tWyUFLafOX','http://stackoverflow.com/jobs','stackoverflow.com/jobs',23,NULL,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(43,'https://t.co/dKIZKmDEvC','https://stackoverflow.com/jobs/202014/eng-4-software-dev-engineering-comcast','stackoverflow.com/jobs/202014/en…',NULL,24,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(44,'https://t.co/b5Oyx1k1ye','http://techcrunch.com','techcrunch.com',24,NULL,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(45,'https://t.co/I7i9sz0FVl','https://tcrn.ch/2O6ke9i','tcrn.ch/2O6ke9i',NULL,25,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(46,'https://t.co/trFjw6yxoC','http://recode.net','recode.net',25,NULL,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(47,'https://t.co/RYOzneoNcY','https://www.recode.net/2018/9/10/17826810/social-media-use-teens-time-spent-facebook-instagram-snapchat?utm_campaign=recode.social&utm_content=recode&utm_medium=social&utm_source=twitter','recode.net/2018/9/10/1782…',NULL,26,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(48,'http://t.co/gliZLgXpD1','http://news.ycombinator.com/','news.ycombinator.com',26,NULL,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(49,'https://t.co/dJBws48P75','https://github.com/nickdrozd/reazon','github.com/nickdrozd/reaz…',NULL,27,'2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(50,'http://t.co/kPXKmBf59N','http://www.phunware.com','phunware.com',27,NULL,'2018-09-11 08:27:07','2018-09-11 08:27:07'),
	(51,'http://t.co/0HcV671qZB','http://www.marketwatch.com/','marketwatch.com',28,NULL,'2018-09-11 08:27:54','2018-09-11 08:27:54'),
	(52,'https://t.co/h1mjbYStdS','https://on.mktw.net/2N7OC6I','on.mktw.net/2N7OC6I',NULL,30,'2018-09-11 08:28:07','2018-09-11 08:28:07'),
	(53,'https://t.co/vjBXk8ZOJG','https://stackoverflow.com/jobs/202016/principal-platform-architect-engineer-comcast','stackoverflow.com/jobs/202016/pr…',NULL,32,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(54,'https://t.co/TjtLor0YLH','https://on.mktw.net/2N3GPa6','on.mktw.net/2N3GPa6',NULL,34,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(56,'https://t.co/eQxUjPwpUR','https://stackoverflow.com/jobs/202015/engineer-4-software-dev-engineering-comcast','stackoverflow.com/jobs/202015/en…',NULL,37,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(57,'https://t.co/i8T7gfA8Kd','https://on.mktw.net/2NzVCIQ','on.mktw.net/2NzVCIQ',NULL,38,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(58,'https://t.co/ZC8BPRaLM7','http://tcrn.ch/2N80xBl','tcrn.ch/2N80xBl',NULL,39,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(59,'https://t.co/XFIrBmI1At','https://on.mktw.net/2NwPhOs','on.mktw.net/2NwPhOs',NULL,40,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(60,'https://t.co/xo8zmDOrzt','https://www.recode.net/2018/9/11/17843984/cbs-ceo-moonves-elon-musk-brain-innovators-bannon-uber-messina-snap-imran-khan-youtube-burnout?utm_campaign=recode.net&utm_content=chorus&utm_medium=social&utm_source=twitter','recode.net/2018/9/11/1784…',NULL,41,'2018-09-11 08:32:18','2018-09-11 08:32:18'),
	(61,'https://t.co/gZ5qjAQVRt','https://tcrn.ch/2NMTkpY','tcrn.ch/2NMTkpY',NULL,42,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(62,'https://t.co/ZiqewH1tKw','https://tcrn.ch/2D0JD2W','tcrn.ch/2D0JD2W',NULL,43,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(63,'https://t.co/hQDvpv6YiA','https://slikts.github.io/js-equality-game/','slikts.github.io/js-equality-ga…',NULL,44,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(64,'https://t.co/LgZd8X51WQ','https://fundingchoices.google.com/start/','fundingchoices.google.com/start/',NULL,45,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(65,'https://t.co/8QQO0BCGlY','http://aws.amazon.com','aws.amazon.com',29,NULL,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(66,'https://t.co/tMu23U95EQ','https://twitter.com/i/web/status/1041673630713892864','twitter.com/i/web/status/1…',NULL,46,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(67,'https://t.co/GiWKjEOB1g','https://stackoverflow.com/jobs/202618/document-manager-m-w-x-in-an-agile-project-core','stackoverflow.com/jobs/202618/do…',NULL,47,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(68,'https://t.co/lhcCPP1ReQ','http://dev.to','dev.to',30,NULL,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(69,'https://t.co/KMGY9VcOBE','https://twitter.com/i/web/status/1041673424475762690','twitter.com/i/web/status/1…',NULL,48,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(70,'https://t.co/yrcmIUsgtR','http://www.theonion.com','theonion.com',31,NULL,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(71,'https://t.co/wyFxFc4T9f','https://trib.al/ZvWOe7I','trib.al/ZvWOe7I',NULL,49,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(72,'https://t.co/NYHVWfnkxa','https://tcrn.ch/2pe01mU','tcrn.ch/2pe01mU',NULL,50,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(73,'https://t.co/A0g7hbneF9','https://twitter.com/i/web/status/1041672261743329280','twitter.com/i/web/status/1…',NULL,50,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(74,'https://t.co/sOHzWNHiRQ','https://on.mktw.net/2NjU86m','on.mktw.net/2NjU86m',NULL,51,'2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(75,'https://t.co/JV5396gd2O','http://medium.com','medium.com',32,NULL,'2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(76,'https://t.co/U9Fn94bzNO','http://read.medium.com/WvfeOcZ','read.medium.com/WvfeOcZ',NULL,52,'2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(77,'https://t.co/AL0TU0Etu4','https://www.sec.gov/news/press-release/2018-226','sec.gov/news/press-rel…',NULL,53,'2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(78,'https://t.co/XDZByKUCdv','https://on.mktw.net/2DCN0NZ','on.mktw.net/2DCN0NZ',NULL,54,'2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(79,'https://t.co/ggeq74VBJ7','http://www.agiledeveloper.com','agiledeveloper.com',33,NULL,'2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(80,'https://t.co/HIXchFke98','https://stackoverflow.com/jobs/203842/infrastructure-developer-jp-morgan-chase','stackoverflow.com/jobs/203842/in…',NULL,56,'2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(81,'https://t.co/d09Az3lUpi','https://stackoverflow.com/jobs/companies/arcadia-group','stackoverflow.com/jobs/companies…',NULL,57,'2018-09-29 17:12:14','2018-09-29 17:12:14'),
	(82,'https://t.co/twmMz6LT6H','https://on.mktw.net/2NNntGq','on.mktw.net/2NNntGq',NULL,58,'2018-09-29 17:12:14','2018-09-29 17:12:14'),
	(83,'https://t.co/APeRxODBCT','https://on.mktw.net/2NNwI9D','on.mktw.net/2NNwI9D',NULL,59,'2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(84,'https://t.co/PmdbGiqYIS','https://twitter.com/i/web/status/1046164273540550657','twitter.com/i/web/status/1…',NULL,61,'2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(85,'https://t.co/KSBSbZ81nE','https://on.mktw.net/2DzTc9k','on.mktw.net/2DzTc9k',NULL,62,'2018-09-29 17:36:59','2018-09-29 17:36:59'),
	(86,'https://t.co/PnQHzAZTJ9','https://on.mktw.net/2NRI6kY','on.mktw.net/2NRI6kY',NULL,64,'2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(87,'https://t.co/1OdUiTtlUa','https://on.mktw.net/2Nb955R','on.mktw.net/2Nb955R',NULL,65,'2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(88,'https://t.co/ZUsxO1TO20','https://tcrn.ch/2xNPyn1','tcrn.ch/2xNPyn1',NULL,66,'2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(89,'https://t.co/ik9kEkzANT','https://tcrn.ch/2DD4JVx','tcrn.ch/2DD4JVx',NULL,67,'2018-09-29 17:47:05','2018-09-29 17:47:05'),
	(90,'https://t.co/mlDiWxkRDl','https://dev.to/johnkazer/in-defence-of-vanilla-javascript-aon','dev.to/johnkazer/in-d…',NULL,68,'2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(91,'https://t.co/p8gis5ZVBh','https://nextcity.org/daily/entry/cincinnati-joins-the-list-of-cities-saying-no-to-parking-minimums','nextcity.org/daily/entry/ci…',NULL,69,'2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(92,'https://t.co/LSzsPBJDGp','https://on.mktw.net/2Nceyt8','on.mktw.net/2Nceyt8',NULL,70,'2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(93,'https://t.co/7OTja8CJJJ','https://twitter.com/i/web/status/1046484312550518784','twitter.com/i/web/status/1…',NULL,71,'2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(94,'https://t.co/aVmxTJtZ6T','https://trib.al/pBByabY','trib.al/pBByabY',NULL,72,'2018-09-30 15:12:20','2018-09-30 15:12:20'),
	(95,'https://t.co/D8i39Dflv5','https://on.mktw.net/2NTVZyM','on.mktw.net/2NTVZyM',NULL,73,'2018-09-30 15:16:58','2018-09-30 15:16:58'),
	(96,'http://t.co/KSUlK5cqJi','http://www.aerospike.com','aerospike.com',34,NULL,'2018-09-30 17:07:52','2018-09-30 17:07:52'),
	(97,'https://t.co/jGX0qcnHE7','https://twitter.com/i/web/status/1046521872995799041','twitter.com/i/web/status/1…',NULL,74,'2018-09-30 17:08:05','2018-09-30 17:08:05'),
	(98,'https://t.co/vDZIeisAKi','https://on.mktw.net/2NV7dmM','on.mktw.net/2NV7dmM',NULL,75,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(99,'https://t.co/CVlcRtrSme','https://patrickcollison.com/questions','patrickcollison.com/questions',NULL,77,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(100,'https://t.co/ihWw3KXH55','https://www.lumendatabase.org/blog_entries/800','lumendatabase.org/blog_entries/8…',NULL,78,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(101,'https://t.co/sKZFWZ3fro','https://medium.com/s/not-another-first-time-story/check-out-my-friends-a18d8dc039e2','medium.com/s/not-another-…',NULL,79,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(102,'https://t.co/ZHzv6eIUcT','https://stackoverflow.com/jobs/203851/ios-software-developer-contract-substantial','stackoverflow.com/jobs/203851/io…',NULL,80,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(103,'https://t.co/HKCqr3x5dS','https://on.mktw.net/2Ncudbz','on.mktw.net/2Ncudbz',NULL,81,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(104,'https://t.co/1Z6a9aLQUZ','https://tcrn.ch/2DF8Ujz','tcrn.ch/2DF8Ujz',NULL,82,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(105,'https://t.co/EOgyBBXXvn','http://500.co','500.co',35,NULL,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(106,'https://t.co/rzg6cQbrsW','https://twitter.com/i/web/status/1046514272061345795','twitter.com/i/web/status/1…',NULL,83,'2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(107,'https://t.co/MOMzYBHWM4','https://stackoverflow.com/jobs/companies/hte-gmbh','stackoverflow.com/jobs/companies…',NULL,85,'2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(108,'https://t.co/ooee8MBZMQ','https://www.recode.net/2018/9/27/17909858/facebook-instagram-legacy-quality-taste-mike-krieger-kevin-systrom?utm_campaign=recode.social&utm_content=recode&utm_medium=social&utm_source=twitter','recode.net/2018/9/27/1790…',NULL,86,'2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(109,'https://t.co/GrPYlbIWiA','https://on.mktw.net/2N6VLiN','on.mktw.net/2N6VLiN',NULL,88,'2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(110,'https://t.co/6lIm7EhfoO','https://stackoverflow.com/jobs/companies/bbm','stackoverflow.com/jobs/companies…',NULL,89,'2018-09-30 20:06:39','2018-09-30 20:06:39'),
	(111,'https://t.co/GoTBmtP44b','https://dev.to/koffeinfrei/the-git-fixup-workflow-386d','dev.to/koffeinfrei/th…',NULL,90,'2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(112,'https://t.co/KT6WgsrBo9','https://on.mktw.net/2NPvwTb','on.mktw.net/2NPvwTb',NULL,91,'2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(113,'https://t.co/4ZXfpg8mqH','https://trib.al/BP57GyX','trib.al/BP57GyX',NULL,92,'2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(114,'https://t.co/XBVC8tdigW','https://twitter.com/i/web/status/1046555613709430784','twitter.com/i/web/status/1…',NULL,93,'2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(115,'https://t.co/Yy4YuEeST9','https://on.mktw.net/2NVglYA','on.mktw.net/2NVglYA',NULL,94,'2018-09-30 20:06:40','2018-09-30 20:06:40'),
	(116,'https://t.co/0tnHcPZGdJ','https://stackoverflow.com/jobs/203856/senior-data-engineer-thoughtworks-inc','stackoverflow.com/jobs/203856/se…',NULL,96,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(117,'http://t.co/rM9N1bQpLr','http://blog.codinghorror.com','blog.codinghorror.com',36,NULL,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(118,'https://t.co/SNkeJPkFyz','https://twitter.com/i/web/status/1046577627358609409','twitter.com/i/web/status/1…',NULL,97,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(119,'https://t.co/7kM6bheeoa','https://stackoverflow.com/jobs/203855/mssql-database-developer-and-web-analytics-guru-scholarly-iq-llc','stackoverflow.com/jobs/203855/ms…',NULL,98,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(120,'https://t.co/9CNgYliaW9','https://dev.to/stevebrownlee/finding-love-while-coding-88n','dev.to/stevebrownlee/…',NULL,99,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(121,'https://t.co/UY6cBTZASH','http://read.medium.com/ivvsshz','read.medium.com/ivvsshz',NULL,100,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(122,'https://t.co/MOQ4GaYEkY','https://stackoverflow.com/jobs/203854/senior-architect-m-f-clark-germany','stackoverflow.com/jobs/203854/se…',NULL,101,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(123,'https://t.co/7UhT7etmq8','https://popula.com/2018/09/30/sarahs-magnum-opus/','popula.com/2018/09/30/sar…',NULL,102,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(124,'https://t.co/XzFVMKrmCv','https://twitter.com/i/web/status/1046571150896189440','twitter.com/i/web/status/1…',NULL,103,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(125,'https://t.co/P4dkn8QzWA','https://stackoverflow.com/jobs/203853/senior-frontend-developer-m-f-clark-germany','stackoverflow.com/jobs/203853/se…',NULL,104,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(126,'https://t.co/LFIKVReTBJ','https://on.mktw.net/2NQ4o6y','on.mktw.net/2NQ4o6y',NULL,105,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(127,'https://t.co/mc831GJSBC','http://www.moviepass.com','moviepass.com',37,NULL,'2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(128,'https://t.co/INppt5IHE0','https://stackoverflow.com/jobs/203852/senior-software-engineer-m-f-clark-germany','stackoverflow.com/jobs/203852/se…',NULL,107,'2018-09-30 20:57:47','2018-09-30 20:57:47');

/*!40000 ALTER TABLE `urls` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) unsigned NOT NULL,
  `name` varchar(1024) DEFAULT NULL,
  `screenName` varchar(1024) DEFAULT NULL,
  `location` varchar(1024) DEFAULT NULL,
  `description` text,
  `userCreatedAt` varchar(256) DEFAULT NULL,
  `favoritesCount` bigint(20) NOT NULL,
  `statusesCount` bigint(20) NOT NULL,
  `followersCount` bigint(20) NOT NULL,
  `friendsCount` bigint(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`id`, `userId`, `name`, `screenName`, `location`, `description`, `userCreatedAt`, `favoritesCount`, `statusesCount`, `followersCount`, `friendsCount`, `language`, `createdAt`, `updatedAt`)
VALUES
	(23,190626981,'Stack Overflow Jobs','StackDevJobs','','Stack Overflow Jobs is the official careers platform of Stack Overflow, the #1 Q&A site built by programmers, for programmers.','Tue Sep 14 12:53:24 +0000 2010',390,167444,18342,322,'en','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(24,816653,'TechCrunch','TechCrunch','San Francisco, CA','Breaking technology news, analysis, and opinions from TechCrunch. Home to Disrupt, TC Sessions, and Startup Battlefield. Got a tip? tips@techcrunch.com','Wed Mar 07 01:27:09 +0000 2007',3385,186089,10026324,1035,'en','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(25,2244340904,'Recode','Recode','San Francisco, CA','Tech and business news, live. Sign up for our daily newsletter: https://t.co/tUx3axo4pW','Fri Dec 13 19:03:27 +0000 2013',2102,40813,357226,637,'en','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(26,14335498,'Hacker News','newsycombinator','The Internet','I\'m a news.ycombinator bot, get the latest from Hacker News! Built by the lovely @riklomas from @superhi_ – sport shoes welcome.','Tue Apr 08 19:58:28 +0000 2008',3,110647,182334,2,'en','2018-09-11 08:11:35','2018-09-11 08:11:35'),
	(27,39877097,'Alan Knitowski','alanknit','Austin, TX','Ex-military serial entrepreneur and father of four beautiful, talented and athletic kids.','Thu May 14 00:09:23 +0000 2009',1093,3726,1301,1132,'en','2018-09-11 08:27:07','2018-09-11 08:27:07'),
	(28,624413,'MarketWatch','MarketWatch','','News, personal finance & commentary from MarketWatch.','Thu Jan 11 05:15:48 +0000 2007',2630,239655,3581324,190,'en','2018-09-11 08:27:53','2018-09-11 08:27:53'),
	(29,66780587,'Amazon Web Services','awscloud','Seattle, WA','Official Global Twitter Feed for Amazon Web Services. New to the cloud? Start here: https://t.co/xICTf0UhQ1','Tue Aug 18 19:52:16 +0000 2009',1030,23972,1672325,722,'en','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(30,2735246778,'DEV Community ??????????','ThePracticalDev','','Great posts from the amazing https://t.co/xHvFQQ9jeO community, with some opinion and humor mixed in. Created by @bendhalpern. Join https://t.co/xHvFQQ9jeO!','Fri Aug 15 19:11:17 +0000 2014',42413,20221,150075,2267,'en','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(31,14075928,'The Onion','TheOnion','','America\'s Finest News Source.','Tue Mar 04 02:48:37 +0000 2008',1,62523,11004881,14,'en','2018-09-17 08:06:45','2018-09-17 08:06:45'),
	(32,571202103,'Medium','Medium','San Francisco, CA, US','Words matter. Learn more at https://t.co/KXvi8xUeaK. For support go to @MediumSupport or https://t.co/A7QR3wf377. To contact us,  email yourfriends@medium.com.','Fri May 04 20:16:39 +0000 2012',6526,24192,2305308,90,'en','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(33,14429713,'Venkat Subramaniam','venkat_s','Colorado','programmer, author, speaker, founder Agile Developer, Inc., creator of https://t.co/iCugymwDfM, professor of CS at U of Houston','Fri Apr 18 04:27:59 +0000 2008',0,19478,31266,330,'en','2018-09-29 17:08:29','2018-09-29 17:08:29'),
	(34,741664123,'aerospikedb','aerospikedb','Mountain View, CA','High-performance distributed database with strong consistency and high availability. Fast, scalable, predictable, flash-optimized, in-memory. #Aerospike','Mon Aug 06 23:21:26 +0000 2012',4069,8155,5089,4163,'en','2018-09-30 17:07:52','2018-09-30 17:07:52'),
	(35,168857946,'500 Startups','500Startups','Silicon Valley & The Interwebs','VC firm on a mission to discover and back the world’s most talented entrepreneurs, help create successful companies at scale & build thriving global ecosystems.','Tue Jul 20 23:42:16 +0000 2010',6996,21778,615614,2848,'en','2018-09-30 17:08:19','2018-09-30 17:08:19'),
	(36,5637652,'Jeff Atwood','codinghorror','Bay Area, CA','Indoor enthusiast. Co-founder of https://t.co/P7MEYP7MjF and https://t.co/rlk2RG61MA. Abyss domain expert. Disclaimer: I have no idea what I\'m talking about.','Sun Apr 29 20:50:37 +0000 2007',19964,61907,248061,200,'en','2018-09-30 20:57:47','2018-09-30 20:57:47'),
	(37,273457173,'MoviePass','MoviePass','New York, NY','Let\'s go to the movies | $9.95/month ?? @MoviePass_CS for Customer Support','Mon Mar 28 14:43:38 +0000 2011',3466,31654,72799,481,'en','2018-09-30 20:57:47','2018-09-30 20:57:47');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
