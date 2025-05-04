package net.forestany.forestj.lib.test.nettest.sock.https;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test tcp tiny rest instance
 */
class TCPTinyRestTest {
	/**
	 * method to test tcp tiny rest instance
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTCPTinyRest() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
				String s_certificatesDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "com" + net.forestany.forestj.lib.io.File.DIR;
				String s_rootDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "restserver" + net.forestany.forestj.lib.io.File.DIR;
				String s_sessionDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "restsessions" + net.forestany.forestj.lib.io.File.DIR;
				String s_host = java.net.InetAddress.getLocalHost().getHostAddress();
				String s_hostPart = s_host.substring(0, s_host.lastIndexOf("."));
				int i_port = 443;
				
				for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(s_sessionDirectory)) {
					if (!o_file.getFullName().endsWith("dummy.txt")) {
						net.forestany.forestj.lib.io.File.deleteFile(o_file.getFullName());
					}
				}
				
				/* SERVER */
				
				net.forestany.forestj.lib.net.https.Config o_serverConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.REST, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
				o_serverConfig.setAllowSourceList( java.util.Arrays.asList(s_hostPart + ".1/24") );
				o_serverConfig.setHost(s_host);
				o_serverConfig.setPort(i_port);
				o_serverConfig.setRootDirectory(s_rootDirectory);
				o_serverConfig.setSessionDirectory(s_sessionDirectory);
				o_serverConfig.setSessionMaxAge(new net.forestany.forestj.lib.DateInterval("PT30M"));
				o_serverConfig.setSessionRefresh(true);
				o_serverConfig.setForestREST(new TestREST());
				
				net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket> o_serverTask = new net.forestany.forestj.lib.net.sock.task.recv.https.TinyHttpsServer<javax.net.ssl.SSLServerSocket>( o_serverConfig );
				net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket> o_socketReceive = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(javax.net.ssl.SSLServerSocket.class, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER, s_host, i_port, o_serverTask, 30000, -1, 1500, net.forestany.forestj.lib.Cryptography.createSSLContextWithOneCertificate(s_certificatesDirectory + "server/KeyStore-srv.p12", "123456", "test_server2"));
				Thread o_threadServer = new Thread(o_socketReceive);
				
				/* CLIENT */
				
				/* I don't know why exactly, but it is very important that for local test of TCP bidirectional on the same machine, truststore must be set right here with all trusting certificates */
				System.setProperty("javax.net.ssl.trustStore", s_certificatesDirectory + "all/TrustStore-all.p12");
				System.setProperty("javax.net.ssl.trustStorePassword", "123456");
				
				net.forestany.forestj.lib.net.https.Config o_clientConfig = new net.forestany.forestj.lib.net.https.Config("https://" + s_host, net.forestany.forestj.lib.net.https.Mode.REST, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SOCKET);
				net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket> o_clientTask = new net.forestany.forestj.lib.net.sock.task.send.https.TinyHttpsClient<javax.net.ssl.SSLSocket>( o_clientConfig );
				
				/* it is wrong to check reachability in this junit test, because server side is not online when creating socket instance for sending */
				boolean b_checkReachability = false;
				
				net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket> o_socketSend = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(javax.net.ssl.SSLSocket.class, s_host, i_port, o_clientTask, 30000, b_checkReachability, 1, 25, 1500, java.net.InetAddress.getLocalHost().getHostAddress(), 0, null);
				o_clientConfig.setSendingSocketInstanceForHttpClient(o_socketSend);
				
				/* START SERVER + CLIENT */
				
				o_threadServer.start();
				
				Thread.sleep(100);
				
				String s_destination = "https://" + s_host;

