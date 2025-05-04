package net.forestany.forestj.lib.test.nettest.sock.https;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test tcp tiny http instance
 */
public class TCPTinyHttpsTest {
	/**
	 * method to test tcp tiny http instance
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTCPTinyHttps() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			testTCPTinyHttpsRequests();
			testTCPTinyHttpsSession();
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void testTCPTinyHttpsRequests() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testTCPTinyHttps" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
				net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
				"directory[" + s_testDirectory + "] does not exist"
			);
			
				String s_certificatesDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
				String s_rootDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpsserver" + net.forestany.forestj.lib.io.File.DIR;
				String s_sessionDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpssessions" + net.forestany.forestj.lib.io.File.DIR;
				String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
				String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
				int i_port = 443;
				
				for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(s_sessionDirectory)) {
					if (!o_file.getFullName().endsWith("dummy.txt")) {
						net.forestany.forestj.lib.io.File.deleteFile(o_file.getFullName());
					}
				}
				
				/* SERVER */
				
				net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.DYNAMIC, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
				o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
				o_serverConfig.setHost(s_host);
				o_serverConfig.setPort(i_port);
				o_serverConfig.setRootDirectory(s_rootDirectory);
				o_serverConfig.setSessionDirectory(s_sessionDirectory);
				o_serverConfig.setSessionMaxAge(new net.forestany.forestj.lib.DateInterval("PT30M"));
				o_serverConfig.setSessionRefresh(true);
				o_serverConfig.setForestSeed(new TestSeed());
				
