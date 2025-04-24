package net.forestany.forestj.sandbox;

import net.forestany.forestj.sandbox.util.*;

public class Sandbox {
	public static void main(String[] args) {
		System.out.println("Sandbox started . . ." + "\n");
		
		try {
			int i_input = -1;
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			
			do {
				System.out.println("++++++++++++++++++++++++++++++++");
				System.out.println("+ forestJ Library Sandbox      +");
				System.out.println("++++++++++++++++++++++++++++++++");
				
				System.out.println("");
				
				System.out.println("[1] test Console");
				System.out.println("[2] test ConsoleProgressBar");
				System.out.println("[3] test JVMMemoryInfo");
				System.out.println("[4] test Sorts");
				System.out.println("[5] test ZipProgressBar");
				System.out.println("[6] test AI");
				System.out.println("[7] test WebRequestProgressBar");
				System.out.println("[8] test FTPS");
				System.out.println("[9] test SFTP");
				System.out.println("[0] quit");
				
				System.out.println("");
				
				i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-13;0]: ", "Invalid input.", "Please enter a value[1-13;0].");
				
				System.out.println("");
				
				if (i_input == 1) {
					ConsoleTest.testConsole();
				} else if (i_input == 2) {
					ConsoleTest.testConsoleProgressBar();
				} else if (i_input == 3) {
					JVMMemoryInfoTest.testJVMMemoryInfo();
				} else if (i_input == 4) {
					SortsTest.testSorts();
				} else if (i_input == 5) {
					ZipTest.testZipProgressBar(s_currentDirectory);
				} else if (i_input == 6) {
					AITest.testAIMenu(s_currentDirectory);
				} else if (i_input == 7) {
					WebRequestTest.testWebRequestProgressBar(s_currentDirectory, "https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.zip");
				} else if (i_input == 8) {
					FTPSTest.testFTPS(s_currentDirectory, "172.28.234.246", 12221, "user", "user");
				} else if (i_input == 9) {
					SFTPTest.testSFTP(s_currentDirectory, "172.28.234.246", 2222, "user", "user");
				}
								
				if ( (i_input >= 1) && (i_input <= 13) ) {
					System.out.println("");
					
					net.forestany.forestj.lib.Console.consoleInputString("Press any key to continue . . . ", true);
					
					System.out.println("");
				}
				
				System.out.println("");
				
			} while (i_input != 0);
		} catch (Exception o_exc) {
			o_exc.printStackTrace();
		}
		
		System.out.println("\n" + " . . . Sandbox finished");
	}
}