				/* ------------------------------------------ */
				/* Testing GET #1                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				String s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				String s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				String s_expectedHash = "852396605c97809eaa877969a45c160a76701ba84096b463e00220cdf108ccc5";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #1 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #2                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("To[gt]", 2);
				o_clientTask.addRequestParameter("To[lt]", 4);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "a5f3e58824c0906e8b73a0ace7efdbd851ec444a1814f810b89da5e9b4fe8fa1";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #2 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing POST #1                            */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons", net.forestany.forestj.lib.net.http.RequestType.POST);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("PIN", 621897);
				o_clientTask.addRequestParameter("Name", "Max Mustermann");
				o_clientTask.addRequestParameter("Age", 35);
				o_clientTask.addRequestParameter("City", "Essen");
				o_clientTask.addRequestParameter("Country", "DE");
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "b5b545137c25fc8baa0c1802848dcc338025ba942e8c9bd3582f63bc62937cac";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #3 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #3                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/5", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "2c03eafddac3a50252019fe2c272836d72810c5fff60f4b67463b2404ff97673";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #4 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing POST #2                            */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/5/messages", net.forestany.forestj.lib.net.http.RequestType.POST);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("To", "Jennifer Garcia");
				o_clientTask.addRequestParameter("Subject", "Hey");
				o_clientTask.addRequestParameter("Message", "How are you doing?");
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "dd2ac51b19874ff6343fa15e08379db04057d9c3b30e02e622fffaaeabe6597e";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #5 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #4                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/3/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "6e2257cb54d74e701ed2d4954585b74add927115a82525ae44f727685671c3b3";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #6 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing PUT #1                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/1/messages/2", net.forestany.forestj.lib.net.http.RequestType.PUT);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("Subject", "Subject changed");
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "7bdf9acf042781e6ce795aa741472834d7466159a5316087af9a9477bb1f2ea2";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #7 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #5                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "4f3f56053cc7f74119f9da016d75d5d564da11fd1e3bc5b546ffbc8cb503370c";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #8 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing PUT #2                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/2", net.forestany.forestj.lib.net.http.RequestType.PUT);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("Name", "Elisabeth Johnson");
				o_clientTask.addRequestParameter("Age", 59);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "8a6e186070aad202b200d5555ccb8459ad6b6f83bc52ca8e668af2363b7a9459";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #9 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #6                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "267297f2f82b724b6599dd492d6fb6c0303918328f033b45b010b6ea88d40f6d";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #10 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #7                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "fd3249a0cc4dacb32bed491b5673cf823520be7b1e007727c888b62fc1d3d571";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #11 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing DELETE #1                          */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/4/messages/1", net.forestany.forestj.lib.net.http.RequestType.DELETE);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "d36c1a10f77b0d650c97a3a234152647df3a33123b91a6e3025eb01357a0b0c0";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #12 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #8                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/4/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "0f12ce171a0bf9242f20013d6906e1c343315cbe77a5fa2ea0e9c38896d4809c";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #13 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing DELETE #2                          */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/2", net.forestany.forestj.lib.net.http.RequestType.DELETE);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "a3d7ba2ce017e2f52311abc3f16354c453adb342881b39674e4d05a2089e8151";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #14 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #9                             */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "3f440528b902c06912d60009bd053d7731110e3cc1d93fb903fd9985aed9b7e0";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #15 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET #10                            */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "a3effdf783c9280f1344649f457f264cee6e26391923b3a5213cf65fa8e9cc10";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #16 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET - No results.                  */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/2/messages", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "200 - OK";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '200 - OK', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "c9a4ff4843dbab2bcd5477b72f59872bfcad51dd85d558b4bc1d95a8a0897374";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #17 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
				
				/* ------------------------------------------ */
				/* Testing GET - Bad Request.                 */
				/* ------------------------------------------ */
				
				o_clientTask.setRequest(s_destination + "/persons/42/admin", net.forestany.forestj.lib.net.http.RequestType.GET);
				o_clientTask.setContentType(net.forestany.forestj.lib.net.http.PostType.HTML);
				o_clientTask.addRequestParameter("User", "Admin");
				o_clientTask.addRequestParameter("Password", 123465);
				o_clientTask.executeRequest();
				
				s_expectedReturnValues = "400 - Bad Request";
				
				assertTrue(
					s_expectedReturnValues.contentEquals(o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage()),
					"received return values do not match '400 - Bad Request', but are '" + o_clientTask.getReturnCode() + " - " + o_clientTask.getReturnMessage() + "'"
				);
				
				s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", o_clientTask.getResponse().getBytes());
				s_expectedHash = "2e931508c1496a6d0b7825ad7bdd4aa955c5bd1c94751072a1ce7a894c27c286";
				
				assertTrue(
					s_hash.contentEquals(s_expectedHash),
					"received REST response #18 does not match expected hash value '" + s_expectedHash + "', but it is '" + s_hash + "'"
				);
								
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
