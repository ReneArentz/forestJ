package net.forestany.forestj.lib.test.net.sftp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test sftp
 */
public class SFTPTest {
	/**
	 * method to test sftp
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testSFTP() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			/*
			 * !!!!!!!!!!!!!!!!!
			 * it is maybe necessary to create new authentication and known_hosts files
			 * !!!!!!!!!!!!!!!!!
			 */
			
			String s_sftpFileAuthentication = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "sftp" + net.forestany.forestj.lib.io.File.DIR + "sftp_id_rsa";
			String s_sshFileAuthentication = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "sftp" + net.forestany.forestj.lib.io.File.DIR + "ssh_id_rsa";
			String s_fileKnownHosts = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "sftp" + net.forestany.forestj.lib.io.File.DIR + "known_hosts";
			
			net.forestany.forestj.lib.net.sftp.TunnelCredentials o_sshTunnelCredentials = new net.forestany.forestj.lib.net.sftp.TunnelCredentials(
				"172.28.226.9",
				2223,
				22,
				"userssh",
				"userssh",
				s_sshFileAuthentication
			);
			
			runSftp("172.28.234.246", 2222, "user", "user", s_sftpFileAuthentication, s_fileKnownHosts, "/", o_sshTunnelCredentials);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
	
	private void runSftp(String p_s_sftpHostIp, int p_i_sftpPort, String p_s_sftpUser, String p_s_sftpPassword, String p_s_sftpFileAuthenticationFile, String p_s_fileKnownHostsFile, String p_s_startingFolderRemote, net.forestany.forestj.lib.net.sftp.TunnelCredentials p_o_sshTunnelCredentials) throws Exception {
		String s_sftpUrlPrefix = "sftp://";
		String s_sftpUrl = null;
				
		/* sftp with user + publickey */
		s_sftpUrl = s_sftpUrlPrefix + p_s_sftpHostIp;
		net.forestany.forestj.lib.net.sftp.Client o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client(s_sftpUrl, p_i_sftpPort, p_s_sftpUser, p_s_sftpFileAuthenticationFile, null, p_s_fileKnownHostsFile);
		runRoutine(o_sftpClient, p_s_startingFolderRemote);
		
		/* sftp with user + password */
		s_sftpUrl = s_sftpUrlPrefix + p_s_sftpUser + ":" + p_s_sftpPassword + "@" + p_s_sftpHostIp + ":" + p_i_sftpPort;
		o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client(s_sftpUrl, p_s_fileKnownHostsFile);
		runRoutine(o_sftpClient, p_s_startingFolderRemote);
		
		String s_oldSSHTunnelPassword = p_o_sshTunnelCredentials.getTunnelPassword();
		String s_oldSSHTunnelFilePathAuthentication = p_o_sshTunnelCredentials.getTunnelFilePathAuthentication();
		
		/* sftp with user + publickey + ssh tunnel (user + password) */
		p_o_sshTunnelCredentials.setTunnelFilePathAuthentication(null);
		s_sftpUrl = s_sftpUrlPrefix + p_s_sftpHostIp;
		o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client(s_sftpUrl, p_i_sftpPort, p_s_sftpUser, p_s_sftpFileAuthenticationFile, null, p_s_fileKnownHostsFile, 8192, true, p_o_sshTunnelCredentials);
		runRoutine(o_sftpClient, p_s_startingFolderRemote);
		
		/* sftp with user + publickey + ssh tunnel (user + publickey) */
		p_o_sshTunnelCredentials.setTunnelPassword(null);
		p_o_sshTunnelCredentials.setTunnelFilePathAuthentication(s_oldSSHTunnelFilePathAuthentication);
		s_sftpUrl = s_sftpUrlPrefix + p_s_sftpHostIp;
		o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client(s_sftpUrl, p_i_sftpPort, p_s_sftpUser, p_s_sftpFileAuthenticationFile, null, p_s_fileKnownHostsFile, 8192, true, p_o_sshTunnelCredentials);
		runRoutine(o_sftpClient, p_s_startingFolderRemote);
		
		/* sftp with user + password + ssh tunnel (user + password) */
		p_o_sshTunnelCredentials.setTunnelPassword(s_oldSSHTunnelPassword);
		p_o_sshTunnelCredentials.setTunnelFilePathAuthentication(null);
		s_sftpUrl = s_sftpUrlPrefix + p_s_sftpUser + ":" + p_s_sftpPassword + "@" + p_s_sftpHostIp + ":" + p_i_sftpPort;
		o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client(s_sftpUrl, p_s_fileKnownHostsFile, 8192, true, p_o_sshTunnelCredentials);
		runRoutine(o_sftpClient, p_s_startingFolderRemote);
		
		/* sftp with user + password + ssh tunnel (user + publickey) */
		p_o_sshTunnelCredentials.setTunnelPassword(null);
		p_o_sshTunnelCredentials.setTunnelFilePathAuthentication(s_oldSSHTunnelFilePathAuthentication);
		s_sftpUrl = s_sftpUrlPrefix + p_s_sftpUser + ":" + p_s_sftpPassword + "@" + p_s_sftpHostIp + ":" + p_i_sftpPort;
		o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client(s_sftpUrl, p_s_fileKnownHostsFile, 8192, true, p_o_sshTunnelCredentials);
		runRoutine(o_sftpClient, p_s_startingFolderRemote);
	}
	
