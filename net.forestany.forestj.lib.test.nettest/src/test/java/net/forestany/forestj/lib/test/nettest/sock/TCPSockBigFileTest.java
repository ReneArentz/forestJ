package net.forestany.forestj.lib.test.nettest.sock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test tcp socket big file
 */
public class TCPSockBigFileTest {
	/**
	 * method to test tcp socket big file
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTCPSockBigFileTest() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			testTCPSockBigFile();
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void testTCPSockBigFile() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testTCPSockBigFileTest" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
				net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
				"directory[" + s_testDirectory + "] does not exist"
			);
			
			String s_sourceFile = s_testDirectory + "sourceFile.txt";
			String s_destinationFile = s_testDirectory + "destinationFile.txt";
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_sourceFile, true);
			
			net.forestany.forestj.lib.io.File.replaceFileContent(s_sourceFile, net.forestany.forestj.lib.io.File.generateRandomFileContent_100MB());
			assertTrue(
				net.forestany.forestj.lib.io.File.fileLength(s_sourceFile) == 104857600,
				"file length != 104857600"
			);
			
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
						
							try {
								int i_amountBytes = this.amountBytesProtocol();				
								net.forestany.forestj.lib.Global.ilog("Amount bytes: " + i_amountBytes);
								
								/* ------------------------------------------------------ */
								
								byte[] a_receivedData = this.receiveBytes(i_amountBytes);
								net.forestany.forestj.lib.Global.ilog("Received file with length[" + a_receivedData.length + "]");
								
								try (java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(s_destinationFile)) {
									o_fileOutputStream.write(a_receivedData);
								}
								net.forestany.forestj.lib.Global.ilog("Wrote bytes into file");
								
								/* ------------------------------------------------------ */
								
								this.sendACK();
							} catch (java.io.IOException o_exc) {
								throw o_exc;
							}
							
							net.forestany.forestj.lib.Global.ilog("Socket communication closed");
							
							this.b_stop = true;
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
					1,													/* max. number of executions */
					8192,												/* receive buffer size */
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
					
							byte[] a_buffer = new byte[8192];
							byte[] a_data = null;
							int i_read = 0;
							java.io.ByteArrayOutputStream o_byteArrayOutputStream = new java.io.ByteArrayOutputStream();
							
							try (java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(s_sourceFile)) {
							    while ((i_read = o_fileInputStream.read(a_buffer)) != -1) {
							    	o_byteArrayOutputStream.write(a_buffer, 0, i_read);
						        }
							} finally {
								try {
									if (o_byteArrayOutputStream != null) {
										a_data = o_byteArrayOutputStream.toByteArray();
										o_byteArrayOutputStream.close();
									}
								} catch (Exception o_exc) {
									/* nothing to do */
								}
							}
							
							if (a_data == null) {
								throw new Exception("Could not read big file, readed data is null");
							}
							
							int i_amountBytes = this.amountBytesProtocol(a_data.length);
							net.forestany.forestj.lib.Global.ilog("Amount bytes: " + i_amountBytes);
							
							/* ------------------------------------------------------ */
							
							this.sendBytes(a_data);
							net.forestany.forestj.lib.Global.ilog("Sended data, amount of bytes: " + a_data.length);
							
							/* ------------------------------------------------------ */
							
							this.receiveACK();
				 
							net.forestany.forestj.lib.Global.ilog("Socket communication finished");
							
							this.b_stop = true;
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
					-1,															/* max. number of executions */
					25,															/* interval for waiting for other communication side */
					8192,														/* buffer size */
					java.net.InetAddress.getLocalHost().getHostAddress(),		/* sender address */
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
			
			String s_hashSource = net.forestany.forestj.lib.io.File.hashFile(s_sourceFile, "SHA-256");
			String s_hashDestination = net.forestany.forestj.lib.io.File.hashFile(s_destinationFile, "SHA-256");
				
			assertEquals(s_hashSource, s_hashDestination, "hash value of source and destination file are not matching: '" + s_hashSource + "' != '" + s_hashDestination + "'");
			
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
				net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
				"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
}
