package de.forestj.lib.io;

/**
 * 
 * Listing element class which is holding general information about a file or a directory element.
 *
 */
public class ListingElement {

	/* Fields */
	
	private String s_name;
	private String s_fullName;
	private boolean b_isDirectory;
	private long l_size;
	private java.time.LocalDateTime dt_creationTime;
	private java.time.LocalDateTime dt_lastAccessTime;
	private java.time.LocalDateTime dt_lastModifiedTime;
	
	/* Properties */
	
	public String getName() {
		return this.s_name;
	}
	
	private void setName(String p_s_value) {
		this.s_name = p_s_value;
	}
	
	public String getFullName() {
		return this.s_fullName;
	}
	
	private void setFullName(String p_s_value) {
		this.s_fullName = p_s_value;
	}
	
	public boolean getIsDirectory() {
		return this.b_isDirectory;
	}
	
	private void setIsDirectory(boolean p_b_value) {
		this.b_isDirectory = p_b_value;
	}
	
	public long getSize() {
		return this.l_size;
	}
	
	private void setSize(long p_l_value) {
		this.l_size = p_l_value;
	}
		
	public java.time.LocalDateTime getCreationTime() {
		return this.dt_creationTime;
	}
	
	private void setCreationTime(java.time.LocalDateTime p_o_datetime) {
		this.dt_creationTime = p_o_datetime;
	}
		
	public java.time.LocalDateTime getLastAccessTime() {
		return this.dt_lastAccessTime;
	}
	
	private void setLastAccessTime(java.time.LocalDateTime p_o_datetime) {
		this.dt_lastAccessTime = p_o_datetime;
	}
	
	public java.time.LocalDateTime getLastModifiedTime() {
		return this.dt_lastModifiedTime;
	}
	
	private void setLastModifiedTime(java.time.LocalDateTime p_o_datetime) {
		this.dt_lastModifiedTime = p_o_datetime;
	}
		
	/* Methods */
	
	/**
	 * Default constructor, all fields have value null, 0 or false
	 */
	public ListingElement() {
		this(null, null, false, 0, null, null, null);
	}
	
	/**
	 * Default constructor, listing element size is set to 0 and time instances are set to null
	 * 
	 * @param p_s_name				name of listing element
	 * @param p_s_fullName			full path + name of listing element
	 * @param p_b_isDirectory		information if listing element is a directory
	 */
	public ListingElement(String p_s_name, String p_s_fullName, boolean p_b_isDirectory) {
		this(p_s_name, p_s_fullName, p_b_isDirectory, 0, null, null, null);
	}
	
	/**
	 * Default constructor
	 * 
	 * @param p_s_name				name of listing element
	 * @param p_s_fullName			full path + name of listing element
	 * @param p_b_isDirectory		information if listing element is a directory
	 * @param p_l_size				file size of listing element
	 * @param p_o_creationTime		creation time of listing element
	 * @param p_o_lastAccessTime	last access time of listing element
	 * @param p_o_lastModifiedTime	last modified time of listing element
	 */
	public ListingElement(String p_s_name, String p_s_fullName, boolean p_b_isDirectory, long p_l_size, java.time.LocalDateTime p_o_creationTime, java.time.LocalDateTime p_o_lastAccessTime, java.time.LocalDateTime p_o_lastModifiedTime) {
		this.setName(p_s_name);
		this.setFullName(p_s_fullName);
		this.setIsDirectory(p_b_isDirectory);
		this.setSize(p_l_size);
		this.setCreationTime(p_o_creationTime);
		this.setLastAccessTime(p_o_lastAccessTime);
		this.setLastModifiedTime(p_o_lastModifiedTime);
	}
	
	/**
	 * Return all listing element information fields as string line
	 * 
	 * @return		string line with all listing element information fields, separated by '|'-sign
	 * @see			String 
	 */
	@Override
	public String toString() {
		StringBuilder s_foo = new StringBuilder();
		
		for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
			String s_value = "null";
			
			try {
				s_value = o_field.get(this).toString();
			} catch (Exception o_exc) {
				/* nothing to do */
			}
			
			s_foo.append(o_field.getName() + " = " + s_value + "|");
		}
		
		return s_foo.toString().substring(0, s_foo.length() - 1);
	}
}
