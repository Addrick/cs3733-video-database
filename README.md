# cs3733-video-database
SoftEng Group Project

Names: Zach Zlotnick, Adam Santos, Jonathon Brownlow, Dakota Payette


YAML Questions:
- What is required for a Model when there is no required argument? (what is the schema for the fields? ie ListLibrary)

- How to differentiate GET vs POST (does a POST also "GET" information? so use it only when database changes need too occur?yes)

- Can we "tag:" both participant and admin in the same Model?yes


Database Schema:

CREATE SCHEMA IF NOT EXISTS `Video and Playlist DB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci

CREATE TABLE IF NOT EXISTS `Video and Playlist DB`.`playlists` (
  `id_playlist` VARCHAR(64) NOT NULL COMMENT 'Name (and ID) of playlist.',
  `order_playlist` INT NOT NULL COMMENT 'Order to play entry in the playlist.',
  PRIMARY KEY (`id_playlist`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `Video and Playlist DB`.`videos` (
  `id_video` VARCHAR(64) NOT NULL COMMENT 'Name (and ID) of video segment',
  `characters` VARCHAR(128) NOT NULL COMMENT 'List of characters in video scene',
  `transcript` VARCHAR(256) NOT NULL COMMENT 'Transcript of video contents',
  PRIMARY KEY (`id_video`))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS `Video and Playlist DB`.`remote libraries` (
  `url` VARCHAR(256) NOT NULL COMMENT 'URL storage for remote libraries.',
  PRIMARY KEY (`url`))
ENGINE = InnoDB
