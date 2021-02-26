package org.mauriello.virtualkey.entities;

public class VirtualKeyFile implements Comparable<VirtualKeyFile>{

	private String name;
	private String data;
	
	public VirtualKeyFile(String name) {
		this.name = name;
	}
	
	public VirtualKeyFile(String name, String data) {
		this.name = name;
		this.data = data;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return this.data;
	}

	@Override
	public int compareTo(VirtualKeyFile o) {
		return name.toLowerCase().compareTo(o.getName().toLowerCase());
	}
}