				net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
				net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(javax.net.ssl.SSLServerSocket.class, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER, s_host, i_port, o_serverTask, 30000, -1, 1500, net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2"));
				Thread o_threadServer = new Thread(o_socketReceive);
				
				/* CLIENT */
				
				/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
				System.setProperty("javax.net.ssl.trustStore", s_certificatesDirectory + "all/TrustStore-all.p12");
				System.setProperty("javax.net.ssl.trustStorePassword", "123456");
				
				net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.NORMAL, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
				net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
				
				/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
				boolean b_checkReachability = false;
				
				net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(javax.net.ssl.SSLSocket.class, s_host, i_port, o_clientTask, 30000, b_checkReachability, 1, 25, 1500, java.net.InetAddress.getLocalHost().getHostAddress(), 0, null);
				o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
				
				/* START SERVER + use CLIENT */
				
				o_threadServer.start();
				
				Thread.sleep(100);
				
				String s_destination = "https://" + s_host;

				/* ------------------------------------------ */
				/* Testing GET                                */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				String s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				String s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				String s_expectedHash = "8d286bc8ef08f350b8dfe1ca805af08394fbd4a39852f141b0dd97f89adb475e";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received dynamic static response does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing POST with form data                */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/reflectPOST.html", net.forestany.forestj.lib.net.http.RequestType.POST);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("fname", "John");
				o_clientTask.addRequestParameter("lname", "Doe");
				o_clientTask.addRequestParameter("trip-start", "2022-04-08");
				o_clientTask.addRequestParameter("scales", "on");
				o_clientTask.addRequestParameter("bsubmit", "Send");
				
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "0675118efc073a7e2271859d78ede316c0a5b26664772f11d5e9a03b0e74c20a";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received dynamic static POST response does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing POST with form and file data       */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/reflectPOSTandFILES.html", net.forestany.forestj.lib.net.http.RequestType.POST);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS);
				o_clientTask.addRequestParameter("fname", "John");
				o_clientTask.addRequestParameter("lname", "Doe");
				o_clientTask.addRequestParameter("trip-start", "2022-04-08");
				o_clientTask.addRequestParameter("scales", "on");
				o_clientTask.addRequestParameter("bsubmit", "Send");
				
				String s_filepath = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httprequest" + net.forestany.forestj.lib.io.File.DIR + "products.json";
				o_clientTask.addAttachement("products_json_file", s_filepath);
				
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "b17f0536fb23291a59857f8a55ada7ed792a9fa01b1e8a2c703285ac1525b98f";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received dynamic static POST and FILES response does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing DOWNLOAD                           */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/products.json", net.forestany.forestj.lib.net.http.RequestType.DOWNLOAD);
				o_clientTask.setDownloadFilename(s_testDirectory + "products.json");
				o_clientTask.executeRequest();
				
				assertTrue(
					((o_clientTask.getReturnCode() == 200) && (o_clientTask.getReturnMessage().startsWith("File[/products.json] downloaded to"))),
					"return code[" + o_clientTask.getReturnCode() + "] is not 200 or file could not be downloaded"
				);
				
				assertTrue( net.forestany.forestj.lib.io.File.exists(s_testDirectory + "products.json"), "file[" + s_testDirectory + "products.json" + "] does not exist" );
				assertTrue( net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "products.json") == 12929, "file length of downloaded file != 12929, file length = " + net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "products.json") );
				
				/* ------------------------------------------ */
				/* Stop SERVER and Close CLIENT               */
				/* ------------------------------------------ */
				
				if (o_socketReceive != null) {
					o_socketReceive.stop();
				}
				
				if (o_socketSend != null) {
					o_socketSend.stop();
				}
				
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
	
	private void testTCPTinyHttpsSession() throws Exception {
		String s_cookie = testTCPTinyHttpsSessionRequests(true, true, null);
		s_cookie = testTCPTinyHttpsSessionRequests(true, false, null);
		s_cookie = testTCPTinyHttpsSessionRequests(false, false, s_cookie);
	}
	
	private String testTCPTinyHttpsSessionRequests(boolean p_b_useCookiesFromPreviousRequest, boolean p_b_sessionRefresh, String p_s_cookie) {
		try {
			String s_certificatesDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
			String s_rootDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpsserver" + net.forestany.forestj.lib.io.File.DIR;
			String s_sessionDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httpssessions" + net.forestany.forestj.lib.io.File.DIR;
			String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
			int i_port = 443;
			
			if (p_b_sessionRefresh) {
				for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(s_sessionDirectory)) {
					if (!o_file.getFullName().endsWith("dummy.txt")) {
						net.forestany.forestj.lib.io.File.deleteFile(o_file.getFullName());
					}
				}
			}
			
			/* SERVER */
			
			net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.DYNAMIC, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
			o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
			o_serverConfig.setHost(s_host);
			o_serverConfig.setPort(i_port);
			o_serverConfig.setRootDirectory(s_rootDirectory);
			o_serverConfig.setSessionDirectory(s_sessionDirectory);
			o_serverConfig.setSessionMaxAge(new net.forestany.forestj.lib.DateInterval("PT30M"));
			o_serverConfig.setSessionRefresh(p_b_sessionRefresh);
			o_serverConfig.setForestSeed(new SessionSeed());
			
			net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
			net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(javax.net.ssl.SSLServerSocket.class, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER, s_host, i_port, o_serverTask, 30000, -1, 1500, net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2"));
			Thread o_threadServer = new Thread(o_socketReceive);
			
			/* CLIENT */
			
			/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
			System.setProperty("javax.net.ssl.trustStore", s_certificatesDirectory + "all/TrustStore-all.p12");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
			
			net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.NORMAL, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
			net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
			
			/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
			boolean b_checkReachability = false;
			
			net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(javax.net.ssl.SSLSocket.class, s_host, i_port, o_clientTask, 30000, b_checkReachability, 1, 25, 1500, java.net.InetAddress.getLocalHost().getHostAddress(), 0, null);
			o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
			o_clientConfig.setClientUseCookiesFromPreviousRequest(p_b_useCookiesFromPreviousRequest);
			
			/* START SERVER + use CLIENT */
			
			o_threadServer.start();
			
			Thread.sleep(100);
		
			String s_destination = "https://" + s_host;

			/* ------------------------------------------ */
			/* Testing SESSION #1                         */
			/* ------------------------------------------ */
			
			o_clientTask.setRequest(s_destination + "/reflectSESSION.html", net.forestany.forestj.lib.net.http.RequestType.GET);
			
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_cookie)) {
				o_clientTask.getSeed().getRequestHeader().setCookie(p_s_cookie);
			}
			
			o_clientTask.executeRequest();
			
			String s_expectedReturnValues = "200 - OK";
			
			assertTrue(
				s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
				"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
			);

			String s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
			String s_expectedHash = "ea3c3fab7626cd0d7448838dfa434eb0067042b0a9db5abe930a83b396d48797";
			
			if ( (!p_b_useCookiesFromPreviousRequest) && (!p_b_sessionRefresh) ) {
				s_expectedHash = "17777fe9d81333244954cdefb22d8a797a3c2fae6c9045b99fc3dfbc65c22b77";
			}
			
			assertTrue(
				s_hash.contentEquals(s_expectedHash),
				"received dynamic static response #1 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
			);
			
			if ( (!p_b_useCookiesFromPreviousRequest) && (!p_b_sessionRefresh) ) {
				s_expectedHash = "ea3c3fab7626cd0d7448838dfa434eb0067042b0a9db5abe930a83b396d48797";
			}
			
			/* ------------------------------------------ */
			/* Testing SESSION #2                         */
			/* ------------------------------------------ */
			
			o_clientTask.setRequest(s_destination + "/reflectSESSION.html", net.forestany.forestj.lib.net.http.RequestType.GET);
			o_clientTask.executeRequest();
			
			s_expectedReturnValues = "200 - OK";
			
			assertTrue(
				s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
				"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
			);

			s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
			if (p_b_useCookiesFromPreviousRequest) s_expectedHash = "8aaae71e7cf6b6ebe98e1f2745ec841394d83043fb2203f5c6d2bde08f036ac9";
			
			assertTrue(
				s_hash.contentEquals(s_expectedHash),
				"received dynamic static response #2 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
			);
			
			/* ------------------------------------------ */
			/* Testing SESSION #3                         */
			/* ------------------------------------------ */
			
			o_clientTask.setRequest(s_destination + "/reflectSESSION.html", net.forestany.forestj.lib.net.http.RequestType.GET);
			o_clientTask.executeRequest();
			
			s_expectedReturnValues = "200 - OK";
			
			assertTrue(
				s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
				"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
			);

			s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
			if (p_b_useCookiesFromPreviousRequest) s_expectedHash = "b9353dc9ed4880cf2ab6d1a4bcb1bb84bbd22a0c95c28cb1ab3b0070ca669d47";
			
			assertTrue(
				s_hash.contentEquals(s_expectedHash),
				"received dynamic static response #3 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
			);
			
			/* ------------------------------------------ */
			/* Testing SESSION #4                         */
			/* ------------------------------------------ */
			
			o_clientTask.setRequest(s_destination + "/reflectSESSION.html", net.forestany.forestj.lib.net.http.RequestType.GET);
			o_clientTask.executeRequest();
			
			s_expectedReturnValues = "200 - OK";
			
			assertTrue(
				s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
				"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
			);
		
			s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
			if (p_b_useCookiesFromPreviousRequest) s_expectedHash = "da79a4dfa84b52ed011fe5ad1a3dff90de6b4677b34f7516cf987e4ef06b6334";
			
			assertTrue(
				s_hash.contentEquals(s_expectedHash),
				"received dynamic static response #4 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
			);

			/* ------------------------------------------ */
			/* Testing SESSION #5                         */
			/* ------------------------------------------ */
			
			o_clientTask.setRequest(s_destination + "/reflectSESSION.html", net.forestany.forestj.lib.net.http.RequestType.GET);
			o_clientTask.executeRequest();
			
			s_expectedReturnValues = "200 - OK";
			
			assertTrue(
				s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
				"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
			);
	
			s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
			if (p_b_useCookiesFromPreviousRequest) s_expectedHash = "17777fe9d81333244954cdefb22d8a797a3c2fae6c9045b99fc3dfbc65c22b77";
			
			assertTrue(
				s_hash.contentEquals(s_expectedHash),
				"received dynamic static response #5 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
			);

			/* ------------------------------------------ */
			/* Stop SERVER and Close CLIENT               */
			/* ------------------------------------------ */
			
			if (o_socketReceive != null) {
				o_socketReceive.stop();
			}
			
			if (o_socketSend != null) {
				o_socketSend.stop();
			}
			
			return o_clientTask.getSeed().getResponseHeader().getCookie().clientCookieToString();
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
		
		return null;
	}
}
