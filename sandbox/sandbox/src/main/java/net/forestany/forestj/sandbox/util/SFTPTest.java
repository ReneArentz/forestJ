package net.forestany.forestj.sandbox.util;

public class SFTPTest {
	public static void testSFTP(String p_s_currentDirectory, String p_s_sftpHost, int p_i_sftpPort, String p_s_sftpUser, String p_s_sftpPassword) throws Exception {
		testSFTPProgressBar(p_s_currentDirectory, p_s_sftpHost, p_i_sftpPort, p_s_sftpUser, p_s_sftpPassword, "/", null);
		testSFTPProgressBar(p_s_currentDirectory, p_s_sftpHost, p_i_sftpPort, p_s_sftpUser, p_s_sftpPassword, "/", new net.forestany.forestj.lib.ConsoleProgressBar());
	}
	
	private static void testSFTPProgressBar(String p_s_currentDirectory, String p_s_sftpHostIp, int p_i_sftpPort, String p_s_sftpUser, String p_s_sftpPassword, String p_s_startingFolderRemote, net.forestany.forestj.lib.ConsoleProgressBar p_o_consoleProgressBar) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testSftp" + net.forestany.forestj.lib.io.File.DIR;
		String s_resourcesDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ftp" + net.forestany.forestj.lib.io.File.DIR;
		
		/*
		 * !!!!!!!!!!!!!!!!!
		 * it is maybe necessary to create new authentication and known_hosts file
		 * !!!!!!!!!!!!!!!!!
		 */
		
		String s_fileAuthentication = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "sftp" + net.forestany.forestj.lib.io.File.DIR + "sftp_id_rsa";
		String s_fileKnownHosts = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "sftp" + net.forestany.forestj.lib.io.File.DIR + "known_hosts";
			
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		
		net.forestany.forestj.lib.net.sftp.Client o_sftpClient = new net.forestany.forestj.lib.net.sftp.Client("sftp://" + p_s_sftpHostIp, p_i_sftpPort, p_s_sftpUser, s_fileAuthentication, p_s_sftpPassword, s_fileKnownHosts);
		
		boolean b_consoleProgressBarParameter = false;
		
		if (p_o_consoleProgressBar == null) {
			o_sftpClient.setProgressBar(new net.forestany.forestj.lib.ConsoleProgressBar());
		} else {
			o_sftpClient.setDelegate(
				new net.forestany.forestj.lib.net.sftp.Client.IDelegate() {
					@Override public void PostProgress(double p_d_progress) {
						p_o_consoleProgressBar.report(p_d_progress);
					}
				}
			);
			
			b_consoleProgressBarParameter = true;
		}
			
		o_sftpClient.setDelegateFolder(
			new net.forestany.forestj.lib.net.sftp.Client.IDelegateFolder() { 
				@Override public void PostProgressFolder(int p_i_filesProcessed, int p_i_files) {
					System.out.println(p_i_filesProcessed + "/" + p_i_files);
				}
			}
		);
		
