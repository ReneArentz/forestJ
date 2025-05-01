package net.forestany.forestj.lib.test.nettest.sock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test tcp socket handshake
 */
public class TCPSockHandshakeTest {
	/**
	 * method to test tcp socket handshake
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTCPSockHandshakeTestLoop() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			for (int i = 0; i < 10; i++) {
				testTCPSockHandshakeTest();
				try {
					/* 10 milliseconds to close handshake connection */
					Thread.sleep(10);
				} catch (InterruptedException e) {
					net.forestany.forestj.lib.Global.logException(e);
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void testTCPSockHandshakeTest() {
		try {
			String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			int i_port = 8080;
		
			/* SERVER */
			
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket> o_socketReceive = null;
			net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.HandshakeReceive<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP);
			o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket>(
				java.net.ServerSocket.class,						/* class type */
				net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET,	/* socket type */
				s_host,												/* receiving address */
				i_port,												/* receiving port */
				o_serverTask,										/* server task */
				10000,												/* timeout milliseconds */
				-1,													/* max. number of executions */
				1500,												/* receive buffer size */
				null												/* ssl context */
			);
			o_threadServer = new Thread(o_socketReceive);
			
			/* CLIENT */
			
			Thread o_threadClient = null;
			net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket> o_socketSend = null;
			net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.HandshakeSend<java.net.Socket>(net.forestany.forestj.lib.net.sock.Type.TCP);
			
			/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
			boolean b_checkReachability = false;
			
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>(
				java.net.Socket.class,										/* class type */
				s_host,														/* destination address */
				i_port,														/* destination port */
				o_clientTask,												/* client task */
				10000,														/* timeout milliseconds */
				b_checkReachability,										/* check if destination is reachable */
				1,															/* max. number of executions */
				25,															/* interval for waiting for other communication side */
				1500,														/* buffer size */
				null,														/* sender address */
				0,															/* sender port */
				null														/* ssl context */
			);
			o_threadClient = new Thread(o_socketSend);
			
			/* START SERVER + CLIENT */
			
			o_threadServer.start();
			o_threadClient.start();
			
			o_threadClient.join();
			
			if (o_socketReceive != null) {
				o_socketReceive.stop();
			}
			
			if (o_socketSend != null) {
				o_socketSend.stop();
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
}
