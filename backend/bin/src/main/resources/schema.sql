
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafit
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafit` DEFAULT CHARACTER SET utf8;
USE `ssafit`;

-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User`;

CREATE TABLE IF NOT EXISTS `User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_tag` VARCHAR(255) NOT NULL,
  `user_nickname` VARCHAR(45) NOT NULL,
  `user_birth` DATE NULL,
  `user_profileImage` VARCHAR(200) NULL DEFAULT NULL,
  `user_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_streakColor` VARCHAR(45) NULL DEFAULT 'pink',
  `user_streakPrice` INT NULL DEFAULT 15,
  PRIMARY KEY (`user_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Routine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Routine`;

CREATE TABLE IF NOT EXISTS `Routine` (
  `routine_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `routine_name` VARCHAR(45) NOT NULL,
  `routine_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `routine_desc` TEXT NULL DEFAULT NULL,
  `routine_updateDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`routine_id`),
  CONSTRAINT `fk_Routine_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `User` (`user_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Board`;

CREATE TABLE IF NOT EXISTS `Board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `routine_id` INT NULL DEFAULT NULL,
  `board_content` TEXT NULL DEFAULT NULL,
  `board_imgUrl` VARCHAR(255) NULL DEFAULT NULL,
  `board_viewCnt` INT UNSIGNED NULL DEFAULT NULL,
  `board_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `board_updateDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `board_visibility` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`board_id`),
  CONSTRAINT `fk_Board_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `User` (`user_id`),
  CONSTRAINT `fk_Board_Routine`
    FOREIGN KEY (`routine_id`)
    REFERENCES `Routine` (`routine_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `BoardLikes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BoardLikes`;

CREATE TABLE IF NOT EXISTS `BoardLikes` (
  `boardLikes_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `board_id` INT NOT NULL,
  `boardLikes_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`boardLikes_id`),
  CONSTRAINT `fk_BoardLikes_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `User` (`user_id`),
  CONSTRAINT `fk_BoardLikes_Board`
    FOREIGN KEY (`board_id`)
    REFERENCES `Board` (`board_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Exercise`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Exercise`;

CREATE TABLE IF NOT EXISTS `Exercise` (
  `exercise_id` INT NOT NULL AUTO_INCREMENT,
  `exercise_name` VARCHAR(45) NOT NULL,
  `exercise_desc` TEXT NULL DEFAULT NULL,
  `exercise_part` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`exercise_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `RoutineComponents`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `RoutineComponents`;

CREATE TABLE IF NOT EXISTS `RoutineComponents` (
  `routineComponents_id` INT NOT NULL AUTO_INCREMENT,
  `exercise_id` INT NOT NULL,
  `routine_id` INT NOT NULL,
  `routineComponents_reps` INT NULL,
  `routineComponents_weight` INT NULL,
  PRIMARY KEY (`routineComponents_id`),
  CONSTRAINT `fk_RoutineComponents_Exercise`
    FOREIGN KEY (`exercise_id`)
    REFERENCES `Exercise` (`exercise_id`),
  CONSTRAINT `fk_RoutineComponents_Routine`
    FOREIGN KEY (`routine_id`)
    REFERENCES `Routine` (`routine_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Follow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Follow`;

CREATE TABLE IF NOT EXISTS `Follow` (
  `follow_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `following_user_id` INT NOT NULL,
  `follow_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`follow_id`),
  CONSTRAINT `fk_Follow_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `User` (`user_id`),
  CONSTRAINT `fk_Follow_FollowingUser`
    FOREIGN KEY (`following_user_id`)
    REFERENCES `User` (`user_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Point`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Point`;

CREATE TABLE IF NOT EXISTS `Point` (
  `point_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `point_amount` INT NULL,
  `point_desc` VARCHAR(200) NULL,
  `point_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`point_id`),
  CONSTRAINT `fk_Point_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `User` (`user_id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Notification`;

CREATE TABLE IF NOT EXISTS `Notification` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `notification_content` VARCHAR(200) NULL,
  `notification_regDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `notification_read` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`notification_id`),
  CONSTRAINT `fk_Notification_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `User` (`user_id`)
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
