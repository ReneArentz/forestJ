package net.forestany.forestj.lib.test.nettest.sock.https;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test tcp tiny soap instance
 */
public class TCPTinySoapTest {
	/**
	 * method to test tcp tiny soap instance
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTCPTinySoap() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
				String s_certificatesDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
				String s_rootDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "soapserver" + net.forestany.forestj.lib.io.File.DIR;
				String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
				String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
				int i_port = 443;
				
				/* SERVER */
				
				net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.SOAP, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
				o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
				o_serverConfig.setHost(s_host);
				o_serverConfig.setPort(i_port);
				o_serverConfig.setRootDirectory(s_rootDirectory);
				o_serverConfig.setNotUsingCookies(true);
				
				net.forestany.forestj.lib.net.https.soap.WSDL o_serverWsdl = new net.forestany.forestj.lib.net.https.soap.WSDL(s_rootDirectory + "calculator" + net.forestany.forestj.lib.io.File.DIR + "calculator.wsdl");
				o_serverConfig.setWSDL(o_serverWsdl);
				
				o_serverConfig.getWSDL().setSOAPOperation("Add", CalculatorImpl.Add());
				o_serverConfig.getWSDL().setSOAPOperation("Subtract", CalculatorImpl.Subtract());
				o_serverConfig.getWSDL().setSOAPOperation("Multiply", CalculatorImpl.Multiply());
				o_serverConfig.getWSDL().setSOAPOperation("Divide", CalculatorImpl.Divide());
				
				net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
				net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(javax.net.ssl.SSLServerSocket.class, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER, s_host, i_port, o_serverTask, 30000, -1, 1500, net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2"));
				Thread o_threadServer = new Thread(o_socketReceive);
				
				/* CLIENT */
				
				/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
				System.setProperty("javax.net.ssl.trustStore", s_certificatesDirectory + "all/TrustStore-all.p12");
				System.setProperty("javax.net.ssl.trustStorePassword", "123456");
				
				net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.SOAP, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
				net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
				
				/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
				boolean b_checkReachability = false;
				
				net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(javax.net.ssl.SSLSocket.class, s_host, i_port, o_clientTask, 30000, b_checkReachability, 1, 25, 1500, java.net.InetAddress.getLocalHost().getHostAddress(), 0, null);
				o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
				
				net.forestany.forestj.lib.net.https.soap.WSDL o_clientWsdl = new net.forestany.forestj.lib.net.https.soap.WSDL(s_rootDirectory + "calculator" + net.forestany.forestj.lib.io.File.DIR + "calculator.wsdl");
				o_clientConfig.setWSDL(o_clientWsdl);
				
				/* START SERVER + CLIENT */
				
				o_threadServer.start();
				
				Thread.sleep(100);
				
				/* ------------------------------------------ */
				/* Testing SOAP Add                           */
				/* ------------------------------------------ */
				
				Calculator o_calculator = new Calculator();
				
				Calculator.Add o_add = o_calculator.new Add();
				o_add.param1 = 10.25;
				o_add.param2 = 5.85;
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_add);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					Calculator.AddResult o_addResult = (Calculator.AddResult) o_clientTask.getSOAPResponse();
					double d_result = o_add.param1 + o_add.param2;
					assertTrue( d_result == o_addResult.result, "result is not '" + d_result + "', but '" + o_addResult.result + "'" );
				}
				
				/* ------------------------------------------ */
				/* Testing SOAP Subtract                      */
				/* ------------------------------------------ */
				
				Calculator.Subtract o_subtract = o_calculator.new Subtract();
				o_subtract.param1 = 10.25;
				o_subtract.param2 = 5.85;
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_subtract);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					Calculator.SubtractResult o_subtractResult = (Calculator.SubtractResult) o_clientTask.getSOAPResponse();
					double d_result = o_subtract.param1 - o_subtract.param2;
					assertTrue( d_result == o_subtractResult.result, "result is not '" + d_result + "', but '" + o_subtractResult.result + "'" );
				}
				
				/* ------------------------------------------ */
				/* Testing SOAP Multiply                      */
				/* ------------------------------------------ */
				
				Calculator.Multiply o_multiply = o_calculator.new Multiply();
				o_multiply.param1 = 10.25;
				o_multiply.param2 = 5.85;
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_multiply);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					Calculator.MultiplyResult o_multiplyResult = (Calculator.MultiplyResult) o_clientTask.getSOAPResponse();
					double d_result = o_multiply.param1 * o_multiply.param2;
					assertTrue( d_result == o_multiplyResult.result, "result is not '" + d_result + "', but '" + o_multiplyResult.result + "'" );
				}
				
				/* ------------------------------------------ */
				/* Testing SOAP Divide                        */
				/* ------------------------------------------ */
				
				Calculator.Divide o_divide = o_calculator.new Divide();
				o_divide.param1 = 10.25;
				o_divide.param2 = 5.85;
				
				o_clientTask.setSOAPRequest(o_clientWsdl.getService().getServicePorts().get(0).getAddressLocation(), o_divide);
				o_clientTask.executeRequest();
				
				if (o_clientTask.getSOAPFault() != null) {
					throw new Exception( "SOAP fault occured: " +  o_clientTask.getSOAPFault() );
				} else {
					Calculator.DivideResult o_divideResult = (Calculator.DivideResult) o_clientTask.getSOAPResponse();
					double d_result = o_divide.param1 / o_divide.param2;
					assertTrue( d_result == o_divideResult.result, "result is not '" + d_result + "', but '" + o_divideResult.result + "'" );
				}
				
				/* ------------------------------------------ */
				/* Stop SERVER                                */
				/* ------------------------------------------ */
				
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
