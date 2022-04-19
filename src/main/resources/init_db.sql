CREATE TABLE `library_db`.`manufacturers` (
                                              `id` INT NOT NULL,
                                              `country` VARCHAR(255) NOT NULL,
                                              `name` VARCHAR(255) NOT NULL,
                                              `is_deleted` TINYINT NOT NULL,
                                              PRIMARY KEY (`id`));