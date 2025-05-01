package net.forestany.forestj.lib.test.nettest.sock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test udp socket big file
 */
public class UDPSockTest {
	/**
	 * method to test udp socket big file
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testUDPSockTest() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
		
			for (int i = 0; i < 10; i++) {
				testUDPSock();
				
				try {
					/* 10 milliseconds to close connection */
					Thread.sleep(10);
				} catch (InterruptedException e) {
					net.forestany.forestj.lib.Global.logException(e);
				}
			}
			
			for (int i = 0; i < 10; i++) {
				testUDPSockWithMarshallingTest();
				
				try {
					/* 10 milliseconds to close connection */
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
	
	private void testUDPSock() {
		try {
			String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			int i_port = 8080;
		
			/* SERVER */
			
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.DatagramSocket> o_socketReceive = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket>(net.forestany.forestj.lib.net.sock.Type.UDP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Handle incoming datagram packet communication with " + this.o_datagramPacket.getAddress().toString() + ":" + this.o_datagramPacket.getPort());
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Data length: " + this.o_datagramPacket.getLength());
						String s_foo = new String(this.o_datagramPacket.getData()).trim();
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Data: " + s_foo);
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Datagram packet communication closed");
						
						assertEquals(this.o_datagramPacket.getLength(), 13, "data length is not '13', but '" + this.o_datagramPacket.getLength() + "'");
						assertEquals(s_foo, "Hello World!!", "data content is not 'Hello World!!', but '" + s_foo + "'");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
			
			o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.DatagramSocket>(
				java.net.DatagramSocket.class,						/* class type */
				net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET,	/* socket type */
				s_host,												/* receiving address */
				i_port,												/* receiving port */
				o_serverTask,										/* server task */
				10000,												/* timeout milliseconds */
				-1,													/* max. number of executions */
				1500												/* receive buffer size */
			);
			o_threadServer = new Thread(o_socketReceive);
			
			/* CLIENT */
			
			Thread o_threadClient = null;
			net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.DatagramSocket> o_socketSend = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket>(net.forestany.forestj.lib.net.sock.Type.UDP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Send outgoing datagram packet with " + this.o_datagramPacket.getAddress().toString() + ":" + this.o_datagramPacket.getPort());
			
						String s_message = "Hello World!!";
						byte[] a_bytes = s_message.getBytes();
						
						this.o_datagramPacket.setData(a_bytes);
						this.o_datagramPacket.setLength(a_bytes.length);
						
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Data length: " + this.o_datagramPacket.getLength());
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Data: " + new String(this.o_datagramPacket.getData()));
						
						((java.net.DatagramSocket)this.o_socket.getSocket()).send(this.o_datagramPacket);
						
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Datagram packet communication closed");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
				
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.DatagramSocket>(
				java.net.DatagramSocket.class,								/* class type */
				s_host,														/* destination address */
				i_port,														/* destination port */
				o_clientTask,												/* client task */
				25,															/* interval for waiting for other communication side */
				5,															/* max. number of executions */
				1500,														/* buffer size */
				java.net.InetAddress.getLocalHost().getHostAddress(),		/* sender address */
				0															/* sender port */
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
	
	private void testUDPSockWithMarshallingTest() {
		try {
			String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			int i_port = 8080;
		
			/* SERVER */
			
			Thread o_threadServer = null;
			net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.DatagramSocket> o_socketReceive = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket>(net.forestany.forestj.lib.net.sock.Type.UDP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Handle incoming datagram packet communication with " + this.o_datagramPacket.getAddress().toString() + ":" + this.o_datagramPacket.getPort());
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Data length: " + this.o_datagramPacket.getLength());
						String s_foo = (String)net.forestany.forestj.lib.net.msg.Marshall.unmarshallObject(String.class, this.o_datagramPacket.getData());
						s_foo = s_foo.trim();
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Data: " + s_foo);
						net.forestany.forestj.lib.Global.ilog("S" + "\t" + "Datagram packet communication closed");
						
						assertEquals(this.o_datagramPacket.getLength(), 21, "data length is not '21', but '" + this.o_datagramPacket.getLength() + "'");
						assertEquals(s_foo, "Hello World!!", "data content is not 'Hello World!!', but '" + s_foo + "'");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
			
			o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.DatagramSocket>(
				java.net.DatagramSocket.class,						/* class type */
				net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET,	/* socket type */
				s_host,												/* receiving address */
				i_port,												/* receiving port */
				o_serverTask,										/* server task */
				10000,												/* timeout milliseconds */
				-1,													/* max. number of executions */
				1500												/* receive buffer size */
			);
			o_threadServer = new Thread(o_socketReceive);
			
			/* CLIENT */
			
			Thread o_threadClient = null;
			net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.DatagramSocket> o_socketSend = null;
			
			net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket>(net.forestany.forestj.lib.net.sock.Type.UDP) {
				public Class<?> getSocketTaskClassType() {
					return net.forestany.forestj.lib.net.sock.task.Task.class;
				}
				
				public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<java.net.DatagramSocket> p_o_sourceTask) {
					this.cloneBasicFields(p_o_sourceTask);
				}
				
				protected void runTask() throws Exception {
					try {
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Send outgoing datagram packet with " + this.o_datagramPacket.getAddress().toString() + ":" + this.o_datagramPacket.getPort());
			
						String s_message = "Hello World!!";
						byte[] a_bytes = net.forestany.forestj.lib.net.msg.Marshall.marshallObject(s_message);
						
						this.o_datagramPacket.setData(a_bytes);
						this.o_datagramPacket.setLength(a_bytes.length);
						
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Data length: " + this.o_datagramPacket.getLength());
						
						((java.net.DatagramSocket)this.o_socket.getSocket()).send(this.o_datagramPacket);
						
						net.forestany.forestj.lib.Global.ilog("C" + "\t" + "Datagram packet communication closed");
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
						fail(o_exc.getMessage());
					}
				}
			};
				
			o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.DatagramSocket>(
				java.net.DatagramSocket.class,								/* class type */
				s_host,														/* destination address */
				i_port,														/* destination port */
				o_clientTask,												/* client task */
				25,															/* interval for waiting for other communication side */
				5,															/* max. number of executions */
				1500,														/* buffer size */
				java.net.InetAddress.getLocalHost().getHostAddress(),		/* sender address */
				0															/* sender port */
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
