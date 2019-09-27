CREATE SCHEMA `newdatabase` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `newdatabase`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `price` DECIMAL(6,4) NOT NULL,
  PRIMARY KEY (`item_id`));
