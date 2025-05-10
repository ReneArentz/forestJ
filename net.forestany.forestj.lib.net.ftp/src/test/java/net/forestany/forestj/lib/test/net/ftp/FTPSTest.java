package net.forestany.forestj.lib.test.net.ftp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test ftps
 */
public class FTPSTest {
	/**
	 * method to test ftps
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testFTPS() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			runFtps("172.24.87.100", 12221, "user", "user", "/");
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private void runFtps(String p_s_ftpHostIp, int p_i_ftpPort, String p_s_ftpUser, String p_s_ftpPassword, String p_s_startingFolderRemote) throws Exception {
		String s_ftpUrlPrefix = "ftps://";
		String s_ftpUrl = null;
		boolean b_useMListDir = false;
		
		s_ftpUrl = s_ftpUrlPrefix + p_s_ftpHostIp;
		net.forestany.forestj.lib.net.ftp.Client o_ftpClient = new net.forestany.forestj.lib.net.ftp.Client(s_ftpUrl, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword);
		net.forestany.forestj.lib.test.net.ftp.FTPTest.runRoutine(o_ftpClient, p_s_startingFolderRemote, b_useMListDir);
		
		s_ftpUrl = s_ftpUrlPrefix + p_s_ftpUser + ":" + p_s_ftpPassword + "@" + p_s_ftpHostIp + ":" + p_i_ftpPort;
		o_ftpClient = new net.forestany.forestj.lib.net.ftp.Client(s_ftpUrl);
		net.forestany.forestj.lib.test.net.ftp.FTPTest.runRoutine(o_ftpClient, p_s_startingFolderRemote, b_useMListDir);
		
		b_useMListDir = true;
		
		s_ftpUrl = s_ftpUrlPrefix + p_s_ftpHostIp;
		o_ftpClient = new net.forestany.forestj.lib.net.ftp.Client(s_ftpUrl, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword);
		net.forestany.forestj.lib.test.net.ftp.FTPTest.runRoutine(o_ftpClient, p_s_startingFolderRemote, b_useMListDir);
		
		s_ftpUrl = s_ftpUrlPrefix + p_s_ftpUser + ":" + p_s_ftpPassword + "@" + p_s_ftpHostIp + ":" + p_i_ftpPort;
		o_ftpClient = new net.forestany.forestj.lib.net.ftp.Client(s_ftpUrl);
		net.forestany.forestj.lib.test.net.ftp.FTPTest.runRoutine(o_ftpClient, p_s_startingFolderRemote, b_useMListDir);
	}
}