		o_sftpClient.mkDir(p_s_startingFolderRemote + "second_folder");
		if (!o_sftpClient.directoryExists(p_s_startingFolderRemote + "second_folder")) {
			throw new Exception("folder '" + p_s_startingFolderRemote + "second_folder' could not be created");
		}
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Upload bytes . . .", "Done.");
		o_sftpClient.upload("Hello World!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", false);
		if (!o_sftpClient.fileExists(p_s_startingFolderRemote + "first_subfolder/test.txt")) {
			throw new Exception("could not upload bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
			
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Upload bytes . . .", "Done.");
		o_sftpClient.upload("Hello World!!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", true);
		if (!o_sftpClient.fileExists(p_s_startingFolderRemote + "first_subfolder/test.txt")) {
			throw new Exception("could not append bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Download bytes . . .", "Done.");
		o_sftpClient.download(p_s_startingFolderRemote + "first_subfolder/test.txt", s_testDirectory + "text.txt");
		if (!net.forestany.forestj.lib.io.File.exists(s_testDirectory + "text.txt")) {
			throw new Exception("could not download file to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
			
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "text.txt", false);
		if (!o_file.getFileContent().contentEquals("Hello World!\r\nHello World!!\r\n")) {
			throw new Exception("downloaded file content from '" + p_s_startingFolderRemote + "first_subfolder/test.txt' is not equal to uploaded bytes from before");
		}
		
		o_sftpClient.rename(p_s_startingFolderRemote + "first_subfolder/test.txt", p_s_startingFolderRemote + "renamed_test.txt");
		if (!o_sftpClient.fileExists(p_s_startingFolderRemote + "renamed_test.txt")) {
			throw new Exception("could not rename file '" + p_s_startingFolderRemote + "first_subfolder/test.txt' to '" + p_s_startingFolderRemote + "renamed_test.txt'");
		}
		
		o_sftpClient.rename(p_s_startingFolderRemote + "first_subfolder", p_s_startingFolderRemote + "delete_subfolder");
		if (!o_sftpClient.directoryExists(p_s_startingFolderRemote + "delete_subfolder")) {
			throw new Exception("could not rename folder '" + p_s_startingFolderRemote + "first_subfolder' to '" + p_s_startingFolderRemote + "delete_subfolder'");
		}
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Upload bytes . . .", "Done.");
		o_sftpClient.upload(s_resourcesDirectory + "1-MB-Test.xlsx", p_s_startingFolderRemote + "second_folder/singleFile.xlsx", false);
		if (!o_sftpClient.fileExists(p_s_startingFolderRemote + "second_folder/singleFile.xlsx")) {
			throw new Exception("could not upload file '1-MB-Test.xlsx' to '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Download bytes . . .", "Done.");
		if (o_sftpClient.getLength(p_s_startingFolderRemote + "second_folder/singleFile.xlsx") != o_sftpClient.download(p_s_startingFolderRemote + "second_folder/singleFile.xlsx").length) {
			throw new Exception("file length from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx' is not equal do downloaded bytes from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
		o_sftpClient.uploadFolder(s_resourcesDirectory, p_s_startingFolderRemote + "delete_subfolder/test", true);
		
		java.util.List<net.forestany.forestj.lib.net.sftp.Entry> a_result = o_sftpClient.ls(p_s_startingFolderRemote + "delete_subfolder/test", false, false, false, true);
		
		int i_countFiles = 0;
		int i_coundDirectories = 0;
		
		for (net.forestany.forestj.lib.net.ftpcore.Entry o_entry : a_result) {
			if (o_entry.isDirectory()) {
				i_coundDirectories++;
			} else {
				i_countFiles++;
			}
		}
		
		if (i_coundDirectories != 1) {
			throw new Exception("found remote directories under '" + p_s_startingFolderRemote + "delete_subfolder/test' are not '1', but '" + i_coundDirectories + "'");
		}
		if (i_countFiles != 6) {
			throw new Exception("found remote files under '" + p_s_startingFolderRemote + "delete_subfolder/test' are not '6', but '" + i_countFiles + "'");
		}
		
		o_sftpClient.downloadFolder(p_s_startingFolderRemote + "delete_subfolder", s_testDirectory + net.forestany.forestj.lib.io.File.DIR + "download_folder", true, true);
		
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
		
		if (i_coundDirectories != 1) {
			throw new Exception("found local directories under './download_folder' are not '1', but '" + i_coundDirectories + "'");
		}
		if (i_countFiles != 6) {
			throw new Exception("found local files under './download_folder' are not '6', but '" + i_countFiles + "'");
		}
		
		long l_left = net.forestany.forestj.lib.io.File.fileLength(s_resourcesDirectory + "10-MB-Test.xlsx");
		long l_right = net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "download_folder" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "10-MB-Test.xlsx");
		
		if (l_left != l_right) {
			throw new Exception("file length of '10-MB-Test.xlsx' in upload sources and downloaded folder are not equal: '" + l_left + "' != '" + l_right + "'");
		}
		
		o_sftpClient.delete(p_s_startingFolderRemote + "renamed_test.txt");
		if (o_sftpClient.fileExists(p_s_startingFolderRemote + "renamed_test.txt")) {
			throw new Exception("could not delete file '" + p_s_startingFolderRemote + "renamed_test.txt'");
		}
		
		o_sftpClient.rmDir(p_s_startingFolderRemote + "delete_subfolder");
		if (o_sftpClient.directoryExists(p_s_startingFolderRemote + "delete_subfolder")) {
			throw new Exception("could not delete directory '" + p_s_startingFolderRemote + "delete_subfolder'");
		}
		
		o_sftpClient.rmDir(p_s_startingFolderRemote + "second_folder");
		if (o_sftpClient.directoryExists(p_s_startingFolderRemote + "second_folder")) {
			throw new Exception("could not delete directory '" + p_s_startingFolderRemote + "second_folder'");
		}
		
		o_sftpClient.logout();
		
		/* ******** */
		/* clean up */
		/* ******** */
		
		net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
	}
}
