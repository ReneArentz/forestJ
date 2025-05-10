package net.forestany.forestj.lib.test.net.ftp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test ftp
 */
public class FTPTest {
	/**
	 * method to test ftp
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testFTP() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			runFtp("172.24.87.100", 12220, "user", "user", "/");
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private void runFtp(String p_s_ftpHostIp, int p_i_ftpPort, String p_s_ftpUser, String p_s_ftpPassword, String p_s_startingFolderRemote) throws Exception {
		String s_ftpUrlPrefix = "ftp://";
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
	
	/**
	 * ftp routine method with some standard calls
	 * 
	 * @param p_o_ftpClient					ftp client instance
	 * @param p_s_startingFolderRemote		path to starting folder on ftp(s) server
	 * @param p_b_useMListDir				use mlist directory flag
	 * @throws Exception					any exception happening during routine
	 */
	public static void runRoutine(net.forestany.forestj.lib.net.ftp.Client p_o_ftpClient, String p_s_startingFolderRemote, boolean p_b_useMListDir) throws Exception {
		String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFtp" + net.forestany.forestj.lib.io.File.DIR;
		String s_resourcesDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ftp" + net.forestany.forestj.lib.io.File.DIR;
		
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		assertTrue(
			net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
			"directory[" + s_testDirectory + "] does not exist"
		);
				
		assertTrue(p_o_ftpClient != null, "ftp(s) client object is null");
		
		p_o_ftpClient.setUseMListDir(p_b_useMListDir);
		p_o_ftpClient.setSkipCompletePendingCommand(false);
		
		assertTrue(p_o_ftpClient.mkDir(p_s_startingFolderRemote + "second_folder"), "folder '" + p_s_startingFolderRemote + "second_folder' could not be created; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 257, "ftp reply code is not '257', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.upload("Hello World!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", false), "could not upload bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.upload("Hello World!!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", true), "could not append bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.download(p_s_startingFolderRemote + "first_subfolder/test.txt", s_testDirectory + "text.txt"), "could not download file from '" + p_s_startingFolderRemote + "first_subfolder/test.txt'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "text.txt", false);
		assertTrue(o_file.getFileContent().contentEquals("Hello World!\r\nHello World!!\r\n"), "downloaded file content from '" + p_s_startingFolderRemote + "first_subfolder/test.txt' is not equal to uploaded bytes from before");
		
		assertTrue(p_o_ftpClient.rename(p_s_startingFolderRemote + "first_subfolder/test.txt", p_s_startingFolderRemote + "renamed_test.txt"), "could not rename file '" + p_s_startingFolderRemote + "first_subfolder/test.txt' to '" + p_s_startingFolderRemote + "renamed_test.txt'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 250, "ftp reply code is not '250', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.rename(p_s_startingFolderRemote + "first_subfolder", p_s_startingFolderRemote + "delete_subfolder"), "could not rename folder '" + p_s_startingFolderRemote + "first_subfolder' to '" + p_s_startingFolderRemote + "delete_subfolder'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 250, "ftp reply code is not '250', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.directoryExists(p_s_startingFolderRemote + "delete_subfolder"), "directory '" + p_s_startingFolderRemote + "delete_subfolder' does not exist; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.fileExists(p_s_startingFolderRemote + "renamed_test.txt"), "file '" + p_s_startingFolderRemote + "renamed_test.txt' does not exist; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.upload(s_resourcesDirectory + "1-MB-Test.xlsx", p_s_startingFolderRemote + "second_folder/singleFile.xlsx", false), "could not upload file '1-MB-Test.xlsx' to '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.getLength(p_s_startingFolderRemote + "second_folder/singleFile.xlsx") == p_o_ftpClient.download(p_s_startingFolderRemote + "second_folder/singleFile.xlsx").length, "file length from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx' is not equal do downloaded bytes from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 226, "ftp reply code is not '226', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		p_o_ftpClient.uploadFolder(s_resourcesDirectory, p_s_startingFolderRemote + "delete_subfolder/test", true);
		
		java.util.List<net.forestany.forestj.lib.net.ftpcore.Entry> a_result = p_o_ftpClient.ls(p_s_startingFolderRemote + "delete_subfolder/test", false, false, true);
		
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
		
		p_o_ftpClient.downloadFolder(p_s_startingFolderRemote + "delete_subfolder", s_testDirectory + net.forestany.forestj.lib.io.File.DIR + "download_folder", true, true);
		
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
		
		assertTrue(p_o_ftpClient.delete(p_s_startingFolderRemote + "renamed_test.txt"), "could not delete file '" + p_s_startingFolderRemote + "renamed_test.txt'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 250, "ftp reply code is not '250', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.rmDir(p_s_startingFolderRemote + "delete_subfolder"), "could not delete directory '" + p_s_startingFolderRemote + "delete_subfolder'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 250, "ftp reply code is not '250', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		assertTrue(p_o_ftpClient.rmDir(p_s_startingFolderRemote + "second_folder"), "could not delete directory '" + p_s_startingFolderRemote + "second_folder'; " + p_o_ftpClient.getFTPReply());
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 250, "ftp reply code is not '250', but '" + p_o_ftpClient.getFTPReply() + "'");
		
		p_o_ftpClient.logout();
		assertTrue(p_o_ftpClient.getFTPReplyCode() == 221, "ftp reply code is not '221' for logout, but '" + p_o_ftpClient.getFTPReply() + "'");
		
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
