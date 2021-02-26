package org.mauriello.virtualkey.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.mauriello.virtualkey.entities.VirtualKeyDirectory;
import org.mauriello.virtualkey.entities.VirtualKeyFile;

public class VirtualKeyFunctions {

	public static VirtualKeyDirectory addTestData() {
		VirtualKeyDirectory vkd = new VirtualKeyDirectory("root", ".");
		
		VirtualKeyFile f1 = new VirtualKeyFile("File1", "Sample text inside File 1");
		VirtualKeyFile f2 = new VirtualKeyFile("small", "Not much text in here.");
		vkd.addFile(f1); vkd.addFile(f2);
		
		VirtualKeyDirectory subdir = new VirtualKeyDirectory("subfolder", null);
		f1 = new VirtualKeyFile("Subfile", "This is a subfile inside subdir.");
		f2 = new VirtualKeyFile("subfile2", "A different subfile.");
		subdir.addFile(f1); subdir.addFile(f2); subdir.addDirectory("EmptyDir");
		vkd.addDirectory(subdir);
		
		subdir = new VirtualKeyDirectory("AnotherDir", null);
		f1 = new VirtualKeyFile("Zetafile", "This file should be last in the alphabetical order");
		subdir.addFile(f1);
		
		vkd.addDirectory(subdir);
		
		return vkd;
	}
	
	public static <T> void addObject(T newObject, VirtualKeyDirectory vkd) {
		if (newObject instanceof VirtualKeyFile) {
			if (vkd.searchFile( ((VirtualKeyFile) newObject).getName()) == null) {
				vkd.addFile( (VirtualKeyFile) newObject);
				System.out.println("File added successfully.\n");
			} else {
				System.out.println("FIle was not added, another file already has that name.");
			}
		} else if (newObject instanceof VirtualKeyDirectory) {
			if (vkd.searchFile( ((VirtualKeyDirectory) newObject).getName()) == null) {
				vkd.addDirectory( (VirtualKeyDirectory) newObject);
				System.out.println("Directory added successfully\n");
			} else {
				System.out.println("Directory was not added, another directory with that name already exists.");
			}
		} else {
			System.out.println("Object not recongized.");
		}
	}
	
	public static void returnFileNames(boolean perFolder, VirtualKeyDirectory vkd) {
		if (perFolder) {
			//sort by each folder
			Object[] dirFiles = vkd.getFiles();
			System.out.println("All Files in " + vkd.getParent()  + "/" + vkd.getName() + ", total files: " + dirFiles.length);
			Arrays.sort(dirFiles);
			for (Object i: dirFiles) {
				System.out.println( ((VirtualKeyFile) i).getName());
			}
			Object[] dirDirs = vkd.getDirectories();
			for (Object i: dirDirs) {
				returnFileNames(true, (VirtualKeyDirectory) i);
			}
			
		} else {
			//gather all files then sort.
			System.out.println("All Files in all directories.");
			ArrayList<VirtualKeyFile> allFiles = vkd.getAllFiles(); 
			Collections.sort(allFiles);
			for (VirtualKeyFile i: allFiles) {
				System.out.println(i.getName());
			}
		}
		
		System.out.print("\n");
	}
	
	public static void deleteFile(String fileName, VirtualKeyDirectory vkd) {
		if (vkd.removeFile(fileName)) {
			System.out.println("FIle " + fileName + " has been removed.\n");
		} else {
			System.out.println("File not found.\n");
		}
	}
	
	public static void searchFile(String fileName, VirtualKeyDirectory vkd) {
		Object file = vkd.searchFile(fileName);
		if (file == null) {
			System.out.println("File " + fileName + " cannot be found.\n");
		} else if (file instanceof VirtualKeyFile){
			System.out.println("File found! File is " + ((VirtualKeyFile) file).getName());
			System.out.println("Data of file: " + ((VirtualKeyFile) file).getData() + "\n");
			
		} else if (file instanceof VirtualKeyDirectory) {
			System.out.println("Directory found! Directory is " + ((VirtualKeyDirectory) file).getParent()  + "/" + ((VirtualKeyDirectory) file).getName());
			System.out.println("Files inside this directory are: ");
			Object[] files = ((VirtualKeyDirectory) file).getFiles();
			for (Object i: files) {
				System.out.println( ((VirtualKeyFile) i).getName());
			}
			System.out.println("\n");
		}
	}
}
