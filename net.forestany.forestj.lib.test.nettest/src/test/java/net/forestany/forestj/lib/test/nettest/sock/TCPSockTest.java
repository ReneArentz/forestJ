package net.forestany.forestj.lib.test.nettest.sock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test tcp socket
 */
public class TCPSockTest {
	/**
	 * method to test tcp socket
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTCPSockTestLoop() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();

			for (int i = 0; i < 10; i++) {
				testTCPSockTest();
				
				try {
					/* 10 milliseconds to close connection */
					Thread.sleep(10);
				} catch (InterruptedException e) {
					net.forestany.forestj.lib.Global.logException(e);
				}
			}
			
			for (int i = 0; i < 10; i++) {
				testTCPSockTestWithMarshalling();
				
				try {
					/* 10 milliseconds to close connection */
					Thread.sleep(10);
				} catch (InterruptedException e) {
					net.forestany.forestj.lib.Global.logException(e);
				}
			}
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private void testTCPSockTest() {
		try {
			String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			int i_port = 8080;
		
			/* SERVER */
			
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket> o_socketReceive = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("Handle incoming socket communication with " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().toString());
			
						int i_amountBytes = this.amountBytesProtocol();				
						net.forestany.forestj.lib.Global.ilog("Amount bytes: " + i_amountBytes);
						
						/* ------------------------------------------------------ */
						
						byte[] a_receivedData = this.receiveBytes(i_amountBytes);
						String s_message = new String(a_receivedData);
						net.forestany.forestj.lib.Global.ilog("Received data: '" + s_message + "'");
						
						assertEquals(a_receivedData.length, 13, "data length is not '13', but '" + a_receivedData.length + "'");
						assertEquals(s_message, "Hello World!!", "data content is not 'Hello World!!', but '" + s_message + "'");
						
						/* ------------------------------------------------------ */
						
						this.sendACK();
						
						net.forestany.forestj.lib.Global.ilog("Socket communication closed");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
			
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
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("Starting socket communication");
			
						String s_message = "Hello World!!";
						
						@SuppressWarnings("unused")
						int i_foo = this.amountBytesProtocol(s_message.length());
						
						/* ------------------------------------------------------ */
						
						this.sendBytes(s_message.getBytes());
						net.forestany.forestj.lib.Global.ilog("Sended data, amount of bytes: " + s_message.length());
						
						/* ------------------------------------------------------ */
						
						this.receiveACK();
						
						net.forestany.forestj.lib.Global.ilog("Socket communication finished");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
			
			/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
			boolean b_checkReachability = false;
			
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>(
				java.net.Socket.class,										/* class type */
				s_host,														/* destination address */
				i_port,														/* destination port */
				o_clientTask,												/* client task */
				10000,														/* timeout milliseconds */
				b_checkReachability,										/* check if destination is reachable */
				5,															/* max. number of executions */
				25,															/* interval for waiting for other communication side */
				1500,														/* buffer size */
				java.net.InetAddress.getLocalHost().getHostAddress(),		/* sender address */
				0,															/* sender port */
				null														/* ssl context */
			);
			o_threadClient = new Thread(o_socketSend);
			
			/* START SERVER + CLIENT */
			
			o_threadServer.start();
			o_threadClient.start();
			
			o_threadClient.join();
						
			if (o_socketSend != null) {
				o_socketSend.stop();
			}
			
			if (o_socketReceive != null) {
				o_socketReceive.stop();
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void testTCPSockTestWithMarshalling() {
		try {
			String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			int i_port = 8080;
		
			/* SERVER */
			
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket> o_socketReceive = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.ServerSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("Handle incoming socket communication with " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().toString());
			
						int i_amountBytes = this.amountBytesProtocol();				
						net.forestany.forestj.lib.Global.ilog("Amount bytes: " + i_amountBytes);
						
						/* ------------------------------------------------------ */
						
						byte[] a_receivedData = this.receiveBytes(i_amountBytes);
						String s_message = (String)net.forestany.forestj.lib.net.msg.Marshall.unmarshallObject(String.class, a_receivedData);
						net.forestany.forestj.lib.Global.ilog("Received data: '" + s_message + "'");
						
						assertEquals(a_receivedData.length, 21, "data length is not '21', but '" + a_receivedData.length + "'");
						assertEquals(s_message, "Hello World!!", "data content is not 'Hello World!!', but '" + s_message + "'");
						
						/* ------------------------------------------------------ */
						
						this.sendACK();
						
						net.forestany.forestj.lib.Global.ilog("Socket communication closed");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
			
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
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket>(net.forestany.forestj.lib.net.sock.Type.TCP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.Socket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("Starting socket communication");
			
						String s_message = "Hello World!!";
						
						byte[] a_data = net.forestany.forestj.lib.net.msg.Marshall.marshallObject(s_message);
						
						@SuppressWarnings("unused")
						int i_foo = this.amountBytesProtocol(a_data.length);
						
						/* ------------------------------------------------------ */
						
						this.sendBytes(a_data);
						net.forestany.forestj.lib.Global.ilog("Sended data, amount of bytes: " + a_data.length);
						
						/* ------------------------------------------------------ */
						
						this.receiveACK();
						
						net.forestany.forestj.lib.Global.ilog("Socket communication finished");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
			
			/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
			boolean b_checkReachability = false;
			
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>(
				java.net.Socket.class,										/* class type */
				s_host,														/* destination address */
				i_port,														/* destination port */
				o_clientTask,												/* client task */
				10000,														/* timeout milliseconds */
				b_checkReachability,										/* check if destination is reachable */
				5,															/* max. number of executions */
				25,															/* interval for waiting for other communication side */
				1500,														/* buffer size */
				java.net.InetAddress.getLocalHost().getHostAddress(),		/* sender address */
				0,															/* sender port */
				null														/* ssl context */
			);
			o_threadClient = new Thread(o_socketSend);
			
			/* START SERVER + CLIENT */
			
			o_threadServer.start();
			o_threadClient.start();
			
			o_threadClient.join();
						
			if (o_socketSend != null) {
				o_socketSend.stop();
			}
			
			if (o_socketReceive != null) {
				o_socketReceive.stop();
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
}
