CREATE TABLE user
(
	`user_id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(200),
	`first_name` VARCHAR(200),
    `last_name` VARCHAR(200),
    `email_id` VARCHAR(200),
    `phone_number`  VARCHAR(20),
    `address` VARCHAR(300),
    `password` VARCHAR(200),
    `premium` VARCHAR(10),
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Primary Key (user_id)
);

ALTER TABLE user AUTO_INCREMENT = 1;

CREATE TABLE receipt
(
	`receipt_id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `store_name` VARCHAR(200),
    `phone_number` VARCHAR(20),
    `address` VARCHAR(300),
    `subtotal` INT NOT NULL,
    `receipt_name` VARCHAR(200),
    `tax` INT NOT NULL,
    `completetotal` DOUBLE NOT NULL,
	`category` VARCHAR(100),
	`updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Primary Key (receipt_id),
    Foreign Key (user_id) references `user`(user_id)
);

ALTER TABLE receipt AUTO_INCREMENT = 10;

CREATE TABLE receipt_items
(	
	`receipt_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `item_name` VARCHAR(200),
    `item_id` INT NOT NULL AUTO_INCREMENT,
    `item_price` INT NOT NULL,
	`updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    Primary Key (item_id),
    Foreign Key (receipt_id) references receipt(receipt_id)
);

ALTER TABLE receipt_items AUTO_INCREMENT = 10;

