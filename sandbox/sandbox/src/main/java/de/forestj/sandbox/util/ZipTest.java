package de.forestj.sandbox.util;

public class ZipTest {
	public static void testZipProgressBar(String p_s_currentDirectory) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_testDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR + "testZipProgressBar" + de.forestj.lib.io.File.DIR;
		
		if ( de.forestj.lib.io.File.folderExists(s_testDirectory) ) {
			de.forestj.lib.io.File.deleteDirectory(s_testDirectory);
		}
		
		de.forestj.lib.io.File.createDirectory(s_testDirectory);
		
		String s_file = s_testDirectory + "sourceFile.txt";
		String s_zipFile = s_testDirectory + "zippedFile.zip";
		String s_originalFile = s_testDirectory + "originalSourceFile.txt";
		@SuppressWarnings("unused")
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(s_file, true);
		
		de.forestj.lib.io.File.replaceFileContent(s_file, de.forestj.lib.io.File.generateRandomFileContent_250MB());
		
		if (de.forestj.lib.io.File.fileLength(s_file) != 250L * 1024L * 1024L) {
			throw new Exception ("file length != " + 250L * 1024L * 1024L + " bytes");
		}
		
		de.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new de.forestj.lib.ConsoleProgressBar();
		
		de.forestj.lib.io.ZIP.IDelegate itf_delegate = new de.forestj.lib.io.ZIP.IDelegate() { 
			@Override public void PostProgress(double p_d_progress) {
				o_consoleProgressBar.report(p_d_progress);
			}
		};
		
		o_consoleProgressBar.init("Zip . . .", "Done.");
		de.forestj.lib.io.ZIP.zip(s_file, s_zipFile, itf_delegate); 
		o_consoleProgressBar.close();
		
		System.out.println("Zipped '" + s_file + "' to '" + s_zipFile + "'");
		
		o_consoleProgressBar.init("Check archive . . .", "Done.");
		boolean b_valid = de.forestj.lib.io.ZIP.checkArchive(s_zipFile, itf_delegate); 
		o_consoleProgressBar.close();
		
		if (b_valid) {
			System.out.println("Zip file '" + s_zipFile + "' is valid");
		} else {
			System.out.println("Zip file '" + s_zipFile + "' is corrupted");
		}
		
		de.forestj.lib.io.File.moveFile(s_file, s_originalFile);
		
		o_consoleProgressBar.init("Unzip . . .", "Done.");
		de.forestj.lib.io.ZIP.unzip(s_zipFile, s_testDirectory, true, true, itf_delegate);
		o_consoleProgressBar.close();
		
		System.out.println("Unzipped '" + s_zipFile + "' to '" + s_file + "'");
		
		String s_hashSource = de.forestj.lib.io.File.hashFile(s_file, "SHA-256");
		String s_hashDestination = de.forestj.lib.io.File.hashFile(s_originalFile, "SHA-256");
		
		if (!s_hashSource.contentEquals(s_hashDestination)) {
			throw new Exception("hash value of source and destination file are not matching: '" + s_hashSource + "' != '" + s_hashDestination + "'");
		} else {
			System.out.println("Unzipped file matches with original source");
		}
		
		de.forestj.lib.io.File.deleteDirectory(s_testDirectory);
	}
}
