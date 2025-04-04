package net.forestany.forestj.sandbox.util;

public class ZipTest {
	public static void testZipProgressBar(String p_s_currentDirectory) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testZipProgressBar" + net.forestany.forestj.lib.io.File.DIR;
		
		if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
		
		String s_file = s_testDirectory + "sourceFile.txt";
		String s_zipFile = s_testDirectory + "zippedFile.zip";
		String s_originalFile = s_testDirectory + "originalSourceFile.txt";
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file, true);
		
		net.forestany.forestj.lib.io.File.replaceFileContent(s_file, net.forestany.forestj.lib.io.File.generateRandomFileContent_250MB());
		
		if (net.forestany.forestj.lib.io.File.fileLength(s_file) != 250L * 1024L * 1024L) {
			throw new Exception ("file length != " + 250L * 1024L * 1024L + " bytes");
		}
		
		net.forestany.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		
		net.forestany.forestj.lib.io.ZIP.IDelegate itf_delegate = new net.forestany.forestj.lib.io.ZIP.IDelegate() { 
			@Override public void PostProgress(double p_d_progress) {
				o_consoleProgressBar.report(p_d_progress);
			}
		};
		
		o_consoleProgressBar.init("Zip . . .", "Done.");
		net.forestany.forestj.lib.io.ZIP.zip(s_file, s_zipFile, itf_delegate); 
		o_consoleProgressBar.close();
		
		System.out.println("Zipped '" + s_file + "' to '" + s_zipFile + "'");
		
		o_consoleProgressBar.init("Check archive . . .", "Done.");
		boolean b_valid = net.forestany.forestj.lib.io.ZIP.checkArchive(s_zipFile, itf_delegate); 
		o_consoleProgressBar.close();
		
		if (b_valid) {
			System.out.println("Zip file '" + s_zipFile + "' is valid");
		} else {
			System.out.println("Zip file '" + s_zipFile + "' is corrupted");
		}
		
		net.forestany.forestj.lib.io.File.moveFile(s_file, s_originalFile);
		
		o_consoleProgressBar.init("Unzip . . .", "Done.");
		net.forestany.forestj.lib.io.ZIP.unzip(s_zipFile, s_testDirectory, true, true, itf_delegate);
		o_consoleProgressBar.close();
		
		System.out.println("Unzipped '" + s_zipFile + "' to '" + s_file + "'");
		
		String s_hashSource = net.forestany.forestj.lib.io.File.hashFile(s_file, "SHA-256");
		String s_hashDestination = net.forestany.forestj.lib.io.File.hashFile(s_originalFile, "SHA-256");
		
		if (!s_hashSource.contentEquals(s_hashDestination)) {
			throw new Exception("hash value of source and destination file are not matching: '" + s_hashSource + "' != '" + s_hashDestination + "'");
		} else {
			System.out.println("Unzipped file matches with original source");
		}
		
		net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
	}
}
