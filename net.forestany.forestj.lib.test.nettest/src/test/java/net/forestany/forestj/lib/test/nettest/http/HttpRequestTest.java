package net.forestany.forestj.lib.test.nettest.http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test http request
 */
public class HttpRequestTest {
	/**
	 * if this unit test fails completely, there is an issue with https://httpbin.org/ or no outgoing internet connection
	 * maybe httpbin must be run locally to run the unit test
	 * maybe the used proxy does not exist anymore
	 * 
	 * each test run for http requests and other ssl context/proxy settings must be in a separate test method, because somehow
	 * the affect of previous test will affect the next test ssl context
	 * 
	 * the ssl context seems not to be restartable, because it is not written in a DI manner.
	 * 
	 * if we would do all three or just two in one row we would get ssl handshake exception
	 * 
	 * there is an explicit switch, if logging should be used or not. Request.setUseLog(boolean):
	 * somehow I encounter a state where active logging leads to ssl handshake exception
	 */
	/*@org.junit.jupiter.api.Disabled*/
	@Test
	public void testHttpRequestNoProxy() {
		try {
			/* System.setProperty("https.protocols", "TLSv1.2"); */
			String s_httpBinUrl = "https://httpbin.org/";
			
			runHttpRequests(s_httpBinUrl, null, 80, false); /* using no proxy */
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	/**
	 * test with a proxy server
	 */
	@org.junit.jupiter.api.Disabled
	@Test
	public void testHttpRequestProxy() {
		try {
			/* System.setProperty("https.protocols", "TLSv1.2"); */
			String s_httpBinUrl = "https://httpbin.org/";
			
			runHttpRequests(s_httpBinUrl, "38.242.238.254", 2509, false); /* using proxy */
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	/**
	 * test with another proxy server
	 */
	@org.junit.jupiter.api.Disabled
	@Test
	public void testHttpRequestProxyAlternative() {
		try {
			/* System.setProperty("https.protocols", "TLSv1.2"); */
			String s_httpBinUrl = "https://httpbin.org/";
					
			runHttpRequests(s_httpBinUrl, "103.114.53.2", 8080, false); /* using proxy alternative */
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}

	private void runHttpRequests(String s_httpBinUrl, String s_proxyAddress, int i_proxyPort, boolean p_b_useLog) throws Exception {
		if (p_b_useLog) {
			net.forestany.forestj.lib.Global.get().resetLog();
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfigAll = new net.forestany.forestj.lib.LoggingConfig();
			o_loggingConfigAll.setLevel(java.util.logging.Level.CONFIG);
			o_loggingConfigAll.setUseConsole(true);
			o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.CONFIG);
			o_loggingConfigAll.setUseFile(false);
			o_loggingConfigAll.loadConfig(net.forestany.forestj.lib.Global.get().ILOG);
			
			net.forestany.forestj.lib.Global.get().by_logControl = net.forestany.forestj.lib.Global.CONFIG;
			net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.get().by_logControl;
		}
		
		String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testHttpRequest" + net.forestany.forestj.lib.io.File.DIR;
		
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		assertTrue(
			net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
			"directory[" + s_testDirectory + "] does not exist"
		);
					
		/* ******** */
		/* 1st test */
		/* ******** */
		
		net.forestany.forestj.lib.net.http.Request o_webRequestGet = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.GET, s_httpBinUrl + "get", s_proxyAddress, i_proxyPort);
		o_webRequestGet.setUseLog(p_b_useLog);
		
		String s_response = o_webRequestGet.executeWebRequest();
		
		assertEquals(o_webRequestGet.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestGet.getResponseCode() + "'");
		assertEquals(o_webRequestGet.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestGet.getResponseMessage() + "'");
		assertTrue(s_response.contains("\"args\": {}"), "Response message does not contain '\"args\": {}'");
		
		/* ******** */
		/* 2nd test */
		/* ******** */
					
		o_webRequestGet = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.GET, s_httpBinUrl + "get", s_proxyAddress, i_proxyPort);
		o_webRequestGet.addRequestParameter("param_1", "Hello World!");
		o_webRequestGet.addRequestParameter("param_2", 1234.56);
		o_webRequestGet.setUseLog(p_b_useLog);
		
		s_response = o_webRequestGet.executeWebRequest();
		
		assertEquals(o_webRequestGet.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestGet.getResponseCode() + "'");
		assertEquals(o_webRequestGet.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestGet.getResponseMessage() + "'");
		assertTrue(s_response.contains("\"param_1\": \"Hello World!\",     \"param_2\": \"1234.56\""), "Response message does not contain '\"param_1\": \"Hello World!\",     \"param_2\": \"1234.56\"'");
		
		/* ******** */
		/* 3rd test */
		/* ******** */
					
		o_webRequestGet = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.GET, s_httpBinUrl + "get", s_proxyAddress, i_proxyPort);
		o_webRequestGet.addRequestParameter("param_1", "Hello World!");
		o_webRequestGet.addRequestParameter("param_2", 1234.56);
		o_webRequestGet.addRequestParameter("param_3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		o_webRequestGet.setUseLog(p_b_useLog);
		
		s_response = o_webRequestGet.executeWebRequest();
		
		assertEquals(o_webRequestGet.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestGet.getResponseCode() + "'");
		assertEquals(o_webRequestGet.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestGet.getResponseMessage() + "'");
		assertTrue(s_response.contains("\"param_1\": \"Hello World!\",     \"param_2\": \"1234.56\",     \"param_3\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\""), "Response message does not contain expected string");
		
		/* ******** */
		/* 4th test */
		/* ******** */
		
		String s_attachmentFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "httprequest" + net.forestany.forestj.lib.io.File.DIR + "products.json";
		
		net.forestany.forestj.lib.net.http.Request o_webRequestPost = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.POST, s_httpBinUrl + "post", net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS, s_proxyAddress, i_proxyPort);
		o_webRequestPost.addAttachement("file1", s_attachmentFile);
		o_webRequestPost.setUseLog(p_b_useLog);
		
		s_response = o_webRequestPost.executeWebRequest();
		
		assertEquals(o_webRequestPost.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestPost.getResponseCode() + "'");
		assertEquals(o_webRequestPost.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestPost.getResponseMessage() + "'");
		assertTrue(s_response.contains("\"args\": {},   \"data\": \"\",   \"files\": {    \"file1\": \"[\\r\\n  {\\r\\n    \\\"ProductID\\\": 1,\\r\\n    \\\"ProductName\\\": \\\"Chais\\\",\\r\\n    \\\"SupplierID\\\": 1,\\r\\n    \\\"CategoryID\\\": 1,\\r\\n    \\\"Unit\\\": \\\"10 boxes x 20 bags\\\",\\r\\n    \\\"Price\\\": 18\\r\\n  },\\r\\n"), "Response message does not contain expected string");
		
		/* ******** */
		/* 5th test */
		/* ******** */
		
		o_webRequestPost = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.POST, s_httpBinUrl + "post", net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS, s_proxyAddress, i_proxyPort);
		o_webRequestPost.addRequestParameter("param_1", "Hello World!");
		o_webRequestPost.addRequestParameter("param_2", 1234.56);
		o_webRequestPost.addAttachement("file1", s_attachmentFile);
		o_webRequestPost.setUseLog(p_b_useLog);
		
		s_response = o_webRequestPost.executeWebRequest();
		
		assertEquals(o_webRequestPost.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestPost.getResponseCode() + "'");
		assertEquals(o_webRequestPost.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestPost.getResponseMessage() + "'");
		assertTrue(s_response.contains("\"args\": {},   \"data\": \"\",   \"files\": {    \"file1\": \"[\\r\\n  {\\r\\n    \\\"ProductID\\\": 1,\\r\\n    \\\"ProductName\\\": \\\"Chais\\\",\\r\\n    \\\"SupplierID\\\": 1,\\r\\n    \\\"CategoryID\\\": 1,\\r\\n    \\\"Unit\\\": \\\"10 boxes x 20 bags\\\",\\r\\n    \\\"Price\\\": 18\\r\\n  },\\r\\n"), "Response message does not contain expected string");
		assertTrue(s_response.contains("\"form\": {    \"param_1\": \"Hello World!\",     \"param_2\": \"1234.56\"  }"), "Response message does not contain expected string");
			
		/* ******** */
		/* 6th test */
		/* ******** */
		
		o_webRequestGet = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.GET, s_httpBinUrl + "basic-auth/user/password", s_proxyAddress, i_proxyPort);
		o_webRequestGet.setAuthenticationUser("user");
		o_webRequestGet.setAuthenticationPassword("password");
		o_webRequestGet.setUseLog(p_b_useLog);
		
