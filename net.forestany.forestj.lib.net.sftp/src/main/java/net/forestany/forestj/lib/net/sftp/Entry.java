package net.forestany.forestj.lib.net.sftp;

/**
 * Data holder class to hold all information about a file or directory on a sftp server, inheriting from net.forestany.forestj.lib.net.ftp.Entry.
 */
public class Entry extends net.forestany.forestj.lib.net.ftpcore.Entry {
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Constructor to create a sftp entry object
	 * 
	 * @param p_s_name						name of file or directory
	 * @param p_s_group						name of assigned group of entry on sftp server
	 * @param p_s_user						name of assigned user of entry on sftp server
	 * @param p_s_path						path to file or directory
	 * @param p_s_access					copy of sftp raw listing of user, group and all access rights
	 * @param p_l_size						file size
	 * @param p_o_timestamp					timestamp value of file or directory
	 * @param p_b_directory					true - entry is directory, false - entry is file
	 * @throws IllegalArgumentException		parameter value is null or empty, or in case of a numeric value is lower than 0
	 */
	public Entry(String p_s_name,
		String p_s_group,
		String p_s_user,
		String p_s_path,
		String p_s_access,
		long p_l_size,
		java.util.Date p_o_timestamp,
		boolean p_b_directory
	) throws IllegalArgumentException {
		super(p_s_name, p_s_group, p_s_user, p_s_path, p_s_access, p_l_size, p_o_timestamp, p_b_directory);
	}
}