	private void runRoutine(net.forestany.forestj.lib.net.sftp.Client p_o_sftpClient, String p_s_startingFolderRemote) throws Exception {
		String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testSftp" + net.forestany.forestj.lib.io.File.DIR;
		String s_resourcesDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ftp" + net.forestany.forestj.lib.io.File.DIR;
		
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		assertTrue(
			net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
			"directory[" + s_testDirectory + "] does not exist"
		);
		
		assertTrue(p_o_sftpClient != null, "sftp(s) client object is null");
		
		p_o_sftpClient.mkDir(p_s_startingFolderRemote + "second_folder");
		assertTrue(p_o_sftpClient.directoryExists(p_s_startingFolderRemote + "second_folder"), "folder '" + p_s_startingFolderRemote + "second_folder' could not be created");
		
		p_o_sftpClient.upload("Hello World!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", false);
		assertTrue(p_o_sftpClient.fileExists(p_s_startingFolderRemote + "first_subfolder/test.txt"), "could not upload bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'");
		
		p_o_sftpClient.upload("Hello World!!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", true);
		assertTrue(p_o_sftpClient.fileExists(p_s_startingFolderRemote + "first_subfolder/test.txt"), "could not append bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'");
		
		p_o_sftpClient.download(p_s_startingFolderRemote + "first_subfolder/test.txt", s_testDirectory + "text.txt");
		assertTrue(net.forestany.forestj.lib.io.File.exists(s_testDirectory + "text.txt"), "could not download file from '" + p_s_startingFolderRemote + "first_subfolder/test.txt'");
		
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "text.txt", false);
		assertTrue(o_file.getFileContent().contentEquals("Hello World!\r\nHello World!!\r\n"), "downloaded file content from '" + p_s_startingFolderRemote + "first_subfolder/test.txt' is not equal to uploaded bytes from before");
		
		p_o_sftpClient.rename(p_s_startingFolderRemote + "first_subfolder/test.txt", p_s_startingFolderRemote + "renamed_test.txt");
		assertTrue(p_o_sftpClient.fileExists(p_s_startingFolderRemote + "renamed_test.txt"), "could not rename file '" + p_s_startingFolderRemote + "first_subfolder/test.txt' to '" + p_s_startingFolderRemote + "renamed_test.txt'");

		p_o_sftpClient.rename(p_s_startingFolderRemote + "first_subfolder", p_s_startingFolderRemote + "delete_subfolder");
		assertTrue(p_o_sftpClient.directoryExists(p_s_startingFolderRemote + "delete_subfolder"), "could not rename folder '" + p_s_startingFolderRemote + "first_subfolder' to '" + p_s_startingFolderRemote + "delete_subfolder'");

