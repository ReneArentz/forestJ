/**
 * Module definition
 */
module net.forestany.forestj.lib.sql.pool {
	requires transitive net.forestany.forestj.lib.sql;

	requires static net.forestany.forestj.lib.sql.mariadb;
	requires static net.forestany.forestj.lib.sql.mssql;
	requires static net.forestany.forestj.lib.sql.oracle;
	requires static net.forestany.forestj.lib.sql.pgsql;
	requires static net.forestany.forestj.lib.sql.sqlite;
	requires static net.forestany.forestj.lib.sql.nosqlmdb;
	
	exports net.forestany.forestj.lib.sql.pool;
}