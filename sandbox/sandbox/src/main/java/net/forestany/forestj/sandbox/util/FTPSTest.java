package net.forestany.forestj.sandbox.util;

public class FTPSTest {
	public static void testFTPS(String p_s_currentDirectory, String p_s_ftpHost, int p_i_ftpPort, String p_s_ftpUser, String p_s_ftpPassword) throws Exception {
		testFTPSProgressBar(p_s_currentDirectory, p_s_ftpHost, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword, "/", false, null);
		testFTPSProgressBar(p_s_currentDirectory, p_s_ftpHost, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword, "/", true, null);
		testFTPSProgressBar(p_s_currentDirectory, p_s_ftpHost, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword, "/", true, new net.forestany.forestj.lib.ConsoleProgressBar());
		testFTPSProgressBar(p_s_currentDirectory, p_s_ftpHost, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword, "/", false, new net.forestany.forestj.lib.ConsoleProgressBar());
	}
	
	private static void testFTPSProgressBar(String p_s_currentDirectory, String p_s_ftpHostIp, int p_i_ftpPort, String p_s_ftpUser, String p_s_ftpPassword, String p_s_startingFolderRemote, boolean p_b_useMListDir, net.forestany.forestj.lib.ConsoleProgressBar p_o_consoleProgressBar) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFtp" + net.forestany.forestj.lib.io.File.DIR;
		String s_resourcesDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ftp" + net.forestany.forestj.lib.io.File.DIR;
		
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		
		net.forestany.forestj.lib.net.ftp.Client o_ftpClient = new net.forestany.forestj.lib.net.ftp.Client("ftps://" + p_s_ftpHostIp, p_i_ftpPort, p_s_ftpUser, p_s_ftpPassword);
		
		o_ftpClient.setUseMListDir(p_b_useMListDir);
		o_ftpClient.setSkipCompletePendingCommand(false);
		
		boolean b_consoleProgressBarParameter = false;
		
		if (p_o_consoleProgressBar == null) {
			o_ftpClient.setProgressBar(new net.forestany.forestj.lib.ConsoleProgressBar());
		} else {
			o_ftpClient.setDelegate(
				new net.forestany.forestj.lib.net.ftp.Client.IDelegate() {
					@Override public void PostProgress(double p_d_progress) {
						p_o_consoleProgressBar.report(p_d_progress);
					}
				}
			);
			
			b_consoleProgressBarParameter = true;
		}
			
		o_ftpClient.setDelegateFolder(
			new net.forestany.forestj.lib.net.ftp.Client.IDelegateFolder() { 
				@Override public void PostProgressFolder(int p_i_filesProcessed, int p_i_files) {
					System.out.println(p_i_filesProcessed + "/" + p_i_files);
				}
			}
		);
		
