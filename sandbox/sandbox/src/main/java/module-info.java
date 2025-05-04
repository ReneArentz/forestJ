module net.forestany.forestj.sandbox {
	requires transitive net.forestany.forestj;
	requires transitive net.forestany.forestj.lib.ai;
	requires transitive net.forestany.forestj.lib.net;
	requires transitive net.forestany.forestj.lib.net.ftp;
	requires transitive net.forestany.forestj.lib.net.sftp;
	requires transitive net.forestany.forestj.lib.net.mail;
	requires transitive net.forestany.forestj.lib.net.sql.pool;
	requires transitive net.forestany.forestj.lib.test.nettest;
	
	requires transitive net.forestany.forestj.lib.sql.mariadb;
	requires transitive net.forestany.forestj.lib.sql.pool;

	
	requires java.desktop; // needed for java.awt.image
	
	opens net.forestany.forestj.sandbox.util.net.https to net.forestany.forestj, net.forestany.forestj.lib.sql;
}