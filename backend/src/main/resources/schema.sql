
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
  ON DELETE CASCADE
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
    REFERENCES `User` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_Board_Routine`
    FOREIGN KEY (`routine_id`)
    REFERENCES `Routine` (`routine_id`)
    ON DELETE CASCADE
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
    REFERENCES `User` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_BoardLikes_Board`
    FOREIGN KEY (`board_id`)
    REFERENCES `Board` (`board_id`)
    ON DELETE CASCADE
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
    REFERENCES `Exercise` (`exercise_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_RoutineComponents_Routine`
    FOREIGN KEY (`routine_id`)
    REFERENCES `Routine` (`routine_id`)
    ON DELETE CASCADE
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
    REFERENCES `User` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_Follow_FollowingUser`
    FOREIGN KEY (`following_user_id`)
    REFERENCES `User` (`user_id`)
    ON DELETE CASCADE
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
    ON DELETE CASCADE
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
    ON DELETE CASCADE
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO User (user_tag, user_nickname, user_birth, user_profileImage, user_regDate, user_streakColor, user_streakPrice)
VALUES
("user001", "테스트1", "1997-05-15", "null", NOW(), "BLACK", 0),
("user002", "테스트2", "1999-04-13", "null", NOW(), "PINK", 0),
("user003", "테스트3", "1992-01-22", "null", NOW(), "GREEN", 0),
("user004", "테스트4", "1991-02-20", "null", NOW(), "BLUE", 0),
("user005", "테스트5", "1993-03-31", "null", NOW(), "BLACK", 0),
("user006", "테스트6", "1999-04-10", "null", NOW(), "PINK", 0),
("user007", "테스트7", "1988-07-07", "null", NOW(), "GREEN", 0),
("user008", "테스트8", "1990-08-15", "null", NOW(), "BLUE", 0),
("user009", "테스트9", "1998-12-25", "null", NOW(), "BLACK", 0),
("user010", "테스트10", "1995-11-17", "null", NOW(), "PINK", 0),
("user011", "김철수", '1990-11-01', 'profile11.jpg', NOW(), 'BLACK', 11),
("user012", "이영희", '1992-12-12', 'profile12.jpg', NOW(), 'PINK', 12),
("user013", "박민수", '1994-03-23', 'profile13.jpg', NOW(), 'BLUE', 13),
("user014", "최유리", '1996-04-14', 'profile14.jpg', NOW(), 'GREEN', 14),
("user015", "정석원", '1998-05-25', 'profile15.jpg', NOW(), 'BLACK', 15),
("user016", "한예슬", '2000-06-06', 'profile16.jpg', NOW(), 'PINK', 16),
("user017", "장기훈", '1988-07-17', 'profile17.jpg', NOW(), 'BLUE', 17),
("user018", "서지우", '1986-08-18', 'profile18.jpg', NOW(), 'GREEN', 18),
("user019", "권민지", '1984-09-19', 'profile19.jpg', NOW(), 'BLACK', 19),
("user020", "윤하나", '1982-10-20', 'profile20.jpg', NOW(), 'PINK', 20),
("user021", "김현우", '1991-01-21', 'profile21.jpg', NOW(), 'BLACK', 21),
("user022", "이수빈", '1993-02-22', 'profile22.jpg', NOW(), 'PINK', 22),
("user023", "박지훈", '1995-03-23', 'profile23.jpg', NOW(), 'BLUE', 23),
("user024", "최민호", '1997-04-24', 'profile24.jpg', NOW(), 'GREEN', 24),
("user025", "정다은", '1999-05-25', 'profile25.jpg', NOW(), 'BLACK', 25),
("user026", "한서준", '2001-06-26', 'profile26.jpg', NOW(), 'PINK', 26),
("user027", "장유진", '1989-07-27', 'profile27.jpg', NOW(), 'BLUE', 27),
("user028", "서준영", '1987-08-28', 'profile28.jpg', NOW(), 'GREEN', 28),
("user029", "권은빈", '1985-09-29', 'profile29.jpg', NOW(), 'BLACK', 0),
("user030", "윤채영", '1983-10-30', 'profile30.jpg', NOW(), 'PINK', 1),
("user031", "김도윤", '1990-11-01', 'profile31.jpg', NOW(), 'BLACK', 2),
("user032", "이정현", '1992-12-12', 'profile32.jpg', NOW(), 'PINK', 3),
("user033", "박민재", '1994-03-23', 'profile33.jpg', NOW(), 'BLUE', 4),
("user034", "최영수", '1996-04-14', 'profile34.jpg', NOW(), 'GREEN', 5),
("user035", "정성훈", '1998-05-25', 'profile35.jpg', NOW(), 'BLACK', 6),
("user036", "한지민", '2000-06-06', 'profile36.jpg', NOW(), 'PINK', 7),
("user037", "장민서", '1988-07-17', 'profile37.jpg', NOW(), 'BLUE', 8),
("user038", "서은지", '1986-08-18', 'profile38.jpg', NOW(), 'GREEN', 9),
("user039", "권하나", '1984-09-19', 'profile39.jpg', NOW(), 'BLACK', 10),
("user040", "윤재영", '1982-10-20', 'profile40.jpg', NOW(), 'PINK', 11);

INSERT INTO Follow (user_id, following_user_id, follow_regDate)
VALUES
(1, 2, NOW()),
(1, 3, NOW()),
(2, 4, NOW()),
(2, 5, NOW()),
(3, 1, NOW()),
(4, 3, NOW()),
(5, 2, NOW()),
(6, 7, NOW()),
(8, 9, NOW()),
(9, 10, NOW()),
(11, 12, NOW()),
(11, 13, NOW()),
(12, 14, NOW()),
(12, 15, NOW()),
(13, 11, NOW()),
(14, 13, NOW()),
(15, 12, NOW()),
(16, 17, NOW()),
(18, 19, NOW()),
(19, 20, NOW()),
(21, 22, NOW()),
(21, 23, NOW()),
(22, 24, NOW()),
(22, 25, NOW()),
(23, 21, NOW()),
(24, 23, NOW()),
(25, 22, NOW()),
(26, 27, NOW()),
(28, 29, NOW()),
(29, 30, NOW()),
(31, 32, NOW()),
(31, 33, NOW()),
(32, 34, NOW()),
(32, 35, NOW()),
(33, 31, NOW()),
(34, 33, NOW()),
(35, 32, NOW()),
(36, 37, NOW()),
(38, 39, NOW()),
(39, 40, NOW());
INSERT INTO Notification (user_id, notification_content, notification_regDate, notification_read)
VALUES
(1, "New follower: Bob", NOW(), 0),
(2, "New follower: Alice", NOW(), 0),
(3, "Your post got a like!", NOW(), 1),
(4, "New message from Diana", NOW(), 0),
(5, "Your workout streak is impressive!", NOW(), 1),
(6, "New follower: George", NOW(), 0),
(7, "New like on your comment", NOW(), 1),
(8, "Hannah mentioned you in a post", NOW(), 0),
(9, "New comment on your post", NOW(), 1),
(10, "Jane liked your post", NOW(), 0),
(11, "김철수님이 팔로우합니다.", NOW(), 0),
(12, "이영희님이 댓글을 달았습니다.", NOW(), 0),
(13, "박민수님이 좋아요를 눌렀습니다.", NOW(), 1),
(14, "최유리님이 메시지를 보냈습니다.", NOW(), 0),
(15, "정석원님의 새로운 게시물!", NOW(), 1),
(16, "한예슬님이 팔로우합니다.", NOW(), 0),
(17, "장기훈님이 댓글을 달았습니다.", NOW(), 1),
(18, "서지우님이 언급했습니다.", NOW(), 0),
(19, "권민지님의 새로운 댓글", NOW(), 1),
(20, "윤하나님이 좋아요를 눌렀습니다.", NOW(), 0),
(21, "김현우님이 팔로우합니다.", NOW(), 0),
(22, "이수빈님이 댓글을 달았습니다.", NOW(), 0),
(23, "박지훈님이 좋아요를 눌렀습니다.", NOW(), 1),
(24, "최민호님이 메시지를 보냈습니다.", NOW(), 0),
(25, "정다은님의 새로운 게시물!", NOW(), 1),
(26, "한서준님이 팔로우합니다.", NOW(), 0),
(27, "장유진님이 댓글을 달았습니다.", NOW(), 1),
(28, "서준영님이 언급했습니다.", NOW(), 0),
(29, "권은빈님의 새로운 댓글", NOW(), 1),
(30, "윤채영님이 좋아요를 눌렀습니다.", NOW(), 0),
(31, "김도윤님이 팔로우합니다.", NOW(), 0),
(32, "이정현님이 댓글을 달았습니다.", NOW(), 0),
(33, "박민재님이 좋아요를 눌렀습니다.", NOW(), 1),
(34, "최영수님이 메시지를 보냈습니다.", NOW(), 0),
(35, "정성훈님의 새로운 게시물!", NOW(), 1),
(36, "한지민님이 팔로우합니다.", NOW(), 0),
(37, "장민서님이 댓글을 달았습니다.", NOW(), 1),
(38, "서은지님이 언급했습니다.", NOW(), 0),
(39, "권하나님의 새로운 댓글", NOW(), 1),
(40, "윤재영님이 좋아요를 눌렀습니다.", NOW(), 0);
INSERT INTO Point (user_id, point_amount, point_desc, point_regDate)
VALUES
(1, 100, "Initial signup bonus", NOW()),
(2, 200, "Referred a friend", NOW()),
(3, 150, "Completed a challenge", NOW()),
(4, 250, "Weekly activity bonus", NOW()),
(5, 300, "Top performer of the month", NOW()),
(6, 120, "Daily login bonus", NOW()),
(7, 180, "Shared a workout routine", NOW()),
(8, 220, "First post", NOW()),
(9, 170, "Commented on a post", NOW()),
(10, 210, "Liked a post", NOW()),
(11, 130, "첫 게시물 작성", NOW()),
(12, 140, "친구 초대 성공", NOW()),
(13, 160, "챌린지 완료", NOW()),
(14, 190, "주간 활동 보너스", NOW()),
(15, 200, "이달의 최우수 사용자", NOW()),
(16, 110, "일일 로그인 보너스", NOW()),
(17, 175, "운동 루틴 공유", NOW()),
(18, 215, "첫 댓글 작성", NOW()),
(19, 165, "댓글 작성", NOW()),
(20, 205, "좋아요 누르기", NOW()),
(21, 135, "초기 회원가입 보너스", NOW()),
(22, 145, "친구 추천 성공", NOW()),
(23, 155, "챌린지 완료", NOW()),
(24, 195, "주간 활동 보너스", NOW()),
(25, 210, "이달의 최고 활동자", NOW()),
(26, 125, "로그인 보너스", NOW()),
(27, 185, "운동 루틴 작성", NOW()),
(28, 225, "첫 게시물 작성", NOW()),
(29, 175, "댓글 작성", NOW()),
(30, 215, "좋아요 누르기", NOW()),
(31, 130, "초기 회원가입 보너스", NOW()),
(32, 140, "친구 초대 성공", NOW()),
(33, 160, "챌린지 완료", NOW()),
(34, 190, "주간 활동 보너스", NOW()),
(35, 200, "이달의 최우수 사용자", NOW()),
(36, 110, "일일 로그인 보너스", NOW()),
(37, 175, "운동 루틴 공유", NOW()),
(38, 215, "첫 댓글 작성", NOW()),
(39, 165, "댓글 작성", NOW()),
(40, 205, "좋아요 누르기", NOW());
INSERT INTO EXERCISE(exercise_name, exercise_desc,exercise_part)
VALUES
("팔굽혀펴기", NULL, "가슴"),
("스쿼트", NULL, "하체"),
("플랭크", NULL, "코어"),
("턱걸이", NULL, "등"),
("덤벨 컬", NULL, "팔"),
("벤치 프레스", NULL, "가슴"),
("데드리프트", NULL, "하체"),
("레그 레이즈", NULL, "하체"),
("사이드 플랭크", NULL, "코어"),
("런지", NULL, "하체"),
("버피", NULL, "전신"),
("마운틴 클라이머", NULL, "코어"),
("바벨 로우", NULL, "등"),
("덤벨 플라이", NULL, "가슴"),
("숄더 프레스", NULL, "어깨"),
("프론트 레이즈", NULL, "어깨"),
("사이드 레터럴 레이즈", NULL, "어깨"),
("트라이셉스 익스텐션", NULL, "팔"),
("바이셉스 컬", NULL, "팔"),
("딥스", NULL, "가슴"),
("레그 프레스", NULL, "하체"),
("햄스트링 컬", NULL, "하체"),
("카프 레이즈", NULL, "하체"),
("윗몸 일으키기", NULL, "코어"),
("레그 킥백", NULL, "하체"),
("바벨 스쿼트", NULL, "하체"),
("마니안 데드리프트", NULL, "하체"),
("케틀벨 스윙", NULL, "전신"),
("치닝", NULL, "등"),
("와이드 그립 풀업", NULL, "등"),
("클로즈 그립 푸시업", NULL, "가슴"),
("인클라인 벤치 프레스", NULL, "가슴"),
("디클라인 벤치 프레스", NULL, "가슴"),
("펙 덱 플라이", NULL, "가슴"),
("라잉 트라이셉스 익스텐션", NULL, "팔"),
("해머 컬", NULL, "팔"),
("리버스 컬", NULL, "팔"),
("얼터네이트 덤벨 컬", NULL, "팔"),
("컨센트레이션 컬", NULL, "팔"),
("데드 버그", NULL, "코어"),
("러시안 트위스트", NULL, "코어"),
("바이시클 크런치", NULL, "코어"),
("잭나이프", NULL, "코어"),
("크런치", NULL, "코어"),
("아비 롤아웃", NULL, "코어"),
("스위스 볼 크런치", NULL, "코어"),
("토 터치", NULL, "코어"),
("힐 터치", NULL, "코어"),
("리버스 크런치", NULL, "코어"),
("힙 쓰러스트", NULL, "하체"),
("카프 레이즈 머신", NULL, "하체"),
("시티드 레그 컬", NULL, "하체"),
("스탠딩 레그 컬", NULL, "하체"),
("글루트 브리지", NULL, "하체"),
("사이드 스텝 업", NULL, "하체"),
("불가리안 스플릿 스쿼트", NULL, "하체"),
("핵 스쿼트", NULL, "하체"),
("스텝 업", NULL, "하체"),
("라잉 레그 프레스", NULL, "하체"),
("클로즈 그립 벤치 프레스", NULL, "팔"),
("인클라인 덤벨 프레스", NULL, "가슴"),
("디클라인 덤벨 프레스", NULL, "가슴"),
("덤벨 풀오버", NULL, "가슴"),
("스미스 머신 벤치 프레스", NULL, "가슴"),
("리버스 플라이", NULL, "등"),
("와이드 그립 로우", NULL, "등"),
("덤벨 로우", NULL, "등"),
("페이스 풀", NULL, "어깨"),
("케이블 로우", NULL, "등"),
("원 암 덤벨 로우", NULL, "등"),
("티바 로우", NULL, "등"),
("벤트오버 로우", NULL, "등"),
("펙 덱 리버스 플라이", NULL, "어깨"),
("라잉 트라이셉스 프레스", NULL, "팔"),
("오버헤드 트라이셉스 익스텐션", NULL, "팔"),
("트라이셉스 킥백", NULL, "팔"),
("케이블 트라이셉스 푸시다운", NULL, "팔"),
("케이블 바이셉스 컬", NULL, "팔"),
("얼터네이트 케이블 컬", NULL, "팔"),
("리버스 케이블 푸시다운", NULL, "팔"),
("크로스바디 케이블 트라이셉스 익스텐션", NULL, "팔"),
("바벨 오버헤드 프레스", NULL, "어깨"),
("아놀드 프레스", NULL, "어깨"),
("덤벨 오버헤드 프레스", NULL, "어깨"),
("케이블 프론트 레이즈", NULL, "어깨"),
("케이블 사이드 레터럴 레이즈", NULL, "어깨"),
("케이블 리어 델트 플라이", NULL, "어깨"),
("머신 숄더 프레스", NULL, "어깨"),
("프론트 스쿼트", NULL, "하체"),
("슈퍼맨", NULL, "코어"),
("로테이팅 플랭크", NULL, "코어");

INSERT INTO Routine (user_id, routine_name, routine_regDate, routine_desc, routine_updateDate)
VALUES
(1, "Full Body Workout", NOW(), "A comprehensive full body workout routine", NOW()),
(2, "Cardio Blast", NOW(), "High-intensity cardio exercises", NOW()),
(3, "Strength Training", NOW(), "Build muscle strength and endurance", NOW()),
(4, "Flexibility Routine", NOW(), "Improve your flexibility with these exercises", NOW()),
(5, "Core Strength", NOW(), "Strengthen your core muscles", NOW()),
(6, "Upper Body Workout", NOW(), "Focus on upper body muscles", NOW()),
(7, "Lower Body Workout", NOW(), "Strengthen your legs and glutes", NOW()),
(8, "Quick HIIT", NOW(), "High-Intensity Interval Training for quick workouts", NOW()),
(9, "Yoga Flow", NOW(), "A relaxing yoga routine", NOW()),
(10, "Powerlifting Routine", NOW(), "Exercises for powerlifting", NOW()),
(11, "전신 운동", NOW(), "포괄적인 전신 운동 루틴", NOW()),
(12, "심장 강화 운동", NOW(), "고강도 심장 강화 운동", NOW()),
(13, "근력 훈련", NOW(), "근력을 기르고 지구력을 향상시키는 운동", NOW()),
(14, "유연성 운동", NOW(), "유연성을 향상시키는 운동", NOW()),
(15, "코어 강화", NOW(), "코어 근육을 강화하는 운동", NOW()),
(16, "상체 운동", NOW(), "상체 근육을 집중적으로 운동", NOW()),
(17, "하체 운동", NOW(), "다리와 엉덩이를 강화하는 운동", NOW()),
(18, "빠른 HIIT", NOW(), "빠른 고강도 인터벌 트레이닝", NOW()),
(19, "요가 플로우", NOW(), "편안한 요가 루틴", NOW()),
(20, "파워리프팅 루틴", NOW(), "파워리프팅 운동", NOW()),
(21, "Full Body Workout", NOW(), "A comprehensive full body workout routine", NOW()),
(22, "Cardio Blast", NOW(), "High-intensity cardio exercises", NOW()),
(23, "Strength Training", NOW(), "Build muscle strength and endurance", NOW()),
(24, "Flexibility Routine", NOW(), "Improve your flexibility with these exercises", NOW()),
(25, "Core Strength", NOW(), "Strengthen your core muscles", NOW()),
(26, "Upper Body Workout", NOW(), "Focus on upper body muscles", NOW()),
(27, "Lower Body Workout", NOW(), "Strengthen your legs and glutes", NOW()),
(28, "Quick HIIT", NOW(), "High-Intensity Interval Training for quick workouts", NOW()),
(29, "Yoga Flow", NOW(), "A relaxing yoga routine", NOW()),
(30, "Powerlifting Routine", NOW(), "Exercises for powerlifting", NOW()),
(31, "전신 운동", NOW(), "포괄적인 전신 운동 루틴", NOW()),
(32, "심장 강화 운동", NOW(), "고강도 심장 강화 운동", NOW()),
(33, "근력 훈련", NOW(), "근력을 기르고 지구력을 향상시키는 운동", NOW()),
(34, "유연성 운동", NOW(), "유연성을 향상시키는 운동", NOW()),
(35, "코어 강화", NOW(), "코어 근육을 강화하는 운동", NOW()),
(36, "상체 운동", NOW(), "상체 근육을 집중적으로 운동", NOW()),
(37, "하체 운동", NOW(), "다리와 엉덩이를 강화하는 운동", NOW()),
(38, "빠른 HIIT", NOW(), "빠른 고강도 인터벌 트레이닝", NOW()),
(39, "요가 플로우", NOW(), "편안한 요가 루틴", NOW()),
(40, "파워리프팅 루틴", NOW(), "파워리프팅 운동", NOW());
INSERT INTO RoutineComponents (routine_id, exercise_id, routineComponents_reps, routineComponents_weight)
VALUES
(1, 1, 10, 0),
(1, 2, 15, 0),
(1, 3, 20, 0),
(2, 4, 10, 0),
(2, 5, 12, 0),
(2, 6, 15, 0),
(3, 7, 8, 50),
(3, 8, 10, 60),
(3, 9, 12, 70),
(4, 10, 15, 0),
(4, 11, 20, 0),
(4, 12, 25, 0),
(5, 13, 10, 0),
(5, 14, 12, 0),
(5, 15, 15, 0),
(6, 16, 8, 30),
(6, 17, 10, 40),
(6, 18, 12, 50),
(7, 19, 15, 0),
(7, 20, 20, 0),
(7, 21, 25, 0),
(8, 22, 10, 0),
(8, 23, 12, 0),
(8, 24, 15, 0),
(9, 25, 8, 20),
(9, 26, 10, 30),
(9, 27, 12, 40),
(10, 28, 15, 0),
(10, 29, 20, 0),
(10, 30, 25, 0),
(11, 31, 10, 0),
(11, 32, 12, 0),
(11, 33, 15, 0),
(12, 34, 8, 20),
(12, 35, 10, 30),
(12, 36, 12, 40),
(13, 37, 15, 0),
(13, 38, 20, 0),
(13, 39, 25, 0),
(14, 40, 10, 0),
(14, 1, 12, 0);
INSERT INTO Board (user_id, routine_id, board_content, board_imgUrl, board_viewCnt, board_regDate, board_updateDate, board_visibility)
VALUES
(1, 1, "Check out my new workout routine!", 'src/assets/uploads/pic1.jpg', 100, NOW(), NOW(), 1),
(2, 2, "Just finished a tough session", 'src/assets/uploads/pic2.jpg', 150, NOW(), NOW(), 1),
(3, 3, "Loving the new exercises", 'src/assets/uploads/pic3.png', 200, NOW(), NOW(), 1),
(4, 4, "Feeling great after this workout", 'src/assets/uploads/pic4.jpg', 250, NOW(), NOW(), 1),
(5, 5, "Here's my progress", 'src/assets/uploads/pic5.jpg', 300, NOW(), NOW(), 1),
(6, 6, "My favorite exercises", 'src/assets/uploads/pic6.jpg', 120, NOW(), NOW(), 1),
(7, 7, "Workout tips", 'src/assets/uploads/pic7.jpg', 180, NOW(), NOW(), 1),
(8, 8, "Healthy lifestyle", 'src/assets/uploads/pic8.jpg', 220, NOW(), NOW(), 1),
(9, 9, "Gym day!", 'src/assets/uploads/pic9.jpg', 170, NOW(), NOW(), 1),
(10, 10, "Outdoor workout", 'src/assets/uploads/pic10.jpg', 210, NOW(), NOW(), 1),
(11, 1, "오늘의 운동 루틴을 공유합니다.", 'src/assets/uploads/pic11.jpg', 130, NOW(), NOW(), 1),
(12, 2, "힘든 세션을 마쳤습니다.", 'src/assets/uploads/pic12.jpg', 140, NOW(), NOW(), 1),
(13, 3, "새로운 운동을 사랑합니다.", 'src/assets/uploads/pic13.jpg', 150, NOW(), NOW(), 1),
(14, 4, "이 운동 후 기분이 좋습니다.", 'workout14.jpg', 160, NOW(), NOW(), 1),
(15, 5, "여기 내 진전이 있습니다.", 'progress15.jpg', 170, NOW(), NOW(), 1),
(16, 6, "내가 좋아하는 운동들", 'favorites16.jpg', 180, NOW(), NOW(), 1),
(17, 7, "운동 팁", 'tips17.jpg', 190, NOW(), NOW(), 1),
(18, 8, "건강한 생활 방식", 'lifestyle18.jpg', 200, NOW(), NOW(), 1),
(19, 9, "체육관 날!", 'gym19.jpg', 210, NOW(), NOW(), 1),
(20, 10, "야외 운동", 'outdoor20.jpg', 220, NOW(), NOW(), 1),
(21, 1, "새로운 운동 루틴을 확인하세요!", 'workout21.jpg', 230, NOW(), NOW(), 1),
(22, 2, "힘든 세션을 막 끝냈습니다.", 'session22.jpg', 240, NOW(), NOW(), 1),
(23, 3, "새로운 운동을 즐기고 있습니다", 'exercise23.jpg', 250, NOW(), NOW(), 1),
(24, 4, "이 운동 후 기분이 정말 좋습니다", 'workout24.jpg', 260, NOW(), NOW(), 1),
(25, 5, "내 진전을 여기에서 확인하세요", 'progress25.jpg', 270, NOW(), NOW(), 1),
(26, 6, "제가 좋아하는 운동들입니다", 'favorites26.jpg', 280, NOW(), NOW(), 1),
(27, 7, "운동 팁을 공유합니다", 'tips27.jpg', 290, NOW(), NOW(), 1),
(28, 8, "건강한 생활 방식을 유지하세요", 'lifestyle28.jpg', 300, NOW(), NOW(), 1),
(29, 9, "체육관에서의 하루!", 'gym29.jpg', 310, NOW(), NOW(), 1),
(30, 10, "야외에서의 운동!", 'outdoor30.jpg', 320, NOW(), NOW(), 1),
(31, 1, "Check out my new workout routine!", 'workout31.jpg', 100, NOW(), NOW(), 1),
(32, 2, "Just finished a tough session", 'session32.jpg', 150, NOW(), NOW(), 1),
(33, 3, "Loving the new exercises", 'exercise33.jpg', 200, NOW(), NOW(), 1),
(34, 4, "Feeling great after this workout", 'workout34.jpg', 250, NOW(), NOW(), 1),
(35, 5, "Here's my progress", 'progress35.jpg', 300, NOW(), NOW(), 1),
(36, 6, "My favorite exercises", 'favorites36.jpg', 120, NOW(), NOW(), 1),
(37, 7, "Workout tips", 'tips37.jpg', 180, NOW(), NOW(), 1),
(38, 8, "Healthy lifestyle", 'lifestyle38.jpg', 220, NOW(), NOW(), 1),
(39, 9, "Gym day!", 'gym39.jpg', 170, NOW(), NOW(), 1),
(40, 10, "Outdoor workout", 'outdoor40.jpg', 210, NOW(), NOW(), 1);
INSERT INTO BoardLikes (user_id, board_id, boardlikes_regDate)
VALUES
(1, 1, NOW()),
(2, 1, NOW()),
(3, 2, NOW()),
(4, 2, NOW()),
(5, 3, NOW()),
(6, 3, NOW()),
(7, 4, NOW()),
(8, 4, NOW()),
(9, 5, NOW()),
(10, 5, NOW()),
(11, 6, NOW()),
(12, 6, NOW()),
(13, 7, NOW()),
(14, 7, NOW()),
(15, 8, NOW()),
(16, 8, NOW()),
(17, 9, NOW()),
(18, 9, NOW()),
(19, 10, NOW()),
(20, 10, NOW()),
(21, 11, NOW()),
(22, 11, NOW()),
(23, 12, NOW()),
(24, 12, NOW()),
(25, 13, NOW()),
(26, 13, NOW()),
(27, 14, NOW()),
(28, 14, NOW()),
(29, 15, NOW()),
(30, 15, NOW()),
(31, 16, NOW()),
(32, 16, NOW()),
(33, 17, NOW()),
(34, 17, NOW()),
(35, 18, NOW()),
(36, 18, NOW()),
(37, 19, NOW()),
(38, 19, NOW()),
(39, 20, NOW()),
(40, 20, NOW());
