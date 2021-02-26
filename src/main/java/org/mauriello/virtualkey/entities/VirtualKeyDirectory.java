package org.mauriello.virtualkey.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class VirtualKeyDirectory implements Comparable<VirtualKeyDirectory> {

	private String name, parent;
	private HashMap<String, VirtualKeyFile> files = new HashMap<String, VirtualKeyFile>(); 
	private HashMap<String, VirtualKeyDirectory> directories = new HashMap<String, VirtualKeyDirectory>();
	
	public VirtualKeyDirectory(String name, String parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return this.parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Object[] getFiles() {
		return files.values().toArray();
	}
	public void setFiles(HashMap<String, VirtualKeyFile> files) {
		this.files = files;
	}
	public Object[] getDirectories(){
		return directories.values().toArray();
	}
	public void setDirectories(HashMap<String, VirtualKeyDirectory> directories) {
		this.directories = directories;
	}
	
	public ArrayList<VirtualKeyFile> getAllFiles() {
		ArrayList<VirtualKeyFile> allFiles = new ArrayList<VirtualKeyFile>();
		for (Object i: this.getFiles()) {
			allFiles.add((VirtualKeyFile) i);
		}
		for (Object i: this.getDirectories()) {
			allFiles.addAll(((VirtualKeyDirectory) i).getAllFiles());
		}
		return allFiles;
	}
	
	public Object searchFile(String filename) {
		filename = filename.toLowerCase();
		if (filename.equals(this.name.toLowerCase())) {
			return this;
		}
		Object[] filesLower = files.values().toArray();
		for (Object i: filesLower) {
			if (filename.equals( ((VirtualKeyFile)i).getName().toLowerCase())) {
				return i;
			}
		}
		Object[] dirsLower = directories.values().toArray();
		for (Object i: dirsLower) {
			if (filename.equals( ((VirtualKeyDirectory)i).getName().toLowerCase())) {
				return i;
			}
		}
		
		for (VirtualKeyDirectory i: directories.values()) {
			if (i.searchFile(filename) != null){
				return i.searchFile(filename);
			}
		}
		return null;
	}
	
	public boolean removeFile(String fileName) {
		Object file = this.searchFile(fileName);
		fileName = fileName.toLowerCase();
		if ( file != null) {
			for (VirtualKeyFile i: files.values()) {
				if (fileName.equals(i.getName().toLowerCase())) {
					this.deleteFile(i.getName());
					return true;
				}
			}
			for (VirtualKeyDirectory i: directories.values()) {
				if (fileName.equals(i.getName().toLowerCase())) {
					this.deleteDirectory(i.getName());
					return true;
				}
			}			
			for (VirtualKeyDirectory i: directories.values()) {
				if (i.removeFile(fileName)) {
					return true;
				}
			}
			System.out.println("File " + fileName + " was found but was not removed.");
		}
		return false;
	}
	
	public void deleteFile(String fileName) {
		files.remove(fileName);
	}
	public VirtualKeyFile addFile(String fileName) {
		return files.putIfAbsent(fileName, new VirtualKeyFile(fileName));
	}
	public VirtualKeyFile addFile(VirtualKeyFile file) {
		return files.putIfAbsent(file.getName(), file);
	}
	public void deleteDirectory(String dirName) {
		directories.remove(dirName);
	}
	public VirtualKeyDirectory addDirectory(String dirName) {
		return directories.putIfAbsent(dirName, new VirtualKeyDirectory(dirName, this.getName()));
	}
	public VirtualKeyDirectory addDirectory(VirtualKeyDirectory directory) {
		directory.setParent(this.getName());
		return directories.putIfAbsent(directory.getName(), directory);
	}

	@Override
	public int compareTo(VirtualKeyDirectory o) {
		return name.toLowerCase().compareTo(o.getName().toLowerCase());
	}
}
