/**
 * Module definition
 */
module net.forestany.forestj {
	requires transitive java.logging;
	
	exports net.forestany.forestj.lib;
	exports net.forestany.forestj.lib.io;
	exports net.forestany.forestj.lib.sqlcore;
	exports net.forestany.forestj.lib.net.ftpcore;
}