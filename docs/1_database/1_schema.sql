/*Table structure for table `tb_bonus_input` */



DROP TABLE IF EXISTS `tb_bonus_input`;



CREATE TABLE `tb_bonus_input` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `stage` varchar(20) DEFAULT NULL,
  `json_data` text,
  `register_time` varchar(14) DEFAULT NULL,
  `db_path` varchar(1000) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `tb_bonus_result` */



DROP TABLE IF EXISTS `tb_bonus_result`;



CREATE TABLE `tb_bonus_result` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `input_seq` int(11) DEFAULT NULL,
  `stage` varchar(20) DEFAULT NULL,
  `register_time` varchar(14) DEFAULT NULL,
  `play_game_count` bigint(20) DEFAULT NULL,
  `total_bet` bigint(20) DEFAULT NULL,
  `payout_rate` double DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `payout` bigint(20) DEFAULT NULL,
  `win_game_rate` double DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `tb_freespin_input` */



DROP TABLE IF EXISTS `tb_freespin_input`;



CREATE TABLE `tb_freespin_input` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `stage` varchar(20) DEFAULT NULL,
  `json_data` text,
  `register_time` varchar(14) DEFAULT NULL,
  `db_path` varchar(1000) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `tb_freespin_result` */



DROP TABLE IF EXISTS `tb_freespin_result`;



CREATE TABLE `tb_freespin_result` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `input_seq` int(11) DEFAULT NULL,
  `stage` varchar(20) DEFAULT NULL,
  `register_time` varchar(14) DEFAULT NULL,
  `play_game_count` bigint(20) DEFAULT NULL,
  `total_bet` bigint(20) DEFAULT NULL,
  `payout_rate` double DEFAULT NULL,
  `freespin_scatter_count_total` bigint(20) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `payout` bigint(20) DEFAULT NULL,
  `win_game_rate` double DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*Table structure for table `tb_simulator_input` */



DROP TABLE IF EXISTS `tb_simulator_input`;



CREATE TABLE `tb_simulator_input` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `stage` varchar(20) DEFAULT NULL,
  `json_data` text,
  `register_time` varchar(14) DEFAULT NULL,
  `db_path` varchar(1000) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*Table structure for table `tb_simulator_result` */



DROP TABLE IF EXISTS `tb_simulator_result`;



CREATE TABLE `tb_simulator_result` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `input_seq` int(11) DEFAULT NULL,
  `stage` varchar(20) DEFAULT NULL,
  `register_time` varchar(14) DEFAULT NULL,
  `play_game_count` bigint(20) DEFAULT NULL,
  `total_bet` bigint(20) DEFAULT NULL,
  `payout_rate` double DEFAULT NULL,
  `win_game_rate` double DEFAULT NULL,
  `line_payout_rate` double DEFAULT NULL,
  `win_line_rate` double DEFAULT NULL,
  `scatter_payout_rate` double DEFAULT NULL,
  `win_scatter_rate` double DEFAULT NULL,
  `scatter_bonus_payout_rate` double DEFAULT NULL,
  `scatter_freespin_payout_rate` double DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `payout` bigint(20) DEFAULT NULL,
  `win_scatter_bonus_rate` double DEFAULT NULL,
  `win_scatter_freespin_rate` double DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