		if (!o_ftpClient.mkDir(p_s_startingFolderRemote + "second_folder")) {
			throw new Exception("folder '" + p_s_startingFolderRemote + "second_folder' could not be created; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 257) {
			throw new Exception("ftp reply code is not '257', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Upload bytes . . .", "Done.");
		if (!o_ftpClient.upload("Hello World!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", false)) {
			throw new Exception("could not upload bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Upload bytes . . .", "Done.");
		if (!o_ftpClient.upload("Hello World!!\r\n".getBytes(), p_s_startingFolderRemote + "first_subfolder/test.txt", true)) {
			throw new Exception("could not append bytes to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Download bytes . . .", "Done.");
		if (!o_ftpClient.download(p_s_startingFolderRemote + "first_subfolder/test.txt", s_testDirectory + "text.txt")) {
			throw new Exception("could not download file to '" + p_s_startingFolderRemote + "first_subfolder/test.txt'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "text.txt", false);
		if (!o_file.getFileContent().contentEquals("Hello World!\r\nHello World!!\r\n")) {
			throw new Exception("downloaded file content from '" + p_s_startingFolderRemote + "first_subfolder/test.txt' is not equal to uploaded bytes from before");
		}
		
		if (!o_ftpClient.rename(p_s_startingFolderRemote + "first_subfolder/test.txt", p_s_startingFolderRemote + "renamed_test.txt")) {
			throw new Exception("could not rename file '" + p_s_startingFolderRemote + "first_subfolder/test.txt' to '" + p_s_startingFolderRemote + "renamed_test.txt'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 250) {
			throw new Exception("ftp reply code is not '250', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		if (!o_ftpClient.rename(p_s_startingFolderRemote + "first_subfolder", p_s_startingFolderRemote + "delete_subfolder")) {
			throw new Exception("could not rename folder '" + p_s_startingFolderRemote + "first_subfolder' to '" + p_s_startingFolderRemote + "delete_subfolder'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 250) {
			throw new Exception("ftp reply code is not '250', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		if (!o_ftpClient.directoryExists(p_s_startingFolderRemote + "delete_subfolder")) {
			throw new Exception("directory '" + p_s_startingFolderRemote + "delete_subfolder' does not exists; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		if (!o_ftpClient.fileExists(p_s_startingFolderRemote + "renamed_test.txt")) {
			throw new Exception("file '" + p_s_startingFolderRemote + "renamed_test.txt' does not exists; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Upload bytes . . .", "Done.");
		if (!o_ftpClient.upload(s_resourcesDirectory + "1-MB-Test.xlsx", p_s_startingFolderRemote + "second_folder/singleFile.xlsx", false)) {
			throw new Exception("could not upload file '1-MB-Test.xlsx' to '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
		
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.init("Download bytes . . .", "Done.");
		if (o_ftpClient.getLength(p_s_startingFolderRemote + "second_folder/singleFile.xlsx") != o_ftpClient.download(p_s_startingFolderRemote + "second_folder/singleFile.xlsx").length) {
			throw new Exception("file length from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx' is not equal do downloaded bytes from '" + p_s_startingFolderRemote + "second_folder/singleFile.xlsx'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 226) {
			throw new Exception("ftp reply code is not '226', but '" + o_ftpClient.getFTPReply() + "'");
		}
			if (b_consoleProgressBarParameter) p_o_consoleProgressBar.close();
			
		o_ftpClient.uploadFolder(s_resourcesDirectory, p_s_startingFolderRemote + "delete_subfolder/test", true);
		
		java.util.List<net.forestany.forestj.lib.net.ftpcore.Entry> a_result = o_ftpClient.ls(p_s_startingFolderRemote + "delete_subfolder/test", false, false, true);
		
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
		
		o_ftpClient.downloadFolder(p_s_startingFolderRemote + "delete_subfolder", s_testDirectory + net.forestany.forestj.lib.io.File.DIR + "download_folder", true, true);
		
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
		
		if (!o_ftpClient.delete(p_s_startingFolderRemote + "renamed_test.txt")) {
			throw new Exception("could not delete file '" + p_s_startingFolderRemote + "renamed_test.txt'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 250) {
			throw new Exception("ftp reply code is not '250', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		if (!o_ftpClient.rmDir(p_s_startingFolderRemote + "delete_subfolder")) {
			throw new Exception("could not delete directory '" + p_s_startingFolderRemote + "delete_subfolder'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 250) {
			throw new Exception("ftp reply code is not '250', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		if (!o_ftpClient.rmDir(p_s_startingFolderRemote + "second_folder")) {
			throw new Exception("could not delete directory '" + p_s_startingFolderRemote + "second_folder'; " + o_ftpClient.getFTPReply());
		}
		if (o_ftpClient.getFTPReplyCode() != 250) {
			throw new Exception("ftp reply code is not '250', but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		o_ftpClient.logout();
		if (o_ftpClient.getFTPReplyCode() != 221) {
			throw new Exception("ftp reply code is not '221' for logout, but '" + o_ftpClient.getFTPReply() + "'");
		}
		
		/* ******** */
		/* clean up */
		/* ******** */
		
		net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
	}
}
