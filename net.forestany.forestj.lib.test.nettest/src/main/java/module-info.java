/**
 * Module definition
 */
module net.forestany.forestj.lib.test.nettest {
	requires transitive net.forestany.forestj.lib.net;
	
	exports net.forestany.forestj.lib.test.nettest;
	exports net.forestany.forestj.lib.test.nettest.msg;
	exports net.forestany.forestj.lib.test.nettest.sock.com;
	exports net.forestany.forestj.lib.test.nettest.sock.https;
	
	opens net.forestany.forestj.lib.test.nettest.sock.com to net.forestany.forestj.lib.net;
}