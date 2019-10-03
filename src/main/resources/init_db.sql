CREATE SCHEMA `newdatabase` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `newdatabase`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `price` DECIMAL(6,4) NOT NULL,
  PRIMARY KEY (`item_id`));

CREATE TABLE `newdatabase`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `token` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `newdatabase`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `token` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `newdatabase`.`buckets` (
  `bucket_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`bucket_id`),
  INDEX `bucket_user_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `bucket_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `newdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `newdatabase`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `orsers_user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `orsers_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `newdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `newdatabase`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `newdatabase`.`users_buckets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `bucket_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `newdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `newdatabase`.`users_buckets`
ADD INDEX `bucket_fk_idx` (`bucket_id` ASC) VISIBLE;
;
ALTER TABLE `newdatabase`.`users_buckets`
ADD CONSTRAINT `bucket_fk`
  FOREIGN KEY (`bucket_id`)
  REFERENCES `newdatabase`.`buckets` (`bucket_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `newdatabase`.`users_orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `newdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `newdatabase`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `newdatabase`.`users_roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `users_roles_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `roles_users_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `users_roles_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `newdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `roles_users_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `newdatabase`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `newdatabase`.`items_buckets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `item_id` INT NOT NULL,
  `bucket_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `items_buckets_idx` (`item_id` ASC) VISIBLE,
  INDEX `bucket_items_fk_idx` (`bucket_id` ASC) VISIBLE,
  CONSTRAINT `items_buckets_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `newdatabase`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `bucket_items_fk`
    FOREIGN KEY (`bucket_id`)
    REFERENCES `newdatabase`.`buckets` (`bucket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `newdatabase`.`items_orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `item_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `items_orders_fk_idx` (`item_id` ASC) VISIBLE,
  INDEX `orders_items_fk_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `items_orders_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `newdatabase`.`items` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_items_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `newdatabase`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
