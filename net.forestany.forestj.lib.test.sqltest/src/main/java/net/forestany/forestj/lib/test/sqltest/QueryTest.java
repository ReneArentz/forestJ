package net.forestany.forestj.lib.test.sqltest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test query instances
 */
public class QueryTest {
	/**
	 * empty constructor
	 */
	public QueryTest() {
		
	}
	
	/**
	 * method to test query instances
	 */
	public static void testQuery() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			java.util.Map<String, Integer> a_baseGateways = new java.util.HashMap<String, Integer>();
			a_baseGateways.put(net.forestany.forestj.lib.sqlcore.BaseGateway.MARIADB.toString(), 0);
			a_baseGateways.put(net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE.toString(), 1);
			a_baseGateways.put(net.forestany.forestj.lib.sqlcore.BaseGateway.MSSQL.toString(), 2);
			a_baseGateways.put(net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL.toString(), 3);
			a_baseGateways.put(net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE.toString(), 4);
			a_baseGateways.put(net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB.toString(), 5);
			
			String[][] a_expectedQueries = {
				/* MARIADB */ {
					"CREATE TABLE `sys_forestj_testddl` (`Id` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, `UUID` VARCHAR(36) NOT NULL UNIQUE, `ShortText` VARCHAR(255) NULL, `Text` TEXT NULL, `SmallInt` SMALLINT(6) NULL, `Int` INT(10) NULL, `BigInt` BIGINT(20) NULL, `DateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, `Date` TIMESTAMP NOT NULL DEFAULT '2020-04-06 08:10:12', `Time` TIME NULL, `LocalDateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, `LocalDate` TIMESTAMP NULL, `LocalTime` TIME DEFAULT '12:24:46', `DoubleCol` DOUBLE NULL, `Decimal` DECIMAL(38,9) NULL, `Bool` BIT(1) NULL)",
					"CREATE TABLE `sys_forestj_testddl2` (`Id` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, `DoubleCol` DOUBLE NULL)",
					"ALTER TABLE `sys_forestj_testddl` ADD `Text2` VARCHAR(36) NULL, ADD `ShortText2` VARCHAR(255) NULL",
					"ALTER TABLE `sys_forestj_testddl` ADD UNIQUE `new_index_Int` (`Int`)",
					"ALTER TABLE `sys_forestj_testddl` ADD UNIQUE `new_index_SmallInt_Bool` (`SmallInt`, `Bool`), DROP INDEX `new_index_Int`",
					"ALTER TABLE `sys_forestj_testddl` ADD INDEX `new_index_Text2` (`Text2`)",
					"ALTER TABLE `sys_forestj_testddl` DROP INDEX `new_index_Text2`",
					"ALTER TABLE `sys_forestj_testddl` CHANGE `Text2` `Text2Changed` VARCHAR(255) NOT NULL DEFAULT 'Das ist das Haus vom Nikolaus'",
					"INSERT INTO `sys_forestj_testddl` (`sys_forestj_testddl`.`UUID`, `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`Text`, `sys_forestj_testddl`.`SmallInt`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl`.`BigInt`, `sys_forestj_testddl`.`DateTime`, `sys_forestj_testddl`.`Date`, `sys_forestj_testddl`.`Time`, `sys_forestj_testddl`.`LocalDateTime`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`LocalTime`, `sys_forestj_testddl`.`DoubleCol`, `sys_forestj_testddl`.`Decimal`, `sys_forestj_testddl`.`Bool`, `sys_forestj_testddl`.`Text2Changed`, `sys_forestj_testddl`.`ShortText2`) VALUES ('123e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 123, 1234567890, 1234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, true, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO `sys_forestj_testddl` (`sys_forestj_testddl`.`UUID`, `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`Text`, `sys_forestj_testddl`.`SmallInt`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl`.`BigInt`, `sys_forestj_testddl`.`DateTime`, `sys_forestj_testddl`.`Date`, `sys_forestj_testddl`.`Time`, `sys_forestj_testddl`.`LocalDateTime`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`LocalTime`, `sys_forestj_testddl`.`DoubleCol`, `sys_forestj_testddl`.`Decimal`, `sys_forestj_testddl`.`Bool`, `sys_forestj_testddl`.`Text2Changed`, `sys_forestj_testddl`.`ShortText2`) VALUES ('223e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 223, 1234567890, 2234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, false, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO `sys_forestj_testddl` (`sys_forestj_testddl`.`UUID`, `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`Text`, `sys_forestj_testddl`.`SmallInt`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl`.`BigInt`, `sys_forestj_testddl`.`DateTime`, `sys_forestj_testddl`.`Date`, `sys_forestj_testddl`.`Time`, `sys_forestj_testddl`.`LocalDateTime`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`LocalTime`, `sys_forestj_testddl`.`DoubleCol`, `sys_forestj_testddl`.`Decimal`, `sys_forestj_testddl`.`Bool`, `sys_forestj_testddl`.`Text2Changed`, `sys_forestj_testddl`.`ShortText2`) VALUES ('323e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 323, 1234567890, 3234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, true, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"SELECT `sys_forestj_testddl`.`ShortText`, MIN(`sys_forestj_testddl`.`SmallInt`), `sys_forestj_testddl`.`LocalDate` AS 'Spalte C', `sys_forestj_testddl`.`Int`, `sys_forestj_testddl2`.`Id` FROM `sys_forestj_testddl` INNER JOIN `sys_forestj_testddl2` ON (`sys_forestj_testddl2`.`Id` = `sys_forestj_testddl`.`Id` AND `sys_forestj_testddl2`.`DoubleCol` <= `sys_forestj_testddl`.`DoubleCol`) WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl2`.`Id` >= 123 AND `sys_forestj_testddl`.`SmallInt` > 25353 GROUP BY `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl2`.`Id` HAVING (`sys_forestj_testddl`.`Int` <= 456.0 AND `sys_forestj_testddl`.`ShortText` = 'Trew' AND `sys_forestj_testddl`.`LocalDate` <> '2018-11-16') ORDER BY `sys_forestj_testddl2`.`Id` ASC, `sys_forestj_testddl`.`ShortText` DESC LIMIT 0, 10",
					"UPDATE `sys_forestj_testddl` SET `sys_forestj_testddl`.`ShortText` = 'Wert', `sys_forestj_testddl`.`Int` = 1337, `sys_forestj_testddl`.`DoubleCol` = 35.67, `sys_forestj_testddl`.`DateTime` = '2003-12-15 08:33:03' WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl`.`SmallInt` >= 123 AND `sys_forestj_testddl`.`DateTime` >= '2003-12-15 08:33:03'",
					"SELECT * FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`DateTime` <> '2003-12-15 08:33:03' OR `sys_forestj_testddl`.`Date` >= '2009-06-29' AND `sys_forestj_testddl`.`Time` > '11:01:43'",
					"SELECT * FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`LocalDateTime` <> '2010-09-02 05:55:13' OR `sys_forestj_testddl`.`LocalDate` >= '2018-11-16' AND `sys_forestj_testddl`.`LocalTime` > '17:42:23'",
					"SELECT * FROM `sys_forestj_testddl`",
					"DELETE FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl`.`SmallInt` >= 32.45 AND `sys_forestj_testddl`.`DateTime` > '2003-12-15 08:33:03'",
					"ALTER TABLE `sys_forestj_testddl` DROP `ShortText2`",
					"ALTER TABLE `sys_forestj_testddl` DROP `BigInt`, DROP `Int`",
					"ALTER TABLE `sys_forestj_testddl` DROP INDEX `new_index_SmallInt_Bool`",
					"TRUNCATE TABLE `sys_forestj_testddl`",
					"DROP TABLE `sys_forestj_testddl`",
					"DROP TABLE `sys_forestj_testddl2`"
				},
				/* SQLITE */ {
					"CREATE TABLE `sys_forestj_testddl` (`Id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `UUID` varchar(36) NOT NULL UNIQUE, `ShortText` varchar(255) NULL, `Text` text NULL, `SmallInt` smallint NULL, `Int` integer NULL, `BigInt` bigint NULL, `DateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `Date` datetime NOT NULL DEFAULT '2020-04-06 08:10:12', `Time` time NULL, `LocalDateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `LocalDate` datetime NULL, `LocalTime` time DEFAULT '12:24:46', `DoubleCol` double NULL, `Decimal` decimal(38,9) NULL, `Bool` bit(1) NULL)",
					"CREATE TABLE `sys_forestj_testddl2` (`Id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `DoubleCol` double NULL)",
					"ALTER TABLE `sys_forestj_testddl` ADD `Text2` varchar(36) NULL::forestjSQLQuerySeparator::ALTER TABLE `sys_forestj_testddl` ADD `ShortText2` varchar(255) NULL",
					"CREATE UNIQUE INDEX `new_index_Int` ON `sys_forestj_testddl` (`Int`)",
					"DROP INDEX `new_index_Int`::forestjSQLQuerySeparator::CREATE UNIQUE INDEX `new_index_SmallInt_Bool` ON `sys_forestj_testddl` (`SmallInt`, `Bool`)",
					"CREATE INDEX `new_index_Text2` ON `sys_forestj_testddl` (`Text2`)",
					"DROP INDEX `new_index_Text2`",
					"CREATE TABLE `REPLACE_RANDOM_sys_forestj_testddl` (`Id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `UUID` varchar(36) NOT NULL UNIQUE, `ShortText` varchar(255) NULL, `Text` text NULL, `SmallInt` smallint NULL, `Int` integer NULL, `BigInt` bigint NULL, `DateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `Date` datetime NOT NULL DEFAULT '2020-04-06 08:10:12', `Time` time NULL, `LocalDateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `LocalDate` datetime NULL, `LocalTime` time DEFAULT '12:24:46', `DoubleCol` double NULL, `Decimal` decimal(38,9) NULL, `Bool` bit(1) NULL, `Text2Changed` varchar(255) NULL DEFAULT 'Das ist das Haus vom Nikolaus', `ShortText2` varchar(255) NULL)::forestjSQLQuerySeparator::INSERT INTO `REPLACE_RANDOM_sys_forestj_testddl` (`Id`,`UUID`,`ShortText`,`Text`,`SmallInt`,`Int`,`BigInt`,`DateTime`,`Date`,`Time`,`LocalDateTime`,`LocalDate`,`LocalTime`,`DoubleCol`,`Decimal`,`Bool`,`Text2Changed`,`ShortText2`) SELECT `Id`,`UUID`,`ShortText`,`Text`,`SmallInt`,`Int`,`BigInt`,`DateTime`,`Date`,`Time`,`LocalDateTime`,`LocalDate`,`LocalTime`,`DoubleCol`,`Decimal`,`Bool`,`Text2`,`ShortText2` FROM `sys_forestj_testddl`::forestjSQLQuerySeparator::DROP TABLE `sys_forestj_testddl`::forestjSQLQuerySeparator::ALTER TABLE `REPLACE_RANDOM_sys_forestj_testddl` RENAME TO `sys_forestj_testddl`::forestjSQLQuerySeparator::",
					"INSERT INTO `sys_forestj_testddl` (`UUID`, `ShortText`, `Text`, `SmallInt`, `Int`, `BigInt`, `DateTime`, `Date`, `Time`, `LocalDateTime`, `LocalDate`, `LocalTime`, `DoubleCol`, `Decimal`, `Bool`, `Text2Changed`, `ShortText2`) VALUES ('123e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 123, 1234567890, 1234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, true, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO `sys_forestj_testddl` (`UUID`, `ShortText`, `Text`, `SmallInt`, `Int`, `BigInt`, `DateTime`, `Date`, `Time`, `LocalDateTime`, `LocalDate`, `LocalTime`, `DoubleCol`, `Decimal`, `Bool`, `Text2Changed`, `ShortText2`) VALUES ('223e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 223, 1234567890, 2234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, false, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO `sys_forestj_testddl` (`UUID`, `ShortText`, `Text`, `SmallInt`, `Int`, `BigInt`, `DateTime`, `Date`, `Time`, `LocalDateTime`, `LocalDate`, `LocalTime`, `DoubleCol`, `Decimal`, `Bool`, `Text2Changed`, `ShortText2`) VALUES ('323e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 323, 1234567890, 3234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, true, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"SELECT `sys_forestj_testddl`.`ShortText`, MIN(`sys_forestj_testddl`.`SmallInt`), `sys_forestj_testddl`.`LocalDate` AS 'Spalte C', `sys_forestj_testddl`.`Int`, `sys_forestj_testddl2`.`Id` FROM `sys_forestj_testddl` INNER JOIN `sys_forestj_testddl2` ON (`sys_forestj_testddl2`.`Id` = `sys_forestj_testddl`.`Id` AND `sys_forestj_testddl2`.`DoubleCol` <= `sys_forestj_testddl`.`DoubleCol`) WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl2`.`Id` >= 123 AND `sys_forestj_testddl`.`SmallInt` > 25353 GROUP BY `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl2`.`Id` HAVING (`sys_forestj_testddl`.`Int` <= 456.0 AND `sys_forestj_testddl`.`ShortText` = 'Trew' AND `sys_forestj_testddl`.`LocalDate` <> '2018-11-16') ORDER BY `sys_forestj_testddl2`.`Id` ASC, `sys_forestj_testddl`.`ShortText` DESC LIMIT 0, 10",
					"UPDATE `sys_forestj_testddl` SET `ShortText` = 'Wert', `Int` = 1337, `DoubleCol` = 35.67, `DateTime` = '2003-12-15 08:33:03' WHERE `ShortText` <> 'Wert' OR `SmallInt` >= 123 AND `DateTime` >= '2003-12-15 08:33:03'",
					"SELECT * FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`DateTime` <> '2003-12-15 08:33:03' OR `sys_forestj_testddl`.`Date` >= '2009-06-29' AND `sys_forestj_testddl`.`Time` > '11:01:43'",
					"SELECT * FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`LocalDateTime` <> '2010-09-02 05:55:13' OR `sys_forestj_testddl`.`LocalDate` >= '2018-11-16' AND `sys_forestj_testddl`.`LocalTime` > '17:42:23'",
					"SELECT * FROM `sys_forestj_testddl`",
					"DELETE FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl`.`SmallInt` >= 32.45 AND `sys_forestj_testddl`.`DateTime` > '2003-12-15 08:33:03'",
					"CREATE TABLE `REPLACE_RANDOM_sys_forestj_testddl` (`Id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `UUID` varchar(36) NOT NULL UNIQUE, `ShortText` varchar(255) NULL, `Text` text NULL, `SmallInt` smallint NULL, `Int` integer NULL, `BigInt` bigint NULL, `DateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `Date` datetime NOT NULL DEFAULT '2020-04-06 08:10:12', `Time` time NULL, `LocalDateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `LocalDate` datetime NULL, `LocalTime` time DEFAULT '12:24:46', `DoubleCol` double NULL, `Decimal` decimal(38,9) NULL, `Bool` bit(1) NULL, `Text2Changed` varchar(36) NULL DEFAULT 'Das ist das Haus vom Nikolaus')::forestjSQLQuerySeparator::INSERT INTO `REPLACE_RANDOM_sys_forestj_testddl` (`Id`,`UUID`,`ShortText`,`Text`,`SmallInt`,`Int`,`BigInt`,`DateTime`,`Date`,`Time`,`LocalDateTime`,`LocalDate`,`LocalTime`,`DoubleCol`,`Decimal`,`Bool`,`Text2Changed`) SELECT `Id`,`UUID`,`ShortText`,`Text`,`SmallInt`,`Int`,`BigInt`,`DateTime`,`Date`,`Time`,`LocalDateTime`,`LocalDate`,`LocalTime`,`DoubleCol`,`Decimal`,`Bool`,`Text2Changed` FROM `sys_forestj_testddl`::forestjSQLQuerySeparator::DROP TABLE `sys_forestj_testddl`::forestjSQLQuerySeparator::ALTER TABLE `REPLACE_RANDOM_sys_forestj_testddl` RENAME TO `sys_forestj_testddl`::forestjSQLQuerySeparator::CREATE UNIQUE INDEX `new_index_BigInt_Bool` ON `sys_forestj_testddl` (`BigInt`, `Bool`)::forestjSQLQuerySeparator::",
					"CREATE TABLE `REPLACE_RANDOM_sys_forestj_testddl` (`Id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `UUID` varchar(36) NOT NULL UNIQUE, `ShortText` varchar(255) NULL, `Text` text NULL, `SmallInt` smallint NULL, `DateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `Date` datetime NOT NULL DEFAULT '2020-04-06 08:10:12', `Time` time NULL, `LocalDateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP, `LocalDate` datetime NULL, `LocalTime` time DEFAULT '12:24:46', `DoubleCol` double NULL, `Decimal` decimal(38,9) NULL, `Bool` bit(1) NULL, `Text2Changed` varchar(36) NULL DEFAULT 'Das ist das Haus vom Nikolaus')::forestjSQLQuerySeparator::INSERT INTO `REPLACE_RANDOM_sys_forestj_testddl` (`Id`,`UUID`,`ShortText`,`Text`,`SmallInt`,`DateTime`,`Date`,`Time`,`LocalDateTime`,`LocalDate`,`LocalTime`,`DoubleCol`,`Decimal`,`Bool`,`Text2Changed`) SELECT `Id`,`UUID`,`ShortText`,`Text`,`SmallInt`,`DateTime`,`Date`,`Time`,`LocalDateTime`,`LocalDate`,`LocalTime`,`DoubleCol`,`Decimal`,`Bool`,`Text2Changed` FROM `sys_forestj_testddl`::forestjSQLQuerySeparator::DROP TABLE `sys_forestj_testddl`::forestjSQLQuerySeparator::ALTER TABLE `REPLACE_RANDOM_sys_forestj_testddl` RENAME TO `sys_forestj_testddl`::forestjSQLQuerySeparator::CREATE UNIQUE INDEX `new_index_SmallInt_Bool` ON `sys_forestj_testddl` (`SmallInt`, `Bool`)::forestjSQLQuerySeparator::",
					"DROP INDEX `new_index_SmallInt_Bool`",
					"DELETE FROM `sys_forestj_testddl`::forestjSQLQuerySeparator::VACUUM",
					"DROP TABLE `sys_forestj_testddl`",
					"DROP TABLE `sys_forestj_testddl2`"
				}, 
				/* MSSQL */ {
					"CREATE TABLE [sys_forestj_testddl] ([Id] int NOT NULL PRIMARY KEY IDENTITY(1,1), [UUID] nvarchar(36) NOT NULL UNIQUE, [ShortText] nvarchar(255) NULL, [Text] text NULL, [SmallInt] smallint NULL, [Int] int NULL, [BigInt] bigint NULL, [DateTime] datetime NULL DEFAULT CURRENT_TIMESTAMP, [Date] datetime NOT NULL DEFAULT '2020-04-06T08:10:12', [Time] time NULL, [LocalDateTime] datetime NULL DEFAULT CURRENT_TIMESTAMP, [LocalDate] datetime NULL, [LocalTime] time DEFAULT '12:24:46', [DoubleCol] float NULL, [Decimal] decimal(38,9) NULL, [Bool] bit NULL)",
					"CREATE TABLE [sys_forestj_testddl2] ([Id] int NOT NULL PRIMARY KEY IDENTITY(1,1), [DoubleCol] float NULL)",
					"ALTER TABLE [sys_forestj_testddl] ADD [Text2] nvarchar(36) NULL, [ShortText2] nvarchar(255) NULL",
					"CREATE UNIQUE INDEX [new_index_Int] ON [sys_forestj_testddl] ([Int])",
					"DROP INDEX [new_index_Int] ON [sys_forestj_testddl]::forestjSQLQuerySeparator::CREATE UNIQUE INDEX [new_index_SmallInt_Bool] ON [sys_forestj_testddl] ([SmallInt], [Bool])",
					"CREATE INDEX [new_index_Text2] ON [sys_forestj_testddl] ([Text2])",
					"DROP INDEX [new_index_Text2] ON [sys_forestj_testddl]",
					"EXEC sp_rename \"[sys_forestj_testddl].[Text2]\", \"Text2Changed\", \"COLUMN\"::forestjSQLQuerySeparator::ALTER TABLE [sys_forestj_testddl] ALTER COLUMN [Text2Changed] nvarchar(255) NOT NULL",
					"INSERT INTO [sys_forestj_testddl] ([sys_forestj_testddl].[UUID], [sys_forestj_testddl].[ShortText], [sys_forestj_testddl].[Text], [sys_forestj_testddl].[SmallInt], [sys_forestj_testddl].[Int], [sys_forestj_testddl].[BigInt], [sys_forestj_testddl].[DateTime], [sys_forestj_testddl].[Date], [sys_forestj_testddl].[Time], [sys_forestj_testddl].[LocalDateTime], [sys_forestj_testddl].[LocalDate], [sys_forestj_testddl].[LocalTime], [sys_forestj_testddl].[DoubleCol], [sys_forestj_testddl].[Decimal], [sys_forestj_testddl].[Bool], [sys_forestj_testddl].[Text2Changed], [sys_forestj_testddl].[ShortText2]) VALUES ('123e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 123, 1234567890, 1234567890123, '2003-12-15T08:33:03', '2009-06-29', '11:01:43', '2010-09-02T05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, 1, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO [sys_forestj_testddl] ([sys_forestj_testddl].[UUID], [sys_forestj_testddl].[ShortText], [sys_forestj_testddl].[Text], [sys_forestj_testddl].[SmallInt], [sys_forestj_testddl].[Int], [sys_forestj_testddl].[BigInt], [sys_forestj_testddl].[DateTime], [sys_forestj_testddl].[Date], [sys_forestj_testddl].[Time], [sys_forestj_testddl].[LocalDateTime], [sys_forestj_testddl].[LocalDate], [sys_forestj_testddl].[LocalTime], [sys_forestj_testddl].[DoubleCol], [sys_forestj_testddl].[Decimal], [sys_forestj_testddl].[Bool], [sys_forestj_testddl].[Text2Changed], [sys_forestj_testddl].[ShortText2]) VALUES ('223e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 223, 1234567890, 2234567890123, '2003-12-15T08:33:03', '2009-06-29', '11:01:43', '2010-09-02T05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, 0, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO [sys_forestj_testddl] ([sys_forestj_testddl].[UUID], [sys_forestj_testddl].[ShortText], [sys_forestj_testddl].[Text], [sys_forestj_testddl].[SmallInt], [sys_forestj_testddl].[Int], [sys_forestj_testddl].[BigInt], [sys_forestj_testddl].[DateTime], [sys_forestj_testddl].[Date], [sys_forestj_testddl].[Time], [sys_forestj_testddl].[LocalDateTime], [sys_forestj_testddl].[LocalDate], [sys_forestj_testddl].[LocalTime], [sys_forestj_testddl].[DoubleCol], [sys_forestj_testddl].[Decimal], [sys_forestj_testddl].[Bool], [sys_forestj_testddl].[Text2Changed], [sys_forestj_testddl].[ShortText2]) VALUES ('323e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 323, 1234567890, 3234567890123, '2003-12-15T08:33:03', '2009-06-29', '11:01:43', '2010-09-02T05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, 1, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"SELECT [sys_forestj_testddl].[ShortText], MIN([sys_forestj_testddl].[SmallInt]), [sys_forestj_testddl].[LocalDate] AS 'Spalte C', [sys_forestj_testddl].[Int], [sys_forestj_testddl2].[Id] FROM [sys_forestj_testddl] INNER JOIN [sys_forestj_testddl2] ON ([sys_forestj_testddl2].[Id] = [sys_forestj_testddl].[Id] AND [sys_forestj_testddl2].[DoubleCol] <= [sys_forestj_testddl].[DoubleCol]) WHERE [sys_forestj_testddl].[ShortText] <> 'Wert' OR [sys_forestj_testddl2].[Id] >= 123 AND [sys_forestj_testddl].[SmallInt] > 25353 GROUP BY [sys_forestj_testddl].[ShortText], [sys_forestj_testddl].[LocalDate], [sys_forestj_testddl].[Int], [sys_forestj_testddl2].[Id] HAVING ([sys_forestj_testddl].[Int] <= 456.0 AND [sys_forestj_testddl].[ShortText] = 'Trew' AND [sys_forestj_testddl].[LocalDate] <> '2018-11-16') ORDER BY [sys_forestj_testddl2].[Id] ASC, [sys_forestj_testddl].[ShortText] DESC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY",
					"UPDATE [sys_forestj_testddl] SET [sys_forestj_testddl].[ShortText] = 'Wert', [sys_forestj_testddl].[Int] = 1337, [sys_forestj_testddl].[DoubleCol] = 35.67, [sys_forestj_testddl].[DateTime] = '2003-12-15T08:33:03' WHERE [sys_forestj_testddl].[ShortText] <> 'Wert' OR [sys_forestj_testddl].[SmallInt] >= 123 AND [sys_forestj_testddl].[DateTime] >= '2003-12-15T08:33:03'",
					"SELECT * FROM [sys_forestj_testddl] WHERE [sys_forestj_testddl].[DateTime] <> '2003-12-15T08:33:03' OR [sys_forestj_testddl].[Date] >= '2009-06-29' AND [sys_forestj_testddl].[Time] > '11:01:43'",
					"SELECT * FROM [sys_forestj_testddl] WHERE [sys_forestj_testddl].[LocalDateTime] <> '2010-09-02T05:55:13' OR [sys_forestj_testddl].[LocalDate] >= '2018-11-16' AND [sys_forestj_testddl].[LocalTime] > '17:42:23'",
					"SELECT * FROM [sys_forestj_testddl]",
					"DELETE FROM [sys_forestj_testddl] WHERE [sys_forestj_testddl].[ShortText] <> 'Wert' OR [sys_forestj_testddl].[SmallInt] >= 32.45 AND [sys_forestj_testddl].[DateTime] > '2003-12-15T08:33:03'",
					"ALTER TABLE [sys_forestj_testddl] DROP COLUMN [ShortText2]",
					"ALTER TABLE [sys_forestj_testddl] DROP COLUMN [BigInt], COLUMN [Int]",
					"DROP INDEX [new_index_SmallInt_Bool] ON [sys_forestj_testddl]",
					"TRUNCATE TABLE [sys_forestj_testddl]",
					"DROP TABLE [sys_forestj_testddl]",
					"DROP TABLE [sys_forestj_testddl2]"
				},
				/* PGSQL */ {
					"CREATE TABLE \"sys_forestj_testddl\" (\"Id\" serial PRIMARY KEY, \"UUID\" varchar(36) NOT NULL UNIQUE, \"ShortText\" varchar(255) NULL, \"Text\" text NULL, \"SmallInt\" smallint NULL, \"Int\" integer NULL, \"BigInt\" bigint NULL, \"DateTime\" timestamp NULL DEFAULT CURRENT_TIMESTAMP, \"Date\" timestamp NOT NULL DEFAULT '2020-04-06 08:10:12', \"Time\" time NULL, \"LocalDateTime\" timestamp NULL DEFAULT CURRENT_TIMESTAMP, \"LocalDate\" timestamp NULL, \"LocalTime\" time DEFAULT '12:24:46', \"DoubleCol\" double precision NULL, \"Decimal\" decimal(38,9) NULL, \"Bool\" smallint DEFAULT 0 CHECK (\"Bool\" >= 0 AND \"Bool\" <= 1) NULL)",
					"CREATE TABLE \"sys_forestj_testddl2\" (\"Id\" serial PRIMARY KEY, \"DoubleCol\" double precision NULL)",
					"ALTER TABLE \"sys_forestj_testddl\" ADD \"Text2\" varchar(36) NULL, ADD \"ShortText2\" varchar(255) NULL",
					"ALTER TABLE \"sys_forestj_testddl\" ADD CONSTRAINT \"new_index_Int\" UNIQUE (\"Int\")",
					"ALTER TABLE \"sys_forestj_testddl\" ADD CONSTRAINT \"new_index_SmallInt_Bool\" UNIQUE (\"SmallInt\", \"Bool\"), DROP CONSTRAINT \"new_index_Int\"",
					"CREATE INDEX \"new_index_Text2\" ON \"sys_forestj_testddl\" (\"Text2\")",
					"DROP INDEX \"new_index_Text2\"",
					"ALTER TABLE \"sys_forestj_testddl\" RENAME COLUMN \"Text2\" TO \"Text2Changed\"::forestjSQLQuerySeparator::ALTER TABLE \"sys_forestj_testddl\" ALTER COLUMN \"Text2Changed\" TYPE varchar(255), ALTER COLUMN \"Text2Changed\" SET NOT NULL, ALTER COLUMN \"Text2Changed\" SET DEFAULT  'Das ist das Haus vom Nikolaus'",
					"INSERT INTO \"sys_forestj_testddl\" (\"UUID\", \"ShortText\", \"Text\", \"SmallInt\", \"Int\", \"BigInt\", \"DateTime\", \"Date\", \"Time\", \"LocalDateTime\", \"LocalDate\", \"LocalTime\", \"DoubleCol\", \"Decimal\", \"Bool\", \"Text2Changed\", \"ShortText2\") VALUES ('123e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 123, 1234567890, 1234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, 1, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO \"sys_forestj_testddl\" (\"UUID\", \"ShortText\", \"Text\", \"SmallInt\", \"Int\", \"BigInt\", \"DateTime\", \"Date\", \"Time\", \"LocalDateTime\", \"LocalDate\", \"LocalTime\", \"DoubleCol\", \"Decimal\", \"Bool\", \"Text2Changed\", \"ShortText2\") VALUES ('223e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 223, 1234567890, 2234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, 0, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO \"sys_forestj_testddl\" (\"UUID\", \"ShortText\", \"Text\", \"SmallInt\", \"Int\", \"BigInt\", \"DateTime\", \"Date\", \"Time\", \"LocalDateTime\", \"LocalDate\", \"LocalTime\", \"DoubleCol\", \"Decimal\", \"Bool\", \"Text2Changed\", \"ShortText2\") VALUES ('323e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 323, 1234567890, 3234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, 1, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"SELECT \"sys_forestj_testddl\".\"ShortText\", MIN(\"sys_forestj_testddl\".\"SmallInt\"), \"sys_forestj_testddl\".\"LocalDate\" AS \"Spalte C\", \"sys_forestj_testddl\".\"Int\", \"sys_forestj_testddl2\".\"Id\" FROM \"sys_forestj_testddl\" INNER JOIN \"sys_forestj_testddl2\" ON (\"sys_forestj_testddl2\".\"Id\" = \"sys_forestj_testddl\".\"Id\" AND \"sys_forestj_testddl2\".\"DoubleCol\" <= \"sys_forestj_testddl\".\"DoubleCol\") WHERE \"sys_forestj_testddl\".\"ShortText\" <> 'Wert' OR \"sys_forestj_testddl2\".\"Id\" >= 123 AND \"sys_forestj_testddl\".\"SmallInt\" > 25353 GROUP BY \"sys_forestj_testddl\".\"ShortText\", \"sys_forestj_testddl\".\"LocalDate\", \"sys_forestj_testddl\".\"Int\", \"sys_forestj_testddl2\".\"Id\" HAVING (\"sys_forestj_testddl\".\"Int\" <= 456.0 AND \"sys_forestj_testddl\".\"ShortText\" = 'Trew' AND \"sys_forestj_testddl\".\"LocalDate\" <> '2018-11-16') ORDER BY \"sys_forestj_testddl2\".\"Id\" ASC, \"sys_forestj_testddl\".\"ShortText\" DESC LIMIT 10 OFFSET 0",
					"UPDATE \"sys_forestj_testddl\" SET \"ShortText\" = 'Wert', \"Int\" = 1337, \"DoubleCol\" = 35.67, \"DateTime\" = '2003-12-15 08:33:03' WHERE \"ShortText\" <> 'Wert' OR \"SmallInt\" >= 123 AND \"DateTime\" >= '2003-12-15 08:33:03'",
					"SELECT * FROM \"sys_forestj_testddl\" WHERE \"sys_forestj_testddl\".\"DateTime\" <> '2003-12-15 08:33:03' OR \"sys_forestj_testddl\".\"Date\" >= '2009-06-29' AND \"sys_forestj_testddl\".\"Time\" > '11:01:43'",
					"SELECT * FROM \"sys_forestj_testddl\" WHERE \"sys_forestj_testddl\".\"LocalDateTime\" <> '2010-09-02 05:55:13' OR \"sys_forestj_testddl\".\"LocalDate\" >= '2018-11-16' AND \"sys_forestj_testddl\".\"LocalTime\" > '17:42:23'",
					"SELECT * FROM \"sys_forestj_testddl\"",
					"DELETE FROM \"sys_forestj_testddl\" WHERE \"sys_forestj_testddl\".\"ShortText\" <> 'Wert' OR \"sys_forestj_testddl\".\"SmallInt\" >= 32.45 AND \"sys_forestj_testddl\".\"DateTime\" > '2003-12-15 08:33:03'",
					"ALTER TABLE \"sys_forestj_testddl\" DROP \"ShortText2\"",
					"ALTER TABLE \"sys_forestj_testddl\" DROP \"BigInt\", DROP \"Int\"",
					"ALTER TABLE \"sys_forestj_testddl\" DROP CONSTRAINT \"new_index_SmallInt_Bool\"",
					"TRUNCATE TABLE \"sys_forestj_testddl\"",
					"DROP TABLE \"sys_forestj_testddl\"",
					"DROP TABLE \"sys_forestj_testddl2\""
				},
				/* ORACLE */ {
					"CREATE TABLE \"sys_forestj_testddl\" (\"Id\" NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY, \"UUID\" VARCHAR2(36) NOT NULL UNIQUE, \"ShortText\" VARCHAR2(255) NULL, \"Text\" CLOB NULL, \"SmallInt\" NUMBER(5) NULL, \"Int\" NUMBER(10) NULL, \"BigInt\" LONG NULL, \"DateTime\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL, \"Date\" TIMESTAMP DEFAULT timestamp '2020-04-06 08:10:12' NOT NULL, \"Time\" INTERVAL DAY(0) TO SECOND(0) NULL, \"LocalDateTime\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL, \"LocalDate\" TIMESTAMP NULL, \"LocalTime\" INTERVAL DAY(0) TO SECOND(0) DEFAULT '0 12:24:46', \"DoubleCol\" BINARY_DOUBLE NULL, \"Decimal\" NUMBER(38,9) NULL, \"Bool\" CHAR(1) NULL)",
					"CREATE TABLE \"sys_forestj_testddl2\" (\"Id\" NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY, \"DoubleCol\" BINARY_DOUBLE NULL)",
					"ALTER TABLE \"sys_forestj_testddl\" ADD (\"Text2\" VARCHAR2(36) NULL, \"ShortText2\" VARCHAR2(255) NULL)",
					"ALTER TABLE \"sys_forestj_testddl\" ADD CONSTRAINT \"new_index_Int\" UNIQUE (\"Int\")",
					"ALTER TABLE \"sys_forestj_testddl\" DROP CONSTRAINT \"new_index_Int\"::forestjSQLQuerySeparator::ALTER TABLE \"sys_forestj_testddl\" ADD CONSTRAINT \"new_index_SmallInt_Bool\" UNIQUE (\"SmallInt\", \"Bool\")",
					"CREATE INDEX \"new_index_Text2\" ON \"sys_forestj_testddl\" (\"Text2\")",
					"DROP INDEX \"new_index_Text2\"",
					"ALTER TABLE \"sys_forestj_testddl\" RENAME COLUMN \"Text2\" TO \"Text2Changed\"::forestjSQLQuerySeparator::ALTER TABLE \"sys_forestj_testddl\" MODIFY (\"Text2Changed\" VARCHAR2(255) DEFAULT 'Das ist das Haus vom Nikolaus' NOT NULL)",
					"INSERT INTO \"sys_forestj_testddl\" (\"UUID\", \"ShortText\", \"Text\", \"SmallInt\", \"Int\", \"BigInt\", \"DateTime\", \"Date\", \"Time\", \"LocalDateTime\", \"LocalDate\", \"LocalTime\", \"DoubleCol\", \"Decimal\", \"Bool\", \"Text2Changed\", \"ShortText2\") VALUES ('123e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 123, 1234567890, 1234567890123, '2003-12-15 08:33:03', '2009-06-29', TO_DSINTERVAL('+0 11:01:43'), '2010-09-02 05:55:13', '2018-11-16', TO_DSINTERVAL('+0 17:42:23'), 3.141592, 2.718281828, 1, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO \"sys_forestj_testddl\" (\"UUID\", \"ShortText\", \"Text\", \"SmallInt\", \"Int\", \"BigInt\", \"DateTime\", \"Date\", \"Time\", \"LocalDateTime\", \"LocalDate\", \"LocalTime\", \"DoubleCol\", \"Decimal\", \"Bool\", \"Text2Changed\", \"ShortText2\") VALUES ('223e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 223, 1234567890, 2234567890123, '2003-12-15 08:33:03', '2009-06-29', TO_DSINTERVAL('+0 11:01:43'), '2010-09-02 05:55:13', '2018-11-16', TO_DSINTERVAL('+0 17:42:23'), 3.141592, 2.718281828, 0, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO \"sys_forestj_testddl\" (\"UUID\", \"ShortText\", \"Text\", \"SmallInt\", \"Int\", \"BigInt\", \"DateTime\", \"Date\", \"Time\", \"LocalDateTime\", \"LocalDate\", \"LocalTime\", \"DoubleCol\", \"Decimal\", \"Bool\", \"Text2Changed\", \"ShortText2\") VALUES ('323e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 323, 1234567890, 3234567890123, '2003-12-15 08:33:03', '2009-06-29', TO_DSINTERVAL('+0 11:01:43'), '2010-09-02 05:55:13', '2018-11-16', TO_DSINTERVAL('+0 17:42:23'), 3.141592, 2.718281828, 1, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"SELECT \"sys_forestj_testddl\".\"ShortText\", MIN(\"sys_forestj_testddl\".\"SmallInt\"), \"sys_forestj_testddl\".\"LocalDate\" AS \"Spalte C\", \"sys_forestj_testddl\".\"Int\", \"sys_forestj_testddl2\".\"Id\" FROM \"sys_forestj_testddl\" INNER JOIN \"sys_forestj_testddl2\" ON (\"sys_forestj_testddl2\".\"Id\" = \"sys_forestj_testddl\".\"Id\" AND \"sys_forestj_testddl2\".\"DoubleCol\" <= \"sys_forestj_testddl\".\"DoubleCol\") WHERE \"sys_forestj_testddl\".\"ShortText\" <> 'Wert' OR \"sys_forestj_testddl2\".\"Id\" >= 123 AND \"sys_forestj_testddl\".\"SmallInt\" > 25353 GROUP BY \"sys_forestj_testddl\".\"ShortText\", \"sys_forestj_testddl\".\"LocalDate\", \"sys_forestj_testddl\".\"Int\", \"sys_forestj_testddl2\".\"Id\" HAVING (\"sys_forestj_testddl\".\"Int\" <= 456.0 AND \"sys_forestj_testddl\".\"ShortText\" = 'Trew' AND \"sys_forestj_testddl\".\"LocalDate\" <> '2018-11-16') ORDER BY \"sys_forestj_testddl2\".\"Id\" ASC, \"sys_forestj_testddl\".\"ShortText\" DESC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY",
					"UPDATE \"sys_forestj_testddl\" SET \"ShortText\" = 'Wert', \"Int\" = 1337, \"DoubleCol\" = 35.67, \"DateTime\" = '2003-12-15 08:33:03' WHERE \"ShortText\" <> 'Wert' OR \"SmallInt\" >= 123 AND \"DateTime\" >= '2003-12-15 08:33:03'",
					"SELECT * FROM \"sys_forestj_testddl\" WHERE \"sys_forestj_testddl\".\"DateTime\" <> '2003-12-15 08:33:03' OR \"sys_forestj_testddl\".\"Date\" >= '2009-06-29' AND \"sys_forestj_testddl\".\"Time\" > TO_DSINTERVAL('+0 11:01:43')",
					"SELECT * FROM \"sys_forestj_testddl\" WHERE \"sys_forestj_testddl\".\"LocalDateTime\" <> '2010-09-02 05:55:13' OR \"sys_forestj_testddl\".\"LocalDate\" >= '2018-11-16' AND \"sys_forestj_testddl\".\"LocalTime\" > TO_DSINTERVAL('+0 17:42:23')",
					"SELECT * FROM \"sys_forestj_testddl\"",
					"DELETE FROM \"sys_forestj_testddl\" WHERE \"sys_forestj_testddl\".\"ShortText\" <> 'Wert' OR \"sys_forestj_testddl\".\"SmallInt\" >= 32.45 AND \"sys_forestj_testddl\".\"DateTime\" > '2003-12-15 08:33:03'",
					"ALTER TABLE \"sys_forestj_testddl\" DROP (\"ShortText2\")",
					"ALTER TABLE \"sys_forestj_testddl\" DROP (\"BigInt\", \"Int\")",
					"ALTER TABLE \"sys_forestj_testddl\" DROP CONSTRAINT \"new_index_SmallInt_Bool\"",
					"TRUNCATE TABLE \"sys_forestj_testddl\"",
					"DROP TABLE \"sys_forestj_testddl\"",
					"DROP TABLE \"sys_forestj_testddl2\""
				},
				/* NOSQLMDB */ {
					"CREATE TABLE `sys_forestj_testddl` (`Id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, `UUID` VARCHAR NOT NULL UNIQUE, `ShortText` VARCHAR NULL, `Text` TEXT NULL, `SmallInt` SMALLINT NULL, `Int` INTEGER NULL, `BigInt` BIGINT NULL, `DateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, `Date` TIMESTAMP NOT NULL DEFAULT '2020-04-06 08:10:12', `Time` TIME NULL, `LocalDateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, `LocalDate` TIMESTAMP NULL, `LocalTime` TIME DEFAULT '12:24:46', `DoubleCol` DOUBLE NULL, `Decimal` DECIMAL NULL, `Bool` BOOL NULL)",
					"CREATE TABLE `sys_forestj_testddl2` (`Id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, `DoubleCol` DOUBLE NULL)",
					"ALTER TABLE `sys_forestj_testddl` ADD `ShortText3` VARCHAR NULL, ADD `Text3` VARCHAR NULL",
					"ALTER TABLE `sys_forestj_testddl` ADD UNIQUE `new_index_Int` (`Int`)",
					"ALTER TABLE `sys_forestj_testddl` ADD UNIQUE `new_index_SmallInt_Bool` (`SmallInt`, `Bool`), DROP INDEX `new_index_Int`",
					"ALTER TABLE `sys_forestj_testddl` ADD INDEX `new_index_Text2` (`Text2`)",
					"ALTER TABLE `sys_forestj_testddl` DROP INDEX `new_index_Text2`",
					"ALTER TABLE `sys_forestj_testddl` CHANGE `Text2Changed` `Text2` VARCHAR NOT NULL DEFAULT 'Das ist das Haus vom Nikolaus'",
					"INSERT INTO `sys_forestj_testddl` (`sys_forestj_testddl`.`UUID`, `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`Text`, `sys_forestj_testddl`.`SmallInt`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl`.`BigInt`, `sys_forestj_testddl`.`DateTime`, `sys_forestj_testddl`.`Date`, `sys_forestj_testddl`.`Time`, `sys_forestj_testddl`.`LocalDateTime`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`LocalTime`, `sys_forestj_testddl`.`DoubleCol`, `sys_forestj_testddl`.`Decimal`, `sys_forestj_testddl`.`Bool`, `sys_forestj_testddl`.`Text2Changed`, `sys_forestj_testddl`.`ShortText2`) VALUES ('123e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 123, 1234567890, 1234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, true, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO `sys_forestj_testddl` (`sys_forestj_testddl`.`UUID`, `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`Text`, `sys_forestj_testddl`.`SmallInt`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl`.`BigInt`, `sys_forestj_testddl`.`DateTime`, `sys_forestj_testddl`.`Date`, `sys_forestj_testddl`.`Time`, `sys_forestj_testddl`.`LocalDateTime`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`LocalTime`, `sys_forestj_testddl`.`DoubleCol`, `sys_forestj_testddl`.`Decimal`, `sys_forestj_testddl`.`Bool`, `sys_forestj_testddl`.`Text2Changed`, `sys_forestj_testddl`.`ShortText2`) VALUES ('223e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 223, 1234567890, 2234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, false, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"INSERT INTO `sys_forestj_testddl` (`sys_forestj_testddl`.`UUID`, `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`Text`, `sys_forestj_testddl`.`SmallInt`, `sys_forestj_testddl`.`Int`, `sys_forestj_testddl`.`BigInt`, `sys_forestj_testddl`.`DateTime`, `sys_forestj_testddl`.`Date`, `sys_forestj_testddl`.`Time`, `sys_forestj_testddl`.`LocalDateTime`, `sys_forestj_testddl`.`LocalDate`, `sys_forestj_testddl`.`LocalTime`, `sys_forestj_testddl`.`DoubleCol`, `sys_forestj_testddl`.`Decimal`, `sys_forestj_testddl`.`Bool`, `sys_forestj_testddl`.`Text2Changed`, `sys_forestj_testddl`.`ShortText2`) VALUES ('323e4567-e89b-42d3-a456-556642440000', 'a short text', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.', 323, 1234567890, 3234567890123, '2003-12-15 08:33:03', '2009-06-29', '11:01:43', '2010-09-02 05:55:13', '2018-11-16', '17:42:23', 3.141592, 2.718281828, true, 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.', 'another short text')",
					"SELECT `sys_forestj_testddl`.`ShortText`, `sys_forestj_testddl`.`LocalDate` AS 'Spalte C', `sys_forestj_testddl`.`Int` FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl`.`Id` >= 123 AND `sys_forestj_testddl`.`SmallInt` > 25353 ORDER BY `sys_forestj_testddl`.`Id` ASC, `sys_forestj_testddl`.`ShortText` DESC LIMIT 0, 10",
					"UPDATE `sys_forestj_testddl` SET `sys_forestj_testddl`.`ShortText` = 'Wert', `sys_forestj_testddl`.`Int` = 1337, `sys_forestj_testddl`.`DoubleCol` = 35.67, `sys_forestj_testddl`.`DateTime` = '2003-12-15 08:33:03' WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl`.`SmallInt` >= 123 AND `sys_forestj_testddl`.`DateTime` >= '2003-12-15 08:33:03'",
					"SELECT * FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`DateTime` <> '2003-12-15 08:33:03' OR `sys_forestj_testddl`.`Date` >= '2009-06-29' AND `sys_forestj_testddl`.`Time` > '11:01:43'",
					"SELECT * FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`LocalDateTime` <> '2010-09-02 05:55:13' OR `sys_forestj_testddl`.`LocalDate` >= '2018-11-16' AND `sys_forestj_testddl`.`LocalTime` > '17:42:23'",
					"SELECT * FROM `sys_forestj_testddl`",
					"DELETE FROM `sys_forestj_testddl` WHERE `sys_forestj_testddl`.`ShortText` <> 'Wert' OR `sys_forestj_testddl`.`SmallInt` >= 32.45 AND `sys_forestj_testddl`.`DateTime` > '2003-12-15 08:33:03'",
					"ALTER TABLE `sys_forestj_testddl` DROP `ShortText2`",
					"ALTER TABLE `sys_forestj_testddl` DROP `BigInt`, DROP `Int`",
					"ALTER TABLE `sys_forestj_testddl` DROP INDEX `new_index_SmallInt_Bool`",
					"TRUNCATE TABLE `sys_forestj_testddl`",
					"DROP TABLE `sys_forestj_testddl`",
					"DROP TABLE `sys_forestj_testddl2`",
					"SELECT `sys_forestj_products`.`SupplierID`, `sys_forestj_products`.`CategoryID`, `sys_forestj_categories`.`CategoryName`, `sys_forestj_categories`.`Description`, `sys_forestj_products`.`ProductID`, `sys_forestj_products`.`ProductName`, `sys_forestj_products`.`Unit`, `sys_forestj_products`.`Price` FROM `sys_forestj_products` INNER JOIN `sys_forestj_categories` ON `sys_forestj_products`.`CategoryID` = `sys_forestj_categories`.`CategoryID` WHERE `sys_forestj_products`.`ProductID` > 50 AND `sys_forestj_categories`.`CategoryID` > 3 ORDER BY `sys_forestj_products`.`SupplierID` ASC, `sys_forestj_categories`.`CategoryName` ASC LIMIT 0, 50",
					"SELECT `sys_forestj_products`.`SupplierID`, COUNT(`sys_forestj_products`.`ProductID`), `sys_forestj_products`.`ProductName`, `sys_forestj_products`.`Unit`, MAX(`sys_forestj_products`.`Price`) FROM `sys_forestj_products` WHERE `sys_forestj_products`.`SupplierID` < 100 GROUP BY `sys_forestj_products`.`SupplierID` HAVING MAX(`sys_forestj_products`.`Price`) > 50.0 ORDER BY COUNT(`sys_forestj_products`.`ProductID`) ASC, MAX(`sys_forestj_products`.`Price`) ASC LIMIT 0, 50",
					"SELECT `sys_forestj_products`.`SupplierID`, `sys_forestj_products`.`CategoryID`, `sys_forestj_categories`.`CategoryName`, `sys_forestj_categories`.`Description`, COUNT(`sys_forestj_products`.`ProductID`), `sys_forestj_products`.`ProductName`, `sys_forestj_products`.`Unit`, MIN(`sys_forestj_products`.`Price`) FROM `sys_forestj_products` INNER JOIN `sys_forestj_categories` ON `sys_forestj_products`.`CategoryID` = `sys_forestj_categories`.`CategoryID` WHERE `sys_forestj_products`.`SupplierID` < 50 GROUP BY `sys_forestj_products`.`SupplierID` HAVING MIN(`sys_forestj_products`.`Price`) > 20.0 AND COUNT(`sys_forestj_products`.`ProductID`) > 1 ORDER BY COUNT(`sys_forestj_products`.`ProductID`) DESC, `sys_forestj_products`.`SupplierID` ASC LIMIT 0, 50"
				}
			};
			
			net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
			
			int i_amountQueries = 23;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_amountQueries = 26;
			} else {
				i_amountQueries = 23;
			}
			
