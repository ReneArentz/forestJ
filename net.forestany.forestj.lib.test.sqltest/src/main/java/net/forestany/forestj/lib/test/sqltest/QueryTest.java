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
					"DROP TABLE `sys_forestj_testddl2`",
					"CREATE TABLE `sys_forestj_short_table` (`Key` INT(10) NOT NULL PRIMARY KEY, `Text` VARCHAR(255) NULL, `Number` INT(10) NULL)",
					"INSERT INTO `sys_forestj_short_table` (`sys_forestj_short_table`.`Key`, `sys_forestj_short_table`.`Text`, `sys_forestj_short_table`.`Number`) VALUES (1, 'Lorem', 10),(2, 'ipsum', 20),(3, 'dolor', 30),(4, 'sit', 40),(5, 'amet', 50),(6, 'consectetur', 60),(7, 'adipiscing', 70),(8, 'elit', 80),(9, 'sed', 90),(10, 'do', -10),(11, 'eiusmod', -20),(12, 'tempor', -30),(13, 'incididunt', -40),(14, 'ut', -50),(15, 'labore', -60),(16, 'et', -70),(17, 'dolore', -80),(18, 'magna', -90)",
					"SELECT * FROM `sys_forestj_short_table`",
					"DROP TABLE `sys_forestj_short_table`",
					"CREATE TABLE `sys_forestj_financial_entry` (`Id` INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, `UUID` VARCHAR(36) NOT NULL UNIQUE, `From` INT(10) NULL, `To` INT(10) NULL, `Amount` DECIMAL(38,9) NULL, `Created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)",
					"INSERT INTO `sys_forestj_financial_entry` (`sys_forestj_financial_entry`.`UUID`, `sys_forestj_financial_entry`.`From`, `sys_forestj_financial_entry`.`To`, `sys_forestj_financial_entry`.`Amount`) VALUES ('09941105-00ca-d2b0-a553-7ed16f611a18', 600, 300, 509.410001),('67782667-42b8-4178-e14d-037ddc8f7d67', 600, 100, 6689.250001),('990153d3-2a12-c6a2-1367-49b321461437', 200, 600, 7553.490001),('e8357ecf-01bf-0984-93f1-fa03b819b101', 200, 100, 8362.140001),('82e5c985-e088-0851-3106-4464d9e2266c', 400, 300, 3152.880001),('f8f495c4-ba5a-25e5-85aa-e76c025d6dd2', 200, 500, 5352.790001),('c6d6ef9d-aab7-527b-33a2-50beae79db89', 400, 100, 2788.820001),('09ae2767-5a7e-010a-970a-06b27969cc52', 100, 400, 1733.300001),('2bf0c01d-c92f-f677-6bed-6fa83b5cca59', 600, 400, 8291.340001),('4b529347-3ab9-ea17-6fd9-7775cabe4ab2', 500, 600, 2879.270001),('9169ccfc-2e06-b52a-1760-44aafd40bd01', 600, 300, 200.980001),('80f1b819-7590-4939-a09c-1b220db652a9', 600, 500, 1966.590001),('1b660d75-d4db-fd14-cedd-3743a0cef3b1', 300, 600, 5605.590001),('d714ca78-a65e-72d4-f73c-4514680b1f29', 100, 600, 3437.120001),('4358765b-84f6-fc1d-be9f-b5eb3343873b', 100, 500, 9200.050001),('516cd463-6bb0-327b-9c9c-9704776d8049', 300, 600, 8414.740001),('75a4e2ba-2d80-af7a-37bb-43124fd21464', 200, 600, 328.630001),('7f27e417-992d-ae59-35f2-f78d57dc6329', 200, 600, 8873.380001),('e10d996f-17d6-1f03-d394-20d7c7f8550a', 500, 300, 702.200001),('fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505', 100, 600, 1459.780001),('0d373136-3d34-150b-8451-1c955a9abfde', 600, 400, 7762.180001),('8e10e09b-1469-ef75-e952-55cf657943bc', 400, 100, 7511.230001),('d101df29-ce3b-3761-b923-afed90cf41fa', 600, 500, 1739.100001),('ea81b084-0cb9-eb31-2ace-74c0b9d0a621', 200, 100, 4816.670001),('e78b0d1a-ae46-8258-3e56-cc4d655678e5', 200, 400, 8445.740001),('e8a992c9-d375-e7df-2cff-e3aa122c9c8c', 400, 300, 5194.580001),('3ec321bb-40a5-9e63-a9bf-8881b6b8aab2', 500, 300, 8148.840001),('c944c71a-301c-db5a-4703-3f02110fa4a7', 300, 500, 5203.180001),('b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d', 300, 100, 5023.310001),('028d9407-eafd-3dcc-74b3-37199970c73a', 500, 600, 7242.860001),('38196216-9410-a2ed-111b-c5a112ba0e5b', 600, 400, 6931.270001),('c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d', 400, 100, 2854.300001),('4cefae12-ac31-1e18-ce3b-15cf3ec49c1e', 300, 500, 6592.230001),('aea7470c-0413-c805-a1b8-518ae8241b70', 400, 600, 8862.900001),('51b02978-81a6-3573-653f-a7a03bdfacf9', 400, 300, 7011.320001),('50840402-b8b2-0b39-1f71-d0bcc06540ed', 500, 100, 9443.890001),('61fa7920-a078-4570-7ac6-e78e9895e96b', 500, 100, 7688.160001),('effa7253-ace3-697e-3bbf-a68b9ae1d7a4', 300, 100, 337.020001),('909ca161-7cbe-48f3-2aca-a852792e2fc4', 600, 500, 6080.080001),('485f9318-c453-63c7-78dc-663fdb055eb9', 500, 200, 7093.540001),('e1230f48-20ed-d4be-7ed3-d3b418e1e336', 500, 100, 7880.560001),('02f87fb8-e163-bf34-2f03-f998c1d5c16d', 600, 400, 1706.250001),('0b8003d5-2cd9-dcfe-6848-8f9cf87e3529', 300, 600, 2144.770001),('c7ecb642-995c-bdd2-38c6-e21f2d66a0c9', 400, 600, 4028.290001),('34305445-824f-0bda-1692-a710a3443f36', 400, 500, 4053.070001),('57226446-e3f1-0753-9063-669c12dcfa98', 200, 100, 9894.970001),('96342937-80b3-6848-ed77-ea80bb63bfba', 300, 200, 1905.690001),('49749252-fa6f-70db-ff40-7f443cf28c4f', 600, 300, 9441.830001),('3efeb7ad-2ce3-b89e-ef80-6db725eb1890', 600, 400, 504.010001),('48ff63d3-6ef4-b86c-02c5-926ebcdfa679', 600, 100, 8739.020001),('3c49ad61-04b2-b226-1192-eba11aba19f1', 500, 600, 3569.920001),('5a37ab27-ee96-9145-e6c9-54e33bfdae40', 500, 200, 1259.400001),('598c36cf-ecda-1a3f-bf6b-a19de7b69894', 100, 300, 2443.120001),('089fc1f7-d918-5989-b2e0-33cda82bcca8', 300, 100, 42.230001),('1ab272fb-1221-ccf1-87ba-48e9645aa044', 200, 100, 9339.400001),('c978de1f-43ee-a9ab-25df-d1a5899180d4', 600, 200, 6504.800001),('657c0fb8-46af-c1e1-203f-1e76ebb62dea', 400, 200, 5962.030001),('ea2160fe-c590-e4e6-5a0f-82d04dd70319', 300, 200, 957.710001),('d9a69bde-4e6e-0945-38db-a9a71208750f', 400, 300, 2823.170001),('989ddf78-2d8f-f7e5-fb6c-c69944d8e6da', 300, 500, 5139.270001),('e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b', 500, 200, 2287.390001),('2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3', 200, 600, 9778.640001),('5246a23f-2479-07ad-aac3-add3160bbca8', 500, 600, 8522.110001),('229e2868-e2bf-4111-2dc9-1ee49ed440b8', 600, 100, 3044.040001),('0163c14b-357d-d9df-6738-7a51559e1f15', 600, 300, 6057.640001),('d62fcce1-63dc-db55-8837-5385cf4c9c2d', 200, 300, 8690.590001),('acc672dd-a797-6aab-214e-cc2067a852cf', 400, 300, 5147.290001),('b8399ea0-4cf0-223e-d608-6da2f8404205', 500, 600, 565.070001),('ca3f8884-0b25-c09e-d659-6175cde664a1', 200, 100, 6586.560001),('94d7e52b-7d18-79bc-d8d2-d412fe9c6761', 500, 200, 8321.190001),('033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0', 400, 300, 452.110001),('bebd0447-ee4f-6e03-66ea-f43b5d30a095', 500, 100, 7428.190001),('aae48129-88f7-ecea-5492-d9e7bb6cfb7c', 100, 200, 7109.210001),('31780780-ae1f-401d-d5b3-cbe0f84c42b7', 200, 300, 4965.820001),('c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51', 600, 100, 4472.010001),('7baf282f-9d60-69b1-e7c5-48f4cc10c55c', 300, 100, 4585.330001)",
					"SELECT * FROM `sys_forestj_financial_entry`",
					"DROP TABLE `sys_forestj_financial_entry`"
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
					"DROP TABLE `sys_forestj_testddl2`",
					"CREATE TABLE `sys_forestj_short_table` (`Key` integer NOT NULL PRIMARY KEY, `Text` varchar(255) NULL, `Number` integer NULL)",
					"INSERT INTO `sys_forestj_short_table` (`Key`, `Text`, `Number`) VALUES (1, 'Lorem', 10),(2, 'ipsum', 20),(3, 'dolor', 30),(4, 'sit', 40),(5, 'amet', 50),(6, 'consectetur', 60),(7, 'adipiscing', 70),(8, 'elit', 80),(9, 'sed', 90),(10, 'do', -10),(11, 'eiusmod', -20),(12, 'tempor', -30),(13, 'incididunt', -40),(14, 'ut', -50),(15, 'labore', -60),(16, 'et', -70),(17, 'dolore', -80),(18, 'magna', -90)",
					"SELECT * FROM `sys_forestj_short_table`",
					"DROP TABLE `sys_forestj_short_table`",
					"CREATE TABLE `sys_forestj_financial_entry` (`Id` integer NOT NULL PRIMARY KEY AUTOINCREMENT, `UUID` varchar(36) NOT NULL UNIQUE, `From` integer NULL, `To` integer NULL, `Amount` decimal(38,9) NULL, `Created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP)",
					"INSERT INTO `sys_forestj_financial_entry` (`UUID`, `From`, `To`, `Amount`) VALUES ('09941105-00ca-d2b0-a553-7ed16f611a18', 600, 300, 509.410001),('67782667-42b8-4178-e14d-037ddc8f7d67', 600, 100, 6689.250001),('990153d3-2a12-c6a2-1367-49b321461437', 200, 600, 7553.490001),('e8357ecf-01bf-0984-93f1-fa03b819b101', 200, 100, 8362.140001),('82e5c985-e088-0851-3106-4464d9e2266c', 400, 300, 3152.880001),('f8f495c4-ba5a-25e5-85aa-e76c025d6dd2', 200, 500, 5352.790001),('c6d6ef9d-aab7-527b-33a2-50beae79db89', 400, 100, 2788.820001),('09ae2767-5a7e-010a-970a-06b27969cc52', 100, 400, 1733.300001),('2bf0c01d-c92f-f677-6bed-6fa83b5cca59', 600, 400, 8291.340001),('4b529347-3ab9-ea17-6fd9-7775cabe4ab2', 500, 600, 2879.270001),('9169ccfc-2e06-b52a-1760-44aafd40bd01', 600, 300, 200.980001),('80f1b819-7590-4939-a09c-1b220db652a9', 600, 500, 1966.590001),('1b660d75-d4db-fd14-cedd-3743a0cef3b1', 300, 600, 5605.590001),('d714ca78-a65e-72d4-f73c-4514680b1f29', 100, 600, 3437.120001),('4358765b-84f6-fc1d-be9f-b5eb3343873b', 100, 500, 9200.050001),('516cd463-6bb0-327b-9c9c-9704776d8049', 300, 600, 8414.740001),('75a4e2ba-2d80-af7a-37bb-43124fd21464', 200, 600, 328.630001),('7f27e417-992d-ae59-35f2-f78d57dc6329', 200, 600, 8873.380001),('e10d996f-17d6-1f03-d394-20d7c7f8550a', 500, 300, 702.200001),('fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505', 100, 600, 1459.780001),('0d373136-3d34-150b-8451-1c955a9abfde', 600, 400, 7762.180001),('8e10e09b-1469-ef75-e952-55cf657943bc', 400, 100, 7511.230001),('d101df29-ce3b-3761-b923-afed90cf41fa', 600, 500, 1739.100001),('ea81b084-0cb9-eb31-2ace-74c0b9d0a621', 200, 100, 4816.670001),('e78b0d1a-ae46-8258-3e56-cc4d655678e5', 200, 400, 8445.740001),('e8a992c9-d375-e7df-2cff-e3aa122c9c8c', 400, 300, 5194.580001),('3ec321bb-40a5-9e63-a9bf-8881b6b8aab2', 500, 300, 8148.840001),('c944c71a-301c-db5a-4703-3f02110fa4a7', 300, 500, 5203.180001),('b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d', 300, 100, 5023.310001),('028d9407-eafd-3dcc-74b3-37199970c73a', 500, 600, 7242.860001),('38196216-9410-a2ed-111b-c5a112ba0e5b', 600, 400, 6931.270001),('c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d', 400, 100, 2854.300001),('4cefae12-ac31-1e18-ce3b-15cf3ec49c1e', 300, 500, 6592.230001),('aea7470c-0413-c805-a1b8-518ae8241b70', 400, 600, 8862.900001),('51b02978-81a6-3573-653f-a7a03bdfacf9', 400, 300, 7011.320001),('50840402-b8b2-0b39-1f71-d0bcc06540ed', 500, 100, 9443.890001),('61fa7920-a078-4570-7ac6-e78e9895e96b', 500, 100, 7688.160001),('effa7253-ace3-697e-3bbf-a68b9ae1d7a4', 300, 100, 337.020001),('909ca161-7cbe-48f3-2aca-a852792e2fc4', 600, 500, 6080.080001),('485f9318-c453-63c7-78dc-663fdb055eb9', 500, 200, 7093.540001),('e1230f48-20ed-d4be-7ed3-d3b418e1e336', 500, 100, 7880.560001),('02f87fb8-e163-bf34-2f03-f998c1d5c16d', 600, 400, 1706.250001),('0b8003d5-2cd9-dcfe-6848-8f9cf87e3529', 300, 600, 2144.770001),('c7ecb642-995c-bdd2-38c6-e21f2d66a0c9', 400, 600, 4028.290001),('34305445-824f-0bda-1692-a710a3443f36', 400, 500, 4053.070001),('57226446-e3f1-0753-9063-669c12dcfa98', 200, 100, 9894.970001),('96342937-80b3-6848-ed77-ea80bb63bfba', 300, 200, 1905.690001),('49749252-fa6f-70db-ff40-7f443cf28c4f', 600, 300, 9441.830001),('3efeb7ad-2ce3-b89e-ef80-6db725eb1890', 600, 400, 504.010001),('48ff63d3-6ef4-b86c-02c5-926ebcdfa679', 600, 100, 8739.020001),('3c49ad61-04b2-b226-1192-eba11aba19f1', 500, 600, 3569.920001),('5a37ab27-ee96-9145-e6c9-54e33bfdae40', 500, 200, 1259.400001),('598c36cf-ecda-1a3f-bf6b-a19de7b69894', 100, 300, 2443.120001),('089fc1f7-d918-5989-b2e0-33cda82bcca8', 300, 100, 42.230001),('1ab272fb-1221-ccf1-87ba-48e9645aa044', 200, 100, 9339.400001),('c978de1f-43ee-a9ab-25df-d1a5899180d4', 600, 200, 6504.800001),('657c0fb8-46af-c1e1-203f-1e76ebb62dea', 400, 200, 5962.030001),('ea2160fe-c590-e4e6-5a0f-82d04dd70319', 300, 200, 957.710001),('d9a69bde-4e6e-0945-38db-a9a71208750f', 400, 300, 2823.170001),('989ddf78-2d8f-f7e5-fb6c-c69944d8e6da', 300, 500, 5139.270001),('e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b', 500, 200, 2287.390001),('2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3', 200, 600, 9778.640001),('5246a23f-2479-07ad-aac3-add3160bbca8', 500, 600, 8522.110001),('229e2868-e2bf-4111-2dc9-1ee49ed440b8', 600, 100, 3044.040001),('0163c14b-357d-d9df-6738-7a51559e1f15', 600, 300, 6057.640001),('d62fcce1-63dc-db55-8837-5385cf4c9c2d', 200, 300, 8690.590001),('acc672dd-a797-6aab-214e-cc2067a852cf', 400, 300, 5147.290001),('b8399ea0-4cf0-223e-d608-6da2f8404205', 500, 600, 565.070001),('ca3f8884-0b25-c09e-d659-6175cde664a1', 200, 100, 6586.560001),('94d7e52b-7d18-79bc-d8d2-d412fe9c6761', 500, 200, 8321.190001),('033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0', 400, 300, 452.110001),('bebd0447-ee4f-6e03-66ea-f43b5d30a095', 500, 100, 7428.190001),('aae48129-88f7-ecea-5492-d9e7bb6cfb7c', 100, 200, 7109.210001),('31780780-ae1f-401d-d5b3-cbe0f84c42b7', 200, 300, 4965.820001),('c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51', 600, 100, 4472.010001),('7baf282f-9d60-69b1-e7c5-48f4cc10c55c', 300, 100, 4585.330001)",
					"SELECT * FROM `sys_forestj_financial_entry`",
					"DROP TABLE `sys_forestj_financial_entry`"
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
					"DROP TABLE [sys_forestj_testddl2]",
					"CREATE TABLE [sys_forestj_short_table] ([Key] int NOT NULL PRIMARY KEY, [Text] nvarchar(255) NULL, [Number] int NULL)",
					"INSERT INTO [sys_forestj_short_table] ([sys_forestj_short_table].[Key], [sys_forestj_short_table].[Text], [sys_forestj_short_table].[Number]) VALUES (1, 'Lorem', 10),(2, 'ipsum', 20),(3, 'dolor', 30),(4, 'sit', 40),(5, 'amet', 50),(6, 'consectetur', 60),(7, 'adipiscing', 70),(8, 'elit', 80),(9, 'sed', 90),(10, 'do', -10),(11, 'eiusmod', -20),(12, 'tempor', -30),(13, 'incididunt', -40),(14, 'ut', -50),(15, 'labore', -60),(16, 'et', -70),(17, 'dolore', -80),(18, 'magna', -90)",
					"SELECT * FROM [sys_forestj_short_table]",
					"DROP TABLE [sys_forestj_short_table]",
					"CREATE TABLE [sys_forestj_financial_entry] ([Id] int NOT NULL PRIMARY KEY IDENTITY(1,1), [UUID] nvarchar(36) NOT NULL UNIQUE, [From] int NULL, [To] int NULL, [Amount] decimal(38,9) NULL, [Created] datetime NOT NULL DEFAULT CURRENT_TIMESTAMP)",
					"INSERT INTO [sys_forestj_financial_entry] ([sys_forestj_financial_entry].[UUID], [sys_forestj_financial_entry].[From], [sys_forestj_financial_entry].[To], [sys_forestj_financial_entry].[Amount]) VALUES ('09941105-00ca-d2b0-a553-7ed16f611a18', 600, 300, 509.410001),('67782667-42b8-4178-e14d-037ddc8f7d67', 600, 100, 6689.250001),('990153d3-2a12-c6a2-1367-49b321461437', 200, 600, 7553.490001),('e8357ecf-01bf-0984-93f1-fa03b819b101', 200, 100, 8362.140001),('82e5c985-e088-0851-3106-4464d9e2266c', 400, 300, 3152.880001),('f8f495c4-ba5a-25e5-85aa-e76c025d6dd2', 200, 500, 5352.790001),('c6d6ef9d-aab7-527b-33a2-50beae79db89', 400, 100, 2788.820001),('09ae2767-5a7e-010a-970a-06b27969cc52', 100, 400, 1733.300001),('2bf0c01d-c92f-f677-6bed-6fa83b5cca59', 600, 400, 8291.340001),('4b529347-3ab9-ea17-6fd9-7775cabe4ab2', 500, 600, 2879.270001),('9169ccfc-2e06-b52a-1760-44aafd40bd01', 600, 300, 200.980001),('80f1b819-7590-4939-a09c-1b220db652a9', 600, 500, 1966.590001),('1b660d75-d4db-fd14-cedd-3743a0cef3b1', 300, 600, 5605.590001),('d714ca78-a65e-72d4-f73c-4514680b1f29', 100, 600, 3437.120001),('4358765b-84f6-fc1d-be9f-b5eb3343873b', 100, 500, 9200.050001),('516cd463-6bb0-327b-9c9c-9704776d8049', 300, 600, 8414.740001),('75a4e2ba-2d80-af7a-37bb-43124fd21464', 200, 600, 328.630001),('7f27e417-992d-ae59-35f2-f78d57dc6329', 200, 600, 8873.380001),('e10d996f-17d6-1f03-d394-20d7c7f8550a', 500, 300, 702.200001),('fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505', 100, 600, 1459.780001),('0d373136-3d34-150b-8451-1c955a9abfde', 600, 400, 7762.180001),('8e10e09b-1469-ef75-e952-55cf657943bc', 400, 100, 7511.230001),('d101df29-ce3b-3761-b923-afed90cf41fa', 600, 500, 1739.100001),('ea81b084-0cb9-eb31-2ace-74c0b9d0a621', 200, 100, 4816.670001),('e78b0d1a-ae46-8258-3e56-cc4d655678e5', 200, 400, 8445.740001),('e8a992c9-d375-e7df-2cff-e3aa122c9c8c', 400, 300, 5194.580001),('3ec321bb-40a5-9e63-a9bf-8881b6b8aab2', 500, 300, 8148.840001),('c944c71a-301c-db5a-4703-3f02110fa4a7', 300, 500, 5203.180001),('b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d', 300, 100, 5023.310001),('028d9407-eafd-3dcc-74b3-37199970c73a', 500, 600, 7242.860001),('38196216-9410-a2ed-111b-c5a112ba0e5b', 600, 400, 6931.270001),('c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d', 400, 100, 2854.300001),('4cefae12-ac31-1e18-ce3b-15cf3ec49c1e', 300, 500, 6592.230001),('aea7470c-0413-c805-a1b8-518ae8241b70', 400, 600, 8862.900001),('51b02978-81a6-3573-653f-a7a03bdfacf9', 400, 300, 7011.320001),('50840402-b8b2-0b39-1f71-d0bcc06540ed', 500, 100, 9443.890001),('61fa7920-a078-4570-7ac6-e78e9895e96b', 500, 100, 7688.160001),('effa7253-ace3-697e-3bbf-a68b9ae1d7a4', 300, 100, 337.020001),('909ca161-7cbe-48f3-2aca-a852792e2fc4', 600, 500, 6080.080001),('485f9318-c453-63c7-78dc-663fdb055eb9', 500, 200, 7093.540001),('e1230f48-20ed-d4be-7ed3-d3b418e1e336', 500, 100, 7880.560001),('02f87fb8-e163-bf34-2f03-f998c1d5c16d', 600, 400, 1706.250001),('0b8003d5-2cd9-dcfe-6848-8f9cf87e3529', 300, 600, 2144.770001),('c7ecb642-995c-bdd2-38c6-e21f2d66a0c9', 400, 600, 4028.290001),('34305445-824f-0bda-1692-a710a3443f36', 400, 500, 4053.070001),('57226446-e3f1-0753-9063-669c12dcfa98', 200, 100, 9894.970001),('96342937-80b3-6848-ed77-ea80bb63bfba', 300, 200, 1905.690001),('49749252-fa6f-70db-ff40-7f443cf28c4f', 600, 300, 9441.830001),('3efeb7ad-2ce3-b89e-ef80-6db725eb1890', 600, 400, 504.010001),('48ff63d3-6ef4-b86c-02c5-926ebcdfa679', 600, 100, 8739.020001),('3c49ad61-04b2-b226-1192-eba11aba19f1', 500, 600, 3569.920001),('5a37ab27-ee96-9145-e6c9-54e33bfdae40', 500, 200, 1259.400001),('598c36cf-ecda-1a3f-bf6b-a19de7b69894', 100, 300, 2443.120001),('089fc1f7-d918-5989-b2e0-33cda82bcca8', 300, 100, 42.230001),('1ab272fb-1221-ccf1-87ba-48e9645aa044', 200, 100, 9339.400001),('c978de1f-43ee-a9ab-25df-d1a5899180d4', 600, 200, 6504.800001),('657c0fb8-46af-c1e1-203f-1e76ebb62dea', 400, 200, 5962.030001),('ea2160fe-c590-e4e6-5a0f-82d04dd70319', 300, 200, 957.710001),('d9a69bde-4e6e-0945-38db-a9a71208750f', 400, 300, 2823.170001),('989ddf78-2d8f-f7e5-fb6c-c69944d8e6da', 300, 500, 5139.270001),('e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b', 500, 200, 2287.390001),('2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3', 200, 600, 9778.640001),('5246a23f-2479-07ad-aac3-add3160bbca8', 500, 600, 8522.110001),('229e2868-e2bf-4111-2dc9-1ee49ed440b8', 600, 100, 3044.040001),('0163c14b-357d-d9df-6738-7a51559e1f15', 600, 300, 6057.640001),('d62fcce1-63dc-db55-8837-5385cf4c9c2d', 200, 300, 8690.590001),('acc672dd-a797-6aab-214e-cc2067a852cf', 400, 300, 5147.290001),('b8399ea0-4cf0-223e-d608-6da2f8404205', 500, 600, 565.070001),('ca3f8884-0b25-c09e-d659-6175cde664a1', 200, 100, 6586.560001),('94d7e52b-7d18-79bc-d8d2-d412fe9c6761', 500, 200, 8321.190001),('033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0', 400, 300, 452.110001),('bebd0447-ee4f-6e03-66ea-f43b5d30a095', 500, 100, 7428.190001),('aae48129-88f7-ecea-5492-d9e7bb6cfb7c', 100, 200, 7109.210001),('31780780-ae1f-401d-d5b3-cbe0f84c42b7', 200, 300, 4965.820001),('c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51', 600, 100, 4472.010001),('7baf282f-9d60-69b1-e7c5-48f4cc10c55c', 300, 100, 4585.330001)",
					"SELECT * FROM [sys_forestj_financial_entry]",
					"DROP TABLE [sys_forestj_financial_entry]"
				},
				/* PGSQL */ {
					"CREATE TABLE \"sys_forestj_testddl\" (\"Id\" integer PRIMARY KEY GENERATED by default AS IDENTITY, \"UUID\" varchar(36) NOT NULL UNIQUE, \"ShortText\" varchar(255) NULL, \"Text\" text NULL, \"SmallInt\" smallint NULL, \"Int\" integer NULL, \"BigInt\" bigint NULL, \"DateTime\" timestamp NULL DEFAULT CURRENT_TIMESTAMP, \"Date\" timestamp NOT NULL DEFAULT '2020-04-06 08:10:12', \"Time\" time NULL, \"LocalDateTime\" timestamp NULL DEFAULT CURRENT_TIMESTAMP, \"LocalDate\" timestamp NULL, \"LocalTime\" time DEFAULT '12:24:46', \"DoubleCol\" double precision NULL, \"Decimal\" decimal(38,9) NULL, \"Bool\" smallint DEFAULT 0 CHECK (\"Bool\" >= 0 AND \"Bool\" <= 1) NULL)",
					"CREATE TABLE \"sys_forestj_testddl2\" (\"Id\" integer PRIMARY KEY GENERATED by default AS IDENTITY, \"DoubleCol\" double precision NULL)",
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
					"DROP TABLE \"sys_forestj_testddl2\"",
					"CREATE TABLE \"sys_forestj_short_table\" (\"Key\" integer PRIMARY KEY GENERATED by default AS IDENTITY, \"Text\" varchar(255) NULL, \"Number\" integer NULL)",
					"INSERT INTO \"sys_forestj_short_table\" (\"Key\", \"Text\", \"Number\") VALUES (1, 'Lorem', 10),(2, 'ipsum', 20),(3, 'dolor', 30),(4, 'sit', 40),(5, 'amet', 50),(6, 'consectetur', 60),(7, 'adipiscing', 70),(8, 'elit', 80),(9, 'sed', 90),(10, 'do', -10),(11, 'eiusmod', -20),(12, 'tempor', -30),(13, 'incididunt', -40),(14, 'ut', -50),(15, 'labore', -60),(16, 'et', -70),(17, 'dolore', -80),(18, 'magna', -90)",
					"SELECT * FROM \"sys_forestj_short_table\"",
					"DROP TABLE \"sys_forestj_short_table\"",
					"CREATE TABLE \"sys_forestj_financial_entry\" (\"Id\" integer PRIMARY KEY GENERATED by default AS IDENTITY, \"UUID\" varchar(36) NOT NULL UNIQUE, \"From\" integer NULL, \"To\" integer NULL, \"Amount\" decimal(38,9) NULL, \"Created\" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP)",
					"INSERT INTO \"sys_forestj_financial_entry\" (\"UUID\", \"From\", \"To\", \"Amount\") VALUES ('09941105-00ca-d2b0-a553-7ed16f611a18', 600, 300, 509.410001),('67782667-42b8-4178-e14d-037ddc8f7d67', 600, 100, 6689.250001),('990153d3-2a12-c6a2-1367-49b321461437', 200, 600, 7553.490001),('e8357ecf-01bf-0984-93f1-fa03b819b101', 200, 100, 8362.140001),('82e5c985-e088-0851-3106-4464d9e2266c', 400, 300, 3152.880001),('f8f495c4-ba5a-25e5-85aa-e76c025d6dd2', 200, 500, 5352.790001),('c6d6ef9d-aab7-527b-33a2-50beae79db89', 400, 100, 2788.820001),('09ae2767-5a7e-010a-970a-06b27969cc52', 100, 400, 1733.300001),('2bf0c01d-c92f-f677-6bed-6fa83b5cca59', 600, 400, 8291.340001),('4b529347-3ab9-ea17-6fd9-7775cabe4ab2', 500, 600, 2879.270001),('9169ccfc-2e06-b52a-1760-44aafd40bd01', 600, 300, 200.980001),('80f1b819-7590-4939-a09c-1b220db652a9', 600, 500, 1966.590001),('1b660d75-d4db-fd14-cedd-3743a0cef3b1', 300, 600, 5605.590001),('d714ca78-a65e-72d4-f73c-4514680b1f29', 100, 600, 3437.120001),('4358765b-84f6-fc1d-be9f-b5eb3343873b', 100, 500, 9200.050001),('516cd463-6bb0-327b-9c9c-9704776d8049', 300, 600, 8414.740001),('75a4e2ba-2d80-af7a-37bb-43124fd21464', 200, 600, 328.630001),('7f27e417-992d-ae59-35f2-f78d57dc6329', 200, 600, 8873.380001),('e10d996f-17d6-1f03-d394-20d7c7f8550a', 500, 300, 702.200001),('fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505', 100, 600, 1459.780001),('0d373136-3d34-150b-8451-1c955a9abfde', 600, 400, 7762.180001),('8e10e09b-1469-ef75-e952-55cf657943bc', 400, 100, 7511.230001),('d101df29-ce3b-3761-b923-afed90cf41fa', 600, 500, 1739.100001),('ea81b084-0cb9-eb31-2ace-74c0b9d0a621', 200, 100, 4816.670001),('e78b0d1a-ae46-8258-3e56-cc4d655678e5', 200, 400, 8445.740001),('e8a992c9-d375-e7df-2cff-e3aa122c9c8c', 400, 300, 5194.580001),('3ec321bb-40a5-9e63-a9bf-8881b6b8aab2', 500, 300, 8148.840001),('c944c71a-301c-db5a-4703-3f02110fa4a7', 300, 500, 5203.180001),('b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d', 300, 100, 5023.310001),('028d9407-eafd-3dcc-74b3-37199970c73a', 500, 600, 7242.860001),('38196216-9410-a2ed-111b-c5a112ba0e5b', 600, 400, 6931.270001),('c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d', 400, 100, 2854.300001),('4cefae12-ac31-1e18-ce3b-15cf3ec49c1e', 300, 500, 6592.230001),('aea7470c-0413-c805-a1b8-518ae8241b70', 400, 600, 8862.900001),('51b02978-81a6-3573-653f-a7a03bdfacf9', 400, 300, 7011.320001),('50840402-b8b2-0b39-1f71-d0bcc06540ed', 500, 100, 9443.890001),('61fa7920-a078-4570-7ac6-e78e9895e96b', 500, 100, 7688.160001),('effa7253-ace3-697e-3bbf-a68b9ae1d7a4', 300, 100, 337.020001),('909ca161-7cbe-48f3-2aca-a852792e2fc4', 600, 500, 6080.080001),('485f9318-c453-63c7-78dc-663fdb055eb9', 500, 200, 7093.540001),('e1230f48-20ed-d4be-7ed3-d3b418e1e336', 500, 100, 7880.560001),('02f87fb8-e163-bf34-2f03-f998c1d5c16d', 600, 400, 1706.250001),('0b8003d5-2cd9-dcfe-6848-8f9cf87e3529', 300, 600, 2144.770001),('c7ecb642-995c-bdd2-38c6-e21f2d66a0c9', 400, 600, 4028.290001),('34305445-824f-0bda-1692-a710a3443f36', 400, 500, 4053.070001),('57226446-e3f1-0753-9063-669c12dcfa98', 200, 100, 9894.970001),('96342937-80b3-6848-ed77-ea80bb63bfba', 300, 200, 1905.690001),('49749252-fa6f-70db-ff40-7f443cf28c4f', 600, 300, 9441.830001),('3efeb7ad-2ce3-b89e-ef80-6db725eb1890', 600, 400, 504.010001),('48ff63d3-6ef4-b86c-02c5-926ebcdfa679', 600, 100, 8739.020001),('3c49ad61-04b2-b226-1192-eba11aba19f1', 500, 600, 3569.920001),('5a37ab27-ee96-9145-e6c9-54e33bfdae40', 500, 200, 1259.400001),('598c36cf-ecda-1a3f-bf6b-a19de7b69894', 100, 300, 2443.120001),('089fc1f7-d918-5989-b2e0-33cda82bcca8', 300, 100, 42.230001),('1ab272fb-1221-ccf1-87ba-48e9645aa044', 200, 100, 9339.400001),('c978de1f-43ee-a9ab-25df-d1a5899180d4', 600, 200, 6504.800001),('657c0fb8-46af-c1e1-203f-1e76ebb62dea', 400, 200, 5962.030001),('ea2160fe-c590-e4e6-5a0f-82d04dd70319', 300, 200, 957.710001),('d9a69bde-4e6e-0945-38db-a9a71208750f', 400, 300, 2823.170001),('989ddf78-2d8f-f7e5-fb6c-c69944d8e6da', 300, 500, 5139.270001),('e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b', 500, 200, 2287.390001),('2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3', 200, 600, 9778.640001),('5246a23f-2479-07ad-aac3-add3160bbca8', 500, 600, 8522.110001),('229e2868-e2bf-4111-2dc9-1ee49ed440b8', 600, 100, 3044.040001),('0163c14b-357d-d9df-6738-7a51559e1f15', 600, 300, 6057.640001),('d62fcce1-63dc-db55-8837-5385cf4c9c2d', 200, 300, 8690.590001),('acc672dd-a797-6aab-214e-cc2067a852cf', 400, 300, 5147.290001),('b8399ea0-4cf0-223e-d608-6da2f8404205', 500, 600, 565.070001),('ca3f8884-0b25-c09e-d659-6175cde664a1', 200, 100, 6586.560001),('94d7e52b-7d18-79bc-d8d2-d412fe9c6761', 500, 200, 8321.190001),('033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0', 400, 300, 452.110001),('bebd0447-ee4f-6e03-66ea-f43b5d30a095', 500, 100, 7428.190001),('aae48129-88f7-ecea-5492-d9e7bb6cfb7c', 100, 200, 7109.210001),('31780780-ae1f-401d-d5b3-cbe0f84c42b7', 200, 300, 4965.820001),('c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51', 600, 100, 4472.010001),('7baf282f-9d60-69b1-e7c5-48f4cc10c55c', 300, 100, 4585.330001)",
					"SELECT * FROM \"sys_forestj_financial_entry\"",
					"DROP TABLE \"sys_forestj_financial_entry\""
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
					"DROP TABLE \"sys_forestj_testddl2\"",
					"CREATE TABLE \"sys_forestj_short_table\" (\"Key\" NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY, \"Text\" VARCHAR2(255) NULL, \"Number\" NUMBER(10) NULL)",
					"INSERT INTO \"sys_forestj_short_table\" (\"Key\", \"Text\", \"Number\") VALUES (1, 'Lorem', 10),(2, 'ipsum', 20),(3, 'dolor', 30),(4, 'sit', 40),(5, 'amet', 50),(6, 'consectetur', 60),(7, 'adipiscing', 70),(8, 'elit', 80),(9, 'sed', 90),(10, 'do', -10),(11, 'eiusmod', -20),(12, 'tempor', -30),(13, 'incididunt', -40),(14, 'ut', -50),(15, 'labore', -60),(16, 'et', -70),(17, 'dolore', -80),(18, 'magna', -90)",
					"SELECT * FROM \"sys_forestj_short_table\"",
					"DROP TABLE \"sys_forestj_short_table\"",
					"CREATE TABLE \"sys_forestj_financial_entry\" (\"Id\" NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY, \"UUID\" VARCHAR2(36) NOT NULL UNIQUE, \"From\" NUMBER(10) NULL, \"To\" NUMBER(10) NULL, \"Amount\" NUMBER(38,9) NULL, \"Created\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)",
					"INSERT INTO \"sys_forestj_financial_entry\" (\"UUID\", \"From\", \"To\", \"Amount\") VALUES ('09941105-00ca-d2b0-a553-7ed16f611a18', 600, 300, 509.410001),('67782667-42b8-4178-e14d-037ddc8f7d67', 600, 100, 6689.250001),('990153d3-2a12-c6a2-1367-49b321461437', 200, 600, 7553.490001),('e8357ecf-01bf-0984-93f1-fa03b819b101', 200, 100, 8362.140001),('82e5c985-e088-0851-3106-4464d9e2266c', 400, 300, 3152.880001),('f8f495c4-ba5a-25e5-85aa-e76c025d6dd2', 200, 500, 5352.790001),('c6d6ef9d-aab7-527b-33a2-50beae79db89', 400, 100, 2788.820001),('09ae2767-5a7e-010a-970a-06b27969cc52', 100, 400, 1733.300001),('2bf0c01d-c92f-f677-6bed-6fa83b5cca59', 600, 400, 8291.340001),('4b529347-3ab9-ea17-6fd9-7775cabe4ab2', 500, 600, 2879.270001),('9169ccfc-2e06-b52a-1760-44aafd40bd01', 600, 300, 200.980001),('80f1b819-7590-4939-a09c-1b220db652a9', 600, 500, 1966.590001),('1b660d75-d4db-fd14-cedd-3743a0cef3b1', 300, 600, 5605.590001),('d714ca78-a65e-72d4-f73c-4514680b1f29', 100, 600, 3437.120001),('4358765b-84f6-fc1d-be9f-b5eb3343873b', 100, 500, 9200.050001),('516cd463-6bb0-327b-9c9c-9704776d8049', 300, 600, 8414.740001),('75a4e2ba-2d80-af7a-37bb-43124fd21464', 200, 600, 328.630001),('7f27e417-992d-ae59-35f2-f78d57dc6329', 200, 600, 8873.380001),('e10d996f-17d6-1f03-d394-20d7c7f8550a', 500, 300, 702.200001),('fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505', 100, 600, 1459.780001),('0d373136-3d34-150b-8451-1c955a9abfde', 600, 400, 7762.180001),('8e10e09b-1469-ef75-e952-55cf657943bc', 400, 100, 7511.230001),('d101df29-ce3b-3761-b923-afed90cf41fa', 600, 500, 1739.100001),('ea81b084-0cb9-eb31-2ace-74c0b9d0a621', 200, 100, 4816.670001),('e78b0d1a-ae46-8258-3e56-cc4d655678e5', 200, 400, 8445.740001),('e8a992c9-d375-e7df-2cff-e3aa122c9c8c', 400, 300, 5194.580001),('3ec321bb-40a5-9e63-a9bf-8881b6b8aab2', 500, 300, 8148.840001),('c944c71a-301c-db5a-4703-3f02110fa4a7', 300, 500, 5203.180001),('b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d', 300, 100, 5023.310001),('028d9407-eafd-3dcc-74b3-37199970c73a', 500, 600, 7242.860001),('38196216-9410-a2ed-111b-c5a112ba0e5b', 600, 400, 6931.270001),('c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d', 400, 100, 2854.300001),('4cefae12-ac31-1e18-ce3b-15cf3ec49c1e', 300, 500, 6592.230001),('aea7470c-0413-c805-a1b8-518ae8241b70', 400, 600, 8862.900001),('51b02978-81a6-3573-653f-a7a03bdfacf9', 400, 300, 7011.320001),('50840402-b8b2-0b39-1f71-d0bcc06540ed', 500, 100, 9443.890001),('61fa7920-a078-4570-7ac6-e78e9895e96b', 500, 100, 7688.160001),('effa7253-ace3-697e-3bbf-a68b9ae1d7a4', 300, 100, 337.020001),('909ca161-7cbe-48f3-2aca-a852792e2fc4', 600, 500, 6080.080001),('485f9318-c453-63c7-78dc-663fdb055eb9', 500, 200, 7093.540001),('e1230f48-20ed-d4be-7ed3-d3b418e1e336', 500, 100, 7880.560001),('02f87fb8-e163-bf34-2f03-f998c1d5c16d', 600, 400, 1706.250001),('0b8003d5-2cd9-dcfe-6848-8f9cf87e3529', 300, 600, 2144.770001),('c7ecb642-995c-bdd2-38c6-e21f2d66a0c9', 400, 600, 4028.290001),('34305445-824f-0bda-1692-a710a3443f36', 400, 500, 4053.070001),('57226446-e3f1-0753-9063-669c12dcfa98', 200, 100, 9894.970001),('96342937-80b3-6848-ed77-ea80bb63bfba', 300, 200, 1905.690001),('49749252-fa6f-70db-ff40-7f443cf28c4f', 600, 300, 9441.830001),('3efeb7ad-2ce3-b89e-ef80-6db725eb1890', 600, 400, 504.010001),('48ff63d3-6ef4-b86c-02c5-926ebcdfa679', 600, 100, 8739.020001),('3c49ad61-04b2-b226-1192-eba11aba19f1', 500, 600, 3569.920001),('5a37ab27-ee96-9145-e6c9-54e33bfdae40', 500, 200, 1259.400001),('598c36cf-ecda-1a3f-bf6b-a19de7b69894', 100, 300, 2443.120001),('089fc1f7-d918-5989-b2e0-33cda82bcca8', 300, 100, 42.230001),('1ab272fb-1221-ccf1-87ba-48e9645aa044', 200, 100, 9339.400001),('c978de1f-43ee-a9ab-25df-d1a5899180d4', 600, 200, 6504.800001),('657c0fb8-46af-c1e1-203f-1e76ebb62dea', 400, 200, 5962.030001),('ea2160fe-c590-e4e6-5a0f-82d04dd70319', 300, 200, 957.710001),('d9a69bde-4e6e-0945-38db-a9a71208750f', 400, 300, 2823.170001),('989ddf78-2d8f-f7e5-fb6c-c69944d8e6da', 300, 500, 5139.270001),('e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b', 500, 200, 2287.390001),('2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3', 200, 600, 9778.640001),('5246a23f-2479-07ad-aac3-add3160bbca8', 500, 600, 8522.110001),('229e2868-e2bf-4111-2dc9-1ee49ed440b8', 600, 100, 3044.040001),('0163c14b-357d-d9df-6738-7a51559e1f15', 600, 300, 6057.640001),('d62fcce1-63dc-db55-8837-5385cf4c9c2d', 200, 300, 8690.590001),('acc672dd-a797-6aab-214e-cc2067a852cf', 400, 300, 5147.290001),('b8399ea0-4cf0-223e-d608-6da2f8404205', 500, 600, 565.070001),('ca3f8884-0b25-c09e-d659-6175cde664a1', 200, 100, 6586.560001),('94d7e52b-7d18-79bc-d8d2-d412fe9c6761', 500, 200, 8321.190001),('033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0', 400, 300, 452.110001),('bebd0447-ee4f-6e03-66ea-f43b5d30a095', 500, 100, 7428.190001),('aae48129-88f7-ecea-5492-d9e7bb6cfb7c', 100, 200, 7109.210001),('31780780-ae1f-401d-d5b3-cbe0f84c42b7', 200, 300, 4965.820001),('c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51', 600, 100, 4472.010001),('7baf282f-9d60-69b1-e7c5-48f4cc10c55c', 300, 100, 4585.330001)",
					"SELECT * FROM \"sys_forestj_financial_entry\"",
					"DROP TABLE \"sys_forestj_financial_entry\""
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
					"CREATE TABLE `sys_forestj_short_table` (`Key` INTEGER NOT NULL PRIMARY KEY, `Text` VARCHAR NULL, `Number` INTEGER NULL)",
					"INSERT INTO `sys_forestj_short_table` (`sys_forestj_short_table`.`Key`, `sys_forestj_short_table`.`Text`, `sys_forestj_short_table`.`Number`) VALUES (1, 'Lorem', 10),(2, 'ipsum', 20),(3, 'dolor', 30),(4, 'sit', 40),(5, 'amet', 50),(6, 'consectetur', 60),(7, 'adipiscing', 70),(8, 'elit', 80),(9, 'sed', 90),(10, 'do', -10),(11, 'eiusmod', -20),(12, 'tempor', -30),(13, 'incididunt', -40),(14, 'ut', -50),(15, 'labore', -60),(16, 'et', -70),(17, 'dolore', -80),(18, 'magna', -90)",
					"SELECT * FROM `sys_forestj_short_table`",
					"DROP TABLE `sys_forestj_short_table`",
					"CREATE TABLE `sys_forestj_financial_entry` (`Id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, `UUID` VARCHAR NOT NULL UNIQUE, `From` INTEGER NULL, `To` INTEGER NULL, `Amount` DECIMAL NULL, `Created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)",
					"INSERT INTO `sys_forestj_financial_entry` (`sys_forestj_financial_entry`.`UUID`, `sys_forestj_financial_entry`.`From`, `sys_forestj_financial_entry`.`To`, `sys_forestj_financial_entry`.`Amount`) VALUES ('09941105-00ca-d2b0-a553-7ed16f611a18', 600, 300, 509.410001),('67782667-42b8-4178-e14d-037ddc8f7d67', 600, 100, 6689.250001),('990153d3-2a12-c6a2-1367-49b321461437', 200, 600, 7553.490001),('e8357ecf-01bf-0984-93f1-fa03b819b101', 200, 100, 8362.140001),('82e5c985-e088-0851-3106-4464d9e2266c', 400, 300, 3152.880001),('f8f495c4-ba5a-25e5-85aa-e76c025d6dd2', 200, 500, 5352.790001),('c6d6ef9d-aab7-527b-33a2-50beae79db89', 400, 100, 2788.820001),('09ae2767-5a7e-010a-970a-06b27969cc52', 100, 400, 1733.300001),('2bf0c01d-c92f-f677-6bed-6fa83b5cca59', 600, 400, 8291.340001),('4b529347-3ab9-ea17-6fd9-7775cabe4ab2', 500, 600, 2879.270001),('9169ccfc-2e06-b52a-1760-44aafd40bd01', 600, 300, 200.980001),('80f1b819-7590-4939-a09c-1b220db652a9', 600, 500, 1966.590001),('1b660d75-d4db-fd14-cedd-3743a0cef3b1', 300, 600, 5605.590001),('d714ca78-a65e-72d4-f73c-4514680b1f29', 100, 600, 3437.120001),('4358765b-84f6-fc1d-be9f-b5eb3343873b', 100, 500, 9200.050001),('516cd463-6bb0-327b-9c9c-9704776d8049', 300, 600, 8414.740001),('75a4e2ba-2d80-af7a-37bb-43124fd21464', 200, 600, 328.630001),('7f27e417-992d-ae59-35f2-f78d57dc6329', 200, 600, 8873.380001),('e10d996f-17d6-1f03-d394-20d7c7f8550a', 500, 300, 702.200001),('fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505', 100, 600, 1459.780001),('0d373136-3d34-150b-8451-1c955a9abfde', 600, 400, 7762.180001),('8e10e09b-1469-ef75-e952-55cf657943bc', 400, 100, 7511.230001),('d101df29-ce3b-3761-b923-afed90cf41fa', 600, 500, 1739.100001),('ea81b084-0cb9-eb31-2ace-74c0b9d0a621', 200, 100, 4816.670001),('e78b0d1a-ae46-8258-3e56-cc4d655678e5', 200, 400, 8445.740001),('e8a992c9-d375-e7df-2cff-e3aa122c9c8c', 400, 300, 5194.580001),('3ec321bb-40a5-9e63-a9bf-8881b6b8aab2', 500, 300, 8148.840001),('c944c71a-301c-db5a-4703-3f02110fa4a7', 300, 500, 5203.180001),('b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d', 300, 100, 5023.310001),('028d9407-eafd-3dcc-74b3-37199970c73a', 500, 600, 7242.860001),('38196216-9410-a2ed-111b-c5a112ba0e5b', 600, 400, 6931.270001),('c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d', 400, 100, 2854.300001),('4cefae12-ac31-1e18-ce3b-15cf3ec49c1e', 300, 500, 6592.230001),('aea7470c-0413-c805-a1b8-518ae8241b70', 400, 600, 8862.900001),('51b02978-81a6-3573-653f-a7a03bdfacf9', 400, 300, 7011.320001),('50840402-b8b2-0b39-1f71-d0bcc06540ed', 500, 100, 9443.890001),('61fa7920-a078-4570-7ac6-e78e9895e96b', 500, 100, 7688.160001),('effa7253-ace3-697e-3bbf-a68b9ae1d7a4', 300, 100, 337.020001),('909ca161-7cbe-48f3-2aca-a852792e2fc4', 600, 500, 6080.080001),('485f9318-c453-63c7-78dc-663fdb055eb9', 500, 200, 7093.540001),('e1230f48-20ed-d4be-7ed3-d3b418e1e336', 500, 100, 7880.560001),('02f87fb8-e163-bf34-2f03-f998c1d5c16d', 600, 400, 1706.250001),('0b8003d5-2cd9-dcfe-6848-8f9cf87e3529', 300, 600, 2144.770001),('c7ecb642-995c-bdd2-38c6-e21f2d66a0c9', 400, 600, 4028.290001),('34305445-824f-0bda-1692-a710a3443f36', 400, 500, 4053.070001),('57226446-e3f1-0753-9063-669c12dcfa98', 200, 100, 9894.970001),('96342937-80b3-6848-ed77-ea80bb63bfba', 300, 200, 1905.690001),('49749252-fa6f-70db-ff40-7f443cf28c4f', 600, 300, 9441.830001),('3efeb7ad-2ce3-b89e-ef80-6db725eb1890', 600, 400, 504.010001),('48ff63d3-6ef4-b86c-02c5-926ebcdfa679', 600, 100, 8739.020001),('3c49ad61-04b2-b226-1192-eba11aba19f1', 500, 600, 3569.920001),('5a37ab27-ee96-9145-e6c9-54e33bfdae40', 500, 200, 1259.400001),('598c36cf-ecda-1a3f-bf6b-a19de7b69894', 100, 300, 2443.120001),('089fc1f7-d918-5989-b2e0-33cda82bcca8', 300, 100, 42.230001),('1ab272fb-1221-ccf1-87ba-48e9645aa044', 200, 100, 9339.400001),('c978de1f-43ee-a9ab-25df-d1a5899180d4', 600, 200, 6504.800001),('657c0fb8-46af-c1e1-203f-1e76ebb62dea', 400, 200, 5962.030001),('ea2160fe-c590-e4e6-5a0f-82d04dd70319', 300, 200, 957.710001),('d9a69bde-4e6e-0945-38db-a9a71208750f', 400, 300, 2823.170001),('989ddf78-2d8f-f7e5-fb6c-c69944d8e6da', 300, 500, 5139.270001),('e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b', 500, 200, 2287.390001),('2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3', 200, 600, 9778.640001),('5246a23f-2479-07ad-aac3-add3160bbca8', 500, 600, 8522.110001),('229e2868-e2bf-4111-2dc9-1ee49ed440b8', 600, 100, 3044.040001),('0163c14b-357d-d9df-6738-7a51559e1f15', 600, 300, 6057.640001),('d62fcce1-63dc-db55-8837-5385cf4c9c2d', 200, 300, 8690.590001),('acc672dd-a797-6aab-214e-cc2067a852cf', 400, 300, 5147.290001),('b8399ea0-4cf0-223e-d608-6da2f8404205', 500, 600, 565.070001),('ca3f8884-0b25-c09e-d659-6175cde664a1', 200, 100, 6586.560001),('94d7e52b-7d18-79bc-d8d2-d412fe9c6761', 500, 200, 8321.190001),('033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0', 400, 300, 452.110001),('bebd0447-ee4f-6e03-66ea-f43b5d30a095', 500, 100, 7428.190001),('aae48129-88f7-ecea-5492-d9e7bb6cfb7c', 100, 200, 7109.210001),('31780780-ae1f-401d-d5b3-cbe0f84c42b7', 200, 300, 4965.820001),('c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51', 600, 100, 4472.010001),('7baf282f-9d60-69b1-e7c5-48f4cc10c55c', 300, 100, 4585.330001)",
					"SELECT * FROM `sys_forestj_financial_entry`",
					"DROP TABLE `sys_forestj_financial_entry`",
					"SELECT `sys_forestj_products`.`SupplierID`, `sys_forestj_products`.`CategoryID`, `sys_forestj_categories`.`CategoryName`, `sys_forestj_categories`.`Description`, `sys_forestj_products`.`ProductID`, `sys_forestj_products`.`ProductName`, `sys_forestj_products`.`Unit`, `sys_forestj_products`.`Price` FROM `sys_forestj_products` INNER JOIN `sys_forestj_categories` ON `sys_forestj_products`.`CategoryID` = `sys_forestj_categories`.`CategoryID` WHERE `sys_forestj_products`.`ProductID` > 50 AND `sys_forestj_categories`.`CategoryID` > 3 ORDER BY `sys_forestj_products`.`SupplierID` ASC, `sys_forestj_categories`.`CategoryName` ASC LIMIT 0, 50",
					"SELECT `sys_forestj_products`.`SupplierID`, COUNT(`sys_forestj_products`.`ProductID`), `sys_forestj_products`.`ProductName`, `sys_forestj_products`.`Unit`, MAX(`sys_forestj_products`.`Price`) FROM `sys_forestj_products` WHERE `sys_forestj_products`.`SupplierID` < 100 GROUP BY `sys_forestj_products`.`SupplierID` HAVING MAX(`sys_forestj_products`.`Price`) > 50.0 ORDER BY COUNT(`sys_forestj_products`.`ProductID`) ASC, MAX(`sys_forestj_products`.`Price`) ASC LIMIT 0, 50",
					"SELECT `sys_forestj_products`.`SupplierID`, `sys_forestj_products`.`CategoryID`, `sys_forestj_categories`.`CategoryName`, `sys_forestj_categories`.`Description`, COUNT(`sys_forestj_products`.`ProductID`), `sys_forestj_products`.`ProductName`, `sys_forestj_products`.`Unit`, MIN(`sys_forestj_products`.`Price`) FROM `sys_forestj_products` INNER JOIN `sys_forestj_categories` ON `sys_forestj_products`.`CategoryID` = `sys_forestj_categories`.`CategoryID` WHERE `sys_forestj_products`.`SupplierID` < 50 GROUP BY `sys_forestj_products`.`SupplierID` HAVING MIN(`sys_forestj_products`.`Price`) > 20.0 AND COUNT(`sys_forestj_products`.`ProductID`) > 1 ORDER BY COUNT(`sys_forestj_products`.`ProductID`) DESC, `sys_forestj_products`.`SupplierID` ASC LIMIT 0, 50"
				}
			};
			
			net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
			
			int i_amountQueries = 31;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_amountQueries = 34;
			} else {
				i_amountQueries = 31;
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
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_D, 123, ">=", false, "OR") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect,  new net.forestany.forestj.lib.sql.Column(o_querySelect, "SmallInt"), java.lang.Short.valueOf("25353"), ">", false, "AND") );
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
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_F, 123, ">=", false, "OR") );
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect,  new net.forestany.forestj.lib.sql.Column(o_querySelect, "SmallInt"), java.lang.Short.valueOf("25353"), ">", false, "AND") );
				/* #### GroupBy ############################################################################ */
				o_querySelect.getQuery().a_groupBy.add(column_A);
				o_querySelect.getQuery().a_groupBy.add(column_C);
				o_querySelect.getQuery().a_groupBy.add(column_D);
				o_querySelect.getQuery().a_groupBy.add(column_E);
				/* #### Having ############################################################################ */
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_D, 456.f, "<=", false, "", true) );
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_A, "Trew", "=", false, "AND") );
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_C, o_localDate, "<>", false, "AND", false, true) );
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
			o_queryUpdate.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryUpdate, new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "SmallInt"), 123, ">=", false, "OR") );
			o_queryUpdate.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryUpdate, new net.forestany.forestj.lib.sql.Column(o_queryUpdate, "DateTime"), o_dateTime, ">=", false, "AND") );
			
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
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_B, o_date, ">=", false, "OR") );
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_C, o_time, ">", false, "AND") );
			
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
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_B, o_localDate, ">=", false, "OR") );
			o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_C, o_localTime, ">", false, "AND") );
			
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
			o_queryDelete.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryDelete, new net.forestany.forestj.lib.sql.Column(o_queryDelete, "SmallInt"), 32.45f, ">=", false, "OR") );
			o_queryDelete.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_queryDelete, new net.forestany.forestj.lib.sql.Column(o_queryDelete, "DateTime"), o_dateTime, ">", false, "AND") );
			
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
			/* #### ######  ############################################################################ */
			/* #### CREATE  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_short_table");
			
			/* #### Columns ############################################################################ */
			java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_properties = new java.util.Properties();
			o_properties.put("name", "Key");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NOT NULL;PRIMARY KEY");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "Text");
			o_properties.put("columnType", "text [255]");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);

			o_properties = new java.util.Properties();
			o_properties.put("name", "Number");
			o_properties.put("columnType", "integer [int]");
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
			/* #### ######  ############################################################################ */
			/* #### INSERT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_short_table");
			/* #### Rows ############################################################################ */
			
			java.util.List<net.forestany.forestj.lib.sql.ColumnValue> a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 1) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "Lorem") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 10) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 2) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "ipsum") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 20) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 3) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "dolor") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 30) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 4) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "sit") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 40) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 5) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "amet") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 50) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 6) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "consectetur") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 60) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 7) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "adipiscing") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 70) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 8) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "elit") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 80) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 9) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "sed") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), 90) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 10) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "do") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -10) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 11) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "eiusmod") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -20) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 12) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "tempor") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -30) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 13) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "incididunt") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -40) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 14) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "ut") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -50) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 15) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "labore") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -60) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 16) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "et") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -70) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 17) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "dolore") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -80) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Key"), 18) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), "magna") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Number"), -90) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);
				
			o_queryReturn = o_queryInsert;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### SELECT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_short_table");
			
			/* #### Columns ############################################################################ */
			o_querySelect.getQuery().a_columns.add(new net.forestany.forestj.lib.sql.Column(o_querySelect, "*"));

			o_queryReturn = o_querySelect;
		} else if (p_i_queryNumber == i_number++) {
			/* #### #### ############################################################################ */
			/* #### DROP ############################################################################ */
			/* #### #### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_short_table");
			o_queryReturn = o_queryDrop;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### CREATE  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_financial_entry");
			
			/* #### Columns ############################################################################ */
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
			o_properties.put("name", "From");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);

			o_properties = new java.util.Properties();
			o_properties.put("name", "To");
			o_properties.put("columnType", "integer [int]");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);
			
			o_properties = new java.util.Properties();
			o_properties.put("name", "Amount");
			o_properties.put("columnType", "decimal");
			o_properties.put("constraints", "NULL");
			a_columnsDefinition.add(o_properties);

			o_properties = new java.util.Properties();
			o_properties.put("name", "Created");
			o_properties.put("columnType", "datetime");
			o_properties.put("constraints", "NOT NULL;DEFAULT");
			o_properties.put("constraintDefaultValue", "CURRENT_TIMESTAMP");
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
			/* #### ######  ############################################################################ */
			/* #### INSERT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_financial_entry");
			/* #### Rows ############################################################################ */
			
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Id");

			java.util.List<net.forestany.forestj.lib.sql.ColumnValue> a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "09941105-00ca-d2b0-a553-7ed16f611a18") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(509.410001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "67782667-42b8-4178-e14d-037ddc8f7d67") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6689.250001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "990153d3-2a12-c6a2-1367-49b321461437") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7553.490001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "e8357ecf-01bf-0984-93f1-fa03b819b101") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8362.140001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "82e5c985-e088-0851-3106-4464d9e2266c") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(3152.880001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "f8f495c4-ba5a-25e5-85aa-e76c025d6dd2") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5352.790001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "c6d6ef9d-aab7-527b-33a2-50beae79db89") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2788.820001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "09ae2767-5a7e-010a-970a-06b27969cc52") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1733.300001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "2bf0c01d-c92f-f677-6bed-6fa83b5cca59") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8291.340001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "4b529347-3ab9-ea17-6fd9-7775cabe4ab2") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2879.270001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "9169ccfc-2e06-b52a-1760-44aafd40bd01") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(200.980001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "80f1b819-7590-4939-a09c-1b220db652a9") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1966.590001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "1b660d75-d4db-fd14-cedd-3743a0cef3b1") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5605.590001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "d714ca78-a65e-72d4-f73c-4514680b1f29") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(3437.120001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "4358765b-84f6-fc1d-be9f-b5eb3343873b") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(9200.050001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "516cd463-6bb0-327b-9c9c-9704776d8049") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8414.740001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "75a4e2ba-2d80-af7a-37bb-43124fd21464") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(328.630001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "7f27e417-992d-ae59-35f2-f78d57dc6329") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8873.380001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "e10d996f-17d6-1f03-d394-20d7c7f8550a") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(702.200001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "fe3a2bfc-fb58-4bd1-cd1d-6caa9ef1d505") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1459.780001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "0d373136-3d34-150b-8451-1c955a9abfde") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7762.180001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "8e10e09b-1469-ef75-e952-55cf657943bc") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7511.230001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "d101df29-ce3b-3761-b923-afed90cf41fa") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1739.100001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "ea81b084-0cb9-eb31-2ace-74c0b9d0a621") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(4816.670001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "e78b0d1a-ae46-8258-3e56-cc4d655678e5") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8445.740001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "e8a992c9-d375-e7df-2cff-e3aa122c9c8c") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5194.580001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "3ec321bb-40a5-9e63-a9bf-8881b6b8aab2") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8148.840001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "c944c71a-301c-db5a-4703-3f02110fa4a7") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5203.180001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "b1ea0bf0-a5d9-811f-3041-c0c5c0ec7d6d") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5023.310001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "028d9407-eafd-3dcc-74b3-37199970c73a") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7242.860001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "38196216-9410-a2ed-111b-c5a112ba0e5b") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6931.270001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "c7359d42-d50c-4b6f-a8a8-cbf2ed6c192d") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2854.300001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "4cefae12-ac31-1e18-ce3b-15cf3ec49c1e") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6592.230001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "aea7470c-0413-c805-a1b8-518ae8241b70") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8862.900001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "51b02978-81a6-3573-653f-a7a03bdfacf9") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7011.320001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "50840402-b8b2-0b39-1f71-d0bcc06540ed") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(9443.890001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "61fa7920-a078-4570-7ac6-e78e9895e96b") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7688.160001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "effa7253-ace3-697e-3bbf-a68b9ae1d7a4") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(337.020001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "909ca161-7cbe-48f3-2aca-a852792e2fc4") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6080.080001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "485f9318-c453-63c7-78dc-663fdb055eb9") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7093.540001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "e1230f48-20ed-d4be-7ed3-d3b418e1e336") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7880.560001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "02f87fb8-e163-bf34-2f03-f998c1d5c16d") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1706.250001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "0b8003d5-2cd9-dcfe-6848-8f9cf87e3529") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2144.770001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "c7ecb642-995c-bdd2-38c6-e21f2d66a0c9") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(4028.290001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "34305445-824f-0bda-1692-a710a3443f36") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(4053.070001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "57226446-e3f1-0753-9063-669c12dcfa98") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(9894.970001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "96342937-80b3-6848-ed77-ea80bb63bfba") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1905.690001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "49749252-fa6f-70db-ff40-7f443cf28c4f") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(9441.830001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "3efeb7ad-2ce3-b89e-ef80-6db725eb1890") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(504.010001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "48ff63d3-6ef4-b86c-02c5-926ebcdfa679") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8739.020001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "3c49ad61-04b2-b226-1192-eba11aba19f1") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(3569.920001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "5a37ab27-ee96-9145-e6c9-54e33bfdae40") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(1259.400001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "598c36cf-ecda-1a3f-bf6b-a19de7b69894") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2443.120001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "089fc1f7-d918-5989-b2e0-33cda82bcca8") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(42.230001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "1ab272fb-1221-ccf1-87ba-48e9645aa044") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(9339.400001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "c978de1f-43ee-a9ab-25df-d1a5899180d4") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6504.800001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "657c0fb8-46af-c1e1-203f-1e76ebb62dea") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5962.030001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "ea2160fe-c590-e4e6-5a0f-82d04dd70319") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(957.710001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "d9a69bde-4e6e-0945-38db-a9a71208750f") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2823.170001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "989ddf78-2d8f-f7e5-fb6c-c69944d8e6da") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5139.270001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "e13c6bd8-f8c4-2b66-bc8f-9fb5645a8f6b") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(2287.390001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "2fcad26f-fa6e-0bbf-9c8b-e1bbdc64aee3") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(9778.640001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "5246a23f-2479-07ad-aac3-add3160bbca8") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8522.110001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "229e2868-e2bf-4111-2dc9-1ee49ed440b8") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(3044.040001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "0163c14b-357d-d9df-6738-7a51559e1f15") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6057.640001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "d62fcce1-63dc-db55-8837-5385cf4c9c2d") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8690.590001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "acc672dd-a797-6aab-214e-cc2067a852cf") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(5147.290001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "b8399ea0-4cf0-223e-d608-6da2f8404205") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(565.070001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "ca3f8884-0b25-c09e-d659-6175cde664a1") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(6586.560001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "94d7e52b-7d18-79bc-d8d2-d412fe9c6761") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(8321.190001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "033cf5b4-a9a6-31b3-7fa3-84fb0f2e9bd0") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 400) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(452.110001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "bebd0447-ee4f-6e03-66ea-f43b5d30a095") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 500) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7428.190001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "aae48129-88f7-ecea-5492-d9e7bb6cfb7c") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(7109.210001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "31780780-ae1f-401d-d5b3-cbe0f84c42b7") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 200) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(4965.820001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "c2993b15-3bd8-d8c7-4ef5-7b3ab96afd51") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 600) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(4472.010001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);

			a_row = new java.util.ArrayList<net.forestany.forestj.lib.sql.ColumnValue>();
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), "7baf282f-9d60-69b1-e7c5-48f4cc10c55c") );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "From"), 300) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "To"), 100) );
			a_row.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Amount"), java.math.BigDecimal.valueOf(4585.330001)) );
			o_queryInsert.getQuery().a_multipleRecords.add(a_row);
				
			o_queryReturn = o_queryInsert;
		} else if (p_i_queryNumber == i_number++) {
			/* #### ######  ############################################################################ */
			/* #### SELECT  ############################################################################ */
			/* #### ######  ############################################################################ */
			
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelect = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.SELECT, "sys_forestj_financial_entry");
			
			/* #### Columns ############################################################################ */
			o_querySelect.getQuery().a_columns.add(new net.forestany.forestj.lib.sql.Column(o_querySelect, "*"));

			o_queryReturn = o_querySelect;
		} else if (p_i_queryNumber == i_number++) {
			/* #### #### ############################################################################ */
			/* #### DROP ############################################################################ */
			/* #### #### ############################################################################ */
						
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_financial_entry");
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
				o_querySelect.getQuery().a_where.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_I, 3, ">", false, "AND") );
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
				o_querySelect.getQuery().a_having.add( new net.forestany.forestj.lib.sql.Where(o_querySelect, column_E, 1, ">", false, "AND") );
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
