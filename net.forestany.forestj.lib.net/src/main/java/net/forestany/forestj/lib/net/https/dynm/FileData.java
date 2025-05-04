package net.forestany.forestj.lib.net.https.dynm;

/**
 * Encapsulation of POST file data with field name, file name, content type and file data.
 */
public class FileData {
	
	/* Fields */
	
	private String s_fieldName;
	private String s_fileName;
	private String s_contentType;
	private byte[] a_fileData;
	
	/* Properties */
	
	/**
	 * get field name
	 * 
	 * @return String
	 */
	public String getFieldName() {
		return this.s_fieldName;
	}
	
	/**
	 * set field name
	 * 
	 * @param p_s_value String
	 */
	public void setFieldName(String p_s_value) {
		this.s_fieldName = p_s_value;
	}
	
	/**
	 * get file name
	 * 
	 * @return String
	 */
	public String getFileName() {
		return this.s_fileName;
	}
	
	/**
	 * set file name
	 * 
	 * @param p_s_value String
	 */
	public void setFileName(String p_s_value) {
		this.s_fileName = p_s_value;
	}
	
	/**
	 * get content type
	 * 
	 * @return String
	 */
	public String getContentType() {
		return this.s_contentType;
	}
	
	/**
	 * set content type
	 * 
	 * @param p_s_value String
	 */
	public void setContentType(String p_s_value) {
		this.s_contentType = p_s_value;
	}
	
	/**
	 * get file data
	 * 
	 * @return byte[]
	 */
	public byte[] getFileData() {
		return this.a_fileData;
	}
	
	/**
	 * set file data
	 * 
	 * @param p_a_value byte[]
	 */
	public void setFileData(byte[] p_a_value) {
		if ( (p_a_value != null) && (p_a_value.length > 0) ) {
			this.a_fileData = new byte[p_a_value.length];
		
			for (int i = 0; i < p_a_value.length; i++) {
				this.a_fileData[i] = p_a_value[i];
			}
		} else {
			this.a_fileData = p_a_value;
		}
	}
		
	/* Methods */
	
	/**
	 * FileData constructor, no file data
	 * 
	 * @param p_s_fieldName			name of html field element which handled the upload
	 * @param p_s_fileName			file name of source file which was uploaded
	 */
	public FileData(String p_s_fieldName, String p_s_fileName) {
		this(p_s_fieldName, p_s_fileName, null, null);
	}
	
	/**
	 * FileData constructor
	 * 
	 * @param p_s_fieldName			name of html field element which handled the upload
	 * @param p_s_fileName			file name of source file which was uploaded
	 * @param p_s_contentType		content type of source file
	 * @param p_a_fileData			file data
	 */
	public FileData(String p_s_fieldName, String p_s_fileName, String p_s_contentType, byte[] p_a_fileData) {
		this.setFieldName(p_s_fieldName);
		this.setFileName(p_s_fileName);
		this.setContentType(p_s_contentType);
		this.setFileData(p_a_fileData);
	}
}
