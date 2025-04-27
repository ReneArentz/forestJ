module net.forestany.forestj.sandbox {
	requires transitive net.forestany.forestj;
	requires transitive net.forestany.forestj.lib.ai;
	requires transitive net.forestany.forestj.lib.net;
	requires transitive net.forestany.forestj.lib.net.ftp;
	requires transitive net.forestany.forestj.lib.net.sftp;
	requires transitive net.forestany.forestj.lib.net.mail;
	
	requires java.desktop; // needed for java.awt.image
}