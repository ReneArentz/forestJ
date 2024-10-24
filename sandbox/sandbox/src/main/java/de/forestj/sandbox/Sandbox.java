package de.forestj.sandbox;

import de.forestj.sandbox.util.*;

public class Sandbox {
	public static void main(String[] args) {
		System.out.println("Sandbox started . . ." + "\n");
		
		try {
			int i_input = -1;
			
			do {
				System.out.println("++++++++++++++++++++++++++++++++");
				System.out.println("+ forestJ Library Sandbox      +");
				System.out.println("++++++++++++++++++++++++++++++++");
				
				System.out.println("");
				
				System.out.println("[1] test Console");
				System.out.println("[0] quit");
				
				System.out.println("");
				
				i_input = de.forestj.lib.Console.consoleInputInteger("Enter menu number[1-11;0]: ", "Invalid input.", "Please enter a value[1-11;0].");
				
				System.out.println("");
				
				if (i_input == 1) {
					ConsoleTest.testConsole();
				}
				
				if ( (i_input >= 1) && (i_input <= 12) ) {
					System.out.println("");
					
					de.forestj.lib.Console.consoleInputString("Press any key to continue . . . ", true);
					
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
