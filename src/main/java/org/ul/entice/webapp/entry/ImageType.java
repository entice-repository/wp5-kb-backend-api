package org.ul.entice.webapp.entry;

public enum ImageType {
	//assigning the values of enum constants - http://examples.javacodegeeks.com/java-basics/java-enumeration-example/ 
	VMI("VMI"), CI("CI");

	private String value;
	private ImageType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
