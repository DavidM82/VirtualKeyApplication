package org.mauriello.virtualkey.entities;

import java.util.Scanner;
import org.mauriello.virtualkey.functions.VirtualKeyFunctions;

public class UserInterface {

	private static String welcome = "Welcome to VirtualKey Application!\n\tCreated by David Mauriello\n";
	private static String[] options = {"1. Show all files\n", "2. Search for a file\n", "3. Delete a file\n","4. Add a file\n","5. Exit Application.\n", "Select an option: "};
	private static Scanner scan;
	private static VirtualKeyDirectory root;
	//private static Stream<String> stream = Stream.of(options);
	
	public static void virtualKeyApplication(boolean sampleData) {
		scan = new Scanner(System.in);
		
		root = (sampleData ? VirtualKeyFunctions.addTestData(): new VirtualKeyDirectory("root", ".") );
		
		System.out.println(welcome);
		
		while (true) {
			for (String i: options) {
				System.out.print(i);
			}
			int input;
			boolean isInt = scan.hasNextInt();
			if (!isInt) {
				System.out.println("Error, not a number!\n"); 
			} else {
				input = scan.nextInt();
				switch(input){
					case 1: 
						System.out.print("Would you like the files ordered by their directory? (y/n/c) ");
						String gatherFiles = scan.next();
						boolean isChar = gatherFiles.length() > 0 && gatherFiles.length() < 2;
						
						if (isChar) {
							if (gatherFiles.toLowerCase().equals("y")) {
								VirtualKeyFunctions.returnFileNames(true, root);
							} else if (gatherFiles.toLowerCase().equals("n")) {
								VirtualKeyFunctions.returnFileNames(false, root);
							}else if (gatherFiles.toLowerCase().equals("c")) {
								System.out.println("Process cancelled.\n");
							} else {
								System.out.println("Error, not a y (yes), no (no), or c (cancel).\n");
							}
						} else {
							System.out.println("Input was not a y (yes) or n (no) or c (cancel).\n");
						}
						break;
					
					case 2: 
						System.out.print("Type in the name of file you wish to find (no spaces): ");
						String findFile = scan.next();
						VirtualKeyFunctions.searchFile(findFile, root);
						
						break;
					
					case 3: 
						System.out.print("Type in the name of file you wish to remove (No spaces): ");
						String removeFile = scan.next();
						VirtualKeyFunctions.deleteFile(removeFile, root);
						break;
					
					case 4:
						System.out.print("Type in the name of the new file (no spaces): ");
						String newName = scan.next();
						System.out.print("Is this a file or a directory? (f/d/c) ");
						String newObject = scan.next();
						boolean newObjectChar = newObject.length() > 0 && newObject.length() < 2;
						
						if (newObjectChar) {
							if (newObject.toLowerCase().equals("f")) {
								VirtualKeyFile file = new VirtualKeyFile(newName, "System-generated file");
								VirtualKeyFunctions.addObject(file, root);
							} else if (newObject.toLowerCase().equals("d")) {
								VirtualKeyDirectory file = new VirtualKeyDirectory(newName, root.getName());
								VirtualKeyFunctions.addObject(file, root);
							} else if (newObject.toLowerCase().equals("c")) {
								System.out.println("Process cancelled.\n");
							}
							else {
								System.out.println("Error, not a f (file), d (directory) or c (cancel).\n");
							}
						} else {
							System.out.println("Input was not a f (file), d directory) or c (cancel).\n");
						}
						
						break;
					
					case 5:
						System.out.println("Thank you for using VirtualKey!");
						scan.close(); System.exit(1);
					default:
						System.out.println("That's not a valid input, please select 1, 2, 3 or 4.");
				}	
			}
			scan.nextLine();
		}
	}

}