		p_o_sftpClient.upload(s_resourcesDirectory + "1-MB-Test.xlsx", p_s_startingFolderRemote + "second_folder/singleFile.xlsx", false);
		assertTrue(p_o_sftpClient.fileExists(p_s_startingFolderRemote + "second_folder/singleFile.xlsx"), "could not upload file '1-MB-Test.xlsx' to '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'");

		assertTrue(p_o_sftpClient.getLength(p_s_startingFolderRemote + "second_folder/singleFile.xlsx") == p_o_sftpClient.download(p_s_startingFolderRemote + "second_folder/singleFile.xlsx").length, "file length from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx' is not equal do downloaded bytes from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'");

		p_o_sftpClient.uploadFolder(s_resourcesDirectory, p_s_startingFolderRemote + "delete_subfolder/test", true);
		
		java.util.List<net.forestany.forestj.lib.net.sftp.Entry> a_result = p_o_sftpClient.ls(p_s_startingFolderRemote + "delete_subfolder/test", false, false, false, true);
		
		int i_countFiles = 0;
		int i_coundDirectories = 0;
		
		for (net.forestany.forestj.lib.net.ftpcore.Entry o_entry : a_result) {
			if (o_entry.isDirectory()) {
				i_coundDirectories++;
			} else {
				i_countFiles++;
			}
		}
		
		assertTrue(i_coundDirectories == 1, "found remote directories under '" + p_s_startingFolderRemote + "delete_subfolder/test' are not '1', but '" + i_coundDirectories + "'");
		assertTrue(i_countFiles == 6, "found remote files under '" + p_s_startingFolderRemote + "delete_subfolder/test' are not '6', but '" + i_countFiles + "'");
		
		p_o_sftpClient.downloadFolder(p_s_startingFolderRemote + "delete_subfolder", s_testDirectory + net.forestany.forestj.lib.io.File.DIR + "download_folder", true, true);
		
		java.util.List<net.forestany.forestj.lib.io.ListingElement> a_list = net.forestany.forestj.lib.io.File.listDirectory(s_testDirectory + "download_folder" + net.forestany.forestj.lib.io.File.DIR + "test", true);
		
		i_countFiles = 0;
		i_coundDirectories = 0;
		
		for (net.forestany.forestj.lib.io.ListingElement o_entry : a_list) {
			if (o_entry.getIsDirectory()) {
				i_coundDirectories++;
			} else {
				i_countFiles++;
			}
		}
		
		assertTrue(i_coundDirectories == 1, "found local directories under './download_folder' are not '1', but '" + i_coundDirectories + "'");
		assertTrue(i_countFiles == 6, "found local files under './download_folder' are not '6', but '" + i_countFiles + "'");
		
		long l_left = net.forestany.forestj.lib.io.File.fileLength(s_resourcesDirectory + "10-MB-Test.xlsx");
		long l_right = net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "download_folder" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "10-MB-Test.xlsx");
		
		assertTrue(l_left == l_right, "file length of '10-MB-Test.xlsx' in upload sources and downloaded folder are not equal: '" + l_left + "' != '" + l_right + "'");
		
		p_o_sftpClient.delete(p_s_startingFolderRemote + "renamed_test.txt");
		assertTrue(!p_o_sftpClient.fileExists(p_s_startingFolderRemote + "renamed_test.txt"), "could not delete file '" + p_s_startingFolderRemote + "renamed_test.txt'");
		
		p_o_sftpClient.rmDir(p_s_startingFolderRemote + "delete_subfolder");
		assertTrue(!p_o_sftpClient.directoryExists(p_s_startingFolderRemote + "delete_subfolder"), "could not delete directory '" + p_s_startingFolderRemote + "delete_subfolder'");
	
		p_o_sftpClient.rmDir(p_s_startingFolderRemote + "second_folder");
		assertTrue(!p_o_sftpClient.directoryExists(p_s_startingFolderRemote + "second_folder"), "could not delete directory '" + p_s_startingFolderRemote + "second_folder'");
		
		p_o_sftpClient.logout();
		
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
