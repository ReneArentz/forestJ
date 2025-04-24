package net.forestany.forestj.lib.net.ftpcore;

/**
 * 
 * Data holder class to hold all information about a file or directory on a ftp server.
 *
 */
public class Entry {
	
	/* Fields */
	
	/**
	 * name
	 */
	protected String s_name;
	/**
	 * group
	 */
	protected String s_group;
	/**
	 * user
	 */
	protected String s_user;
	/**
	 * path
	 */
	protected String s_path;
	/**
	 * access
	 */
	protected String s_access;
	/**
	 * size
	 */
	protected long l_size;
	/**
	 * timestamp
	 */
	protected java.util.Date o_timestamp;
	/**
	 * directory flag
	 */
	protected boolean b_directory; 
	
	/* Properties */

	/**
	 * get name
	 * 
	 * @return String
	 */
	public String getName() {
		return this.s_name;
	}
	
	/**
	 * get group
	 * 
	 * @return String
	 */
	public String getGroup() {
		return this.s_group;
	}
	
	/**
	 * get user
	 * 
	 * @return String
	 */
	public String getUser() {
		return this.s_user;
	}
	
	/**
	 * get path
	 * 
	 * @return String
	 */
	public String getPath() {
		return this.s_path;
	}
	
	/**
	 * get full path
	 * 
	 * @return String
	 */
	public String getFullPath() {
		return this.s_path + this.s_name;
	}
	
	/**
	 * get access
	 * 
	 * @return String
	 */
	public String getAccess() {
		return this.s_access;
	}
	
	/**
	 * get size
	 * 
	 * @return long
	 */
	public long getSize() {
		return this.l_size;
	}
	
	/**
	 * get timestamp
	 * 
	 * @return java.util.Date
	 */
	public java.util.Date getTimestamp() {
		return this.o_timestamp;
	}
	
	/**
	 * get directory flag
	 * 
	 * @return boolean
	 */
	public boolean isDirectory() {
		return this.b_directory;
	}
	
	/* Methods */
	
	/**
	 * Constructor to create a ftp entry object
	 * 
	 * @param p_s_name						name of file or directory
	 * @param p_s_group						name of assigned group of entry on ftp server
	 * @param p_s_user						name of assigned user of entry on ftp server
	 * @param p_s_path						path to file or directory
	 * @param p_s_access					copy of ftp raw listing of user, group and all access rights (e.g. rw-r-----)
	 * @param p_l_size						file size
	 * @param p_o_timestamp					timestamp value of file or directory
	 * @param p_b_directory					true - entry is directory, false - entry is file
	 * @throws IllegalArgumentException		parameter value is null or empty, or in case of a numeric value is lower than 0
	 */
	public Entry(
		String p_s_name,
		String p_s_group,
		String p_s_user,
		String p_s_path,
		String p_s_access,
		long p_l_size,
		java.util.Date p_o_timestamp,
		boolean p_b_directory
	) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_name)) {
			throw new IllegalArgumentException("'Name' is null or empty");
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_path)) {
			throw new IllegalArgumentException("'Path' is null or empty");
		}
		
		if (p_l_size < -1) {
			throw new IllegalArgumentException("'Size' is lower than '-1'");
		}
		
		if (p_o_timestamp == null) {
			throw new IllegalArgumentException("'Timestamp' is null or empty");
		}
		
		this.s_name = p_s_name;
		this.s_group = p_s_group;
		this.s_user = p_s_user;
		this.s_path = p_s_path;
		this.s_access = p_s_access;
		this.l_size = p_l_size;
		this.o_timestamp = p_o_timestamp;
		this.b_directory = p_b_directory;
	}
	
	/**
	 * Easy way to show all information of ftp entry and all fields in one string line
	 */
	@Override
	public String toString() {
		/* return value */
		String s_foo = "";
		
		/* iterate each field of Entry class */
		for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
			try {
				/* if we have a generic list */
				if (o_field.get(this) instanceof java.util.List<?>) {
					/* cast field as generic list */
					java.util.List<?> a_objects = (java.util.List<?>)o_field.get(this);
					
					if (a_objects.size() > 0) { /* generic list has elements */
						/* add field name */
						s_foo += o_field.getName() + " = [";
						
						for (Object o_object : a_objects) {
							/* add generic list value to a listing separated by ', ' */
							s_foo += o_object.toString() + ", ";
						}
						
						/* remove last ', ' */
						s_foo = s_foo.substring(0, s_foo.length() - 2);
						
						s_foo += "]";
					} else { /* generic list has no elements, return null */
						s_foo += o_field.getName() + " = null|";
					}
				} else {
					/* add field name and value */
					s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
				}
			} catch (Exception o_exc) { /* handle null pointer or access violation */
				if (o_exc instanceof java.lang.NullPointerException) {
					s_foo += o_field.getName() + " = null|";
				} else {
					s_foo += o_field.getName() + " = ERR:AccessViolation|";
				}
			}
		}
		
		if (s_foo.length() > 0) {
			/* remove last '|' */
			s_foo = s_foo.substring(0, s_foo.length() - 1);
		}
				
		return s_foo;
	}
}
