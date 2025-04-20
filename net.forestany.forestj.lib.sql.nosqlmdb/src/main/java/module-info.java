/**
 * Module definition
 */
module net.forestany.forestj.lib.sql.nosqlmdb {
	requires transitive net.forestany.forestj.lib.sql;

	requires java.sql;
	
	requires transitive org.mongodb.bson;
	requires org.mongodb.driver.core;
	requires org.mongodb.driver.sync.client;
	
	exports net.forestany.forestj.lib.sql.nosqlmdb;
}