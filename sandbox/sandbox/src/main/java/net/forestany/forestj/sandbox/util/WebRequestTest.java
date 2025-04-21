package net.forestany.forestj.sandbox.util;

public class WebRequestTest {
	public static void testWebRequestProgressBar(String p_s_currentDirectory, String p_s_httpsUrl) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testWebRequestProgressBar" + net.forestany.forestj.lib.io.File.DIR;
		
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		
		net.forestany.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		
		net.forestany.forestj.lib.net.http.Request.IDelegate itf_delegate = new net.forestany.forestj.lib.net.http.Request.IDelegate() { 
			@Override public void PostProgress(double p_d_progress, String p_s_filename) {
				o_consoleProgressBar.setMarqueeText(p_s_filename);
				o_consoleProgressBar.report(p_d_progress);
			}
		};
		
		String s_file = s_testDirectory + "jdk.zip";
		
		net.forestany.forestj.lib.net.http.Request o_webRequestDownload = new net.forestany.forestj.lib.net.http.Request(net.forestany.forestj.lib.net.http.RequestType.DOWNLOAD, p_s_httpsUrl);
		o_webRequestDownload.setDownloadFilename(s_file);
		o_webRequestDownload.setDelegate(itf_delegate);
		
		o_consoleProgressBar.init("Download file . . .", "Download finished.");
		String s_response = o_webRequestDownload.executeWebRequest();
		o_consoleProgressBar.close();
		
		System.out.println(s_response);
		
		if (o_webRequestDownload.getResponseCode() != 200) {
			throw new Exception("Response code is not '200', it is '" + o_webRequestDownload.getResponseCode() + "'");
		}
		
		if (!o_webRequestDownload.getResponseMessage().contentEquals("OK")) {
			throw new Exception("Response code is not 'OK', it is '" + o_webRequestDownload.getResponseMessage() + "'");	
		}
		
		System.out.println("file length = '" + net.forestany.forestj.lib.io.File.fileLength(s_file) + " bytes'");
		
		net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
	}
}