		s_response = o_webRequestGet.executeWebRequest();
		
		assertEquals(o_webRequestGet.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestGet.getResponseCode() + "'");
		assertEquals(o_webRequestGet.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestGet.getResponseMessage() + "'");
		assertTrue(s_response.contains("{  \"authenticated\": true,   \"user\": \"user\"}"), "Response message does not contain expected string '{  \"authenticated\": true,   \"user\": \"user\"}'");
		
		/* ******** */
		/* 7th test */
		/* ******** */
		
		o_webRequestGet = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.GET, s_httpBinUrl + "basic-auth/user/password", s_proxyAddress, i_proxyPort);
		o_webRequestGet.setAuthenticationUser("false");
		o_webRequestGet.setAuthenticationPassword("wrong");
		o_webRequestGet.setUseLog(p_b_useLog);
		
		s_response = o_webRequestGet.executeWebRequest();
		
		assertEquals(o_webRequestGet.getResponseCode(), 401, "Response code is not '401', it is '" + o_webRequestGet.getResponseCode() + "'");
		assertEquals(o_webRequestGet.getResponseMessage(), "UNAUTHORIZED", "Response code is not 'UNAUTHORIZED', it is '" + o_webRequestGet.getResponseMessage() + "'");
		assertTrue(s_response.contains("Error in web request - Server returned HTTP response code: 401 for URL: " + s_httpBinUrl + "basic-auth/user/password"), "Response message does not contain expected string 'Error in web request - Server returned HTTP response code: 401 for URL: " + s_httpBinUrl + "basic-auth/user/password'");
		
		/* ******** */
		/* 8th test */
		/* ******** */
		
		net.forestany.forestj.lib.net.http.Request o_webRequestDownload = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.DOWNLOAD, s_httpBinUrl + "bytes/1024", s_proxyAddress, i_proxyPort);
		o_webRequestDownload.setDownloadFilename(s_testDirectory + "random.txt");
		o_webRequestDownload.setUseLog(p_b_useLog);
		
		s_response = o_webRequestDownload.executeWebRequest();
		
		assertEquals(o_webRequestDownload.getResponseCode(), 200, "Response code is not '200', it is '" + o_webRequestDownload.getResponseCode() + "'");
		assertEquals(o_webRequestDownload.getResponseMessage(), "OK", "Response code is not 'OK', it is '" + o_webRequestDownload.getResponseMessage() + "'");
		
		assertTrue( net.forestany.forestj.lib.io.File.exists(s_testDirectory + "random.txt"), "file[" + s_testDirectory + "random.txt" + "] does not exist" );
		assertTrue( net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "random.txt") == 1024, "file length of downloaded file != 1024, file length = " + net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "random.txt") );
		
		/* ******** */
		/* 9th test */
		/* ******** */
		
		o_webRequestGet = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.GET, s_httpBinUrl + "url-does-not-exists", s_proxyAddress, i_proxyPort);
		o_webRequestGet.setUseLog(p_b_useLog);
		
		s_response = o_webRequestGet.executeWebRequest();
		
		assertEquals(o_webRequestGet.getResponseCode(), 404, "Response code is not '404', it is '" + o_webRequestGet.getResponseCode() + "'");
		assertEquals(o_webRequestGet.getResponseMessage(), "NOT FOUND", "Response code is not 'NOT FOUND', it is '" + o_webRequestGet.getResponseMessage() + "'");
		assertTrue(s_response.contains("Error in web request - " + s_httpBinUrl + "url-does-not-exists"), "Response message does not contain expected string 'Error in web request - " + s_httpBinUrl + "url-does-not-exists'");
		
		/* ******** */
		/* clean up */
		/* ******** */
		
		net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		assertFalse(
			net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
			"directory[" + s_testDirectory + "] does exist"
		);
	}
}
