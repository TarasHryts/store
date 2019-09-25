CREATE SCHEMA `newdatabase` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `newdatabase`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `price` DECIMAL(6,4) NOT NULL,
  PRIMARY KEY (`item_id`));

ALTER TABLE `newdatabase`.`items`
CHANGE COLUMN `price` `price` DECIMAL(10,4) NOT NULL ;

INSERT INTO `newdatabase`.`items` (`name`, `price`) VALUES ('mersedes', '10');

SELECT * FROM newdatabase.items where item_id=1;

SELECT * FROM newdatabase.items;

DELETE FROM newdatabase.items WHERE item_id=2;

UPDATE newdatabase.items SET item_id=300 WHERE item_id=3;

UPDATE newdatabase.items SET item_id=302, name='newcar', price=999.1 WHERE item_id=4;