			if (a_expectedQueries[a_baseGateways.get(o_glob.BaseGateway.toString())].length != i_amountQueries) {
				assertTrue(false, "Amount of expected queries is not valid for basegateway '" + o_glob.BaseGateway + "': " + a_expectedQueries[a_baseGateways.get(o_glob.BaseGateway.toString())].length + " != " + i_amountQueries);
			}
			
			for (int i = 1; i <= i_amountQueries; i++) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty(a_expectedQueries[a_baseGateways.get(o_glob.BaseGateway.toString())][(i - 1)])) {
					continue;
				}
				
				java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> a_values = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, Object>>();
				String s_testQuery = net.forestany.forestj.lib.sql.Query.convertPreparedStatementSqlQueryToStandard(net.forestany.forestj.lib.sql.Query.convertToPreparedStatementQuery(o_glob.BaseGateway, testQueryGenerator(i).toString(), a_values, true), a_values);
				String s_expectedQuery = a_expectedQueries[a_baseGateways.get(o_glob.BaseGateway.toString())][(i - 1)];
				
				/* within sqlite query generation, we use random string value for creating temp. tables for changing columns or constraints */
				if ( (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE) && ( (i == 8) || (i == 18) || (i == 19) ) ) {
					String s_random = s_testQuery.substring(14, 30);
					s_expectedQuery = s_expectedQuery.replace("REPLACE_RANDOM", s_random);
				}
					
				assertEquals(
					s_testQuery,
					s_expectedQuery,
					"[" + o_glob.BaseGateway + "] - Query #" + (i) + " does not match expectation"
				);
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
	}

	/**
	 * method to generate queries
	 * 
	 * @param p_i_queryNumber number for type of query we want to produce
	 * @return Query&lt;?&gt; instance
	 * @throws Exception any exception during creating query instance
	 */
	public static net.forestany.forestj.lib.sql.Query<?> testQueryGenerator(int p_i_queryNumber) throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		net.forestany.forestj.lib.sql.Query<?> o_queryReturn = null;
		
		java.util.Date o_dateTime = null;
		java.util.Date o_date = null;
		java.util.Date o_time = null;
		
		try {
			o_dateTime = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.GERMAN).parse("15.12.2003 08:33:03");
			o_date = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).parse("29.06.2009");
			o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("11:01:43");
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
		
		java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.of(2010, 9, 2, 5, 55, 13);
		java.time.LocalDate o_localDate = java.time.LocalDate.of(2018, 11, 16);
		java.time.LocalTime o_localTime = java.time.LocalTime.of(17, 42, 23);
		
		int i_number = 1;
		
		if (p_i_queryNumber == i_number++) {	
			/* #### ######  ############################################################################ */
			/* #### CREATE  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_testddl");
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinition = testGetColumnDefinitions(0);
			
			/* #### Query ############################################################################ */
			
			for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
				net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryCreate);
				o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
				o_column.s_name = o_columnDefinition.getProperty("name");
				o_column.setAlterOperation("ADD");
				
				if (o_columnDefinition.containsKey("constraints")) {
					String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
					
					for (int i = 0; i < a_constraints.length; i++) {
						o_column.addConstraint(o_queryCreate.constraintTypeAllocation(a_constraints[i]));
						
						if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
							o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
						}
					}
				}
				
				o_queryCreate.getQuery().a_columns.add(o_column);
			}
			
			o_queryReturn = o_queryCreate;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### CREATE  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_testddl2");
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "Id");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NOT NULL;PRIMARY KEY;AUTO_INCREMENT");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "DoubleCol");
			o_properties.put("columnType", "double");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);
			
			/* #### Query ############################################################################ */
			
			for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
				net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryCreate);
				o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
				o_column.s_name = o_columnDefinition.getProperty("name");
				o_column.setAlterOperation("ADD");
				
				if (o_columnDefinition.containsKey("constraints")) {
					String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
					
					for (int i = 0; i < a_constraints.length; i++) {
						o_column.addConstraint(o_queryCreate.constraintTypeAllocation(a_constraints[i]));
						
						if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
							o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
						}
					}
				}
				
				o_queryCreate.getQuery().a_columns.add(o_column);
			}
			
			o_queryReturn = o_queryCreate;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 1 ############################################################################ */
			/* #### ####### ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "Text2");
			o_properties.put("columnType", "text [36]");
			o_properties.put("constraints", "NULL");
			
			/* other query for nosqlmdb */
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				o_properties.put("name", "ShortText3");
			}
			
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "ShortText2");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NULL");
			
			/* other query for nosqlmdb */
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				o_properties.put("name", "Text3");
			}
		
			a_columnsDefinition.add(o_properties);
			
			/* #### Query ############################################################################ */
			
			for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
				net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryAlter);
				o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
				o_column.s_name = o_columnDefinition.getProperty("name");
				o_column.setAlterOperation("ADD");
				
				if (o_columnDefinition.containsKey("constraints")) {
					String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
					
					for (int i = 0; i < a_constraints.length; i++) {
						o_column.addConstraint(o_queryAlter.constraintTypeAllocation(a_constraints[i]));
						
						if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
							o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
						}
					}
				}
				
				o_queryAlter.getQuery().a_columns.add(o_column);
			}
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 2 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Constraints ############################################################################ */
			net.forestany.forestj.lib.sql.Constraint o_constraint = new net.forestany.forestj.lib.sql.Constraint(o_queryAlter, "UNIQUE", "new_index_Int", "", "ADD");
				o_constraint.a_columns.add("Int");
				
			o_queryAlter.getQuery().a_constraints.add(o_constraint);
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 3 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Constraints ############################################################################ */
			net.forestany.forestj.lib.sql.Constraint o_constraint = new net.forestany.forestj.lib.sql.Constraint(o_queryAlter, "UNIQUE", "new_index_Int", "new_index_SmallInt_Bool", "CHANGE");
				o_constraint.a_columns.add("SmallInt");
				o_constraint.a_columns.add("Bool");
			
			o_queryAlter.getQuery().a_constraints.add(o_constraint);
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 4 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Constraints ############################################################################ */
			net.forestany.forestj.lib.sql.Constraint o_constraint = new net.forestany.forestj.lib.sql.Constraint(o_queryAlter, "INDEX", "new_index_Text2", "", "ADD");
				o_constraint.a_columns.add("Text2");
				
			o_queryAlter.getQuery().a_constraints.add(o_constraint);
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 5 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Constraints ############################################################################ */
			net.forestany.forestj.lib.sql.Constraint o_constraint = new net.forestany.forestj.lib.sql.Constraint(o_queryAlter, "INDEX", "new_index_Text2", "", "DROP");
				
			o_queryAlter.getQuery().a_constraints.add(o_constraint);
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 6 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Columns To Change ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "Text2");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NOT NULL;DEFAULT");
			o_properties.put("constraintDefaultValue", "Das ist das Haus vom Nikolaus");
			o_properties.put("newName", "Text2Changed");
			
			/* other query for nosqlmdb */
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				o_properties.put("name", "Text2Changed");
				o_properties.put("newName", "Text2");
			}
			
			a_columnsDefinition.add(o_properties);
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinitionSqLite = testGetColumnDefinitions(1);
			
			/* #### Query ############################################################################ */
			
			for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
				net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryAlter);
				o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
				o_column.s_name = o_columnDefinition.getProperty("name");
				o_column.setAlterOperation("CHANGE");
				
				if (o_columnDefinition.containsKey("newName")) {
					o_column.s_newName = o_columnDefinition.getProperty("newName");
				}
				
				if (o_columnDefinition.containsKey("constraints")) {
					String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
					
					for (int i = 0; i < a_constraints.length; i++) {
						o_column.addConstraint(o_queryAlter.constraintTypeAllocation(a_constraints[i]));
						
						if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
							o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
						}
					}
				}
				
				o_queryAlter.getQuery().a_columns.add(o_column);
			}
			
			/* only for sqlite */
			o_queryAlter.getQuery().a_sqliteColumnsDefinition = a_columnsDefinitionSqLite;
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### INSERT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_testddl");
			/* #### Columns ############################################################################ */
			
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Id");
			
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "123e4567-e89b-42d3-a456-556642440000") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText"), "a short text") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "SmallInt"), 123) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Int"), 1_234_567_890) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "BigInt"), java.lang.Long.valueOf("1234567890123")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DateTime"), o_dateTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Date"), o_date) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Time"), o_time) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDateTime"), o_localDateTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDate"), o_localDate) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalTime"), o_localTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DoubleCol"), 3.141592d) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Decimal"), java.math.BigDecimal.valueOf(2.718281828d)) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Bool"), true) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text2Changed"), "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText2"), "another short text") );
				
			o_queryReturn = o_queryInsert;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### INSERT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_testddl");
			/* #### Columns ############################################################################ */
			
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Id");
			
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "223e4567-e89b-42d3-a456-556642440000") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText"), "a short text") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "SmallInt"), 223) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Int"), 1_234_567_890) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "BigInt"), java.lang.Long.valueOf("2234567890123")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DateTime"), o_dateTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Date"), o_date) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Time"), o_time) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDateTime"), o_localDateTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDate"), o_localDate) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalTime"), o_localTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DoubleCol"), 3.141592d) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Decimal"), java.math.BigDecimal.valueOf(2.718281828d)) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Bool"), false) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text2Changed"), "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText2"), "another short text") );
				
			o_queryReturn = o_queryInsert;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### INSERT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_testddl");
			/* #### Columns ############################################################################ */
			
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Id");
			
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "323e4567-e89b-42d3-a456-556642440000") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText"), "a short text") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "SmallInt"), 323) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Int"), 1_234_567_890) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "BigInt"), java.lang.Long.valueOf("3234567890123")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DateTime"), o_dateTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Date"), o_date) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Time"), o_time) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDateTime"), o_localDateTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDate"), o_localDate) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalTime"), o_localTime) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DoubleCol"), 3.141592d) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Decimal"), java.math.BigDecimal.valueOf(2.718281828d)) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Bool"), true) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text2Changed"), "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.") );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText2"), "another short text") );
				
			o_queryReturn = o_queryInsert;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### SELECT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_testddl");
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				/* #### Columns ############################################################################ */
				net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ShortText");
				net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "LocalDate", "Spalte C");
				net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Int");
				net.forestany.forestj.lib.sql.Column column_D = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Id");
				
				o_querySelect.getQuery().a_columns.add(column_A);
				o_querySelect.getQuery().a_columns.add(column_B);
				o_querySelect.getQuery().a_columns.add(column_C);
				/* ##### Where ########################################################################### */
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, "Wert", "<>") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_D, 123, ">=", "OR") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect,  new net.forestany.forestj.lib.sql.Column(o_querySelect, "SmallInt"), java.lang.Short.valueOf("25353"), ">", "AND") );
				/* #### OrderBy ############################################################################ */
				o_querySelect.getQuery().o_orderBy = new net.forestany.forestj.lib.sql.OrderBy(o_querySelect, java.util.Arrays.asList(column_D, column_A), java.util.Arrays.asList(true, false));
				/* #### Limit ############################################################################ */
				o_querySelect.getQuery().o_limit = new net.forestany.forestj.lib.sql.Limit(o_querySelect, 0 , 10);
			} else {
				/* #### Columns ############################################################################ */
				net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ShortText");
				net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "SmallInt", "", "MIN");
				net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "LocalDate", "Spalte C");
				net.forestany.forestj.lib.sql.Column column_D = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Int");
				net.forestany.forestj.lib.sql.Column column_E = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Id");
					column_E.s_table = "sys_forestj_testddl2";
				
				o_querySelect.getQuery().a_columns.add(column_A);
				o_querySelect.getQuery().a_columns.add(column_B);
				o_querySelect.getQuery().a_columns.add(column_C);
				o_querySelect.getQuery().a_columns.add(column_D);
				o_querySelect.getQuery().a_columns.add(column_E);
				/* #### Joins ############################################################################ */
				net.forestany.forestj.lib.sql.Join join_A = new net.forestany.forestj.lib.sql.Join(o_querySelect, "INNER JOIN");
					join_A.s_table = "sys_forestj_testddl2";
		
				net.forestany.forestj.lib.sql.Column column_F = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Id");
					column_F.s_table = join_A.s_table;
				
				net.forestany.forestj.lib.sql.Column column_G = new net.forestany.forestj.lib.sql.Column(o_querySelect, "DoubleCol");
					column_G.s_table = join_A.s_table;
					
				join_A.a_relations.add( new net.forestany.forestj.lib.sql.Relation(o_querySelect, column_F, new net.forestany.forestj.lib.sql.Column(o_querySelect, "Id"), "=", "", true) );
				join_A.a_relations.add( new net.forestany.forestj.lib.sql.Relation(o_querySelect, column_G, new net.forestany.forestj.lib.sql.Column(o_querySelect, "DoubleCol"), "<=", "AND", false, true) );
				
				o_querySelect.getQuery().a_joins.add(join_A);
				/* ##### Where ########################################################################### */
				
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, "Wert", "<>") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_F, 123, ">=", "OR") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect,  new net.forestany.forestj.lib.sql.Column(o_querySelect, "SmallInt"), java.lang.Short.valueOf("25353"), ">", "AND") );
				/* #### GroupBy ############################################################################ */
				o_querySelect.getQuery().a_groupBy.add(column_A);
				o_querySelect.getQuery().a_groupBy.add(column_C);
				o_querySelect.getQuery().a_groupBy.add(column_D);
				o_querySelect.getQuery().a_groupBy.add(column_E);
				/* #### Having ############################################################################ */
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_D, 456.f, "<=", "", true) );
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, "Trew", "=", "AND") );
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_C, o_localDate, "<>", "AND", false, true) );
				/* #### OrderBy ############################################################################ */
				o_querySelect.getQuery().o_orderBy = new net.forestany.forestj.lib.sql.OrderBy(o_querySelect, java.util.Arrays.asList(column_F, column_A), java.util.Arrays.asList(true, false));
				/* #### Limit ############################################################################ */
				o_querySelect.getQuery().o_limit = new net.forestany.forestj.lib.sql.Limit(o_querySelect, 0 , 10);
			}
			
			o_queryReturn = o_querySelect;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### UPDATE  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Update> o_queryUpdate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Update>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.UPDATE, "sys_forestj_testddl");
			
			/* #### Columns ############################################################################ */
			o_queryUpdate.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "ShortText"), "Wert") );
			o_queryUpdate.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "Int"), 1337) );
			o_queryUpdate.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "DoubleCol"), 35.67f) );
			o_queryUpdate.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "DateTime"), o_dateTime) );
			
			/* ##### Where ########################################################################### */
			o_queryUpdate.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryUpdate, new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "ShortText"), "Wert", "<>") );
			o_queryUpdate.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryUpdate, new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "SmallInt"), 123, ">=", "OR") );
			o_queryUpdate.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryUpdate, new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "DateTime"), o_dateTime, ">=", "AND") );
			
			o_queryReturn = o_queryUpdate;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### SELECT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_testddl");
			
			/* #### Columns ############################################################################ */
			o_querySelect.getQuery().a_columns.add(new net.forestany.forestj.lib.sql.Column(o_querySelect, "*"));

			/* ##### Where ########################################################################### */
			net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "DateTime");
			net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Date");
			net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Time");
			
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, o_dateTime, "<>") );
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_B, o_date, ">=", "OR") );
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_C, o_time, ">", "AND") );
			
			o_queryReturn = o_querySelect;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### SELECT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_testddl");
			
			/* #### Columns ############################################################################ */
			o_querySelect.getQuery().a_columns.add(new net.forestany.forestj.lib.sql.Column(o_querySelect, "*"));

			/* ##### Where ########################################################################### */
			net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "LocalDateTime");
			net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "LocalDate");
			net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "LocalTime");
			
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, o_localDateTime, "<>") );
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_B, o_localDate, ">=", "OR") );
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_C, o_localTime, ">", "AND") );
			
			o_queryReturn = o_querySelect;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### SELECT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_testddl");
			
			/* #### Columns ############################################################################ */
			o_querySelect.getQuery().a_columns.add(new net.forestany.forestj.lib.sql.Column(o_querySelect, "*"));

			o_queryReturn = o_querySelect;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### DELETE  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Delete> o_queryDelete = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Delete>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DELETE, "sys_forestj_testddl");
			
			/* ##### Where ########################################################################### */
			o_queryDelete.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryDelete, new net.forestany.forestj.lib.sql.Column(o_queryDelete, "ShortText"), "Wert", "<>") );
			o_queryDelete.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryDelete, new net.forestany.forestj.lib.sql.Column(o_queryDelete, "SmallInt"), 32.45f, ">=", "OR") );
			o_queryDelete.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryDelete, new net.forestany.forestj.lib.sql.Column(o_queryDelete, "DateTime"), o_dateTime, ">", "AND") );
			
			o_queryReturn = o_queryDelete;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 7 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Query ############################################################################ */
			net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryAlter);
			o_column.columnTypeAllocation("text [255]");
			o_column.s_name = "ShortText2";
			o_column.setAlterOperation("DROP");
			
			o_queryAlter.getQuery().a_columns.add(o_column);
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinitionSqLite = testGetColumnDefinitions(2);
			
			/* only for sqlite */
			o_queryAlter.getQuery().a_sqliteColumnsDefinition = a_columnsDefinitionSqLite;
			
			/* #### Indexes ############################################################################ */
			java.util.List<java.util.Properties> a_indexesDefinitionSqLite = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "new_index_BigInt_Bool");
			o_properties.put("columns", "BigInt;Bool");
			o_properties.put("unique", "1");
			a_indexesDefinitionSqLite.add(o_properties);
			
			/* only for sqlite */
			o_queryAlter.getQuery().a_sqliteIndexes = a_indexesDefinitionSqLite;
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 8 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Query ############################################################################ */
			net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryAlter);
			o_column.columnTypeAllocation("integer [big]");
			o_column.s_name = "BigInt";
			o_column.setAlterOperation("DROP");
			
			o_queryAlter.getQuery().a_columns.add(o_column);
			
			o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryAlter);
			o_column.columnTypeAllocation("integer [int]");
			o_column.s_name = "Int";
			o_column.setAlterOperation("DROP");
			
			o_queryAlter.getQuery().a_columns.add(o_column);
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinitionSqLite = testGetColumnDefinitions(3);
						
			/* only for sqlite */
			o_queryAlter.getQuery().a_sqliteColumnsDefinition = a_columnsDefinitionSqLite;
			
			/* #### Indexes ############################################################################ */
			java.util.List<java.util.Properties> a_indexesDefinitionSqLite = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "new_index_SmallInt_Bool");
			o_properties.put("columns", "SmallInt;Bool");
			o_properties.put("unique", "1");
			a_indexesDefinitionSqLite.add(o_properties);
			
			/* only for sqlite */
			o_queryAlter.getQuery().a_sqliteIndexes = a_indexesDefinitionSqLite;
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ####### ############################################################################ */
			/* #### ALTER 9 ############################################################################ */
			/* #### ####### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_testddl");
			
			/* #### Constraints ############################################################################ */
			net.forestany.forestj.lib.sql.Constraint o_constraint = new net.forestany.forestj.lib.sql.Constraint(o_queryAlter, "UNIQUE", "new_index_SmallInt_Bool", "", "DROP");
				
			o_queryAlter.getQuery().a_constraints.add(o_constraint);
			
			o_queryReturn = o_queryAlter;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######## ############################################################################ */
			/* #### TRUNCATE ############################################################################ */
			/* #### ######## ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Truncate> o_queryTruncate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Truncate>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.TRUNCATE, "sys_forestj_testddl");
			o_queryReturn = o_queryTruncate;
		} else if (p_i_queryNumber == i_number++) {
			/* #### #### ############################################################################ */
			/* #### DROP ############################################################################ */
			/* #### #### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_testddl");
			o_queryReturn = o_queryDrop;
		} else if (p_i_queryNumber == i_number++) {
			/* #### #### ############################################################################ */
			/* #### DROP ############################################################################ */
			/* #### #### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_testddl2");
			o_queryReturn = o_queryDrop;
		} else if (p_i_queryNumber == i_number++) {
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				/* #### ######  ############################################################################ */
				/* #### SELECT  ############################################################################ */
				/* #### ######  ############################################################################ */
				
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_products");
				
				/* #### Columns ############################################################################ */
				net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "SupplierID");
				net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "CategoryID");
				net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "CategoryName");
					column_C.s_table = "sys_forestj_categories";
				net.forestany.forestj.lib.sql.Column column_D = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Description");
					column_D.s_table = "sys_forestj_categories";
				net.forestany.forestj.lib.sql.Column column_E = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ProductID");
				net.forestany.forestj.lib.sql.Column column_F = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ProductName");
				net.forestany.forestj.lib.sql.Column column_G = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Unit");
				net.forestany.forestj.lib.sql.Column column_H = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Price");
				
				o_querySelect.getQuery().a_columns.add(column_A);
				o_querySelect.getQuery().a_columns.add(column_B);
				o_querySelect.getQuery().a_columns.add(column_C);
				o_querySelect.getQuery().a_columns.add(column_D);
				o_querySelect.getQuery().a_columns.add(column_E);
				o_querySelect.getQuery().a_columns.add(column_F);
				o_querySelect.getQuery().a_columns.add(column_G);
				o_querySelect.getQuery().a_columns.add(column_H);
				/* #### Joins ############################################################################ */
				net.forestany.forestj.lib.sql.Join join_A = new net.forestany.forestj.lib.sql.Join(o_querySelect, "INNER JOIN");
					join_A.s_table = "sys_forestj_categories";
		
				net.forestany.forestj.lib.sql.Column column_I = new net.forestany.forestj.lib.sql.Column(o_querySelect, "CategoryID");
					column_I.s_table = join_A.s_table;
				
				join_A.a_relations.add( new net.forestany.forestj.lib.sql.Relation(o_querySelect, column_B, column_I, "=") );
				
				o_querySelect.getQuery().a_joins.add(join_A);
				/* ##### Where ########################################################################### */
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_E, 50, ">") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_I, 3, ">", "AND") );
				/* #### OrderBy ############################################################################ */
				o_querySelect.getQuery().o_orderBy = new net.forestany.forestj.lib.sql.OrderBy(o_querySelect, java.util.Arrays.asList(column_A, column_C), java.util.Arrays.asList(true, true));
				/* #### Limit ############################################################################ */
				o_querySelect.getQuery().o_limit = new net.forestany.forestj.lib.sql.Limit(o_querySelect, 0 , 50);
				
				o_queryReturn = o_querySelect;
			}
		} else if (p_i_queryNumber == i_number++) {
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				/* #### ######  ############################################################################ */
				/* #### SELECT  ############################################################################ */
				/* #### ######  ############################################################################ */
				
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_products");
				
				/* #### Columns ############################################################################ */
				net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "SupplierID");
				net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ProductID", "", "COUNT");
				net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ProductName");
				net.forestany.forestj.lib.sql.Column column_D = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Unit");
				net.forestany.forestj.lib.sql.Column column_E = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Price", "", "MAX");
				
				o_querySelect.getQuery().a_columns.add(column_A);
				o_querySelect.getQuery().a_columns.add(column_B);
				o_querySelect.getQuery().a_columns.add(column_C);
				o_querySelect.getQuery().a_columns.add(column_D);
				o_querySelect.getQuery().a_columns.add(column_E);
				/* ##### Where ########################################################################### */
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, 100, "<") );
				/* #### GroupBy ############################################################################ */
				o_querySelect.getQuery().a_groupBy.add(column_A);
				/* #### Having ############################################################################ */
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_E, 50.d, ">") );
				/* #### OrderBy ############################################################################ */
				o_querySelect.getQuery().o_orderBy = new net.forestany.forestj.lib.sql.OrderBy(o_querySelect, java.util.Arrays.asList(column_B, column_E), java.util.Arrays.asList(true, true));
				/* #### Limit ############################################################################ */
				o_querySelect.getQuery().o_limit = new net.forestany.forestj.lib.sql.Limit(o_querySelect, 0 , 50);
				
				o_queryReturn = o_querySelect;
			}
		} else if (p_i_queryNumber == i_number++) {
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				/* #### ######  ############################################################################ */
				/* #### SELECT  ############################################################################ */
				/* #### ######  ############################################################################ */
				
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_products");
				
				/* #### Columns ############################################################################ */
				net.forestany.forestj.lib.sql.Column column_A = new net.forestany.forestj.lib.sql.Column(o_querySelect, "SupplierID");
				net.forestany.forestj.lib.sql.Column column_B = new net.forestany.forestj.lib.sql.Column(o_querySelect, "CategoryID");
				net.forestany.forestj.lib.sql.Column column_C = new net.forestany.forestj.lib.sql.Column(o_querySelect, "CategoryName");
					column_C.s_table = "sys_forestj_categories";
				net.forestany.forestj.lib.sql.Column column_D = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Description");
					column_D.s_table = "sys_forestj_categories";
				net.forestany.forestj.lib.sql.Column column_E = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ProductID", "", "COUNT");
				net.forestany.forestj.lib.sql.Column column_F = new net.forestany.forestj.lib.sql.Column(o_querySelect, "ProductName");
				net.forestany.forestj.lib.sql.Column column_G = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Unit");
				net.forestany.forestj.lib.sql.Column column_H = new net.forestany.forestj.lib.sql.Column(o_querySelect, "Price", "", "MIN");
				
				o_querySelect.getQuery().a_columns.add(column_A);
				o_querySelect.getQuery().a_columns.add(column_B);
				o_querySelect.getQuery().a_columns.add(column_C);
				o_querySelect.getQuery().a_columns.add(column_D);
				o_querySelect.getQuery().a_columns.add(column_E);
				o_querySelect.getQuery().a_columns.add(column_F);
				o_querySelect.getQuery().a_columns.add(column_G);
				o_querySelect.getQuery().a_columns.add(column_H);
				/* #### Joins ############################################################################ */
				net.forestany.forestj.lib.sql.Join join_A = new net.forestany.forestj.lib.sql.Join(o_querySelect, "INNER JOIN");
					join_A.s_table = "sys_forestj_categories";
		
				net.forestany.forestj.lib.sql.Column column_I = new net.forestany.forestj.lib.sql.Column(o_querySelect, "CategoryID");
					column_I.s_table = join_A.s_table;
				
				join_A.a_relations.add( new net.forestany.forestj.lib.sql.Relation(o_querySelect, column_B, column_I, "=") );
				
				o_querySelect.getQuery().a_joins.add(join_A);
				/* ##### Where ########################################################################### */
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, 50, "<") );
				/* #### GroupBy ############################################################################ */
				o_querySelect.getQuery().a_groupBy.add(column_A);
				/* #### Having ############################################################################ */
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_H, 20.d, ">") );
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_E, 1, ">", "AND") );
				/* #### OrderBy ############################################################################ */
				o_querySelect.getQuery().o_orderBy = new net.forestany.forestj.lib.sql.OrderBy(o_querySelect, java.util.Arrays.asList(column_E, column_A), java.util.Arrays.asList(false, true));
				/* #### Limit ############################################################################ */
				o_querySelect.getQuery().o_limit = new net.forestany.forestj.lib.sql.Limit(o_querySelect, 0 , 50);
				
				o_queryReturn = o_querySelect;
			}
		}
		
		return o_queryReturn;
	}

	/**
	 * method to get column definitions for creating queries
	 * 
	 * @param p_i_columnDefinitionsNumber number for column definitions, for extinguishing some examples
	 * @return java.util.List&lt;java.util.Properties&gt;
	 */
	private static java.util.List<java.util.Properties> testGetColumnDefinitions(int p_i_columnDefinitionsNumber) {
		java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
		
		java.util.Properties o_properties = new java.util.Properties();
		o_properties.put("name", "Id");
		o_properties.put("columnType", "integer [int]");
		o_properties.put("constraints", "NOT NULL;PRIMARY KEY;AUTO_INCREMENT");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "UUID");
		o_properties.put("columnType", "text [36]");
		o_properties.put("constraints", "NOT NULL;UNIQUE");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "ShortText");
		o_properties.put("columnType", "text [255]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Text");
		o_properties.put("columnType", "text");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "SmallInt");
		o_properties.put("columnType", "integer [small]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Int");
		o_properties.put("columnType", "integer [int]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "BigInt");
		o_properties.put("columnType", "integer [big]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "DateTime");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "CURRENT_TIMESTAMP");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Date");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NOT NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "2020-04-06 08:10:12");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Time");
		o_properties.put("columnType", "time");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "LocalDateTime");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "CURRENT_TIMESTAMP");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "LocalDate");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "LocalTime");
		o_properties.put("columnType", "time");
		o_properties.put("constraints", "DEFAULT");
		o_properties.put("constraintDefaultValue", "12:24:46");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "DoubleCol");
		o_properties.put("columnType", "double");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Decimal");
		o_properties.put("columnType", "decimal");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Bool");
		o_properties.put("columnType", "bool");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		if (p_i_columnDefinitionsNumber == 1) {
			o_properties = new java.util.Properties();
			o_properties.put("name", "Text2");
			o_properties.put("columnType", "text [36]");
			o_properties.put("constraints", "NULL;DEFAULT");
			o_properties.put("constraintDefaultValue", "Das ist das Haus vom Nikolaus");
			a_columnsDefinition.add(o_properties);

			o_properties = new java.util.Properties();
			o_properties.put("name", "ShortText2");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);
		} else if (p_i_columnDefinitionsNumber == 2) {
			o_properties = new java.util.Properties();
			o_properties.put("name", "Text2Changed");
			o_properties.put("columnType", "text [36]");
			o_properties.put("constraints", "NULL;DEFAULT");
			o_properties.put("constraintDefaultValue", "Das ist das Haus vom Nikolaus");
			a_columnsDefinition.add(o_properties);

			o_properties = new java.util.Properties();
			o_properties.put("name", "ShortText2");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);
		} else if (p_i_columnDefinitionsNumber == 3) {
			o_properties = new java.util.Properties();
			o_properties.put("name", "Text2Changed");
			o_properties.put("columnType", "text [36]");
			o_properties.put("constraints", "NULL;DEFAULT");
			o_properties.put("constraintDefaultValue", "Das ist das Haus vom Nikolaus");
			a_columnsDefinition.add(o_properties);
		}
		
		return a_columnsDefinition;
	}
}
